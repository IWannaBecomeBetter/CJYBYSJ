/**
 * Created by Administrator on 2017/4/6.
 */
var register = {
    init:function () {
        var that = this;
        document.getElementById("agree").checked=true
        that.bindEvent();
    },
    bindEvent: function () {
        $('#register').bind('click', registerIn);
    }
}

function checkTel(value){
    var isMob= /^(13[0123456789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7}|17[0-9]{9})$/;

    if(isMob.test(value)) {
        return true;
    }
    else{
        return false;
    }
}

function registerIn() {
    var confirmRule = document.getElementById("agree").checked==false;
    if(confirmRule){
        showBaseAlert('您尚未同意用户协议！');
        return;
    }

    var telephone = $('#telephone').val().trim();
    if(telephone == '') {
        showBaseAlert('请输入手机号码');
        return;
    }
    if (!checkTel(telephone)) {
        showBaseAlert("请输入正确的手机号码");
        return;
    }

    var checkcode = $('#checkcode').val();
    var realCode = $('#realCode').val();
    if(checkcode!=realCode){
        showBaseAlert("验证码错误",null,null,reloadCode);
        return;
    }

    var pass = $('#pass').val().trim();
    if(pass == '') {
        showBaseAlert('请输入密码');
        return;
    }

    var repass = $('#repass').val().trim();
    if(repass == '') {
        showBaseAlert('请确认密码');
        return;
    }

    if(pass!=repass){
        showBaseAlert('两次密码不一致，请核对');
        return;
    }

    var url = base+'/register';
    doAjaxPost(url,$('#registerForm'),registerSuccess)
}

function registerSuccess(){
    location.href = base+'/treat/identification'
}