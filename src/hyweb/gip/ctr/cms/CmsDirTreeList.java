package hyweb.gip.ctr.cms;

import hyweb.gip.dao.service.CodeMainService;
import hyweb.gip.dao.service.SeqService;
import hyweb.gip.dao.service.TreeNodeService;
import hyweb.gip.pojo.mybatis.table.CodeMain;
import hyweb.gip.pojo.mybatis.table.TreeNode;
import hyweb.gip.validator.EmptyValidator;
import hyweb.util.CollectionHelper;
import hyweb.util.SpringLifeCycle;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/cms/DirTreeList")
public class CmsDirTreeList {
	
	private EmptyValidator validator = null;
	private TreeNodeService treeNodeService = 
			(TreeNodeService)SpringLifeCycle.getBean("TreeNodeServiceImpl");
	
	private CodeMainService codeMainService = 
			(CodeMainService)SpringLifeCycle.getBean("CodeMainServiceImpl");
	
	private SeqService seqService = 
			(SeqService)SpringLifeCycle.getBean("SeqServiceImpl");
	                      
	public EmptyValidator getValidator() {
		return validator;
	}
	
	@Autowired
	public void setValidator(EmptyValidator validator) {
		this.validator = validator;
	}
	
	private int tIdValue;
	
	public List<CodeMain> getNodeStatus() {
		return codeMainService.selectByCodeId("nodeStatus");
	}
	
	public List<CodeMain> getTreeType() {
		return codeMainService.selectByCodeId("treeType");
	}
	
	public List<CodeMain> getDataCollection() {
		return codeMainService.selectByCodeId("shelvesDsType");
	}
	
	/**
	 * 目錄樹呈現頁
	 * */
	@RequestMapping(value="list/dirTree", method=RequestMethod.GET)
	public String dirShow(ModelMap model, int tId) throws Exception{
		tIdValue = tId;
		model.addAttribute("tId", tIdValue);		
		return "dirTreeList";
	}
	
	/**
	 * 目錄樹新增頁
	 * */
	@RequestMapping(value="insert/dirTreePage", method=RequestMethod.GET)
	public String addPage(ModelMap model, int tId, int tnLevel, int parent){
		TreeNode treeNode = new TreeNode();
		Long tnId = seqService.getSeq("DataSet");
		String resultXpath = "";
		if(parent != 0){
			TreeNode Xpath = treeNodeService.selectByPrimaryKey((long)parent);
			resultXpath = Xpath.getTnxpath().replace("-", "") + "." + tnId + "-";
		}else{
			resultXpath = tnId + "-";
		}	
		model.addAttribute("tnId", tnId);
		model.addAttribute("tId", tId);
		model.addAttribute("tnXpath", resultXpath);
		model.addAttribute("tnLevel", tnLevel + 1);
		model.addAttribute("parent", parent);
		model.addAttribute("treeNode", treeNode);
		model.addAttribute("nodeStatus", getNodeStatus());
		model.addAttribute("treeType", getTreeType());
		model.addAttribute("dataCollection", getDataCollection());
		return "dirTreeAdd";
	}

	/**
	 * 目錄樹新增action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="insert/dirTreeAction", method=RequestMethod.POST)
	public String add(@ModelAttribute(value="treeNode") TreeNode treeNode, BindingResult result,
			ModelMap model) throws UnsupportedEncodingException{
		validator.validate(treeNode, result);
		String msg = "";
		
		if(!result.hasErrors()){
			try{
				treeNodeService.insert(treeNode);
				msg = "新增成功";		
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "新增失敗";
			}
			String nextUrl = "/admin/app/cms/DirTreeList/list/dirTree?tId="+treeNode.getTid();
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		model.addAttribute("tId", treeNode.getTid());
		model.addAttribute("tnId", treeNode.getTnid());
		model.addAttribute("tnXpath", treeNode.getTnxpath());
		model.addAttribute("tnLevel", treeNode.getTnlevel());
		model.addAttribute("parent", treeNode.getParent());
		model.addAttribute("treeNode", treeNode);
		model.addAttribute("nodeStatus", getNodeStatus());
		model.addAttribute("treeType", getTreeType());
		model.addAttribute("dataCollection", getDataCollection());
		
		return "dirTreeAdd";
	}
	
	/**
	 * 目錄樹編輯頁
	 * */
	@RequestMapping(value="view/dirTreePage", method=RequestMethod.GET)
	public String editPage(ModelMap model , Long tnId){
		TreeNode treeNode = treeNodeService.selectByPrimaryKey(tnId);
		List<TreeNode> hasChild = treeNodeService.selectByParent(tnId, "");
		List<CodeMain> treeType = null;
		if(hasChild.size() != 0){		
			treeType = CollectionHelper.findAll(getTreeType(), new CollectionHelper.Checker<CodeMain>() {

				@Override
				public boolean check(CodeMain obj) {
					if ("0".equals(obj.getCodevalue()) || "2".equals(obj.getCodevalue())) {
						return true;
					}
					return false;
				}
			});
		}else{
			treeType = getTreeType();
		}
		model.addAttribute("treeNode", treeNode);
		model.addAttribute("nodeStatus", getNodeStatus());
		model.addAttribute("treeType", treeType);
		model.addAttribute("dataCollection", getDataCollection());
		return "dirTreeEdit";
	}
	
	/**
	 * 目錄樹修改action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="update/dirTreeAction", method=RequestMethod.POST)
	public String update(@ModelAttribute(value="treeNode") TreeNode treeNode, BindingResult result,
			ModelMap model) throws UnsupportedEncodingException{
		validator.validate(treeNode, result);
		String msg = "";
		if(!result.hasErrors()){
			try{
				treeNodeService.updateByPrimaryKey(treeNode);
				msg = "修改成功";
			}catch(Exception ex){
				ex.printStackTrace(System.out);
				msg = "修改失敗";
			}
			String nextUrl = "/admin/app/cms/DirTreeList/list/dirTree?tId="+treeNode.getTid();
			return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		}
		
		
		model.addAttribute("treeNode", treeNode);
		model.addAttribute("nodeStatus", getNodeStatus());
		model.addAttribute("treeType", getTreeType());
		model.addAttribute("dataCollection", getDataCollection());
		
		return "dirTreeEdit";
	}
	
	/**
	 * 目錄樹刪除action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="delete/dirTreeAction", method=RequestMethod.POST)
	public String delete(@ModelAttribute(value="treeNode") TreeNode treeNode, BindingResult result) throws UnsupportedEncodingException{
		String msg = "";
		try{
			treeNodeService.deleteByPrimaryKey(treeNode.getTnid());
			msg = "刪除成功";

		}catch(Exception ex){
			ex.printStackTrace(System.out);
			msg = "刪除失敗";
		}
		
		String nextUrl = "/admin/app/cms/DirTreeList/list/dirTree?tId="+treeNode.getTid();
		return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
		
	}

}
