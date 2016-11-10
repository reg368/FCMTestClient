package hyweb.gip.model.session.impl;

import hyweb.gip.model.session.ProjectSessionHandle;
import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.gip.pojo.mybatis.table.InfoUserLogin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 使用HttpSession 的實作
 * @author A0074
 *
 */
public class HttpSessionHandle implements ProjectSessionHandle {
	public static final String LOGIN_USER = "InfoUser";
	public static final String LOGIN_RECORD = "InfoUserLogin";
	//private static final Logger LOG = LoggerFactory.getLogger(HttpSessionHandle.class);
	private HttpSession session;

	public HttpSessionHandle(HttpServletRequest request){
		super();
		this.setRequest(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object get(String key) {
		return this.session.getAttribute(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set(String key, Object obj) {
		this.session.setAttribute(key, obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRequest(final HttpServletRequest request) {
		this.session = request.getSession(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InfoUser getLoginUser() {
		return (InfoUser)this.session.getAttribute(HttpSessionHandle.LOGIN_USER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLoginUser(InfoUser user) {
		this.session.setAttribute(HttpSessionHandle.LOGIN_USER, user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InfoUserLogin getLoginRecord() {
		return (InfoUserLogin)this.session.getAttribute(HttpSessionHandle.LOGIN_RECORD);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLoginRecord(InfoUserLogin infoUserLogin) {
		this.session.setAttribute(HttpSessionHandle.LOGIN_RECORD, infoUserLogin);
		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidate() {
		this.session.invalidate();
	}
}
