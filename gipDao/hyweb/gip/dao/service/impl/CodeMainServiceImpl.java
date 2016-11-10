package hyweb.gip.dao.service.impl;

import hyweb.core.kit.CollectionsKit;
import hyweb.gip.dao.CodeMainMapper;
import hyweb.gip.dao.service.CodeMainService;
import hyweb.gip.pojo.mybatis.table.CodeMain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public class CodeMainServiceImpl implements CodeMainService {
	@Autowired
	private CodeMainMapper codeMainMapper;
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(Integer codeseq) {
		return codeMainMapper.deleteByPrimaryKey(codeseq);
	}

	@Override
	@Transactional
	public int insert(CodeMain record) {
		return codeMainMapper.insert(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public CodeMain selectByPrimaryKey(Integer codeseq) {
		return codeMainMapper.selectByPrimaryKey(codeseq);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(CodeMain record) {
		return codeMainMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeMain> selectAll() {
		return codeMainMapper.selectAll();
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeMain> selectShowValueByPrimaryKey(Integer codeseq) {
		return codeMainMapper.selectShowValueByPrimaryKey(codeseq);
	}
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeMain> selectByCodeId(String codeid) {
		return codeMainMapper.selectByCodeId(codeid);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Map<String, CodeMain> selectMapByCodeId(String codeid) {
		List<CodeMain> codeMainList = codeMainMapper.selectByCodeId(codeid);
		Map<String, CodeMain> map = new HashMap<String, CodeMain>();
		for (CodeMain codeMain : codeMainList) {
			map.put(codeMain.getCodevalue(), codeMain);
		}
		return map;
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeMain> selectAllByTree() {
		return codeMainMapper.selectAll();
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeMain> selectForRef(String codeid, String codevalue){
		return codeMainMapper.selectForRef(codeid, codevalue);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeMain> listByTree(int page, int showNum) {
		return codeMainMapper.list(page, showNum);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public int countAll() {
		return codeMainMapper.countAll();
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeMain> listByCodeId(String codeid, int page, int showNum) {
		return codeMainMapper.listByCodeId("'" + codeid + "'", page, showNum);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public int countByCodeId(String codeid) {
		return codeMainMapper.countByCodeId("'"+codeid+"'");
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String getCodeShowBySub(String refvalue, String codevalue) {
		return codeMainMapper.getCodeShowBySub(refvalue, codevalue);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String getAlert(String codeid, String codevalue) {
		return codeMainMapper.getAlert(codeid, codevalue);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String getInAlert(String codeid, String codevalue) {
		List<String> list = codeMainMapper.getInAlert(codeid, codevalue);
		return CollectionsKit.join(list, ",");
	}
}
