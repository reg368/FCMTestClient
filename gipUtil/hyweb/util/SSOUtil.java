package hyweb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author ycli
 *
 */
public class SSOUtil {

	private static final Logger logger = LoggerFactory.getLogger(SSOUtil.class);
	
	public static boolean checkSSO(HttpServletRequest request, HttpServletResponse response, String siteId) throws UnsupportedEncodingException, IOException{
				
		boolean result = false, getTicekt = false;
		String url = "", ticketURL = "", ticket ="", validate = "", firstFlag = "";
		String service = "", username = "", password = "";
		
	    //post的參數
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();			
	    
        try{
        	
        	url = Cfg.get("SSOURL"); 
        	service = Cfg.get("SERVICEURL") + "masterpage-"+ siteId;
        	firstFlag = SSOUtil.isNull( request.getParameter("first") );
        	ticket = SSOUtil.isNull( request.getParameter("ticket") );
    
        	//step1:檢查是否能取得ticket 
        	logger.info("firstFlag:"+firstFlag);
        	
        		if( !"".equals(ticket) ){	//有取得ticket
        			getTicekt = true;
        			ticketURL = service + "?first=Y&ticket=" + ticket;
        			logger.info("step1:"+ticketURL);
        			
        		}else if( "Y".equals(firstFlag) ){	//沒有取得ticket、輸入帳密
                	//step2:帳密驗證
                	username = SSOUtil.isNull( request.getParameter("username") );
                	password = SSOUtil.isNull( request.getParameter("password") );
                    	
                	//post的參數
                    nvps.add(new BasicNameValuePair("username", username));
                    nvps.add(new BasicNameValuePair("password", password) );
                    nvps.add(new BasicNameValuePair("service", service));
                    nvps.add(new BasicNameValuePair("gateway", "true"));          
                	logger.info("username:"+username+"，password:"+password);
                	logger.info("service:"+service+"，url:"+url);
                	
                    //ticketURL = sendPost("2", url+"login", nvps);
                	//因為密碼中可能有特殊符號，所以改成將url串在一起，並將password做URLEncoding	2011-04-27
                	ticketURL = sendPost("2", url+"login?service="+service +"&username="+username+"&password="+java.net.URLEncoder.encode(password, "UTF-8")+"&gateway=true" ,null);
                	logger.info("step2:"+ticketURL);

                    if( ticketURL.indexOf("ticket=") > 0 ){	//驗證成功，有取到ticket
                    	getTicekt = true;
                    }
        		}
 
            
            //step3:針對ticket進行validate
            if( getTicekt ){
            	ticket = ticketURL.substring( ticketURL.indexOf("ticket=")+7 );
            	//post的參數
            	if( ticketURL.indexOf("first")>0 ) service += "?first=Y";	//其他系統已登入帶過來的     		
            	nvps = new ArrayList <NameValuePair>();
            	nvps.add(new BasicNameValuePair("ticket", ticket.trim()));
            	nvps.add(new BasicNameValuePair("service", service));
            	validate = sendPost("1", url+"validate", nvps);

            	logger.info("validate:"+validate);
       
            	if( validate.startsWith("yes") ){	//驗證成功  		
            		result = true;
            	}
            }            
        	
        }catch(Exception e){
        	e.printStackTrace();
        }
        
		return result;
	}
	
	/**
	 * 利用httpclient丟出request
	 * @param type 回傳值的類別，1:response的content、2:header中location的值
	 * @param url url連結
	 * @param nvps post的參數
	 * @return 結果字串
	 */
	public static String sendPost(String type, String url, List <NameValuePair> nvps) throws Exception{
		String result = "";
		int timeoutMilliseconds = 3000;
		DefaultHttpClient httpclient = null;
		String keyStorePath = "", keyStorePass = "";
		int httpsSocket = 443;
		
		try{

			keyStorePath = Cfg.get("KEYSTOREPATH");
			keyStorePass = Cfg.get("KEYSTOREPASS");
			httpsSocket = Integer.parseInt(Cfg.get("SSLPORT"));
	
			HttpPost httpost = new HttpPost(url);

			//SSL
		    KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());

		    FileInputStream instream = new FileInputStream(new File(keyStorePath)); 
		    try {
		       trustStore.load(instream, keyStorePass.toCharArray());
		    } finally {
		       instream.close();
		    }	     	    		
	
			HttpParams params = new BasicHttpParams();		
//			ConnManagerParams.setMaxTotalConnections(params, 100);		
//			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);		
//			HttpProtocolParams.setUseExpectContinue(params, false);	
//			ConnManagerParams.setTimeout(params, 1000);//in millisecs
			
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);

			Scheme sch = new Scheme("https", socketFactory, httpsSocket);

			schemeRegistry.register(sch);
			
			ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
			
			httpclient = new DefaultHttpClient(cm, params);	
			
			/**
			 * 比較簡化的寫法
			 * 
			 * SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
		     * Scheme sch = new Scheme("https", socketFactory, 8443);
			 * httpclient.getConnectionManager().getSchemeRegistry().register(sch);
			 * 
			 * httpclient = new DefaultHttpClient();
			 * 
			 */
		    
			//Timeout的設定
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeoutMilliseconds);	//connection timeout			
			HttpConnectionParams.setSoTimeout(httpParams, timeoutMilliseconds);		//socket timeout					
	        
	        //參數編碼
			if( nvps != null )					
				httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));        


	        HttpResponse res = httpclient.execute(httpost);	        

	        HttpEntity entity = res.getEntity();
	        	        
	        if (entity != null) {

		        if( "1".equals(type) ){	//response的content
		        	result = EntityUtils.toString(entity);
     	
		        }else if( "2".equals(type) ){	//遇到重導時，需透過header中的location參數中抓出重導的URL
			        Header header = res.getFirstHeader("Location");
			        if( header != null )
			        	result = header.getValue();		        	
		        }
	        }                      

		}finally{	        
	        //close連線
	        // When HttpClient instance is no longer needed, 
	        // shut down the connection manager to ensure
	        // immediate deallocation of all system resources
			if(httpclient  !=  null){
				ClientConnectionManager  ccm  =  httpclient.getConnectionManager();
				if(ccm  !=  null){
					ccm.shutdown();
				}
			}
		}
		return result;
	}


	
	/**
	 * 判斷是否為null，null時回傳空字串
	 * @param obj
	 * @return
	 */
	public static String isNull(Object obj){
		if( obj == null ){
			return "";
		}else{
			return obj.toString();
		}
	}
	
	public static String sendURL(String url, List <NameValuePair> nvps) {		
		String result = "";
		int timeoutMilliseconds = 3000;
		DefaultHttpClient httpclient = null;
		try{
			HttpPost httpost = new HttpPost(url);

			httpclient = new DefaultHttpClient();
			
			//Timeout的設定
			HttpParams httpParams = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeoutMilliseconds);	//connection timeout			
			HttpConnectionParams.setSoTimeout(httpParams, timeoutMilliseconds);		//socket timeout					
	        
	        //參數編碼
			if( nvps != null )
	        	httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));        
	        
	        HttpResponse res = httpclient.execute(httpost);	 
	        
	        HttpEntity entity = res.getEntity();
	        	        
	        if (entity != null) {	        	
	        	result = EntityUtils.toString(entity);	   
	        	if( "".equals(result) ){
			        Header header = res.getFirstHeader("Location");
			        if( header != null )
			        	result = header.getValue();	        		
	        	}
	        }
//	        System.out.println("statusCode:"+res.getStatusLine());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
	        httpclient.getConnectionManager().shutdown();  			
		}
		
//		System.out.println("result:"+result);
		return result;
	}
	
}
