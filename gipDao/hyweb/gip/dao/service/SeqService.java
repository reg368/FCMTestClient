package hyweb.gip.dao.service;

import org.springframework.transaction.annotation.Transactional;

public interface SeqService {
	@Transactional
	public Long getSeq(String name);
	
	@Transactional
	public String getNextOrderNum();
}
