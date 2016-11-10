package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class UgrpAp extends UgrpApKey implements Serializable{

	private static final long serialVersionUID = 2904650824212725601L;
	private String apcname;
	private Integer apfunc;
	
	public String getApcname() {
		return apcname;
	}

	public void setApcname(String apcname) {
		this.apcname = apcname;
	}

	public Integer getApfunc() {
		return apfunc;
	}

	public void setApfunc(Integer apfunc) {
		this.apfunc = apfunc;
	}
    

}