/**
 * Created by Administrator on 2016/8/30.
 */
/**
 * 初始化
 */
var patientDetail = {
    init: function () {
        var that = this;
        //图片控件初始化
        that.headImgUpload = new iCare.radioUpload({
            browse_button: 'headPic', //选择图片按钮
            file_input: 'idPath' //存储文件id的隐藏域
        });

        that.bodyImgUpload = new iCare.radioUpload({
            browse_button: 'bodyPic', //选择图片按钮
            file_input: 'bodyPath' //存储文件id的隐藏域
        });

        getData();
        initTab();
        that.bindEvent(); //绑定事件

    },


    bindEvent: function () {
        var that = this;
        $('#patientDetail-edit').on('click',edit);
        $('#patientDetail-reset').on('click',getData);

        $('#patientDetail-modify').on('click',function(){


            var data = {
                "headImgUpload" : that.headImgUpload,
                "bodyImgUpload" : that.bodyImgUpload
            }
            $('#patientDetailForm').validator().on('submit',function(e){
                if(!e.isDefaultPrevented()){//没有阻止表单提交
                    showQuestionAlert("保存之后数据无法恢复",'是否继续?',picupload,data);
                    e.preventDefault();
                }else{
                }
            });
        });

    }
};

/**
 * 初始化tab
 */
function initTab() {
    //获取url上#后面的内容
    var tab = window.location.hash || 'info';
    var id = '#patientDetailTab a[href='+ tab +']'
    $(id).tab('show');
}

/**
 * 获取数据
 * @param formId
 */
function getData() {
    var url = base + '/account/getPatientByAccountId';
    doAjaxGet(url, $('#patientDetailForm'), patientDetailSuccess);

}

/**
 * 获取数据成功---在页面上赋值
 */
function patientDetailSuccess(data) {
    $('#patientDetailForm').find('input[name="id"]').val(data.patient.id);
    $('#patientDetailForm').find('input[name="accountId"]').val(data.patient.accountId);
    if (data.patient.idPath){
        // 身份证正面照
        getFileUrl(data.patient.idPath,setIdPath);
    }

    if (data.patient.bodyPath) {
        // 身份证背面照
        getFileUrl(data.patient.bodyPath,setBodyPath);
    }

    patientDetail.headImgUpload.clearElement();
    patientDetail.bodyImgUpload.clearElement();

    if(data.patient.idPath){
        patientDetail.headImgUpload.setDefaultPreview(data.patient.idPath);
    }
    if(data.patient.bodyPath){
        patientDetail.bodyImgUpload.setDefaultPreview(data.patient.bodyPath);
    }
    if(data.photo){
        patientDetail.multiUploader.addDefaultImage(data.photo);
    }
    $('#name').val(data.patient.name);
    $('label[name="name"]').text(data.patient.name||"未填写");
    $('#age').val(data.patient.age);
    $('label[name="age"]').text(data.patient.age||"未填写");
    $('#sex').selectpicker('val', data.patient.sex);
    $('label[name="sex"]').text(getSex(data.patient.sex)||"未填写");
    $('#idCode').val(data.patient.idCode);
    $('label[name="idCode"]').text(data.patient.idCode||"未填写");

    $('#serviceExp').val(data.patient.serviceExp);
    $('label[name="serviceExp"]').text(data.patient.serviceExp||"未填写");
}

/**
 * 点击编辑按钮隐藏label显示input
 */
function edit(){
    $('.card_box .hide').removeClass('hide');
    $('.card_box .display').addClass('hide');
}


/**
 * 设置身份证正面照
 * @param fileUrls
 */
var setIdPath = function(fileUrl) {
    var fileUrls = [fileUrl];
    $('#headPicShow').append(getImagePopHtml(fileUrls,'身份证正面照',true,'200px','90%'));
};

/**
 * 设置身份证背面照
 * @param fileUrls
 */
var setBodyPath = function(fileUrl) {
    var fileUrls = [fileUrl];
    $('#bodyPicShow').append(getImagePopHtml(fileUrls,'身份证背面照',true,'200px','90%'));
};


var picupload = function(data){
    //if(!data.headImgUpload.isEmpty()&&!data.bodyImgUpload.isEmpty()){
    //    data.headImgUpload.start(function(){
    //        data.headImgUpload.headImgUpload_flag = true;
    //        modifyPatientDetail(data.headImgUpload,data.bodyImgUpload);
    //    });
    //    data.bodyImgUpload.start(function(){
    //        data.bodyImgUpload.bodyImgUpload_flag = true;
    //        modifyPatientDetail(data.headImgUpload,data.bodyImgUpload);
    //    })
    //}else{
    //    showWaringToast('缺少必要证件图片');
    //}
    data.headImgUpload.start(function(){
        data.headImgUpload.headImgUpload_flag = true;
        modifyPatientDetail(data.headImgUpload,data.bodyImgUpload);
    });
    data.bodyImgUpload.start(function(){
        data.bodyImgUpload.bodyImgUpload_flag = true;
        modifyPatientDetail(data.headImgUpload,data.bodyImgUpload);
    })
};


/**
 * 保存按钮点击事件
 */
function modifyPatientDetail(headImgUpload,bodyImgUpload){
    if ( !headImgUpload.headImgUpload_flag || !bodyImgUpload.bodyImgUpload_flag){
        // console.log('完成一次文件上传');
        return;
    } else {
        headImgUpload.headImgUpload_flag = false;
        bodyImgUpload.bodyImgUpload_flag = false;
    }

    var url = base + '/account/patientModify';
    doAjaxPost(url,$('#patientDetailForm'),addpatientDetailSuccess);
}

function addpatientDetailSuccess(){
    showConfirmAlert("操作成功",'是否留在当前页面?', function () {
        window.location.reload();
    },function () {
        window.close();
    },null,"继续操作","关闭标签页");
}

$(function () {
    patientDetail.init();
});