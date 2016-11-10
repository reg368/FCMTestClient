package hyweb.gip.aop;

import hyweb.gip.dao.service.ApService;
import hyweb.gip.pojo.mybatis.table.Ap;
import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.util.PageLimits;
import hyweb.util.SpringLifeCycle;

import javax.servlet.http.HttpServletRequest;

public class CheckFunc {
	
	public static void checkApFunc(InfoUser infoUser,HttpServletRequest req) {
		String[] url = req.getRequestURI().split("/");
		String useAp = url[4];
		String urlLimit = url[5];
		
		ApService apService = (ApService)SpringLifeCycle.getBean("ApServiceImpl");
		Ap ap = apService.selectApByEName(useAp);
		
		if (ap == null) {
			return;
		}
		
		PageLimits limits = new PageLimits(req, useAp);
		
		limits.checkLimit(urlLimit);
	}
}
