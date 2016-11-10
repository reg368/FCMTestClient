package hyweb.gip.dao.service;

import hyweb.gip.pojo.mybatis.table.CodeMainView;

import java.util.LinkedList;

import org.springframework.transaction.annotation.Transactional;

public interface CodeMainViewService {
	@Transactional
	LinkedList<CodeMainView> selectByCodeId(String codeid);
}
