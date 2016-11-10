package hyweb.gip.model.table.impl;

import hyweb.gip.model.table.TableRowAction;
import hyweb.util.ParamParser;
import hyweb.util.ParamParser.Param;
import hyweb.util.cacheable.BeanHelper;
import hyweb.util.cacheable.DomHelper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;

public class DefaultTableRowAction implements TableRowAction {
	@Override
	public void startAction(Element elm, Object pojo) {
		
	}

	@Override
	public void endAction(Element elm, Object pojo) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException {
		List<Element> list = DomHelper.getListByXpath("column", elm);
		
		ParamParser parser = new ParamParser();
		for (Element e : list) {
			String url = e.getAttributeValue("url");
			Map<String, Param> map = parser.parseString(url);
			e.setAttribute("url", parserUrl(e, pojo, url, map));
		}
	}

	public String parserUrl(Element elm, Object pojo, String url, Map<String, Param> mapParam) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException {
		String retUrl = url;
		Iterator<Map.Entry<String, Param>> it = mapParam.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry<String, Param> entry = it.next();
		    Param p = entry.getValue();
		    if("attr".equals(p.getType())){
				retUrl = retUrl.replace(p.getKey(), BeanHelper.getAttr(pojo, p.getName()).toString());
				it.remove();
			}
		}
		return retUrl;
	}
}
