package hyweb.gip.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.mybatis.table.ProcessCodeMain;
@Repository
public interface ProcessCodeMainMapper extends Mapper<Integer, ProcessCodeMain>{
	String getPath(@Param(value = "codamainid") int codamainid);
}