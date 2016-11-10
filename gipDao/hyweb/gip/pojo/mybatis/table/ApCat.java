package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class ApCat implements Serializable{

	private static final long serialVersionUID = -4558906794358164920L;

	private String apcatid;

    private String apcatcname;

    private String apcatename;

    private Integer apcatsort;

    private String apcaticonurl;

    public String getApcatid() {
        return apcatid;
    }

    public void setApcatid(String apcatid) {
        this.apcatid = apcatid;
    }

    public String getApcatcname() {
        return apcatcname;
    }

    public void setApcatcname(String apcatcname) {
        this.apcatcname = apcatcname;
    }

    public String getApcatename() {
        return apcatename;
    }

    public void setApcatename(String apcatename) {
        this.apcatename = apcatename;
    }

    public Integer getApcatsort() {
        return apcatsort;
    }

    public void setApcatsort(Integer apcatsort) {
        this.apcatsort = apcatsort;
    }

    public String getApcaticonurl() {
        return apcaticonurl;
    }

    public void setApcaticonurl(String apcaticonurl) {
        this.apcaticonurl = apcaticonurl;
    }
}