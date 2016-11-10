<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hyweb.jo.log.JOLogger"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="hyweb.jo.util.DateUtil"%>
<%@page import="hyweb.jo.util.TextUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page import="hyweb.jo.model.JOWPObject"%>
<%@page import="hyweb.jo.org.json.JSONObject"%>
<%@page import="hyweb.jo.util.JOFunctional"%>
<%@page import="hyweb.jo.web.JOWebObject"%>
<%@include file="/baphiq/include/rpt_params.jsp"%>
<%
    String funId = "0020160203";
    JOWebObject web = new JOWebObject("/prj/baphiq", pageContext);
    try {
 		init_params(web);
        // -- default   
        JSONObject params = web.params();
        Map<String, Object> fp = params;
        Date now = new Date();
        Date dmin = DateUtil.firstDayOfMonth(DateUtil.add_date(now, Calendar.MONTH, -13));
        Date dmax = DateUtil.lastDayOfMonth(DateUtil.add_date(now, Calendar.MONTH, -1));
        String minDate = new SimpleDateFormat("yyyy/MM/dd").format(dmin);
        String maxDate = new SimpleDateFormat("yyyy/MM/dd").format(dmax);
        web.request().setAttribute("maxDate", maxDate);
        web.request().setAttribute("minDate", minDate);
        params.put("numPage", params.optInt("numPage", 15));
        web.request().setAttribute("fp", fp);
        String act = params.optString("act");
        params.put("dv", params.opt("cityId"));
        String ht_pcity = (String) JOFunctional.exec2("ht_select", web, "pcity", params);
        web.request().setAttribute("ht_pcity", ht_pcity);
        if ("query".equals(act)) {
            JOWPObject wp = new JOWPObject(web, "rpt_supload", "query");
            // 日期條件
            String sdp1 = params.optString("dp1");
            sdp1 = (sdp1.length() == 10) ? sdp1 : minDate;
            params.put("sdp1", sdp1.replaceAll("/", "-"));
            String sdp2 = params.optString("dp2");
            sdp2 = (sdp2.length() == 10) ? sdp2 : maxDate;
            sdp2 = DateUtil.date_next("yyyy/MM/dd", sdp2);
            params.put("sdp2", sdp2.replaceAll("/", "-"));
             //  上傳類型
            JOLogger.debug(wp.p().toString(4));
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("wp_page", wp);
            web.request().setAttribute("rows", rows);
            JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/stat/rpt_supload_view.jsp");
            return;
        }

        JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/stat/rpt_supload_view.jsp");
    } finally {
        web.release();
    }

%>