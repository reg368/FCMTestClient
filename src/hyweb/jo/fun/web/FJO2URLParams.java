package hyweb.jo.fun.web;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONObject;
import java.net.URLEncoder;

public class FJO2URLParams implements IJOFunction<String, Object[]> {

    @Override
    public String exec(Object[] args) throws Exception {
        JSONObject p = (JSONObject) args[0];
        String[] items = (args.length > 1) ? ((String) args[1]).split(",") : null;
        boolean flag = (Boolean) (args.length > 2 ? args[2] : true);
        return exec(p, flag, items);
    }

    public String exec(JSONObject p, boolean flag, String... items) throws Exception {
        if (items != null) {
            StringBuilder sb = new StringBuilder();
            if (flag) {
                sb.append("?");
            } else {
                sb.append("&");
            }
            int idx = 0;
            for (String arg : items) {
                if (p.has(arg)) {
                    sb.append(arg);
                    sb.append('=').append(URLEncoder.encode(p.optString(arg), "utf8"));
                    sb.append("&");
                    idx++;
                }
            }
            if (idx > 0) {
                sb.setLength(sb.length() - 1);
            }
            return sb.toString();
        }
        return "";
    }

}
