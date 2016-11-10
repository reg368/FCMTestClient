package hyweb.gip.exception;

public class NotExistPageException extends LevelableException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5999598367201653252L;

	public NotExistPageException(String message) {
		super(message, ErrorLevel.ALERT);
	}
}
