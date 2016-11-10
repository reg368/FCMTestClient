package hyweb.gip.ctr.cms;

import hyweb.gip.dao.service.TreeNodeService;
import hyweb.gip.dao.service.TreeService;
import hyweb.gip.pojo.mybatis.table.Tree;
import hyweb.gip.pojo.mybatis.table.TreeNode;
import hyweb.gip.validator.EmptyValidator;
import hyweb.util.SpringLifeCycle;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/cms/TreeList")
public class PageTreeList {
	
	private EmptyValidator validator = null;
	private  TreeNodeService treeNodeService = 
			(TreeNodeService)SpringLifeCycle.getBean("TreeNodeServiceImpl");
	
	private  TreeService treeService = 
			(TreeService)SpringLifeCycle.getBean("TreeServiceImpl");
	
	public EmptyValidator getValidator() {
		return validator;
	}
	
	/**
	 * 產生樹節點
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * */
	public String treeNode() throws JsonGenerationException, JsonMappingException, IOException{
		Tree trees = treeService.selectByPrimaryKey(2);
		List<TreeNode> nodes = treeNodeService.selectByLevel(2, 1);
		StringBuffer treeResult = new StringBuffer();
		
		//產生樹節點
		treeResult.append("[");
		treeResult.append("{ id:\"01\", pId:0, name:\"" + trees.getTname() + "\", click:\"getEditTreePage(" + trees.getTid() + ");\", open:true, iconOpen:\"/admin/app/css/zTreeStyle/img/diy/1_open.png\", iconClose:\"/admin/app/css/zTreeStyle/img/diy/1_close.png\"},");
		for(int i = 0; i < nodes.size(); i++){
			treeResult.append("{ id:" + nodes.get(i).getTnid() + ", pId:\"01\", name:\"" + nodes.get(i).getTnname() + "\", click:\"getEditPage(" + nodes.get(i).getTnid() + ");\", isParent: \"true\"},");	
		}	
		treeResult.append("];");
		
		//System.out.println(treeResult);
		return treeResult.toString();
	}
	
	/**
	 * 目錄樹呈現頁
	 * */
	@RequestMapping(value="list/directoryManager", method=RequestMethod.GET)
	public String dirShow(ModelMap model) throws Exception{
		
		model.addAttribute("node", this.treeNode());
		return "dirTreeList";
	} 

}
