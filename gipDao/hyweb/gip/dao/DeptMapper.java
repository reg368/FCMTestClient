package hyweb.gip.dao;

import hyweb.gip.pojo.mybatis.table.Dept;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface DeptMapper extends Mapper<Integer, Dept>  {
    int deleteByPrimaryKey(Integer deptseq);

    void insert(Dept record);

    Dept selectByPrimaryKey(Integer deptseq);

    int updateByPrimaryKey(Dept record);

    LinkedList<Dept> selectAll();
    
    Dept selectParent(Integer deptseq);
    
    LinkedList<Dept> list(@Param("page") int page, @Param("showNum") int showNum, @Param("deptseq") int deptseq);
    
    int countAll();
    
    LinkedList<Dept> selectAllChild(
    		@Param("depthierarchycode") String depthierarchycode,
			@Param("depthierarchycode1") String depthierarchycode1,
			@Param("depthierarchycode2") String depthierarchycode2,
			@Param("depthierarchycode3") String depthierarchycode3);
    
    LinkedList<Dept> selectAllByTreeAndDept(Integer deptseq);
    
    
    int countAllByTreeAndDept(Integer deptseq);
}