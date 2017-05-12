package hyweb.gip.dao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.DeviceInfoMapper;
import hyweb.gip.dao.service.DeviceInfoService;
import hyweb.gip.pojo.mybatis.table.DeviceInfo;

public class DeviceInfoServiceImpl implements DeviceInfoService {

	@Autowired
	private DeviceInfoMapper mapper;
	
	@Override
	@Transactional
	public int insert(DeviceInfo record) {
		return mapper.insert(record);
	}

	@Override
	@Transactional
	public int updateByPrimaryKey(DeviceInfo record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public DeviceInfo selectByClientToken(String clienttoken) {
		return mapper.selectByClientToken(clienttoken);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public DeviceInfo selectByDeviceToken(String devicetoken) {
		return mapper.selectByDeviceToken(devicetoken);
	}

}
