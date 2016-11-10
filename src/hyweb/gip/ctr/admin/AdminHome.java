package hyweb.gip.ctr.admin;

import hyweb.gip.dao.service.ApCatService;
import hyweb.gip.dao.service.ApService;
import hyweb.gip.dao.service.DeptService;
import hyweb.gip.model.session.impl.HttpSessionHandle;
import hyweb.gip.pojo.mybatis.table.Ap;
import hyweb.gip.pojo.mybatis.table.ApCat;
import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.util.SpringLifeCycle;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/admin/Home")
public class AdminHome {

	private ApService apService = 
			(ApService)SpringLifeCycle.getBean("ApServiceImpl");
	
	private ApCatService apCatService = 
			(ApCatService)SpringLifeCycle.getBean("ApCatServiceImpl");
	private DeptService deptService = 
			(DeptService)SpringLifeCycle.getBean("DeptServiceImpl");
	
	/**
	 * 主頁
	 * */
	@RequestMapping(method=RequestMethod.GET)
	public String mainFrame(){
		return "home";
	}
	
	/**
     * 頁首
     * */
	@RequestMapping(value="top", method={RequestMethod.GET, RequestMethod.POST})
	public String showTop(HttpServletRequest request,ModelMap model){
		return "top";
	}
	
	@RequestMapping(value="left", method={RequestMethod.GET, RequestMethod.POST})
	public String showLeft(HttpServletRequest request,ModelMap model){
		InfoUser infoUser = new HttpSessionHandle(request).getLoginUser();
		String userid = infoUser.getUserid();
		
		List<ApCat> apCats = apCatService.selectTopApCat(userid);
		List<Ap> aps = 
				apService.selectUseAp(userid);
		model.addAttribute("apCats", apCats);
		model.addAttribute("aps", aps);
		ApCat apCat = new ApCat();
		model.addAttribute("apCat", apCat);
		model.addAttribute("deptname", deptService.selectByPrimaryKey(infoUser.getDeptid()).getDeptname());
		return "left";
	}

}
