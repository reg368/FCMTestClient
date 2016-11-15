package hyweb.gip.dao;

import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.mybatis.table.DeviceInfo;

@Repository
public interface DeviceInfoMapper {
	int insert(DeviceInfo record);
}
