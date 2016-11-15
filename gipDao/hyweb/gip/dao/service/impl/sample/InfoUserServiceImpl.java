package hyweb.gip.dao.service.impl;

import hyweb.gip.dao.CodeMainMapper;
import hyweb.gip.dao.InfoUserMapper;
import hyweb.gip.dao.UgrpMapper;
import hyweb.gip.dao.service.InfoUserService;
import hyweb.gip.model.encrypt.impl.EncryptHandleImpl;
import hyweb.gip.pojo.mybatis.table.InfoUser;
import hyweb.util.SpringLifeCycle;

import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class InfoUserServiceImpl implements InfoUserService {
	@Autowired
	private InfoUserMapper infoUserMapper;
	
	@Autowired
	private UgrpMapper ugrpMapper;
	
	@Autowired
	private CodeMainMapper codeMainMapper;
	
	private EncryptHandleImpl encryptHandleImpl = 
			(EncryptHandleImpl)SpringLifeCycle.getBean("encryptHandleImpl");
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(String userid) {
		return infoUserMapper.deleteByPrimaryKey(userid);
	}

	@Override
	@Transactional
	public int insert(InfoUser record) {
		record.setVisitcount(0);
		record = encryptHandleImpl.encryptInfoUser(record, record.getUserid());
		record.setPassword(encryptHandleImpl.irreversible(record.getPassword()));
		return infoUserMapper.insert(record);
	}

	@Override
	@Transactional
	public InfoUser selectByPrimaryKey(String userid) {
		InfoUser infoUser = infoUserMapper.selectByPrimaryKey(userid);
		infoUser = encryptHandleImpl.decryptInfoUser(infoUser);
		return infoUser;
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(InfoUser record, String userid) {
		record = encryptHandleImpl.encryptInfoUser(record, userid);
		record.setPassword(encryptHandleImpl.irreversible(record.getPassword()));
		record.setUpdatetime(new Date());
		return infoUserMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional
	public LinkedList<InfoUser> selectAll() {
		LinkedList<InfoUser> infoUsers = infoUserMapper.selectAll();
		return selectToView(infoUsers);
	}
	
	@Override
	@Transactional
	public int countAll() {
		return infoUserMapper.countAll();
	}
	
	@Override
	@Transactional
	public LinkedList<InfoUser> list(int page, int showNum, String deptid) {
		LinkedList<InfoUser> infoUsers = infoUserMapper.list(page, showNum,deptid);
		
		return selectToView(infoUsers);
	}
	
	private LinkedList<InfoUser> selectToView(LinkedList<InfoUser> infoUsers) {
		for(int i = 0;i<infoUsers.size();i++){
			String ugrp = "";
			String postLimit = "";
			String reviewLimit = "";
			InfoUser infoUser = infoUsers.get(i);
			infoUser = encryptHandleImpl.decryptInfoUser(infoUser);
			String[] ugrps = (infoUser.getUgrpid() == null?  new String[]{""}:infoUser.getUgrpid().split(","));
			for(int j=0;j<ugrps.length;j++){
				if(ugrps[j].length()>0 && ugrpMapper.selectByPrimaryKey(Integer.parseInt(ugrps[j]))!=null){
					if(j==ugrps.length-1)
						ugrp=ugrp + ugrpMapper.selectByPrimaryKey(Integer.parseInt(ugrps[j])).getUgrpname();
				
					else
						ugrp=ugrp + ugrpMapper.selectByPrimaryKey(Integer.parseInt(ugrps[j])).getUgrpname()+",";
				}
			}
			
			String[] postlimits = (infoUser.getPostlimit() == null?  new String[]{""}:infoUser.getPostlimit().split(","));
			for(int j=0;j<postlimits.length;j++){
				if(postlimits[j].length()>0 && codeMainMapper.selectByPrimaryKey(Integer.parseInt(postlimits[j]))!=null){
					if(j==postlimits.length-1)
						postLimit = postLimit + codeMainMapper.selectByPrimaryKey(Integer.parseInt(postlimits[j])).getCodeshow();
				
					else
						postLimit = postLimit + codeMainMapper.selectByPrimaryKey(Integer.parseInt(postlimits[j])).getCodeshow()+",";
				}
			}
			String[] reviewlimits = (infoUser.getReviewlimit() == null?  new String[]{""}:infoUser.getReviewlimit().split(","));
			for(int j=0;j<reviewlimits.length;j++){
				if(reviewlimits[j].length()>0 && codeMainMapper.selectByPrimaryKey(Integer.parseInt(reviewlimits[j]))!=null){
					if(j==reviewlimits.length-1)
						reviewLimit = reviewLimit + codeMainMapper.selectByPrimaryKey(Integer.parseInt(reviewlimits[j])).getCodeshow();
				
					else
						reviewLimit = reviewLimit + codeMainMapper.selectByPrimaryKey(Integer.parseInt(reviewlimits[j])).getCodeshow()+",";
				}
			}
			
			infoUser.setUgrpid(ugrp);
			infoUser.setPostlimit(postLimit);
			infoUser.setReviewlimit(reviewLimit);
			infoUsers.set(i, infoUser);
		}
		
		return infoUsers;
	}
	
	@Override
	@Transactional
	public InfoUser selectByUserPassword(InfoUser record) {
		return infoUserMapper.selectByUserPassword(record);
	}

	@Override
	@Transactional
	public int updateCountByPrimaryKey(InfoUser record) {
		return infoUserMapper.updateCountByPrimaryKey(record);
	}

	@Override
	@Transactional
	public int selectUserId(String userid) {
		return infoUserMapper.selectUserId(userid);
	}

	@Override
	@Transactional
	public int updateWithoutPwd(InfoUser infoUser, String userid) {
		infoUser = encryptHandleImpl.encryptInfoUser(infoUser, userid);
		infoUser.setPassword(encryptHandleImpl.irreversible(infoUser.getPassword()));
		infoUser.setUpdatetime(new Date());
		return infoUserMapper.updateWithoutPwd(infoUser);
	}

	@Override
	@Transactional
	public int countAllByDept(String deptid) {
		return infoUserMapper.countAllByDept(deptid);
	}
}
