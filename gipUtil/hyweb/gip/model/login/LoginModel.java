package hyweb.gip.model.login;

import hyweb.gip.model.session.ProjectSessionHandle;

/**
 * 
 * @author A0074
 *
 * @param <T>
 */
public interface LoginModel <T>{
	/**
	 * 是否允許登入
	 * @param input
	 * @return 當不允許登入時回傳null
	 */
	public T accept(T input);

	/**
	 * 確定登入後一些後續行為
	 * @param session
	 */
	public void saveLoginInfo(ProjectSessionHandle session);
}
