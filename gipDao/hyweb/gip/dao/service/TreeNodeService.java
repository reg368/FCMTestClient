package hyweb.gip.dao.service;

import hyweb.gip.pojo.mybatis.table.TreeNode;

import java.util.LinkedList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface TreeNodeService {
	@Transactional
	public int deleteByPrimaryKey(Long tnid);
	@Transactional
	public int insert(TreeNode record);
	@Transactional
	public TreeNode selectByPrimaryKey(Long tnid);
	@Transactional
	public TreeNode selectByCheckSiteId(String siteId, Long tnid);
	@Transactional
	public int updateByPrimaryKey(TreeNode record);
	@Transactional
	public LinkedList<TreeNode> selectAll();
	@Transactional
	public int insertTest(Integer tid, String tnname, String tndesc, String tnpic, Integer tnsort, String tnispublic, String tntype, String tndatasource, String tnxpath, String tnlevel);
	@Transactional
	public LinkedList<TreeNode> selectByLevel(Integer tid, Integer tnlevel);
	@Transactional
	public LinkedList<TreeNode> selectChild(Long tnid);
	@Transactional
	public LinkedList<TreeNode> selectNode(Integer top, Integer tid, String order);
	@Transactional
	public LinkedList<TreeNode> selectByParent(Long tnid,String top);
	@Transactional
	public LinkedList<TreeNode> selectByXpathAndLevel(Long tnid,String tnxpath,Integer tnlevel,String top);
	@Transactional
	public LinkedList<TreeNode> selectByTid(Integer tid);
	@Transactional
	public LinkedList<TreeNode> selectInTnid(List<Long> list);
	@Transactional
	public LinkedList<TreeNode> searchLevel();
	@Transactional
	public LinkedList<TreeNode> selectAllChild(Long tnid);
	@Transactional
	public LinkedList<TreeNode> selectAllPublic();
	@Transactional
	public LinkedList<TreeNode> selectByTnName(String tnname);
}
