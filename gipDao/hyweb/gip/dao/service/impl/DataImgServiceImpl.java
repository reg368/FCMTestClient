package hyweb.gip.dao.service.impl;

import java.util.LinkedList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.DataImgMapper;
import hyweb.gip.dao.service.DataImgService;
import hyweb.gip.pojo.mybatis.table.DataImg;

public class DataImgServiceImpl implements DataImgService {

	@Autowired
	private DataImgMapper dataImgMapper;
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<DataImg> selectByData(Long didataid, String ditablename) {
		return dataImgMapper.selectByData(didataid,ditablename);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<DataImg> getByDsId(Long didataid){
		return dataImgMapper.getByDsId(didataid);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<Map<Object, Object>> selectMapByDsId(Long dsid) {
		return dataImgMapper.selectMapByDsId(dsid);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public DataImg getMainImgByDsId(Long didataid) {
		return dataImgMapper.getMainImgByDsId(didataid);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public DataImg getByDsIdAndIseq(Long didataid, String diimgseq) {
		return dataImgMapper.getByDsIdAndIseq(didataid, diimgseq);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Map<Object, Object> selectMapByDataidAndImgSeq(Long didataid, String diimgseq, String ditablename) {
		Map<Object, Object> mapImg = dataImgMapper.selectMapByDataidAndImgSeq(didataid, diimgseq, ditablename);
		
		if (mapImg == null) {
			mapImg = dataImgMapper.selectMapByImgSeq(diimgseq);
		}
		return mapImg;
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<DataImg> getNewsImgByDsId(Long didataid) {
		return dataImgMapper.getNewsImgByDsId(didataid);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<DataImg> selectByTableName(Long didataid,
			String ditablename) {
		return dataImgMapper.selectByTableName(didataid, ditablename);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public DataImg getByDsIdAndImgseq(Long didataid, String diimgseq) {
		return dataImgMapper.getByDsIdAndImgseq(didataid, diimgseq);
	}
}
