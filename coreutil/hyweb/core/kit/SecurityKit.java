package hyweb.core.kit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * <pre>有關各種各種安全性的簡化呼叫，盡可能以效能為最大考量，禁止繼承，禁止用LOGGER</pre>
 * @author A0074
 * @version 1.0.090827
 * @since xBox 1.0
 */
public final class SecurityKit {
	public final static int BASE16 = 1;
	public final static int BASE64 = 2;
	public final static int BASE64_URL = 3;
	private final static char[] EN_16_ALPHABET = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	private final static char[] EN_64_ALPHABET = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','/'};
	private final static char[] EN_64_URL_ALPHABET = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9','*','-'};
	private final static byte[] DE_64_ALPHABET = {
		    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
		    52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
		    -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
		    15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
		    -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
		    41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };

	private SecurityKit(){
		super();
	}

	/**
	 * 使用帶入的演算法進行加密的運算
	 * @param source 資料來源
	 * @param algorithm 演算法名稱
	 * @return 加密後的byte[]
	 * @throws NoSuchAlgorithmException
	 */
	private static byte[] encrypt(byte[] source, String algorithm) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.update(source);
		return md.digest();
	}

	/**
	 * 使用RSA演算法進行加密
	 * @param publicKey RSA公鑰
	 * @param source 來源
	 * @return 加密後的byte[]
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	private static byte[] encrypt(RSAPublicKey publicKey, byte[] source) throws NoSuchPaddingException,NoSuchAlgorithmException,InvalidKeyException,BadPaddingException,IllegalBlockSizeException{
		if (publicKey != null){
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(source);
        }
        return null;
    }

	/**
	 * 使用RSA演算法對經由同演算法加密的資料進行解密
	 * @param privateKey RSA私鑰
	 * @param source 來源
	 * @return 解密後的byte[]
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	private static byte[] decrypt(RSAPrivateKey privateKey, byte[] source) throws NoSuchPaddingException,NoSuchAlgorithmException,InvalidKeyException,BadPaddingException, IllegalBlockSizeException{
		if (privateKey != null){
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(source);
		}else{
			return null;
		}
    }

	/**
	 * Message-Digest Algorithm 5，不可逆的加密演算法，已被破解。會固定回傳128bit的資料，常用32個16進位字串來表示，或是24個64進位字串來表示。
	 * @param source 來源字串
	 * @param returnType 回傳資料的編碼方式{SecurityKit.BASE16,SecurityKit.BASE64,SecurityKit.BASE64_URL}
	 * @return 依據編碼後的字串，如果不支援這種編碼格式則回傳StringKit.NULL_STRING
	 * @throws NoSuchAlgorithmException
	 */
	public static String encryptByMD5(String source,int returnType) throws NoSuchAlgorithmException{
		byte[] tmp = SecurityKit.encrypt(source.getBytes(), "MD5");
		switch(returnType){
			case SecurityKit.BASE16 :
				return SecurityKit.encryptByBase16(tmp);
			case SecurityKit.BASE64 :
				return SecurityKit.encryptByBase64(tmp, false);
			case SecurityKit.BASE64_URL :
				return SecurityKit.encryptByBase64(tmp, true);
			default :
				return StringKit.NULL_STRING;
		}
	}

	/**
	 * Secure Hash Algorithm，不可逆的加密演算法，為美國國家標準。會固定回傳160bit的資料，最大支援2^64 - 1 長度的資料。
	 * @param source 來源字串
	 * @param returnType 回傳資料的編碼方式{SecurityKit.BASE16,SecurityKit.BASE64,SecurityKit.BASE64_URL}
	 * @return 依據編碼後的字串，如果不支援這種編碼格式則回傳StringKit.NULL_STRING
	 * @throws NoSuchAlgorithmException
	 */
	public static String encryptBySHA1(String source, int returnType) throws NoSuchAlgorithmException{
		byte[] tmp = SecurityKit.encrypt(source.getBytes(), "SHA-1");
		switch(returnType){
			case SecurityKit.BASE16 :
				return SecurityKit.encryptByBase16(tmp);
			case SecurityKit.BASE64 :
				return SecurityKit.encryptByBase64(tmp, false);
			case SecurityKit.BASE64_URL :
				return SecurityKit.encryptByBase64(tmp, true);
			default :
				return StringKit.NULL_STRING;
		}
	}

	/**
	 * 取得加解密用的key
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static KeyPair getRSAkey() throws NoSuchAlgorithmException{
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		return keyPairGen.generateKeyPair();
	}

	/**
	 * 使用公用的key進行編碼，如果要編譯為16進位字串請於輸出後呼叫ByteKit.convertToHex()。
	 * @param source
	 * @param keyPair
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptRSA(String source, KeyPair keyPair) throws Exception{
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		return SecurityKit.encrypt(publicKey, source.getBytes());
	}

	/**
	 * 使用私人的key進行解碼，如果由16進位字串要轉回請先呼叫ByteKit.convertHexStrToByte()。
	 * @param source
	 * @param keyPair
	 * @return
	 * @throws Exception
	 */
	public static String encryptRSA(byte[] source,KeyPair keyPair) throws Exception{
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		return new String(SecurityKit.decrypt(privateKey,source));
	}

	/**
	 * 16進位的字串表示法轉為byte[]
	 * @param data 16進位表示法
	 * @return
	 */
	public static byte[] decryptByBase16(String data) {
		char[] source = data.toCharArray();
		byte[] out = new byte[source.length / 2];
		int value1,value2,count = 0;
		for(int i = 0 ; i < out.length ; i++){
			value1 = (int)source[count ++];
			value1 = value1 - (value1 >= 97 ? 87 : (value1 <= 57 ? 48 : 55));
			value2 = (int)source[count ++];
			value2 = value2 - (value2 >= 97 ? 87 : (value2 <= 57 ? 48 : 55));
			out[i] = (byte)((value1 << 4) + value2);
		}
		return out;
	}

	/**
	 * byte[]轉為16進位的字串表示法
	 * @param data
	 * @return 小寫的16進位表示法
	 */
	public static String encryptByBase16(byte[] data) {
		char[] source = new char[data.length * 2];
		int count = 0;
		for(int i = 0 ; i < source.length ; i += 2){
			byte tmp = data[count ++];
			source[i] = SecurityKit.EN_16_ALPHABET[(tmp & 0xff) >> 4];
			source[i + 1] = SecurityKit.EN_16_ALPHABET[tmp & 0x0F];
		}
		return new String(source);
	}

	/**
	 * 64進位的字串表示法轉為byte[]
	 * @param data
	 * @param isForUrl 是否用於網址列
	 * @return
	 * @throws IOException 
	 */
	public static byte[] decryptByBase64(String data, boolean isForUrl) throws IOException{
		// TODO isForUrl 尚未實作
        byte[] source = data.getBytes();
        int len = source.length;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
        int i = 0;
        int b1, b2, b3, b4;
        byte[] base64DecodeChars = SecurityKit.DE_64_ALPHABET;
        while (i < len) {
            do {
                b1 = base64DecodeChars[source[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1) {
                break;
            }
            do {
                b2 = base64DecodeChars[source[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1) {
                break;
            }
            buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));
            do {
                b3 = source[i++];
                if (b3 == 61) {
                    return buf.toByteArray();
                }
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1) {
                break;
            }
            buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));
            do {
                b4 = source[i++];
                if (b4 == 61) {
                    return buf.toByteArray();
                }
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1) {
                break;
            }
            buf.write((int) (((b3 & 0x03) << 6) | b4));
        }
		//return new sun.misc.BASE64Decoder().decodeBuffer(data);
        return buf.toByteArray();
	}

	/**
	 * byte[]轉為64進位的字串表示法
	 * @param data
	 * @param isForUrl 是否用於網址列
	 * @return 
	 */
	public static String encryptByBase64(byte[] data, boolean isForUrl){
		// TODO 轉String時的預設編碼由參數檔帶入
		final int newLineDistance = 76;
		final int newLineAdd = isForUrl ? 0 : 1;
		char[] alphabet = isForUrl ? SecurityKit.EN_64_URL_ALPHABET : SecurityKit.EN_64_ALPHABET; 
		int totalLength = NumberKit.toInt(((data.length * 8) / 6), NumberKit.CARRY_ROUNDING_CEILING);
		int complementLength = isForUrl ? 0 : (totalLength % 4);
		int newLineLength = isForUrl ? 0 : ((totalLength + complementLength)/newLineDistance);
		char[] source = new char[totalLength + complementLength + newLineLength];
		int newLineCount = 0;
		int dataCount = 0;
		int turnCount = 1;
		for(int i = 0 ; i < source.length ; i++){
			if(data.length > dataCount){//8 + 8 + 8 = 6 + 6 + 6 + 6
				if(turnCount == 1){// 6
					source[i] = alphabet[(data[dataCount] & 0xff) >> 2];
				}else if(turnCount == 2){//2 + 4
					if(data.length == dataCount + 1){
						source[i] = alphabet[((data[dataCount++] & 0x03) << 4)];
					}else{
						source[i] = alphabet[((data[dataCount] & 0x03) << 4)+ ((data[++dataCount] & 0xff) >> 4)];
					}
					
				}else if(turnCount == 3){//4 + 2
					if(data.length == dataCount + 1){
						source[i] = alphabet[((data[dataCount++] & 0x0F) << 2)];
					}else{
						source[i] = alphabet[((data[dataCount] & 0x0F) << 2) + ((data[++dataCount] & 0xff) >> 6)];
					}
				}else if(turnCount == 4){//6
					source[i] = alphabet[(data[dataCount++] & 0x3F)];
					turnCount = 0;
				}
				turnCount ++;
			}else{
				source[i] = '=';
			}
			newLineCount += newLineAdd;
			if((newLineCount == newLineDistance) && !isForUrl){
				newLineCount = 0;
				source[++i] = '\n';
			}
		}
		return new String(source);
	}

	public static String lightWeightEncrypt(String src, String seed){
		if (src == null || src.length() == 0){
			return "";
		}
		BigInteger bi_passwd = new BigInteger(src.getBytes());
		BigInteger bi_r0 = new BigInteger(seed);
		BigInteger bi_r1 = bi_r0.xor(bi_passwd);
		return bi_r1.toString(16);
	}

	public static String lightWeightDecrypt(String encrypted, String seed){
		if (StringKit.isEmpty(encrypted)){
			return "";
		}
		BigInteger bi_confuse = new BigInteger(seed);
		try {
			BigInteger bi_r1 = new BigInteger(encrypted, 16);
			BigInteger bi_r0 = bi_r1.xor(bi_confuse);
			return new String(bi_r0.toByteArray());
		} catch (Exception e) {
			return "";
		}
	}
}
