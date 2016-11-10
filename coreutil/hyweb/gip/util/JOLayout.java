package hyweb.gip.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONWriter;

public class JOLayout extends Layout {

    private String[] mdcKeys = new String[0];

    public JOLayout() {
  
    }

    @Override
    public String format(LoggingEvent event) {
        try {     	
            StringWriter sw = new StringWriter();
            JSONWriter jw = new JSONWriter(sw);
            jw.object();
            writeBasicFields(event, jw);
            sw.append(event.getMessage().toString());
            writeMDCValues(event, jw);
            writeThrowableEvents(event, jw);
            writeNDCValues(event, jw);
            jw.endObject();
            sw.flush();
            sw.close();
            sw.append("\n");
            return sw.toString();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return "";
    }

    private void writeBasicFields(LoggingEvent event, JSONWriter jw) throws IOException {
    	jw.key("logger").value(event.getLoggerName());
    	jw.key("level").value(event.getLevel());
    	jw.key("timestamp").value(event.getTimeStamp());
    	jw.key("threadName").value(event.getThreadName());
    }

    private void writeNDCValues(LoggingEvent event,  JSONWriter jw) throws IOException {
        if (event.getNDC() != null) {
        	jw.key("NDC").value(event.getNDC());
        }
    }

    private void writeThrowableEvents(LoggingEvent event,  JSONWriter jw) throws IOException {
        String throwableString;
        String[] throwableStrRep = event.getThrowableStrRep();
        throwableString = "";
        if (throwableStrRep != null) {
            for (String s : throwableStrRep) {
                throwableString += s + "\n";
            }
        }
        if (throwableString.length() > 0) {
        	jw.key("throwable").value(throwableString);
        }
    }

    private void writeMDCValues(LoggingEvent event,  JSONWriter jw) throws IOException {
        if (mdcKeys.length > 0) {
            event.getMDCCopy();
            jw.key("MDC").object();
            for (String s : mdcKeys) {
                Object mdc = event.getMDC(s);
                if (mdc != null) {
                	jw.key(s).value( mdc.toString());
                }
            }
            jw.endObject();
        }
    }

    public String[] getMdcKeys() {
        return Arrays.copyOf(mdcKeys, mdcKeys.length);
    }

    public void setMdcKeysToUse(String mdcKeystoUse){
        if (mdcKeystoUse!=null && mdcKeystoUse.trim().length()>0){
            this.mdcKeys = mdcKeystoUse.split(",");
        }
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    @Override
    public void activateOptions() {
    }
}