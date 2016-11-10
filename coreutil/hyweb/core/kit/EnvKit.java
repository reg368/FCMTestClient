package hyweb.core.kit;
/**
 * 處理jdk版本
 * @author A0074
 * @version 1.0.110305
 * @since xBox 1.0
 */
public final class EnvKit {
	private static int JDK_VERSION;
	private static String JDK_VENDOR;
	static{
		JDK_VENDOR = System.getProperty("java.vm.vendor");
		try{
			Class.forName("java.lang.CharSequence");
			JDK_VERSION = 4;
			Class.forName("java.net.Proxy");
			JDK_VERSION = 5;
			Class.forName("java.util.ServiceLoader");
			JDK_VERSION = 6;
		}catch(ClassNotFoundException e){
			JDK_VERSION = -1;
		}
	}

	private EnvKit(){
		super();
	}

	/**
	 * 目前JDK版本是否大於該版本
	 * @param jdkVersion  JDK4=4,JDK5=5,JDK6=6
	 * @return
	 */
	public static boolean isSuppose(int jdkVersion){
		return  jdkVersion >= JDK_VERSION;
	}

	public static boolean isAndroid() {
		return JDK_VENDOR.indexOf("Android") != -1;
	}

	public static boolean isIBM() {
    	return JDK_VENDOR.indexOf("IBM") != -1;
    }
}
