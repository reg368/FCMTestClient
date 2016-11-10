package hyweb.gip.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


/**
 * @author A0074
 *
 * @param <S> pk 的類型
 * @param <T> pojo
 */
public interface Mapper<S , T> {
	public List<T> getAll();
	public List<T> query(
			@Param(value = "select") String select, 
			@Param(value = "where") String where, 
			@Param(value = "orderby") String orderby,
			@Param(value = "page") int page,
			@Param(value = "showNum") int showNum);
	public int count(@Param(value = "where") String where);
	public T getById(S id);
	public List<T> getByDsId(S id);
	public void insert(T pojo);
	public void update(T pojo);
	public void delete(S id);
}
