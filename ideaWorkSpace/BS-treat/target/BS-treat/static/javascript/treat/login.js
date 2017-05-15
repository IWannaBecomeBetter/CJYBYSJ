/**
 * Created by Administrator on 2017/4/6.
 */
$(function() {
    document.getElementById("autoLogin").addEventListener("toggle",function(event){
        if(event.detail.isActive){
            showToast("自动登录时效为一天");
        }
    })
    
    $('#login').bind('click', login);
});

function checkTel(value){
    var isMob= /^(13[0123456789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7}|17[0-9]{9})$/;

    if(isMob.test(value)) {
        return true;
    }
    else{
        return false;
    }
}

// 登陆
function login() {
    var phoneNo = $('#telephone').val();
    if(phoneNo == '') {
        showBaseAlert('请输入手机号码');
        return;
    }
    if (!checkTel(phoneNo)) {
        showBaseAlert("请输入正确的手机号码");
        return;
    }

    var pwd = $('#password').val();
    if(pwd == '') {
        showBaseAlert('请输入密码');
        return;
    }
    
    var autoLogin = $('#autoLogin').hasClass("mui-active");
    if(autoLogin){
        $('#loginForm').find('input[name="autoLogin"]').val('1');
    }else{
        $('#loginForm').find('input[name="autoLogin"]').val('0');
    }
    

    mui($('#login')).button('loading');

    var url = base + '/account/login';
    doAjaxPost(url,$('#loginForm'),loginSuccess);
    mui($('#login')).button('reset');
}

function loginSuccess(data) {
    location.href = base+'/treat/home';
}

function toRegister() {
    location.href = base+'/account/register';
}