package hyweb.gip.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.mybatis.table.Tree;
@Repository
public interface TreeMapper {
    int deleteByPrimaryKey(Integer tid);

    int insert(Tree record);

    Tree selectByPrimaryKey(Integer tid);

    int updateByPrimaryKey(Tree record);
    
    LinkedList<Tree> selectAll();
    
    List<Tree> selectByPrimaryKeyList(@Param("tIds") String[] tIds);    
}