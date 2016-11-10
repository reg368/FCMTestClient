<%@page import="hyweb.jo.type.JODateType"%>
<%@page import="hyweb.jo.util.JOFunctional"%>
<%@page import="hyweb.jo.web.JOWebObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String funId = "0020160101";
    JOWebObject web = new JOWebObject("/prj/baphiq", pageContext);
    if (!(Boolean) JOFunctional.exec2("baphiq.admin.VGrant", web, funId)) {
        out.println("您沒有檢視的權限!!!");
        return;
    }
    try {
        Object baphiqId = web.params().optString("baphiqId");
        Object dt = web.params().optString("dt");
        Object chkDate = new JODateType().check( web.params().opt("chkDate"));
        System.out.println(chkDate);
        try {
            web.db().execute("insert into psPaper (baphiqId,dt,chkDate) values (?,?,?) ", baphiqId, dt, chkDate);
            out.println("紙本");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("");
        }
    } finally {
        web.release();
    }
%>