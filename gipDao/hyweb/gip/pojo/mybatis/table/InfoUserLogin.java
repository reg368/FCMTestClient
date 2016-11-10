package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;
import java.util.Date;

public class InfoUserLogin implements Serializable{

	private static final long serialVersionUID = 5811233949118158850L;

	private Long loginseq;

    private String loginuserid;

    private Date lastvisit;

    private String lastip;

    public Long getLoginseq() {
        return loginseq;
    }

    public void setLoginseq(Long loginseq) {
        this.loginseq = loginseq;
    }

    public String getLoginuserid() {
        return loginuserid;
    }

    public void setLoginuserid(String loginuserid) {
        this.loginuserid = loginuserid;
    }

    public Date getLastvisit() {
        return lastvisit;
    }

    public void setLastvisit(Date lastvisit) {
        this.lastvisit = lastvisit;
    }

    public String getLastip() {
        return lastip;
    }

    public void setLastip(String lastip) {
        this.lastip = lastip;
    }
}