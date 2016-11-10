package hyweb.gip.pojo.custom;

import java.io.Serializable;

public class UgrpApQuery implements Serializable{

	private static final long serialVersionUID = -9143363423242175327L;
	
	private String userid;
	
	private String apurl;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getApurl() {
		return apurl;
	}

	public void setApurl(String apurl) {
		this.apurl = apurl;
	}
	

}
