package hyweb.gip.dao;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import hyweb.gip.pojo.mybatis.table.Ugrp;

@Repository
public interface UgrpMapper {
    int deleteByPrimaryKey(Integer ugrpid);

    int insert(Ugrp record);

    Ugrp selectByPrimaryKey(Integer ugrpid);

    int updateByPrimaryKey(Ugrp record);
    
    LinkedList<Ugrp> selectAll();
    
    LinkedList<Ugrp> list(@Param("page") int page, @Param("showNum") int showNum);
    
    int countAll();
    
    LinkedList<Ugrp> selectByUser(String userid);
}