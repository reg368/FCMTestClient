package hyweb.gip.dao.service;

import hyweb.gip.pojo.mybatis.table.DataImg;
import java.util.LinkedList;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

public interface DataImgService {
	@Transactional
    public LinkedList<DataImg> selectByData(Long didataid, String ditablename);
	
	@Transactional
	public LinkedList<DataImg> getByDsId(Long didataid);
	
	@Transactional
	public LinkedList<Map<Object, Object>> selectMapByDsId(Long dsid);
	
	@Transactional
	public DataImg getMainImgByDsId(Long didataid);
	
	@Transactional
	public DataImg getByDsIdAndIseq(Long didataid, String diimgseq);
	
	@Transactional
	public Map<Object, Object> selectMapByDataidAndImgSeq(Long didataid, String diimgseq, String ditablename);

	@Transactional
	public LinkedList<DataImg> getNewsImgByDsId(Long didataid);
	
	@Transactional
	public LinkedList<DataImg> selectByTableName(Long didataid, String ditablename);

	@Transactional
	public DataImg getByDsIdAndImgseq(Long didataid, String diimgseq);

}
