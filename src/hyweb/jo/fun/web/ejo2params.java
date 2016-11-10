package hyweb.jo.fun.web;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;

/**
 * @author william
 */
public class ejo2params implements IJOFunction<Boolean, JOProcObject> {

    public Boolean exec(JOProcObject proc) throws Exception {
        JSONObject wp = proc.params();
        if(wp.has("ejo")){
            JSONObject ejo = JOTools.decode_jo(wp.optString("ejo"));
            JSONArray names = ejo.names();
            for(int i=0;i<names.length();i++){
                String n = names.optString(i);
                if(!wp.has(n)){
                    wp.put(n, ejo.opt(n));
                }
            }
           wp.remove("ejo");
        }
        return true;
    }
}
