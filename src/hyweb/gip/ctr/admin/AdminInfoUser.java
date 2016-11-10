package hyweb.gip.ctr.admin;

import hyweb.gip.dao.service.CodeMainService;
import hyweb.gip.dao.service.DeptService;
import hyweb.gip.dao.service.InfoUserService;
import hyweb.gip.dao.service.UgrpService;
import hyweb.gip.model.session.impl.HttpSessionHandle;
import hyweb.gip.pojo.mybatis.table.CodeMain;
import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.gip.pojo.mybatis.table.Ugrp;
import hyweb.gip.validator.EmptyValidator;
import hyweb.util.PageHelper;
import hyweb.util.PageJump;
import hyweb.util.PageLimits;
import hyweb.util.SessionResultHelper;
import hyweb.util.SpringLifeCycle;
import hyweb.util.UrlHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * infouser相關Controller
 * @author Istar
 * */

@Controller
@RequestMapping(value="/admin/InfoUser")
public class AdminInfoUser {
	
	private final String useAp = "InfoUser";
	
	private EmptyValidator validator = null;
	
	private InfoUserService infoUserService = 
			(InfoUserService)SpringLifeCycle.getBean("InfoUserServiceImpl");
	
	private UgrpService ugrpService = 
			(UgrpService)SpringLifeCycle.getBean("UgrpServiceImpl");
	
	private CodeMainService codeMainService =
			(CodeMainService)SpringLifeCycle.getBean("CodeMainServiceImpl");
	
	private DeptService deptService =
			(DeptService)SpringLifeCycle.getBean("DeptServiceImpl");
	
	public EmptyValidator getValidator() {
		return validator;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	
	@Autowired
	public void setValidator(EmptyValidator validator) {
		this.validator = validator;
	}

	/**
	 * 使用者列表頁
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="list/infoUser", method=RequestMethod.GET)
	public String showForm(
			@RequestParam(value = "page", required = false, defaultValue = "1") String paramPage,
			HttpServletRequest req,
			ModelMap model) throws UnsupportedEncodingException{
		SessionResultHelper.saveRequestParam(req);
		this.getList(paramPage, model, req);
		return "infoUserList";
	}
	/**
	 * 使用者新增頁
	 * */
	@RequestMapping(value="insert/infoUserPage",method=RequestMethod.GET)
	public String addPage(ModelMap model, HttpServletRequest req){
		InfoUser infoUser = new InfoUser();
		infoUser.setSex("M");		
		model.addAttribute("infoUser", infoUser);
		LinkedList<Ugrp> ugrps = ugrpService.selectByUser(new HttpSessionHandle(req).getLoginUser().getUserid());
		model.addAttribute("ugrps", ugrps);
		LinkedList<CodeMain> codeMains = codeMainService.selectByCodeId("dataCollection");
		model.addAttribute("codeMains",codeMains);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "infoUserAdd";
	}
	/**
	 * 使用者新增action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="insert/infoUserAction", method=RequestMethod.POST)
	public String add(@ModelAttribute(value="infoUser") InfoUser infoUser,
			ModelMap model,
							BindingResult result, HttpServletRequest req) throws UnsupportedEncodingException{
		validator.validate(infoUser, result);
		String msg ="";
		if(!result.hasErrors()){
			try{
				infoUser.setCreatetime(new Date());
				infoUser.setCreateuser(new HttpSessionHandle(req).getLoginUser().getUserid());
				infoUserService.insert(infoUser);
				msg = "新增成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "新增失敗";
			}
			String nextUrl = "/admin/app/admin/InfoUser/list/infoUser";
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		LinkedList<Ugrp> ugrps = ugrpService.selectByUser(new HttpSessionHandle(req).getLoginUser().getUserid());
		model.addAttribute("ugrps", ugrps);
		LinkedList<CodeMain> codeMains = codeMainService.selectByCodeId("dataCollection");
		model.addAttribute("codeMains",codeMains);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "infoUserAdd";
	}
	/**
	 * 使用者編輯頁
	 * */
	@RequestMapping(value="view/infoUserPage",method=RequestMethod.GET)
	public String editPage(ModelMap model , String userid, HttpServletRequest req){
		InfoUser infoUser = infoUserService.selectByPrimaryKey(userid);
		infoUser.setPassword("");
		LinkedList<Ugrp> ugrps = ugrpService.selectByUser(new HttpSessionHandle(req).getLoginUser().getUserid());
		model.addAttribute("infoUser", infoUser);
		model.addAttribute("ugrps", ugrps);
		LinkedList<CodeMain> codeMains = codeMainService.selectByCodeId("dataCollection");
		model.addAttribute("codeMains",codeMains);
		model.addAttribute("dept", deptService.selectByPrimaryKey(infoUser.getDeptid()));
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "infoUserEdit";
	}
	/**
	 * 使用者更新action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="update/infoUserAction", method=RequestMethod.POST)
	public String update(@ModelAttribute(value="infoUser") InfoUser infoUser,
							   BindingResult result,HttpServletRequest req,ModelMap model) throws UnsupportedEncodingException{
		boolean changePassWord = false;
		if(infoUser.getPassword()!=null && "".equals(infoUser.getPassword()))
			infoUser.setPassword("noPwd");
		else
			changePassWord = true;
		String msg = "";
		validator.validate(infoUser, result);
		if(!result.hasErrors()){
			try{
				infoUser.setUpdateuser(new HttpSessionHandle(req).getLoginUser().getUserid());
				if(changePassWord)
					infoUserService.updateByPrimaryKey(infoUser, infoUser.getUserid());
				else
					infoUserService.updateWithoutPwd(infoUser, infoUser.getUserid());

				msg = "修改成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "修改失敗";
			}
			String nextUrl = "/admin/app/admin/InfoUser/list/infoUser";
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		else{
			model.addAttribute("msg", "修改失敗");
			LinkedList<Ugrp> ugrps = ugrpService.selectByUser(new HttpSessionHandle(req).getLoginUser().getUserid());
			model.addAttribute("ugrps", ugrps);
			LinkedList<CodeMain> codeMains = codeMainService.selectByCodeId("dataCollection");
			model.addAttribute("codeMains",codeMains);
			model.addAttribute("limits", new PageLimits(req, useAp));
			return "infoUserEdit";
		}
	}
	/**
	 * 使用者刪除action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="delete/infoUserAction", method=RequestMethod.POST)
	public String delete(@ModelAttribute(value="infoUser") InfoUser infoUser,
			HttpServletRequest req, BindingResult result,ModelMap model) throws UnsupportedEncodingException{
		
		String msg ="";
		try{
			infoUserService.deleteByPrimaryKey(infoUser.getUserid());
			msg = "刪除成功";
		}catch(Exception ex){
			ex.printStackTrace(System.out);
			msg = "刪除失敗";
		}
		String nextUrl = "/admin/app/admin/InfoUser/list/infoUser";
		return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
	}
	@RequestMapping(value="/checkUserId",method=RequestMethod.POST)
	public String checkUserId(String userid){
		return "checkUserId";
	}
	
	/**
	 * 個人編輯頁
	 * */
	@RequestMapping(value="view/infoUserOneselfPage",method=RequestMethod.GET)
	public String editOneselfPage(ModelMap model , HttpServletRequest req){
		String userid = new HttpSessionHandle(req).getLoginUser().getUserid();
		InfoUser infoUser =null;
		if(userid!=null){
			infoUser = infoUserService.selectByPrimaryKey(userid);
			infoUser.setPassword("");
			model.addAttribute("infoUser", infoUser);
			model.addAttribute("dept", deptService.selectByPrimaryKey(infoUser.getDeptid()));
			model.addAttribute("limits", new PageLimits(req, useAp));
			return "infoUserOneselfEdit";
		}
		else
			return "redirect:/app/page/ListShowFormAction/list/dataSetList";
	}
	/**
	 * 使用者更新action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="update/infoUserOneselfAction", method=RequestMethod.POST)
	public String updateOneselfPage(@ModelAttribute(value="infoUser") InfoUser infoUser,
							   BindingResult result,HttpServletRequest req,ModelMap model) throws UnsupportedEncodingException{
		boolean changePassWord = false;
		if(infoUser.getPassword()!=null && "".equals(infoUser.getPassword()))
			infoUser.setPassword("noPwd");
		else
			changePassWord = true;
			
		validator.validate(infoUser, result);
		if(!result.hasErrors()){
			String msg = "";
			try{
				infoUser.setUpdateuser(new HttpSessionHandle(req).getLoginUser().getUserid());
				if(changePassWord)
					infoUserService.updateByPrimaryKey(infoUser, infoUser.getUserid());
				else
					infoUserService.updateWithoutPwd(infoUser, infoUser.getUserid());
				msg = "修改成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "修改失敗";
			}
			String nextUrl = "/admin/app/admin/InfoUser/list/infoUser";
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		else{
			model.addAttribute("msg", "修改失敗");
			infoUser.setPassword("");
			model.addAttribute("infoUser", infoUser);
			return "infoUserOneselfEdit";
		}

	}
	
	private void getList(String paramPage,ModelMap model,HttpServletRequest req){
		String deptid = new HttpSessionHandle(req).getLoginUser().getDeptid().toString();
		int page = PageHelper.getValue(paramPage, 1);
		int showNum = 10;
		int pageSize = 5;
		int count = infoUserService.countAllByDept(deptid);
		
		PageJump pageJump = PageHelper.getPageJump(page, showNum, pageSize, count);
		
		LinkedList<InfoUser> infoUsers = infoUserService.list(page, showNum,deptid);
		model.addAttribute("infoUsers", infoUsers);
		InfoUser infoUser = new InfoUser();
		model.addAttribute("infoUser", infoUser);
		model.addAttribute("limits", new PageLimits(req, useAp));
		model.addAttribute("depts", deptService.selectAll());
		model.addAttribute("args", UrlHelper.getQueryString(req.getParameterMap(), "page"));
		model.addAttribute("pageJump", pageJump);
		model.addAttribute("count", count);
	}
}
