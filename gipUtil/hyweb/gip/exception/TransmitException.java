package hyweb.gip.exception;

/**
 * 傳輸時出現錯誤
 * @author A0074
 *
 */
public class TransmitException extends LevelableException {
	private static final long serialVersionUID = 1070214137452501805L;

	public TransmitException(String message) {
		super(message, ErrorLevel.ALERT);
	}

	public TransmitException(String message, Throwable cause){
		super(message, cause, ErrorLevel.ALERT);
	}
}
