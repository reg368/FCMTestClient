package hyweb.core.kit;

import hyweb.core.net.WGet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author A0074
 * @version 1.0.120705
 * @since xBox 1.0
 */
public final class ServletKit {
	private static final Map<String, String> MIME_TYPE_MAP;
	static{
		MIME_TYPE_MAP = CollectionsKit.newMap();
		MIME_TYPE_MAP.put("DOC", "application/msword");
		MIME_TYPE_MAP.put("DOCX", "application/msword");
		MIME_TYPE_MAP.put("XLS", "application/vnd.ms-excel");
		MIME_TYPE_MAP.put("XLSX", "application/vnd.ms-excel");
		MIME_TYPE_MAP.put("PPT", "application/vnd.ms-powerpoint");
		MIME_TYPE_MAP.put("PPTX", "application/vnd.ms-powerpoint");
		MIME_TYPE_MAP.put("PDF", "application/pdf");
		MIME_TYPE_MAP.put("ZIP", "application/zip");
		MIME_TYPE_MAP.put("RAR", "application/x-rar-compressed");
		MIME_TYPE_MAP.put("7Z", "application/application/x-7z-compressed");
		MIME_TYPE_MAP.put("JPG", "image/jpeg");
		MIME_TYPE_MAP.put("JPEG", "image/jpeg");
		MIME_TYPE_MAP.put("JPE", "image/jpeg");
		MIME_TYPE_MAP.put("PNG", "image/png");
		MIME_TYPE_MAP.put("GIF", "image/gif");
		MIME_TYPE_MAP.put("BMP", "image/bmp");
		
		
	}
	private ServletKit(){
		super();
	}

	/**
	 * 設定下載時的MineType
	 * @param response
	 * @param fileName
	 */
	public static void setDownloadMineType(HttpServletResponse response, String fileName){
		String mineType = MIME_TYPE_MAP.get(FileKit.getExtension(fileName).toUpperCase());
		if(mineType == null){
			mineType = "application/octet-stream";
		}
		response.setContentType(mineType);
	}

	/**
	 * 設定下載時的檔名，可避免使用者下載後變亂碼
	 * @param request
	 * @param response
	 * @param downloadName
	 */
	public static void setDownloadName(HttpServletRequest request, HttpServletResponse response, String downloadFileName){
		String agent = request.getHeader("USER-AGENT");
		boolean isFF = agent != null && (agent.indexOf("Firefox") >= 0);
		try{
			if(isFF){
				response.addHeader("content-disposition", "filename*=" + java.net.URLEncoder.encode(downloadFileName, "UTF-8"));
			}else{
				response.addHeader("content-disposition", "filename=" + java.net.URLEncoder.encode(downloadFileName, "UTF-8"));
			}
		}catch(Exception ex){
			response.addHeader("content-disposition", "filename=" + downloadFileName);
		}
	}

	/**
	 * 串流複製
	 * @param response
	 * @param file
	 */
	public static void setDownloadCopy(HttpServletResponse response, File file) throws IOException{
		response.setContentLength((int) file.length());
		InputStream is = null;
		OutputStream os = null;
		try{
			is = new FileInputStream(file);
			os = response.getOutputStream();
			StreamKit.copy(is, os);
			os.flush();
		}finally{
			StreamKit.close(is, os);
		}
	}

	/**
	 * 設定不使用Cache
	 * @param response
	 */
	private static void setNoCache(HttpServletResponse response){
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store");
		response.addHeader("Cache-Control", "must-revalidate");
		response.addHeader("Cache-Control", "max-age=0");
	}

	private static boolean setCahce(HttpServletRequest request, HttpServletResponse response, int second, long lastModifiedTimeMrcoTime){
		String rfc1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
		boolean ret = true;
		long now = System.currentTimeMillis();
		long millsSeconds =TimeUnit.SECONDS.toMillis(second);
		if(lastModifiedTimeMrcoTime != -1){
			//因為Client 只使用到秒。寫到毫秒會造成誤差
			long lastModifiedTimeMillisTime = TimeUnit.SECONDS.toSeconds(lastModifiedTimeMrcoTime);
			if(request != null && !StringKit.isEmpty(request.getHeader("If-Modified-Since"))){
				String  ifModifiedSince = request.getHeader("If-Modified-Since");
				try {
					long ifModifiedSinceMillisTime = new SimpleDateFormat(rfc1123, Locale.US).parse(ifModifiedSince).getTime();
					ret = lastModifiedTimeMillisTime !=  ifModifiedSinceMillisTime;
				} catch (Exception e) {}
			}else{
				response.setDateHeader("Last-Modified", lastModifiedTimeMillisTime);
			}
		}
		response.setDateHeader("Date", now);
		response.setHeader("Pragma", "Pragma");
		if(ret){
			response.setDateHeader("Expires", now + millsSeconds);
			response.setHeader("Cache-Control", "public, max-age=" + second);
		}else{
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
		}
		return ret;
	}

	/**
	 * 設定瀏覽器 庫存的時間
	 * @param response
	 * @param second
	 */
	public static void setResponseTimeout(HttpServletResponse response, int second){
		ServletKit.setResponseTimeout(null, response, second, -1);
	}

	/**
	 * 設定瀏覽器 庫存的時間.並設定Last-Modified 標籤
	 * @param response
	 * @param second
	 * @param lastModifiedTimeMrcoTime (ex:file.lastModified())
	 */
	public static void setResponseTimeout(HttpServletResponse response, int second, long lastModifiedTimeMrcoTime){
		ServletKit.setResponseTimeout(null, response, second, lastModifiedTimeMrcoTime);
	}

	/**
	 * 設定瀏覽器 庫存的時間.並將無須更新的檔案設定為304
	 * @param request
	 * @param response
	 * @param second
	 * @param lastModifiedTimeMrcoTime
	 * @return  為設定成304時會回傳false
	 */
	public static boolean setResponseTimeout(HttpServletRequest request, HttpServletResponse response, int second, long lastModifiedTimeMrcoTime){
		if(second == 0){
			ServletKit.setNoCache(response);
			return true;
		}else{
			return ServletKit.setCahce(request, response, second, lastModifiedTimeMrcoTime);
		}
	}

	/**
	 * 
	 * @param request
	 * @param newFile
	 * @return
	 */
	public static String saveOneFile(HttpServletRequest request, File newFile){
		String contentType = request.getContentType();
		InputStream is = null;
		OutputStream os = null;
		String orgFileName = null;
		try{
			is = new DataInputStream(request.getInputStream());
			int formDataLength = request.getContentLength();
			byte dataBytes[] = new byte[formDataLength];
			int byteRead = 0, totalBytesRead = 0;
			while (totalBytesRead < formDataLength) {
				byteRead = is.read(dataBytes, totalBytesRead, formDataLength);
				totalBytesRead += byteRead;
			}
			String file = new String(dataBytes);
			orgFileName = file.substring(file.indexOf("filename=\"") + 10);
			orgFileName = orgFileName.substring(0, orgFileName.indexOf("\n"));
			orgFileName = orgFileName.substring(orgFileName.lastIndexOf("\\") + 1,orgFileName.indexOf("\""));

			int lastIndex = contentType.lastIndexOf("=");
			String boundary = contentType.substring(lastIndex + 1,contentType.length());
			int pos;
			pos = file.indexOf("filename=\"");
			pos = file.indexOf("\n", pos) + 1;
			pos = file.indexOf("\n", pos) + 1;
			pos = file.indexOf("\n", pos) + 1;

			int boundaryLocation = file.indexOf(boundary, pos) - 4;
			int startPos = ((file.substring(0, pos)).getBytes()).length;
			int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
			os = new FileOutputStream(newFile);
			os.write(dataBytes, startPos, (endPos - startPos));
			os.flush();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			StreamKit.close(is, os);
		}
		return orgFileName;
	}

	/**
	 * 取得真實ip
	 * @param request
	 * @return
	 */
	public static String getIP(HttpServletRequest request){
		String ip = ServletKit.findIpInString(request.getHeader("x-forwarded-for")); 
		if(null == ip){
			ip = ServletKit.findIpInString(request.getHeader("Proxy-Client-IP"));
		}
		if(null == ip){
			ip = ServletKit.findIpInString(request.getHeader("WL-Proxy-Client-IP"));
		}
		if(null == ip){
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 由x-forwarded-for 提供的字串格式取得ip
	 * ex unknown, 10.10.1.10, 10.10.1.1, 192.168.1.1, 100.100.100.100
	 * @param str
	 * @return 當沒找到都回傳null
	 */
	private static String findIpInString(String str){
		if(null == str || 0 == str.length()){
			return null;
		}
		str = str.toLowerCase().trim();
		if('u' != str.charAt(0)){
			int start = 0;
			for(int i = 7 ; i < str.length() ; i++){
				if(',' == str.charAt(i)){
					String ip = str.substring(start, i).trim();
					if(!"unknown".equals(ip)){
						return ip;
					}
				}
			}
		}
		return null;
	}

	public static void setResponseWithRequest(WGet wget, HttpServletRequest request) throws UnsupportedEncodingException{
		if(request != null){
			Enumeration<String> enumeration = request.getParameterNames();
			while(enumeration.hasMoreElements()){
				String key = enumeration.nextElement();
				String[] values = request.getParameterValues(key);
				for(String value : values){
						wget.addParam(key, value);
				}
			}		
		}
	}

	final static Set<String> DONT_ADD_PARAM_IN_HYCMS;
	static{
		DONT_ADD_PARAM_IN_HYCMS = new HashSet<String>();
		DONT_ADD_PARAM_IN_HYCMS.add("showXml");
		DONT_ADD_PARAM_IN_HYCMS.add("btMemberId");
	}
	/**
	 * 將request 的參數轉丟 wget
	 * @param wget
	 * @param request
	 */
	public static void setResponseWithRequest4HyCMS(WGet wget, HttpServletRequest request) throws UnsupportedEncodingException{
		if(request != null){
			Enumeration<String> enumeration = request.getParameterNames();
			while(enumeration.hasMoreElements()){
				String key = enumeration.nextElement();
				if(!DONT_ADD_PARAM_IN_HYCMS.contains(key)){
					String[] values = request.getParameterValues(key);
					for(String value : values){
							wget.addParam(key, value);
					}					
				}
			}			
		}
	}

	public static String getReqStr(HttpServletRequest request, String key, String defVal){
		String val = request.getParameter(key);
		return StringKit.isEmpty(val) ? defVal : val;
	}

	public static Integer getReqInt(HttpServletRequest request, String key, Integer defVal){
		try{
			return Integer.parseInt(request.getParameter(key), 10 );
		}catch(Exception ex){
			return defVal;
		}
	}

	/**
	 * 取得request的array
	 * @param request
	 * @param key
	 * @param defVal
	 * @return
	 */
	public static String[] getArray(HttpServletRequest request, String key, String[] defVal){
		String[] ret = request.getParameterValues(key);
		System.out.println(key + ":" + (ret == null ? defVal.length : ret.length));
		return ret == null ? defVal : ret;
	}
}
