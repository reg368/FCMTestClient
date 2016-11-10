package hyweb.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 
 * @author A0074
 *
 */
public class ThreadPool {
	final private static ExecutorService THREAD_POOL;
	static{
		THREAD_POOL = Executors.newFixedThreadPool(20); 
	}

	/**
	 * 新增一個工作
	 * @param command
	 */
	public static void addCommand(Runnable command){
		THREAD_POOL.execute(command);
	}

	/**
	 * 關閉thread pool
	 */
	public static void showdown(){
		THREAD_POOL.shutdownNow();
	}
}
