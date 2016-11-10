package hyweb.core.kit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;

final public class LoggerKit {
	private LoggerKit(){
		super();
	}

	private static String stream2String(InputStream is, String charSet){
		StringBuffer sb = new StringBuffer();
		try {
			StreamKit.copy(new BufferedReader(new InputStreamReader(is, charSet)), sb, "");
		} catch (Exception ex) {
			sb.append(ex.getMessage());
		}
		return sb.toString();
	}

	public static void error(Logger log, InputStream is, String charSet){
		if(log.isErrorEnabled()){
			log.error(LoggerKit.stream2String(is, charSet));
		}
	}

	public static void info(Logger log, InputStream is, String charSet){
		if(log.isInfoEnabled()){
			log.info(LoggerKit.stream2String(is, charSet));
		}
	}

	public static void debug(Logger log, InputStream is, String charSet){
		if(log.isDebugEnabled()){
			log.debug(LoggerKit.stream2String(is, charSet));
		}
	}
}
