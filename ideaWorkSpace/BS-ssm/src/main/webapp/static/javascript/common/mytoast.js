/**
 * Created by Administrator on 2016/8/25.
 */

/**
 * 网络异常的toast
 */
function showNetWaringToast() {
    $.toast({
        heading: "网络异常",
        text: "无法连接网络，请检查您的数据连接并重试",
        position: 'top-center',
        loaderBg:'#ff6849',
        icon: 'warning',
        hideAfter: 2000,
        bgColor: '#8a6d3b',
        stack: 6
    });
}

/**
 * 提示toast
 */
function showWaringToast(message) {
    $.toast({
        heading: "",
        text: message,
        position: 'top-center',
        loaderBg:'#ff6849',
        icon: 'warning',
        hideAfter: 2000,
        bgColor: '#8a6d3b',
        stack: 6
    });
}

/**
 * Info toast
 */
function showSuccessToast(title, message) {
    $.toast({
        heading: title,
        text: message,
        position: 'top-center',
        loaderBg:'#ff6849',
        icon: 'success',
        bgColor: '#00c292',
        hideAfter: 2000,
        stack: 6
    });
}