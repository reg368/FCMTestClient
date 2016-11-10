package hyweb.util;

import hyweb.gip.dao.service.TreeNodeService;
import hyweb.gip.pojo.mybatis.table.TreeNode;

public class TreeNodeXPath {
	private TreeNodeService treeNodeService =
			(TreeNodeService)SpringLifeCycle.getBean("TreeNodeServiceImpl");
	public String getXPathUrl(Long tnid,String mpSite){
		TreeNode treeNode =  treeNodeService.selectByPrimaryKey(tnid);
		String subTnType = treeNode.getTntype();
		String subXPath = "";
		if("0".equals(subTnType)){
			subXPath =  "<a href='"+"category-"+mpSite+"-"+treeNode.getTnid()+"'>" +treeNode.getTnname()+"</a>";
		}
		else if("1".equals(subTnType)){
			subXPath = "<a href='"+"list-"+mpSite+"-"+treeNode.getTnid()+"'>" +treeNode.getTnname()+"</a>";
		}
		else if("3".equals(subTnType)){
			subXPath = "<a href='"+"list-"+mpSite+"-"+treeNode.getTnid()+"'>" +treeNode.getTnname()+"</a>";
		}
		else{
			String tnUrl = treeNode.getTnurl();
			if(tnUrl.indexOf("http")!=-1)
				subXPath = "<a href='"+treeNode.getTnurl()+"' target='_blank'>" +treeNode.getTnname()+"</a>";
			else
				subXPath = "<a href='"+treeNode.getTnurl()+"'>" +treeNode.getTnname()+"</a>";
		}
		
		
		String tnxpath[] = treeNode.getTnxpath().replace("-", "").split("\\.");
		String xPath = "";
		TreeNode tn = null;
		
		
		//父節點
		for(int i=0;i<tnxpath.length-1;i++){
			tn = treeNodeService.selectByPrimaryKey(Long.parseLong(tnxpath[i]));
			String tnType = tn.getTntype();
			if("0".equals(tnType)){
				xPath = xPath +"<a href='"+"category-"+mpSite+"-"+tn.getTnid()+"'>" +tn.getTnname()+"</a>.";
			}
			else if("1".equals(tnType)){
				xPath = xPath +"<a href='"+"list-"+mpSite+"-"+tn.getTnid()+"'>" +tn.getTnname()+"</a>.";
			}
			else if("3".equals(tnType)){
				xPath = xPath +"<a href='"+"list-"+mpSite+"-"+tn.getTnid()+"'>" +tn.getTnname()+"</a>.";
			}
			else{
				String tnUrl = treeNode.getTnurl();
				if(tnUrl.indexOf("http")!=-1)
					xPath = xPath +"<a href='"+treeNode.getTnurl()+"' target='_blank'>" +tn.getTnname()+"</a>.";
				else
					xPath = xPath +"<a href='"+treeNode.getTnurl()+"'>" +tn.getTnname()+"</a>.";
			}
		}
		
		return xPath + subXPath;
	}
	public String getXPath(Long tnid){
		TreeNode treeNode =  treeNodeService.selectByPrimaryKey(tnid);
		String subXPath = treeNode.getTnname();
			
		String tnxpath[] = treeNode.getTnxpath().replace("-", "").split("\\.");
		String xPath = "";
		TreeNode tn = null;
		
		
		//父節點
		for(int i=0;i<tnxpath.length-1;i++){
			tn = treeNodeService.selectByPrimaryKey(Long.parseLong(tnxpath[i]));
			xPath = xPath + tn.getTnname()+".";
		}
		
		return xPath + subXPath;
	}
}
