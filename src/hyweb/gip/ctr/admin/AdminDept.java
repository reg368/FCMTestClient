package hyweb.gip.ctr.admin;

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

import hyweb.gip.dao.service.DeptService;
import hyweb.gip.dao.service.SeqService;
import hyweb.gip.model.session.impl.HttpSessionHandle;
import hyweb.gip.pojo.mybatis.table.Dept;
import hyweb.gip.validator.EmptyValidator;
import hyweb.util.PageHelper;
import hyweb.util.PageJump;
import hyweb.util.PageLimits;
import hyweb.util.SessionResultHelper;
import hyweb.util.SpringLifeCycle;
import hyweb.util.UrlHelper;

@Controller
@RequestMapping(value="/admin/Dept")
public class AdminDept {
	
	private final String useAp = "Dept";
	
	private EmptyValidator validator = null;
	private  DeptService deptService = 
			(DeptService)SpringLifeCycle.getBean("DeptServiceImpl");
	private SeqService seqService = 
				(SeqService)SpringLifeCycle.getBean("SeqServiceImpl");
	
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
	public String treeNode(HttpServletRequest req){
		//登入者的單位
		Integer userDept = new HttpSessionHandle(req).getLoginUser().getDeptid();
		List<Dept> depts = deptService.selectAllByTreeAndDept(userDept);
		StringBuffer treeResult = new StringBuffer();

		//產生樹節點
		treeResult.append("[");
		for(int i = 0; i < depts.size(); i++){
			if(deptService.selectParent(depts.get(i).getDeptseq()) == null)
				treeResult.append("{ id:" + depts.get(i).getDeptseq() + ", pId:0 , name:\"" + depts.get(i).getDeptname() + "\", click:\"getEditPage(" + depts.get(i).getDeptseq() +");\"},");
			else
				treeResult.append("{ id:" + depts.get(i).getDeptseq() + ", pId:" + deptService.selectParent(depts.get(i).getDeptseq()).getDeptseq() + ", name:\"" + depts.get(i).getDeptname() + "\", click:\"getEditPage(" + depts.get(i).getDeptseq() +");\"},");
					
		}
		treeResult.append("];");
		return treeResult.toString();
	}
	/**
	 * 選擇部門頁面的樹節點
	 * */
	public String treeNodeName(HttpServletRequest req){
		//登入者的單位
		Integer userDept = new HttpSessionHandle(req).getLoginUser().getDeptid();
		List<Dept> depts = deptService.selectAllByTreeAndDept(userDept);
		StringBuffer treeResult = new StringBuffer();
		
		
		//產生樹節點
		treeResult.append("[");
		for(int i = 0; i < depts.size(); i++){
			if(deptService.selectParent(depts.get(i).getDeptseq()) == null){
				treeResult.append("{ id:" + depts.get(i).getDeptseq() + ", pId: 0, name:\"" + depts.get(i).getDeptname() 
								  + "\", click:\"getEditPage(" 
								  + depts.get(i).getDeptseq() + ",'"+ depts.get(i).getDeptname() + "');\"},");
			}
			else{
				treeResult.append("{ id:" + depts.get(i).getDeptseq() + ", pId:" + 
						deptService.selectParent(depts.get(i).getDeptseq()).getDeptseq() + 
								  ", name:\"" + depts.get(i).getDeptname() 
								  + "\", click:\"getEditPage(" 
								  + depts.get(i).getDeptseq() + ",'"+ depts.get(i).getDeptname() + "');\"},");
			}			
		}
		treeResult.append("];");
		return treeResult.toString();
	}
	/**
     * 樹/列表頁
	 * @throws UnsupportedEncodingException 
     * */
	@RequestMapping(value="list/dept", method=RequestMethod.GET)
	public String showForm(ModelMap model, HttpServletRequest req,
			@RequestParam(value = "page", required = false, defaultValue = "1") String paramPage) throws UnsupportedEncodingException{
		SessionResultHelper.saveRequestParam(req);
		int userDept = new HttpSessionHandle(req).getLoginUser().getDeptid();
		int page = PageHelper.getValue(paramPage, 1);
		int showNum = 10;
		int pageSize = 5;
		int count = deptService.countAllByTreeAndDept(userDept);
		
		PageJump pageJump = PageHelper.getPageJump(page, showNum, pageSize, count);
		
		List<Dept> depts = deptService.listByTree(page, showNum,userDept);
		model.addAttribute("depts", depts);
		model.addAttribute("node", this.treeNode(req));
		Dept dept = new Dept();
		model.addAttribute("dept", dept);
		model.addAttribute("args", UrlHelper.getQueryString(req.getParameterMap(), "page"));
		model.addAttribute("pageJump", pageJump);
		model.addAttribute("count", count);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "deptTreeList";
	}
	
	/**
	 * 新增頁
	 * */
	@RequestMapping(value="insert/deptPage", method=RequestMethod.GET)
	public String addPage(ModelMap model, HttpServletRequest req, String depthierarchycode){
		Dept dept = new Dept();
		dept.setDepthierarchycode(depthierarchycode);
		model.addAttribute("dept", dept);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "deptAdd";
	}

	/**
	 * 新增action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="insert/deptAction", method=RequestMethod.POST)
	public String add(@ModelAttribute(value="dept") Dept dept,BindingResult result,
			HttpServletRequest req, ModelMap model) throws UnsupportedEncodingException{
		String seq = seqService.getSeq("Dept").toString();
		dept.setDeptseq(Integer.parseInt(seq));
		dept.setDepthierarchycode(dept.getDepthierarchycode()+"."+seq);
		validator.validate(dept, result);
//		ModelAndView mv = new ModelAndView("deptAdd");
		String msg = "" ;
		
			if(!result.hasErrors()){
				try{
					deptService.insert(dept);
					msg = "新增成功";
				}catch(Exception ex){
					ex.printStackTrace(System.out);
					msg = "新增失敗";
				}
				String nextUrl = "/admin/app/admin/Dept/list/dept";
				return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
			}
		
		model.addAttribute("dept", dept);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "deptAdd";
	}
	
	/**
	 * 編輯頁
	 * */
	@RequestMapping(value="view/deptPage", method=RequestMethod.GET)
	public String editPage(ModelMap model , int deptSeq, HttpServletRequest req){
		Dept dept = deptService.selectByPrimaryKey(deptSeq);
		model.addAttribute("dept", dept);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "deptEdit";
	}
	
	/**
	 * 修改action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="update/deptAction", method=RequestMethod.POST)
	public String update(@ModelAttribute(value="dept") Dept dept,BindingResult result,
			HttpServletRequest req,ModelMap model) throws UnsupportedEncodingException{
		validator.validate(dept, result);
		String msg = "";
		
			if(!result.hasErrors()){
				try{
					deptService.updateByPrimaryKey(dept);
					msg = "修改成功";
				}catch(Exception ex){
					ex.printStackTrace(System.out);
					msg = "修改失敗";
				}
				String nextUrl = "/admin/app/admin/Dept/list/dept";
				return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
			}
		model.addAttribute("dept", dept);
		model.addAttribute("limits", new PageLimits(req, useAp));
		return "deptEdit";
	}
	
	/**
	 * 刪除action
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="delete/deptAction", method=RequestMethod.POST)
	public String delete(@ModelAttribute(value="dept") Dept dept,BindingResult result, 
			HttpServletRequest req) throws UnsupportedEncodingException{
		String msg = "";
		try{
			deptService.deleteByPrimaryKey(dept.getDeptseq());
			msg = "刪除成功";
		}catch(Exception ex){
			ex.printStackTrace(System.out);
			msg = "刪除失敗";
		}
		String nextUrl = "/admin/app/admin/Dept/list/dept";
		return "redirect:/app/page/PageRedirectMsg/alert?nextUrl="+ nextUrl +"&msg="+ URLEncoder.encode(msg, "UTF-8");
	}
	
	/**
	 * 選擇樹頁面
	 * */
	@RequestMapping(value="insert/deptTreePage", method=RequestMethod.GET)
	public String selectDeptTree(ModelMap model,HttpServletRequest req){
		model.addAttribute("node", this.treeNodeName(req));
		return "selectDeptTree";
	}
	
	/**
	 * 選擇樹頁面(上稿人物)
	 * */
	@RequestMapping(value="insert/deptTreePageForm", method=RequestMethod.GET)
	public String selectDeptTreeForm(ModelMap model,HttpServletRequest req){
		model.addAttribute("node", this.treeNodeName(req));
		return "selectDeptTreeForm";
	}
}	
	
