package hyweb.gip.exception;

public class GenXmlException extends LevelableException {
	private static final long serialVersionUID = -7023264296668775197L;

	public GenXmlException(String message) {
		super(message, ErrorLevel.ALERT);
	}
}
