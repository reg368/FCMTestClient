package hyweb.core.kit;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

/**
 * 與數字相關的基本工具
 * @author A0074
 * @version 1.0.100409
 * @since xBox 1.0
 */
public final class NumberKit {
	public static final int CARRY_ROUNDING_FLOOR = 1;
	public static final int CARRY_ROUNDING = 2;
	public static final int CARRY_ROUNDING_CEILING = 3;
	static final NumberFormat MONEY_FORMAT = new DecimalFormat("#,###,###");

	private NumberKit(){
		super();
	}

	/**
	 * 轉整數
	 * <pre>
	 * StringKit.toInt("a000",10) = 10
	 * StringKit.toInt(" 0010 ",1)) = 10
	 *</pre>
	 * @param src
	 * @param defValue
	 * @return
	 */
	public static int toInt(String src, int defValue){
		try{
			return Integer.parseInt(src.trim(), 10);
		}catch(NumberFormatException ex){
			return defValue;
		}catch(Exception exp){
			return StringKit.NULL_INTEGER;
		}
	}
	
	/**
	 * 轉整數
	 * <pre>
	 * StringKit.toInt("a000",10) = 10
	 * StringKit.toInt(" 0010 ",1)) = 10
	 *</pre>
	 * @param src
	 * @param defValue
	 * @return
	 */
	public static Integer toInteger(String src, Integer defValue){
		try{
			return Integer.parseInt(src.trim(), 10);
		}catch(NumberFormatException ex){
			return defValue;
		}catch(Exception exp){
			return StringKit.NULL_INTEGER;
		}
	}

	/**
	 * 轉換有號無號byte為整數
	 * <pre>
	 * NumberKit.toInt((byte)129,false) = -127
	 * NumberKit.toInt((byte)129,true) = 129
	 * </pre>
	 * @param src 輸入byte
	 * @param isSignedByte 是否要當為有號數
	 * @return 
	 */
	public static int toInt(byte src, boolean isSignedByte){
		int signedValue = (int)src;
		if(isSignedByte){
			return signedValue;
		}else{
			return signedValue >= 0 ? signedValue : Math.abs(((src ^ 0xFF) + 1));
		}
	}
	
	/**
	 * float 轉整數，並提供三種進位法
	 * <pre>
	 * NumberKit.toInt(-15.9f,NumberKit.CARRY_ROUNDING_FLOOR) = -15
	 * NumberKit.toInt(-15.5f,NumberKit.CARRY_ROUNDING) = -16
	 * NumberKit.toInt(-15.1f,NumberKit.CARRY_ROUNDING_CEILING) = -16
	 * </pre>
	 * @param src 要轉換的字串
	 * @param defValue 當非數字格式時回傳的值 
	 */
	public static int toInt(float src, int carryType){
		if(src == 0f){
			return 0;
		}
		switch(carryType){
			case NumberKit.CARRY_ROUNDING_FLOOR :
				return (int)src;
			case NumberKit.CARRY_ROUNDING :
				return (int)(src + (src > 0f ? 0.5f : -0.5f));
			case NumberKit.CARRY_ROUNDING_CEILING :
				return (int)src + (src > 0f ? 1 : -1);
			default:
				return (int)src;
		}
	}
	
	/**
	 * double 轉整數，並提供三種進位法
	 * <pre>
	 * NumberKit.toInt(15.9d,NumberKit.CARRY_ROUNDING_FLOOR) = 15
	 * NumberKit.toInt(15.5d,NumberKit.CARRY_ROUNDING) = 16
	 * NumberKit.toInt(15.1d,NumberKit.CARRY_ROUNDING_CEILING) = 16
	 * </pre>
	 * @param src 要轉換的字串
	 * @param defValue 當非數字格式時回傳的值 
	 */
	public static int toInt(double src, int carryType){
		if(src == 0d){
			return 0;
		}
		switch(carryType){
			case NumberKit.CARRY_ROUNDING_FLOOR :
				return (int)src;
			case NumberKit.CARRY_ROUNDING :
				return (int)(src + (src > 0d ? 0.5d : -0.5d));
			case NumberKit.CARRY_ROUNDING_CEILING :
				return (int)src + (src > 0d ? 1 : -1);
			default:
				return (int)src;
		}
	}

	/**
	 * float 的字串格式化，因為java的浮點數算法並不依照標準規定，數值會有很些微的差距。
	 * <pre>
	 * NumberKit.toFloatStr(10.2223f,20,new StringBuffer()).toString() = "10.22229958"
	 * </pre>
	 * @param src 輸入值
	 * @param decimalDigits 保留小數位數
	 * @param sb 給輸出用的輸入
	 * @return
	 */
	public static StringBuffer toFloatStr(float src, int decimalDigits, StringBuffer sb){
		int integerValue = (int)src;
		String decimal = String.valueOf(src - integerValue);
		int maxLength = decimal.length() -2;
		sb.append(String.valueOf(integerValue)).append(".").append(decimal.substring(2,(maxLength < decimalDigits ? decimal.length() : decimalDigits + 2)));
		return sb;
	}

	/**
	 * double 的字串格式化，因為java的浮點數算法並不依照標準規定，數值會有很些微的差距。
	 * @param src 輸入值
	 * @param decimalDigits 保留小數位數
	 * @param sb 給輸出用的輸入
	 * @return
	 */
	public static StringBuffer toDoubleStr(double src, int decimalDigits, StringBuffer sb){
		long longValue = (long)src;
		String decimal = String.valueOf(src - longValue);
		int maxLength = decimal.length() -2;
		sb.append(String.valueOf(longValue)).append(".").append(decimal.substring(2,(maxLength < decimalDigits ? decimal.length() : decimalDigits + 2)));
		return sb;
	}

	/**
	 * 轉為金錢格式
	 * @param src
	 * @return
	 */
	public static String toMoneyStyle(long src){
		return NumberKit.MONEY_FORMAT.format(src);
	}
	
	/**
	 * 轉巨數
	 * @param src
	 * @return
	 */
	public static BigDecimal toBigDecimal(String src){
		return new BigDecimal(src);
	}

	/**
	 * 轉巨數
	 * @param src
	 * @return
	 */
	public static BigDecimal toBigDecimal(long src){
		return BigDecimal.valueOf(src);
	}

	/**
	 * 不使用E來表示浮點數
	 * @param d
	 * @return
	 */
	public static String toStr(double d){
		String strVal = Double.toString(d);
		String[] strValArr = strVal.split("E");
		if(strValArr.length == 2){
			String coefficient = strValArr[0].replaceAll("\\.", "");
			int exponent = Integer.parseInt(strValArr[1], 10) + 1;
			if(exponent == coefficient.length()){//1.234567E7
				return coefficient;
			}else if(exponent > coefficient.length()){//1.0E7
				char[] zero = new char[exponent - coefficient.length() + 1];
				Arrays.fill(zero, '0');
				return coefficient + String.valueOf(zero); 
			}else{//1.2345678E7
				return coefficient.substring(0, exponent) + "." + coefficient.substring(exponent, coefficient.length());
			}
		}else{//< 1.0E8,123.456
			int idx = strVal.indexOf(".");
			return strVal.substring(0, idx <= 0 || idx != strVal.length() - 2 ? strVal.length() : idx);
		}
	}
	
	/**
	 * 判斷是否為數字，不含小數點
	 * @param str
	 * @return
	 */
	public static boolean isDigits(String str) {
		if (StringKit.isEmpty(str)) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
