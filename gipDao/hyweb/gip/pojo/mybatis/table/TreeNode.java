package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class TreeNode implements Serializable{

	private static final long serialVersionUID = 7947617874492203500L;

	private Long tnid;
	
	private Integer tid;
	
	private String tnname;
	
	private String tndesc;
	
	private String tnpic;
	
	private Integer tnsort;
	
	private String tnispublic;
	
	private String tntype;
	
	private String tndatasource;
	
	private String tnxpath;
	
	private Integer tnlevel;
	
	private Long parent; 
	
	private String tnurl;
	
	private String tnkeyword;

	public Long getTnid() {
		return tnid;
	}

	public void setTnid(Long tnid) {
		this.tnid = tnid;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getTnname() {
		return tnname;
	}

	public void setTnname(String tnname) {
		this.tnname = tnname;
	}

	public String getTndesc() {
		return tndesc;
	}

	public void setTndesc(String tndesc) {
		this.tndesc = tndesc;
	}

	public String getTnpic() {
		return tnpic;
	}

	public void setTnpic(String tnpic) {
		this.tnpic = tnpic;
	}

	public Integer getTnsort() {
		return tnsort;
	}

	public void setTnsort(Integer tnsort) {
		this.tnsort = tnsort;
	}

	public String getTnispublic() {
		return tnispublic;
	}

	public void setTnispublic(String tnispublic) {
		this.tnispublic = tnispublic;
	}

	public String getTntype() {
		return tntype;
	}

	public void setTntype(String tntype) {
		this.tntype = tntype;
	}

	public String getTndatasource() {
		return tndatasource;
	}

	public void setTndatasource(String tndatasource) {
		this.tndatasource = tndatasource;
	}

	public String getTnxpath() {
		return tnxpath;
	}

	public void setTnxpath(String tnxpath) {
		this.tnxpath = tnxpath;
	}

	public Integer getTnlevel() {
		return tnlevel;
	}

	public void setTnlevel(Integer tnlevel) {
		this.tnlevel = tnlevel;
	}

	public String getTnurl() {
		return tnurl;
	}

	public void setTnurl(String tnurl) {
		this.tnurl = tnurl;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public String getTnkeyword() {
		return tnkeyword;
	}

	public void setTnkeyword(String tnkeyword) {
		this.tnkeyword = tnkeyword;
	}

}