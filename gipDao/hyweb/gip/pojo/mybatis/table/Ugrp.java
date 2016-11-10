package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class Ugrp implements Serializable{

	private static final long serialVersionUID = -5360322828558993372L;

	private Integer ugrpid;

    private String ugrpname;

    private String remark;

    public Integer getUgrpid() {
        return ugrpid;
    }

    public void setUgrpid(Integer ugrpid) {
        this.ugrpid = ugrpid;
    }

    public String getUgrpname() {
        return ugrpname;
    }

    public void setUgrpname(String ugrpname) {
        this.ugrpname = ugrpname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}