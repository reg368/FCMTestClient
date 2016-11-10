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
		System.out.println(p.opt("statDate"));
		List<String> csv = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		sb.append(p.opt("cond")).append(",,,,,,,,,").append("製表日期(")
				.append(DateUtil.convert("yyyy/MM/dd", new Date())).append(")");
		sb.append("\r\n");
		sb.append(p.opt("dp1")).append("~").append(p.opt("dp2"));
		csv.add(sb.toString());
		web.put("$csv", csv);
		JOWPObject wp2 = new JOWPObject(web, "rpt_sell_store", "export");
		JOFunctional.exec("docs.wp_csv", wp2);
		out.clear();
		//out = pageContext.pushBody();
		//os = response.getOutputStream();
		String fname = URLEncoder.encode("零售店銷售紀錄.csv", "UTF-8");
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