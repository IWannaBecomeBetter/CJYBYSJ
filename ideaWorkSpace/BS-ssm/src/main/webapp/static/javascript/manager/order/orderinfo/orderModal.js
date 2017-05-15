/**
 * Created by Administrator on 2016/8/30.
 */
/**
 * 初始化
 */
function init() {

}


function toAllocateTreat(data) {
    // 分派康复管家
    $('.allocateTreat').on('show.bs.modal', function (el) {

        $('#treatDiv').find('input[name="orderId"]').val(data);
        getTreatList();
    }).modal('show');

}


function toAuditOrder(data){
    // 审核订单
    $('.auditOrder').on('show.bs.modal', function (el) {

        // 重置form
        resetOrderInfo();

        // 订单ID
        $('#auditOrderForm').find('input[name="id"]').val(data);
        $('#fg-auditDesc').hide(); // 隐藏信息也审核意见展示

        getOrderBaseInfo(data);

    }).modal('show');
}


/**
 * 获取订单审核基本数据
 * @param orderId
 */
function getOrderBaseInfo() {

    var url = base + '/order/getOrderBaseInfo';
    doAjaxPost(url, $('#auditOrderForm'), orderInfoSuccess);

}

/**
 * 获取数据成功---数据对应结构ManagerOrderDetail
 */
function orderInfoSuccess(data) {

    // 基本信息
    $('#patientPhone').text(data.orderBooking.telephone);
    $('#patientName').text(data.orderBooking.name);
    $('#patientAge').text(data.orderBooking.age);
    $('#patientSex').text(getSex(data.orderBooking.sex));
    $('#address').text(data.orderBooking.address);
    $('#mainConditions').text(data.orderBooking.mainConditions);

    // 诊断证明
    getFileUrls(data.orderBooking.fileids,setDiagnosisFiles);

    //待审核
    if (data.orderInfo.orderStatus == '1'){
        $('#auditDesc').text('');
        $('.only-close').hide();
        $('.two-button').show();
    } else {
        $('#auditDesc').text(data.orderInfo.auditDesc);
        $('.only-close').show();
        $('.two-button').hide();
    }
}

/**
 * 重置表单数据
 */
function resetOrderInfo() {

    $('.beReset').text('...');

    // 预约时间段
    for (var i = 0; i < 3; i++) {
        var id = '#orderTime' + i;
        $(id).text('未设置');
    }

    // 诊断证明
    $('#diagnosisList').empty();

}
/**
 * 设置诊断证明图片
 * @param fileUrls
 */
function setDiagnosisFiles(fileUrls) {
    $('#diagnosisList').append(getImagePopHtml(fileUrls,'诊断证明'));
}

/**
 * 订单审核不通过
 */
function auditFailed() {
    var orderId = $('#auditOrderForm').find('input[name="id"]').val();
    var auditDesc = $.trim($('#auditOrderForm').find('textarea[name="auditDesc"]').val());
    if(!auditDesc){
        showWaringToast("审核意见不能为空！");
        return;
    }
    var auditType = '0';//审核不通过
    // ajax请求
    auditOrder(orderId,auditType,auditDesc);
}

/**
 * 订单审核通过
 */
function auditOk() {
    var orderId = $('#auditOrderForm').find('input[name="id"]').val();
    var auditDesc = $.trim($('#auditOrderForm').find('textarea[name="auditDesc"]').val());
    if(!auditDesc){
        showWaringToast("审核意见不能为空！");
        return;
    }
    var auditType = '1';//审核通过
    // ajax请求
    auditOrder(orderId,auditType,auditDesc);
}

/**
 * 订单审核操作
 * @param orderId
 * @param auditType
 */
function auditOrder(orderId,auditType,auditDesc){
    var url = base + '/order/orderAudit';
    var data = {"auditType":auditType,
        "auditDesc":auditDesc,
        "orderId":orderId
    };

    doAjaxPostData(url,data,auditSuccess);
}

/**
 * 审核完成之后弹出提示框刷新列表
 * @param data
 */
function auditSuccess(data){
    showBaseMessage('操作成功！');
    $('.auditOrder').modal('hide');
    orderList.dataTable.fnDraw();
}


/**
 * 获取康复师
 */
function getTreatList(){
    // qryData('treatSearchForm');
    $('#queryResultList').load(base + '/order/treatList');
}

/**
 * 确认分派康复师
 */
function allocate(){
    var orderId = $('#treatDiv').find('input[name="orderId"]').val();
    var accountId = $('#treatDiv').find('input[name="accountId"]').val();
    var url = base + '/order/treatAllocate';
    var data ={
        "orderId" : orderId,
        "accountId" : accountId
    }
    doAjaxPostData(url,data,allocateTreatSuccess);
}

/**
 * 康复师分派成功
 */
function allocateTreatSuccess(){
    showBaseMessage('操作成功！');
    $('.allocateTreat').modal('hide');
    orderList.dataTable.fnDraw();
}


/**
 * 康复师重新分派成功
 */
function reAllocateTreatSuccess(){
    showBaseMessage('操作成功！');
    $('.allocateTreat').modal('hide');
    refuseList.dataTable.fnDraw();
}


$(function () {
    init();
});