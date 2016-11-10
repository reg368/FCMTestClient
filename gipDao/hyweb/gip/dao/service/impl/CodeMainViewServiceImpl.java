package hyweb.gip.dao.service.impl;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.CodeMainViewMapper;
import hyweb.gip.dao.service.CodeMainViewService;
import hyweb.gip.pojo.mybatis.table.CodeMainView;

public class CodeMainViewServiceImpl implements CodeMainViewService {
	@Autowired
	private CodeMainViewMapper codeMainViewMapper;
	
	@Override
	@Transactional
	public LinkedList<CodeMainView> selectByCodeId(String codeid) {
		return codeMainViewMapper.selectByCodeId(codeid);
	}

}
