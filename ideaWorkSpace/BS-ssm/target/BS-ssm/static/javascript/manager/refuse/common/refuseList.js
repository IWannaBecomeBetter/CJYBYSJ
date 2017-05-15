/**
 * Created by 93701 on 2016/9/13.
 */
/**
 * Created by 93701 on 2016/9/12.
 */

var refuseList = {
    init: function (type) {
        var that = this;
        that.initDatePicker(); //初始化时间选择控件
        that.initTable(type); //初始化列表
        that.bindEvent(); //绑定时间
    },
    initDatePicker: function () {
        jQuery('.datepicker-autoclose').datepicker({
            autoclose: true,
            todayHighlight: true,
            format: 'yyyy-mm-dd'
        });
    },
    initTable: function (type) {
        this.dataTable = $('#myTable').dataTable({
            sAjaxSource: base+'/refuse/refuseList?type='+type,   //ajax地址
            fnServerParams: function (aoData) {        //将form中的查询条件加到提交参数中
                aoData.push({name: "search", value: $('input[name="keyword"]').val()});
            },
            bSort: false,
            //aaSorting: [[3,"desc"]],    //排序,[第几列,正序或倒序]
            aoColumns: [                //列定义
                {
                    mDataProp: 'orderinfo.id',    //对应key
                    sTitle: '相关订单编号',  //列名
                    bSortable: false,
                    mRender: function (data, type, full) {
                        return '<a href="javascript: goOrderDetail(' +  full.refuseDetail.orderId + ')">' + full.orderInfo.orderCode + '</a>';
                    }
                },
                {
                    mDataProp: 'refuseDetail.message',
                    sTitle: '申请理由',
                    bSortable: false,
                    mRender: function (data, type, full) {
                        return full.refuseDetail.message;
                    }
                },
                {
                    mDataProp: 'refuseDetail.msgDes',
                    sTitle: '申请理由描述',
                    bSortable: false,
                    mRender: function (data, type, full) {
                        return full.refuseDetail.msgDes;
                    }
                },
                {
                    mDataProp: 'refuseDetail.updateDate',
                    sTitle: '最近操作时间',
                    bSortable: false,
                    mRender: function (data, type, full) { //渲染函数,即 td 内显示的内容

                        return dateFormat(data,'yyyy-MM-dd hh:mm',true);
                    }
                },
                {
                    mDataProp: null,
                    sTitle: '操作',
                    mRender: function (data, type, full) {
                        var status = full.refuseDetail.status;
                        var html = '';
                        if(status == 1){
                            html += '<button class="btn btn-outline btn-info" type="'+ full.refuseDetail.type +'" orderId="' + full.refuseDetail.orderId + '" data-target="#auditRefuse" data-toggle="modal" id="' + full.refuseDetail.id + '">审核</button>';
                        }else if(status == 2){
                            html +='已通过';
                        }else{
                            html +='已拒绝';
                        }
                        //html += '<button class="btn btn-outline btn-info" data-target=".operationRecord" data-toggle="modal" orderId="' + full.id + '">查看操作记录</button>'
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
