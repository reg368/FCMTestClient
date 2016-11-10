package hyweb.gip.dao;

import hyweb.gip.pojo.mybatis.table.CodeMain;

import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface CodeMainMapper {
    int deleteByPrimaryKey(Integer codeseq);

    int insert(CodeMain record);

    CodeMain selectByPrimaryKey(Integer codeseq);

    int updateByPrimaryKey(CodeMain record);
    
    LinkedList<CodeMain> selectAll();
    
    LinkedList<CodeMain> selectShowValueByPrimaryKey(Integer codeseq);
    
    LinkedList<CodeMain> selectByCodeId(String codeid);
    
    LinkedList<CodeMain> selectForRef(@Param("codeid") String codeid, @Param("codevalue") String codevalue);
    
    LinkedList<CodeMain> list(@Param("page") int page, @Param("showNum") int showNum);
    
    int countAll();
    
    LinkedList<CodeMain> listByCodeId(@Param("codeid") String codeid,@Param("page") int page, @Param("showNum") int showNum);
    
    int countByCodeId(@Param("codeid") String codeid);
    
    String getCodeShowBySub(@Param("refvalue") String refvalue, @Param("codevalue") String codevalue);
    
    String getAlert(@Param("codeid") String codeid, @Param("codevalue") String codevalue);

    List<String> getInAlert(@Param("codeid") String codeid, @Param("codevalue") String codevalue);
}