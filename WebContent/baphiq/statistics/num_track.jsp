<%@page import="hyweb.jo.fun.util.FJORMWhitespace"%>
<%@page import="hyweb.jo.util.TextUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page import="hyweb.jo.model.JOWPObject"%>
<%@page import="hyweb.jo.org.json.JSONObject"%>
<%@page import="hyweb.jo.util.JOFunctional"%>
<%@page import="hyweb.jo.web.JOWebObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/baphiq/include/rpt_params.jsp" %>
<%
	String funId = "0020160201";
	JOWebObject web = new JOWebObject("/prj/baphiq", pageContext);
	try {
		if(!check_grant(web)){	
			return ;
		}
		init_params(web);
		JSONObject params = web.params();
		Map<String, Object> fp = params.m();
		String minDate = TextUtils.next(Calendar.YEAR, -5, "yyyy/MM/dd");
		fp.put("dp1", params.optString("dp1", minDate));
		web.request().setAttribute("maxDate", TextUtils.now("yyyy/MM/dd"));
		web.request().setAttribute("minDate", minDate);
		params.put("numPage", params.optInt("numPage", 50));
		web.request().setAttribute("fp", fp);
		String act = params.optString("act");
		params.put("dv", params.opt("cityId"));
		String ht_pcity = (String) JOFunctional.exec2("ht_select", web, "pcity", params);
		web.request().setAttribute("ht_pcity", ht_pcity);
		if ("query".equals(act)) {
			JOWPObject wp1 = new JOWPObject(web, "rpt_num_track", "query");
			List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("wp_page", wp1);
			JOWPObject wp2 = new JOWPObject(web, "rpt_num_track", "query_barcode");
			wp2.reset(new FJORMWhitespace().exec(web.params()));
			JSONObject barcode = (JSONObject) JOFunctional.exec("wp_row", wp2);

			web.request().setAttribute("rows", rows);
			web.request().setAttribute("row", barcode);
			JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/stat/num_track_view.jsp");
			return;
		} else if ("detail".equals(act)) {
			JOWPObject wp = new JOWPObject(web, "rpt_num_track", "detail");
			List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("wp_page", wp);
			web.request().setAttribute("rows", rows);
			System.out.println(rows);
			JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/stat/num_track_detail.jsp");
			return;
		}

		JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/stat/num_track_view.jsp");
	} finally {
		web.release();
	}
%>
