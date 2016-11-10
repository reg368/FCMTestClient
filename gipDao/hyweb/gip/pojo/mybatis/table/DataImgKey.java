package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class DataImgKey implements Serializable{

	private static final long serialVersionUID = -7741964532252319109L;

	private Long didataid;

    private String diimgseq;
    
    private String ditablename;

    public Long getDidataid() {
        return didataid;
    }

    public void setDidataid(Long didataid) {
        this.didataid = didataid;
    }

    public String getDiimgseq() {
        return diimgseq;
    }

    public void setDiimgseq(String diimgseq) {
        this.diimgseq = diimgseq;
    }

	public String getDitablename() {
		return ditablename;
	}

	public void setDitablename(String ditablename) {
		this.ditablename = ditablename;
	}
    
}