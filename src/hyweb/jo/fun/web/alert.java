package hyweb.jo.fun.web;

import hyweb.jo.IJOFunction;
import hyweb.jo.util.JOTools;
import hyweb.jo.JOProcConst;
import hyweb.jo.web.JOWebObject;
import javax.servlet.jsp.PageContext;

/**
 * @author william java script 提示訊息導頁
 */
public class alert implements IJOFunction<Boolean, Object[]> {

    public Boolean exec(Object[] args) throws Exception {
        JOWebObject web = (JOWebObject) args[0];
        String url = (String) args[1];
        String msg = (String) args[2];
        String query = (args.length > 3) ? (String) args[3] : null;
        String url_string = web.request().getContextPath() + url;
        String mid = web.params().optString("mid", "0");
        if ("all".equals(query)) {
            url_string = url_string + "?mid=" + mid + "&ejo=" + JOTools.encode(web.params().toString());
        } else if ("mid".equals(query)) {
            url_string = url_string + "?mid=" + mid;
        } else if ("ejo".equals(query)) {
            url_string = url_string + "?ejo=" + JOTools.encode(web.params().toString());
        }

        PageContext pg = (PageContext) web.opt(JOProcConst.w_pg);
        if (pg != null) {
            pg.getOut().println("<script type='text/javascript'>");
            if (msg != null) {
                pg.getOut().println("alert('" + msg + "');");
            }
            pg.getOut().println("window.location='" + url_string + "';");
            pg.getOut().println("</script>");
        } else {
            web.response().setCharacterEncoding("UTF-8");
            web.response().setContentType("text/html; charset=UTF-8");
            web.response().getWriter().println("<script type='text/javascript'>");
            if (msg != null) {
                web.response().getWriter().println("alert('" + msg + "');");
            }
            web.response().getWriter().println("window.location='" + url_string + "';");
            web.response().getWriter().println("</script>");
        }
        return true;
    }

}
