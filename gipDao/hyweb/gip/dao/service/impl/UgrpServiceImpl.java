package hyweb.gip.dao.service.impl;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.ApMapper;
import hyweb.gip.dao.UgrpApMapper;
import hyweb.gip.dao.UgrpMapper;
import hyweb.gip.dao.service.UgrpService;
import hyweb.gip.pojo.mybatis.table.Ap;
import hyweb.gip.pojo.mybatis.table.Ugrp;
import hyweb.gip.pojo.mybatis.table.UgrpAp;

public class UgrpServiceImpl implements UgrpService {

	@Autowired
	private UgrpMapper ugrpMapper;
	
	@Autowired
	private UgrpApMapper ugrpApMapper;
	
	@Autowired
	private ApMapper apMapper;
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(Integer ugrpid) {
		return ugrpMapper.deleteByPrimaryKey(ugrpid);
	}

	@Override
	@Transactional
	public int insert(Ugrp record) {
		int res = ugrpMapper.insert(record);
		int ugrpid = record.getUgrpid();
		LinkedList<Ap> apList =apMapper.selectAll();
		UgrpAp ugrpAp = null;
		for(int i =0;i<apList.size();i++){
			ugrpAp = new UgrpAp();
			ugrpAp.setUgrpid(ugrpid);
			ugrpAp.setApid(apList.get(i).getApid());
			ugrpAp.setApfunc(0);
			ugrpApMapper.insert(ugrpAp);
		}
		return res;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Ugrp selectByPrimaryKey(Integer ugrpid) {
		return ugrpMapper.selectByPrimaryKey(ugrpid);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(Ugrp record) {
		return ugrpMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Ugrp> selectAll() {
		return ugrpMapper.selectAll();
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Ugrp> list(int page, int showNum) {
		return ugrpMapper.list(page, showNum);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public int countAll() {
		return ugrpMapper.countAll();
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Ugrp> selectAllUgrp() {
		return ugrpMapper.selectAll();
	}

	@Override
	@Transactional
	public LinkedList<Ugrp> selectByUser(String userid) {
		return ugrpMapper.selectByUser(userid);
	}
}
