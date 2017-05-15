/**
 * Created by Administrator on 2017/4/11.
 */
var headUpload;
var mine = {
    init : function () {
        headUpload = new CJY.imgUploader({
            buttonId: 'headImgBtn',
            maxCount: 1,
            auto_start: true,
            callback:function(serverIds){
                var url = base + '/account/setHead'
                var data = {
                    "head":serverIds[0]
                }
                doAjaxPostData(url,data,setHeadSuccess)
            },
            ImageAdded: function(src,id){
                var img = $('#headImgTag');
                img.attr('src',src);
                img.show();
                $('#preImg').css("display","none");
            }
        })
    }
}


function setHeadSuccess() {
    showToast("设置成功！")
}

function logout() {
    showConfirm("确认退出？请三思！",null,function () {
        location.href = base + "/exit";
    },function () {
        return;
    })

}

function reIdentification(data) {
    var auditDesc = $('#auditDesc').val();
    showConfirm("经工作人员审核后,您的认证未通过。原因如下："+auditDesc+"\n是否重新认证？",null,function () {
        location.href = base + '/patient/identification';
    },null);
}

function identification() {
    location.href = base + '/patient/identification';
}

$(function () {
    mine.init()
})