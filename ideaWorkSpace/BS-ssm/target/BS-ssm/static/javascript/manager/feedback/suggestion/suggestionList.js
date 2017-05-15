/**
 * Created by 93701 on 2016/9/13.
 */
/**
 * Created by 93701 on 2016/9/12.
 */

var feedbackList = {
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
        this.dataTable = $('#myTable').dataTable({
            sAjaxSource: base+'/feedback/suggestionList',   //ajax地址
            fnServerParams: function (aoData) {        //将form中的查询条件加到提交参数中
                aoData.push({name: "search", value: $('input[name="keyword"]').val()});
                aoData.push({name: "status", value: $('select[name="status"]').val()});
                aoData.push({name: "startTime", value: $('input[name="startTime"]').val()});
                aoData.push({name: "endTime", value: $('input[name="endTime"]').val()});
            },
            aaSorting: [[2,"desc"]],    //排序,[第几列,正序或倒序]
            aoColumns: [                //列定义
                {
                    mDataProp: 'telephone',
                    sTitle: '联系方式',
                    bSortable: false
                },
                {
                    mDataProp: 'name',
                    sTitle: '姓名',
                    bSortable: false
                },
                {
                    mDataProp: 'score',
                    sTitle: '评分',
                    bSortable: false
                },
                {
                    mDataProp: 'createTime',
                    sTitle: '发起时间',
                    bSortable: false,
                    mRender: function (data, type, full) { //渲染函数,即 td 内显示的内容

                        return dateFormat(data,'yyyy-MM-dd hh:mm',true);
                    }
                },
                {
                    mDataProp: 'status',
                    sTitle: '状态',
                    bSortable: false,
                    mRender: function (data, type, full) { //渲染函数,即 td 内显示的内容
                        if(data == 1){
                            return '新增';
                        }else if(data == 2){
                            return '已处理';
                        }
                    }
                },
                {
                    mDataProp: null,
                    sTitle: '操作',
                    mRender: function (data, type, full) {
                        var status = full.status;
                        var html = '';
                        html += '<button class="btn btn-outline btn-info" onClick="feedbackDetail(' + full.id + ')">详情</button>';
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
 * 反馈详情
 * @param id
 */
var feedbackDetail = function (data){
    toFeedBackModel(data);
};

$(function () {
    feedbackList.init();
});