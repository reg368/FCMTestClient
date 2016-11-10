package hyweb.gip.dao;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.mybatis.table.DataFile;
@Repository
public interface DataFileMapper extends Mapper<Long, DataFile>{
	
    LinkedList<DataFile> selectByData(@Param("dfdataid") Long dfdataid, @Param("ispublic") String ispublic); 
}