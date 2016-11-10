package hyweb.gip.dao.service.impl;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.UgrpApMapper;
import hyweb.gip.dao.service.UgrpApService;
import hyweb.gip.pojo.custom.UgrpApQuery;
import hyweb.gip.pojo.mybatis.table.UgrpAp;
import hyweb.gip.pojo.mybatis.table.UgrpApKey;

public class UgrpApServiceImpl implements UgrpApService {

	@Autowired
	private UgrpApMapper ugrpApMapper;
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(UgrpApKey key) {
		return ugrpApMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Transactional
	public int insert(UgrpAp record) {
		return ugrpApMapper.insert(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public UgrpAp selectByPrimaryKey(UgrpApKey key) {
		return ugrpApMapper.selectByPrimaryKey(key);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(UgrpAp record) {
		return ugrpApMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<UgrpAp> selectByUgrpId(int ugrpid) {
		return ugrpApMapper.selectByUgrpId(ugrpid);
	}
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public UgrpAp selectUgrpApByUgrpId(UgrpApQuery record) {
		LinkedList<UgrpAp> ugrpAps = ugrpApMapper.selectUgrpApByUgrpId(record);
		UgrpAp uap = null;
		
		UgrpAp ugrpAp = new UgrpAp();
		int apfunc = 0;
		for(int i=0;i<ugrpAps.size();i++){
			uap = ugrpAps.get(i);
			ugrpAp.setApid(uap.getApid());
			apfunc = uap.getApfunc() | apfunc;
			ugrpAp.setApfunc(apfunc);
		}
		return ugrpAp;
	}

}
