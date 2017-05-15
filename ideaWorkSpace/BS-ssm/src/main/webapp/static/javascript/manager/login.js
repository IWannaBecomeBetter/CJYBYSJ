// 登录页面

function init() {

}

/**
 * 回车点击事件
 */
function loginKeydown() {
    if (event.keyCode == 13) {
        event.preventDefault();
        $('#loginbtn').click();
    }
}

// 登录
function loginIn() {

    if ($('#telephone').val().trim() === '') {
        showWaringToast('账户不能为空');
        return;
    }

    if ($('#password').val().trim() === '') {
        showWaringToast('密码不能为空');
        return;
    }

    var url = base + '/login';
    doAjaxPost(url, $('#loginForm'), loginSuccess);
}

function loginSuccess(data) {
    gotoPage('manager_index');
}

$(function () {
    init();
});