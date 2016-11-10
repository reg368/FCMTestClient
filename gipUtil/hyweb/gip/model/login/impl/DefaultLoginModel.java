package hyweb.gip.model.login.impl;

import hyweb.gip.dao.service.InfoUserLoginService;
import hyweb.gip.dao.service.InfoUserService;
import hyweb.gip.model.encrypt.impl.EncryptHandleImpl;
import hyweb.gip.model.login.LoginModel;
import hyweb.gip.model.session.ProjectSessionHandle;
import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.gip.pojo.mybatis.table.InfoUserLogin;
import hyweb.util.SpringLifeCycle;

/**
 * 登入實作
 * @author Istar
 *
 **/
public class DefaultLoginModel implements LoginModel<InfoUser>{
	private InfoUser infoUser;
	
	private InfoUserLogin infoUserLogin;
	
	private InfoUserService infoUserService = 
			(InfoUserService)SpringLifeCycle.getBean("InfoUserServiceImpl");
	
	private InfoUserLoginService infoUserLoginService = 
			(InfoUserLoginService)SpringLifeCycle.getBean("InfoUserLoginServiceImpl");
	
	private EncryptHandleImpl encryptHandleImpl = 
			(EncryptHandleImpl)SpringLifeCycle.getBean("encryptHandleImpl");
		
	@Override
	public InfoUser accept(InfoUser input) {
		infoUser = infoUserService.selectByUserPassword(input);
		if(infoUser==null){
			this.infoUser = null;
		}else{
			this.infoUserLogin = infoUserLoginService.selectLastByUserId(input.getUserid());
		}
		return infoUser = encryptHandleImpl.decryptInfoUser(this.infoUser);
	}

	@Override
	public void saveLoginInfo(ProjectSessionHandle session) {
		session.setLoginUser(this.infoUser);
		session.setLoginRecord(this.infoUserLogin);
	}
}
