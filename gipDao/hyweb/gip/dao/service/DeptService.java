package hyweb.gip.dao.service;

import hyweb.gip.pojo.mybatis.table.Dept;

import java.util.LinkedList;

import org.springframework.transaction.annotation.Transactional;

public interface DeptService {
	@Transactional
	public int deleteByPrimaryKey(Integer deptseq);
	@Transactional
	public void insert(Dept record);
	@Transactional
	public Dept selectByPrimaryKey(Integer deptseq);
	@Transactional
	public int updateByPrimaryKey(Dept record);
	@Transactional
	public LinkedList<Dept> selectAll();
	@Transactional
	public Dept selectParent(Integer deptseq);
	@Transactional
	public LinkedList<Dept> selectAllByTree();
	@Transactional
	public LinkedList<Dept> listByTree(int page, int showNum,int deptseq);
	@Transactional
	public int countAll();
	@Transactional
	public LinkedList<Dept> selectAllChild(String depthierarchycode);
	@Transactional
	public LinkedList<Dept> selectAllByTreeAndDept(Integer deptseq);
	@Transactional
	public int countAllByTreeAndDept(Integer deptseq);
}
