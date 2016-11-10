package hyweb.gip.pattern.service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Map;

import hyweb.util.cacheable.MybatisHelper;


/**
 * Service
 * 可用於Add、Edit、Delete頁面
 */
public interface ServiceEvent {
	
	public void execute(String tableName, MybatisHelper mybatis, Map<String, String[]> req)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException, ParseException;

}
