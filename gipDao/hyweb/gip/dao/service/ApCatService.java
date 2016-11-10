package hyweb.gip.dao.service;

import hyweb.gip.pojo.mybatis.table.ApCat;

import java.util.LinkedList;

import org.springframework.transaction.annotation.Transactional;

public interface ApCatService {
	@Transactional
	public int deleteByPrimaryKey(String apcatid);
	@Transactional
	public int insert(ApCat record);
	@Transactional
	public ApCat selectByPrimaryKey(String apcatid);
	@Transactional
	public int updateByPrimaryKey(ApCat record);
	@Transactional
	public LinkedList<ApCat> selectAll();
	@Transactional
	public LinkedList<ApCat> selectTopApCat(String userid);
	@Transactional
	public LinkedList<ApCat> selectAllByAp();
	@Transactional
    public LinkedList<ApCat> list(int page, int showNum);
	@Transactional
    public int countAll();
}
