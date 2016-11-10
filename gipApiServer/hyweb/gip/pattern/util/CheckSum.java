package hyweb.gip.pattern.util;

import java.security.NoSuchAlgorithmException;

import hyweb.core.kit.SecurityKit;
import hyweb.core.kit.ServletKit;

import javax.servlet.http.HttpServletRequest;

public class CheckSum {

	public static String getCode(String... args){
		StringBuilder sb = new StringBuilder(256);
		for(String arg : args){
			sb.append(arg == null ? "" : arg);
		}
		return CheckSum.encode(sb);
	}

	public static String getReqCode(HttpServletRequest request, String... args){
		StringBuilder sb = new StringBuilder(256);
		for(String arg : args){
			sb.append(ServletKit.getReqStr(request, arg, ""));
		}
		return CheckSum.encode(sb);
	}

	private static String encode(StringBuilder sb){
		try {
			String fullStr = SecurityKit.encryptByMD5(sb.toString(), SecurityKit.BASE64_URL);
			return fullStr.substring(2, 12);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(System.out);
			return "";
		}
	}

	/**
	 * 檢查驗證碼
	 * @param code
	 * @param args
	 * @return
	 */
	public static boolean check(String code1, String code2){
		if(code1 == null || code2 == null || "".equals(code1) || "".equals(code2)){
			return false;
		}
		return code1.equals(code2);
	}
}
