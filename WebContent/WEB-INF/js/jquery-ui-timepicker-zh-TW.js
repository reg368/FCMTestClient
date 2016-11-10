(function($) {
	var dpFunc = $.datepicker._generateHTML;
    $.datepicker._generateHTML = function (inst) {
        var thishtml = $(dpFunc.call($.datepicker, inst));
        thishtml = $('<div>').append(thishtml);

        $('.ui-datepicker-buttonpane', thishtml).append(
            $('<button class="ui-datepicker-clear ui-state-default ui-priority-primary ui-corner-all">清除</button>')
            .click(function () {
                inst.input.val('');
                //inst.input.datepicker('hide'); 點擊後隱藏
            })
        );

        thishtml = thishtml.children();
        return thishtml;
    };
    
	$.timepicker.regional['zh-TW'] = {
		clearText: '清除', clearStatus: '清除已選日期',
		closeText: '關閉', closeStatus: '取消選擇',
		prevText: '<上一月', prevStatus: '顯示上個月',
		nextText: '下一月>', nextStatus: '顯示下個月',
		currentText: '今天', currentStatus: '顯示本月',
		monthNames: ['一月','二月','三月','四月','五月','六月',
		'七月','八月','九月','十月','十一月','十二月'],
		monthNamesShort: ['一','二','三','四','五','六',
		'七','八','九','十','十一','十二'],
		monthStatus: '選擇月份', yearStatus: '選擇年份',
		weekHeader: '周', weekStatus: '',
		dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
		dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
		dayNamesMin: ['日','一','二','三','四','五','六'],
		dayStatus: '設定每周第一天', dateStatus: '選擇 m月 d日, DD',
		dateFormat: 'yy-mm-dd', firstDay: 1, 
		initStatus: '請選擇日期', isRTL: false,
		timeOnlyTitle: '選擇時分秒',
		timeText: '時間',
		hourText: '時',
		minuteText: '分',
		secondText: '秒',
		millisecText: '毫秒',
		timezoneText: '時區',
		currentText: '現在時間',
		closeText: '確定',
		timeFormat: 'HH:mm',
		amNames: ['上午', 'AM', 'A'],
		pmNames: ['下午', 'PM', 'P']
	};
	$.timepicker.setDefaults($.timepicker.regional['zh-TW']);
	$.datepicker.setDefaults($.timepicker.regional['zh-TW']);
})(jQuery);
