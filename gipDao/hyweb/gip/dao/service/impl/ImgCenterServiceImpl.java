package hyweb.gip.dao.service.impl;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.ImgCenterMapper;
import hyweb.gip.dao.service.ImgCenterService;
import hyweb.gip.pojo.mybatis.table.ImgCenter;

public class ImgCenterServiceImpl implements ImgCenterService {
	
	@Autowired
	private ImgCenterMapper imgCenterMapper;
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(Integer imgseq) {
		return imgCenterMapper.deleteByPrimaryKey(imgseq);
	}

	@Override
	@Transactional
	public int insert(ImgCenter record) {
		return imgCenterMapper.insert(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public ImgCenter selectByPrimaryKey(Integer imgseq) {
		return imgCenterMapper.selectByPrimaryKey(imgseq);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(ImgCenter record) {
		return imgCenterMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<ImgCenter> selectAll() {
		return imgCenterMapper.selectAll();
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<ImgCenter> selectByKey(String key) {
		key = "%"+ key +"%";
		return imgCenterMapper.selectByKey(key);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<ImgCenter> selectByDept(
			String depthierarchycode) {
		return imgCenterMapper.selectByDept("'"+ depthierarchycode +".%'"," '"+depthierarchycode+"' ");
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<ImgCenter> query(String select, String where,
			String orderby,String depthierarchycode, int page, int showNum) {
		return imgCenterMapper.query(select, where, orderby, " '"+ depthierarchycode +".%'"," '"+depthierarchycode+"' ", page, showNum);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public int count(String where, String depthierarchycode) {
		return imgCenterMapper.count(where, " '"+ depthierarchycode +".%'"," '"+depthierarchycode+"' ");
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public ImgCenter selectBySidAndImgId(String sid, String imgId) {
		return imgCenterMapper.selectBySidAndImgId(sid, imgId);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String getFileName(String imgId) {
		return imgCenterMapper.getFileName(imgId);
	}
}
