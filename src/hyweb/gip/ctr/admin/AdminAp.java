package hyweb.gip.ctr.admin;

import hyweb.gip.dao.service.ApCatService;
import hyweb.gip.dao.service.ApService;
import hyweb.gip.pojo.mybatis.table.Ap;
import hyweb.gip.pojo.mybatis.table.ApCat;
import hyweb.gip.validator.EmptyValidator;
import hyweb.util.PageHelper;
import hyweb.util.PageJump;
import hyweb.util.PageLimits;
import hyweb.util.SessionResultHelper;
import hyweb.util.SpringLifeCycle;
import hyweb.util.UrlHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/admin/Ap")
public class AdminAp {
	
	private final String useAp = "Ap";
	
	private EmptyValidator validator = null;


	private ApService apService = 
			(ApService)SpringLifeCycle.getBean("ApServiceImpl");
	
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
	@RequestMapping(value="list/ap", method=RequestMethod.GET)
	public String showForm(
			@RequestParam(value = "page", required = false, defaultValue = "1") String paramPage,
			HttpServletRequest req,
			ModelMap model) throws UnsupportedEncodingException{
		SessionResultHelper.saveRequestParam(req);
		int page = PageHelper.getValue(paramPage, 1);
		int showNum = 10;
		int pageSize = 5;
		int count = apService.countAll();
		
		PageJump pageJump = PageHelper.getPageJump(page, showNum, pageSize, count);
		
		List<Ap> aps = apService.list(page, showNum);
		List<ApCat> apCats = apCatService.selectAllByAp();
		model.addAttribute("aps", aps);
		model.addAttribute("apCats", apCats);
		Ap ap = new Ap();
		model.addAttribute("ap", ap);
		
		model.addAttribute("args", UrlHelper.getQueryString(req.getParameterMap(), "page"));
		model.addAttribute("limits", new PageLimits(req, useAp));
		model.addAttribute("pageJump", pageJump);
		model.addAttribute("count", count);
		return "apList";
	}
	
	/**
	 * 新增頁
	 * */
	@RequestMapping(value="insert/apPage", method=RequestMethod.GET)
	public String addPage(ModelMap model, HttpServletRequest req){
		Ap ap = new Ap();
		List<ApCat> apCats = apCatService.selectAllByAp();
		model.addAttribute("ap", ap);
		model.addAttribute("apCats", apCats);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "apAdd";
	}

	/**
	 * 新增action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="insert/apAction", method=RequestMethod.POST)
	public String add(@ModelAttribute(value="ap") Ap ap,BindingResult result, HttpServletRequest req
			,ModelMap model) throws UnsupportedEncodingException{
		validator.validate(ap, result);
		String msg = "";
		
			if(!result.hasErrors()){
				try{
					apService.insert(ap);
					msg = "新增成功";
				}catch(Exception ex){
					ex.printStackTrace(System.out);
					msg = "新增失敗";
				}
				String nextUrl = "/admin/app/admin/Ap/list/ap";
				return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
			}
		
		model.addAttribute("ap", ap);
		model.addAttribute("apCats", apCatService.selectAllByAp());
		model.addAttribute("limits", new PageLimits(req, useAp));

		return "apAdd";
	}
	
	/**
	 * 編輯頁
	 * */
	@RequestMapping(value="view/apPage", method=RequestMethod.GET)
	public String editPage(ModelMap model , int apId, HttpServletRequest req){
		Ap ap = apService.selectByPrimaryKey(apId);
		List<ApCat> apCats = apCatService.selectAllByAp();
		model.addAttribute("ap", ap);
		model.addAttribute("apCats", apCats);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "apEdit";
	}
	
	/**
	 * 修改action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="update/apAction", method=RequestMethod.POST)
	public String update(@ModelAttribute(value="ap") Ap ap,BindingResult result, HttpServletRequest req
			,ModelMap model) throws UnsupportedEncodingException{
		validator.validate(ap, result);
		String msg = "";
		
			if(!result.hasErrors()){
				try{
					apService.updateByPrimaryKey(ap);
					msg = "修改成功";
				}catch(Exception ex){
					ex.printStackTrace(System.out);
					msg = "修改失敗";
				}
				String nextUrl = "/admin/app/admin/Ap/list/ap";
				return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
			}
		model.addAttribute("ap", ap);
		model.addAttribute("apCats", apCatService.selectAllByAp());
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "apEdit";
	}
	
	/**
	 * 刪除action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="delete/apAction", method=RequestMethod.POST)
	public String delete(@ModelAttribute(value="ap") Ap ap,BindingResult result,
			HttpServletRequest req) throws UnsupportedEncodingException{
		String msg = "";
		try{
			apService.deleteByPrimaryKey(ap.getApid());
			msg = "刪除成功";
		}catch(Exception ex){
			ex.printStackTrace(System.out);
			msg = "刪除失敗";
		}
		String nextUrl = "/admin/app/admin/Ap/list/ap";
		return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
	}
}
