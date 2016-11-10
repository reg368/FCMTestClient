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
		System.out.println(p.opt("statDate"));
		List<String> csv = new ArrayList<String>();
		csv.add("條件,統計截止日期：" + DateUtil.convert("yyyy/MM/dd", DateUtil.to_date(p.optString("statDate")))
				+ ",,,,,,,,");
		csv.add(",商品超賣量：" + p.optString("@allow") + ",,,,,,,,");
		web.put("$csv", csv);
		JOWPObject wp2 = new JOWPObject(web, "rpt_barcode_area", "export");
		JOFunctional.exec("docs.wp_csv", wp2);
		out.clear();
		//out = pageContext.pushBody();
		//os = response.getOutputStream();
		String fname = URLEncoder.encode("零售品項進銷總量異常統.csv", "UTF-8");
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

