package hyweb.gip.dao;

import java.util.LinkedList;
import org.springframework.stereotype.Repository;

import hyweb.gip.pojo.custom.UgrpApQuery;
import hyweb.gip.pojo.mybatis.table.UgrpAp;
import hyweb.gip.pojo.mybatis.table.UgrpApKey;

@Repository
public interface UgrpApMapper {
    int deleteByPrimaryKey(UgrpApKey key);

    int insert(UgrpAp record);

    UgrpAp selectByPrimaryKey(UgrpApKey key);

    int updateByPrimaryKey(UgrpAp record);
    
    LinkedList<UgrpAp> selectByUgrpId(int ugrpid);
    
    LinkedList<UgrpAp> selectUgrpApByUgrpId(UgrpApQuery record);
    
    int deleteByApId(int apid);
}