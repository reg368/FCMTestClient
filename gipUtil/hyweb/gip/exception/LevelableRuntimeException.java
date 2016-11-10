package hyweb.gip.exception;

/**
 * 有錯誤等級的RuntimeException
 * @author A0074
 * @since 1.0
 */
public class LevelableRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -7843651424078882754L;
	private ErrorLevel errorLevel;

	/**
	 * 使用Exception自定義的錯誤等級
	 * @param message 訊息
	 */
	public LevelableRuntimeException(String message){
		super(message);
		this.errorLevel = this.getDefaultErrorLevel();
	}

	/**
	 * 自訂錯誤等級
	 * @param message 訊息
	 * @param errorLevel 錯誤等級,越高越嚴重
	 */
	public LevelableRuntimeException(String message, ErrorLevel errorLevel){
		super(message);
		this.errorLevel = errorLevel;
	}

	/**
	 * 使用Exception自定義的錯誤等級
	 * @param message 訊息
	 * @param cause 錯誤堆疊
	 */
	public LevelableRuntimeException(String message, Throwable cause){
		super(message, cause);
		this.errorLevel = this.getDefaultErrorLevel();
	}

	/**
	 * 自訂錯誤等級
	 * @param message 訊息
	 * @param cause 錯誤堆疊
	 * @param errorLevel 錯誤等級,越高越嚴重
	 */
	public LevelableRuntimeException(String message, Throwable cause, ErrorLevel errorLevel){
		super(message, cause);
		this.errorLevel = errorLevel;
	}

	/**
	 * 取得錯誤層級
	 * @return
	 */
	public ErrorLevel getErrorLevel(){
		return this.errorLevel;
	}

	/**
	 * 預設的錯誤等級, 當繼承本物件時，務必覆寫本物件
	 * @return
	 */
	private ErrorLevel getDefaultErrorLevel(){
		return ErrorLevel.LOG;
	}
}
