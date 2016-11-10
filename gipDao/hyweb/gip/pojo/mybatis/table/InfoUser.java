package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;
import java.util.Date;

public class InfoUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5832089617942812307L;

	private String userid;

    private String password;

    private String name;

    private String nickname;

    private String sex;

    private Date birthday;

    private String tel;

    private String cellphone;

    private String email;

    private Integer deptid;

    private String jobtitles;

    private String createuser;

    private Date createtime;

    private String ugrpid;

    private String status;

    private Integer visitcount;

    private String postlimit;

    private String reviewlimit;

    private String shelveslimit;

    private String updateuser;

    private Date updatetime;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getJobtitles() {
        return jobtitles;
    }

    public void setJobtitles(String jobtitles) {
        this.jobtitles = jobtitles;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUgrpid() {
        return ugrpid;
    }

    public void setUgrpid(String ugrpid) {
        this.ugrpid = ugrpid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVisitcount() {
        return visitcount;
    }

    public void setVisitcount(Integer visitcount) {
        this.visitcount = visitcount;
    }

    public String getPostlimit() {
        return postlimit;
    }

    public void setPostlimit(String postlimit) {
        this.postlimit = postlimit;
    }

    public String getReviewlimit() {
        return reviewlimit;
    }

    public void setReviewlimit(String reviewlimit) {
        this.reviewlimit = reviewlimit;
    }

    public String getShelveslimit() {
        return shelveslimit;
    }

    public void setShelveslimit(String shelveslimit) {
        this.shelveslimit = shelveslimit;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	@Override
	public String toString() {
		return "InfoUser [userid=" + userid + ", password=" + password
				+ ", name=" + name + ", nickname=" + nickname + ", sex=" + sex
				+ ", birthday=" + birthday + ", tel=" + tel + ", cellphone="
				+ cellphone + ", email=" + email + ", deptid=" + deptid
				+ ", jobtitles=" + jobtitles + ", createuser=" + createuser
				+ ", createtime=" + createtime + ", ugrpid=" + ugrpid
				+ ", status=" + status + ", visitcount=" + visitcount
				+ ", postlimit=" + postlimit + ", reviewlimit=" + reviewlimit
				+ ", shelveslimit=" + shelveslimit + ", updateuser="
				+ updateuser + ", updatetime=" + updatetime + "]";
	}
}