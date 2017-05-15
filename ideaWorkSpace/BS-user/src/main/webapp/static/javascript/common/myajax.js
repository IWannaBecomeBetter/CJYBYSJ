/**
 * Created by Administrator on 2016/8/25.
 */

/**
 * ajax请求--get
 * @param url
 * @param formId 表单id--$('#loginForm')
 * @param success 成功返回回调（业务成功）
 */
function doAjaxGet(url,formId,success) {
    var dataForm = formId.serialize();
    doAjax(url,'GET',dataForm,success);
}

/**
 * ajax请求--post
 * @param url
 * @param formId 表单id--$('#loginForm')
 * @param success 成功返回回调（业务成功）
 */
function doAjaxPost(url,formId,success) {
    var dataForm = {};
    if (formId){
        dataForm = formId.serialize();
    }
    doAjax(url,'POST',dataForm,success);
}

/**
 * ajax请求--post
 * @param url
 * @param type
 * @param formId 表单id--$('#loginForm')
 * @param success 成功返回回调（业务成功）
 */
function doAjax(url,type,data,success,viewId) {

    $('.preloaderAjax').show();
    $.ajax({
        url: url,
        type: type,
        dataType: 'json',
        data: data,
        error : function (XMLHttpRequest, textStatus, errorThrown) {
            $(".preloaderAjax").fadeOut(1500);
            showNetErrorToast();
        },
        success : function (data) {
            $(".preloaderAjax").fadeOut(1500);
            // 判断resultbean返回的标志
            var flag = data.flag;

            if(flag) {
                if (success){
                    success(data.data,viewId);
                }
            } else {
                showBaseAlert(data.msg);
            }
        }
    });
}

/**
 * ajax请求--post
 * @param url
 * @param type
 * @param formId 表单id--$('#loginForm')
 * @param success 成功返回回调（业务成功）
 */
function doAjaxWithObject(url,type,data,success,object) {

    $('.preloaderAjax').show();
    $.ajax({
        url: url,
        type: type,
        dataType: 'json',
        data: data,
        error : function (XMLHttpRequest, textStatus, errorThrown) {
            $(".preloaderAjax").fadeOut(1500);
            showNetErrorToast();
        },
        success : function (data) {
            $(".preloaderAjax").fadeOut(1500);
            // 判断resultbean返回的标志
            var flag = data.flag;

            if(flag) {
                if (success){
                    success(data.data,object);
                }
            } else {
                showBaseAlert(data.msg);
            }
        }
    });
}

/**
 *
 * @param url
 * @param data {"key":val;"key":val}
 * @param success
 */
function doAjaxPostData(url,data,success) {
    doAjax(url,'POST',data,success);
}


/**
 *
 * @param url
 * @param data  {"key":val;"key":val}
 * @param success
 */
function doAjaxGetData(url,data,success) {
    doAjax(url,'GET',data,success);
}

/**
 * 获取图片url----由数据库存的id数据转为url---单图
 * @param fileId
 * @param success
 * @param 显示控件的id
 */
function getFileUrl(fileId, success,viewId) {

    $.ajax({
        url: base + '/getFileUrl',
        type: 'post',
        dataType: 'json',
        data: {'fileId':fileId},
        error : function (XMLHttpRequest, textStatus, errorThrown) {
            showNetErrorToast();
        },
        success : function (data) {
            // 判断resultbean返回的标志
            var flag = data.flag;

            if(flag) {
                if (success){
                    success(data.data.fileUrl,viewId);
                }
            } else {
                showBaseAlert(data.msg);
            }
        }
    });
}

/**
 * 获取图片url----由数据库存的id数据转为urls---多图
 * @param fileIds 字符串 "["1111","222"]"
 * @param success
 * @param 显示控件的id
 */
function getFileUrls(fileIds, success,viewId) {

    if (jQuery.isArray(fileIds)){
        fileIds = JSON.stringify(fileIds);
    }

    $.ajax({
        url: base + '/getFileUrls',
        type: 'post',
        dataType: 'json',
        data: {'fileIds':fileIds},
        error : function (XMLHttpRequest, textStatus, errorThrown) {
            showNetErrorToast();
        },
        success : function (data) {
            // 判断resultbean返回的标志
            var flag = data.flag;

            if(flag) {
                if (success){
                    success(data.data.fileUrls,viewId);
                }
            } else {
                showBaseAlert(data.msg);
            }
        }
    });
}
