/**
 * Created by Administrator on 2016/8/29.
 */

/**
 * 初始化
 */
function init() {
    getData('feedbackForm');

}

/**
 * 获取数据
 * @param formId
 */
function getData(formId) {
    qryData(formId);
}

/**
 * 重置数据
 * @param formId
 */
function resetData(formId) {
    // 重置form
    resetForm(formId);
    getData(formId);
}

$(function () {
    init();
});