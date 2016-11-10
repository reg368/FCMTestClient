package hyweb.gip.dao.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.TreeNodeMapper;
import hyweb.gip.dao.service.TreeNodeService;
import hyweb.gip.pojo.mybatis.table.TreeNode;

public class TreeNodeServiceImpl implements TreeNodeService {
	@Autowired
	private TreeNodeMapper treeNodeMapper;
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(Long tnid) {
		return treeNodeMapper.deleteByPrimaryKey(tnid);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public TreeNode selectByCheckSiteId(String siteId, Long tnid) {
		return treeNodeMapper.selectByCheckSiteId(siteId, tnid);
	}

	@Override
	@Transactional
	public int insert(TreeNode record) {
		return treeNodeMapper.insert(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public TreeNode selectByPrimaryKey(Long tnid) {
		return treeNodeMapper.selectByPrimaryKey(tnid);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(TreeNode record) {
		return treeNodeMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<TreeNode> selectAll() {
		return treeNodeMapper.selectAll();
	}

	@Override
	@Transactional
	public int insertTest(Integer tid, String tnname, String tndesc, String tnpic, Integer tnsort, String tnispublic, String tntype, String tndatasource, String tnxpath, String tnlevel) {
		return treeNodeMapper.insertTest(tid, tnname, tndesc, tnpic, tnsort, tnispublic, tntype, tndatasource, tnxpath, tnlevel);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<TreeNode> selectByLevel(Integer tid, Integer tnlevel) {
		return treeNodeMapper.selectByLevel(tid, tnlevel);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<TreeNode> selectChild(Long tnid) {
		return treeNodeMapper.selectChild(tnid);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<TreeNode> selectNode(Integer top, Integer tid,
			String order) {
		return treeNodeMapper.selectNode(top, tid, order);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<TreeNode> selectByParent(Long tnid, String top) {
		return treeNodeMapper.selectByParent(tnid,top);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<TreeNode> selectByTid(Integer tid) {
		return treeNodeMapper.selectByTid(tid);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<TreeNode> selectInTnid(List<Long> list) {
		return treeNodeMapper.selectInTnid(list);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<TreeNode> selectByXpathAndLevel(Long tnid,
			String tnxpath, Integer tnlevel, String top) {
		return treeNodeMapper.selectByXpathAndLevel(tnid, tnxpath, tnlevel, top);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<TreeNode> searchLevel() {
		return treeNodeMapper.searchLevel();
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<TreeNode> selectAllChild(Long tnid) {
		return treeNodeMapper.selectAllChild(tnid);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<TreeNode> selectAllPublic() {
		// TODO Auto-generated method stub
		return treeNodeMapper.selectAllPublic();
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<TreeNode> selectByTnName(String tnname) {
		return treeNodeMapper.selectByTnName(tnname);
	}
}
