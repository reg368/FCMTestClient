package hyweb.gip.ctr.admin;

import hyweb.gip.dao.service.InfoUserLoginService;
import hyweb.gip.dao.service.InfoUserService;
import hyweb.gip.model.encrypt.impl.EncryptHandleImpl;
import hyweb.gip.model.login.impl.DefaultLoginModel;
import hyweb.gip.model.session.impl.HttpSessionHandle;
import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.gip.pojo.mybatis.table.InfoUserLogin;
import hyweb.util.GetIP;
import hyweb.util.SpringLifeCycle;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * login相關Controller
 * @author Istar
 * */

@Controller
@RequestMapping("/login")
public class AdminLogin {

	private InfoUserService infoUserService = 
			(InfoUserService)SpringLifeCycle.getBean("InfoUserServiceImpl");

	private InfoUserLoginService infoUserLoginService = 
		(InfoUserLoginService)SpringLifeCycle.getBean("InfoUserLoginServiceImpl");
	
	/**
	 * 登入頁面
	 * */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	/**
	 * 登出頁面
	 * */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(){
		return "logout";
	}
	
	/**
	 * 驗證圖形碼頁面
	 * */
	@RequestMapping(value = "checkCode", method = RequestMethod.POST)
	public String checkCode() {
		return "checkCode";
	}
	/**
	 * 產生圖形碼頁面
	 * */
	@RequestMapping(value = "getImage", method = RequestMethod.GET)
	public String getImage(){
		return "getImage";
	}
	
	/**
	 * 驗證登入帳密
	 * */
	@RequestMapping(value = "accept" , method = RequestMethod.POST)
	public String accept(HttpServletRequest request, String userid, String password) throws ServletException, IOException{
		InfoUser infoUser = new InfoUser();
		infoUser.setUserid(userid);
		EncryptHandleImpl encryptHandleImpl = 
				(EncryptHandleImpl)SpringLifeCycle.getBean("encryptHandleImpl");
		infoUser.setPassword(encryptHandleImpl.irreversible(password));
		DefaultLoginModel defaultLoginModel = 
				(DefaultLoginModel)SpringLifeCycle.getBean("defaultLoginModel");
		infoUser = defaultLoginModel.accept(infoUser);
		if(infoUser==null)
			return "loginFail";
		else{
			infoUser.setVisitcount(infoUser.getVisitcount()+1);
			infoUserService.updateCountByPrimaryKey(infoUser);
			InfoUserLogin infoUserLogin = new InfoUserLogin();
			infoUserLogin.setLoginuserid(userid);
			infoUserLogin.setLastip(new GetIP().getIpAddr(request));
			infoUserLogin.setLastvisit(new Date());
			infoUserLoginService.insert(infoUserLogin);
			
			defaultLoginModel.saveLoginInfo(new HttpSessionHandle(request));
			
			return "redirect:/app/admin/Home";
		}
	}
}
