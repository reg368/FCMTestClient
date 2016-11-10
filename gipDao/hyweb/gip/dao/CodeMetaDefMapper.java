package hyweb.gip.dao;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.mybatis.table.CodeMetaDef;
@Repository
public interface CodeMetaDefMapper {
    int deleteByPrimaryKey(Integer codemetaseq);

    int insert(CodeMetaDef record);

    CodeMetaDef selectByPrimaryKey(Integer codemetaseq);

    int updateByPrimaryKey(CodeMetaDef record);
    
    LinkedList<CodeMetaDef> selectAll();
    
    LinkedList<CodeMetaDef> selectType();
    
    LinkedList<CodeMetaDef> selectNode(String codemetatype);
    
    LinkedList<CodeMetaDef> selectTblName(String codemetaid);
    
    LinkedList<CodeMetaDef> list(@Param("page") int page, @Param("showNum") int showNum);
    
    int countAll();
}