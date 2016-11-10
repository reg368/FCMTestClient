package hyweb.gip.ctr.cms;

import hyweb.core.kit.NumberKit;
import hyweb.core.kit.StringKit;
import hyweb.gip.dao.service.TreeNodeService;
import hyweb.gip.dao.service.TreeService;
import hyweb.gip.pojo.mybatis.table.Tree;
import hyweb.gip.pojo.mybatis.table.TreeNode;
import hyweb.util.SpringLifeCycle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/cms/BuildTree")
public class CmsTree {
	public static final boolean DEBUG = false;
	public static final String SHOW_CHILD = "showChild";
	public static final String SHOW_VIEW = "showView";
	
	private TreeNodeService treeNodeService = 
			(TreeNodeService)SpringLifeCycle.getBean("TreeNodeServiceImpl");
	
	private TreeService treeService = 
			(TreeService)SpringLifeCycle.getBean("TreeServiceImpl");
	
	/**
	 * 產生樹節點
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * */
	@RequestMapping(value = "tree", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public List<Object> treeNode(
			String root, String tIds, 
			String closedNode, String hiddenNode, 
			String json, int level, boolean ajax) throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		
		List<Object> list = new ArrayList<Object>();
		
		boolean isParent = true;
		if (!StringKit.isBlank(root)) {
			isParent = false;
			
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put("id", "0");
			rootMap.put("pId", "x");
			rootMap.put("name", root);
			rootMap.put("click", "javascript:void(0);");
			rootMap.put("open", true);
			
			list.add(rootMap);
		}
		
		String[] tIdsVal = null;
		if (tIds.indexOf(',') == -1) {
			tIdsVal = new String[]{tIds};
		} else {
			tIdsVal = tIds.split(",");
		}

		List<Tree> trees = treeService.selectByPrimaryKeyList(tIdsVal);
		
		for (Tree tree : trees) {
			List<String> closeNodes = new ArrayList<String>();
			List<String> hideNodes = new ArrayList<String>();
			
			List<TreeNode> nodes = treeNodeService.selectByTid(tree.getTid());
			JSONObject jsonValue = new JSONObject(json);
			String[] NP = {"NP", ""}, LP = {"LP", ""}, AP = {"AP", ""}, SP = {"SP", ""};
			String[][] tnType = {NP, LP, AP, SP};
			for (int i = 0; i < tnType.length; i++) {
				JSONObject obj = jsonValue.getJSONObject(tnType[i][0]);
				if (obj != null) {
					String picUrl = obj.getString("picUrl");
					tnType[i][1] = picUrl;
				}
			}
			
			Map<String, String> breadcrumbs = new HashMap<String, String>();
			breadcrumbs.put("tId-"+tree.getTid(), tree.getTname());
			
			Map<String, Object> mpMap = new HashMap<String, Object>();
			mpMap.put("id", "tId-"+tree.getTid());
			mpMap.put("pId", "0");
			mpMap.put("name", tree.getTname());
			mpMap.put("click", createClickNodeMethod("MP", Long.toString(tree.getTid()), "tId-"+tree.getTid(), null, breadcrumbs));
			
			mpMap.put("iconOpen", "/admin/app/css/zTreeStyle/img/diy/1_open.png");
			mpMap.put("iconClose", "/admin/app/css/zTreeStyle/img/diy/1_close.png");
			mpMap.put("isParent", isParent);
			
			if (level >= 1) {
				mpMap.put("open", true);
			}
			list.add(mpMap);
			
			JSONObject jsonClosed = new JSONObject(closedNode);
			JSONObject jsonHidden = new JSONObject(hiddenNode);
			
			for (TreeNode node : nodes) {
				breadcrumbs.put(Long.toString(node.getTnid()), node.getTnname());
				
				if ("0".equals(node.getTnispublic())) {
					closeNodes.add(Long.toString(node.getTnid()));
				} else if ("2".equals(node.getTnispublic())) {
					hideNodes.add(Long.toString(node.getTnid()));
				}
			}
			
			Map<String, Object> childMap = null;
			for (TreeNode node : nodes) {
				if (ajax) {
					if (node.getTnlevel() > level) {
						continue;
					}
				}
				
				int status = checkParentStatus(closeNodes, hideNodes, node);
				
				if ((("0".equals(node.getTnispublic()) || status == 0) && jsonClosed.getBoolean(SHOW_CHILD)) || 
					(("2".equals(node.getTnispublic()) || status == 2) && jsonHidden.getBoolean(SHOW_CHILD)) || 
					("1".equals(node.getTnispublic())  && status == -1)) {
					
					if (node.getTnlevel() == 1) {
						childMap = createMap(node, level, "tId-"+tree.getTid(), tnType, jsonClosed, jsonHidden, breadcrumbs);
					} else {
						childMap = createMap(node, level, Long.toString(node.getParent()), tnType, jsonClosed, jsonHidden, breadcrumbs);
					}
			
					list.add(childMap);
				}
			}
		}
		
		if (DEBUG) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(System.out, list);
		}
		
		return list;
	}
	
	/**
	 * 產生樹節點
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * */
	@RequestMapping(value = "tree/ajax", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
	@ResponseBody
	public List<Map<String, Object>> treeNodeAjax(String id, String name, String level, String closedNode, String hiddenNode, String json) throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		List<Map<String, Object>> listNode = new ArrayList<Map<String, Object>>();
		
		if (NumberKit.isDigits(id) == false) {
			return null;
		}
		
		JSONObject jsonValue = new JSONObject(json);
		String[] NP = {"NP", ""}, LP = {"LP", ""}, AP = {"AP", ""}, SP = {"SP", ""};
		String[][] tnType = {NP, LP, AP, SP};
		for (int i = 0; i < tnType.length; i++) {
			JSONObject obj = jsonValue.getJSONObject(tnType[i][0]);
			if (obj != null) {
				String picUrl = obj.getString("picUrl");
				tnType[i][1] = picUrl;
			}
		}
		
		JSONObject jsonClosed = new JSONObject(closedNode);
		JSONObject jsonHidden = new JSONObject(hiddenNode);
		
		Map<String, String> breadcrumbs = getNoed(Long.parseLong(id));
		List<TreeNode> childs = treeNodeService.selectByParent(Long.parseLong(id),"");
		Map<String, Object> childMap = null;
		for (TreeNode child : childs) {
			if (("0".equals(child.getTnispublic()) && jsonClosed.getBoolean(SHOW_CHILD)) || 
					("2".equals(child.getTnispublic()) && jsonHidden.getBoolean(SHOW_CHILD)) || 
					"1".equals(child.getTnispublic())) {
				breadcrumbs.put(Long.toString(child.getTnid()), child.getTnname());
				
				childMap = createMap(child, null, null, tnType, jsonClosed, jsonHidden, breadcrumbs);
				
				listNode.add(childMap);
			}
		}
		
		if (DEBUG) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(System.out, listNode);
		}
		return listNode;
	}
	
	private String breadcrumbsToString(Map<String, String> breadcrumbs, String tnXpath, String tId) {
		String result = breadcrumbs.get("tId-"+tId);
		
		if (tnXpath != null) {
			String[] xpath = tnXpath.replace("-", "").split("\\.");
			for (String val : xpath) {
				result += ".";
				
				result += breadcrumbs.get(val);
			}
		}
		result = result.replace("'", "\\'");
		return result;
	}
	
	private String createClickNodeMethod(String type, String tId, String tnId, TreeNode node, Map<String, String> breadcrumbs) {
		String dataSource = "";
		String url = "";
		String xpath = null;
		if (node != null) {
			dataSource = node.getTndatasource();
			if (StringKit.isEmpty(dataSource)) {
				dataSource = "";
			}
			url = node.getTnurl();
			if (StringKit.isEmpty(url)) {
				url = "";
			}
			xpath = node.getTnxpath();
		}
		return "clickNode('"+type+"', '" + tId + "', '"+tnId+ 
				"', '"+breadcrumbsToString(breadcrumbs, xpath, tId)+"', '"+dataSource+"', '"+url+"');";
	}
	
	private Map<String, Object> createMap(TreeNode node, Integer level, String pId, String[][] tnType, JSONObject jsonClosed, JSONObject jsonHidden, Map<String, String> breadcrumbs) throws NumberFormatException, JSONException {
		Map<String, Object> childMap = new HashMap<String, Object>();
		childMap.put("id", node.getTnid());
		childMap.put("name", node.getTnname());
		childMap.put("isParent", "true");
		if (pId != null) {
			childMap.put("pId", pId);
		}
		
		if (level != null) {
			if (level > node.getTnlevel()) {
				childMap.put("open", true);
			}
		}
		
		if (("0".equals(node.getTnispublic()) && jsonClosed.getBoolean(SHOW_VIEW)) || 
			("2".equals(node.getTnispublic()) && jsonHidden.getBoolean(SHOW_VIEW)) || 
			"1".equals(node.getTnispublic())) {
			childMap.put("click", createClickNodeMethod(
						tnType[Integer.parseInt(node.getTntype())][0], 
						Long.toString(node.getTid()),
						Long.toString(node.getTnid()),
						node,
						breadcrumbs
					)
			);
			
		}
		
		if (!"".equals(tnType[Integer.parseInt(node.getTntype())][1]) && "1".equals(node.getTnispublic())) {
			childMap.put("icon", tnType[Integer.parseInt(node.getTntype())][1]);
		} else if ("0".equals(node.getTnispublic())) {
			childMap.put("icon", "/admin/app/css/zTreeStyle/img/diy/9.png");
		} else if ("2".equals(node.getTnispublic())) {
			childMap.put("icon", "/admin/app/css/zTreeStyle/img/diy/7.png");
		}
		
		return childMap;
	}
	
	private Map<String, String> getNoed(Long tnId) {
		TreeNodeService treeNodeService = 
				(TreeNodeService)SpringLifeCycle.getBean("TreeNodeServiceImpl");
		TreeService treeService = 
				(TreeService)SpringLifeCycle.getBean("TreeServiceImpl");
		
		TreeNode treeNode = treeNodeService.selectByPrimaryKey(tnId);
		String[] treeNodes = treeNode.getTnxpath().replace("-", "").split("\\.");
		List<Long> listId = new ArrayList<Long>();
		if (treeNodes != null) {
			for (int i = 0; i < treeNodes.length; i++) {
				listId.add(Long.parseLong(treeNodes[i]));
			}
		}
		listId.add(treeNode.getTnid());
		
		List<TreeNode> nodeList = new ArrayList<TreeNode>();
		if (listId.size() > 0) {
			nodeList = treeNodeService.selectInTnid(listId);
		}
		
		Map<String, String> title = new HashMap<String, String>();
		Tree root = treeService.selectByPrimaryKey(treeNode.getTid());
		title.put("tId-"+treeNode.getTid(), root.getTname());	
		for (TreeNode node : nodeList) {
			title.put(Long.toString(node.getTnid()), node.getTnname());
		}
		
		return title;
	}
	
	//確認父節點是否有關閉或隱藏, 關閉=0, 隱藏=2, 其它-1
	private int checkParentStatus(List<String> closeNodes, List<String> hideNodes, TreeNode node) {
		if (node.getTnxpath() == null) {
			return -1;
		}
		String[] xpath = node.getTnxpath().split("\\.");
		xpath[xpath.length-1] = xpath[xpath.length-1].replace("-", "");
		
		for (String val : xpath) {
			if (closeNodes.contains(val)) {
				return 0;
			} else if (hideNodes.contains(val)) {
				return 2;
			}
		}
		
		return -1;
	}
}
