/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.web;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.web.JOWebObject;
import java.util.Set;

/**
 * @author william
 * getServletContext().getRequestDispatcher("/WEB-INF/sample.jsp");
 */
public class dispatcher implements IJOFunction<Boolean, Object[]> {

    public Boolean exec(Object[] args) throws Exception {
        JOWebObject web = (JOWebObject) args[0];
        String url = (String) args[1];
        JSONObject wp = web.params();
        Set<String> names = wp.keySet();
        for (String n : names) {
            web.request().setAttribute(n, wp.opt(n));
        }
        web.application().getRequestDispatcher(url).forward(web.request(), web.response());
        return true;
    }

}
