/**
 * Created by joeshao on 16/9/9.
 */

var refuseList = {
    init: function () {
        var that = this;
        that.initDatePicker(); //初始化时间选择控件
        that.initTable(); //初始化列表
        that.bindEvent(); //绑定时间
    },
    initDatePicker: function () {
        jQuery('.datepicker-autoclose').datepicker({
            autoclose: true,
            todayHighlight: true,
            format: 'yyyy-mm-dd'
        });
    },
    initTable: function () {
        this.dataTable = $('#refuseTable').dataTable({
            sAjaxSource: base+'/order/refuseList',   //ajax地址
            fnServerParams: function (aoData) {        //将form中的查询条件加到提交参数中
                aoData.push({name: "search", value: $('input[name="keyword"]').val()});
                aoData.push({name: "status", value: $('select[name="status"]').val()});
                aoData.push({name: "startTime", value: $('input[name="startTime"]').val()});
                aoData.push({name: "endTime", value: $('input[name="endTime"]').val()});
            },
            aoColumns: [                //列定义
                {
                    mDataProp: 'id',    //对应key
                    sTitle: '订单编号',  //列名
                    mRender: function (data, type, full) { //渲染函数,即 td 内显示的内容
                        return '<a href="javascript: orderDetail(' + full.id + ')">' + full.orderCode + '</a>';
                    }
                },
                {
                    mDataProp: 'typeId',
                    sTitle: '服务类型',
                    mRender: function (data, type, full) { //渲染函数,即 td 内显示的内容
                        if (data == '1') {
                            return '脑瘫康复';
                        } else if (data == '2') {
                            return '脑卒中康复';
                        }
                    }
                },
                {
                    mDataProp: 'refuseReason',
                    sTitle: '拒单理由',
                },
                {
                    mDataProp: 'updateTime',
                    sTitle: '最近操作时间',
                    mRender: function (data, type, full) { //渲染函数,即 td 内显示的内容

                        return dateFormat(data,'yyyy-MM-dd hh:mm',true);
                    }
                },
                {
                    mDataProp: null,
                    sTitle: '操作',
                    mRender: function (data, type, full) {
                        var status = full.orderStatus;
                        var html = '';
                        if (status == '-4') {
                            html += '<button class="btn btn-outline btn-success" onClick="toAllocateTreat(' + full.id + ')">重新分配康复师</button>';
                        }
                        return html;
                    },
                    bSortable: false
                }
            ]
        });

    },
    bindEvent: function () {
        var that = this;
        $('.search').on('click', function () {
            that.dataTable.fnDraw();
        });
        $('.reset').on('click',function(){
            $('#searchForm')[0].reset();
            that.dataTable.fnDraw();
        })
    }
};



/**
 * 订单详情
 * @param accoutId
 */
var orderDetail = function (data){
    toAuditOrder(data);
};

$(function () {
    refuseList.init();
});