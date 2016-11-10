package hyweb.util;

import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import hyweb.gip.exception.NotExistPageException;
import hyweb.util.cacheable.DomHelper;

public class XmlParseExceptionHelper {
	public static void parseXml(String xml) throws JDOMException, IOException, NotExistPageException {
		Document dom = DomHelper.getStringToDom(xml);
		Element rootElm = dom.getRootElement();
		if ("error".equals(rootElm.getName())) {
			if ("404".equals(rootElm.getAttribute("code").getValue())) {
				throw new NotExistPageException(rootElm.getText());
			}
		}
	}
}
