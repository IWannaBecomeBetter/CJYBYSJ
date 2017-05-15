/**
 * Created by Administrator on 2016/8/30.
 */

var treatModal = {
    /**
     * 初始化
     */
    init : function () {
        var that = this;

        that.bindEvent(); //绑定事件
    },

    /**
     * 获取信息
     */
    getTreatInfo : function () {

        var url = base + '/treat/getTreatByAccountId';
        doAjaxPost(url, $('#treatInfoForm'), treatInfoSuccess);
    },

    /**
     * 重置表单数据
     */
    resetTreatInfo : function () {

        $('.beReset').text('未设置');

        $('#headPic').empty();
        $('#bodyPic').empty();
        $('#paperList').empty();

    },

    /**
     * 绑定事件
     */
    bindEvent: function () {

        $('#treat-failed').on('click', function () {
            $('#treatInfoForm').find('input[name="auditType"]').val('0');
            treatAudit();
        });

        $('#treat-ok').on('click',function(){
            $('#treatInfoForm').find('input[name="auditType"]').val('1');
            treatAudit();
        })
    }
};


function toTreatModel(data) {
    // 康复师信息
    $('#treatInfo').on('show.bs.modal', function (el) {

        treatModal.resetTreatInfo();

        $('#treatInfoForm').find('input[name="accountId"]').val(data);

        treatModal.getTreatInfo();
    }).modal('show');
}


var treatInfoSuccess = function (data) {
    $('#treatInfoForm').find('input[name="id"]').val(data.treat.id);

    // 基本信息
    $('#telephone').text(data.account.telephone);
    $('#name').text(data.treat.name);
    $('#idCard').text(data.treat.idCode);
    $('#age').text(data.treat.age);
    $('#sex').text(getSex(data.treat.sex));
    // 身份证正面照
    getFileUrl(data.treat.face,setIdPath);
    // 身份证背面照
    getFileUrl(data.treat.back,setBodyPath);
    $('#workYears').text(data.treat.workYears);
    if(data.treat.level == '1'){
        $('#level').text("初级康复师");
    }else if(data.treat.level == '2'){
        $('#level').text("中级康复师");
    }else if(data.treat.level == '3'){
        $('#level').text("高级康复师");
    }

    $('#serviceExp').text(data.treat.serviceExp);

    getFileUrls(data.treat.fileids,setPaperList);

    //待审核
    if (data.account.status == 2){
        $('.only-close').hide();
        $('.two-button').show();
        $('#auditDesc').text('');
    } else {
        $('.only-close').show();
        $('.two-button').hide();
        $('#auditDesc').text(data.account.auditDesc);
    }

};

/**
 * 设置身份证正面照
 * @param fileUrls
 */
var setIdPath = function(fileUrl) {
    var fileUrls = [fileUrl];
    $('#headPic').empty();
    $('#headPic').append(getImagePopHtml(fileUrls,'身份证正面照',true,'200px','200px'));
};

/**
 * 设置身份证背面照
 * @param fileUrls
 */
var setBodyPath = function(fileUrl) {
    var fileUrls = [fileUrl];
    $('#bodyPic').empty();
    $('#bodyPic').append(getImagePopHtml(fileUrls,'身份证背面照',true,'200px','200px'));
};

/**
 * 设置证件信息
 * @param fileUrls
 */
var setPaperList = function(fileUrls) {
    $('#paperList').empty();
    $('#paperList').append(getImagePopHtml(fileUrls,'证件信息',true));
};


/**
 * 康复管家审核
 */
var treatAudit = function () {

    var profess = $('#treatInfoForm').find('select[name="profess"]').val();

    var auditMsg = $.trim($('#treatInfoForm').find('textarea[name="auditDesc"]').val());

    if(!auditMsg){
        showWaringToast("审核意见不能为空！");
        return;
    }

    var url = base + '/treat/audit';
    doAjaxPost(url,$('#treatInfoForm'),treatAuditSuccess);

};

var treatAuditSuccess = function (data) {
    showBaseMessage('操作成功！');
    $('#treatInfo').modal('hide');
    treatList.dataTable.fnDraw();
};

$(function () {
    treatModal.init();
});