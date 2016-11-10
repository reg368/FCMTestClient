package hyweb.gip.ctr.admin;

import hyweb.gip.dao.service.CodeMetaDefService;
import hyweb.gip.pojo.mybatis.table.CodeMetaDef;
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
@RequestMapping(value="/admin/CodeMetaDef")
public class AdminCodeMetaDef {
	
	private final String useAp = "CodeMetaDef";
	
	private EmptyValidator validator = null;

		
	private CodeMetaDefService codeMetaDefService = 
			(CodeMetaDefService)SpringLifeCycle.getBean("CodeMetaDefServiceImpl");

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
	@RequestMapping(value="list/codeMetaDef", method=RequestMethod.GET)
	public String showForm(
			@RequestParam(value = "page", required = false, defaultValue = "1") String paramPage,
			HttpServletRequest req,
			ModelMap model) throws UnsupportedEncodingException{
		SessionResultHelper.saveRequestParam(req);
		
		int page = PageHelper.getValue(paramPage, 1);
		int showNum = 10;
		int pageSize = 5;
		int count = codeMetaDefService.countAll();
		
		PageJump pageJump = PageHelper.getPageJump(page, showNum, pageSize, count);
		
		List<CodeMetaDef> codeMetaDefs = codeMetaDefService.list(page, showNum);
		model.addAttribute("codeMetaDefs", codeMetaDefs);
		CodeMetaDef codeMetaDef = new CodeMetaDef();
		model.addAttribute("codeMetaDef", codeMetaDef);
		
		model.addAttribute("args", UrlHelper.getQueryString(req.getParameterMap(), "page"));
		model.addAttribute("pageJump", pageJump);
		model.addAttribute("count", count);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "codeMetaDefList";
	}
	
	/**
	 * 新增頁
	 * */
	@RequestMapping(value="insert/codeMetaDefPage", method=RequestMethod.GET)
	public String addPage(ModelMap model, HttpServletRequest req){
		CodeMetaDef codeMetaDef = new CodeMetaDef();
		model.addAttribute("codeMetaDef", codeMetaDef);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "codeMetaDefAdd";
	}

	/**
	 * 新增action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="insert/codeMetaDefAction", method=RequestMethod.POST)
	public String add(@ModelAttribute(value="codeMetaDef") CodeMetaDef codeMetaDef, BindingResult result,
			HttpServletRequest req,ModelMap model) throws UnsupportedEncodingException{
		validator.validate(codeMetaDef, result);
		String msg = "";
		
		if(!result.hasErrors()){
			try{
				codeMetaDefService.insert(codeMetaDef);
				msg = "新增成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "新增失敗";
			}
			String nextUrl = "/admin/app/admin/CodeMetaDef/list/codeMetaDef";
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		model.addAttribute("codeMetaDef", codeMetaDef);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "codeMetaDefAdd";
	}
	
	/**
	 * 編輯頁
	 * */
	@RequestMapping(value="view/codeMetaDefPage", method=RequestMethod.GET)
	public String editPage(ModelMap model , int codeMetaSeq,
			HttpServletRequest req){
		CodeMetaDef codeMetaDef = codeMetaDefService.selectByPrimaryKey(codeMetaSeq);
		model.addAttribute("codeMetaDef", codeMetaDef);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "codeMetaDefEdit";
	}
	
	/**
	 * 修改action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="update/codeMetaDefAction", method=RequestMethod.POST)
	public String update(@ModelAttribute(value="codeMetaDef") CodeMetaDef codeMetaDef, BindingResult result,
			HttpServletRequest req,ModelMap model) throws UnsupportedEncodingException{
		validator.validate(codeMetaDef, result);
		String msg = "";
		
		if(!result.hasErrors()){
			try{
				codeMetaDefService.updateByPrimaryKey(codeMetaDef);
				msg = "修改成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "修改失敗";
			}
			String nextUrl = "/admin/app/admin/CodeMetaDef/list/codeMetaDef";
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		
		model.addAttribute("codeMetaDef", codeMetaDef);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "codeMetaDefEdit";
	}
	
	/**
	 * 刪除action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="delete/codeMetaDefAction", method=RequestMethod.POST)
	public String delete(@ModelAttribute(value="codeMetaDef") CodeMetaDef codeMetaDef, BindingResult result,
			HttpServletRequest req) throws UnsupportedEncodingException{
		validator.validate(codeMetaDef, result);
		String msg = "";
		
		if(!result.hasErrors()){
			try{
				codeMetaDefService.deleteByPrimaryKey(codeMetaDef.getCodemetaseq());
				msg = "刪除成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "刪除失敗";
			}
		}
		
		String nextUrl = "/admin/app/admin/CodeMetaDef/list/codeMetaDef";
		return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
	}
}
