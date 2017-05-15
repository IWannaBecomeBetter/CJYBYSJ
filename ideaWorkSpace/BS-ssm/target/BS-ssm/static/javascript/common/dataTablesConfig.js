/**
 * Created by joeshao on 16/9/9.
 */
;(function () {
    $.extend($.fn.dataTable.defaults, {
        bAutoWidth: true,
        bLengthChange: false,
        bProcessing: true,
        bServerSide: true,
        bFilter: false,
        bSearch: false,
        iDisplayLength: 10,
        fnServerData: retrieveData,
        oLanguage: {
            oAria: {
                sSortAscending: ' - 点击按正序排列',
                sSortDescending: ' - 点击按倒序排列'
            },
            oPaginate: {
                sFirst: '首页',
                sLast: '尾页',
                sNext: '下一页',
                sPrevious: '上一页'
            },
            sEmptyTable: '无数据显示',
            sInfo: '从_START_到_END_/共_TOTAL_条数据',
            sInfoEmpty: '无可显示的数据',
            sLengthMenu: '显示 _MENU_ 条数据',
            sLoadingRecords: '数据加载中...',
            sProcessing: '<div class="preloaderAjax"><div class="cssload-speeding-wheel"></div></div>',
            sZeroRecords: '暂无数据',
            sInfoFiltered: '',
            sInfoPostFix: ''
        }
    });

    function retrieveData(sSource, aoData, fnCallback) {
        $.ajax({
            "type": "post",
            // "contentType": "application/json",
            "url": sSource,
            "dataType": "json",
            "data": aoData,
            "success": function (resp) {
                if (resp.flag) {
                    fnCallback(resp.data); //服务器端返回的对象的returnObject部分是要求的格式
                }
                else {
                    showErrorMessage('请求出错', resp.msg);
                    fnCallback();
                }
            }
        });
    }
})();
