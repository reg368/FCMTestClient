package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class Tree implements Serializable{

	private static final long serialVersionUID = 828223787701349925L;

	private Integer tid;
	
	private String tname;
	
	private String tdesc;
	
	private String tispublic;
	
	private String tstyle;
	
	private String tkeyword;

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTdesc() {
		return tdesc;
	}

	public void setTdesc(String tdesc) {
		this.tdesc = tdesc;
	}

	public String getTispublic() {
		return tispublic;
	}

	public void setTispublic(String tispublic) {
		this.tispublic = tispublic;
	}

	public String getTstyle() {
		return tstyle;
	}

	public void setTstyle(String tstyle) {
		this.tstyle = tstyle;
	}

	public String getTkeyword() {
		return tkeyword;
	}

	public void setTkeyword(String tkeyword) {
		this.tkeyword = tkeyword;
	}
	
}