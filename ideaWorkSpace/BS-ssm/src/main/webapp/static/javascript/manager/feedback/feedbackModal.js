/**
 * Created by Administrator on 2016/8/30.
 */
/**
 * 初始化
 */
function init() {


}

function toFeedBackModel(data) {
    // 患者信息
    $('#feedbackDetailModel').on('show.bs.modal', function (el) {
        resetFeedbackDetail();
        $('#auditFeedBackForm').find('input[name="id"]').val(data);
        getFeedbackDetail();

    }).modal('show');
}


/**
 * 获取数据
 * @param orderId
 */
function getFeedbackDetail() {

    var url = base + '/feedback/getFeedbackDetail';
    doAjaxPost(url, $('#auditFeedBackForm'), feedbackDetailSuccess);

}

/**
 * 获取数据成功
 */
function feedbackDetailSuccess(data) {
    // 基本信息
    if (data.name){
        $('#name').text(data.name);
    }
    $('#telephone').text(data.telephone);
    $('#score').text(data.score);
    $('#feedbackDesc').text(data.feedbackDesc);
    $('#createTime').text(dateFormat(data.createTime,'yyyy-MM-dd hh:mm',true));
    getFileUrls(data.fileids,setDiagnosisFiles);
}

/**
 * 重置表单数据
 */
function resetFeedbackDetail() {

    $('#fdName').text('未设置');
    $('.beReset').text('...');
}

/**
 * 设置证明图片
 * @param fileUrls
 */
function setDiagnosisFiles(fileUrls) {
    $('#diagnosisList').empty();
    $('#diagnosisList').append(getImagePopHtml(fileUrls,'诊断证明'));
}


/**
 * 反馈处理操作
 * @param orderId
 * @param auditType
 */
function auditFeedback(){
    var id = $('#auditFeedBackForm').find('input[name="id"]').val();
    var auditDesc = $('#auditDesc').val();
    if(!auditDesc){
        showBaseMessage("回复不能为空");
        return;
    }
    var url = base + '/feedback/audit';
    var data = {
        "auditDesc":auditDesc,
        "id":id
    };

    doAjaxPostData(url,data,auditSuccess);
}

/**
 * 审核完成之后弹出提示框刷新列表
 * @param data
 */
function auditSuccess(data){
    showBaseMessage('操作成功！');
    $('#feedbackDetailModel').modal('hide');
    feedbackList.dataTable.fnDraw();
}


$(function () {
    init();
});