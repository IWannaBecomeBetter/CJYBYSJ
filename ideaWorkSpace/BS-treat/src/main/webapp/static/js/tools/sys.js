function resetFormValue(formId) {
	$(":input", $("#" + formId)).each(function(i) {
		this.value = "";
	});
}

function resetForm(formId) {
	$('#' + formId)[0].reset();
}

saveForm = function(formId) {
	var ret = true;
	$("body").mask("Waiting...");

	$("input,select", $("#" + formId)).each(function(i) {
		if ($(this).attr("notnull") && $(this).attr("notnull") == "true") {
			if ($(this).val() == null || $(this).val() == "") {
				alert("*标记不能为空！");
				$(this).focus();
				ret = false;
				$("body").unmask();
				return false;
			}
			if (!checkFileType(this)) {
				ret = false;
				$("body").unmask();
				return false;
			}
		}
		if (!checkDataType(this)) {
			ret = false;
			$("body").unmask();
			return false;
		}
	});
	if (ret) {
		$("#" + formId).submit();
		// $("body").unmask();
		/*
		 * if(mask){ mask(); }
		 */
	}
}



checkDataType = function(ele) {
	var dataType = $(ele).attr("dataType");
	if (!dataType)
		return true;
	var re;
	if (dataType.toUpperCase() == "EMAIL") {
		re = /^[a-zA-Z_0-9]+@[a-zA-Z_0-9.\-]+$/;
		if (!re.test($(ele).val())) {
			alert("该输入正确的Email地址!");
			$(ele).focus();
			return false;
		} else {
			return true;
		}
	}

	if (dataType.toUpperCase() == "MOBILEPHONE") {
		re = /^((13|15|18)\d{9})$/;
		if (!re.test($(ele).val())) {
			alert("该输入正确的11位移动手机号码!");
			$(ele).focus();
			return false;
		} else {
			return true;
		}
	}

	if (dataType.toUpperCase() == "PHONE") {
		re = /^([0]?\d{2,3})?[-]?\d{5,8}([-]\d+)?$/;
		if (!re.test($(ele).val())) {
			alert("该输入正确的固定电话号码!");
			$(ele).focus();
			return false;
		} else {
			return true;
		}
	}

	if (dataType.toUpperCase() == "ICCID") {
		re = /^((898601)\d{13})$/;
		if (!re.test($(ele).val())) {
			alert("该输入正确的ICCID!");
			$(ele).focus();
			return false;
		} else {
			return true;
		}
	}

	// 数字型
	if (dataType.toUpperCase() == "NUMBER") {
		if (isNaN(this.trim())) {
			alert("请输入正确的数值(如:1.23)!");
			$(ele).focus();
			return false;
		}
	}

	if (dataType.toUpperCase() == "FLOAT") {
		re = /^(\d+((\.\d+)|(\d*)))$/;
		if (!re.test($(ele).val())) {
			alert("该输入正确的数值!");
			$(ele).focus();
			return false;
		} else {
			return true;
		}
	}
}

checkFileType = function(ele) {
	var allowFileTypes = $(ele).attr("fileType");
	if (!allowFileTypes)
		return true;
	allowFileTypes = allowFileTypes + ',';
	var fileType = $(ele).val().substr($(ele).val().lastIndexOf('.') + 1,
			$(ele).val().length)
			+ ',';
	if (allowFileTypes.indexOf(fileType) == -1) {
		alert("附件格式不正确！支持的上传格式为：" + allowFileTypes);
		$(ele).focus();
		return false;
	} else {
		return true;
	}
}

switchKeyBoxes = function(eleId, checked, eleName) {
	var theForm = $("#" + eleId);
	$("input:checkbox[name='" + eleName + "']", theForm).attr("checked",
			checked);
}

checkSelect = function(eleName, theFormId) {
	return $("input:checked", $("#" + theFormId)).length;
}


function showMask() {
	$('.loaderWrap').show();
}

function hideMask() {
	$('.loaderWrap').hide();
}

//将form中的值转换为键值对。
function getFormJson(formId) {
    var o = {};
    var a = $("#"+formId).serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}
