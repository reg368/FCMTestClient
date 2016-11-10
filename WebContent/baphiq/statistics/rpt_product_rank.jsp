<%@page import="java.util.Date"%>
<%@page import="hyweb.jo.util.DateUtil"%>
<%@page import="org.json.JSONArray"%>
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
    String funId = "0020160201";
    JOWebObject web = new JOWebObject("/prj/baphiq", pageContext);
    try {
     	init_params(web);
        JSONObject params = web.params();
        Map<String, Object> fp = params.m(); // reference 
        Date maxDate = new Date();
        Date minDate = DateUtil.add_date(maxDate,Calendar.YEAR, -3);
        web.request().setAttribute("maxDate",maxDate);
        web.request().setAttribute("minDate", minDate);
        params.put("numPage", params.optInt("numPage", 50));
        params.put("@allow", params.optString("@allow", "0"));
        params.put("dp", this.getMonthYearAsValue(params, "dp"));

        fp.put("pesticideName_data", getPesticideNameData(web));
        fp.put("domManufName_data", getDomManufNameData(web));
        init_condition(web);

        web.request().setAttribute("fp", fp);
        String act = params.optString("act");
        params.put("dv", params.opt("cityId"));
        JOLogger.debug("===== chk dv : " + params);
        String ht_pcity = (String) JOFunctional.exec2("ht_select", web, "pcity", params);
        web.request().setAttribute("ht_pcity", ht_pcity);
        if ("query".equals(act)) {
            JOWPObject wp = new JOWPObject(web, "rpt_product_rank", "query");
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("wp_page", wp);
            System.out.println(wp.mq());
            System.out.println(rows);
            request.setAttribute("rows", rows);
            JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/stat/rpt_product_rank_view.jsp");
            return;
        } else if ("detail".equals(act)) {
            System.out.println(web.params());
            JOWPObject wp = new JOWPObject(web, "rpt_product_rank", "detail");
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("wp_page", wp);
            request.setAttribute("rows", rows);

            JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/stat/rpt_product_rank_detail.jsp");
            return;
        }

        JOFunctional.exec2("web.dispatcher", web, "/WEB-INF/views/admin/baphiq/stat/rpt_product_rank_view.jsp");
    } finally {
        web.release();
    }

%>
<%!
    private int getMonthYearAsValue(JSONObject p, String dp) {
        return p.optInt(dp + "_y", 0) * 100 + p.optInt(dp + "_m", 0);
    }

    private Object getPesticideNameData(JOWebObject web) throws Exception {
        List<JSONObject> rows = web.db().rows("select distinct pesticideName from pesticidelic_t ");
        JSONArray ret = new JSONArray();
        for (JSONObject row : rows) {
            ret.put(row.optString("pesticideName"));
        }
        return ret;
    }

    private Object getDomManufNameData(JOWebObject web) throws Exception {
        List<JSONObject> rows = web.db().rows("select distinct domManufName from pesticidelic_t ");
        JSONArray ret = new JSONArray();
        for (JSONObject row : rows) {
            ret.put(row.optString("domManufName"));
        }
        return ret;
    }

    private void init_condition(JOWebObject web) throws Exception {
        JSONObject p = web.params();
        // 處理查詢條件
        p.put("dp1", getMonthYearAsValue(p, "dp1"));
        p.put("dp2", getMonthYearAsValue(p, "dp2"));
        // 處理條件呈現
        String cityId = p.optString("cityId");
        StringBuilder sb = new StringBuilder();
        String cityIdText = (cityId.length() > 0)
          ? (String) web.db().fun("select cityName from cityset_t where cityId=? ", cityId) : "";
        sb.append(cityIdText)
          .append(p.opt("dp1_y")).append("年").append(p.opt("dp1_m")).append("月~")
          .append(p.opt("dp2_y")).append("年").append(p.opt("dp2_m")).append("月藥劑總量統計");
        p.put("cond", sb.toString());

        //   統計截止日期
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.DATE) > 16) {
            cal.set(Calendar.DATE, 16);
            p.put("statDate", cal.getTime());
        } else {
            cal.set(Calendar.DATE, 16);
            cal.add(Calendar.MONTH, -1);
            p.put("statDate", cal.getTime());
        }
    }
%>
