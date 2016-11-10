package hyweb.core.net;

import java.util.List;

/**
 * IP管理器
 * @author A0074
 * @version 1.0.100409
 * @since xBox 1.0
 */
public interface IpFilter {
	/**
	 * 是否允許
	 * @return
	 */
	public boolean accept(String ip);

	/**
	 * 
	 * @param ip
	 * @return
	 */
	public int add(String ip);

	/**
	 * 
	 * @param ipArray
	 * @return
	 */
	public int add(String[] ipArray);

	/**
	 * 
	 * @param ipList
	 * @return
	 */
	public int add(List<String> ipList);
}
