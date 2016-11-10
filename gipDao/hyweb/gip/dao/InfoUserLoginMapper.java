package hyweb.gip.dao;

import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.mybatis.table.InfoUserLogin;
@Repository
public interface InfoUserLoginMapper {
    int deleteByPrimaryKey(Long loginseq);

    int insert(InfoUserLogin record);

    InfoUserLogin selectByPrimaryKey(Long loginseq);

    int updateByPrimaryKey(InfoUserLogin record);
    
    InfoUserLogin selectLastByUserId(String loginuserid);
}