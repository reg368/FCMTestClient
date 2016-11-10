package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;
import java.util.Date;

public class FileCenter implements Serializable{
  
	private static final long serialVersionUID = 3965210303455863387L;

	private Integer fileseq;

    private String fileid;

    private String filetitle;

    private String filecontent;

    private String filestatus;

    private String filefilename;

    private String filefileuri;

    private String fileuploaduser;

    private Date fileuploadtime;
    
    private Integer fileuploaddept;

    public Integer getFileseq() {
        return fileseq;
    }

    public void setFileseq(Integer fileseq) {
        this.fileseq = fileseq;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getFiletitle() {
        return filetitle;
    }

    public void setFiletitle(String filetitle) {
        this.filetitle = filetitle;
    }

    public String getFilecontent() {
        return filecontent;
    }

    public void setFilecontent(String filecontent) {
        this.filecontent = filecontent;
    }

    public String getFilestatus() {
        return filestatus;
    }

    public void setFilestatus(String filestatus) {
        this.filestatus = filestatus;
    }

    public String getFilefilename() {
        return filefilename;
    }

    public void setFilefilename(String filefilename) {
        this.filefilename = filefilename;
    }

    public String getFilefileuri() {
        return filefileuri;
    }

    public void setFilefileuri(String filefileuri) {
        this.filefileuri = filefileuri;
    }

    public String getFileuploaduser() {
        return fileuploaduser;
    }

    public void setFileuploaduser(String fileuploaduser) {
        this.fileuploaduser = fileuploaduser;
    }

    public Date getFileuploadtime() {
        return fileuploadtime;
    }

    public void setFileuploadtime(Date fileuploadtime) {
        this.fileuploadtime = fileuploadtime;
    }

	public Integer getFileuploaddept() {
		return fileuploaddept;
	}

	public void setFileuploaddept(Integer fileuploaddept) {
		this.fileuploaddept = fileuploaddept;
	}
    
}