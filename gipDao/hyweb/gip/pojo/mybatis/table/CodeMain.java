package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class CodeMain implements Serializable{

	private static final long serialVersionUID = 7058360835932085572L;

	private Integer codeseq;

    private String codeid;

    private String codevalue;

    private String codeshow;

    private Integer codesort;
    
    private String showvalue;

    public Integer getCodeseq() {
        return codeseq;
    }

    public void setCodeseq(Integer codeseq) {
        this.codeseq = codeseq;
    }

    public String getCodeid() {
        return codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid;
    }

    public String getCodevalue() {
        return codevalue;
    }

    public void setCodevalue(String codevalue) {
        this.codevalue = codevalue;
    }

    public String getCodeshow() {
        return codeshow;
    }

    public void setCodeshow(String codeshow) {
        this.codeshow = codeshow;
    }

    public Integer getCodesort() {
        return codesort;
    }

    public void setCodesort(Integer codesort) {
        this.codesort = codesort;
    }

	public String getShowvalue() {
		return showvalue;
	}

	public void setShowvalue(String showvalue) {
		this.showvalue = showvalue;
	}
}