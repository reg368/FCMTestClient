package hyweb.gip.validator;

import java.util.List;
import hyweb.gip.dao.ValidatorMapper;
import hyweb.gip.pojo.mybatis.view.Validatorz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EmptyValidator implements Validator {
	
	@Autowired
	private ValidatorMapper validatorMapper;
	
	public void validate(Object command, Errors errors) {
		String tableName = command.getClass().getSimpleName();
		List<Validatorz> columns = validatorMapper.selectColumn(tableName);
		String tempArray [] = new String [columns.size()];
		
			for(int i = 0; i < columns.size(); i++){
				tempArray[i] = columns.get(i).getTempcolumn().toLowerCase();
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, tempArray[i].toString(), "empty");
			}
	}
	
	public boolean supports(Class<?> c) {
		return Validatorz.class.isAssignableFrom(c);
	}
}
