package hyweb.core.kit;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
/**
 * 動態載入相關
 * @author A0074
 * @version 1.0.110715
 * @since xBox 1.0
 */
public final class ClassKit {
	private ClassKit(){
		super();
	}

	/**
	 * 取得目前Thread包含的Loader
	 * @return
	 */
	private static ClassLoader getStandardClassLoader(){
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * 取得載入ClassKit的Loader
	 * @return
	 */
	private static ClassLoader getFallbackClassLoader(){
		return ClassKit.class.getClassLoader();
	}

	/**
	 * 載入class
	 * @param className 類別全名
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> loadClass(String className) throws ClassNotFoundException{
		Class<?> clazz;
		try {
			clazz = Class.forName(className, true, ClassKit.getStandardClassLoader());
		} catch (ClassNotFoundException e) {
			clazz = Class.forName(className, true, ClassKit.getFallbackClassLoader());
		}
		return clazz;
    }

	/**
	 * 產生實體
	 * @param className 類別全名
	 * @return 
	 */
	public static Object createNewInstance(String className) throws SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException{
		return ClassKit.createNewInstance(className, new Class[0],  new Object[0]);
	}

	public static Object createNewInstance(Class<?> c) throws SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException{
		return ClassKit.createNewInstance(c, new Class[0],  new Object[0]);
	}

	public static Object createNewInstance(Class<?> c, Class<?>[] argTypes, Object[] args) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException{
		Constructor<?> con = c.getDeclaredConstructor(argTypes);
		con.setAccessible(true);
		return con.newInstance(args);
    }

	/**
	 * 產生實體
	 * @param className 類別全名
	 * @param argTypes 建構子的類別陣列
	 * @param args 建構子的實體陣列
	 * @return
	 */
	public static Object createNewInstance(String className, Class<?>[] argTypes, Object[] args) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException{
		return ClassKit.createNewInstance(loadClass(className), argTypes, args);
    }

	public static List<Class<?>> getAllClass(ClassLoader cl,String packageName) throws ClassNotFoundException{
		List<Class<?>> ret = new ArrayList<Class<?>>();
		File directory = new File(cl.getResource(packageName.replace('.', '/')).getFile());
		if(directory.exists()) {
			String[] files=directory.list();
			for(int i=0 ; i<files.length ; i++) {
				if(files[i].endsWith(".class")) {
					ret.add(Class.forName(packageName+'.'+files[i].substring(0, files[i].length()-6)));
				}
			}
		} else {
			throw new ClassNotFoundException("package " + packageName + " is not exist in the class loader");
		}
		return ret;
	}

	public static List<Class<?>> getAllClass(Class<?> c,String packageName) throws ClassNotFoundException, MalformedURLException, URISyntaxException{
		String url = c.getResource("").toString();
		String selfPackage = c.getPackage().getName().replace('.', '/') + "/";
		if(url.startsWith("jar:")){
			url = url.replaceFirst(selfPackage, "");
			return getAllClassFromJar(url.substring(4, url.length() - 2), packageName);
		}
		url = url.replaceFirst(selfPackage, packageName.replace('.', '/') + "/");
		List<Class<?>> ret = new ArrayList<Class<?>>();
		File directory = new File(new URL(url).toURI());
		if(directory.exists()) {
			String[] files=directory.list();
			for(int i=0 ; i<files.length ; i++) {
				if(files[i].endsWith(".class")) {
					ret.add(Class.forName(packageName+'.'+files[i].substring(0, files[i].length()-6)));
				}
			}
		} else {
			throw new ClassNotFoundException("package " + packageName + " is not exist in the class loader");
		}
		return ret;
	}

	private static List<Class<?>> getAllClassFromJar(String jreUrl, String packageName) {
		List<Class<?>> ret = new ArrayList<Class<?>>();
		try{
			JarInputStream jis = null;
			try{
				packageName = packageName.replace('.', '/') + "/";
				jis = new JarInputStream(new FileInputStream(new File(new URL(jreUrl).toURI())));
				JarEntry jarEntry;
				while(true){
					jarEntry=jis.getNextJarEntry ();
					if(jarEntry == null){
						break;
					}
					String entryName = jarEntry.getName();
					if(entryName.startsWith(packageName) && entryName.endsWith(".class")) {
						ret.add(Class.forName(entryName.replace('/', '.').substring(0, entryName.length() - 6)));
					}
				}
			}finally{
				if(jis != null){
					jis.close();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ret;
	}
}
