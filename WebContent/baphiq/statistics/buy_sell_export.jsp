<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Map"%>
<%@page import="com.hyweb.docs.excel.HSSFReport"%>
<%@page import="java.io.OutputStream"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="hyweb.jo.model.JOWPObject"%>
<%@page import="hyweb.jo.baphiq.Grant"%>
<%@page import="hyweb.jo.util.JOFunctional"%>
<%@page import="hyweb.jo.web.JOWebObject"%>
<%
    String funId = "0020160201";
    JOWebObject web = new JOWebObject("/prj/baphiq", pageContext);
    OutputStream os = null;
    try {
        if (!(Boolean) JOFunctional.exec2("baphiq.admin.VGrant", web, funId)) {
            out.println("您沒有檢視的權限!!!");
            return;
        }
        Grant grant = (Grant) web.request().getAttribute("$grant");
        if (grant.isExport()) {
            JOFunctional.exec("web.ejo2params", web);
            System.out.println(web.params());
            JOWPObject wp = new JOWPObject(web, "rpt_buy_sell", "export");
            Map<String, Object> m = (Map<String, Object>) JOFunctional.exec("docs.wp_excel_exp", wp);
            String dp1 = web.params().optString("dp1");
            String dp2 = web.params().optString("dp2");
            m.put("query", String.format("銷售日期區間：%s~%s", dp1, dp2));
            out.clear();
            out = pageContext.pushBody();
            os = response.getOutputStream();
            String fname = URLEncoder.encode("report.xls", "UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fname + "\"");
            new HSSFReport().exec(m, os);
        }

    } finally {
        if (os != null) {
            os.flush();
            os.close();
        }
        web.release();
    }
%>