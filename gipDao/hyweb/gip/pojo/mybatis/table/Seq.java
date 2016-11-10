package hyweb.gip.pojo.mybatis.table;

import java.io.Serializable;

public class Seq implements Serializable{

	private static final long serialVersionUID = -6628261789780096195L;

	private String name;

    private Long val;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVal() {
        return val;
    }

    public void setVal(Long val) {
        this.val = val;
    }
}