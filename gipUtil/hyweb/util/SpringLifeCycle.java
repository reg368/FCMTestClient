package hyweb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 靜態化 ApplicationContext
 * @author A0074
 *
 */
public class SpringLifeCycle implements ApplicationContextAware {
	//private static final Logger LOG = LoggerFactory.getLogger(HttpSessionHandle.class);
	private static ApplicationContext CONTEXT;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringLifeCycle.CONTEXT = applicationContext;
		//LOG.info("applicationContext inject");
	}

	/**
	 * 取得sping中註冊的Bean 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) SpringLifeCycle.CONTEXT.getBean(name);
	}
}
