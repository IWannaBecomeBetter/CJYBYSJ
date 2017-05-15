/**
 * Created by Administrator on 2017/5/8.
 */
var bookingSuccess = {
    init:function () {
        var that =this;
        that.bindEvent();
    },
    bindEvent:function () {
        $('#toDetail').bind('tap',function () {
            location.href  = $(this).data('url');
        })
    }
}

$(function () {
    bookingSuccess.init();
})