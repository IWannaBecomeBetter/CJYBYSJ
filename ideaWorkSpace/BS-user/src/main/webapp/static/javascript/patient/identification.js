/**
 * Created by Administrator on 2017/4/19.
 */
var face;
var back;
var identification ={
    init:function () {
        var that = this;
        face = new CJY.imgUploader({
            buttonId: 'faceImgBtn',
            maxCount: 1,
            auto_start: false,
            ImageAdded: function(src,id){
                var img = $('#faceImgTag');
                img.attr('src',src);
                img.show();
                $(".faceCamera").hide();
            }
        })
        back = new CJY.imgUploader({
            buttonId: 'backImgBtn',
            maxCount: 1,
            auto_start: false,
            ImageAdded: function(src,id){
                var img = $('#backImgTag');
                img.attr('src',src);
                img.show();
                $(".backCamera").hide();
            }
        })
        that.bindEvent();
    },
    bindEvent: function () {
        $('#identify').bind('click', identify);
    }
}

function identify() {
    var name = $('#identifyForm').find('input[name="name"]').val();
    if(name == '') {
        showBaseAlert('请输入姓名');
        return;
    }

    var idCode = $('#identifyForm').find('input[name="idCode"]').val();
    if(idCode == '') {
        showBaseAlert('请输入身份证号');
        return;
    }else{
        if(!$('input[name="idCode"]').checkIdCard()){
            showBaseAlert("请输入正确的身份证号");
            return;
        }
    }

    if(face.isEmpty()) {
        showBaseAlert('请选择身份证正面照');
        return;
    }

    if(back.isEmpty()) {
        showBaseAlert('请选择身份证背面照');
        return;
    }

    face.start( function(ids) {
        $('#faceImg').val(ids[0]);
        back.start( function(ids) {
            $('#backImg').val(ids[0]);
            $.ajax({
                url: base + '/patient/identification',
                data: $('#identifyForm').serialize(),
                type: 'post',
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    showNetErrorToast();
                    face.enable();
                    back.enable();
                },
                success: function(data) {
                    if(data.flag) {
                        showBaseAlert("信息提交成功，请静待后台人员审核，即将进入首页",null,null,function(){
                            location.href = base + '/patient/home';
                        })
                    } else {
                        debugger;
                        showBaseAlert(data.msg);
                    }
                }
            });
        });
    });

}

$(function () {
    identification.init();
})
