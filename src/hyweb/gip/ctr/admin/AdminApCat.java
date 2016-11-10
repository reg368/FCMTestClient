package hyweb.gip.ctr.admin;

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

import hyweb.gip.dao.service.ApCatService;
import hyweb.gip.pojo.mybatis.table.ApCat;
import hyweb.gip.validator.EmptyValidator;
import hyweb.util.PageHelper;
import hyweb.util.PageJump;
import hyweb.util.PageLimits;
import hyweb.util.SessionResultHelper;
import hyweb.util.SpringLifeCycle;
import hyweb.util.UrlHelper;

@Controller
@RequestMapping(value="/admin/ApCat")
public class AdminApCat {
	
	private final String useAp = "ApCat";
	
	private EmptyValidator validator = null;
		
	private ApCatService apCatService = 
			(ApCatService)SpringLifeCycle.getBean("ApCatServiceImpl");


	public EmptyValidator getValidator() {
		return validator;
	}

	@Autowired
	public void setValidator(EmptyValidator validator) {
		this.validator = validator;
	}
	
    /**
     * 列表頁
     * @throws UnsupportedEncodingException 
     * */
	@RequestMapping(value="list/apCat", method=RequestMethod.GET)
	public String showForm(ModelMap model, HttpServletRequest req,
			@RequestParam(value = "page", required = false, defaultValue = "1") String paramPage) throws UnsupportedEncodingException{
		SessionResultHelper.saveRequestParam(req);
		
		int page = PageHelper.getValue(paramPage, 1);
		int showNum = 10;
		int pageSize = 5;
		int count = apCatService.countAll();
		
		PageJump pageJump = PageHelper.getPageJump(page, showNum, pageSize, count);
		
		LinkedList<ApCat> apCats = apCatService.list(page, showNum);
		model.addAttribute("apCats", apCats);
		ApCat apCat = new ApCat();
		model.addAttribute("apCat", apCat);
		model.addAttribute("limits", new PageLimits(req, useAp));
		model.addAttribute("args", UrlHelper.getQueryString(req.getParameterMap(), "page"));
		model.addAttribute("pageJump", pageJump);
		model.addAttribute("count", count);
		return "apCatList";
	}
	
	/**
	 * 新增頁
	 * */
	@RequestMapping(value="insert/apCatPage", method=RequestMethod.GET)
	public String addPage(ModelMap model, HttpServletRequest req){
		ApCat apCat = new ApCat();
		model.addAttribute("apCat", apCat);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "apCatAdd";
	}

	/**
	 * 新增action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="insert/apCatAction", method=RequestMethod.POST)
	public String add(@ModelAttribute(value="apCat") ApCat apCat,BindingResult result,
			HttpServletRequest req, ModelMap model) throws UnsupportedEncodingException{
		validator.validate(apCat, result);
		String msg = "";
		
			if(!result.hasErrors()){
				try{
					apCatService.insert(apCat);
					apCat = new ApCat();
					msg = "新增成功";
				}catch(Exception ex){
					ex.printStackTrace(System.out);
					msg = "新增失敗";
				}
				String nextUrl = "/admin/app/admin/ApCat/list/apCat";
				return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
			}
		model.addAttribute("apCat", apCat);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "apCatAdd";
	}
	
	/**
	 * 編輯頁
	 * */
	@RequestMapping(value="view/apCatPage", method=RequestMethod.GET)
	public String editPage(ModelMap model , String apCatId, HttpServletRequest req){
		ApCat apCat = apCatService.selectByPrimaryKey(apCatId);
		model.addAttribute("apCat", apCat);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "apCatEdit";
	}
	
	/**
	 * 修改action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="update/apCatAction", method=RequestMethod.POST)
	public String update(@ModelAttribute(value="apCat") ApCat apCat,BindingResult result,
			HttpServletRequest req, ModelMap model) throws UnsupportedEncodingException{
		validator.validate(apCat, result);
		String msg = "";
			if(!result.hasErrors()){
				try{
					apCatService.updateByPrimaryKey(apCat);
					msg = "修改成功";
				}catch(Exception ex){
					ex.printStackTrace(System.out);
					msg = "修改失敗";
				}
				String nextUrl = "/admin/app/admin/ApCat/list/apCat";
				return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
			}
			
		model.addAttribute("apCat", apCat);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "apCatEdit";
	}
	
	/**
	 * 刪除action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="delete/apCatAction", method=RequestMethod.POST)
	public String delete(@ModelAttribute(value="apCat") ApCat apCat,BindingResult result,
			HttpServletRequest req) throws UnsupportedEncodingException{
		String msg = "";
		try{
			apCatService.deleteByPrimaryKey(apCat.getApcatid());
			msg = "刪除成功";
		}catch(Exception ex){
			ex.printStackTrace(System.out);
			msg = "刪除失敗";
		}

		String nextUrl = "/admin/app/admin/ApCat/list/apCat";
		return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
	}
}
