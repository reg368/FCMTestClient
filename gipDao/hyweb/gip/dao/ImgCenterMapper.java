package hyweb.gip.dao;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import hyweb.gip.pojo.mybatis.table.ImgCenter;
@Repository
public interface ImgCenterMapper {
	int deleteByPrimaryKey(Integer imgseq);
	int insert(ImgCenter record);
	ImgCenter selectByPrimaryKey(Integer imgseq);
	int updateByPrimaryKey(ImgCenter record);
	LinkedList<ImgCenter> selectAll();
	LinkedList<ImgCenter> selectByKey(String key);
	LinkedList<ImgCenter> query(@Param("select") String select,
								@Param("where") String where,
								@Param("orderby") String orderby,
								@Param("depthierarchycode") String depthierarchycode,
								@Param("depthierarchycode1") String depthierarchycode1,
								@Param("page") int page,
								@Param("showNum") int showNum);
	LinkedList<ImgCenter> selectByDept(@Param("depthierarchycode") String depthierarchycode,
			 @Param("depthierarchycode1") String depthierarchycode1);
	String getByImgSeq(Integer imgseq);
	String getByImgId(String imgId);
	ImgCenter selectBySidAndImgId(@Param("sid") String sid, @Param("imgId") String imgId);
	int count(@Param(value = "where") String where,
			  @Param("depthierarchycode") String depthierarchycode,
			  @Param("depthierarchycode1") String depthierarchycode1);
	String getFileName(String imgId);
}