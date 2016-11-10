package hyweb.gip.dao.service;

import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.pojo.mybatis.table.InfoUserLogin;

public interface InfoUserLoginService {
    @Transactional
	public int deleteByPrimaryKey(Long loginseq);
    @Transactional
	public InfoUserLogin selectByPrimaryKey(Long loginseq);
    @Transactional
	public int updateByPrimaryKey(InfoUserLogin record);
	@Transactional
	public int insert(InfoUserLogin record);
    @Transactional
	public InfoUserLogin selectLastByUserId(String loginuserid);

}
