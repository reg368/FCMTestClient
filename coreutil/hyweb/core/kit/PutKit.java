package hyweb.core.kit;

import hyweb.core.util.PutKitException;

import java.util.Map;

final public class PutKit {
	/**
	 * 取得map中某key的值，如果為null則丟出errorMsg,當errorMsg為null時則回傳null
	 * @param key
	 * @param map
	 * @param errorMsg
	 * @return
	 */
	public static <T> T putExistValueFromMap(String key, Map<String, T> map, String errorMsg) throws PutKitException{
		return putValueFromMap(key, map, errorMsg, null);
	}

	/**
	 * 取得map中某key的值，如果為null則丟出errorMsg,當errorMsg為null時則回傳defaultValue
	 * @param key
	 * @param map
	 * @param errorMsg
	 * @return
	 */
	public static <T> T putValueFromMap(String key, Map<String, T> map, String errorMsg, T defaultValue) throws PutKitException{
		if(key == null){
			throw new RuntimeException("key cant be null");
		}
		if(map == null){
			if(errorMsg == null){
				return defaultValue;
			}else{
				throw new PutKitException(errorMsg);
			}
		}else{
			T value = map.get(key);
			if(value == null){
				throw new PutKitException(errorMsg);
			}else{
				return value;
			}
		}
	}
}
