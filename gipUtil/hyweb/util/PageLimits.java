package hyweb.util;

import hyweb.gip.dao.service.UgrpApService;
import hyweb.gip.exception.LimitsException;
import hyweb.gip.model.session.impl.HttpSessionHandle;
import hyweb.gip.pojo.custom.UgrpApQuery;
import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.gip.pojo.mybatis.table.UgrpAp;

import javax.servlet.http.HttpServletRequest;

public class PageLimits {
	private boolean[] limits = new boolean[8];

	public PageLimits(HttpServletRequest request, String useAp) {
		InfoUser infoUser = new HttpSessionHandle(request).getLoginUser();
		String user = infoUser.getUserid();
		UgrpApService ugrpApService = (UgrpApService) SpringLifeCycle
				.getBean("UgrpApServiceImpl");
		UgrpApQuery ugrpApQuery = new UgrpApQuery();
		ugrpApQuery.setUserid(user);
		ugrpApQuery.setApurl(useAp);
		UgrpAp ugrpAp = ugrpApService.selectUgrpApByUgrpId(ugrpApQuery);
		Integer func = ugrpAp.getApfunc();

		System.out.println("func = " + func);
		if (func == null) {
			return;
		}

		for (int i = 0; i < limits.length; i++) {
			if ((func & (1 << i)) > 0) {
				limits[i] = true;
			}
		}
	}

	public boolean getList() {
		return limits[0];
	}

	public boolean getView() {
		return limits[1];
	}

	public boolean getAdd() {
		return limits[2];
	}

	public boolean getEdit() {
		return limits[3];
	}

	public boolean getDelete() {
		return limits[4];
	}

	public boolean getQuery() {
		return limits[5];
	}

	public boolean getPrint() {
		return limits[6];
	}

	public boolean getExport() {
		return limits[7];
	}

	public void checkLimit(String limit) {
		String msg = "";
		boolean result = false;

		if ("list".equals(limit)) {
			msg = "[條列]";
			result = getList();
		} else if ("view".equals(limit)) {
			msg = "[檢視]";
			result = getView();
		} else if ("add".equals(limit) || "insert".equals(limit)) {
			msg = "[新增]";
			result = getAdd();
		} else if ("edit".equals(limit) || "update".equals(limit)) {
			msg = "[修改]";
			result = getEdit();
		} else if ("delete".equals(limit)) {
			msg = "[刪除]";
			result = getDelete();
		} else if ("query".equals(limit)) {
			msg = "[查詢]";
			result = getQuery();
		} else if ("print".equals(limit)) {
			msg = "[列印]";
			result = getPrint();
		} else if ("export".equals(limit)) {
			msg = "[匯出]";
			result = getExport();
		} else {
			result = true;

		}

		if (result == false) {
			throw new LimitsException("你沒有" + msg + "權限！");
		}
	}
}
