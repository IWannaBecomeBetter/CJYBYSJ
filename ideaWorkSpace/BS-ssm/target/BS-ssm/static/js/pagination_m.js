function qryData(form1) {
	var paginationDiv = $("#" + form1).parents('.paginationDiv');// 容器，为了解决一个页面多个分页的问题
	var pageBar = paginationDiv.find('.pagerBar');// 获取分页的bar

	
	var pageNumber = paginationDiv.find(".p_from").find(".p_number").val();
	paginationDiv.find(".p_from").find("#pageStart").val(pageNumber);
	
	var condition = $("#" + form1).serialize();
	// serialize方法会将空格替换成“+” 号 ，而 “+号被转义为：％２Ｂ，所以放心德替换回来
	condition = condition.replace(/\+/g, " ");

	if (pageBar.attr("oldCondition") != condition) {
		paginationDiv.find(".p_from").find('.p_startRow').val(0);
	}

	var p_param = "";

	if ($('.p_from').size() == 0) {
		var old_p_number = paginationDiv.find('#p_number').val();
		if(old_p_number) {
			p_param = "pageStart=" + old_p_number + "&pageCount=10" + "&pageSize=10";// 第一次页面的时候没有加载过分页信息。
		}
		else {
			p_param = "pageStart=0" + "&pageCount=10" + "&pageSize=10";// 第一次页面的时候没有加载过分页信息。
		}

	} else {
		p_param = paginationDiv.find('.p_from').serialize();
	}

	// 查询使用的完整参数
	var param = p_param + "&" + condition;

	param = convertToObject(param);

	var path = $("#" + form1).attr('action');

	paginationDiv.find(".queryResultList").load(path, param, function() {
		try {

			if (pageBar.attr("oldCondition") == condition) {

			} else {
				pageBar.attr("oldCondition", condition);
				//paginationDiv.find(".p_from").find('.p_startRow').val(0);
			}
			goPage(form1);
			if($("#totalcount")!=null && searchtotalcount!=null){
				$("#totalcount").html("共"+searchtotalcount +"条");
			}
		} catch (err) {

		}
	});


}
goPage = function(form1) {
	
	var paginationDiv = $("#" + form1).parents('.paginationDiv');// 容器，为了解决一个页面多个分页的问题
	var startRow = parseInt(paginationDiv.find(".p_from").find(".p_startRow").val());
	var pageSize = parseInt(paginationDiv.find(".p_from").find(".p_size").val());
	var pageCount = parseInt(paginationDiv.find(".p_from").find(".p_count").val());
	var pageNumber = (startRow/pageSize)+1;

	paginationDiv.find(".p_from").find(".p_number").val(pageNumber);
	
	paginationDiv.find(".pager").pager({
		pagenumber : pageNumber,
		pagecount : pageCount,
		buttonClickCallback : function(pnumber) {
			paginationDiv.find(".p_from").find(".p_number").val(pnumber);
			qryData(form1);
		}
	});
}

function submitForm(form1) {
	var paginationDiv = $("#" + form1).parents('.paginationDiv');
	paginationDiv.find(".p_from").find(".p_startRow").val(0);
//	paginationDiv.find(".p_from").find("#pageStart").val(pnumber);
	qryData(form1);
}

/**
 * 将格式为： key=value&key=value 的字符串转化为js对象
 * 
 * @param {Object}
 *            params
 */
function convertToObject(params) {

	var obj = {};
	var keyValues = params.split('&');
	for (var i = 0; i < keyValues.length; i++) {
		var keyVal = keyValues[i].split('=');
		obj[keyVal[0]] = decodeURIComponent(keyVal[1] ? keyVal[1] : "");
	}
	return obj
}

String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
}
