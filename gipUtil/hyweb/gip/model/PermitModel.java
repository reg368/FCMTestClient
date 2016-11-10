package hyweb.gip.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class PermitModel {
	protected static Map<String, PermitBean> PERMIT_MAP;
	protected static Timer TIMER;
	final static int PERIOD_MIL_SEC = 3 *60 * 60 * 1000;
	static {
		PERMIT_MAP = new ConcurrentHashMap<String, PermitBean>();
		TIMER = new Timer();
		TIMER.schedule(new PermitTask(), PERIOD_MIL_SEC, PERIOD_MIL_SEC);
	}
	/**
	 * 查詢允許碼是否存在
	 * @param permit
	 * @return
	 */
	public static boolean isPermitExist(String permit){
		if(permit == null){
			return false;
		}else{
			return PERMIT_MAP.containsKey(permit);
		}
	}
	
	/**
	 *  取得 {CardNo, PersionNo},存在時同時移除
	 * @param permit
	 * @return
	 */
	public static String[] getPersionDataAndRemvoe(String permit){
		if(permit == null){
			return null;
		}else{
			PermitBean bean = PERMIT_MAP.remove(permit);
			if(bean == null){
				return null;
			}else {
				return bean.getCardnoAndPersionno();
			}
		}
	}
	

	/**
	 * 取得 {CardNo, PersionNo}
	 * @param permit
	 * @return
	 */
	public static String[] getPersionData(String permit){
		if(permit == null){
			return null;
		}else{
			PermitBean bean = PERMIT_MAP.get(permit);
			if(bean == null){
				return null;
			}else {
				return bean.getCardnoAndPersionno();
			}
		}
	}

	/**
	 * 新增一筆許可號
	 * @param cardId
	 */
	public static String addNewPermit(String cardNo, String persionNo){
		char[] defChar = "ndjoP5628Y93hIL70MAstUvq4GrwbCEfXkz1".toCharArray();
		char[] permitArr = new char[10];
		for(int i = 0 ; i < permitArr.length ; i++){
			permitArr[i] = defChar[((int)(Math.random() * 1000)) % defChar.length];
		}
		String permit = new String(permitArr);
		PERMIT_MAP.put(permit, new PermitBean(permit, cardNo, persionNo));
		return permit;
	}

	private PermitModel(){
		super();
	}
}

/**
 * 清除 PERMIT_MAP 中過期的資料
 *
 */
class PermitTask extends TimerTask {
	@Override
	public void run() {
		Set<String> permitSet = PermitModel.PERMIT_MAP.keySet();
		for(String permit : permitSet){
			PermitBean permitBean = PermitModel.PERMIT_MAP.get(permit);
			if(!permitBean.isValidity()){
				PermitModel.PERMIT_MAP.remove(permit);
			}	
		}
	}
}


class PermitBean implements Serializable{
	private static final long serialVersionUID = 1594869729033480106L;
	private String permit;
	private String cardNo;
	private String persionNo;
	private long validityTime;

	public PermitBean(String permit, String cardNo, String persionNo){
		this.permit = permit;
		this.cardNo = cardNo;
		this.persionNo = persionNo;
		this.validityTime = System.currentTimeMillis() + PermitModel.PERIOD_MIL_SEC; 
	}

	public String getPermit(){
		return this.permit;
	}

	public String[] getCardnoAndPersionno(){
		return new String[]{this.cardNo, this.persionNo};
	}

	/**
	 * 是否過期
	 * @return
	 */
	public boolean isValidity(){
		return this.validityTime >= System.currentTimeMillis();
	}
}
