/**
 * Created by Administrator on 2017/4/18.
 */
var bindemail = {
    init:function () {
        var that = this;
        that.bindEvent();
    },
    bindEvent: function () {
        $('#bindEmail').bind('click', bindEmail);
        $('#modify').bind('click', modify);
    }
}

function bindEmail() {
    var email = $('#myemail').val();
    if(!isEmail(email)){
        showBaseAlert("邮箱格式错误请核对！")
        return;
    }
    var url = base + '/account/bindEmail'
    var data = {
        'email':email
    }
    doAjaxPostData(url,data,function () {
        showConfirm("绑定成功！是否返回个人中心", null, function () {
            location.href = base + '/patient/mine';
        }, null);
    })
}


function modify() {
    
}

function isEmail(str){
    var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    return reg.test(str);
}

$(function () {
    bindemail.init();
})