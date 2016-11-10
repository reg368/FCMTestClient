package hyweb.util.cacheable;

import hyweb.core.kit.ArrayKit;
import hyweb.core.kit.StringKit;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class BeanHelper {
	protected static String BASE_POJO_CALSS_PACKAGE = "hyweb.gip.pojo.mybatis.table.";
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

	public static Object getBean(Map<String, String[]> req, String tableName) 
			throws IntrospectionException, ClassNotFoundException, 
				InstantiationException, IllegalAccessException, 
				IllegalArgumentException, InvocationTargetException, ParseException {
		
		Class<?> cls = Class.forName(BASE_POJO_CALSS_PACKAGE+tableName);
		System.out.println("===== cls_class  : " + BASE_POJO_CALSS_PACKAGE+tableName);
		System.out.println("===== cls : " + cls.getName());
		BeanInfo beanInfo = Introspector.getBeanInfo(cls, Object.class);
		Object obj = cls.newInstance();
		
		PropertyDescriptor pd = null;
		Method methodSet = null;
		String[] value = null;
		for (PropertyDescriptor prop : beanInfo.getPropertyDescriptors()) {
			pd = new PropertyDescriptor(prop.getName(), obj.getClass());
			methodSet = pd.getWriteMethod();
			value = req.get(prop.getName());
				if (value != null) {
					
					if(value.length == 1){
						String result = value[0];
							if (prop.getPropertyType().equals(String.class)) {
								methodSet.invoke(obj, result);
							}else if (prop.getPropertyType().equals(Integer.class) || prop.getPropertyType().getName().equals("int")) {
								if (StringKit.isEmpty(result) == false) {
									methodSet.invoke(obj, Integer.parseInt(result));
								}							
							} else if (prop.getPropertyType().equals(Date.class)) {
								methodSet.invoke(obj, "".equals(result) ?  null:dateFormat.parse(result));
							}else if (prop.getPropertyType().equals(Long.class)){
								methodSet.invoke(obj, Long.parseLong(result));
							} else if (prop.getPropertyType().equals(Double.class) || prop.getPropertyType().getName().equals("double")){
								methodSet.invoke(obj, Double.parseDouble(result));
							}
						
					}else if (value.length > 1) {
						String result = ArrayKit.join(value, 0, value.length, ",");
						methodSet.invoke(obj, result);
					}
				}
		}
		
		return obj;
	}

	/**
	 * 取值
	 * @param bean
	 * @param name
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	public static Object getAttr(Object bean, String name) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException{
		if(name == null || "".equals(name)){
			return "";
		}
		Field field = bean.getClass().getDeclaredField(name.toLowerCase());
		field.setAccessible(true);
		return field.get(bean);
	}
	
	/**
	 * 取值
	 * @param bean
	 * @param name
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	public static String getAttrString(Object bean, String name) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException{
		Object attr = BeanHelper.getAttr(bean, name);
		String value = null;
		if (attr != null) {
			value = attr.toString();
		}
		return value;
	}
	
	/**
	 * 判斷欄位是不是空
	 * @param tableName
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static boolean isFieldEmpty(String tableName, String name) throws ClassNotFoundException  {
		Class<?> cls = Class.forName(BASE_POJO_CALSS_PACKAGE+tableName);
		Field field;
		try {
			field = cls.getDeclaredField(name.toLowerCase());
			return field == null;
		} catch (Exception e) {
			return true;
		}
	}
	
	public static void printInfo(Object bean) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
		for (PropertyDescriptor prop : beanInfo.getPropertyDescriptors()) {
			if (prop.getReadMethod() != null) {
				Object obj = prop.getReadMethod().invoke(bean);
				if (obj != null) {
					System.out.println(prop.getReadMethod().getName()+" = "+obj.toString());
				}
			}
		}
	}
}
