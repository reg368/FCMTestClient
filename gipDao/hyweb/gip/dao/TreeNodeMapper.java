package hyweb.gip.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.mybatis.table.TreeNode;
@Repository
public interface TreeNodeMapper {
    int deleteByPrimaryKey(@Param("tnid") Long tnid);

    int insert(TreeNode record);

    TreeNode selectByPrimaryKey(Long tnid);
    
    TreeNode selectByCheckSiteId(@Param("siteId") String siteId, @Param("tnid") Long tnid);

    int updateByPrimaryKey(TreeNode record);
    
    LinkedList<TreeNode> selectAll();
    
    int insertTest(@Param("tid") Integer tid, @Param("tnname") String tnname, @Param("tndesc") String tndesc, @Param("tnpic") String tnpic, @Param("tnsort") Integer tnsort, @Param("tnispublic") String tnispublic, @Param("tntype") String tntype, @Param("tndatasource") String tndatasource, @Param("tnxpath") String tnxpath, @Param("tnlevel") String tnlevel);
    
    LinkedList<TreeNode> selectByLevel(@Param("tid") Integer tid, @Param("tnlevel") Integer tnlevel);
    
    LinkedList<TreeNode> selectChild(@Param("tnid") Long tnid);
    
    LinkedList<TreeNode> selectNode(@Param("top") Integer top, @Param("tid") Integer tid, @Param("order") String order);
    
    LinkedList<TreeNode> selectByParent(@Param("tnid") Long tnid , @Param("top") String top);
    
    LinkedList<TreeNode> selectByXpathAndLevel(@Param("tnid") Long tnid,
    										   @Param("tnxpath") String tnxpath,
    										   @Param("tnlevel") Integer tnlevel,
    										   @Param("top") String top);
    
    LinkedList<TreeNode> selectByTid(Integer tid);
    
    LinkedList<TreeNode> selectInTnid(@Param("list") List<Long> list);
    
    LinkedList<TreeNode> searchLevel();
   
    LinkedList<TreeNode> selectAllChild(@Param("tnid") Long tnid);
    
    LinkedList<TreeNode> selectAllPublic();
    
    LinkedList<TreeNode> selectByTnName(String tnname);
}