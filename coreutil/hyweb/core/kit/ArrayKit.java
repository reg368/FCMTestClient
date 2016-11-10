package hyweb.core.kit;
/**
 * 有關各種矩陣的基本操作，效能是程式撰寫的唯一考量，禁止繼承
 * @author A0074
 * @version 1.0.090505
 * @since xBox 1.0
 */
public final class ArrayKit {
	private ArrayKit(){
		super();
	}

	/**
	 * String[]合併為字串(null self)
	 * <pre>
	 * ArrayKit.join(new String[]{"a","b","c"},0,3,"000") = a000b000c
	 *</pre>
	 * @param src 輸入矩陣
	 * @param start 起始位置
	 * @param total 總共幾筆
	 * @param between 矩陣間的字
	 * @return if the param - start is smaller than 0 and src is null ,it'll be return StringKit.NULL_STRING
	 */
	public static String join(String[] src, int start, int total, String between){
		if(src == null || start < 0){
			return StringKit.NULL_STRING;
		}else{
			total = total > src.length ? src.length : total; 
			StringBuilder ret = new StringBuilder((total - start + 1) << 3);
			total += start;
			for(int i = start ; i < total ; i++){
				ret.append(between).append(src[i]);
			}
			return ret.substring(between.length());
		}
	}

	public static String join(String[] src, String between){
		if(src == null){
			return StringKit.NULL_STRING;
		}else{
			return ArrayKit.join(src, 0, src.length, between);
		}
	}

	/**
	 * byte[]合併為字串(null self)(unsign byte)
	 * @param src 輸入矩陣
	 * @param start 起始位置
	 * @param total 總共幾筆
	 * @param between 矩陣間的字
	 * @return if the param - start is smaller than 0 and src is null ,it'll  return StringKit.NULL_STRING
	 */
	public static String join(byte[] src, int start, int total, String between){
		if(src == null || start < 0){
			return StringKit.NULL_STRING;
		}else{
			total = total > src.length ? src.length : total;
			StringBuilder ret = new StringBuilder((total - start + 1) << 2);
			total += start;
			for(int i = start ; i < total ; i++){
				ret.append(between).append(ByteKit.toUnsignedShort(src[i]));
			}
			return ret.substring(between.length());
		}
	}
}
