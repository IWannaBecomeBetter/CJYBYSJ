/**
 * Created by Administrator on 2017/5/3.
 */
var prove;
var index=0;
var personalInfo = {
    init: function () {
        var that = this;
        prove = new CJY.imgUploader({
            buttonId: 'imageList',
            maxCount: 10,
            auto_start: false,
            ImageAdded: function(src,id) {
                var imgdiv = $('<div class="image-item space" id="imgdiv-'+index+'">');

                var closeButton = $('<div class="image-close" id="del-'+index+'" onclick="delpic(this)">');
                closeButton.html('X')
                var img = $('<img class="image-item" id="img-'+index+'">');
                img.attr('src',src);
                img.attr('localId',id);
                imgdiv.append(img)
                imgdiv.append(closeButton)
                $("#preImage").hide();
                $('#imageList').append(imgdiv);
                index++;
            }
        })
        that.bindEvent();
    },
    bindEvent:function () {
        $('.level').bind('tap',function () {
            $('#levelCollapse').removeClass('mui-active');
            $('#selectedLevel').text($(this).text());
            $('#realLevel').val($(this).data('value'));
        })
        $('#personal').bind('tap',personal)
    }
}

function delpic(that) {
    var id = $(that).attr('id');
    var imgIndex = id.split('-')[1];
    var fileId = $('#img-'+imgIndex).attr('localId')
    prove.removeFile(fileId);
    $('#imgdiv-'+imgIndex).remove();
    if(prove.isEmpty()){
        $("#preImage").show();
    }
}


function personal() {
    var level = $('#realLevel').val();
    var address = $('#address').val();
    var workYears = $('#workYears').val();
    var serviceExp = $('#serviceExp').val();
    if(!address){
        showBaseAlert("地址不能为空");
        return
    }
    if(!workYears){
        showBaseAlert("工作年限不能为空");
        return
    }
    if(!level){
        showBaseAlert("请选择您的康复师级别");
        return
    }
    if(!serviceExp){
        showBaseAlert("请填写您的擅长领域或相关服务经验")
        return 
    }
    if(prove.isEmpty()){
        showBaseAlert("请上传您的相关证件")
        return
    }
    prove.start(function (ids){
        $('#fileids').val(ids.join(','));
        var fileids = $('#fileids').val();
        var url = base + '/treat/personalInfo';
        var data = {
            'level':level,
            'address':address,
            'workYears':workYears,
            'serviceExp':serviceExp,
            'fileids':fileids
        }
        doAjaxPostData(url,data,personalSuccess);
    });
}

function personalSuccess() {
    showBaseAlert("信息提交成功，请静待后台人员审核，即将进入首页",null,null,function(){
        location.href = base + '/treat/home';
    })
}

$(function () {
    personalInfo.init();
})