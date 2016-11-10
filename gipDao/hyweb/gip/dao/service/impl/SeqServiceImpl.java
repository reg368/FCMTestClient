package hyweb.gip.dao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.SeqMapper;
import hyweb.gip.dao.service.SeqService;

public class SeqServiceImpl implements SeqService {

	@Autowired
	private SeqMapper seqMapper;
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Long getSeq(String name) {
		return seqMapper.getSeq(name);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String getNextOrderNum() {
		return seqMapper.getNextOrderNum();
	}
}
