package hyweb.gip.model.session;

import javax.servlet.http.HttpServletRequest;

/**
 * 管理Session 的統一介面
 * @author A0074
 *
 */
public interface SessionHandle {
	/**
	 * 取值
	 * @return
	 */
	public Object get(String key);

	/**
	 * 設值
	 * @param obj
	 */
	public void set(String key, Object obj);

	/**
	 * 設定request
	 * @param request
	 */
	public void setRequest(final HttpServletRequest request);
}
