package hyweb.gip.pattern.provider;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 資料提供者
 * @author A0074
 *
 */
public interface DataProvider {
	/**
	 * 取得給Mvel用的map
	 * @param req
	 * @param mvelMap
	 * @return
	 */
	public void get(HttpServletRequest req, Map<String, Object> mvelMap);
}
