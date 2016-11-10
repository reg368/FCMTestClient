package hyweb.gip.pattern;

import hyweb.core.kit.CollectionsKit;
import hyweb.core.kit.StringKit;
import hyweb.gip.exception.LimitsException;
import hyweb.gip.pojo.mybatis.table.Ap;
import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.gip.pojo.mybatis.table.UgrpAp;
import hyweb.util.cacheable.GIPHelper;

import java.util.List;
import java.util.Map;

import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 權限管理,重啟才會有效
 * @author Allen
 *
 */
public class Limit {
	protected final static Logger LOG = LoggerFactory.getLogger(Limit.class);
	final static Map<String, String> ACTION_MSG;
	static{
		List<Ap> list = GIPHelper.getApList();
		ACTION_MSG = CollectionsKit.newMap();
		for(Ap ap : list){
			ACTION_MSG.put(ap.getApename(), ap.getApcname());
		}
		/*
		ACTION_MSG.put("PostData", "資料上稿");
		ACTION_MSG.put("ReviewData", "資料審稿");
		ACTION_MSG.put("LayoutManager", "版面管理");
		ACTION_MSG.put("Shelves", "資料上架");
		*/
	}
	
	UgrpAp ugrpAp;

	/**
	 * 當使用者沒有使用本頁面權限時，丟出excetion
	 */
	public Limit(InfoUser loginUser, String code, String pattern, List<Element> checkRight) throws LimitsException{
		this.ugrpAp = GIPHelper.getUgrpAp(loginUser.getUserid(), code);
		System.out.println("===== getUserid : " + loginUser.getUserid());
		System.out.println("===== code : " + code);
		System.out.println("===== ugrpAp : " + ugrpAp);
		System.out.println("===== ugrpAp.getApfunc() : " + ugrpAp.getApfunc());
		LimitType limitType = LimitType.get(pattern);
		if (!this.isAllow(pattern)) {
			Ap ap = GIPHelper.getAp(code);
			String msg = ap ==null ? "無此權限群組" : ("您無[" + ap.getApcname() + "]" + limitType.getName() + "權限！");
			throw new LimitsException(msg);
		}

		for (Element e : checkRight) {
			if (!this.isAllow(e.getTextTrim())) {
				e.getParentElement().removeContent();
			}
		}
	}

	/**
	 * 本使用者有無本權限，用於該功能超連結是否顯示
	 * @param action {1=list,2=view,3=add,4=edit,5=delete,6=query,7=print,8=export...}
	 * @return
	 */
	public boolean isAllow(String actionString) {
		if (StringKit.isEmpty(actionString)) {
			return true;
		}
		LimitType action = LimitType.get(actionString);
		if(action == null){
			throw new RuntimeException("無此"+action+"權限");
		}
		int apFunc = this.ugrpAp.getApfunc().intValue();
		LOG.debug("actionName={}, apFunc={}, limite={} ", action, apFunc, action.getLimit());
		return (apFunc & action.getLimit()) == action.getLimit();
	}
}
