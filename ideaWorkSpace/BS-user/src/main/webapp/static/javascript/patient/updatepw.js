/**
 * Created by Administrator on 2017/4/18.
 */
var updatepassword = {
    init:function () {
        var that = this;
        that.bindEvent();
    },
    bindEvent: function () {
        $('#updatepw').bind('click', updatepw);
    }
}

function updatepw() {
    var mypw = $('#mypassword').val();
    var oldpw = $('#oldpassword').val();
    var newpw = $('#newpassword').val();
    var confirmpw = $('#confirmpassword').val();
    if(mypw!=oldpw){
        showBaseAlert("旧密码不正确请核对后重试");
        return;
    }
    if(mypw==newpw){
        showBaseAlert("新密码不能与原密码一致");
        return;
    }
    if(newpw!=confirmpw){
        showBaseAlert("两次密码不一致请核对");
        return;
    }
    
    var url = base + '/account/updatePassword';
    var data ={
        'password':newpw
    }
    doAjaxPostData(url,data,function () {
        showConfirm("修改成功！是否返回个人中心",null,function () {
            location.href = base + '/patient/mine'
        },null);
    })
}

$(function () {
    updatepassword.init()
})