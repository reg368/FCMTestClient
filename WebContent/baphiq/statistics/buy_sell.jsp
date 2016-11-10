<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hyweb.jo.util.TextUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page import="hyweb.jo.model.JOWPObject"%>
<%@page import="hyweb.jo.org.json.JSONObject"%>
<%@page import="hyweb.jo.util.JOFunctional"%>
<%@page import="hyweb.jo.web.JOWebObject"%>
<%
    String funId = "0020160202";
    JOWebObject web = new JOWebObject("/prj/baphiq", pageContext);
    try {
        if (!(Boolean) JOFunctional.exec2("baphiq.admin.VGrant", web, funId)) {
            out.println("您沒有檢視的權限!!!");
            return;
        }
        JSONObject user = (JSONObject) session.getAttribute("$user");
        request.setAttribute("dlevel1", user.optString("dlevel1"));
        request.setAttribute("dlevel1_text", user.optString("dlevel1_text"));
        JOFunctional.exec("web.ejo2params", web);
        // -- default   
        JSONObject p = web.params();
        Map<String, Object> fp = p.m();
        String minDate = TextUtils.next(Calendar.YEAR, -1, "yyyy/MM/dd");
        String maxDate = TextUtils.now("yyyy/MM/dd");
        fp.put("dp1", p.optString("dp1", minDate));
        fp.put("dp2", p.optString("dp2", maxDate));
        p.put("numPage", p.optInt("numPage",15));
        web.request().setAttribute("maxDate", maxDate);
        web.request().setAttribute("minDate", minDate);
        web.request().setAttribute("fp", fp);
        String act = p.optString("act");
        p.put("dv", p.opt("cityId"));
        String ht_pcity = (String) JOFunctional.exec2("ht_select", web, "pcity", p);
        web.request().setAttribute("ht_pcity", ht_pcity);
        if ("query".equals(act)) {
            JOWPObject wp = new JOWPObject(web, "rpt_buy_sell", "query");
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("wp_page", wp);
            web.request().setAttribute("rows", rows);
            JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/stat/buy_sell_view.jsp");
            return;
        }

        JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/stat/buy_sell_view.jsp");
    } finally {
        web.release();
    }

%>
