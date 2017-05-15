/**
 * Created by Administrator on 2017/4/25.
 */
var orderType1 = {
    init:function () {
        var that = this;
        that.bindEvent();
    },
    bindEvent: function () {
        $('#toBooking').bind('click', toBooking);
    }
}

function toBooking() {
    var type = $('#orderType').val()
    location.href = base + '/order/toBooking?type='+type;
}

$(function () {
    orderType1.init();
})