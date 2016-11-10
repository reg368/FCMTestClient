package hyweb.gip.dao;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.mybatis.table.Ap;
@Repository
public interface ApMapper {
    int deleteByPrimaryKey(Integer apid);

    int insert(Ap record);

    Ap selectByPrimaryKey(Integer apid);

    int updateByPrimaryKey(Ap record);
    
    LinkedList<Ap> selectAll();
    
    LinkedList<Ap> selectNoUseAp(int ugrpid);
    
    LinkedList<Ap> selectUseAp(String userid);
    
    Ap selectApByEName(@Param("ename") String ename);
    
    LinkedList<Ap> list(@Param("page") int page, @Param("showNum") int showNum);
    int countAll();
}