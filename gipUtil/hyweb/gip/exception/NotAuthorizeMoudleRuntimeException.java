package hyweb.gip.exception;

public class NotAuthorizeMoudleRuntimeException extends LevelableRuntimeException {
	private static final long serialVersionUID = -279792795404763121L;

	public NotAuthorizeMoudleRuntimeException(String moudleName){
		super("Sorry sir. You don't buy " + moudleName + " moudle yet. If you want to use this api ,you can contact www.hyweb.com.tw", ErrorLevel.ALERT);
	}
}
