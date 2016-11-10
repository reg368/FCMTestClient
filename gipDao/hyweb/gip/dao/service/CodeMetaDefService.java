package hyweb.gip.dao.service;

import hyweb.gip.pojo.mybatis.table.CodeMetaDef;

import java.util.LinkedList;

import org.springframework.transaction.annotation.Transactional;

public interface CodeMetaDefService {
	@Transactional
	public int deleteByPrimaryKey(Integer codemetaseq);
	@Transactional
	public int insert(CodeMetaDef record);
	@Transactional
	public CodeMetaDef selectByPrimaryKey(Integer codemetaseq);
	@Transactional
	public int updateByPrimaryKey(CodeMetaDef record);
	@Transactional
	public LinkedList<CodeMetaDef> selectAll();
	@Transactional
	public LinkedList<CodeMetaDef> list(int page, int showNum);
	@Transactional
	public int countAll();
	@Transactional
	public LinkedList<CodeMetaDef> selectType();
	@Transactional
	public LinkedList<CodeMetaDef> selectNode(String codemetatype);
	@Transactional
	public LinkedList<CodeMetaDef> selectTblName(String codemetaid);
}
