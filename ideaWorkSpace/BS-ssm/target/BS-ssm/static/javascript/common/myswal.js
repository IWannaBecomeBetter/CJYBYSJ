/**
 * Created by Administrator on 2016/8/25.
 */


/**
 * 普通提示
 * @param message
 */
function showBaseMessage(message) {
    swal(message);
}

/**
 * 网络异常提示
 */
function showNetErrorWarning(){
    swal("网络异常", "网络连接不稳", "warning");
}

/**
 * 错误提示
 * @param message
 */
function showErrorMessage(title,message) {
    swal(title, message, "error");
}

/**
 * 通用question---alert
 * @param title 标题
 * @param text 文本
 * @param confirm 确认回调
 * @param data 参数
 */
function showQuestionAlert(title,text,confirm,data) {
    swal({
        title: title,
        text: text,
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        closeOnConfirm: true,
    }, function(isConfirm){
        if (isConfirm) {
            if (confirm){
                confirm(data);
            }
        }
    });
}

/**
 * 按钮回调函数
 * @param title 标题
 * @param text 文字
 * @param confirm 确认回调
 * @param cancel 取消回调
 * @param data 数据
 * @param confirmText 确认按钮文字
 * @param cancelText 取消按钮文字
 * @param type alert type
 */
function showConfirmAlert(title,text,confirm,cancel,data,confirmText,cancelText,type) {

    if (!confirmText){
        confirmText = '确定';
    }

    if (!cancelText){
        cancelText = '取消';
    }

    if (!type){
        type = 'success';
    }

    swal({
        title: title,
        text: text,
        type: type,
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: confirmText,
        cancelButtonText: cancelText,
        closeOnConfirm: true
    }, function(isConfirm){
        if (isConfirm) {
            if (confirm){
                confirm(data);
            }
        } else {
            if (cancel){
                cancel(data);
            }
        }
    });
}