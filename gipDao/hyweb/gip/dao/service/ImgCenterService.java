package hyweb.gip.dao.service;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import hyweb.gip.pojo.mybatis.table.ImgCenter;

public interface ImgCenterService {
	@Transactional
	public int deleteByPrimaryKey(Integer imgseq);
	@Transactional
	public int insert(ImgCenter record);
	@Transactional
	public ImgCenter selectByPrimaryKey(Integer imgseq);
	@Transactional
	public int updateByPrimaryKey(ImgCenter record);
	@Transactional
	public LinkedList<ImgCenter> selectAll();
	@Transactional
	public LinkedList<ImgCenter> selectByKey(String key);
	@Transactional
	public LinkedList<ImgCenter> selectByDept(String depthierarchycode);
	@Transactional
	public LinkedList<ImgCenter> query(String select,String where,
			String orderby,String depthierarchycode,int page,int showNum);
	@Transactional
	public int count(String where,String depthierarchycode);
	
	@Transactional
	public ImgCenter selectBySidAndImgId(String sid, String imgId);
	
	@Transactional
	public String getFileName(String imgId);
}
