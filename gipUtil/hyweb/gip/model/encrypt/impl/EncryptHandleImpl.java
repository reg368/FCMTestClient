package hyweb.gip.model.encrypt.impl;

import hyweb.gip.model.encrypt.ProjectEncryptHandle;
import hyweb.gip.pojo.mybatis.table.InfoUser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
/**
 *	加密處理實作
 *	@author Istar
 *
 */
public class EncryptHandleImpl implements ProjectEncryptHandle{
	
	/**
	 *	可逆加密
	 */
	@Override
	public String reversible_encrypt(String text, String key) {
		  String encrypt_key = "0f9cfb7a9acced8a4167ea8006ccd098";
          int ctr = 0;
          String tmp = "";
          for (int i = 0; i < text.length(); i++) {
              ctr = (ctr == encrypt_key.length()) ? 0 : ctr;
              tmp = tmp + encrypt_key.charAt(ctr)
                     + (char)(text.charAt(i) ^ encrypt_key.charAt(ctr));
              ctr++;
          }
          return base64_encode(key(tmp, key));
	}
	
	/**
	 *	可逆解密
	 */
	@Override
	public String reversible_decrypt(String text, String key) {
		text = base64_decode(text);
		text = key(text, key);
        String tmp = "";
        for (int i = 0; i < text.length(); i++) {
        	int c = 0;
        	c = text.charAt(i) ^ text.charAt(i + 1);
            String x = "" + (char) c;
            tmp += x;
            i++;
        }
        return tmp;
	}
	
	/**
	 *	不可逆加密
	 */
	@Override
	public String irreversible(String text) {
		return strMD5(text);
	}
	
	@Override
	public InfoUser encryptInfoUser(InfoUser infoUser, String userid) {
		infoUser.setName(reversible_encrypt(infoUser.getName(),userid));
		infoUser.setTel(reversible_encrypt(infoUser.getTel(),userid));
		infoUser.setCellphone(reversible_encrypt(infoUser.getCellphone(),userid));
		infoUser.setEmail(reversible_encrypt(infoUser.getEmail(),userid));
		infoUser.setJobtitles(reversible_encrypt(infoUser.getJobtitles(),userid));
		return infoUser;
	}
	
	public InfoUser decryptInfoUser(InfoUser infoUser) {
		if(infoUser!=null){
			infoUser.setName(reversible_decrypt(infoUser.getName(),infoUser.getUserid()));
			infoUser.setTel(reversible_decrypt(infoUser.getTel(), infoUser.getUserid()));
			infoUser.setCellphone(reversible_decrypt(infoUser.getCellphone(),infoUser.getUserid()));
			infoUser.setEmail(reversible_decrypt(infoUser.getEmail(),infoUser.getUserid()));
			infoUser.setJobtitles(reversible_decrypt(infoUser.getJobtitles(),infoUser.getUserid()));
		}
		return infoUser;
	}
	
	public String key(String txt, String encrypt_key) {
       encrypt_key = strMD5(encrypt_key);
       int ctr = 0;
       String tmp = "";
       for (int i = 0; i < txt.length(); i++) {
           ctr = (ctr == encrypt_key.length()) ? 0 : ctr;
           int c = txt.charAt(i) ^ encrypt_key.charAt(ctr);
           String x = "" + (char) c;
           tmp = tmp + x;
           ctr++;
       }
       return tmp;
     }

	public String base64_encode(String str) {
	       try {
			//return new sun.misc.BASE64Encoder().encode(str.getBytes("UTF-8"));
	    	   byte[] message = str.getBytes("UTF-8");
	    	   return  javax.xml.bind.DatatypeConverter.printBase64Binary(message);
	    	   
	    	   
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	 
	public String base64_decode(String str) {
	
		//sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
	    if (str == null)
	    	return null;
	    try {
	    	
	    	//return new String(decoder.decodeBuffer(str), "UTF-8");
	    	return new String(javax.xml.bind.DatatypeConverter.parseBase64Binary(str));
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return null;
	    }
	}
	
	public static final String strMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	               'a', 'b', 'c', 'd', 'e', 'f' };
	    try {
	    	byte[] strTemp = s.getBytes();
	        MessageDigest mdTemp = MessageDigest.getInstance("MD5");
	        mdTemp.update(strTemp);
	        byte[] md = mdTemp.digest();
	        int j = md.length;
	        char str[] = new char[j * 2];
	        int k = 0;
	        for (int i = 0; i < j; i++) {
	        	byte byte0 = md[i];
	        	str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	            str[k++] = hexDigits[byte0 & 0xf];
	        }
	        return new String(str);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    	return null;
	    }

}
