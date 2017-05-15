/**
 * Created by Administrator on 2017/4/6.
 */
/**
 * 网络错误提示框
 */
function showNetErrorAlert(){
    mui.alert("网络异常,请稍后重试","提示","确定",null,null);
}


/**
 * 自定义消息
 * @param message
 */
function showBaseAlert(message){
    mui.alert(message,"提示","确定",null,null);
}

/**
 * 自定义标题和消息
 * @param message
 * @param title
 */
function showBaseAlert(message,title){
    mui.alert(message,title,"确定",null,null);
}

/**
 * 自定义消息、标题和按钮文字
 * @param message
 * @param title
 * @param btnValue
 */
function showBaseAlert(message,title,btnValue){
    mui.alert(message,title,btnValue,null,null);
}

/**
 * 有回调函数
 * @param message
 * @param title
 * @param btnValue
 * @param callback
 */
function showBaseAlert(message,title,btnValue,callback){
    mui.alert(message,title,btnValue,callback,null);
}

/**
 * 默认提示框两秒
 * @param message
 * @constructor
 */
function showToast(message){
    mui.toast(message);
}

function showNetErrorToast(){
    mui.toast("网络异常,请稍后重试");
}

/**
 * 自定义消息时长
 * @param message
 * @param time
 */
function showToast(message,time){
    mui.toast(message,{duration:time})
}

/**
 * 自定义消息的确认框
 * @param message
 */
function showConfirm(message,btnValue,success,failed){
    if(!btnValue){
        var btnArray = ['确定', '取消'];
    }
    mui.confirm(message, '提示', btnArray, function(e) {
        if (e.index == 1) {
            if(failed){
                failed();
            }
        } else {
            if(success){
                success();
            }
        }
    })
}