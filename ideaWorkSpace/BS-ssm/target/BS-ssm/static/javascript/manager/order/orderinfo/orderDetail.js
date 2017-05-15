/**
 * Created by Administrator on 2016/8/30.
 */
/**
 * 初始化
 */
function init() {
    bind();
    getData();
    initTab();
}

/**
 * 初始化tab
 */
function initTab() {
    //获取url上#后面的内容
    var tab = window.location.hash || 'info';
    var id = '#detailTab a[href='+ tab +']'
    $(id).tab('show');
}

/**
 * 获取数据
 * @param formId
 */
function getData() {
    var url = base + '/order/getOrderDetail';
    doAjaxPost(url, $('#orderDetail'), orderDetailSuccess);
}

/**
 * 绑定事件
 */
function bind(){
    $("#orderBaseInfo-edit").click(function(){
        $("#editOrderDetailInfo").removeClass("hide");
        $("#editOrderDetailInfo").addClass("display");
        $("#orderDetailInfo").addClass("hide");
        $("#orderDetailInfo").removeClass("display");


        $(this).removeClass("display");
        $(this).addClass("hide");
        $("#zone-modify").addClass("display");
        $("#zone-modify").removeClass("hide");
    });

    $("#orderBaseInfo-cancle").click(function () {
        $("#editOrderDetailInfo").removeClass("display");
        $("#editOrderDetailInfo").addClass("hide");
        $("#orderDetailInfo").addClass("display");
        $("#orderDetailInfo").removeClass("hide");

        $("#orderBaseInfo-edit").removeClass("hide");
        $("#orderBaseInfo-edit").addClass("display");
        $("#zone-modify").addClass("hide");
        $("#zone-modify").removeClass("display");
    });

    //编辑保存
    $("#orderBaseInfo-modify").click(function(){
        var id = $("#orderDetail input[name='id']").val();
        var phone = $('#patientPhoneEdit').val();
        var patientName = $('#patientNameEdit').val();
        var patientAge = $('#patientAgeEdit').val();
        var patientSex = $('#patientSexEdit').val();
        var province = $("#province").val();
        var city = $("#city").val();
        var country = $("#country").val();
        var addressDetail = $('#addressEdit').val();
        var situation = $('#presentSituationEdit').val();
        var auditDesc =$('#auditDescEdit').val();
        $.ajax({
            type:"post",
            url:base + "/order/editOrderBaseInfo",
            data:{"id":id,"phone":phone,"patientName":patientName,"patientAge":patientAge,"patientSex":patientSex,
                "province":province,"city":city,"country":country,"address":addressDetail,
            "situation":situation,"auditDesc":auditDesc},
            success:function (data) {
                if(data.flag){
                    showSuccessToast("","基本信息更新成功");
                    $("#orderBaseInfo-cancle").click();
                    getData();
                }
            },
            error:function(a,b,c){

            }
        });
    });

    addressInit();

    //联系人
    $("#link-edit-button").click(function(){
        $("#link-edit").removeClass("hide");
        $("#link-edit").addClass("display");
        $("#link-show").addClass("hide");
        $("#link-show").removeClass("display");


        $(this).removeClass("display");
        $(this).addClass("hide");
        $("#link-zone-modify").addClass("display");
        $("#link-zone-modify").removeClass("hide");
    });

    $("#link-cancle").click(function () {
        $("#link-edit").removeClass("display");
        $("#link-edit").addClass("hide");
        $("#link-show").addClass("display");
        $("#link-show").removeClass("hide");

        $("#link-edit-button").removeClass("hide");
        $("#link-edit-button").addClass("display");
        $("#link-zone-modify").addClass("hide");
        $("#link-zone-modify").removeClass("display");
    });

    $("#link-modify").click(function(){
        var id = $("#orderDetail input[name='id']").val();
        var linkName = $("#linkNameEdit").val();
        var linkPhone = $("#linkPhoneEdit").val();
        var linkRelation = $("#linkRelationEdit").val();
        $.ajax({
            type:"post",
            url:base + "/order/editOrderLinkInfo",
            data:{"id":id,"linkName":linkName,"linkPhone":linkPhone,"linkRelation":linkRelation},
            success:function (data) {
                if(data.flag){
                    showSuccessToast("","联系人信息更新成功");
                    $("#link-cancle").click();
                    getData();
                }
            },
            error:function(a,b,c){

            }
        });
    });
}

/**
 * 获取数据成功---数据对应结构ManagerOrderDetail
 */
function orderDetailSuccess(data) {
    var logicStatusBefore = data.logicStatusBefore;
    var logicStatus = data.logicStatus;
    var orderDesc = data.orderDesc;
    var address = data.address;
    // 基本信息
    $('#patientPhone').text(data.managerOrderDetail.account.phoneNo);
    $('#patientName').text(data.managerOrderDetail.orderDetail.orderBooking.patientName);
    $('#patientAge').text(data.managerOrderDetail.orderDetail.orderBooking.patientAge);
    $('#patientSex').text(getSex(data.managerOrderDetail.orderDetail.orderBooking.patientSex));
    $('#address').text(data.managerOrderDetail.address);
    $('#presentSituation').text(data.managerOrderDetail.orderDetail.orderBooking.presentSituation);
    if (data.managerOrderDetail.orderDetail.orderInfo.auditDesc){
        $('#auditDesc').text(data.managerOrderDetail.orderDetail.orderInfo.auditDesc);
    } else {
        $('#auditDesc').text('无');
    }

    //基本信息 编辑模式  address.provinceCode,address.cityCode,address.countryCode

    $('#patientPhoneEdit').val(data.managerOrderDetail.account.phoneNo);
    $('#patientNameEdit').val(data.managerOrderDetail.orderDetail.orderBooking.patientName);
    $('#patientAgeEdit').val(data.managerOrderDetail.orderDetail.orderBooking.patientAge);
    $('#patientSexEdit').selectpicker('val',data.managerOrderDetail.orderDetail.orderBooking.patientSex);
    setTimeout(function () {
        $("#province").selectpicker('val',address.provinceCode);
        $("#city").selectpicker('val',address.cityCode);
        $("#country").selectpicker('val',address.countryCode);
    }, 600);

    $('#addressEdit').val(address.detailAddress);
    $('#presentSituationEdit').text(data.managerOrderDetail.orderDetail.orderBooking.presentSituation);
    if (data.managerOrderDetail.orderDetail.orderInfo.auditDesc){
        $('#auditDescEdit').text(data.managerOrderDetail.orderDetail.orderInfo.auditDesc);
    } else {
        $('#auditDescEdit').text('无');
    }

    // 联系人信息
    $('#linkName').text(data.managerOrderDetail.orderDetail.orderBooking.linkName);
    $('#linkPhone').text(data.managerOrderDetail.orderDetail.orderBooking.linkPhone);
    $('#linkRelation').text(data.managerOrderDetail.relation);

    // l联系人信息编辑
    $('#linkNameEdit').val(data.managerOrderDetail.orderDetail.orderBooking.linkName);
    $('#linkPhoneEdit').val(data.managerOrderDetail.orderDetail.orderBooking.linkPhone);
    $('#linkRelationEdit').selectpicker('val',data.managerOrderDetail.orderDetail.orderBooking.linkRelation);

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

    // 状态跟踪
    if(logicStatus == 99 || logicStatus == 100){//退单
        if(logicStatusBefore>=0) {//订单正常开始
            $('#newOrder').addClass('orderFinishedThisStatus');
        }
        if(logicStatusBefore>=7){//已经确认上门时间
            $('#selectedDate').addClass('orderFinishedThisStatus');
        }
        if(logicStatusBefore>=12){//已出康复计划
            $('#hasPlan').addClass('orderFinishedThisStatus');
        }
        if(logicStatusBefore>=16){//已经预付完成
            $('#prePayed').addClass('orderFinishedThisStatus');
        }
        if(logicStatusBefore>=18){//服务中
            $('#servicing').addClass('orderFinishedThisStatus');
        }
        if(logicStatusBefore==22){//订单已完成
            $('#orderFinished').addClass('orderFinishedThisStatus');
            $('#finishDate').text(dateFormat(data.managerOrderDetail.orderDetail.orderInfo.updateDate,'yyyy-MM-dd hh:mm',true));
        }
        $('#orderFinished small').text(orderDesc);
    }else{//没有退单
        if(logicStatusBefore == -1){//订单审核不通过
            $('#newOrder small').text(orderDesc);
            $('#newOrder').addClass('orderFinishedThisStatus');
        }
        if(logicStatusBefore>=0) {//订单正常开始
            $('#newOrder').addClass('orderFinishedThisStatus');
            if (logicStatusBefore < 7) {
                $('#newOrder small').text(orderDesc);
            }
        }
        if(logicStatusBefore>=7){//已经确认上门时间
            $('#selectedDate').addClass('orderFinishedThisStatus');
            if(logicStatusBefore<12){
                $('#selectedDate small').text(orderDesc);
            }
        }
        if(logicStatusBefore>=12){//已出康复计划
            $('#hasPlan').addClass('orderFinishedThisStatus');
            if(logicStatusBefore<16){
                $('#hasPlan small').text(orderDesc);
            }
        }
        if(logicStatusBefore>=16){//已经预付完成
            $('#prePayed').addClass('orderFinishedThisStatus');
            if(logicStatusBefore<18){
                $('#prePayed small').text(orderDesc);
            }
        }
        if(logicStatusBefore>=18){//服务中
            $('#servicing').addClass('orderFinishedThisStatus');
            if(logicStatusBefore!=22){
                $('#servicing small').text(orderDesc);
            }
        }
        if(logicStatusBefore==22){//订单已完成
            $('#orderFinished').addClass('orderFinishedThisStatus');
            $('#orderFinished small').text(orderDesc);
            $('#finishDate').text(dateFormat(data.managerOrderDetail.orderDetail.orderInfo.updateDate,'yyyy-MM-dd hh:mm',true));
        }
    }

    $('#orderCreateDate').text(dateFormat(data.managerOrderDetail.orderDetail.orderInfo.createDate,'yyyy-MM-dd hh:mm',true));
    $('#selectDate').text(data.selectDate.startDate);
    if (data.managerOrderDetail.orderDetail.orderPlan){
        $('#planDate').text(dateFormat(data.managerOrderDetail.orderDetail.orderPlan.createDate,'yyyy-MM-dd hh:mm',true));
    }
    if (data.managerOrderDetail.orderDetail.orderPrePay) {
        $('#prePayDate').text(dateFormat(data.managerOrderDetail.orderDetail.orderPrePay.createTime,'yyyy-MM-dd hh:mm',true));
    }
    if(logicStatusBefore>=18){
        var serviceNum = '已服务';
        if(data.managerOrderDetail.orderDetail.orderBooking.treatNum == ''){
            serviceNum += '0';
        }else{
            serviceNum += data.managerOrderDetail.orderDetail.orderBooking.treatNum
        }
        serviceNum += '次,共'+data.managerOrderDetail.orderDetail.orderPlan.number+'次';
        $('#treatNum').text(serviceNum);
    }


    // 评估单
    orderAssessment = data.managerOrderDetail.orderDetail.orderAssessment;
    if (orderAssessment){

        $("#orderDetailAssessment").empty();

        orderAssessment.basicData = $.parseJSON(orderAssessment.basicData);

        assessmentHelper(orderAssessment);
        // 评估单
        var html = template('art-assessment', orderAssessment);
        $("#orderDetailAssessment").append(html);

        // 评估单
        getFileUrls(data.managerOrderDetail.orderDetail.orderAssessment.fileIds,setAssessmentFiles);
    }

    // 康复计划
    orderPlan = data.managerOrderDetail.orderDetail.orderPlan;
    if (orderPlan) {
        $("#orderDetailPlan").empty();

        // 康复计划
        var html = template('order-plan', orderPlan);
        $("#orderDetailPlan").append(html);
        // 康复计划
        getFileUrls(data.managerOrderDetail.orderDetail.orderPlan.fileIds,setPlanFiles);

    }

    // 小结总结
    planResults = data.managerOrderDetail.planResultList;
    if (planResults != null && planResults.length > 0) {

        $("#orderDetailSummary").empty();

        var xiaojiezongjieData = {
            items: planResults
        };

        summaryHelper();

        var html = template('art-summary', xiaojiezongjieData);
        $("#orderDetailSummary").append(html);

        for (var i = 0; i < planResults.length;i++){
            var id = "#summaryfiles" + i;
            getFileUrls(planResults[i].fileIds,setSummaryFiles,id);
        }
    }

    // 康复日程项
    exerciseList = data.managerOrderDetail.exerciseList;
    if (exerciseList != null && exerciseList.length > 0) {

        $("#orderDetailExercise").empty();

        var exerciseData = {
            items: exerciseList
        };

        exerciseHelper();

        var html = template('art-exercise', exerciseData);
        $("#orderDetailExercise").append(html);

        for (var i = 0; i < exerciseList.length;i++){
            var id = "#exercisefiles" + i;
            getFileUrls(exerciseList[i].fileIds,setExerciseFiles,id);
        }
    }

    // 相关人员
    curRecover = data.managerOrderDetail.curRecover;
    if (curRecover != null && curRecover.account!=null) {
        var html = template('art-recover', curRecover);
        $("#recoverInfo").append(html);
    }
    curDoctor = data.managerOrderDetail.curDoctor;
    if (curDoctor != null && curDoctor.account!=null) {
        var html = template('art-doctor', curDoctor);
        $("#doctorInfo").append(html);
    }
    curTreat = data.managerOrderDetail.curTreat;
    if (curTreat != null && curTreat.account!=null) {
        var html = template('art-treat', curTreat);
        $("#treatInfo").append(html);
    }
}

/**
 * 设置诊断证明图片
 * @param fileUrls
 */
function setDiagnosisFiles(fileUrls) {
    $('#diagnosisList').append(getImagePopHtml(fileUrls,'诊断证明'));
}

/**
 * 设置评估单图片
 * @param fileUrls
 */
function setAssessmentFiles(fileUrls) {
    $('#assessmentfiles').append(getImagePopHtml(fileUrls,'评估单附件',true));
}

/**
 * 设置康复计划图片
 * @param fileUrls
 */
function setPlanFiles(fileUrls) {
    $('#planfiles').append(getImagePopHtml(fileUrls,'康复计划附件',true));
}

/**
 * 设置小结、总结图片
 * @param fileUrls
 */
function setSummaryFiles(fileUrls,viewId) {
    $(viewId).append(getImagePopHtml(fileUrls,'小结、总结附件',true,"200px"));
}

/**
 * 设置康复日程项图片
 * @param fileUrls
 */
function setExerciseFiles(fileUrls,viewId) {
    $(viewId).append(getImagePopHtml(fileUrls,'康复日程项附件',true,"200px"));
}

$(function () {
    init();
});