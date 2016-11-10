package hyweb.gip.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface SeqMapper {
	public Long getSeq(String name);
	public String getNextOrderNum();
}