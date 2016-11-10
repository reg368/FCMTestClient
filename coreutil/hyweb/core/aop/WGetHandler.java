package hyweb.core.aop;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


/**
 * Wget事件攔截器
 * @author Monyem
 * @version 1.0.121111
 * @since xBox 1.0
 */
public interface WGetHandler extends EventHandler{
	/**
	 * 回傳200與304時時觸發
	 * @param is
	 * @param charSet
	 * @throws IOException
	 */
	public void onSuccess(InputStream is, String charSet, Map<String, List<String>> headerFieldsMap) throws IOException;
	/**
	 * 回傳301與302時時觸發
	 * @param redirectUrl
	 * @return 是否要執行重導頁
	 * @throws IOException
	 */
	public boolean onRedirect(String redirectUrl) throws IOException;
	/**
	 * 非Success,Redirect時觸發
	 * @param is
	 * @param charSet
	 * @param httpCode
	 * @throws IOException
	 */
	public void onFailure(InputStream is, String charSet, int httpCode, Map<String, List<String>> headerFieldsMap) throws IOException;
}
