package hyweb.gip.dao;

import hyweb.gip.pojo.mybatis.view.Validatorz;
import java.util.LinkedList;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ValidatorMapper {
    LinkedList<Validatorz> selectColumn(String tableName);
}