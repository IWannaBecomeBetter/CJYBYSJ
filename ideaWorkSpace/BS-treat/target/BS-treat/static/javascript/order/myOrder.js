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
    }
}


function OrderDetail(id) {
    location.href = base + '/order/orderDetail?id='+id;
}

function deal(type,orderId) {
    var url = base + '/order/dealOrder';
    if(type == 0){//拒绝
        showPrompt("请您填写拒单的理由",null,null,null,function (value) {
            var data ={
                'orderId':orderId,
                'type':type,
                'refuseReason':value
            }
            doAjaxPostData(url,data,function () {
                location.reload();
            })
        },null)
        return
    }
    var data = {
        'orderId':orderId,
        'type':type
    }
    doAjaxPostData(url,data,function () {
        showBaseAlert("操作成功，请您尽快和患者联系",null,null,function () {
            location.reload();
        });
    })
}



$(function () {
    myOrder.init()
})
