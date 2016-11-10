package hyweb.gip.dao.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.TreeMapper;
import hyweb.gip.dao.service.TreeService;
import hyweb.gip.pojo.mybatis.table.Tree;

public class TreeServiceImpl implements TreeService {
	@Autowired
	private TreeMapper treeMapper;
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(Integer tid) {
		return treeMapper.deleteByPrimaryKey(tid);
	}

	@Override
	@Transactional
	public int insert(Tree record) {
		return treeMapper.insert(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Tree selectByPrimaryKey(Integer tid) {
		return treeMapper.selectByPrimaryKey(tid);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(Tree record) {
		return treeMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Tree> selectAll() {
		return treeMapper.selectAll();
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<Tree> selectByPrimaryKeyList(String[] tIds) {
		return treeMapper.selectByPrimaryKeyList(tIds);
	}

}
