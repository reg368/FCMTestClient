package hyweb.gip.dao.service.impl;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.CodeRefMapper;
import hyweb.gip.dao.service.CodeRefService;
import hyweb.gip.pojo.mybatis.table.CodeRef;

public class CodeRefServiceImpl implements CodeRefService {

	@Autowired
	private CodeRefMapper codeRefMapper;
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(Integer seq) {
		return codeRefMapper.deleteByPrimaryKey(seq);
	}

	@Override
	@Transactional
	public int insert(CodeRef record) {
		return codeRefMapper.insert(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public CodeRef selectByPrimaryKey(Integer seq) {
		return codeRefMapper.selectByPrimaryKey(seq);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(CodeRef record) {
		return codeRefMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeRef> selectAll() {
		return codeRefMapper.selectAll();
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<CodeRef> getById(String refid, String refvalue){
		return codeRefMapper.getById(refid, refvalue);
	}

}
