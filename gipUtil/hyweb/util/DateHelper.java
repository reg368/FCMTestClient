package hyweb.util;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class DateHelper {
	/**
	 * 辦理時間<br />
	 * 開始日期和結束日期為同一天的格式：2013/03/13 (三) 15:30-17:00 <br />
	 * 開始日期和結束日期不同一天的格式：2013/03/13 (三) 15:30 - 2013/03/14 (四)17:00 <br />
	 * 開始日期或結束日期為null，只顯示有值的日期，格式為：2013/03/13 (三) 15:30 <br />
	 * 如果都為null則回傳空字串
	 * @param startDate
	 * @param endDate
	 * @return 辦理時間
	 */
	public static String getProcessingTime(Date startDate, Date endDate) {
		String resultDate = "";
		if (startDate != null && endDate != null) {
			resultDate = DateHelper.getFormatDay(startDate, endDate);
		} else if (startDate != null) {
			SimpleDateFormat startFmt = DateHelper.createSimpleDateFormat();
			resultDate = startFmt.format(startDate);
		} else if (endDate != null) {
			SimpleDateFormat endFmt = DateHelper.createSimpleDateFormat();
			resultDate = endFmt.format(endDate);
		}
		return resultDate;
	}
	
	/**
	 * 報名截止<br />
	 * 格式為：2013/10/01 16:59~2013/10/11 23:59 <br />
	 * 只有開始日期，格式為：2013/10/01 16:59~額滿為止
	 * 只有結束日期，格式為：即日起~2013/01/01 16:59
	 * 如果都為null則，格式為：即日起~額滿為止
	 * @param startDate
	 * @param endDate
	 * @return 報名截止時間
	 */
	public static String getDeadline(Date startDate, Date endDate) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		String result = "";
		if (startDate != null && endDate != null) {
			result = fmt.format(startDate)+"~"+fmt.format(endDate);
		} else if (startDate != null) {
			result = fmt.format(startDate)+"~額滿為止";
		} else if (endDate != null) {
			result = "即日起~"+fmt.format(endDate);
		} else {
			result = "即日起~額滿為止";
		}
		return result;
	}
	
	/**
	 * 判斷是否為同一天
	 * @param start
	 * @param end
	 * @return if true 同一天，false則不同一天
	 */
	public static boolean equalsDay(Date startDate, Date endDate) {
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		
		if (start.get(Calendar.YEAR) == end.get(Calendar.YEAR)
				&& start.get(Calendar.DAY_OF_MONTH) == end.get(Calendar.DAY_OF_MONTH)
				&& start.get(Calendar.DATE) == end.get(Calendar.DATE)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 開始日期和結束日期為同一天的格式：2013/03/13 (三) 15:30-17:00 <br />
	 * 開始日期和結束日期不同一天的格式：2013/03/13 (三) 15:30 - 2013/03/14 (四)17:00 <br />
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getFormatDay(Date startDate, Date endDate) {
		SimpleDateFormat fmt1 = DateHelper.createSimpleDateFormat();
		
		String result = fmt1.format(startDate);
		
		if (DateHelper.equalsDay(startDate, endDate)) {
			SimpleDateFormat fmt2 = new SimpleDateFormat("HH:mm");
			result += "-"+fmt2.format(endDate);
		} else {
			result += " - "+fmt1.format(endDate);
		}
		
		return result;
	}
	
	/**
	 * ing=進行中, begin=即將開始, close=已截止
	 * @param listStartDate
	 * @param listEndDate
	 * @return
	 */
	public static String getLectureStatus(LinkedList<Date> listStartDate, LinkedList<Date> listEndDate) {
		if (listStartDate.size() == 0 && listEndDate.size() == 0) {
			return "close";
		}
		
		int ing = 0;
		int begin = 0;
		
		Date nowDate = new Date();
		Date start, end;
		for (int i = 0; i < listStartDate.size(); i++) {
			start = listStartDate.get(i);
			end = listEndDate.get(i);
			if (start != null && end != null) {
				boolean startFlag = !nowDate.before(start);
				boolean endFlag = !nowDate.after(end);
				
				if (startFlag == true && endFlag == true) {
					ing++;
				} else if (endFlag == true) {
					begin++;
				}
			} else if (start != null) {
				if (!nowDate.before(start)) {
					ing++;
				}
			} else if (end != null) {
				if (!nowDate.after(end)) {
					ing++;
				}
			} else {
				ing++;
			}
		}
		
		if (ing > 0) {
			return "ing";
		} else if (begin > 0) {
			return "begin";
		} else {
			return "close";
		}
	}
	
	/**
	 * 建立一個中文星期的SimpleDateFormat
	 * @return
	 */
	private static SimpleDateFormat createSimpleDateFormat() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd (E) HH:mm");
		DateFormatSymbols symbols = fmt.getDateFormatSymbols();
		symbols.setShortWeekdays(new String[] {"", "日", "一", "二", "三", "四", "五", "六"});
		fmt.setDateFormatSymbols(symbols);
		
		return fmt;
	}
}
