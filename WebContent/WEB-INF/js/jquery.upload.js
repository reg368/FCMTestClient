/*
 * jQuery.upload v1.0.2
 *
 * Copyright (c) 2010 lagos
 * Dual licensed under the MIT and GPL licenses.
 *
 * http://lagoscript.org
 */
(function($) {

	

	$.fn.upload = function(url, data, callback, type) {
		var uuid = new Date().getTime()    
		var self = this, inputs, checkbox, checked, iframeName = 'jquery_upload'
				+ uuid;
		// for get response data 
		var iframe = $(
				'<iframe name="' + iframeName+ '" style="position:absolute;top:-9999px" />')
				.appendTo('body');
		
		// using upload from 
		var form = '<form target="' + iframeName + '" method="post" ></form>';
	
		if ($.isFunction(data)) {
			type = callback;
			callback = data;
			data = {};
		}

		form = self.wrapAll(form).parent('form').attr('action', url);
		
		iframe.load(function() {
			var data = handleData(this, type);

			form.after(self).remove();
		

			setTimeout(function() {
				iframe.remove();
				if (type === 'script') {
					$.globalEval(data);
				}
				if (callback) {
					callback.call(self, data);
				}
			}, 0);
		});

		form.submit();
	
		return this;
	};

	function createInputs(data) {
		return $.map(
				param(data),
				function(param) {
					return '<input type="hidden" name="' + param.name
							+ '" value="' + param.value + '"/>';
				}).join('');
	}

	function param(data) {
		if ($.isArray(data)) {
			return data;
		}
		var params = [];

		function add(name, value) {
			params.push({
				name : name,
				value : value
			});
		}

		if (typeof data === 'object') {
			$.each(data, function(name) {
				if ($.isArray(this)) {
					$.each(this, function() {
						add(name, this);
					});
				} else {
					add(name, $.isFunction(this) ? this() : this);
				}
			});
		} else if (typeof data === 'string') {
			$.each(data.split('&'), function() {
				var param = $.map(this.split('='), function(v) {
					return decodeURIComponent(v.replace(/\+/g, ' '));
				});

				add(param[0], param[1]);
			});
		}

		return params;
	}

	/*
	 * function handleData(iframe, type) { var data, contents =
	 * $(iframe).contents().get(0);
	 * 
	 * if ($.isXMLDoc(contents) || contents.XMLDocument) { return
	 * contents.XMLDocument || contents; } data =
	 * $(contents).find('body').html();
	 * 
	 * switch (type) { case 'xml': data = parseXml(data); break; case 'json':
	 * data = window.eval('(' + data + ')'); break; } return data; }
	 */
	
	function handleData(iframe, type) {
		if (type == "json") {
			return jQuery.parseJSON($(iframe).contents().text());
		} else {
			var data, contents = $(iframe).contents().get(0);
			if ($.isXMLDoc(contents) || contents.XMLDocument) {
				return contents.XMLDocument || contents;
			}
			data = $(contents).find('body').html();
			if (type == 'xml')
				return parseXml(data);
			else
				return data;
		}
	}

	function parseXml(text) {
		if (window.DOMParser) {
			return new DOMParser().parseFromString(text, 'application/xml');
		} else {
			var xml = new ActiveXObject('Microsoft.XMLDOM');
			xml.async = false;
			xml.loadXML(text);
			return xml;
		}
	}
	

	

})(jQuery);
