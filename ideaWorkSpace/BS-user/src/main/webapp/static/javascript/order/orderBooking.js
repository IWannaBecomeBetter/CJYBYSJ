/**
 * Created by Administrator on 2017/4/25.
 */
/**
 * Created by Administrator on 2017/4/25.
 */
var prove;
var index=0;
var orderBooking = {
    init:function () {
        if($('#sex').val()=="2"){
            $('#female').attr('checked',true);
        }else{
            $('#male').attr('checked',true);
        }
        var that = this;
        prove = new CJY.imgUploader({
            buttonId: 'pickfiles',
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
                $('.files').append(imgdiv);
                index++;
            }
        })
        that.bindEvent();
    },
    bindEvent: function () {
        //时间选择
        $('#visitTime').bind('tap', function () {
            mui.trigger($('.date')[0],'tap');
        })
        $('.date').bind('tap', function () {
            var optionsJson = this.getAttribute('data-options') || '{}';
            var options = JSON.parse(optionsJson);
            var id = this.getAttribute('id');
            /*
             * 首次显示时实例化组件
             * 示例为了简洁，将 options 放在了按钮的 dom 上
             * 也可以直接通过代码声明 optinos 用于实例化 DtPicker
             */
            var picker = new mui.DtPicker(options);
            picker.show(function(rs) {
                /*
                 * rs.value 拼合后的 value
                 * rs.text 拼合后的 text
                 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
                 * rs.m 月，用法同年
                 * rs.d 日，用法同年
                 * rs.h 时，用法同年
                 * rs.i 分（minutes 的第二个字母），用法同年
                 */
                $('#visitTime').val(rs.text);
                /*
                 * 返回 false 可以阻止选择框的关闭
                 * return false;
                 */
                /*
                 * 释放组件资源，释放后将将不能再操作组件
                 * 通常情况下，不需要示放组件，new DtPicker(options) 后，可以一直使用。
                 * 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。
                 * 所以每次用完便立即调用 dispose 进行释放，下次用时再创建新实例。
                 */
                picker.dispose();
            });
        })
        $('#booking').bind('tap',Booking);
    }

}


function delpic(that) {
    var id = $(that).attr('id');
    var imgIndex = id.split('-')[1];
    var fileId = $('#img-'+imgIndex).attr('localId')
    prove.removeFile(fileId);
    $('#imgdiv-'+imgIndex).remove();
}

function Booking() {
    var name = $('#name').val();
    if(name == '') {
        showBaseAlert("请填写患者姓名",function () {
            $('#name').focus();
        });
        return;
    }
    var telephone = $('#telephone').val();
    if(telephone == '') {
        showBaseAlert("请填写联系方式",function () {
            $('#telephone').focus();
        });
        return;
    }
    var age = $('#age').val();
    if(age == '') {
        showBaseAlert("请填写患者年龄",function () {
            $('#age').focus();
        });
        return;
    }
    var address = $('#address').val();
    if(address == '') {
        showBaseAlert("请填写上门地址",function () {
            $('#address').focus();
        });
        return;
    }
    var visitTime = $('#visitTime').val();
    if(visitTime == '') {
        showBaseAlert("请选择上门时间",function () {
            $('#visitTime').focus();
        });
        return;
    }
    if($('#female').attr('checked')){
        $('#sex').val('2');
    }else{
        $('#sex').val('1');
    }
    var sex = $('#sex').val();
    var typeId = $('#typeId').val();
    var mainConditions = $('#mainConditions').val();
    if (!prove.isEmpty()){
        prove.start(function (ids){
            $('#orderBooking').find('input[name="fileIds"]').val(ids.join(','));
            var fileIds = $('#orderBooking').find('input[name="fileIds"]').val();
            var url = base +　'/order/orderBooking';
            var data = {
                'name': name,
                'telephone':telephone,
                'age':age,
                'address':address,
                'visitTime':visitTime,
                'sex':sex,
                'typeId':typeId,
                'mainConditions':mainConditions,
                'fileids':fileIds
            }
            doAjaxPostData(url,data,bookingSucess);
        });
    }

}


function bookingSucess(data) {
    var orderCode = data.orderCode;
    location.href = base + '/order/bookingSuccess?orderCode='+orderCode;
}

$(function () {
    orderBooking.init();
})