package hyweb.gip.pojo.custom;
/**
 * 權限拋出之Exception
 * @author Istar
 */
public class CustomException extends RuntimeException{

	private static final long serialVersionUID = -2030411916550628416L;
	
	private String msg;
	
	public CustomException(){}
	
	public CustomException(String msg){
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
