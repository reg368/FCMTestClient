package hyweb.gip.exception;

/**
 * 有錯誤等級的Exception
 * @author A0074
 * @since 1.0
 */
public class LevelableException extends Exception{
	private static final long serialVersionUID = -8099904673477255034L;
	private ErrorLevel errorLevel;

	/**
	 * 使用Exception自定義的錯誤等級
	 * @param message 訊息
	 */
	public LevelableException(String message){
		super(message);
		this.errorLevel = this.getDefaultErrorLevel();
	}

	/**
	 * 自訂錯誤等級
	 * @param message 訊息
	 * @param errorLevel 錯誤等級,越高越嚴重
	 */
	public LevelableException(String message, ErrorLevel errorLevel){
		super(message);
		this.errorLevel = errorLevel;
	}

	/**
	 * 使用Exception自定義的錯誤等級
	 * @param message 訊息
	 * @param cause 錯誤堆疊
	 */
	public LevelableException(String message, Throwable cause){
		super(message, cause);
		this.errorLevel = this.getDefaultErrorLevel();
	}

	/**
	 * 自訂錯誤等級
	 * @param message 訊息
	 * @param cause 錯誤堆疊
	 * @param errorLevel 錯誤等級,越高越嚴重
	 */
	public LevelableException(String message, Throwable cause, ErrorLevel errorLevel){
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
