package hyweb.core.kit;

import java.lang.reflect.Field;
import java.util.Arrays;
/**
 * 與字串相關的基本工具
 * @author A0074
 * @version 1.0.090505
 * @since xBox 1.0
 */
public final class StringKit{
	public final static String NULL_STRING = "";
	public final static int NULL_INTEGER = 0;
	
	private StringKit(){
		super();
	}

	/**
	 * is src is empty
	 * @param src 來源
	 * @return true when src is null or src is empty
	 */
	public static boolean isEmpty(String src){
		return src == null || src.length() == 0;
	}
	
	/**
	 * is src is blank
	 * @param src 來源
	 * @return true when src is null or src is blank
	 */
	public static boolean isBlank(CharSequence src) {
		int strLen;
		if (src == null || (strLen = src.length()) == 0) {
			return true;
		}
		
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(src.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 去除空字串
	 * @param src 來源
	 * @return "" if src is null
	 */
	public static String notNull(String src){
		return src == null ? "" : src;
	}
	
	/**
	 * 去除空字串
	 * @param src 來源
	 * @param defaultStr 當字串是空值時，預設的文字
	 * @return "" if src is null
	 */
	public static String notNull(String src, String defaultStr){
		return StringKit.isEmpty(src) ? defaultStr : src;
	}

	/**
	 * 取得長度(null self)
	 * @param src 來源
	 * @return
	 */
	public static int getLen(String src){
		return src == null ? StringKit.NULL_INTEGER : src.length();
	}

	/**
	 * 取得在某兩字串中間的字
	 * @param src 來源
	 * @param sIndex  start index
	 * @param eIndex  end index
	 * @return substing 
	 */
	public static String subBetween(String src, int sIndex, int eIndex){
		if(src == null || sIndex > eIndex || sIndex < 0){
			return StringKit.NULL_STRING;
		}else{
			return src.substring(sIndex, eIndex > src.length() ? src.length() : eIndex);
		}
	}

	/**
	 *
	 * <pre>
	 * StringKit.subBetween(null,*,*,*) = ""
	 * StringKit.subBetween("an apple a day",null,"apple",false) -> "an "
	 * StringKit.subBetween("an apple a day","apple",null,false) -> " a day"
	 * StringKit.subBetween("an apple a day","an","day",false) -> " apple a "
	 * StringKit.subBetween("an apple a day","an","day",true) -> "an apple a day"
	 * StringKit.subBetween("an apple a day",null,null,true) -> "an apple a day"
	 *</pre>
	 * @param src 來源
	 * @param sStr start index
	 * @param eStr  end index
	 * @param include 是否包含夾擊字串
	 * @return
	 */
	public static String subBetween(String src, String sStr, String eStr, boolean include){
		if(src == null){
			return StringKit.NULL_STRING;
		}
		int sIndex = sStr == null ? 0 : src.indexOf(sStr);
		int eIndex = eStr == null ? src.length() : src.indexOf(eStr);
		if(sIndex == -1 || eIndex == -1){
			return StringKit.NULL_STRING;
		}else{
			if(include){
				eIndex += StringKit.getLen(eStr);
			}else{
				sIndex += StringKit.getLen(sStr);
			}
			return src.substring(sIndex,eIndex);
		}
	}

	/**
	 * 濾前後空白(null self)
	 *<pre>
	 * StringKit.trim(null,true) = ""
	 * StringKit.trim("　 a 　   b    　", false)+ "c" -> "　 a 　   b    　c"
	 * StringKit.trim("　 a 　   b    　", true)+ "c" -> "a 　   bc"
	 *</pre>
	 * @param src 某字串
	 * @param isStrict 是否包含全形
	 */
	public static String trim(String src, boolean isStrict){
		if(src == null){
			return StringKit.NULL_STRING;
		}
		if(isStrict){
			int startIndex = 0;
			int endIndex = src.length() -1;
			char tmpChar;
			for(int i = 0 ; i < src.length() ; i++){
				tmpChar = src.charAt(i);
				if(tmpChar == ' ' || tmpChar == '　' || tmpChar == '\t'){
					if(i == startIndex){
						startIndex++;
					}else{
						break;
					}
				}
			}
			for(int i = src.length() -1 ; i >= 0 ; i--){
				tmpChar = src.charAt(i);
				if(tmpChar == ' ' || tmpChar == '　' || tmpChar == '\t'){
					if(i == endIndex){
						endIndex--;
					}else{
						break;
					}
				}
			}
			return src.substring(startIndex,endIndex + 1);
		}else{
			return src.trim();
		}
	}

	/**
	 * 取得某字在某字串中出現的次數
	 * <pre>
	 * StringKit.getWordCount("aaaaaaabbbbbaardgsa", "aaa",true) -> 5
	 * StringKit.getWordCount("aaaaaaabbbbbaardgsa", "aaa",false) -> 2
	 * </pre>
	 * @param src 某字串
	 * @param word 要比對的字
	 * @param isStrict 是否逐字比對
	 * @return count
	 */
	public static int getWordCount(String src, String word, boolean isStrict){
		if(src == null || word == null){
			return StringKit.NULL_INTEGER;
		}
		int count = 0;
		int preIndex = 0,nowIndex = 0;
		int length = isStrict ? 1 : word.length();
		while(true){
			nowIndex = src.indexOf(word, preIndex);
			if(nowIndex == -1){
				break;
			}else{
				preIndex = nowIndex + length;
				count ++;
			}
		}
		return count;
	}
	
	/**
	 * 數字轉字串時補齊
	 *<pre>
	 * StringKit.leftPad(12345678,10) -> 0012345678
	 * StringKit.leftPad(12345678,1) -> 12345678
	 *</pre>
	 * @param src 某數字
	 * @param digit 共幾位
	 * @return
	 */
	public static String leftPad(int src ,int digit){
		return StringKit.leftPad(String.valueOf(src), digit, '0'); 
	}

	/**
	 * 字串補齊
	 *<pre>
	 * StringKit.leftPad("12345678",10,'0') -> 0012345678
	 *</pre>
	 * @param src 某數字
	 * @param digit 共幾位
	 * @return
	 */
	public static String leftPad(String src ,int digit ,char fillChar){
		if(digit <= 0 || src == null){
			return NULL_STRING;
		}else{
			if(src.length() >= digit){
				return src;
			}else{
				char[] help = new char[digit - src.length()];
				Arrays.fill(help,fillChar);
				return String.valueOf(help) + src;
			}
		}
	}
	
	/**
	 * 取得重複相同的字串
	 *<pre>
	 * StringKit.string('a',5) -> aaaaa
	 *</pre>
	 * @param repeatChar 某字元
	 * @param times 次數
	 * @return
	 */
	public static String string(char repeatChar ,int times){
		if(times <= 0){
			return StringKit.NULL_STRING;
		}
		char[] re = new char[times];
		Arrays.fill(re, repeatChar);
		return String.valueOf(re);
	}

	/**
	 * 去除各種換行字元
	 * @param src 輸入
	 * @return
	 */
	public static String removeLineSeparator(String src){
		return src == null ? StringKit.NULL_STRING : src.replaceAll("[\\r\\n]", "");
	}

	/**
	 * 開頭大寫
	 * @param src 輸入
	 * @return
	 */
	public static String capitalize(String src){
		if(src == null || src.length() == 0){
			return src;
		}
		char[] ch = src.toCharArray();
		ch[0] = Character.toUpperCase(ch[0]);
		return new String(ch);
	}

	/**
	 * 不使用正規表示法的取代
	 * @param src 來源
	 * @param repl 要取代的
	 * @param with 被取代的
	 * @param max 最大取代數量,-1表示全部取代
	 * @return
	 */
	public static String replace(String src, String pattern, String with, int max) {
		if(src == null || isEmpty(pattern) || with == null || max == 0) {
			return src;
		}
		StringBuffer buf = new StringBuffer(src.length());
		int start = 0, end = 0;
		while ((end = src.indexOf(pattern, start)) != -1) {
			buf.append(src.substring(start, end)).append(with);
			start = end + pattern.length();
			if (--max == 0) {
				break;
			}
		}
		buf.append(src.substring(start));
		return buf.toString();
	}

	public static String toString(Object bean, String fieldSpilt) throws IllegalArgumentException, IllegalAccessException{
		StringBuilder sb = new StringBuilder(); 
		Field[] fields = bean.getClass().getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			sb.append(field.getName()).append(":").append(field.get(bean)).append(fieldSpilt);
		}
		return sb.toString();
	}
	
	public static String objectToString(Object obj) {
		return obj == null ? "" : obj.toString();
	}
	
	public static String arrayOneToString(Object[] obj) {
		if (obj != null && obj.length > 0) {
			return obj[0].toString();
		} else {
			return "";
		}
	}
}
