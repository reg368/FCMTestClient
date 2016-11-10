package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class ProcessCodeMain implements Serializable{

	private static final long serialVersionUID = 2580355621853774188L;

	private Integer codeseq;

    private Integer codeparent;

    private String codevalue;

    private String codeshow;

    private Integer codesort;

    private Integer deptid;

    public Integer getCodeseq() {
        return codeseq;
    }

    public void setCodeseq(Integer codeseq) {
        this.codeseq = codeseq;
    }

    public Integer getCodeparent() {
        return codeparent;
    }

    public void setCodeparent(Integer codeparent) {
        this.codeparent = codeparent;
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

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }
}