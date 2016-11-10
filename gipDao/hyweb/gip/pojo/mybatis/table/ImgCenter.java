package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;
import java.util.Date;

public class ImgCenter implements Serializable{

	private static final long serialVersionUID = 3131278466377906102L;

	private Integer imgseq;

    private String imgid;

    private String imgtitle;

    private String imgcontent;

    private String imgstatus;

    private String imgfilename;

    private String imgfileuri;

    private String imguploaduser;

    private Date imguploadtime;
    
    private Integer imguploaddept;

    public Integer getImgseq() {
        return imgseq;
    }

    public void setImgseq(Integer imgseq) {
        this.imgseq = imgseq;
    }

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    public String getImgtitle() {
        return imgtitle;
    }

    public void setImgtitle(String imgtitle) {
        this.imgtitle = imgtitle;
    }

    public String getImgcontent() {
        return imgcontent;
    }

    public void setImgcontent(String imgcontent) {
        this.imgcontent = imgcontent;
    }

    public String getImgstatus() {
        return imgstatus;
    }

    public void setImgstatus(String imgstatus) {
        this.imgstatus = imgstatus;
    }

    public String getImgfilename() {
        return imgfilename;
    }

    public void setImgfilename(String imgfilename) {
        this.imgfilename = imgfilename;
    }

    public String getImgfileuri() {
        return imgfileuri;
    }

    public void setImgfileuri(String imgfileuri) {
        this.imgfileuri = imgfileuri;
    }

    public String getImguploaduser() {
        return imguploaduser;
    }

    public void setImguploaduser(String imguploaduser) {
        this.imguploaduser = imguploaduser;
    }

    public Date getImguploadtime() {
        return imguploadtime;
    }

    public void setImguploadtime(Date imguploadtime) {
        this.imguploadtime = imguploadtime;
    }

	public Integer getImguploaddept() {
		return imguploaddept;
	}

	public void setImguploaddept(Integer imguploaddept) {
		this.imguploaddept = imguploaddept;
	}
    
}