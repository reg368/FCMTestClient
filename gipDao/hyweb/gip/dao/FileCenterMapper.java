package hyweb.gip.dao;

import java.util.LinkedList;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.mybatis.table.FileCenter;
@Repository
public interface FileCenterMapper {
    int deleteByPrimaryKey(Integer fileseq);

    int insert(FileCenter record);

    FileCenter selectByPrimaryKey(Integer fileseq);

    int updateByPrimaryKey(FileCenter record);
    
    LinkedList<FileCenter> selectAll();
    
    FileCenter selectBySidAndFileId(@Param("sid") String sid, @Param("fileId") String fileId);
    
    Map<String, String> getFileName(String fileId);
}