/**
 * Created by Administrator on 2016/8/30.
 */

var patientModal = {
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
    getPatientInfo : function () {

        var url = base + '/patient/getPatientByAccountId';
        doAjaxPost(url, $('#patientInfoForm'), patientInfoSuccess);
    },

    /**
     * 重置表单数据
     */
    resetPatientInfo : function () {
        $('.beReset').text('未设置');
        $('#facePic').empty();
        $('#backPic').empty();
    },

    /**
     * 绑定事件
     */
    bindEvent: function () {
        var that = this;

        $('#patient-failed').on('click', function () {
            $('#patientInfoForm').find('input[name="auditType"]').val('0');
            patientAudit();
        });

        $('#patient-ok').on('click',function(){
            $('#patientInfoForm').find('input[name="auditType"]').val('1');
            patientAudit();
        })
    }
};

function toPatientModel(data) {
    // 患者信息
    $('#patientInfoModel').on('show.bs.modal', function (el) {

        patientModal.resetPatientInfo();
        $('#patientInfoForm').find('input[name="id"]').val(data);
        patientModal.getPatientInfo();

    }).modal('show');
}

var patientInfoSuccess = function (data) {
    // 基本信息
    $('#patientPhone').text(data.account.telephone);
    $('#patientName').text(data.patient.name);
    $('#idCard').text(data.patient.idCode || '未填写');
    $('#patientAge').text(data.patient.age || '未填写');
    $('#patientSex').text(getSex(data.patient.sex));
    if (data.patient.face) {
        // 身份证正面照
        getFileUrl(data.patient.face,setFace);
    }

    if (data.patient.back) {
        // 身份证背面照
        getFileUrl(data.patient.back,setBack);
    }

    //待审核
    if (data.account.status == 2){
        $('#auditDesc').text('');
        $('.only-close').hide();
        $('.two-button').show();
    } else {
        $('#auditDesc').text(data.account.auditDesc);
        $('.only-close').show();
        $('.two-button').hide();
    }

};

/**
 * 设置身份证正面照
 * @param fileUrls
 */
var setFace = function(fileUrl) {
    var fileUrls = [fileUrl];
    $('#facePic').empty();
    $('#facePic').append(getImagePopHtml(fileUrls,'身份证正面照',true,'200px','200px'));
};

/**
 * 设置身份证背面照
 * @param fileUrls
 */
var setBack = function(fileUrl) {
    var fileUrls = [fileUrl];
    $('#backPic').empty();
    $('#backPic').append(getImagePopHtml(fileUrls,'身份证背面照',true,'200px','200px'));
};

var patientAudit = function () {

    var auditDesc = $.trim($('#patientInfoForm').find('textarea[name="auditDesc"]').val());
    if(!auditDesc){
        showWaringToast("审核意见不能为空！");
        return;
    }

    var url = base + '/patient/audit';
    doAjaxPost(url,$('#patientInfoForm'),patientAuditSuccess);

};

var patientAuditSuccess = function (data) {
    showBaseMessage('操作成功！');
    $('#patientInfoModel').modal('hide');
    patientList.dataTable.fnDraw();
};

$(function () {
    patientModal.init();
});