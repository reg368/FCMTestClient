package hyweb.gip.dao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import hyweb.gip.dao.DeviceInfoMapper;
import hyweb.gip.dao.service.DeviceInfoService;
import hyweb.gip.pojo.mybatis.table.DeviceInfo;

public class DeviceInfoServiceImpl implements DeviceInfoService {

	@Autowired
	private DeviceInfoMapper deviceInfoMapper;
	
	@Override
	@Transactional
	public int insert(DeviceInfo record) {
		// TODO Auto-generated method stub
		return deviceInfoMapper.insert(record);
	}

}