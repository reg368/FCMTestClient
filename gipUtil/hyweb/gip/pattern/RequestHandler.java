package hyweb.gip.pattern;

import hyweb.core.kit.ArrayKit;
import hyweb.core.kit.NumberKit;
import hyweb.core.kit.StringKit;
import hyweb.gip.exception.LoginTimeoutException;
import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.util.cacheable.BeanHelper;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

/**
 * Request 值 管理器
 * @author A0074
 *
 */
public class RequestHandler {
	/*是否顯示XML*/
	public static final String PARAM_SHOW_XML = "showXml";
	/*外部帶入的XSL*/
	public static final String PARAM_XSL = "xsl";
	/*第幾頁*/
	public static final String PARAM_PAGE_NO = "page";
	/*排序*/
	public static final String PARAM_PAGE_ORDERBY = "orderBy";

	protected Map<String, String[]> requestParamMap;
	protected HttpSession session;

	/**
	 * 建構子
	 * @param requestParamMap
	 * @param session
	 */
	public RequestHandler(Map<String, String[]> requestParamMap, HttpSession session){
		super();
		this.requestParamMap = requestParamMap;
		this.session = session;
	}

	/**
	 * 取得requestParam
	 * @return
	 */
	public Map<String, String[]> getParamMap(){
		return this.requestParamMap;
	}

	/**
	 * 取得session
	 * @return
	 */
	public HttpSession getSession(){
		return this.session;
	}

	/**
	 * 取得request參數的所有key
	 * @return
	 */
	public Set<String> getRequestKeySet(){
		return this.requestParamMap.keySet();
	} 
	
	/**
	 * 取得值
	 * @param key 
	 * @param defVal 預設值
	 * @return
	 */
	public String getRequestValue(String key, String defVal){
		String[] value = this.requestParamMap.get(key);
		return (value == null || value.length == 0) ? defVal : value[0];
	}

	/**
	 * 取得in
	 * @param key
	 * @param defVal
	 * @return
	 */
	public String getInRequestValue(String key, String defVal){
		String[] value = this.requestParamMap.get(key);
		if(value == null || value.length == 0){
			if(StringKit.isEmpty(defVal)){
				return defVal;
			}else{
				return "'" + defVal + "'";
			}
		}else{
			return "'" + ArrayKit.join(value, "','") + "'";
		}
	}

	public String[] getBetweenRequestValue(String key){
		return this.requestParamMap.get(key);
	}

	/**
	 * 以Array回傳 request中某key的值
	 * @param key
	 * @return
	 */
	public String[] getPequestValues(String key){
		return this.requestParamMap.get(key);
	}

	/**
	 * 取得session中的值
	 * @param key
	 * @param defVal 預設值
	 * @return
	 */
	public Object getSessionValue(String key, String defVal) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException{
		String[] keyArray = key.split("\\.");
		if(keyArray.length == 1){
			return this.session.getAttribute(key);
		}else{
			return BeanHelper.getAttr(this.session.getAttribute(keyArray[0]), keyArray[1]);
		}
	}

	/**
	 * XSL 檔名
	 * @param xsl
	 * @return
	 */
	public String getXSL(String xsl){
		String[] newXsl = this.requestParamMap.get(RequestHandler.PARAM_XSL);
		return newXsl == null ? xsl : newXsl.length == 1 ? newXsl[0] : xsl; 
	}

	/**
	 * 是否回傳XML
	 * @return
	 */
	public boolean showXML(){
		return this.requestParamMap.containsKey(RequestHandler.PARAM_SHOW_XML);
	}

	/**
	 * 取得頁碼
	 * @return
	 */
	public int getPageNo(){
		String[] pn = this.requestParamMap.get(RequestHandler.PARAM_PAGE_NO);
		return pn == null ? 1 : NumberKit.toInt(pn[0], 1);
	}

	/**
	 * 取得頁碼
	 * @return
	 */
	public String getOrderBy(){
		String[] ob = this.requestParamMap.get(RequestHandler.PARAM_PAGE_ORDERBY);
		return ob == null ? ""  : ob[0];
	}

	/**
	 * 取得使用者資訊
	 * @return
	 */
	public InfoUser getInfoUserFromSession(){
		if(this.session == null){
			throw new LoginTimeoutException("登入失效，請重新登入");
		}
		Object infoUser = session.getAttribute("InfoUser");
		if(infoUser == null){
			throw new LoginTimeoutException("請重新登入");
		}
		return (InfoUser)infoUser;
	}
}
