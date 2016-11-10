package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class Dept implements Serializable{

	private static final long serialVersionUID = -3279879855990958796L;

	private Integer deptseq;

    private String deptid;

    private String deptname;

    private String deptename;

    private String deptinuse;

    private String depthierarchycode;

    private Integer deptsort;

    public Integer getDeptseq() {
        return deptseq;
    }

    public void setDeptseq(Integer deptseq) {
        this.deptseq = deptseq;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getDeptename() {
        return deptename;
    }

    public void setDeptename(String deptename) {
        this.deptename = deptename;
    }

    public String getDeptinuse() {
        return deptinuse;
    }

    public void setDeptinuse(String deptinuse) {
        this.deptinuse = deptinuse;
    }

    public String getDepthierarchycode() {
        return depthierarchycode;
    }

    public void setDepthierarchycode(String depthierarchycode) {
        this.depthierarchycode = depthierarchycode;
    }

    public Integer getDeptsort() {
        return deptsort;
    }

    public void setDeptsort(Integer deptsort) {
        this.deptsort = deptsort;
    }

}