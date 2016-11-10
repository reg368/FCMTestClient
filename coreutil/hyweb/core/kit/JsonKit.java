package hyweb.core.kit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 支援Json格式的字串
 * @author A0074
 * @version 1.0.121227
 * @since xBox 1.0
 */
public final class JsonKit {
	final protected static int JAVASCRIPT_INT = 0;
	final protected static int JAVASCRIPT_STRING = 1;
	final protected static int JAVASCRIPT_ARRAY = 2;
	final protected static int JAVASCRIPT_OBJECT = 3;

	private JsonKit(){
		super();
	}

	/**
	 * 透過反射將值塞入給定的型別.可使用掛載cglib的加強版
	 * @param key
	 * @param classFullName 完整的class 名稱 ex java.util.Map
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws NoSuchMethodException 
	 * @throws ClassNotFoundException 
	 */
	public static Object getObject(String val, String classFullName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException{
		Object ret = ClassKit.createNewInstance(classFullName, null, null);
		if(JsonKit.chechType(val) == JsonKit.JAVASCRIPT_OBJECT){
			Class<?> c = ret.getClass();
			String[] info = val.substring(1, val.length() -1).split(",");
			for(int i = 0; info != null && i < info.length ; i++){
				int index = info[i].indexOf(':');
				String k = JsonKit.doString(info[i].substring(0, index));
				String v = JsonKit.doString(info[i].substring(index + 1, info[i].length()));
				Field field = c.getDeclaredField(k);
				field.setAccessible(true);
				field.set(ret, v);
			}
		}
		return ret;
	}

	/**
	 * 將字串依照json的規則轉換為物件
	 * @param val
	 * @return
	 */
	public static Object getJson(String val){
		switch(JsonKit.chechType(val)){
		case JsonKit.JAVASCRIPT_INT:
			return JsonKit.doInt(val);
		case JsonKit.JAVASCRIPT_STRING:
			return JsonKit.doString(val);
		case JsonKit.JAVASCRIPT_ARRAY:
			return JsonKit.doArray(val);
		case JsonKit.JAVASCRIPT_OBJECT:
			return JsonKit.doObject(val);
		}
		return StringKit.NULL_STRING;
	}

	/**
	 * 以物件的方式處理.格式錯誤時會回傳null
	 * @param val
	 * @return
	 */
	private static Map<String, Object> doObject(String val){
		if(val.startsWith("{")){
			val = val.substring(1, val.length() -1);
		}
		Map<String, Object> ret = new Hashtable<String, Object>();
		String[] info = val.split(",");
		for(int i = 0 ; info != null && i < info.length ; i++){
			int index = info[i].indexOf(':');
			if(index < 0){
				return null;
			}
			String k = JsonKit.doString(info[i].substring(0, index));
			Object v = JsonKit.getJson(info[i].substring(index + 1, info[i].length()));
			ret.put(k, v);
		}
		return ret;
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	private static Object doArray(String val){
		if(val.length() >= 1 && (val.charAt(1) == '\'' || val.charAt(1) == '\"')){
			return JsonKit.doStrArray(val);
		}else{
			return JsonKit.doIntArray(val);
		}
	}

	/**
	 * 以矩陣方式處理字串陣列.未判斷跳脫字元
	 * @param val
	 * @return
	 */
	private static String[] doStrArray(String val){
		if(val.startsWith("[")){
			val = val.substring(1, val.length() -1);
		}
		char[] valchar = val.toCharArray();
		List<Integer> split = new ArrayList<Integer>();
		boolean isInstring = false;
		for(int i = 0 ; i < valchar.length ; i++){
			if(valchar[i] == ','){
				if(!isInstring){
					split.add(i);
				}
			}
			if(valchar[i] == '\'' || valchar[i] == '\"'){
				isInstring = !isInstring;
			}
		}
		if(split.size() == 0 || split.get(split.size() -1) != valchar.length - 1){
			split.add(valchar.length);
		}
		
		String[] ret = new String[split.size()];
		int start = 0, end = 0;
		for(int i = 0 ; i < split.size() ; i++){
			end = split.get(i);
			String value = val.substring(start, end);
			ret[i] = JsonKit.doString(value.trim());
			start = end + 1;
		}
		return ret;
	}

	/**
	 * 以矩陣方式處理數字陣列.未判斷跳脫字元
	 * @param val
	 * @return
	 */
	private static int[] doIntArray(String val){
		if(val.startsWith("[")){
			val = val.substring(1, val.length() -1);
		}
		String[] str = val.split(",");
		int[] ret = new int[str.length]; 
		for(int i = 0 ; val != null && i < str.length ; i++){
			ret[i] = JsonKit.doInt(str[i].trim());
		}
		return ret;
	}

	/**
	 * 以字串方式處理
	 * @param val
	 * @return
	 */
	private static String doString(String val){
		if(val.startsWith("'")){
			val = val.substring(1, val.length() -1);
		}
		return val;
	}

	/**
	 * 以數字方式處理
	 * @param val
	 * @return
	 */
	private static int doInt(String val){
		try{
			return Integer.parseInt(val, 10);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 檢查資料類型
	 * @param val
	 * @return
	 */
	private static int chechType(String val){
		if(val.length() < 2){
			return JsonKit.JAVASCRIPT_INT;
		}
		char first = val.charAt(0);
		char last = val.charAt(val.length() - 1);
		if('\'' == first && '\'' == last){
			return JsonKit.JAVASCRIPT_STRING;
		}else if('"' == first && '"' == last){
			return JsonKit.JAVASCRIPT_STRING;
		}else if('[' == first && ']' == last){
			return JsonKit.JAVASCRIPT_ARRAY;
		}else if('{' == first && '}' == last){
			return JsonKit.JAVASCRIPT_OBJECT;
		}else{
			return JsonKit.JAVASCRIPT_INT;
		}
	}
}
