package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class DataFile extends DataFileKey implements Serializable{
  
	private static final long serialVersionUID = 8387129716544069237L;

	private Integer dffilesort;

    private String dffilepublic;
    
    private String filefileuri;
    
    private String fileid;
    
    private String filefilename;
    
    public Integer getDffilesort() {
        return dffilesort;
    }

    public void setDffilesort(Integer dffilesort) {
        this.dffilesort = dffilesort;
    }

    public String getDffilepublic() {
        return dffilepublic;
    }

    public void setDffilepublic(String dffilepublic) {
        this.dffilepublic = dffilepublic;
    }

    public String getFilefileuri() {
		return filefileuri;
	}

	public void setFilefileuri(String filefileuri) {
		this.filefileuri = filefileuri;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getFilefilename() {
		return filefilename;
	}

	public void setFilefilename(String filefilename) {
		this.filefilename = filefilename;
	}
    
}