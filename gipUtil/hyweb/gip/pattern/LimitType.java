package hyweb.gip.pattern;

import hyweb.core.kit.CollectionsKit;

import java.util.Map;

/**
 * Pattern 所需的權限與驗證
 * @author A0074
 *
 */
public enum LimitType {
	LIST(new LimitTypeBean("list", "[條列]", 1)),
	VIEW(new LimitTypeBean("view", "[檢視]", 1 << 1)),
	ADD(new LimitTypeBean("add", "[新增]", 1 << 2)),
	EDIT(new LimitTypeBean("edit", "[修改]", 1 << 3)),
	DELETE(new LimitTypeBean("delete", "[刪除]", 1 << 4)),
	QUERY(new LimitTypeBean("query", "[查詢]", 1 << 5)),
	PRINT(new LimitTypeBean("print", "[列印]", 1 << 6)),
	EXPORT(new LimitTypeBean("export", "[匯出]", 1 << 7));

	private static Map<String, LimitType> MAP;
	private static Object LOCK = new Object();

	/**
	 * 透過字串與 HyPatternActionType.getValue 比對,取得對應的HyPatternActionType
	 * @param action
	 * @return
	 */
	public static LimitType get(String action){
		if(MAP == null){
			synchronized(LOCK){
				if(MAP == null){
					MAP = CollectionsKit.newMap();
					for(LimitType actionType : LimitType.values()) {
						MAP.put(actionType.getValue(), actionType);
					}
					LOCK = null;
				}
			}
		}
		return MAP.get(action);
	}

	private LimitTypeBean bean;

	LimitType(LimitTypeBean bean){
		this.bean = bean;
	}

	/**
	 * 值
	 * @return
	 */
	public String getValue(){
		return this.bean.value;
	}

	/**
	 * 名稱
	 * @return
	 */
	public String getName(){
		return this.bean.name;
	}

	/**
	 * 權限
	 * @return
	 */
	public int getLimit(){
		return this.bean.limit;
	}
}

class LimitTypeBean{
	String value;
	String name;
	Integer limit;

	public LimitTypeBean(String value, String name, int limit){
		super();
		this.value = value;
		this.name = name;
		this.limit = limit;
	}
}
