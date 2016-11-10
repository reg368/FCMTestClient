package hyweb.gip.dao.service;

import java.util.LinkedList;

import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.pojo.mybatis.table.CodeRef;

public interface CodeRefService {
	@Transactional
	public int deleteByPrimaryKey(Integer seq);
	@Transactional
	public int insert(CodeRef record);
	@Transactional
	public CodeRef selectByPrimaryKey(Integer seq);
	@Transactional
	public int updateByPrimaryKey(CodeRef record);
	@Transactional
	public LinkedList<CodeRef> selectAll();
	@Transactional
	public LinkedList<CodeRef> getById(String refid, String refvalue);
}
