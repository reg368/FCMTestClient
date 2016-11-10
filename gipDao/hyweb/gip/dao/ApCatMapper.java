package hyweb.gip.dao;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import hyweb.gip.pojo.mybatis.table.ApCat;

@Repository
public interface ApCatMapper {
    int deleteByPrimaryKey(String apcatid);

    int insert(ApCat record);

    ApCat selectByPrimaryKey(String apcatid);

    int updateByPrimaryKey(ApCat record);
    
    LinkedList<ApCat> selectAll();
    
    LinkedList<ApCat> selectTopApCat(String userid);
    
    LinkedList<ApCat> list(@Param("page") int page, @Param("showNum") int showNum);
    
    int countAll();
}