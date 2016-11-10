package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class CodeRef implements Serializable{

	private static final long serialVersionUID = -1326405770497999090L;

	private Integer seq;
	
	private String refid;
	
	private String refvalue;
	
	private String subcodevalue;
	
	private String subcodeshow;
	
	private Integer subcodesort;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getRefid() {
		return refid;
	}

	public void setRefid(String refid) {
		this.refid = refid;
	}

	public String getRefvalue() {
		return refvalue;
	}

	public void setRefvalue(String refvalue) {
		this.refvalue = refvalue;
	}

	public String getSubcodevalue() {
		return subcodevalue;
	}

	public void setSubcodevalue(String subcodevalue) {
		this.subcodevalue = subcodevalue;
	}

	public String getSubcodeshow() {
		return subcodeshow;
	}

	public void setSubcodeshow(String subcodeshow) {
		this.subcodeshow = subcodeshow;
	}

	public Integer getSubcodesort() {
		return subcodesort;
	}

	public void setSubcodesort(Integer subcodesort) {
		this.subcodesort = subcodesort;
	}
	
}