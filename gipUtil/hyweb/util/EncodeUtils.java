package hyweb.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.mail.internet.MimeUtility;

public class EncodeUtils {
	
	public static String encodingDownloadFileName(String userAgent, String fileName) throws UnsupportedEncodingException {
		boolean isInternetExplorer = (userAgent.indexOf("MSIE") > -1);
		
		if (isInternetExplorer) {
		    return URLEncoder.encode(fileName, "UTF-8");
		} else {
		    return MimeUtility.encodeWord(fileName, "UTF-8", null);
		}
	}
}
