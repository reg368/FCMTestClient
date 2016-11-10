package hyweb.gip.dao;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.mybatis.table.InfoUser;
@Repository
public interface InfoUserMapper {
    int deleteByPrimaryKey(String userid);

    int insert(InfoUser record);

    InfoUser selectByPrimaryKey(String userid);

    int updateByPrimaryKey(InfoUser record);
    
    LinkedList<InfoUser> selectAll();
    
    int countAll();
    
    LinkedList<InfoUser> list(@Param("page") int page, @Param("showNum") int showNum
    		,@Param("deptid") String deptid);
    
    InfoUser selectByUserPassword(InfoUser record);
    
    int updateCountByPrimaryKey(InfoUser record);
    
    int selectUserId(String userid);
    
    int updateWithoutPwd(InfoUser infoUser);
    
    int countAllByDept(@Param("deptid") String deptid);
}