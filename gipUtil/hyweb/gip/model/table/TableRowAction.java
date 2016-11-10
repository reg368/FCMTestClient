package hyweb.gip.model.table;

import org.jdom2.Element;

public interface TableRowAction {
	void startAction(Element elm, Object pojo);
	void endAction(Element elm, Object pojo) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException;
}
