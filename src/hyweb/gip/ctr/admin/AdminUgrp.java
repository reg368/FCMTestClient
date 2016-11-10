package hyweb.gip.ctr.admin;

import hyweb.gip.dao.service.ApService;
import hyweb.gip.dao.service.UgrpApService;
import hyweb.gip.dao.service.UgrpService;
import hyweb.gip.pojo.mybatis.table.Ap;
import hyweb.gip.pojo.mybatis.table.Ugrp;
import hyweb.gip.pojo.mybatis.table.UgrpAp;
import hyweb.gip.pojo.mybatis.table.UgrpApKey;
import hyweb.gip.validator.EmptyValidator;
import hyweb.util.PageHelper;
import hyweb.util.PageJump;
import hyweb.util.PageLimits;
import hyweb.util.SessionResultHelper;
import hyweb.util.SpringLifeCycle;
import hyweb.util.UrlHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 權限群組Controller
 * @author Istar
 * */

@Controller
@RequestMapping(value="/admin/Ugrp")
public class AdminUgrp {
	
	private final String useAp = "Ugrp";
	
	private EmptyValidator validator = null;
	
	private UgrpService ugrpService = 
				(UgrpService)SpringLifeCycle.getBean("UgrpServiceImpl");
	
	private UgrpApService ugrpApService = 
			(UgrpApService)SpringLifeCycle.getBean("UgrpApServiceImpl");

	private ApService apService = 
			(ApService)SpringLifeCycle.getBean("ApServiceImpl");
	
	
	public EmptyValidator getValidator() {
		return validator;
	}

	@Autowired
	public void setValidator(EmptyValidator validator) {
		this.validator = validator;
	}

	/**
	 * 權限群組列表頁
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="list/ugrp", method=RequestMethod.GET)
	public String showForm(
			@RequestParam(value = "page", required = false, defaultValue = "1") String paramPage,
			HttpServletRequest req,
			ModelMap model) throws UnsupportedEncodingException{
		SessionResultHelper.saveRequestParam(req);
		int page = PageHelper.getValue(paramPage, 1);
		int showNum = 10;
		int pageSize = 5;
		int count = ugrpService.countAll();
		
		PageJump pageJump = PageHelper.getPageJump(page, showNum, pageSize, count);
		
		LinkedList<Ugrp> ugrps = ugrpService.list(page, showNum);
		model.addAttribute("ugrps", ugrps);
		Ugrp ugrp = new Ugrp();
		model.addAttribute("ugrp", ugrp);
		model.addAttribute("limits", new PageLimits(req, useAp));
		
		model.addAttribute("args", UrlHelper.getQueryString(req.getParameterMap(), "page"));
		model.addAttribute("pageJump", pageJump);
		model.addAttribute("count", count);
		return "ugrpList";
	}
	/**
	 * 權限群組新增頁
	 * */
	@RequestMapping(value="insert/ugrpPage",method=RequestMethod.GET)
	public String addPage(ModelMap model, HttpServletRequest req){
		Ugrp ugrp = new Ugrp();
		model.addAttribute("ugrp", ugrp);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "ugrpAdd";
	}
	/**
	 * 權限群組新增action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="insert/ugrpAction", method=RequestMethod.POST)
	public String add(@ModelAttribute(value="ugrp") Ugrp ugrp,ModelMap model,
							BindingResult result, HttpServletRequest req) throws UnsupportedEncodingException{
		validator.validate(ugrp, result);
		String msg ="";
		
		if(!result.hasErrors()){
			try{
				ugrpService.insert(ugrp);	
				msg = "新增成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "新增失敗";
			}
			String nextUrl = "/admin/app/admin/Ugrp/list/ugrp";
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		
		model.addAttribute("ugrp", ugrp);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "ugrpAdd";
	}
	/**
	 * 權限群組編輯頁
	 * */
	@RequestMapping(value="view/ugrpPage",method=RequestMethod.GET)
	public String editPage(ModelMap model , int ugrpid, HttpServletRequest req){
		Ugrp ugrp = ugrpService.selectByPrimaryKey(ugrpid);
		model.addAttribute("ugrp", ugrp);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "ugrpEdit";
	}
	/**
	 * 權限群組更新action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="update/ugrpAction", method=RequestMethod.POST)
	public String update(@ModelAttribute(value="ugrp") Ugrp ugrp,ModelMap model,
							   BindingResult result, HttpServletRequest req) throws UnsupportedEncodingException{						
		validator.validate(ugrp, result);
		String msg = "";
		
		if(!result.hasErrors()){
			try{
				ugrpService.updateByPrimaryKey(ugrp);		
				msg = "修改成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "修改失敗";
			}
			String nextUrl = "/admin/app/admin/Ugrp/list/ugrp";
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		
		model.addAttribute("ugrp", ugrp);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "ugrpEdit";
	}
	/**
	 * 權限群組刪除action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="delete/ugrpAction", method=RequestMethod.POST)
	public String delete(@ModelAttribute(value="ugrp") Ugrp ugrp,
							   BindingResult result, HttpServletRequest req) throws UnsupportedEncodingException{
		String msg = "";
		try{
			ugrpService.deleteByPrimaryKey(ugrp.getUgrpid());
			msg = "刪除成功";	
		}catch(Exception ex){
			ex.printStackTrace(System.out);
			msg = "刪除失敗";	
		}
		String nextUrl = "/admin/app/admin/Ugrp/list/ugrp";
		return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		
	}
	/**
	 * 權限群組Ap列表頁
	 * */
	@RequestMapping(value="list/ugrpAp",method=RequestMethod.GET)
	public String editUgrpAp(ModelMap model , int ugrpid, HttpServletRequest req){
		LinkedList<UgrpAp> ugrpAps = ugrpApService.selectByUgrpId(ugrpid);
		model.addAttribute("ugrpAps", ugrpAps);
		UgrpAp ugrpAp = new UgrpAp();
		ugrpAp.setUgrpid(ugrpid);
		model.addAttribute("ugrpAp", ugrpAp);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "ugrpApList";
	}
	/**
	 * 權限群組Ap新增頁
	 * */
	@RequestMapping(value="/insert/ugrpApPage",method=RequestMethod.GET)
	public String ugrpApAddPage(ModelMap model, int ugrpid, HttpServletRequest req){
		
		UgrpAp ugrpAp = new UgrpAp();
		ugrpAp.setUgrpid(ugrpid);
		ugrpAp.setApfunc(0);
		model.addAttribute("ugrpAp", ugrpAp);
		LinkedList<Ap> aps = apService.selectNoUseAp(ugrpid);
		model.addAttribute("aps", aps);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "ugrpApAdd";
	}
	/**
	 * 權限群組Ap新增action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="insert/ugrpApAction", method=RequestMethod.POST)
	public String add(@ModelAttribute(value="ugrpAp") UgrpAp ugrpAp,ModelMap model,
							BindingResult result, HttpServletRequest req) throws UnsupportedEncodingException{
		validator.validate(ugrpAp, result);
		int ugrpid = ugrpAp.getUgrpid();
		String msg = "" ;
		
		if(!result.hasErrors()){
			try{
				ugrpApService.insert(ugrpAp);	
				msg = "新增成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "新增失敗";
			}
			String nextUrl = "/admin/app/admin/Ugrp/list/ugrpAp?ugrpid="+ugrpid;
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		model.addAttribute("ugrpAp", ugrpAp);
		LinkedList<Ap> aps = apService.selectNoUseAp(ugrpid);
		model.addAttribute("aps", aps);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "ugrpApAdd";
	}
	/**
	 * 權限群組Ap編輯頁
	 * */
	@RequestMapping(value="view/ugrpApPage",method=RequestMethod.GET)
	public String ugrpApEditPage(ModelMap model , int ugrpid, int apid, HttpServletRequest req){
		UgrpApKey key = new UgrpApKey();
		key.setApid(apid);
		key.setUgrpid(ugrpid);
		UgrpAp ugrpAp = ugrpApService.selectByPrimaryKey(key);
		model.addAttribute("ugrpAp", ugrpAp);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "ugrpApEdit";
	}
	/**
	 * 權限群組Ap更新action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="update/ugrpApAction", method=RequestMethod.POST)
	public String update(@ModelAttribute(value="ugrpAp") UgrpAp ugrpAp,
			ModelMap model , BindingResult result, HttpServletRequest req) throws UnsupportedEncodingException{						
		validator.validate(ugrpAp, result);
		int ugrpid = ugrpAp.getUgrpid();
		String msg = "";
		if(!result.hasErrors()){
			try{
				ugrpApService.updateByPrimaryKey(ugrpAp);
				msg = "修改成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "修改失敗";
			}
			String nextUrl = "/admin/app/admin/Ugrp/list/ugrpAp?ugrpid="+ugrpid;
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		model.addAttribute("ugrpAp", ugrpAp);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "ugrpApEdit";
	}
	/**
	 * 權限群組Ap刪除action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="delete/ugrpApAction", method=RequestMethod.POST)
	public String ugrpApdelete(@ModelAttribute(value="ugrpAp") UgrpAp ugrpAp,
							   BindingResult result, HttpServletRequest req) throws UnsupportedEncodingException{
		int ugrpid = ugrpAp.getUgrpid();
		String msg = "";
		try{
			UgrpApKey key = new UgrpApKey();
			key.setApid(ugrpAp.getApid());
			key.setUgrpid(ugrpAp.getUgrpid());
			ugrpApService.deleteByPrimaryKey(key);
			msg = "刪除成功";
		}catch(Exception ex){
			ex.printStackTrace(System.out);
			msg = "刪除失敗";
		}
		String nextUrl = "/admin/app/admin/Ugrp/list/ugrpAp?ugrpid="+ugrpid;
		return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
	}
}
