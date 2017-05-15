/**
 * Created by Administrator on 2016/8/26.
 */

/**
 * 时间格式化
 * @param date
 * @param format
 * @returns {*}
 */
function dateFormat(date, format, milliseconds) {
    if(format === undefined){
        format = date;
        date = new Date();
    }

    if (milliseconds){
        date = new Date(date);
    }

    var map = {
        "M": date.getMonth() + 1, //月份
        "d": date.getDate(), //日
        "h": date.getHours(), //小时
        "m": date.getMinutes(), //分
        "s": date.getSeconds(), //秒
        "q": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds() //毫秒
    };
    format = format.replace(/([yMdhmsqS])+/g, function(all, t){
        var v = map[t];
        if(v !== undefined){
            if(all.length > 1){
                v = '0' + v;
                v = v.substr(v.length-2);
            }
            return v;
        }
        else if(t === 'y'){
            return (date.getFullYear() + '').substr(4 - all.length);
        }
        return all;
    });
    return format;
}

/**
 * 获取指定的URL参数值
 * URL:http://www.quwan.com/index?name=tyler
 * 参数：paramName URL参数
 * 调用方法:getParam("name")
 * 返回值:tyler
 */
function getParam(paramName) {
    paramValue = "", isFound = !1;
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++
    }
    return paramValue == "" && (paramValue = null), paramValue
}

/**
 * 页面跳转
 * @param path
 */
function gotoPage(path) {
    location.href = base + '/' + path;
}

/**
 * 订单详情---打开新的页面
 * @param orderId
 */
function goOrderDetail(orderId,tab) {

    if (!tab){
        tab = 'info';
    }

    window.open(base + '/order/managerOrderDetail?orderId=' + orderId + '#' + tab);
}

/**
 * 获取性别
 * @param sex
 * @returns {*}
 */
function getSex(sex) {
    if (sex == 1){
        return "男";
    } else {
        return "女";
    }
}



/**
 * 获取图片gallery的html代码
 * @param fileList
 * @param title 标题
 * @param isRest 是否需要重置点击事件
 */
function getImagePopHtml(fileList,title,isReset,maxH,width) {
    var imagePath = "";

    if (!maxH){
        maxH = "300px";
    }

    if (!width){
        width = "32.5%";
    }

    // 诊断证明
    for (var i = 0; i < fileList.length; i++) {
        var image = "<a href=" + fileList[i] + " title=" + title + " data-source=" + fileList[i] + ">" +
            "<img src=" + fileList[i] + " width=" + width +" style=max-height:" + maxH + ";/> " +
            "</a>"
        imagePath += image;
    }

    if (isReset) {
        resetPicPopup();
    }

    return imagePath;
}

/**
 * 重置图片gallery事件----用于模板生成代码之后
 */
function resetPicPopup() {
    $('.zoom-gallery').magnificPopup({
        delegate: 'a',
        type: 'image',
        closeOnContentClick: false,
        closeBtnInside: false,
        mainClass: 'mfp-with-zoom mfp-img-mobile',
        image: {
            verticalFit: true,
            titleSrc: function(item) {
                var div = $('<div>' +item.el.attr('title') + ' &middot; <a class="image-source-link" href="'+item.el.attr('data-source')+'" target="_blank">点击查看原图</a> &middot; </div> ');
                var angel = 0;
                var a = $('<a>', {
                    href :'javascript: void(0)',
                    html :'旋转',
                    class: 'btn btn-outline btn-info',
                    click: function(event) {
                        var el = $(this).closest('figure').find('img.mfp-img');
                        angel = (angel+90)%360;
                        el.css('transform','rotate('+angel+'deg)');
                    }
                });
                div.append(a);
                return div;
            }
        },
        gallery: {
            enabled: true
        },
        zoom: {
            enabled: true,
            duration: 300, // don't foget to change the duration also in CSS
            opener: function(element) {
                return element.find('img');
            }
        }

    });
}

$(function () {

    // 新增订单
    $('.addOrder').click(function () {
        window.open(base + '/order/addOrder');
    });

    // 新增用户
    $('.addUser').click(function () {
        var type = $('.user-type').val();
        window.open(base + '/account/addUser?userType=' + type);
    });
});