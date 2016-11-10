package hyweb.gip.dao;

import java.util.LinkedList;

import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.mybatis.table.CodeMainView;
@Repository
public interface CodeMainViewMapper {
	
	LinkedList<CodeMainView> selectByCodeId(String codeid);

}