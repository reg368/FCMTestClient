package hyweb.gip.dao.service;


import hyweb.gip.pojo.mybatis.table.DeviceInfo;

import org.springframework.transaction.annotation.Transactional;

public interface DeviceInfoService {
	@Transactional
    int insert(DeviceInfo record);
}
