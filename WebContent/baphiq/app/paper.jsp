<%@page import="hyweb.jo.log.JOLogger"%>
<%@page import="java.util.List"%>
<%@page import="hyweb.jo.model.JOWPObject"%>
<%@page import="hyweb.jo.JOProcObject"%>
<%@page import="hyweb.jo.org.json.JSONObject"%>
<%@page import="hyweb.jo.util.JOFunctional"%>
<%@page import="hyweb.jo.web.JOWebObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init_params.jsp" %>
<%
    JOWebObject web = new JOWebObject("/prj/baphiq", pageContext);
    try {
		init_params(web);
        JSONObject params = web.params();
        String act = params.optString("act");

        request.setAttribute("fp", web.params());
        if ("query".equals(act)) {
            JOWPObject wp = new JOWPObject(web, "ps_paper", "query");
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("wp_page", wp);
            System.out.println(rows);
            request.setAttribute("rows", rows);
            JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/paper_view.jsp");
            return;
        } else if ("detail".equals(act)) {
            JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/paper_detail.jsp");
            return;
        }

        JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/paper_view.jsp");
    } finally {
        web.release();
    }
%>

