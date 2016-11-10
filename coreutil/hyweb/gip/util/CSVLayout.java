package hyweb.gip.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

public class CSVLayout extends Layout {

	@Override
	public void activateOptions() {
		
	}

	@Override
	public String format(LoggingEvent event) {
		//SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd");
		String hDate = sdf.format(new Date(event.getTimeStamp()));
		StringBuilder sb = new StringBuilder();
		// SID  , hDate , hURL , hLocation , hSource
		sb.append("1,") ;		// sb.append(SID) 
		sb.append(hDate).append(',') ;
		sb.append(event.getMessage()).append("\r\n");
		return sb.toString();
	}

	@Override
	public boolean ignoresThrowable() {
		return false;
	}

}
