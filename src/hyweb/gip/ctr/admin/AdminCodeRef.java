package hyweb.gip.ctr.admin;

import hyweb.gip.dao.service.CodeRefService;
import hyweb.gip.dao.service.CodeMetaDefService;
import hyweb.gip.pojo.mybatis.table.CodeRef;
import hyweb.gip.pojo.mybatis.table.CodeMetaDef;
import hyweb.gip.validator.EmptyValidator;
import hyweb.util.PageLimits;
import hyweb.util.SessionResultHelper;
import hyweb.util.SpringLifeCycle;

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

@Controller
@RequestMapping(value="/admin/CodeRef")
public class AdminCodeRef {
	
	private final String useAp = "CodeRef";
	
	private EmptyValidator validator = null;

		
	private CodeRefService codeRefService = 
			(CodeRefService)SpringLifeCycle.getBean("CodeRefServiceImpl");
	
	
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
	 * 新增頁
	 * */
	@RequestMapping(value="insert/codeRefPage", method = {RequestMethod.GET , RequestMethod.POST})
	public String addPage(ModelMap model, HttpServletRequest req, String refid, String refvalue){
		CodeRef codeRef = new CodeRef();
		model.addAttribute("codeRef", codeRef);
		model.addAttribute("refid", refid);
		model.addAttribute("refvalue", refvalue);
		model.addAttribute("limits", new PageLimits(req, useAp));
		model.addAttribute("expendTid", req.getParameter("expendTid") == null ? "" : req.getParameter("expendTid"));
		return "codeRefAdd";
	}

	/**
	 * 新增action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="insert/codeRefAction", method=RequestMethod.POST)
	public String add(@ModelAttribute(value="codeRef") CodeRef codeRef, BindingResult result, HttpServletRequest req
			,ModelMap model) throws UnsupportedEncodingException{
		validator.validate(codeRef, result);
		String msg = "";
		
		if(!result.hasErrors()){
			try{
				codeRefService.insert(codeRef);
				msg = "新增成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "新增失敗";
			}
			String nextUrl = "/admin/app/admin/CodeMain/list/codeMain?expendTid=" + req.getParameter("expendTid");
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		
		model.addAttribute("codeRef", codeRef);
		model.addAttribute("refid", codeRef.getRefid());
		model.addAttribute("refvalue", codeRef.getRefvalue());
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "codeRefAdd";
	}
	
	/**
	 * 編輯頁
	 * */
	@RequestMapping(value="view/codeRefPage", method = {RequestMethod.GET , RequestMethod.POST})
	public String editPage(ModelMap model , int seq, HttpServletRequest req){
		CodeRef codeRef = codeRefService.selectByPrimaryKey(seq);
		model.addAttribute("codeRef", codeRef);
		model.addAttribute("limits", new PageLimits(req, useAp));
		model.addAttribute("expendTid", req.getParameter("expendTid") == null ? "" : req.getParameter("expendTid"));
		return "codeRefEdit";
	}
	
	/**
	 * 修改action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="update/codeRefAction", method=RequestMethod.POST)
	public String update(@ModelAttribute(value="codeRef") CodeRef codeRef, BindingResult result,
			HttpServletRequest req,ModelMap model) throws UnsupportedEncodingException{
		validator.validate(codeRef, result);
		String msg = "" ;
		
		if(!result.hasErrors()){
			try{
				codeRefService.updateByPrimaryKey(codeRef);
				msg = "修改成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "修改失敗";
			}
			String nextUrl = "/admin/app/admin/CodeMain/list/codeMain?expendTid=" + req.getParameter("expendTid");
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		
		model.addAttribute("codeRef", codeRef);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "codeRefEdit";
	}
	
	/**
	 * 刪除action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="delete/codeRefAction", method=RequestMethod.POST)
	public String delete(@ModelAttribute(value="codeRef") CodeRef codeRef, BindingResult result,
			HttpServletRequest req) throws UnsupportedEncodingException{
		validator.validate(codeRef, result);
		String msg = "" ;
		
			if(!result.hasErrors()){
				try{
					codeRefService.deleteByPrimaryKey(codeRef.getSeq());
					msg = "刪除成功";
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
	 * */
	@RequestMapping(value="list/codeRefById", method = {RequestMethod.GET , RequestMethod.POST})
	public String showFormById(ModelMap model, HttpServletRequest req, String refid, String refvalue){
		//SessionResultHelper.saveRequestParam(req);
		List<CodeRef> codeRefs = codeRefService.getById(refid, refvalue);
		model.addAttribute("codeRefs", codeRefs);
		CodeRef codeRef = new CodeRef();
		model.addAttribute("codeRef", codeRef);
		model.addAttribute("refid", refid);
		model.addAttribute("refvalue", refvalue);
		model.addAttribute("limits", new PageLimits(req, useAp));
		model.addAttribute("expendTid", req.getParameter("expendTid") == null ? "" : req.getParameter("expendTid"));
		return "codeRefList";
	}
	
}
