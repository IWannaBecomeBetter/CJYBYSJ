/**
 * Created by Administrator on 2017/4/10.
 */
var tabNow
var myOrder = {
    init:function () {
        var that = this;
        mui.init({
            pullRefresh: {
                container: '#refreshContainer',
                down: {
                    height:50,//可选,默认50.触发下拉刷新拖动距离,
                    contentdown : "下拉可以刷新",//可选，在下拉可刷新状态时，下拉刷新控件上显示的标题内容
                    contentover : "释放立即刷新",//可选，在释放可刷新状态时，下拉刷新控件上显示的标题内容
                    contentrefresh : "正在刷新...",//可选，正在刷新状态时，下拉刷新控件上显示的标题内容
                    callback: pulldownRefresh
                },
            }
        });
        /**
         * 下拉刷新具体业务实现
         */
        function pulldownRefresh() {
            setTimeout(function() {
                //刷新列表
                mui.trigger(tabNow,'tap');
                mui('#refreshContainer').pullRefresh().endPulldownToRefresh(); //refresh completed
            }, 1500);
        }

        that.bindEvent();
        mui.trigger(document.getElementById('on'),'tap');

    },
    bindEvent:function () {
        //绑定事件
        var items = $('.mui-control-item');
        $.each(items,function (k,v) {
            $(v).on('tap',function(){
                tabNow = $(this)[0];
                var status = $(this).attr('status');
                $('#orderList').empty();
                $('#orderList').load(base + '/order/orderList?orderStatus=' + status, function() {

                })
            })
        })


        $('ul li').on('touchstart', function(event) {
            debugger;

        });
        
        $('#orderFinish').bind('tap',orderFinish);
    }
}


function OrderDetail(id) {
    location.href = base + '/order/orderDetail?id='+id;
}


function orderFinish(orderId) {
    showConfirm("订单即将完成请确认",null,function () {
        var url = base + '/order/finish';
        var data ={
            'id':orderId
        }
        doAjaxPostData(url,data,finishSuccess);
    },null);
}


function finishSuccess() {
    $('#ok').addClass('mui-active');
    $('#on').removeClass('mui-active');
    mui.trigger(document.getElementById('ok'),'tap');
}

$(function () {
    myOrder.init()
})
