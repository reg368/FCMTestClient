package hyweb.gip.exception;

public class LimitsException extends LevelableRuntimeException {
	private static final long serialVersionUID = 2326563819726690914L;

	public LimitsException(String message) {
		super(message, ErrorLevel.ALERT);
	}
}
