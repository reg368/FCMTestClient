package hyweb.gip.dao.service;

import hyweb.gip.pojo.mybatis.table.DataFile;
import java.util.LinkedList;

import org.springframework.transaction.annotation.Transactional;

public interface DataFileService {
	@Transactional
	public LinkedList<DataFile> selectByData(Long dfdataid, String ispublic); 
}
