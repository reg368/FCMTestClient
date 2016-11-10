package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class DataImg extends DataImgKey implements Serializable{

	private static final long serialVersionUID = 6080945566894212074L;

	private String diimgtitle;

    private Integer diimgsort;

    private String diimgpublic;

    private String diimgnews;

    private String diimgmain;
    
    private String imgfileuri;

    public String getDiimgtitle() {
        return diimgtitle;
    }

    public void setDiimgtitle(String diimgtitle) {
        this.diimgtitle = diimgtitle;
    }

    public Integer getDiimgsort() {
        return diimgsort;
    }

    public void setDiimgsort(Integer diimgsort) {
        this.diimgsort = diimgsort;
    }

    public String getDiimgpublic() {
        return diimgpublic;
    }

    public void setDiimgpublic(String diimgpublic) {
        this.diimgpublic = diimgpublic;
    }

    public String getDiimgnews() {
        return diimgnews;
    }

    public void setDiimgnews(String diimgnews) {
        this.diimgnews = diimgnews;
    }

    public String getDiimgmain() {
        return diimgmain;
    }

    public void setDiimgmain(String diimgmain) {
        this.diimgmain = diimgmain;
    }

	public String getImgfileuri() {
		return imgfileuri;
	}

	public void setImgfileuri(String imgfileuri) {
		this.imgfileuri = imgfileuri;
	}
}