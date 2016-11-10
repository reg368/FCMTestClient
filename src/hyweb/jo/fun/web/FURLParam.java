/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.web;

import hyweb.jo.IJOFunction;

/**
 * @author william
 */

public class FURLParam implements IJOFunction<String, Object[]> {

    public String exec(Object ... args) throws Exception {
        String path = (String) args[0];
        String link = (String) args[1];
        String q = (String) args[2];

        String url = "";
        if (link != null) {
            if (link.startsWith("http")) {
                url = link;
                //pageContext.getOut().print(uri);
            } else {
                if ("#".equals(link) || link.length() == 0) {
                    url = "#";
                } else {
                    url = path + link;
                }
            }
        } else {
            url = path;
        }
        if (q != null) {
            if (url.contains("?")) {
                url = url + "&" + q;
            } else {
                url = url + "?" + q;
            }
        }
        return url;
    }

}
