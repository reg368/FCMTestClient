/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.web;

import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.web.JOWebObject;

/**
 * @author william response redirect 目標頁面
 */
public class redirect implements IJOFunction<Boolean, Object[]> {

    public Boolean exec(Object[] args) throws Exception {
        JOWebObject web = (JOWebObject) args[0];
        String url = (String) args[1];
        String query = (args.length > 2) ? (String) args[2] : "";
        String url_string = url + JOFunctional.exec2("web.FJO2URLParams",web.params(), query);
        JOLogger.debug("===== chk proc_reedirect : " + url_string);
        web.response().sendRedirect(url_string);
        return true;
    }

}
