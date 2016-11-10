package hyweb.gip.dao;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import hyweb.gip.pojo.mybatis.table.CodeRef;
@Repository
public interface CodeRefMapper {
	int deleteByPrimaryKey(Integer seq);

    int insert(CodeRef record);

    CodeRef selectByPrimaryKey(Integer seq);

    int updateByPrimaryKey(CodeRef record);
    
    LinkedList<CodeRef> selectAll();
    
    LinkedList<CodeRef> getById(@Param("refid") String refid, @Param("refvalue") String refvalue);
}