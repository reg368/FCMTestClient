package hyweb.gip.model.encrypt;
/**
 * 加密處理介面
 * @author Istar
 */
public interface EncryptHandle {
	/**
	 * 可逆_加密
	 */
	public String reversible_encrypt(String text, String key);
	
	/**
	 * 可逆_解密
	 */
	public String reversible_decrypt(String text, String key);
	
	/**
	 * 不可逆_加密
	 */
	public String irreversible(String text);
}
