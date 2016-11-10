package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class Ap implements Serializable{

	private static final long serialVersionUID = 84395008328122073L;

	private Integer apid;

    private String apename;

    private String apcname;

    private String apcat;

    private String apurl;

    private Integer apsort;

    public Integer getApid() {
        return apid;
    }

    public void setApid(Integer apid) {
        this.apid = apid;
    }

    public String getApename() {
        return apename;
    }

    public void setApename(String apename) {
        this.apename = apename;
    }

    public String getApcname() {
        return apcname;
    }

    public void setApcname(String apcname) {
        this.apcname = apcname;
    }

    public String getApcat() {
        return apcat;
    }

    public void setApcat(String apcat) {
        this.apcat = apcat;
    }

    public String getApurl() {
        return apurl;
    }

    public void setApurl(String apurl) {
        this.apurl = apurl;
    }

    public Integer getApsort() {
        return apsort;
    }

    public void setApsort(Integer apsort) {
        this.apsort = apsort;
    }
}