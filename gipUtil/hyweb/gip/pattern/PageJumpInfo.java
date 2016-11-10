package hyweb.gip.pattern;

/**
 * 
 * @author A0074
 *
 */
public class PageJumpInfo implements java.io.Serializable{
	private static final long serialVersionUID = 450616078126976153L;
	public String tableName;
	public String select;
	public String where;
	public String order;
	public int page;
	public int pageSize;
	public int showNum;
	public PageJumpInfo(){
		super();
	};
}
