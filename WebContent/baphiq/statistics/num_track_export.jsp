<%@page import="java.util.Date"%>
<%@page import="hyweb.jo.util.DateUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="hyweb.jo.org.json.JSONObject"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Map"%>
<%@page import="java.io.OutputStream"%>
<%@page import="hyweb.jo.model.JOWPObject"%>
<%@page import="hyweb.jo.util.JOFunctional"%>
<%@page import="hyweb.jo.web.JOWebObject"%>
<%@include file="/baphiq/include/rpt_params.jsp"%>
<%
	String funId = "0020160201";
	JOWebObject web = new JOWebObject("/prj/baphiq", pageContext);
	OutputStream os = null;
	try {
		if (!check_grant(web)) {
			return;
		}
		init_params(web);
		JSONObject p = web.params();
		JOWPObject wp1 = new JOWPObject(web, "rpt_num_track", "query_barcode");
		JSONObject row = (JSONObject) JOFunctional.exec("wp_row", wp1);
		List<String> csv = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		//  條件,條碼(普通名稱,劑型,含量%,製藥商(統編)),,,製表日期(YYYY/MM/DD)
		sb.append("條件").append(",\"").append(p.opt("barcode")).append("(").append(row.opt("pesticideName"))
				.append(',').append(row.optString("formCode").trim()).append(',').append(row.opt("contents"))
				.append(',').append(row.opt("domManufName")).append('(').append(row.opt("domManufId"))
				.append(')').append(")\"").append(",,,")
				.append("製表日期(" + DateUtil.convert("yyyy/MM/dd", new Date()) + ")");
		csv.add(sb.toString());
		JOWPObject wp = new JOWPObject(web, "rpt_num_track", "query");
		row = ((List<JSONObject>) JOFunctional.exec("wp_rows", wp)).get(0);
		csv.add(String.format(",農藥批號(%s),,,統計截止日期(%s)", p.optString("makeid"),
				DateUtil.convert("yyyy/MM/dd", row.optDate("ct"))));
		csv.add(String.format(",製造日期(%s),,,", p.optString("makedt")));
		String cityId = p.optString("cityId");
		String cityIdText = "";
		if (cityId.length() > 0) {
			cityIdText = (String) web.db().fun("select cityName from cityset_t where cityId=?", cityId);
		}
		csv.add(String.format(",地區(%s),,,", cityIdText));

		web.put("$csv", csv);
		JOWPObject wp2 = new JOWPObject(web, "rpt_num_track", "export");
		JOFunctional.exec("docs.wp_csv", wp2);
		System.out.println(web.opt("$data"));
		out.clear();
		//out = pageContext.pushBody();
		//os = response.getOutputStream();
		String fname = URLEncoder.encode("批號追蹤統計.csv", "UTF-8");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fname + "\"");
		List<String> data = (List<String>) web.opt("$csv");
		out.write('\ufeff'); // UTF-8 
		for (String line : data) {
			out.println(line);
		}
	} finally {
		if (os != null) {
			os.flush();
			os.close();
		}
		web.release();
	}
%>