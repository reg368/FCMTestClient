package hyweb.gip.pojo.mybatis.view;

import java.io.Serializable;

public class Validatorz implements Serializable{

	private static final long serialVersionUID = 2523955644787306294L;
	
	private String tablename;
	private String tempname;
	private String tempcolumn;

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getTempname() {
		return tempname;
	}

	public void setTempname(String tempname) {
		this.tempname = tempname;
	}

	public String getTempcolumn() {
		return tempcolumn;
	}

	public void setTempcolumn(String tempcolumn) {
		this.tempcolumn = tempcolumn;
	}

}