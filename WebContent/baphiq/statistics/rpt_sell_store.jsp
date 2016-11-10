<%@page import="hyweb.jo.util.DateUtil"%>
<%@page import="hyweb.jo.log.JOLogger"%>
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
    JOWebObject web = new JOWebObject("/prj/baphiq", pageContext);
    try {
		init_params(web);
        JSONObject params = web.params();
        Map<String, Object> fp = params.m();
        String minDate = TextUtils.next(Calendar.YEAR, -3, "yyyy/MM/dd");
        //fp.put("dp1", params.optString("dp1", minDate));
        //web.request().setAttribute("maxDate", TextUtils.now("yyyy/MM/dd"));
        web.request().setAttribute("minDate", minDate);
        params.put("numPage", params.optInt("numPage", 50));
        params.put("@allow", params.optString("@allow", "0"));
        params.put("dp", this.getMonthYearAsValue(params, "dp"));
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.DATE) > 16) {
            cal.set(Calendar.DATE, 16);
            fp.put("statDate", cal.getTime());
            params.put("statDate", cal.getTime());
            web.request().setAttribute("maxDate", DateUtil.convert("yyyy/MM/dd", cal.getTime()));
        } else {
            cal.set(Calendar.DATE, 16);
            cal.add(Calendar.MONTH, -1);
            fp.put("statDate", cal.getTime());
            params.put("statDate", cal.getTime());
            web.request().setAttribute("maxDate", DateUtil.convert("yyyy/MM/dd", cal.getTime()));
        }
        web.request().setAttribute("fp", fp);
        String act = params.optString("act");
        params.put("dv", params.opt("cityId"));
        String ht_pcity = (String) JOFunctional.exec2("ht_select", web, "pcity", params);
        web.request().setAttribute("ht_pcity", ht_pcity);
        if ("query".equals(act)) {
            JSONObject cond = web.db().row("select * from psRefStore where newId=? or oldId=?", params.opt("permit"), params.opt("permit"));
            params.put("cond", "農藥販賣業者：" + cond.optString("cName"));
            JOWPObject wp = new JOWPObject(web, "rpt_sell_store", "query");
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("wp_page", wp);
            request.setAttribute("rows", rows);
            JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/stat/rpt_sell_store_view.jsp");
            return;
        }

        JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/stat/rpt_sell_store_view.jsp");
    } finally {
        web.release();
    }

%>
<%!
    private int getMonthYearAsValue(JSONObject p, String dp) {
        return p.optInt(dp + "_y", 0) * 100 + p.optInt(dp + "_m", 0);
    }

%>
