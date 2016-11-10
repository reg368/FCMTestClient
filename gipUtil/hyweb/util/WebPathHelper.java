package hyweb.util;

import java.io.File;
import java.net.URISyntaxException;

public class WebPathHelper {
	
	public static File getWebAppFile(String filePath) throws URISyntaxException {
		return new File(new File(Cfg.get("DIR_WEB-INF")).getParentFile(), filePath);
	}
	
	public static String getWebInfPath() throws URISyntaxException {
		return Cfg.get("DIR_WEB-INF");
	}

	public static File getWebInfFile(String filePath) throws URISyntaxException {
		return new File(getWebInfPath(), filePath);
	}
	
	public static String getMpStyleXslPath(String mpstyle) throws URISyntaxException {
		return getWebInfPath()+"/mpstyle/"+mpstyle+"/xsl/";
	}
	
	public static File getMpStyleXslFile(String mpstyle, String xsl) throws URISyntaxException {
		return new File(getWebInfPath()+"/mpstyle/"+mpstyle+"/xsl", xsl);
	}
}
