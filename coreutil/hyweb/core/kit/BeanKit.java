package hyweb.core.kit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public final class BeanKit {
	private BeanKit(){
		super();
	}

	/**
	 *  依照 request 內的資料填充帶入的bean
	 * @param bean
	 * @param request
	 * @throws ParseException 
	 */
	public static void fillBean(Object bean, HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, ParseException{
		Field[] fields = bean.getClass().getDeclaredFields();
		String key;
		String[] val;
		for(Field field : fields){
			key = field.getName();
			val = request.getParameterValues(key);
			if(val != null){
				if(val.length >= 1){
					field.setAccessible(true);
					if(val.length == 1){
						BeanKit.fieldSetValue(bean, field, val[0]);
					}else{
						field.set(bean, ArrayKit.join(val, ","));
					}
				}
			}
		}
	}
/*
	public static <T>List<T> fillBean(Class<?> c, HttpServletRequest request, String[] singleValueParam, String[] cantNullParam) throws PutKitException{
		HashSet<String> singleValueParamSet = new HashSet<>();
		for(String singleValue : singleValueParam){
			singleValueParamSet.add(singleValue);
		}
		HashSet<String> cantNullParamSet = new HashSet<>();
		for(String cantNull : cantNullParam){
			cantNullParamSet.add(cantNull);
		}

		List<T> ret = new ArrayList<T>();
		Field[] fields = c.getDeclaredFields();
		String key;
		String[] val;
		int len = 0, maxLen = 0;
		for(Field field : fields){
			key = field.getName();
			val = request.getParameterValues(key);
			if(val == null){
				if(!cantNullParamSet.contains(key)){
					throw new PutKitException(key  + " 沒給值");
				}
			}else if(val.length != 1){
				if(singleValueParamSet.contains(key)){
					throw new PutKitException(key + " 不能有多個值");
				}
			}
		}
		return ret;
	}
*/
	@SuppressWarnings("unchecked")
	public  static<T> List<T> fillBean(Class<?> c, HttpServletRequest request, String testLenField) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException, ParseException{
		int max = 1;
		while(true){
			if(request.getParameter(testLenField + String.valueOf(max)) == null){
				break;
			}
			max++;
		}
		List<T> ret = new ArrayList<T>();
		List<Field> fields = BeanKit.noStaticFieldArray(c.getDeclaredFields());
		String key;
		String val;
		for(int i = 1 ; i < max ; i++){
			T bean = (T)ClassKit.createNewInstance(c);
			for(Field field : fields){
				key = field.getName() + i;
				val = request.getParameter(key);
				field.setAccessible(true);
				BeanKit.fieldSetValue(bean, field, val);
			}
			ret.add(bean);
		}
		return ret;
	}

	/**
	 * 取得不包含static member 的 所有 member
	 * @param fields
	 * @return
	 */
	private static List<Field> noStaticFieldArray(Field[] fields){
		List<Field> ret = new ArrayList<Field>();
		for(Field field : fields){
			if(!Modifier.isStatic(field.getModifiers())){
				ret.add(field);
			}
		}
		return ret;
	}

	private static void fieldSetValue(Object instance, Field field, String value) throws IllegalArgumentException, IllegalAccessException, ParseException{
		if(value == null){
			field.set(instance, null);
			return;
		}
		Class<?> memberType = field.getType();
		if(memberType == String.class){
			field.set(instance, value);
		}else if(memberType == Integer.class){
			if(StringKit.isEmpty(value)){
				field.set(instance, null);
			}else{
				field.set(instance, Integer.parseInt(value));
			}
		}else if(memberType == Date.class){
			if(StringKit.isEmpty(value)){
				field.set(instance, null);
			}else{
				if(value.length() == 10){
					field.set(instance, new SimpleDateFormat("yyyy/MM/dd").parse(value));
				}else{
					field.set(instance, new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(value));
				}				
			}

		}else{
			field.set(instance, value);
		}
	}
}
