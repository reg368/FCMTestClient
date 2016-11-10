package hyweb.gip.dao.service.impl;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.ApMapper;
import hyweb.gip.dao.UgrpApMapper;
import hyweb.gip.dao.UgrpMapper;
import hyweb.gip.dao.service.ApService;
import hyweb.gip.pojo.mybatis.table.Ap;
import hyweb.gip.pojo.mybatis.table.Ugrp;
import hyweb.gip.pojo.mybatis.table.UgrpAp;

public class ApServiceImpl implements ApService {
	@Autowired
	private ApMapper apMapper;
	@Autowired
	private UgrpMapper ugrpMapper;
	@Autowired
	private UgrpApMapper ugrpApMapper;
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(Integer apid) {
		int re = apMapper.deleteByPrimaryKey(apid);
		ugrpApMapper.deleteByApId(apid);
		return re;
	}

	@Override
	@Transactional
	public int insert(Ap record) {
		int re = apMapper.insert(record);
		LinkedList<Ugrp> ugrps = ugrpMapper.selectAll();
		UgrpAp ugrpAp = null;
		for(Ugrp ugrp:ugrps){
			ugrpAp = new UgrpAp();
			ugrpAp.setUgrpid(ugrp.getUgrpid());
			ugrpAp.setApid(record.getApid());
			ugrpAp.setApfunc(0);
			ugrpApMapper.insert(ugrpAp);
		}
		return re;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Ap selectByPrimaryKey(Integer apid) {
		return apMapper.selectByPrimaryKey(apid);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(Ap record) {
		return apMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Ap> selectAll() {
		return apMapper.selectAll();
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Ap> list(int page, int showNum) {
		return apMapper.list(page, showNum);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public int countAll() {
		return apMapper.countAll();
	}
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Ap> selectNoUseAp(int ugrpid) {
		return apMapper.selectNoUseAp(ugrpid);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Ap> selectUseAp(String userid) {
		return apMapper.selectUseAp(userid);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Ap selectApByEName(String ename) {
		return apMapper.selectApByEName(ename);
	}
}
