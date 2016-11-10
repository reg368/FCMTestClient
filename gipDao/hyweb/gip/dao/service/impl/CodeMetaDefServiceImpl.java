package hyweb.gip.dao.service.impl;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.CodeMetaDefMapper;
import hyweb.gip.dao.service.CodeMetaDefService;
import hyweb.gip.pojo.mybatis.table.CodeMetaDef;

public class CodeMetaDefServiceImpl implements CodeMetaDefService {
	@Autowired
	private CodeMetaDefMapper codeMetaDefMapper;
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(Integer codemetaseq) {
		return codeMetaDefMapper.deleteByPrimaryKey(codemetaseq);
	}

	@Override
	@Transactional
	public int insert(CodeMetaDef record) {
		return codeMetaDefMapper.insert(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public CodeMetaDef selectByPrimaryKey(Integer codemetaseq) {
		return codeMetaDefMapper.selectByPrimaryKey(codemetaseq);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(CodeMetaDef record) {
		return codeMetaDefMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeMetaDef> selectAll() {
		return codeMetaDefMapper.selectAll();
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeMetaDef> list(int page, int showNum) {
		return codeMetaDefMapper.list(page, showNum);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public int countAll() {
		return codeMetaDefMapper.countAll();
	}
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeMetaDef> selectType() {
		return codeMetaDefMapper.selectType();
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeMetaDef> selectNode(String codemetatype) {
		return codeMetaDefMapper.selectNode(codemetatype);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeMetaDef> selectTblName(String codemetaid){
		return codeMetaDefMapper.selectTblName(codemetaid);
	}
}
