/**
 * Created by Administrator on 2016/8/30.
 */
/**
 * 初始化
 */
function init() {

    // 审核订单
    $('#auditRefuse').on('show.bs.modal', function (el) {

        // 重置数据
        resetOrderInfo();

        var invoker = $(el.relatedTarget);
        // 订单ID
        var orderId = invoker.attr('orderId');
        $('#auditRefuseForm').find('input[name="orderId"]').val(orderId);
        //退单ID
        var id = invoker.attr('id');
        $('#auditRefuseForm').find('input[name="id"]').val(id);
        //退单类型
        var type = invoker.attr('type');
        $('#auditRefuseForm').find('input[name="applyType"]').val(type);
        getOrderBaseInfo(orderId);
    });

}

/**
 * 获取订单基本数据
 * @param orderId
 */
function getOrderBaseInfo(data) {

    var url = base + '/order/getOrderBaseInfo';
    var data = {
        "id":data
    }
    doAjaxPostData(url,data,orderInfoSuccess);

}

/**
 * 获取数据成功---数据对应结构ManagerOrderDetail
 */
function orderInfoSuccess(data) {

    // 基本信息
    $('#patientPhone').text(data.managerOrderDetail.account.phoneNo);
    $('#patientName').text(data.managerOrderDetail.orderDetail.orderBooking.patientName);
    $('#patientAge').text(data.managerOrderDetail.orderDetail.orderBooking.patientAge);
    $('#patientSex').text(getSex(data.managerOrderDetail.orderDetail.orderBooking.patientSex));
    $('#address').text(data.managerOrderDetail.address);
    $('#presentSituation').text(data.managerOrderDetail.orderDetail.orderBooking.presentSituation);

    // 联系人信息
    $('#linkName').text(data.managerOrderDetail.orderDetail.orderBooking.linkName);
    $('#linkPhone').text(data.managerOrderDetail.orderDetail.orderBooking.linkPhone);
    $('#linkRelation').text(data.managerOrderDetail.relation);

    // 预约时间段
    for (var i = 0; i < 3; i++) {
        var id = '#orderTime' + i;
        if (i < data.managerOrderDetail.orderDateList.list.length) {
            $(id).text(data.managerOrderDetail.orderDateList.list[i].startDate + " - " + data.managerOrderDetail.orderDateList.list[i].endDate);
        } else {
            $(id).text('未设置');
        }
    }

    // 诊断证明
    getFileUrls(data.managerOrderDetail.orderDetail.orderBooking.diagnosisFile,setDiagnosisFiles);
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
 * 退单审核通过
 */
function auditRefuseOK(){
    $('#auditRefuseForm').find('input[name="auditType"]').val(1);//审核通过
    var applyType = $('#auditRefuseForm').find('input[name="applyType"]').val();
    if(applyType == 2 || applyType == 4){//患者拒付
        var url = base + '/refuse/refusePay';
    }else{
        var url = base + '/refuse/refuseOrder';
    }
    doAjaxPost(url,$('#auditRefuseForm'),anditRefuseSuccess);
}


/**
 * 退单审核不通过
 */
function auditRefuseNO(){
    $('#auditRefuseForm').find('input[name="auditType"]').val(0);//审核不通过
    var applyType = $('#auditRefuseForm').find('input[name="applyType"]').val();
    if(applyType == 2 || applyType == 4){//患者拒付
        var url = base + '/refuse/refusePay';
    }else{
        var url = base + '/refuse/refuseOrder';
    }
    doAjaxPost(url,$('#auditRefuseForm'),anditRefuseSuccess);
}


/**
 * 退单审核回调函数
 */
function anditRefuseSuccess(){
    showBaseMessage('操作成功！');
    $('#auditRefuse').modal('hide');
    refuseList.dataTable.fnDraw();
}


$(function () {
    init();
});