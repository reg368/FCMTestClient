package hyweb.gip.dao.service;

import hyweb.gip.pojo.mybatis.table.FileCenter;

import java.util.LinkedList;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

public interface FileCenterService {
	@Transactional
	public int deleteByPrimaryKey(Integer fileseq);
	@Transactional
	public int insert(FileCenter record);
	@Transactional
	public FileCenter selectByPrimaryKey(Integer fileseq);
	@Transactional
	public int updateByPrimaryKey(FileCenter record);
	@Transactional
	public LinkedList<FileCenter> selectAll();
	@Transactional
	public FileCenter selectBySidAndFileId(String sid, String fileId);
	@Transactional
	public Map<String, String> getFileName(String fileId);
}
