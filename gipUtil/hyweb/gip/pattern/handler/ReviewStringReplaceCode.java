package hyweb.gip.pattern.handler;

public class ReviewStringReplaceCode implements StringReplaceCode {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get(Object source) {
		String view = "";
		try{
			if(source != null){
				String dsType = (String) StringReplaceHandler.getAttr(source, "dsType");
				String dsStatus = (String) StringReplaceHandler.getAttr(source, "dsStatus");
				view = dsType;
//				switch (dsStatus) {
//				case "1": //不需審核
//					view += "EditReview";
//					break;
//				case "2": //審核通過
//					view += "EditReview";
//					break;
//				case "3": //待審核
//					view += "EditReview";
//					break;
//				case "4": //審核不通過
//					view += "ViewReview";
//					break;
//				}
			}
		}catch(Exception ex){
		}
		return view;
	}
}
