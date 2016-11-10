package hyweb.jo.fun.web;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.web.JOWebObject;
import java.util.Set;

/**
 * @author William
 *     request     "url"     =  application "$cp/xxx/url" 
 * 
 */

public class forward implements IJOFunction<Boolean, Object[]> {

    public Boolean exec(Object[] args) throws Exception {
        JOWebObject web = (JOWebObject) args[0];
        String url = (String) args[1];
        JSONObject wp = web.params();
        Set<String> names =  wp.keySet();
        for(String n : names){
            web.request().setAttribute(n, wp.opt(n));
        }
        web.request().getRequestDispatcher(url).forward(web.request(), web.response());
        return true ;
    }
    
}
