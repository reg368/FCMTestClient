package hyweb.gip.dao.service.impl;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.DataFileMapper;
import hyweb.gip.dao.service.DataFileService;
import hyweb.gip.pojo.mybatis.table.DataFile;

public class DataFileServiceImpl implements DataFileService {

	@Autowired
	private DataFileMapper dataFileMapper;
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public LinkedList<DataFile> selectByData(Long dfdataid, String ispublic) {

		return dataFileMapper.selectByData(dfdataid, ispublic);
	}

}
