package hyweb.gip.dao.service.impl;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.ApCatMapper;
import hyweb.gip.dao.service.ApCatService;
import hyweb.gip.pojo.mybatis.table.ApCat;

public class ApCatServiceImpl implements ApCatService {

	@Autowired
	private ApCatMapper apCatMapper;
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(String apcatid) {
		return apCatMapper.deleteByPrimaryKey(apcatid);
	}

	@Override
	@Transactional
	public int insert(ApCat record) {
		return apCatMapper.insert(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public ApCat selectByPrimaryKey(String apcatid) {
		return apCatMapper.selectByPrimaryKey(apcatid);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(ApCat record) {
		return apCatMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<ApCat> selectAll() {
		return apCatMapper.selectAll();
	}
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<ApCat> selectTopApCat(String userid) {
		return apCatMapper.selectTopApCat(userid);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<ApCat> selectAllByAp() {
		return apCatMapper.selectAll();
	}

	@Override
	@Transactional
	public LinkedList<ApCat> list(int page, int showNum) {
		return apCatMapper.list(page, showNum);
	}

	@Override
	@Transactional
	public int countAll() {
		return apCatMapper.countAll();
	}
}
