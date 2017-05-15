/**
 * Created by Administrator on 2017/4/18.
 */
var files
var starIndex = 0;
var index=0;
var myfeedback = {
    init:function () {
        var that = this;
        starIndex = 0;
        files = new CJY.imgUploader({
            buttonId: 'imageList',
            maxCount: 4,
            auto_start: false,
            ImageAdded: function(src,id) {
                var imgdiv = $('<div class="image-item space" id="imgdiv-'+index+'">');

                var closeButton = $('<div class="image-close" id="del-'+index+'" onclick="delpic(this)">');
                closeButton.html('X')
                var img = $('<img class="image-item" id="img-'+index+'">');
                img.attr('src',src);
                img.attr('localId',id);
                imgdiv.append(img)
                imgdiv.append(closeButton)
                $('#imageList').append(imgdiv);
                index++;
            }
        })
        that.bindEvent();
    },
    bindEvent: function () {
        $('#feedback').bind('click', feedback);
        //快捷输入
        mui('.mui-popover').on('tap','li',function(e){
            document.getElementById("desc").value = document.getElementById("desc").value + this.children[0].innerHTML;
            mui('.mui-popover').popover('toggle')
        })
        //应用评分

        mui('.icons').on('tap','i',function(){
            var index = parseInt(this.getAttribute("data-index"));
            var parent = this.parentNode;
            var children = parent.children;
            if(this.classList.contains("mui-icon-star")){
                for(var i=0;i<index;i++){
                    children[i].classList.remove('mui-icon-star');
                    children[i].classList.add('mui-icon-star-filled');
                }
            }else{
                for (var i = index; i < 5; i++) {
                    children[i].classList.add('mui-icon-star')
                    children[i].classList.remove('mui-icon-star-filled')
                }
            }
            starIndex = index;
        });
    }
}


function delpic(that) {
    var id = $(that).attr('id');
    var imgIndex = id.split('-')[1];
    var fileId = $('#img-'+imgIndex).attr('localId')
    files.removeFile(fileId);
    $('#imgdiv-'+imgIndex).remove();
}


function feedback() {
    var score = starIndex;
    var desc = $('#desc').val();
    files.start(function (ids){
        $('#imageList').find('input[name="fileIds"]').val(ids.join(','));
        var fileIds = $('#imageList').find('input[name="fileIds"]').val();
        var url = base + '/feedback/add';
        var data ={
            'score':score,
            'feedbackDesc':desc,
            'fileids':fileIds
        }
        doAjaxPostData(url,data,function () {
            showConfirm("提交成功！是否返回个人中心",null,function () {
                location.href = base + '/treat/mine'
            },null);
        })
    });
}

$(function () {
    myfeedback.init()
})