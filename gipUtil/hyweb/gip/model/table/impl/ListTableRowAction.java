package hyweb.gip.model.table.impl;

import hyweb.util.ParamParser.Param;
import hyweb.util.cacheable.BeanHelper;

import java.util.Iterator;
import java.util.Map;

import org.jdom2.Element;

public class ListTableRowAction extends DefaultTableRowAction {
	@Override
	public String parserUrl(Element elm, Object pojo, String url,
			Map<String, Param> mapParam) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException {
		String retUrl = super.parserUrl(elm, pojo, url, mapParam);
		Iterator<Map.Entry<String, Param>> it = mapParam.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry<String, Param> entry = it.next();
		    Param p = entry.getValue();
		    if("formXml".equals(p.getType())){
				String dsType = (String) BeanHelper.getAttr(pojo, "dsType");
				String dsIsPublic = (String) BeanHelper.getAttr(pojo, "dsIsPublic");
				String dsStatus = (String) BeanHelper.getAttr(pojo, "dsStatus");
				
				String view = dsType;
				
				if ("2".equals(dsIsPublic)) {
					view += "DraftEdit";
				} else {
					if("1".equals(dsStatus)){
						view += "View";
					}else if ("2".equals(dsStatus)){
						view += "View";
					} else if ("3".equals(dsStatus)){
						view += "View";
					} else if("4".equals(dsStatus)){
						view += "Edit";
					}
				}
				retUrl = retUrl.replace(p.getKey(), view);
				it.remove();
				break;
			}
		}
		
		return retUrl;
	}
}
