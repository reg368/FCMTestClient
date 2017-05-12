package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;
import java.util.Date;


public class DeviceInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer seq;
	private String userid;
	private String password;
	private String clienttoken;
	private String devicetoken;
	private String clientpaltform;
	private String packagename;
	private Date createdate;
	private Date updatedate;
	private Date lastsenddate;
	private String lastsendtoken;
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getClienttoken() {
		return clienttoken;
	}
	public void setClienttoken(String clienttoken) {
		this.clienttoken = clienttoken;
	}
	public String getDevicetoken() {
		return devicetoken;
	}
	public void setDevicetoken(String devicetoken) {
		this.devicetoken = devicetoken;
	}
	public String getClientpaltform() {
		return clientpaltform;
	}
	public void setClientpaltform(String clientpaltform) {
		this.clientpaltform = clientpaltform;
	}
	public String getPackagename() {
		return packagename;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public Date getLastsenddate() {
		return lastsenddate;
	}
	public void setLastsenddate(Date lastsenddate) {
		this.lastsenddate = lastsenddate;
	}
	public String getLastsendtoken() {
		return lastsendtoken;
	}
	public void setLastsendtoken(String lastsendtoken) {
		this.lastsendtoken = lastsendtoken;
	}
	@Override
	public String toString() {
		return "DeviceInfo [seq=" + seq + ", userid=" + userid + ", password="
				+ password + ", clienttoken=" + clienttoken + ", devicetoken="
				+ devicetoken + ", clientpaltform=" + clientpaltform
				+ ", packagename=" + packagename + ", createdate=" + createdate
				+ ", updatedate=" + updatedate + ", lastsenddate="
				+ lastsenddate + ", lastsendtoken=" + lastsendtoken + "]";
	}
	
	
	
}
