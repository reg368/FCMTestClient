package hyweb.gip.dao.service;

import hyweb.gip.pojo.custom.UgrpApQuery;
import hyweb.gip.pojo.mybatis.table.UgrpAp;
import hyweb.gip.pojo.mybatis.table.UgrpApKey;

import java.util.LinkedList;

import org.springframework.transaction.annotation.Transactional;

public interface UgrpApService {
	@Transactional
	public int deleteByPrimaryKey(UgrpApKey key);
	@Transactional
	public int insert(UgrpAp record);
	@Transactional
	public UgrpAp selectByPrimaryKey(UgrpApKey key);
	@Transactional
	public int updateByPrimaryKey(UgrpAp record);
	@Transactional
	public LinkedList<UgrpAp> selectByUgrpId(int ugrpid);
	@Transactional
	public UgrpAp selectUgrpApByUgrpId(UgrpApQuery record);
}
