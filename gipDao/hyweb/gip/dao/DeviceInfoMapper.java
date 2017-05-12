package hyweb.gip.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.mybatis.table.DeviceInfo;

@Repository
public interface DeviceInfoMapper {
	int insert(DeviceInfo record);
	int updateByPrimaryKey(DeviceInfo record);
	DeviceInfo selectByClientToken(@Param("clienttoken")String clienttoken);
	DeviceInfo selectByDeviceToken(@Param("devicetoken")String devicetoken);
}
