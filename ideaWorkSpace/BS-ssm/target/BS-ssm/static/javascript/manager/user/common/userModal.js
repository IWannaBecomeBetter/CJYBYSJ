/**
 * Created by Administrator on 2016/8/30.
 */

var userModal = {

    /**
     * 初始化
     */
    init : function () {
        var that = this;

        var roles = {};
        roles[1] = "患者";
        roles[4] = "医生";
        roles[5] = "康复管家";
        roles[6] = "康复师";

        // 添加推荐人
        $('.addRecommend').on('show.bs.modal', function (el) {

            $(".div-role").hide();
            resetForm('addRecommendForm');

            $('#checkResult').hide();

            var invoker = $(el.relatedTarget);
            // 订单ID
            var userId = invoker.attr('userId');
            var userType = invoker.attr('userType');
            $('#addRecommendForm').find('input[name="id"]').val(userId);
            $('#addRecommendForm').find('input[name="userType"]').val(userType);

        });

        // 添加备注
        $('.addComment').on('show.bs.modal', function(el) {

            resetForm('addCommentForm');

            var invoker = $(el.relatedTarget);
            var userId = invoker.attr('userId');
            var comment = invoker.attr("data-comment");
            $("#addCommentForm").find("input[name='id']").val(userId);
            if (comment == 'null'){
                comment = '';
            }
            $("#addCommentForm").find("textarea[name='comment']").val(comment);
        });

        // 调整状态
        $('.changeStatus').on('show.bs.modal', function(el) {

            resetForm('changeStatusForm');

            var invoker = $(el.relatedTarget);
            var userId = invoker.attr('userId');
            var status = invoker.attr("userStatus");
            $("#changeStatusForm").find("input[name='id']").val(userId);
            var selectStatus = $("#changeStatusForm").find("select[name='status']");

            selectStatus.selectpicker('val', status);

        });

        // 显示奖励层级
        $('.rewardRelation').on('show.bs.modal', function(el) {

            resetForm('rewardRelationForm');
            $('.beReset').text('无');

            var invoker = $(el.relatedTarget);
            var userId = invoker.attr('userId');

            var url = base + '/account/getRewards?id=' + userId;
            doAjaxGet(url,$('#addCommentForm'),function (data) {
                for(var i = 0; i < data.length; i++) {
                    $('#rewardRelationForm').find('#phone_' + i ).text(data[i].phoneNo + "【" + data[i].name +"("+ roles[data[i].type] + ")" + "】");
                }
            });
        });

        that.bindEvent(); //绑定事件
    },

    /**
     * 绑定事件
     */
    bindEvent: function () {
        var that = this;

        // 校验推荐人
        $('#userCheck').on('click', function () {
            userCheckForRecommend();
        });

        // 设置推荐人
        $('.btn-recommend-ok').on('click',function(){
            showQuestionAlert('确认设置此推荐人？','',setAccountRecommend);
        });

        // 设置备注
        $('.btn-comment-ok').on('click',function(){

            showQuestionAlert('确认设置备注？',$("#addCommentForm").find("input[name='comment']").val(),setAccountComment);
        });

        // 设置备注
        $('.btn-setstatus-ok').on('click',function(){

            showQuestionAlert('确认调整状态？',$("#changeStatusForm").find("select[name='status']").find("option:selected").text(),setAccountStatus);
        });
    }
};

/**
 * 设置推荐人校验
 */
var userCheckForRecommend = function () {

    var auditMsg = $.trim($('#addRecommendForm').find('input[name="phoneNumber"]').val());
    if(!auditMsg){
        showWaringToast("推荐人不能为空！");
        return;
    }

    var id = $('#addRecommendForm').find('input[name="id"]').val();
    var userType = $('#addRecommendForm').find('input[name="userType"]').val();

    var roles = {};
    roles[1] = "患者";
    roles[4] = "医生";
    roles[5] = "康复管家";
    roles[6] = "康复师";

    var url = base + '/account/checkRecommender';
    doAjaxPost(url,$('#addRecommendForm'),function (data) {
        if(data!=null && data.length > 0){
            $("#checkResult").text("校验成功");
            if (data.length > 1) {
                $('#recommerType').empty();
                jQuery("#recommerType").append("<option value='-1'>请选择角色</option>");
                for ( var v in data){
                    jQuery("#recommerType").append("<option value='" + data[v].type + "'>" + roles[data[v].type] + "</option>");
                }
                $('#recommerType').selectpicker('refresh');
                $(".div-role").show();
            } else {
                $(".div-role").hide();
            }
            $(".btn-recommend-ok").removeAttr("disabled");
        }else{
            $(".div-role").hide();
            $("#checkResult").text("校验失败");
            $(".btn-recommend-ok").attr("disabled","disabled");
        }
        $('#checkResult').show();
    });

};

/**
 * 设置推荐人
 */
var setAccountRecommend = function () {

    var auditMsg = $.trim($('#addRecommendForm').find('input[name="phoneNumber"]').val());
    if(!auditMsg){
        showWaringToast("推荐人不能为空！");
        return;
    }

    // 是否需要选择角色
    if ($(".div-role").is(":visible") && $('#recommerType').val() == -1 ){
        showWaringToast("请选择角色！");
        return;
    }

    var url = base + '/account/setRecommender';
    doAjaxPost(url,$('#addRecommendForm'),function (data) {
        showBaseMessage('操作成功！');
        $('.addRecommend').modal('hide');
    });

};

/**
 * 设置备注
 */
var setAccountComment = function () {

    var url = base + '/account/setComment';
    doAjaxPost(url,$('#addCommentForm'),function (data) {
        showBaseMessage('操作成功！');
        $('.addComment').modal('hide');
        refreshList();
    });

};

/**
 * 设置用户状态
 */
var setAccountStatus = function () {

    var url = base + '/account/setStatus';
    doAjaxPost(url,$('#changeStatusForm'),function (data) {
        showBaseMessage('操作成功！');
        $('.changeStatus').modal('hide');
        refreshList();
    });

};



/**
 * 用户删除
 * @param accoutId
 */
var deleteUser = function (accoutId){

    showQuestionAlert("确认删除该用户?","",function () {
        var dataForm = {
            'id': accoutId
        };
        var url = base + "/account/deleteUser";
        doAjaxPostData(url,dataForm,deleteUserSuccess);
    });

};

/**
 * 删除完成之后弹出提示框刷新列表
 * @param data
 */
var deleteUserSuccess = function(data){
    showBaseMessage('操作成功！');
    refreshList();
};

$(function () {
    userModal.init();
});