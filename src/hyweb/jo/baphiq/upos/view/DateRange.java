package hyweb.jo.baphiq.upos.view;

import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.DateUtil;
import java.util.Calendar;
import java.util.Date;


/**
 * @author william
 */
public class DateRange {

    public static String seasonFormat(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR) - 1911;
        int m = cal.get(Calendar.MONTH) + 1;
        String season = "";
        switch (m) {
            case 1:
            case 2:
            case 3:
                season = "第一季";
                break;
            case 4:
            case 5:
            case 6:
                season = "第二季";
                break;
            case 7:
            case 8:
            case 9:
                season = "第三季";
                break;
            case 10:
            case 11:
            case 12:
                season = "第四季";
                break;
        }
        return year + "年" + season;
    }

    public static String month2Format(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR) - 1911;
        int m = cal.get(Calendar.MONTH) + 1;
        String m2 = "";
        switch (m) {
            case 1:
            case 2:
                m2 = "1,2月";
                break;
            case 3:
            case 4:
                m2 = "3,4月";
                break;
            case 5:
            case 6:
                m2 = "5,6月";
                break;
            case 7:
            case 8:
                m2 = "7,8月";
                break;
            case 9:
            case 10:
                m2 = "9,10月";
                break;
            case 11:
            case 12:
                m2 = "11,12月";
                break;
        }
        return year + "年" + m2;
    }

    //  for test 
    public static JSONObject getSeason(String beginDate, Date currDate) {
        JSONObject ret = new JSONObject();
        JSONArray arr = new JSONArray();
        Date start = DateUtil.to_date(beginDate);
        Date d1 = DateUtil.getFirstDayOfSeason(currDate, -2);
        Date d2 = DateUtil.getFirstDayOfSeason(currDate, -1);
        Date d3 = DateUtil.getFirstDayOfSeason(currDate, 0);
        Date d4 = DateUtil.getFirstDayOfSeason(currDate, 1);
        ret.put("limited", DateUtil.monthOfDate(d4, 16)); // 規定15日前
        vaild_season(arr, start, d1, d2);
        vaild_season(arr, start, d2, d3);
        vaild_season(arr, start, d3, d4);
        ret.put("rows", arr);
        return ret;
    }

    public static JSONObject getSeason(String beginDate) {
        return getSeason(beginDate, new Date());
    }

    private static void vaild_season(JSONArray arr, Date start, Date d1, Date d2) {
        if (start.compareTo(d1) <= 0) {
            JSONObject row = new JSONObject();
            row.put("label", seasonFormat(d1));
            row.put("ds", d1);
            row.put("de", DateUtil.offset(Calendar.DATE, d2, -1));
            row.put("limited", DateUtil.monthOfDate(d2,16));
            arr.add(row);
        }
    }

    //  for test 
    public static JSONObject getMonth2(String beginDate, Date currDate) {
        JSONObject ret = new JSONObject();
        JSONArray arr = new JSONArray();
        Date start = DateUtil.to_date(beginDate);
        Date d1 = DateUtil.getFirstDayOfMonth2(currDate, -2);
        Date d2 = DateUtil.getFirstDayOfMonth2(currDate, -1);
        Date d3 = DateUtil.getFirstDayOfMonth2(currDate, 0);
        Date d4 = DateUtil.getFirstDayOfMonth2(currDate, 1);
        vaild_month2(arr, start, d1, d2);
        vaild_month2(arr, start, d2, d3);
        vaild_month2(arr, start, d3, d4);
        ret.put("rows", arr);
        return ret;
    }

    public static JSONObject getMonth2(String beginDate) {
        return getMonth2(beginDate, new Date());
    }

    private static void vaild_month2(JSONArray arr, Date start, Date d1, Date d2) {
        if (start.compareTo(d1) <= 0) {
            JSONObject row = new JSONObject();
            row.put("label", month2Format(d1));
            row.put("ds", d1);
            row.put("de", DateUtil.offset(Calendar.DATE, d2, -1));
            row.put("limited", DateUtil.monthOfDate(d2, 16));
            arr.add(row);
        }
    }

    public static void main(String[] args) {
        Date curr = new Date();
        /**
         * for (int i = 0; i < 10; i++) {
         * System.out.println(DateUtil.convert("yyyy/MM/dd", curr)); JSONObject
         * ret = getSeason("20160401", curr);
         * System.out.println(seasonFormat(curr));
         * System.out.println(ret.toString(4)); curr =
         * DateUtil.offset(Calendar.MONTH, curr, 1);
        }
         */
        curr = new Date();
        for (int i = 0; i < 10; i++) {
            System.out.println(DateUtil.convert("yyyy/MM/dd", curr));
            JSONObject ret = getMonth2("20160501", curr);
            System.out.println(month2Format(curr));
            System.out.println(ret.toString(4));
            curr = DateUtil.offset(Calendar.MONTH, curr, 1);
        }

    }

}
