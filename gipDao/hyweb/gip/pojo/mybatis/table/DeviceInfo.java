package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;
import java.util.Date;


public class DeviceInfo implements Serializable {
	
	private Integer seq;
	private String token;
	private String platform;
	private String packageName;
	private Date createDate;
	private Date updateDate;
	private Date lastSendDate;
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getLastSendDate() {
		return lastSendDate;
	}
	public void setLastSendDate(Date lastSendDate) {
		this.lastSendDate = lastSendDate;
	}
	
	@Override
	   public String toString()
	   {
	      return "DeviceInfo [seq=" + seq + ", token=" + token + ", " +
	            "platform=" + platform + ", packageName=" + packageName + "]";
	   }
	
}
