package hyweb.gip.aop;

import hyweb.gip.exception.LoginTimeoutException;
import hyweb.gip.model.session.impl.HttpSessionHandle;
import hyweb.gip.pojo.mybatis.table.InfoUser;

import javax.servlet.http.HttpServletRequest;

public class CheckLogin {
	public static InfoUser check(HttpServletRequest req){
		InfoUser infoUser = (InfoUser)new HttpSessionHandle(req).getLoginUser();
		if(infoUser != null){
			return infoUser;
		}else{
			throw new LoginTimeoutException("未登入，請重新登入!");
		}
	}
}
