package hyweb.util.cacheable;

import hyweb.gip.dao.Mapper;
import hyweb.util.SpringLifeCycle;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;



public class MybatisHelper {
	protected static ConcurrentHashMap<String, Class<?>> CLASS_MAPPER;
	protected static String BASE_DAO_CALSS_PACKAGE;
	protected static SqlSessionFactory SESSION_FACTORY;
	static{
		CLASS_MAPPER = new ConcurrentHashMap<String, Class<?>>(20);
		BASE_DAO_CALSS_PACKAGE = "hyweb.gip.dao.";
		try {
			SESSION_FACTORY = (SqlSessionFactory)SpringLifeCycle.getBean("sqlSessionFactory");
//			SESSION_FACTORY = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis_mapper.xml"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param tableName
	 * @return
	 * @throws ClassNotFoundException
	 */
	protected static Class<?> loadClass(String tableName) throws ClassNotFoundException {
		Class<?> c = CLASS_MAPPER.get(tableName);
		if(c == null){
			c = Class.forName(BASE_DAO_CALSS_PACKAGE + tableName + "Mapper", true, Thread.currentThread().getContextClassLoader());
		}
		return c;
	}

	/**
	 * 
	 * @param session
	 * @param tableName
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <S,T> Mapper<S,T>getMapper(SqlSession session, String tableName) throws ClassNotFoundException{
		Class<?> c = loadClass(tableName);
		return (Mapper<S,T>) session.getMapper(c);
	}

	/**
	 * 提供一個開啟session 的對象 
	 * @return
	 */
	public static MybatisHelper newSession(){
		return new MybatisHelper();
	}

	private SqlSession session = null;

	private MybatisHelper(){
		super();
	}
	/**
	 * add,需自行呼叫commit
	 * @param pojo
	 * @param tableName
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MybatisHelper insert(Object pojo, String tableName) throws ClassNotFoundException{
		this.session = SESSION_FACTORY.openSession();
		Mapper mapper = MybatisHelper.getMapper(this.session, tableName);
		mapper.insert(pojo);
		return this;
	}
	/**
	 * getAll抓取list列表
	 * @param tableName
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public List getAll(String tableName) throws ClassNotFoundException{
		this.session = SESSION_FACTORY.openSession();
		Mapper mapper = MybatisHelper.getMapper(this.session, tableName);
		return mapper.getAll();
	}
	/**
	 * update,需自行呼叫commit
	 * @param pojo
	 * @param tableName
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MybatisHelper update(Object pojo, String tableName) throws ClassNotFoundException{
		this.session = SESSION_FACTORY.openSession();
		Mapper mapper = MybatisHelper.getMapper(this.session, tableName);
		mapper.update(pojo);
		return this;
	}
	/**
	 * delete,需自行呼叫commit
	 * @param pojo
	 * @param tableName
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MybatisHelper delete(Object id, String tableName) throws ClassNotFoundException{
		this.session = SESSION_FACTORY.openSession();
		Mapper mapper = MybatisHelper.getMapper(this.session, tableName);
		mapper.delete(id);
		return this;
	}
	
	/**
	 * getById
	 * @param pojo
	 * @param tableName
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getById(Object id, String tableName) throws ClassNotFoundException{
		this.session = SESSION_FACTORY.openSession();
		Mapper mapper = MybatisHelper.getMapper(this.session, tableName);
		return mapper.getById(id);
	}
	
	/**
	 * query抓取list列表
	 * @param tableName
	 * @param select
	 * @param where
	 * @param orderby
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public List query(String tableName, String select, String where, String orderby, int page, int showNum) throws ClassNotFoundException{
		this.session = SESSION_FACTORY.openSession();
		Mapper mapper = MybatisHelper.getMapper(this.session, tableName);
		return mapper.query(select, where, orderby, page, showNum);
	}
	/**
	 * count抓取總數
	 * @param tableName
	 * @param where
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public int count(String tableName, String where) throws ClassNotFoundException{
		this.session = SESSION_FACTORY.openSession();
		Mapper mapper = MybatisHelper.getMapper(this.session, tableName);
		return mapper.count(where);
	}
	
	/**
	 * 送出
	 */
	public void commit(){
		try{
			this.session.commit();
		}catch(Exception ex){
			this.session.rollback();
			throw new RuntimeException(ex);
		}finally{
			this.session.close();
		}
	}
	/**
	 * 關閉連線
	 */
	public void close(){
		if (this.session != null) {
			this.session.close();
		}
	}
	
	

}
