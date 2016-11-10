package hyweb.gip.ctr.admin;

import hyweb.gip.dao.service.CodeMainService;
import hyweb.gip.dao.service.CodeMetaDefService;
import hyweb.gip.pojo.mybatis.table.CodeMain;
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
import java.util.LinkedList;
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
@RequestMapping(value="/admin/CodeMain")
public class AdminCodeMain {
	
	private final String useAp = "CodeMain";
	
	private EmptyValidator validator = null;

		
	private CodeMainService codeMainService = 
			(CodeMainService)SpringLifeCycle.getBean("CodeMainServiceImpl");
	
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
	 * 產生樹節點
	 * */
	public String treeNode(){
		List<CodeMetaDef> types = codeMetaDefService.selectType();
		List<CodeMetaDef> nodes = new LinkedList<CodeMetaDef>();
		StringBuffer treeResult = new StringBuffer();
		
		//產生樹節點
		treeResult.append("[");
		treeResult.append("{ id:\"001\", pId:\"0\", name:\"\", open:true},");
		for(int i = 0; i < types.size(); i++){
				treeResult.append("{ id:\"0" + types.get(i).getRowid() + "\", pId:\"001\", name:\"" + types.get(i).getCodemetatype() + "\"},");
				nodes = codeMetaDefService.selectNode(types.get(i).getCodemetatype());
				for(int j = 0; j < nodes.size(); j++){
					if(nodes.get(j).getCodemetatype().equals(types.get(i).getCodemetatype())){
						treeResult.append("{ id:\"" + nodes.get(j).getCodemetaseq() + "\", pId:\"0" + types.get(i).getRowid() + "\", name:\"" + nodes.get(j).getCodemetaname() + "\", click:\"getListPage('" + nodes.get(j).getCodemetaid() + "');\"},");
					}		
				}
		}
		treeResult.append("];");
		return treeResult.toString();
	}
	
    /**
     * 樹/列表頁
     * @throws Exception 
     * */
	@RequestMapping(value="list/codeMain", method=RequestMethod.GET)
	public String showForm(
			@RequestParam(value = "page", required = false, defaultValue = "1") String paramPage,
			HttpServletRequest req,
			ModelMap model) throws Exception{
		SessionResultHelper.saveRequestParam(req);
		
		int page = PageHelper.getValue(paramPage, 1);
		int showNum = 10;
		int pageSize = 5;
		int count = codeMainService.countAll();
		
		PageJump pageJump = PageHelper.getPageJump(page, showNum, pageSize, count);
		
		List<CodeMain> codeMains = codeMainService.listByTree(page, showNum);
		model.addAttribute("codeMains", codeMains);
		model.addAttribute("node", this.treeNode());
		CodeMain codeMain = new CodeMain();
		model.addAttribute("codeMain", codeMain);
		
		model.addAttribute("args", UrlHelper.getQueryString(req.getParameterMap(), "page"));
		model.addAttribute("pageJump", pageJump);
		model.addAttribute("count", count);
		model.addAttribute("limits", new PageLimits(req, useAp));
		model.addAttribute("expendTid", req.getParameter("expendTid") == null ? "" : req.getParameter("expendTid"));		
		return "codeMainTreeList";
	}
	
	/**
	 * 新增頁
	 * */
	@RequestMapping(value="insert/codeMainPage", method=RequestMethod.GET)
	public String addPage(ModelMap model, HttpServletRequest req, String codeId){
		CodeMain codeMain = new CodeMain();
		model.addAttribute("codeMain", codeMain);
		model.addAttribute("codeId", codeId);
		model.addAttribute("limits", new PageLimits(req, useAp));
		model.addAttribute("expendTid", req.getParameter("expendTid") == null ? "" : req.getParameter("expendTid"));
		return "codeMainAdd";
	}

	/**
	 * 新增action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="insert/codeMainAction", method=RequestMethod.POST)
	public String add(@ModelAttribute(value="codeMain") CodeMain codeMain,BindingResult result, 
			HttpServletRequest req,ModelMap model) throws UnsupportedEncodingException{
		validator.validate(codeMain, result);
		String msg = "";
		
		if(!result.hasErrors()){
			try{
				codeMainService.insert(codeMain);
				msg = "新增成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "新增失敗";
			}
			String nextUrl = "/admin/app/admin/CodeMain/list/codeMain?expendTid=" + req.getParameter("expendTid");
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
			
		}
		model.addAttribute("codeMain", codeMain);
		model.addAttribute("codeId", codeMain.getCodeid());
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "codeMainAdd";
	}
	
	/**
	 * 編輯頁
	 * */
	@RequestMapping(value="view/codeMainPage", method=RequestMethod.GET)
	public String editPage(ModelMap model , int codeSeq, HttpServletRequest req){
		CodeMain codeMain = codeMainService.selectByPrimaryKey(codeSeq);
		List<CodeMain> showValue = codeMainService.selectShowValueByPrimaryKey(codeSeq);
		model.addAttribute("codeMain", codeMain);
		model.addAttribute("showValue", showValue);
		model.addAttribute("limits", new PageLimits(req, useAp));
		model.addAttribute("expendTid", req.getParameter("expendTid") == null ? "" : req.getParameter("expendTid"));	
		return "codeMainEdit";
	}
	
	/**
	 * 修改action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="update/codeMainAction", method=RequestMethod.POST)
	public String update(@ModelAttribute(value="codeMain") CodeMain codeMain,BindingResult result,
			HttpServletRequest req,ModelMap model) throws UnsupportedEncodingException{
		validator.validate(codeMain, result);
		String msg = "";
		
		if(!result.hasErrors()){
			try{
				codeMainService.updateByPrimaryKey(codeMain);
				msg = "修改成功" ;
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "修改失敗";
			}
			String nextUrl = "/admin/app/admin/CodeMain/list/codeMain?expendTid=" + req.getParameter("expendTid");
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		List<CodeMain> showValue = codeMainService.selectShowValueByPrimaryKey(codeMain.getCodeseq());
		model.addAttribute("codeMain", codeMain);
		model.addAttribute("showValue", showValue);
		model.addAttribute("limits", new PageLimits(req, useAp));
		
		return "codeMainEdit";
	}
	
	/**
	 * 刪除action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="delete/codeMainAction", method=RequestMethod.POST)
	public String delete(@ModelAttribute(value="codeMain") CodeMain codeMain,BindingResult result,
			HttpServletRequest req) throws UnsupportedEncodingException{
		validator.validate(codeMain, result);
		String msg = "";
		
		if(!result.hasErrors()){
			try{
				codeMainService.deleteByPrimaryKey(codeMain.getCodeseq());
				msg = "刪除成功" ;
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "刪除失敗";
			}
		}
		
		String nextUrl = "/admin/app/admin/CodeMain/list/codeMain?expendTid=" + req.getParameter("expendTid");
		return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
	}
	
	/**
	 * 列表頁(codeMetaId)
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="list/codeMainById", method=RequestMethod.GET)
	public String showFormById(
			@RequestParam(value = "page", required = false, defaultValue = "1") String paramPage,
			ModelMap model, String codeId, HttpServletRequest req) throws UnsupportedEncodingException{
		SessionResultHelper.saveRequestParam(req);
		
		int page = PageHelper.getValue(paramPage, 1);
		int showNum = 10;
		int pageSize = 5;
		int count = codeMainService.countByCodeId(codeId);
		PageJump pageJump = PageHelper.getPageJump(page, showNum, pageSize, count);
		
		List<CodeMain> codeMains = codeMainService.listByCodeId(codeId, page, showNum);

		model.addAttribute("codeMains", codeMains);
		CodeMain codeMain = new CodeMain();
		model.addAttribute("codeMain", codeMain);		
		model.addAttribute("args", UrlHelper.getQueryString(req.getParameterMap(), "page"));
		model.addAttribute("pageJump", pageJump);
		model.addAttribute("count", count);
		model.addAttribute("codeId", codeId);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "codeMainList";
	}
	
}
