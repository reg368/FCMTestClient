package hyweb.gip.dao.service;

import hyweb.gip.pojo.mybatis.table.CodeMain;

import java.util.LinkedList;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

public interface CodeMainService {
	@Transactional
	public int deleteByPrimaryKey(Integer codeseq);
	@Transactional
	public int insert(CodeMain record);
	@Transactional
	public CodeMain selectByPrimaryKey(Integer codeseq);
	@Transactional
	public int updateByPrimaryKey(CodeMain record);
	@Transactional
	public LinkedList<CodeMain> selectAll();
	@Transactional
	public LinkedList<CodeMain> selectShowValueByPrimaryKey(Integer codeseq);
	@Transactional
	public LinkedList<CodeMain> selectByCodeId(String codeid);	
	@Transactional
	public Map<String, CodeMain> selectMapByCodeId(String codeid);	
	@Transactional
	public LinkedList<CodeMain> selectAllByTree();	
	@Transactional
	public LinkedList<CodeMain> selectForRef(String codeid, String codevalue);	
	@Transactional
	public LinkedList<CodeMain> listByTree(int page, int showNum);	
	@Transactional
	public int countAll();	
	@Transactional
	public LinkedList<CodeMain> listByCodeId(String codeid, int page, int showNum);	
	@Transactional
	public int countByCodeId(String codeid);
	@Transactional
	public String getCodeShowBySub(String refvalue,String codevalue);
	@Transactional
	public String getAlert(String codeid, String codevalue);
	@Transactional
	public String getInAlert(String codeid, String codevalue);
}
