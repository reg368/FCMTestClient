package hyweb.core.net;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * 簡化過的IPv6Address
 * @author janvanbesien@gmail.com
 * @author A0074
 * @see http://code.google.com/p/java-ipv6/
 */
public final class IPv6Address implements Comparable<IPv6Address> {
	private static final int N_SHORTS = 8;
	protected final long highBits;
	protected final long lowBits;

	/**
	 * 透過高低位元建立實體, 因為 ipv6需要兩個long來保存
	 * @param highBits highest order bits
	 * @param lowBits lowest order bits
	 */
	IPv6Address(final long highBits, final long lowBits) {
		this.highBits = highBits;
		this.lowBits = lowBits;
	}

	/**
	 * 透過字串建立實體
	 * For example "1234:5678:abcd:0000:9876:3210:ffff:ffff" or "2001::ff" or even "::".
	 * @param ip string representation
	 * @param needValidate 是否需要驗證
	 */
	public IPv6Address(final String ip, final boolean needValidate) {
		if (ip == null){
			throw new IllegalArgumentException("can not parse [null]");
		}
		if(this.isIPv4(ip)){
			this.highBits = 0;
			this.lowBits = this.ipv4ToIpv6(ip);
		}else{
			final long[] longs = this.tryParseStringArrayIntoLongArray(ip, this.expandShortNotation(ip));
			if(needValidate){
				this.validateLongs(longs);
			}
			long high = 0L;
			long low = 0L;
			for (int i = 0; i < longs.length; i++) {
				if (i >= 0 && i < 4){
					high |= (longs[i] << ((longs.length - i - 1) * 16));
				}else {
					low |= (longs[i] << ((longs.length - i - 1) * 16));
				}
			}
			this.highBits = high;
			this.lowBits = low;
		}
	}

	/**
	 * 透過InetAddress 轉換
	 * @param inetAddress
	 */
	public IPv6Address(final InetAddress inetAddress) {
		this(inetAddress.getHostAddress(), false);
	}

	/**
	 * 轉為 InetAddress
	 * @return
	 * @throws UnknownHostException
	 */
	public InetAddress toInetAddress() throws UnknownHostException {
		return Inet6Address.getByName(this.toString());
	}

	/**
	 * 以完整的表示法表達IPv6
	 * @return
	 */
	public String toLongString() {
		final short[] shorts = toShortArray();
		final String[] strings = new String[shorts.length];
		for (int i = 0; i < shorts.length; i++) {
			strings[i] = String.format("%04x", shorts[i]);
		}

		final StringBuilder result = new StringBuilder();
		for (int i = 0; i < strings.length - 1; i++) {
			result.append(strings[i]).append(":");
		}
		result.append(strings[strings.length - 1]);
		return result.toString();
	}

	/**
	 * 驗證格式是否正確
	 * @param longs
	 */
	private void validateLongs(long[] longs) {
		if(longs.length != 8) {
			throw new IllegalArgumentException("an IPv6 address should contain 8 shorts ["+ Arrays.toString(longs) + "]");
		}
		for (long l : longs) {
			if(l < 0) {
				throw new IllegalArgumentException("each element should be positive ["+ Arrays.toString(longs) + "]");
			}
			if(l > 0xFFFF) {
				throw new IllegalArgumentException("each element should be less than 0xFFFF ["+ Arrays.toString(longs) + "]");
			}
		}
	}

	/**
	 * 依照IPv4與IPv6的輸入做簡易的IPv4判斷
	 * @param ip
	 * @return
	 */
	private boolean isIPv4(String ip){
		if(ip == null || ip.length() <= 7){
			return false;
		}else{
			for(int i = 1 ; i <= 4 ; i++){
				if('.' == ip.charAt(i)){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 		"0:0:0:0:0:ffff:c0a8:101
	 * @param ip
	 * @return
	 */
	private long ipv4ToIpv6(String ip){
		String[] sub = ip.split("\\.");
		long ret = 281470681743360l;// ffff 0000 0000
		int[] multiplication = {24, 16, 8 ,1};
		for(int i = 0 ; i < sub.length ; i++){
			ret += Integer.parseInt(sub[i], 10) << multiplication[i];
		}
		return ret;
	}
	/**
	 * 
	 * @param string
	 * @param longNotation
	 * @return
	 */
	private long[] tryParseStringArrayIntoLongArray(String string, String longNotation) {
		try {
			String[] strings = longNotation.split(":");
			final long[] longs = new long[strings.length];
			for (int i = 0; i < strings.length; i++) {
				longs[i] = Long.parseLong(strings[i], 16);
			}
			return longs;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("can not parse [" + string + "]");
		}
	}

	/**
	 * 將短ip轉換為長ip
	 * @param string
	 * @return
	 */
	private String expandShortNotation(String string) {
		if (!string.contains("::")) {
			return string;
		} else if (string.equals("::")) {
			return this.generateZeroes(8);
		} else {
			final int numberOfColons = this.countOccurrences(string, ':');
			if (string.startsWith("::")){
				return string.replace("::", this.generateZeroes((7 + 2) - numberOfColons));
			}else if (string.endsWith("::")){
				return string.replace("::", ":" + this.generateZeroes((7 + 2) - numberOfColons));
			}else{
				return string.replace("::", ":" + this.generateZeroes((7 + 2 - 1) - numberOfColons));
			}
		}
	}

	/**
	 * 建0： ,因為IPv6中這是可省略的
	 * @param number
	 * @return
	 */
	private String generateZeroes(int number) {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < number; i++) {
			builder.append("0:");
		}
		return builder.toString();
	}

	/**
	 * 計算有幾個 needle
	 * @param haystack
	 * @param needle
	 * @return
	 */
	private int countOccurrences(String haystack, char needle) {
		int count = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (haystack.charAt(i) == needle) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 
	 * @return
	 */
	private short[] toShortArray() {
		final short[] shorts = new short[N_SHORTS];
		for (int i = 0; i < N_SHORTS; i++) {
			if (i >= 0 && i < 4){
				shorts[i] = (short) (((highBits << i * 16) >>> 16 * (N_SHORTS - 1)) & 0xFFFF);
			}else{
				shorts[i] = (short) (((lowBits << i * 16) >>> 16 * (N_SHORTS - 1)) & 0xFFFF);
			}
		}
		return shorts;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final short[] shorts = toShortArray();
		final String[] strings = new String[shorts.length];
		for (int i = 0; i < shorts.length; i++) {
			strings[i] = String.format("%x", shorts[i]);
		}

		final StringBuilder result = new StringBuilder();
		boolean shortHandNotationUsed = false;
		boolean shortHandNotationBusy = false;
		for (int i = 0; i < strings.length; i++) {
			if (!shortHandNotationUsed && i < N_SHORTS - 1 && "0".equals(strings[i]) && "0".equals(strings[i + 1])) {
				shortHandNotationUsed = true;
				shortHandNotationBusy = true;
				if (i == 0){
					result.append("::");
				}else{
					result.append(":");
				}
			} else if (!("0".equals(strings[i]) && shortHandNotationBusy)) {
				shortHandNotationBusy = false;
				result.append(strings[i]);
				if (i < N_SHORTS - 1){
					result.append(":");
				}
			}
		}
		return result.toString().toLowerCase();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null || getClass() != obj.getClass()){
			return false;
		}
		IPv6Address that = (IPv6Address) obj;
		return (this.highBits == that.highBits) && (this.lowBits == that.lowBits);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = (int) (lowBits ^ (lowBits >>> 32));
		result = 31 * result + (int) (highBits ^ (highBits >>> 32));
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(IPv6Address that) {
		if (this.highBits == that.highBits){
			return this.lowBits == that.lowBits ? 0 : ((this.lowBits < that.lowBits) ^ ((this.lowBits < 0) != (that.lowBits < 0))) ? -1 : 1;
		}else if (this.highBits == that.highBits){
			return 0;
		}else{
			return ((this.highBits < that.highBits) ^ ((this.highBits < 0) != (that.highBits < 0))) ? -1 : 1;
		}
	}
}
