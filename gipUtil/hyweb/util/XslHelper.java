package hyweb.util;

import hyweb.core.kit.StringKit;
import hyweb.util.cacheable.DomHelper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class XslHelper {
	private static final String XSL_URI = "http://www.w3.org/1999/XSL/Transform";
	
	/**
	 * 取得xml所有xsl路徑
	 * @param dom
	 * @param prefix
	 * @return
	 * @throws MalformedURLException 
	 */
	public static List<String> getXslPathList(Document xmlDom, String prefix) throws MalformedURLException {
		List<Element> elmList = DomHelper.getListByXpath("//*[@xsl]", xmlDom);
		List<String> results = new ArrayList<String>();
		for (Element elm : elmList) {
			String xsl = elm.getAttributeValue("xsl");
			if (StringKit.isBlank(xsl) == false) {
				String val = new File(prefix, xsl).toURI().toURL().toExternalForm();
				if (results.contains(val) == false) {
					results.add(val);
				}
			}
		}
		
		return results;
	}
	
	/**
	 * 將xml所有xsl路徑import到xsl裡
	 * @param xslFile
	 * @param xmlDom
	 * @param prefix
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Document importXsl(File xslFile, Document xmlDom, String prefix) throws JDOMException, IOException {
		List<String> xslList = XslHelper.getXslPathList(xmlDom, prefix);
		
		SAXBuilder builder = new SAXBuilder();
		Document dom = builder.build(xslFile);
		
		for (String xsl : xslList) {
			Element e = new Element("import", Namespace.getNamespace("xsl", XSL_URI));
			e.setAttribute("href", xsl);
			dom.getRootElement().addContent(0, e);
		}
		
		return dom;
	}
	
	public static String getMpStyle(Document dom) {
		return DomHelper.getStringByXpath("/hpMain/mpStyle", dom);
	}
}
