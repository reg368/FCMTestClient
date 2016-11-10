jQuery.extend({
	/* 確認是否為數字, 不包括小數點, must為true時是必填欄位*/
	checkNumber: function(field, must, fieldName, maxValue) {
		var $field = $(field);
		
		var ret = true;
		$field.each(function(key, item) {
			var $item = $(item);
			var value = $.trim($item.val());
			if (must) {
				if (value == '') {
					alert(fieldName+'是必填欄位');
					$item.focus();
					ret = false;
					return;
				}
			}
			
			if (value == '') {
				ret = true;
				return;
			}
			
			var reg = /^\d+$/;
			if (reg.test(value) == false) {
				alert(fieldName+'必需填寫數字');
				$item.focus();
				ret = false;
				return;
			}
			
			if (maxValue) {
				if (parseInt(value) > maxValue) {
					alert(fieldName+'不能超過'+maxValue);
					$item.focus();
					ret = false;
					return;
				}
			}
		});
		
		return ret;
	},
	/* 確認結束日期是否大於啟始日期, must為true時是必填欄位 */
	checkSEDate: function(startDateField, endDateField, must, fieldName) {
		var startDateInputs = $(startDateField);
		var endDateInputs = $(endDateField);
		
		var ret = true;
		for (var i = 0; i < startDateInputs.size(); i++) {
			var startDateInput = startDateInputs.eq(i);
			var endDateInput = endDateInputs.eq(i);
			
			var startDateVal = $.trim(startDateInput.val());
			var endDateVal = $.trim(endDateInput.val());
			
			if (must == true) {
				if (startDateVal == '') {
					alert(fieldName+'是必填欄位');
					ret = false;
					startDateInput.focus();
					break;
				}
				if (endDateVal == '') {
					alert(fieldName+'是必填欄位');
					ret = false;
					startDateInput.focus();
					break;
				}
			}
			
			if (startDate == '' || endDate == '') {
				break;
			}
			
			var startDate = new Date(startDateVal);
			var endDate = new Date(endDateVal);
			
			if (startDate.valueOf() > endDate.valueOf()) {
				alert(fieldName+'的啟始日期不能大於結束日期！');
				ret = false;
				startDateInput.focus();
				break;
			}
			
		}
		
		return ret;
	}
});