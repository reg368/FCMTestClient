package hyweb.gip.dao.service.impl;

import java.util.LinkedList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.FileCenterMapper;
import hyweb.gip.dao.service.FileCenterService;
import hyweb.gip.pojo.mybatis.table.FileCenter;

public class FileCenterServiceImpl implements FileCenterService {

	@Autowired
	private FileCenterMapper fileCenterMapper;
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(Integer fileseq) {
		return fileCenterMapper.deleteByPrimaryKey(fileseq);
	}

	@Override
	@Transactional
	public int insert(FileCenter record) {
		return fileCenterMapper.insert(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public FileCenter selectByPrimaryKey(Integer fileseq) {
		return fileCenterMapper.selectByPrimaryKey(fileseq);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(FileCenter record) {
		return fileCenterMapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<FileCenter> selectAll() {
		return fileCenterMapper.selectAll();
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public FileCenter selectBySidAndFileId(String sid, String fileId) {
		return fileCenterMapper.selectBySidAndFileId(sid, fileId);
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Map<String, String> getFileName(String fileId) {
		return fileCenterMapper.getFileName(fileId);
	}
}
