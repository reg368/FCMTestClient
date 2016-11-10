package hyweb.gip.pattern.provider;

import javax.servlet.http.HttpServletRequest;

/**
 * 資料提供者
 * @author A0074
 *
 */
public interface SaveProvider {

	/**
	 * 儲存
	 * @return 錯誤訊息，當成功時回傳NULL
	 */
	public String save(HttpServletRequest req) throws Exception ;
}
