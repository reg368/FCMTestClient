package hyweb.gip.dao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.InfoUserLoginMapper;
import hyweb.gip.dao.service.InfoUserLoginService;
import hyweb.gip.pojo.mybatis.table.InfoUserLogin;

public class InfoUserLoginServiceImpl implements InfoUserLoginService {

	@Autowired
	private InfoUserLoginMapper infoUserLoginMapper;
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(Long loginseq) {
		return infoUserLoginMapper.deleteByPrimaryKey(loginseq);
	}


	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public InfoUserLogin selectByPrimaryKey(Long loginseq) {
		return infoUserLoginMapper.selectByPrimaryKey(loginseq);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(InfoUserLogin record) {
		return infoUserLoginMapper.updateByPrimaryKey(record);
	}
	@Override
	@Transactional
	public int insert(InfoUserLogin record) {
		return infoUserLoginMapper.insert(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public InfoUserLogin selectLastByUserId(String loginuserid) {
		return infoUserLoginMapper.selectLastByUserId(loginuserid);
	}

}
