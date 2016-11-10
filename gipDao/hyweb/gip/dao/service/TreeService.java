package hyweb.gip.dao.service;

import hyweb.gip.pojo.mybatis.table.Tree;

import java.util.LinkedList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface TreeService {
	@Transactional
	public int deleteByPrimaryKey(Integer tid);
	@Transactional
	public int insert(Tree record);
	@Transactional
	public Tree selectByPrimaryKey(Integer tid);
	@Transactional
	public int updateByPrimaryKey(Tree record);
	@Transactional
	public LinkedList<Tree> selectAll();
	@Transactional
	public List<Tree> selectByPrimaryKeyList(String[] tIds);
}
