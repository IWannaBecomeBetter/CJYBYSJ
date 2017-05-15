/**
 * Created by Administrator on 2017/5/8.
 */
var orderDetail = {
    init:function () {
        var that= this;
        that.bindEvent();
        $('#pic').on('show.bs.modal', function (el) {
            var invoker = $(el.relatedTarget);
            var src = invoker.attr('src');
            debugger;
            if(!src) {
                return;
            }
            $('#pic').find('.bigPicImg').attr('src', src);

        });
    },
    bindEvent:function () {
      $('#newTrain').bind('tap',newTrain);
      $('#finishTrain').bind('tap',finishTrain);
      $('#cancelTrain').bind('tap',cancelTrain)
    }
}


function newTrain() {
    $('#train').show();
}


function finishTrain() {
    var url = base + '/recover/addRecover';
    var orderId = $('#recoverFrom').find('input[name="orderId"]').val();
    var name = $('#recoverFrom').find('input[name="name"]').val();
    var recoverDesc = $('#recoverFrom').find('textarea[name="recoverDesc"]').val();
    if(!name){
        showBaseAlert("请输入项目名称");
        return
    }
    if(!recoverDesc){
        showBaseAlert("请输入训练内容或描述");
        return
    }
    var data = {
        'orderId':orderId,
        'name':name,
        'recoverDesc':recoverDesc
    }
    showConfirm("即将保存训练项目，请确认",null,function () {
        doAjaxPostData(url,data,function () {
            $('#train').hide();
            location.reload();
        })
    },null);

}

function cancelTrain() {
    $('#train').hide();
}

$(function () {
    orderDetail.init()
})