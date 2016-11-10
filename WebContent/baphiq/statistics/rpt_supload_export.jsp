<%@page import="com.hyweb.docs.csv.CSVReport"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Map"%>
<%@page import="java.io.OutputStream"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hyweb.jo.model.JOWPObject"%>
<%@page import="hyweb.jo.util.JOFunctional"%>
<%@page import="hyweb.jo.web.JOWebObject"%>
<%
	String funId = "0020160204";
	JOWebObject web = new JOWebObject("/prj/baphiq", pageContext);
	OutputStream os = null;
	try {
		if (!check_grant(web)) {
			return;
		}
		init_params(web);
		JOWPObject wp = new JOWPObject(web, "rpt_supload", "export");
		Map<String, Object> m = (Map<String, Object>) JOFunctional.exec("docs.wp_excel_exp", wp);
		out.clear();
		out = pageContext.pushBody();
		os = response.getOutputStream();
		String fname = URLEncoder.encode("report.csv", "UTF-8");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fname + "\"");
		new CSVReport().exec(m, os);
	} finally {
		if (os != null) {
			os.flush();
			os.close();
		}
		web.release();
	}
%>