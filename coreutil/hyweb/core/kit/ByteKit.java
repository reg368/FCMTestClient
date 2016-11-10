package hyweb.core.kit;
/**
 * 與byte相關的基本工具，禁止繼承
 * @author A0074
 * @version 1.0.090515
 * @since xBox 1.0
 */
public final class ByteKit {
	public final static short UNSIGNED_MAX_VALUE = (Byte.MAX_VALUE << 1) + 1;
	private final static long SIZE_TB = 1099511627776l;
	private final static long SIZE_GB = 1073741824l;
	private final static long SIZE_MB = 1048576l;
	private final static long SIZE_KB = 1024l;
	
	private ByteKit(){
		super();
	}

	/**
	 * 有號數轉無號數
     * <pre>
     * ByteKit.toUnsignedShort((byte) -100) = 156
     * </pre>
	 * @param src 要轉為無號數的byte
	 * @return 該byte的無號 short 值
     */
	public static short toUnsignedShort(byte src){
		return (short)(src >= 0 ? src : (UNSIGNED_MAX_VALUE + 1 + src));
	}

	/**
	 * 有號數轉無號數
     * <pre>
     * ByteKit.toUnsignedInt((byte) -100) = 156
     *</pre>
	 * @param src 要轉為無號數的byte
	 * @return 該byte的無號 int 值
     */
	public static int toUnsignedInt(byte src){
		return (int)(src >= 0 ? src : (UNSIGNED_MAX_VALUE + 1 + src));
	}

	/**
	 * byte[] 轉 int，最多4byte(不額外阻擋)
     * <pre>
     * ByteKit.toDecimalInteger(String.valueOf("王").getBytes("big5")) = 42237
     * </pre>
	 * @param input 要轉整數的byte[]
     */
	public static int toDecimalInteger(byte[] input)throws Exception{
		int len = input.length;
		int re = 0;
		len = len -1 ;
		for(int i = 0 ; i < input.length ; i++){
			re += ByteKit.toUnsignedInt(input[i]) << (len * 8);
			len --;
		}
		return re;
	}

	/**
	 * 將bytes 轉換為常用的單位 
	 * <pre>
	 * ByteKit.byteFromat(10000,3) = "9.765 KB"
	 * ByteKit.byteFromat(Long.MAX_VALUE,3) = "8388608.0 TB"
	 * </pre>
	 * @param bytes byte數，不是bit
	 * @param decimalDigits 小數點後幾位，超過6就當6。小於0就當0。
	 * @return
	 */
	public static String byteFromat(long bytes, int decimalDigits){
		StringBuffer sb = new StringBuffer();
		if(bytes >= ByteKit.SIZE_TB){
			NumberKit.toDoubleStr(((bytes + 0.0) / ByteKit.SIZE_TB),decimalDigits,sb).append(" TB");
		}else if(bytes >= ByteKit.SIZE_GB){
			NumberKit.toDoubleStr(((bytes + 0.0) / ByteKit.SIZE_GB),decimalDigits,sb).append(" GB");
		}else if(bytes >= ByteKit.SIZE_MB){
			NumberKit.toDoubleStr(((bytes + 0.0) / ByteKit.SIZE_MB),decimalDigits,sb).append(" MB");
		}else if(bytes >= ByteKit.SIZE_KB){
			NumberKit.toDoubleStr(((bytes + 0.0) / ByteKit.SIZE_KB),decimalDigits,sb).append(" KB");
		}else{
			sb.append(bytes).append(" BYTE");
		}
		return sb.toString();
	}

	/**
	 * 回傳該數值的bit列
	 * @param x
	 * @param buf
	 * @return
	 */
	public static StringBuffer getBitString(int x, StringBuffer buf) {
		for (int i = 1; i <= 32; i++){
			buf.append(x >>> (32-i) & 0x00000001);
		}
		return buf;
	}
}
