package hyweb.gip.api.coin;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtils {

	private long a;
	private long b;
	private boolean is_range_ip = false;

	public IPUtils(InetAddress addr) {
		a = ip_to_long(addr);
	}

	// xxx.xxx.xxx.xxx/24
	// xxx.xxx.xxx.xxx
	
	public IPUtils(String host) {
		int ps = 0;
		String ip = host;
		if ((ps = host.indexOf('/')) >= 0) {
			ip = host.substring(0, ps);
			is_range_ip = true;
		}
		a = ip_to_long(ip);
		if (is_range_ip) {
			String range = host.substring(ps+1).trim();
			b = (a & 0xFFFFFF00) + Integer.parseInt(range);
		}
	}

	public boolean match(String host) {
		try {
			return match(InetAddress.getByName(host));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean match(InetAddress address) {
		if (is_range_ip) {
			long ip = ip_to_long(address);
			return (a <= ip && ip <= b);
		} else {
			return a == ip_to_long(address);
		}
	}

	public boolean isRangeIP() {
		return is_range_ip;
	}

	public long ip_to_long(InetAddress address) {
		byte[] addr = address.getAddress();
		long val = addr[0] & 0xFF;
		val = (val << 8) + (addr[1] & 0xFF) ;
		val = (val << 8) + (addr[2] & 0xFF);
		val = (val << 8) + (addr[3] & 0xFF);
		return val;
	}

	public long ip_to_long(String host) {
		try {
			return ip_to_long(InetAddress.getByName(host));
		} catch (UnknownHostException ex) {
			return -1;
		}
	}

	public static String long_to_ip_string(long ip) {
		return ((ip >> 24) & 0xFF) + "."
				+ ((ip >> 16) & 0xFF) + "."
				+ ((ip >> 8) & 0xFF) + "."
				+ (ip & 0xFF);
	}

	public static void main(String[] args) throws Exception {
		IPUtils ipu = new IPUtils("10.10.5.0/24");
		System.out.println(IPUtils.long_to_ip_string(ipu.a));
		System.out.println(IPUtils.long_to_ip_string(ipu.b));
		System.out.println(ipu.match("10.10.5.10"));
		InetAddress addr_155 = InetAddress.getByName("10.10.5.155");
		System.out.println(ipu.match(addr_155));
		IPUtils ipu2 = new IPUtils("10.10.27.27");
		System.out.println(IPUtils.long_to_ip_string(ipu2.a));
		System.out.println(ipu2.match("10.10.27.27"));
		System.out.println(ipu2.match("10.10.27.28"));
	}
	
}
