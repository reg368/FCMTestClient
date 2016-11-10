package hyweb.gip.dao.service;

import hyweb.gip.pojo.mybatis.table.InfoUser;

import java.util.LinkedList;

import org.springframework.transaction.annotation.Transactional;

public interface InfoUserService {
	@Transactional
	int deleteByPrimaryKey(String userid);
	@Transactional
    int insert(InfoUser record);
	@Transactional
    InfoUser selectByPrimaryKey(String userid);
	@Transactional
    int updateByPrimaryKey(InfoUser record, String userid);
	@Transactional
	public LinkedList<InfoUser> selectAll();
	@Transactional
	public int countAll();
	@Transactional
	public LinkedList<InfoUser> list(int page, int showNum,String deptid);
	@Transactional
	public InfoUser selectByUserPassword(InfoUser record);
	@Transactional
	public int updateCountByPrimaryKey(InfoUser record);
	@Transactional
	public int selectUserId(String userid);
	@Transactional
	public int updateWithoutPwd(InfoUser infoUser, String userid);
	@Transactional
	public int countAllByDept(String deptid);
}
