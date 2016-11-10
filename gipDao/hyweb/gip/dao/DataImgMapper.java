package hyweb.gip.dao;

import java.util.LinkedList;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.mybatis.table.DataImg;

@Repository
public interface DataImgMapper extends Mapper<Long, DataImg> {

	LinkedList<DataImg> selectByData(@Param(value = "didataid") Long didataid, @Param(value = "ditablename") String ditablename);

	LinkedList<DataImg> getByDsId(Long didataid);

	LinkedList<Map<Object, Object>> selectMapByDsId(Long dsid);

	DataImg getMainImgByDsId(Long didataid);
	
	DataImg getByDsIdAndIseq(@Param(value = "didataid") Long didataid, @Param(value = "diimgseq") String diimgseq);
	
	Map<Object, Object> selectMapByDataidAndImgSeq(@Param(value = "didataid") Long didataid, @Param(value = "diimgseq") String diimgseq, @Param(value = "ditablename") String ditablename);
	
	Map<Object, Object> selectMapByImgSeq(@Param(value = "diimgseq") String diimgseq);
	
	LinkedList<DataImg> getNewsImgByDsId(Long didataid);
	
	LinkedList<DataImg> selectByTableName(@Param(value = "didataid") Long didataid, @Param(value = "ditablename") String ditablename);

	DataImg getByDsIdAndImgseq(@Param(value = "didataid") Long didataid, @Param(value = "diimgseq") String diimgseq);
}