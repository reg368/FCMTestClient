package hyweb.util.cacheable;

import hyweb.gip.dao.service.ApService;
import hyweb.gip.dao.service.CodeMainService;
import hyweb.gip.dao.service.CodeMetaDefService;
import hyweb.gip.dao.service.DataImgService;
import hyweb.gip.dao.service.UgrpApService;
import hyweb.gip.pojo.custom.UgrpApQuery;
import hyweb.gip.pojo.mybatis.table.Ap;
import hyweb.gip.pojo.mybatis.table.CodeMain;
import hyweb.gip.pojo.mybatis.table.CodeMetaDef;
import hyweb.gip.pojo.mybatis.table.DataImg;
import hyweb.gip.pojo.mybatis.table.UgrpAp;
import hyweb.util.SpringLifeCycle;

import java.util.List;
import java.util.Map;

/**
 *  GIP table 的 cache
 * @author A0074
 *
 */
public class GIPHelper {
	/**
	 * 
	 * @param codeId
	 * @return
	 */
	public static List<CodeMain> getCodeMain(String codeId){
		return ((CodeMainService)SpringLifeCycle.getBean("CodeMainServiceImpl")).selectByCodeId(codeId);
	}
	
	/**
	 * 
	 * @param codeId
	 * @return
	 */
	public static Map<String, CodeMain> getCodeMainMap(String codeId){
		return ((CodeMainService)SpringLifeCycle.getBean("CodeMainServiceImpl")).selectMapByCodeId(codeId);
	}

	/**
	 * 
	 * @param codeId
	 * @param codeValue
	 * @return
	 */
	public static List<CodeMain> getCodeMain(String codeId, String codeValue){
		return  ((CodeMainService)SpringLifeCycle.getBean("CodeMainServiceImpl")).selectForRef(codeId, codeValue);
	}

	/**
	 * 
	 * @return
	 */
	public static UgrpAp getUgrpAp(String userId, String code){
		UgrpApQuery ugrpApQuery = new UgrpApQuery();
		ugrpApQuery.setUserid(userId);
		ugrpApQuery.setApurl(code);
		return ((UgrpApService)SpringLifeCycle.getBean("UgrpApServiceImpl")).selectUgrpApByUgrpId(ugrpApQuery);
	}

	public static Ap getAp(String ename){
		return ((ApService)SpringLifeCycle.getBean("ApServiceImpl")).selectApByEName(ename);
	}

	public static List<Ap> getApList(){
		return ((ApService)SpringLifeCycle.getBean("ApServiceImpl")).selectAll();
	}
	
	/**
	 * 
	 * */
	public static List<CodeMetaDef> getCodeMetaDef(String codeMetaId){
		return  ((CodeMetaDefService)SpringLifeCycle.getBean("CodeMetaDefServiceImpl")).selectTblName(codeMetaId);
	}
	
	/**
	 * 
	 * */
	public static List<DataImg> getImgByDsId(Long didataid){
		return  ((DataImgService)SpringLifeCycle.getBean("DataImgServiceImpl")).getByDsId(didataid);
	}
	
	/**
	 * 
	 * */
	public static String getCodeShow(String codeid, String codevalue){
		return  ((CodeMainService)SpringLifeCycle.getBean("CodeMainServiceImpl")).getAlert(codeid, codevalue);
	}

	public static String getCodeShowCantMuiltValue(String codeid, String codevalue){
		if(codevalue.split("','").length == 1){
			if(codevalue.startsWith("'") && codevalue.endsWith("'")){
				return ((CodeMainService)SpringLifeCycle.getBean("CodeMainServiceImpl")).getInAlert(codeid, codevalue);
			}else{
				return ((CodeMainService)SpringLifeCycle.getBean("CodeMainServiceImpl")).getAlert(codeid, codevalue);
			}
		}else{
			return ((CodeMainService)SpringLifeCycle.getBean("CodeMainServiceImpl")).getInAlert(codeid, codevalue);
		}
	}
}
