package hyweb.gip.model.session;

import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.gip.pojo.mybatis.table.InfoUserLogin;

/**
 * 與專案有關的session 存取
 * @author monyem
 *
 */
public interface ProjectSessionHandle extends SessionHandle{
	/**
	 * 取得已儲存的登入資訊
	 * @return
	 */
	public InfoUser getLoginUser();

	/**
	 * 儲存需要儲存的登入資訊
	 * @param user
	 */
	public void setLoginUser(InfoUser user);
	
	/**
	 * 取得已儲存的登入紀錄資訊
	 * @return
	 */
	public InfoUserLogin getLoginRecord();

	/**
	 * 儲存需要儲存的登入紀錄資訊
	 * @param user
	 */
	public void setLoginRecord(InfoUserLogin infoUserLogin);

	/**
	 * 清除登入資訊
	 */
	public void invalidate();
}
