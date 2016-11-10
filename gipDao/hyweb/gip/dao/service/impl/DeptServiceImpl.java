package hyweb.gip.dao.service.impl;

import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.DeptMapper;
import hyweb.gip.dao.service.DeptService;
import hyweb.gip.pojo.mybatis.table.Dept;

public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptMapper deptMapper;

	
	@Override
	@Transactional
	public int deleteByPrimaryKey(Integer deptseq) {
		return deptMapper.deleteByPrimaryKey(deptseq);
	}

	@Override
	@Transactional
	public void insert(Dept record) {		
		deptMapper.insert(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Dept selectByPrimaryKey(Integer deptseq) {
		return deptMapper.selectByPrimaryKey(deptseq);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(Dept record) {
		return deptMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Dept> selectAll() {
		return deptMapper.selectAll();
	}
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Dept selectParent(Integer deptseq) {
		return deptMapper.selectParent(deptseq);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Dept> selectAllByTree() {
		return deptMapper.selectAll();
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Dept> listByTree(int page, int showNum,int deptseq) {
		return deptMapper.list(page, showNum, deptseq);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public int countAll() {
		return deptMapper.countAll();
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Dept> selectAllChild(String depthierarchycode) {
		
		return deptMapper.selectAllChild(" '"+depthierarchycode+".%' ", " '"+depthierarchycode+"' ",
				" '%."+depthierarchycode+".%' ", " '%."+depthierarchycode+"' ");
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Dept> selectAllByTreeAndDept(Integer deptseq) {
		return deptMapper.selectAllByTreeAndDept(deptseq);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public int countAllByTreeAndDept(Integer deptseq) {
		return deptMapper.countAllByTreeAndDept(deptseq);
	}
	

}
