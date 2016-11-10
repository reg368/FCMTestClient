package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class DataFileKey implements Serializable{

	private static final long serialVersionUID = 6751523194962367351L;

	private Integer dfdataid;

    private String dffileseq;

    public Integer getDfdataid() {
        return dfdataid;
    }

    public void setDfdataid(Integer dfdataid) {
        this.dfdataid = dfdataid;
    }

    public String getDffileseq() {
        return dffileseq;
    }

    public void setDffileseq(String dffileseq) {
        this.dffileseq = dffileseq;
    }
}