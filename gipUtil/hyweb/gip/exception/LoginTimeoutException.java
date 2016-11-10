package hyweb.gip.exception;

public class LoginTimeoutException extends LevelableRuntimeException {
	private static final long serialVersionUID = 6374357376126705729L;

	public LoginTimeoutException(String message) {
		super(message, ErrorLevel.ALERT);
	}

	public LoginTimeoutException(String message, Throwable cause) {
		super(message, cause, ErrorLevel.ALERT);
	}
}
