package hyweb.gip.pattern.handler;


public class ListStringReplaceCode implements StringReplaceCode {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get(Object source) {
		String view = "";
		try{
			if(source != null){
				String dsType = (String) StringReplaceHandler.getAttr(source, "dsType");
				String dsIsPublic = (String) StringReplaceHandler.getAttr(source, "dsIsPublic");
				String dsStatus = (String) StringReplaceHandler.getAttr(source, "dsStatus");
				view = dsType;

				if ("2".equals(dsIsPublic)) {
					view += "DraftEdit";
				} else {
//					switch (dsStatus) {
//					case "1": //不需審核
//						view += "Edit";
//						break;
//					case "2": //審核通過
//						view += "View";
//						break;
//					case "3": //待審核
//						view += "View";
//						break;
//					case "4": //審核不通過
//						view += "Edit";
//						break;
//					}
				}
			}
		}catch(Exception ex){
		}
		return view;
	}
}
