package hyweb.gip.dao.service;


import hyweb.gip.pojo.mybatis.table.DeviceInfo;
import org.springframework.transaction.annotation.Transactional;

public interface DeviceInfoService {
	@Transactional
	int insert(DeviceInfo record);
	@Transactional
	int updateByPrimaryKey(DeviceInfo record);
	@Transactional
	DeviceInfo selectByClientToken(String clienttoken);
	@Transactional
	DeviceInfo selectByDeviceToken(String devicetoken);
}
