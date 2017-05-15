/**
 * Created by Administrator on 2017/4/7.
 */
/**
 * Created by Administrator on 2017/4/6.
 */
var service = {
    init:function () {
        var that = this;
        //初始化页面元素
        that.bindEvent();
    },
    bindEvent:function () {
        //绑定事件
        $('.service').bind('tap',toOrder)
    }
}

function toOrder() {
    var type = $(this).data('type');
    location.href = base + '/order/orderType?type='+type;
}

$(function () {
    service.init()
})