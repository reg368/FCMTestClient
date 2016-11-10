package hyweb.gip.ctr.cms;

import hyweb.gip.dao.service.CodeMainService;
import hyweb.gip.dao.service.TreeService;
import hyweb.gip.pojo.mybatis.table.CodeMain;
import hyweb.gip.pojo.mybatis.table.Tree;
import hyweb.gip.validator.EmptyValidator;
import hyweb.util.SessionResultHelper;
import hyweb.util.SpringLifeCycle;

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

@Controller
@RequestMapping(value="/cms/TreeList")
public class CmsTreeList {
	
	private EmptyValidator validator = null;
	
	private  TreeService treeService = 
			(TreeService)SpringLifeCycle.getBean("TreeServiceImpl");
	
	private  CodeMainService codeMainService = 
			(CodeMainService)SpringLifeCycle.getBean("CodeMainServiceImpl");
	
	public EmptyValidator getValidator() {
		return validator;
	}
	
	@Autowired
	public void setValidator(EmptyValidator validator) {
		this.validator = validator;
	}
	
	public List<CodeMain> getFormShowYN() {
		return codeMainService.selectByCodeId("formShowYN");
	}
	
	public List<CodeMain> getTreeStyle() {
		return codeMainService.selectByCodeId("treeStyle");
	}
	
	/**
     * 樹列表頁
	 * @throws UnsupportedEncodingException 
     * */
	@RequestMapping(value="list/tree", method=RequestMethod.GET)
	public String showForm(ModelMap model,HttpServletRequest req) throws UnsupportedEncodingException{
		SessionResultHelper.saveRequestParam(req);
		List<Tree> trees = treeService.selectAll();
		model.addAttribute("trees", trees);
		model.addAttribute("treeStyle", getTreeStyle());
		Tree tree = new Tree();
		model.addAttribute("tree", tree);
		return "treeList";
	}
	
	/**
	 * 樹新增頁
	 * */
	@RequestMapping(value="insert/treePage", method=RequestMethod.GET)
	public String addPage(ModelMap model){
		Tree tree = new Tree();
		model.addAttribute("tree", tree);
		model.addAttribute("formShowYN", getFormShowYN());
		model.addAttribute("treeStyle", getTreeStyle());
		return "treeAdd";
	}

	/**
	 * 樹新增action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="insert/treeAction", method=RequestMethod.POST)
	public String add(@ModelAttribute(value="tree") Tree tree, BindingResult result,ModelMap model) throws UnsupportedEncodingException{
		validator.validate(tree, result);
		String msg = "";
		
		if(!result.hasErrors()){
			try{
				treeService.insert(tree);
				msg = "新增成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "新增失敗";
			}
			String nextUrl = "/admin/app/cms/TreeList/list/tree";
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		
		model.addAttribute("tree", tree);
		model.addAttribute("formShowYN", getFormShowYN());
		model.addAttribute("treeStyle", getTreeStyle());
		return "treeAdd";
	}
	
	/**
	 * 樹編輯頁
	 * */
	@RequestMapping(value="view/treePage", method=RequestMethod.GET)
	public String editPage(ModelMap model , int tId){
		Tree tree = treeService.selectByPrimaryKey(tId);
		model.addAttribute("tree", tree);
		model.addAttribute("formShowYN", getFormShowYN());
		model.addAttribute("treeStyle", getTreeStyle());
		return "treeEdit";
	}
	
	/**
	 * 樹修改action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="update/treeAction", method=RequestMethod.POST)
	public String update(@ModelAttribute(value="tree") Tree tree, BindingResult result,ModelMap model) throws UnsupportedEncodingException{
		validator.validate(tree, result);
		String msg = "";
		
		if(!result.hasErrors()){
			try{
				treeService.updateByPrimaryKey(tree);
				msg = "修改成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "修改失敗";
			}
			String nextUrl = "/admin/app/cms/TreeList/list/tree";
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		
		model.addAttribute("tree", tree);
		model.addAttribute("formShowYN", getFormShowYN());
		model.addAttribute("treeStyle", getTreeStyle());
		return "treeEdit";
	}
	
	/**
	 * 樹刪除action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="delete/treeAction", method=RequestMethod.POST)
	public String delete(@ModelAttribute(value="tree") Tree tree, BindingResult result) throws UnsupportedEncodingException{

		String msg = "";
		try{
			treeService.deleteByPrimaryKey(tree.getTid());
			msg = "刪除成功";
			
		}catch(Exception ex){
			ex.printStackTrace(System.out);
			msg = "刪除失敗";
		}
		
		String nextUrl = "/admin/app/cms/TreeList/list/tree";
		return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
	}

}
