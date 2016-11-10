package hyweb.gip.dao.service;

import hyweb.gip.pojo.mybatis.table.Ap;

import java.util.LinkedList;

import org.springframework.transaction.annotation.Transactional;

public interface ApService {
	@Transactional
	public int deleteByPrimaryKey(Integer apid);
	@Transactional
	public int insert(Ap record);
	@Transactional
	public Ap selectByPrimaryKey(Integer apid);
	@Transactional
	public int updateByPrimaryKey(Ap record);
	@Transactional
	public LinkedList<Ap> selectAll();
	@Transactional
	public LinkedList<Ap> list(int page, int showNum);
	@Transactional
	public int countAll();
	@Transactional
	public LinkedList<Ap> selectNoUseAp(int ugrpid);
	@Transactional
	public LinkedList<Ap> selectUseAp(String userid);
	@Transactional
	public Ap selectApByEName(String ename);
}
