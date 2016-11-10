package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class UgrpApKey implements Serializable{

	private static final long serialVersionUID = -111930290085756770L;

	private Integer ugrpid;

    private Integer apid;

    public Integer getUgrpid() {
        return ugrpid;
    }

    public void setUgrpid(Integer ugrpid) {
        this.ugrpid = ugrpid;
    }

    public Integer getApid() {
        return apid;
    }

    public void setApid(Integer apid) {
        this.apid = apid;
    }
}