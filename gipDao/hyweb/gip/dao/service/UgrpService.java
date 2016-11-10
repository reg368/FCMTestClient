package hyweb.gip.dao.service;

import hyweb.gip.pojo.mybatis.table.Ugrp;
import java.util.LinkedList;
import org.springframework.transaction.annotation.Transactional;

public interface UgrpService {
	
	@Transactional
    public int deleteByPrimaryKey(Integer ugrpid);
	
	@Transactional
    public int insert(Ugrp record);
	
	@Transactional
    public Ugrp selectByPrimaryKey(Integer ugrpid);

	@Transactional
    public int updateByPrimaryKey(Ugrp record);
    
	@Transactional
    public LinkedList<Ugrp> selectAll();
	
	@Transactional
    public LinkedList<Ugrp> list(int page, int showNum);
	
	@Transactional
    public int countAll();
	
	@Transactional
    public LinkedList<Ugrp> selectAllUgrp();
	
	@Transactional
	public LinkedList<Ugrp> selectByUser(String userid);
}
