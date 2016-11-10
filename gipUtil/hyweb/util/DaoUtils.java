package hyweb.util;


import hyweb.gip.dao.Mapper;
import hyweb.util.cacheable.BeanHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.JSONArray;

public class DaoUtils {
	
	public static List<Map<String, Object>> rows(String sql , Object ... params ) throws SQLException{
		 SqlSession sqlSession = null;
		try{
			QueryRunner run = new QueryRunner();
			sqlSession = ((SqlSessionFactory)SpringLifeCycle.getBean("sqlSessionFactory")).openSession();
			Connection conn = sqlSession.getConnection();
			return run.query(conn, sql, new MapListHandler(), params);
		} finally{
			if(sqlSession!=null){
				sqlSession.commit();
				sqlSession.close();
			}
		}
	}
	
		
	public static List<Map<String, Object>> rows(Connection conn,String sql , Object ... params ) throws SQLException{
		return  new QueryRunner().query(conn, sql, new MapListHandler(), params);
	}
	
	
	public static Map<String, Object> row(String sql , Object ... params ) throws SQLException{
		 SqlSession sqlSession = null;
		try{
			QueryRunner run = new QueryRunner();
			sqlSession = ((SqlSessionFactory)SpringLifeCycle.getBean("sqlSessionFactory")).openSession();
			Connection conn = sqlSession.getConnection();
			return run.query(conn, sql, new MapHandler(), params);
		} finally{
			if(sqlSession!=null){
				sqlSession.commit();
				sqlSession.close();
			}
		}
	}
	
	public static Map<String, Object> row(Connection conn,String sql , Object ... params ) throws SQLException{
		return  new QueryRunner().query(conn, sql, new MapHandler(), params);
	}
	
	public static int execute(String sql , Object ... params ) throws SQLException{
		 SqlSession sqlSession = null;
		try{
			QueryRunner run = new QueryRunner();
			sqlSession = ((SqlSessionFactory)SpringLifeCycle.getBean("sqlSessionFactory")).openSession();
			Connection conn = sqlSession.getConnection();
			return run.update(conn, sql, params);
		} finally{
			if(sqlSession!=null){
				sqlSession.commit();
				sqlSession.close();
			}
		}
	}
	
	
	public static  int execute(Connection conn, String sql , Object ... params ) throws SQLException{
		return  new QueryRunner().update(conn, sql, new MapHandler(), params);
	}
	
	public static int[] batch(String sql , Object[][] params) throws SQLException{
		SqlSession sqlSession = null;
		try{
			QueryRunner run = new QueryRunner();
			sqlSession = ((SqlSessionFactory)SpringLifeCycle.getBean("sqlSessionFactory")).openSession();
			Connection conn = sqlSession.getConnection();
			return run.batch(conn,sql, params);
		} finally{
			if(sqlSession!=null){
				sqlSession.commit();
				sqlSession.close();
			}
		}
	}
	
	public static  int[] batch(Connection conn, String sql , Object[][] params) throws SQLException{
		return  new QueryRunner().batch(conn, sql, params);
	}
	
	public static int mybatis_add(String tableId , Object bean , String name) throws Exception {
		String classId = "hyweb.gip.dao." + tableId+"Mapper" ; 
		SqlSession sqlSession = null;
		try{
			sqlSession = ((SqlSessionFactory)SpringLifeCycle.getBean("sqlSessionFactory")).openSession();
			int ret = sqlSession.insert(classId+".add", bean);
			BeanMap m = BeanMap.create(bean);
			return (Integer) m.get(name);
		} finally{
			if(sqlSession!=null){
				sqlSession.commit();
				sqlSession.close();
			}
		}
	}
	
	
	public static JSONArray ja(String sql , Object ... params ) throws SQLException{
		 SqlSession sqlSession = null;
		try{
			sqlSession = ((SqlSessionFactory)SpringLifeCycle.getBean("sqlSessionFactory")).openSession();
			Connection conn = sqlSession.getConnection();
			return ja(conn,sql,params);
		} finally{
			if(sqlSession!=null){
				sqlSession.commit();
				sqlSession.close();
			}
		}
	}
	
		
	public static JSONArray ja(Connection conn,String sql , Object ... params ) throws SQLException{
		List<Map<String,Object>> ret =   new QueryRunner().query(conn, sql, new MapListHandler(), params);
		return new JSONArray(ret);
	}
	

	
}
