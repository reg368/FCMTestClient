package hyweb.core.util;

/**
 * 丟出給值錯誤
 * @author A0074
 *
 */
public class PutKitException extends Exception {
	private static final long serialVersionUID = 8108509440689433933L;
	public PutKitException(String msg){
		super(msg);
	}
}
