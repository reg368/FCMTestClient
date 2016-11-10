package hyweb.core.net;

/**
 * 用IPv6Address當核心的範圍
 * @author A0074
 * @since xBox 1.0
 */
public class IPv6AddressPair {
	IPv6Address bigger;
	IPv6Address samller;
	public IPv6AddressPair(IPv6Address first, IPv6Address second){
		super();
		if(second == null){
			this.bigger = first;
			this.samller = null;
		}else{
			int ret = first.compareTo(second);
			if(ret == 0){
				this.bigger = first;
				this.samller = null;
			}else if(ret > 0){
				this.bigger = first;
				this.samller = second;
			}else{
				this.bigger = second;
				this.samller = first;
			}			
		}
	}

	public IPv6AddressPair(String first, String second){
		this(new IPv6Address(first, true), new IPv6Address(second, true));
	}

	public boolean contain(IPv6Address ip){
		int ret = this.bigger.compareTo(ip);
		if(ret == 0){
			return true;
		}else if(ret < 0){
			return false;
		}else{
			if(this.samller == null){
				return false;
			}else{
				return this.samller.compareTo(ip) <= 0;
			}
		}
	}
}
