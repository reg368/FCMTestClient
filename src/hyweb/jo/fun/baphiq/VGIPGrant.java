package hyweb.jo.fun.baphiq;

import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;

public class VGIPGrant implements IJOFunction<Boolean, JOProcObject> {
	private String sql = "select a.cityId , a.cityName from cityset_t a left join BaphiqDept b on (a.cityName = b.deptName) where deptSeq=?";

	@Override
	public Boolean exec(JOProcObject proc) throws Exception {
		// 配合GIP權限
		InfoUser user = (InfoUser) proc.get(JOProcObject.p_session, "InfoUser", null);
		if (user != null) {
			JSONObject row = proc.db().row(sql, user.getDeptid());
			System.out.println("===== chk row : " + row);
			if (row != null) {
				proc.set(JOProcObject.p_request, "dlevel1", row.optString("cityId"));
				proc.set(JOProcObject.p_request, "dlevel1_text", row.optString("cityName"));
			} else {
				proc.set(JOProcObject.p_request, "dlevel1", "");
				proc.set(JOProcObject.p_request, "dlevel1_text", "");
			}
			return true;
		}
		return false;
	}

}
