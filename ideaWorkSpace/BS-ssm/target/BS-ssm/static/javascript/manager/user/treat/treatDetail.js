/**
 * Created by Administrator on 2016/8/30.
 */
/**
 * 初始化
 */
var treatDetail = {
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

        that.multiUploader = new iCare.multiUpload({
            browse_button: 'paperList',   //选择图片按钮(区域)
            file_input: 'evidences', //存储文件id的隐藏域
            return_type: 'json'  //file_input的val值格式,'json'为'["a","b","c"]','string'为'a,b,c',默认为'json'
        });
        getData();
        initTab();
        that.initDatePicker(); //初始化时间选择控件
        that.bindEvent(); //绑定时间

    },
    initDatePicker: function () {
        jQuery('.datepicker-autoclose').datepicker({
            autoclose: true,
            todayHighlight: true,
            format: 'yyyy-mm-dd'
        });
    },


    bindEvent: function () {
        var that = this;
        $('#treatDetail-edit').on('click',edit);
        $('#treatDetail-reset').on('click',getData);


        $('#treatDetail-modify').on('click',function(){
            $('#treatDetailForm input[name="hospital"]').val($('#treatDetailForm select[name="hospitalId"]').select2('data')[0].text);
            var province1 = $("#treatDetailForm").find('select[name="province1"]').val();
            var city1 = $("#treatDetailForm").find('select[name="city1"]').val();
            var country1 = $("#treatDetailForm").find('select[name="country1"]').val();
            var area1 = $("#treatDetailForm").find('select[name="area1"]').val();
            var province2 = $("#treatDetailForm").find('select[name="province2"]').val();
            var city2 = $("#treatDetailForm").find('select[name="city2"]').val();
            var country2 = $("#treatDetailForm").find('select[name="country2"]').val();
            var area2 = $("#treatDetailForm").find('select[name="area2"]').val();
            var province3 = $("#treatDetailForm").find('select[name="province3"]').val();
            var city3 = $("#treatDetailForm").find('select[name="city3"]').val();
            var country3 = $("#treatDetailForm").find('select[name="country3"]').val();
            var area3 = $("#treatDetailForm").find('select[name="area3"]').val();

            if(province1 && city1 && country1){
                var cityKey1 = province1 + "," + city1 + "," + country1 + "," + area1;
                $("#treatDetailForm").find("#citykey1").val(cityKey1);
            }
            if(province2 && city2 && country2){
                var cityKey2 = province2 + "," + city2 + "," + country2 + "," + area2;
                $("#treatDetailForm").find("#citykey2").val(cityKey2);
            }
            if(province3 && city3 && country3){
                var cityKey3 = province3 + "," + city3 + "," + country3 + "," + area3;
                $("#treatDetailForm").find("#citykey3").val(cityKey3);
            }

            var data = {
                "headImgUpload" : that.headImgUpload,
                "bodyImgUpload" : that.bodyImgUpload,
                "multiUploader" : that.multiUploader
            }
            $('#treatDetailForm').validator().on('submit',function(e){
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
    var id = '#treatDetailTab a[href='+ tab +']'
    $(id).tab('show');
}

/**
 * 获取数据
 * @param formId
 */
function getData() {
    var url = base + '/account/getTreatByAccountId';
    doAjaxGet(url, $('#treatDetailForm'), treatDetailSuccess);

}

/**
 * 获取数据成功---在页面上赋值
 */
function treatDetailSuccess(data) {
    $('#treatDetailForm').find('input[name="id"]').val(data.treat.id);
    // 身份证正面照
    getFileUrl(data.treat.idPath,setIdPath);
    // 身份证背面照
    getFileUrl(data.treat.bodyPath,setBodyPath);
    getFileUrls(data.photo,setPaperList);

    treatDetail.headImgUpload.clearElement();
    treatDetail.bodyImgUpload.clearElement();
    treatDetail.multiUploader.clearElements();

    if(data.treat.idPath){
        treatDetail.headImgUpload.setDefaultPreview(data.treat.idPath);
    }
    if(data.treat.bodyPath){
        treatDetail.bodyImgUpload.setDefaultPreview(data.treat.bodyPath);
    }
    if(data.photo){
        treatDetail.multiUploader.addDefaultImage(data.photo);
    }
    $('#name').val(data.treat.name);
    $('label[name="name"]').text(data.treat.name||"未填写");
    $('#age').val(data.treat.age);
    $('label[name="age"]').text(data.treat.age||"未填写");
    $('#sex').selectpicker('val', data.treat.sex);
    $('label[name="sex"]').text(getSex(data.treat.sex)||"未填写");
    $('#idCode').val(data.treat.idCode);
    $('label[name="idCode"]').text(data.treat.idCode||"未填写");
    if(data.treat.codeExpiryDate){
        $('#codeExpiryDate').val(dateFormat(data.treat.codeExpiryDate,"yyyy-MM-dd",true)||"");
        $('label[name="codeExpiryDate"]').text(dateFormat(data.treat.codeExpiryDate,"yyyy-MM-dd",true)||"未填写");
    }else{
        $('#codeExpiryDate').val();
        $('label[name="codeExpiryDate"]').text("未填写");
    }
    $('#codeAddress').val(data.treat.codeAddress);
    $('label[name="codeAddress"]').text(data.treat.codeAddress||"未填写");
    $('#address').val(data.treat.address);
    $('label[name="address"]').text(data.treat.address||"未填写");

    $('label[name="hospital"]').text(data.treat.hospital||"未填写");
    if(data.treat.hospitalId){
        var option = $('<option selected value="'+data.treat.hospitalId+'">'+data.treat.hospital+'</option>');
    }else{
        var option = $('<option value="-1" selected>'+'未选择'+'</option>');
    }
    $('#treatDetailForm select[name="hospitalId"]').append(option);
    $('#treatDetailForm select[name="hospitalId"]').select2({
        placeholder: "请选择医院",
        language: "zh-CN",
        ajax: {
            type: 'post',
            url: base + '/account/getHospitals',
            dataType: 'json',
            delay: 250,
            data: function (params) {
                return {
                    hospName: params.term // search term
                };
            },
            processResults: function (data, params) {

                var results = [];
                if (data.flag){
                    for (var i=0;i<data.data.length;i++) {
                        results.push({
                            id:data.data[i].id,
                            text:data.data[i].name
                        })
                    }
                    if (data.data.length == 0){
                        results = [{
                            id:"0",
                            text:"其它"
                        }];
                    }
                }
                return {
                    results: results
                };
            },
            cache: true
        },
        escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
        minimumInputLength: 1
    });

    $('#workYears').val(data.treat.workYears);
    $('label[name="workYears"]').text(data.treat.workYears||"未填写");
    $('#serviceExp').val(data.treat.serviceExp);
    $('label[name="serviceExp"]').text(data.treat.serviceExp||"未填写");
    $('#visittimeComment').val(data.treat.visittimeComment);
    $('label[name="visittimeComment"]').text(data.treat.visittimeComment||"未填写");

    for (var i = 0 ; i < data.treatConfigList.length;i++){
        var id = '#serviceArea' + (i + 1);
        var showid = '#serviceAreaShow' + (i + 1);
        onAddressChange(id);
        addressInitHasData(id,data.treatConfigList[i].province,data.treatConfigList[i].city,data.treatConfigList[i].county,data.treatConfigList[i].area);
        getAddressByCode(data.treatConfigList[i].province,data.treatConfigList[i].city,data.treatConfigList[i].county,data.treatConfigList[i].area,getAddressSuccess,showid);
    }
    $('#cardType').val(data.treat.cardType);
    $('label[name="cardType"]').text(data.treat.cardType||"未填写");
    if(data.treat.certificateApprovalDate){
        $('#certificateApprovalDate').val(data.treat.certificateApprovalDate);
        $('label[name="certificateApprovalDate"]').text(dateFormat(data.treat.certificateApprovalDate,"yyyy-MM-dd",true)||"未填写");
    }else{
        $('#certificateApprovalDate').val();
        $('label[name="certificateApprovalDate"]').text("未填写");
    }
    $('#ticketNumber').val(data.treat.ticketNumber);
    $('label[name="ticketNumber"]').text(data.treat.ticketNumber||"未填写");
    $('#level').selectpicker('val', data.treat.level);
    $('label[name="level"]').text(iCareOptions.level[data.treat.level]||"未填写");
    $('#profess').selectpicker('val', data.treat.profess);
    $('label[name="profess"]').text(iCareOptions.profess[data.treat.profess]||"未填写");
    $('#inSource').val(data.treat.inSource);
    $('label[name="inSource"]').text(data.treat.inSource||"未填写");
    $('#recommender').val(data.treat.recommender);
    $('label[name="recommender"]').text(data.treat.recommender||"未填写");
    $('#director').val(data.treat.director);
    $('label[name="director"]').text(data.treat.director||"未填写");
    $('#followUp').val(data.treat.followUp);
    $('label[name="followUp"]').text(data.treat.followUp||"未填写");
    $('#handler').val(data.treat.handler);
    $('label[name="handler"]').text(data.treat.handler||"未填写");
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


/**
 * 设置地址
 * @param data
 * @param id
 */
var getAddressSuccess= function (data,id) {
    $(id).text(data);
};

/**
 * 设置证件信息
 * @param fileUrls
 */
var setPaperList = function(fileUrls) {
    $('#paperListShow').append(getImagePopHtml(fileUrls,'证件信息',true));
};


var picupload = function(data){
    if(!data.multiUploader.isEmpty()&&!data.headImgUpload.isEmpty()&&!data.bodyImgUpload.isEmpty()){
        data.multiUploader.start(function(){
            data.multiUploader.multiUploader_flag = true;
            modifyTreatDetail(data.multiUploader,data.headImgUpload,data.bodyImgUpload);
        })
        data.headImgUpload.start(function(){
            data.headImgUpload.headImgUpload_flag = true;
            modifyTreatDetail(data.multiUploader,data.headImgUpload,data.bodyImgUpload);
        })
        data.bodyImgUpload.start(function(){
            data.bodyImgUpload.bodyImgUpload_flag = true;
            modifyTreatDetail(data.multiUploader,data.headImgUpload,data.bodyImgUpload);
        })
    }else{
        showWaringToast('缺少必要证件图片');
    }
}


/**
 * 保存按钮点击事件
 */
function modifyTreatDetail(multiUploader,headImgUpload,bodyImgUpload){
    if ( !headImgUpload.headImgUpload_flag || !bodyImgUpload.bodyImgUpload_flag || !multiUploader.multiUploader_flag){
        // console.log('完成一次文件上传');
        return;
    } else {
        headImgUpload.headImgUpload_flag = false;
        bodyImgUpload.bodyImgUpload_flag = false;
        multiUploader.multiUploader_flag = false;
    }
    var url = base + '/account/treatModify';
    doAjaxPost(url,$('#treatDetailForm'),modifyTreatDetailSuccess);
}

function modifyTreatDetailSuccess(){
    showConfirmAlert("操作成功",'是否留在当前页面?', function () {
        window.location.reload();
    },function () {
        window.close();
    },null,"继续操作","关闭标签页");
}

$(function () {
    treatDetail.init();
});