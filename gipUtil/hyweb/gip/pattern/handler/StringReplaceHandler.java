package hyweb.gip.pattern.handler;

import hyweb.core.kit.CollectionsKit;
import hyweb.gip.pattern.RequestHandler;
import hyweb.util.cacheable.BeanHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 負責處理String變數轉換，not thread safe
 * 可自行註冊關鍵字字串${key}、清單${name.}、request、session
 * @author A0074
 *
 */
public class StringReplaceHandler{
	/**
	 * 取得屬性
	 * @param obj Map<String, Object> 或是 pojo
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getAttr(Object obj, String key) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException{
		if(obj instanceof Map){
			return ((Map<String, Object>)obj).get(key);
		}else{
			return BeanHelper.getAttr(obj, key);
		}
	}
	Map<String, Object> registeredStringMap;
	Map<String, List<Object>> registeredListMap;
	/*當不為NULL時，將提供查詢request與session的功能*/
	RequestHandler requestHandler;
	/*主資料的名稱，用來搭配資料庫查詢結果集*/
	String mainListName = null;
	/*用於Iterator的定位*/
	int iteratorIdx;
	/*用於Iterator的快取參照，實體還是存於registeredListMap*/
	List<Object> mainList;
	/*用於Iterator的快取實體，可降低每次皆需分析的成本*/
	List<UrlReplaceInfoBean> iteratorInfoList;
	public StringReplaceHandler(){
		this.registeredStringMap = CollectionsKit.newMap();
		this.registeredListMap = CollectionsKit.newMap();
		this.resetIterator();
	}

	/**
	 * 註冊一個替代，之後可以使用${key} 進行替換
	 * @param key, 請勿使用request與session與this
	 * @param value
	 * @return
	 */
	public StringReplaceHandler registered(String key, String value){
		if("request".equals(key) || "session".equals(key)){
			throw new RuntimeException("Sorry, " + key + " is the keyword of the replace class");
		}
		this.registeredStringMap.put(key, value);
		return this;
	}

	public StringReplaceHandler registered(Map<? extends Object, ? extends Object> map){
		for(Object key : map.keySet()){
			this.registeredStringMap.put(key.toString(), map.get(key));
		}
		return this;
	} 

	/**
	 * 註冊一個查詢結果集，之後可以使用${name.key} 進行替換 key為POJO的欄位名稱，有分大小寫
	 * @param pojoList 建議每個List 大小都一樣，以避免 OutOfIndexException
	 * @param name 註冊的名稱,請勿使用request與session與this
	 * @param isMainList 是否為主要項目，當需要輪巡時將以最後一個設為true者的 list size 為依據
	 * @return
	 */
	public StringReplaceHandler registered(List<Object> pojoList, String name, boolean isMainList){
		if("request".equals(name) || "session".equals(name)){
			throw new RuntimeException("Sorry, " + name + " is the keyword of the replace class");
		}
		this.registeredListMap.put(name, pojoList);
		if(isMainList){
			this.mainListName = name;
		}
		return this;
	}

	/**
	 * 註冊程式處理功能,對應時會將isMainList 的 pojo 帶入其get方法進行數值轉換
	 * @param code
	 * @param key
	 * @return
	 */
	public StringReplaceHandler registered(String key, StringReplaceCode code){
		if("request".equals(key) || "session".equals(key)){
			throw new RuntimeException("Sorry, " + key + " is the keyword of the replace class");
		}
		this.registeredStringMap.put(key, code);
		return this;
	}

	/**
	 * 註冊 ${request.} ${session.}
	 * @param requestHandler
	 * @return
	 */
	public StringReplaceHandler registered(RequestHandler requestHandler){
		this.requestHandler = requestHandler;
		return this;
	}

	/**
	 * 依照isMainList設定的資料數量，回傳同樣數量的以該值取代後字串
	 * @param src
	 * @return 
	 */
	public String[] replaceAsMainList(String src) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException{
		List<UrlReplaceInfoBean> analyzeInfoList = this.analyze(src);
		String[] ret = this.mainListName == null ? new String[1] : new String[this.registeredListMap.get(this.mainListName).size()];
		if(ret.length == 0){
			ret = new String[1];
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < ret.length ; i++){
			int preIdx = 0;
			for(UrlReplaceInfoBean bean : analyzeInfoList){
				sb.append(src.substring(preIdx, bean.startIdx));
				if(bean.set == null){
					Object obj = this.registeredStringMap.get(bean.key);
					if(obj == null){
						sb.append("");
					}else if(obj.getClass() == String.class){
						sb.append((String)obj);
					}else{
						List<Object> list = this.registeredListMap.get(this.mainListName);
						if(!list.isEmpty()){
							Object pojo = list.get(i);
							if(pojo == null){
								sb.append("");
							}else{
								sb.append(((StringReplaceCode)obj).get(pojo));
							}							
						}
					}
				}else if("request".equals(bean.set)){
					if(this.requestHandler == null){
						sb.append("");
					}else{
						sb.append(this.requestHandler.getRequestValue(bean.key, ""));
					}
				}else if("session".equals(bean.set)){
					if(this.requestHandler == null){
						sb.append("");
					}else{
						sb.append(this.requestHandler.getSessionValue(bean.key, ""));
					}
				}else{
					Object obj = this.registeredStringMap.get(bean.key);
					if (obj == null) {
						obj = this.registeredStringMap.get(bean.set+"."+bean.key);
					}
					if(obj == null){
						sb.append("");
					}else if(obj.getClass() == String.class){
						sb.append((String)obj);
					}else{
						Object pojo = this.registeredListMap.get(this.mainListName).get(i);
						if(pojo == null){
							sb.append("");
						}else{
							sb.append(((StringReplaceCode)obj).get(pojo));
						}
					}
				}
				preIdx = bean.endIdx + 1;
			}
			sb.append(src.substring(preIdx));
			ret[i] = sb.toString();
			sb.delete(0, sb.length());
		}
		return ret;
	}

	/**
	 * 用類似Iterator 方式輪巡所有透過registered註冊的List, size以 isMainList為主
	 * @return
	 */
	public boolean hasNext() {
		if(this.mainList == null){
			this.mainList = this.registeredListMap.get(this.mainListName);
		}
		return this.mainList == null ? false : (++this.iteratorIdx < this.mainList.size());
	}

	/**
	 * 取代
	 * @param srcStr 要取代字串的目標字串
	 * @param isChangeSrcStr 如果srcStr不會變請填false,以省略分析時間
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String next(String srcStr, boolean isChangeSrcStr) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException {
		if(isChangeSrcStr){
			this.iteratorInfoList = this.analyze(srcStr);
		}else{
			if(this.iteratorInfoList == null){
				this.iteratorInfoList = this.analyze(srcStr);
			}
		}
		if(this.mainList == null){
			return  null;
		}else{
			int preIdx = 0;
			StringBuilder sb = new StringBuilder(srcStr.length() * 2);
			for(UrlReplaceInfoBean bean : this.iteratorInfoList){
				sb.append(srcStr.substring(preIdx, bean.startIdx));
				if(bean.set == null){
					Object obj = this.registeredStringMap.get(bean.key);
					if(obj == null){
						sb.append("");
					}else if(obj.getClass() == String.class){
						sb.append((String)obj);
					}else{
						Object pojo = this.registeredListMap.get(this.mainListName).get(this.iteratorIdx);
						if(pojo == null){
							sb.append("");
						}else{
							sb.append(((StringReplaceCode)obj).get(pojo));
						}
					}
				}else if("request".equals(bean.set)){
					if(this.requestHandler == null){
						sb.append("");
					}else{
						sb.append(this.requestHandler.getRequestValue(bean.key, ""));
					}
				}else if("session".equals(bean.set)){
					if(this.requestHandler == null){
						sb.append("");
					}else{
						sb.append(this.requestHandler.getSessionValue(bean.key, ""));
					}
				}else{
					Object obj = this.registeredListMap.get(bean.set).get(this.iteratorIdx);
					if(obj instanceof Map){
						sb.append(((Map<String, Object>)obj).get(bean.key));
					}else{
						sb.append(BeanHelper.getAttr(obj, bean.key));
					}
				}
				preIdx = bean.endIdx + 1;
			}
			sb.append(srcStr.substring(preIdx));
			return sb.toString();
		}
	}

	/**
	 * 重設Iterator的指標
	 */
	public void resetIterator(){
		this.iteratorIdx = -1;
		this.mainList = null;
		this.iteratorInfoList = null;
	}

	/**
	 * 解析主程式
	 * @param src 要轉換的字串
	 * @return
	 */
	private List<UrlReplaceInfoBean> analyze(String src){
		char[] array = src.toCharArray();
		boolean notStart = true;
		int preArrayLen = array.length -1;
		int startIdx = 0;
		List<UrlReplaceInfoBean> ret = new ArrayList<UrlReplaceInfoBean>();
		StringBuilder sb = new StringBuilder(); 
		for(int i = 0 ; i < array.length ; i++){
			if(notStart){
				if(array[i] == '$' && i != preArrayLen){
					if(array[++i] == '{'){
						startIdx = i -1;
						sb.delete(0, sb.length());
						notStart = false;
					}
				}
			}else{
				if(array[i] == '}'){
					UrlReplaceInfoBean bean = null;
					String fullName = sb.toString();
					int splitIdx = fullName.indexOf('.');
					if(splitIdx > 0){
						bean = new UrlReplaceInfoBean(startIdx, i, fullName.substring(0, splitIdx),  fullName.substring(splitIdx + 1));
					}else{
						bean = new UrlReplaceInfoBean(startIdx, i, null, fullName);
					}
					ret.add(bean);
					notStart = true;
				}else{
					sb.append(array[i]);
				}
			}
		}
		return ret;
	}
}

class UrlReplaceInfoBean{
	int startIdx;
	int endIdx;
	String set;
	String key;
	public UrlReplaceInfoBean(int startIdx, int endIdx, String set, String key){
		this.startIdx = startIdx;
		this.endIdx = endIdx;
		this.set = set;
		this.key = key;
	}
}