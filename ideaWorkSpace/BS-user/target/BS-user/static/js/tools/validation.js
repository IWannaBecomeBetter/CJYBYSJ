/**
 * Created by huzhicheng on 2016/6/5.

*/
;(function ($) {

    $.constants = {
        'request': /./,
        'trimLeft': /^\s+/,
        'trimRight': /\s+$/,
        'trim': /(^\s+)|(\s+$)/g,
        'password':/^[a-zA-Z\d!?@#$%,.^&*]{6,20}$/,
        'number': /^-?\d+(,\d{3,4})*(\.\d+)?$/,
        'purenumber': /^\d+$/,
        'shenfenzheng': /^\d{17}[\dx]$/i,
        'taibaozheng': /^\d{8}(\d{2})?$/,
        'zhizhao': /^\d{15}$/,
        'mobile': /^1[345789]\d{9}$/,
        'telephone': /^\d{7,8}([ +-]\d+)?$/,
        'email': /^[a-z0-9.\-_+]+@[a-z0-9\-_]+(.[a-z0-9\-_]+)+$/i,
        'url': /^(https?:\/\/)(([\d]{1,3}\.){3}[\d]{1,3}|([\d\w_!~*\\'()-]+\.)*([\d\w][\d\w-]{0,61})?[\d\w]\.[\w]{2,6})(:[\d]{1,4})?((\/?)|(\/[\d\w_!~*\\'().;?:@&=+$,%#-]+)+\/?)$/
    };

    $.fn.checkIdCard = function(){
        var value = this.val();
        if (value === '') {
            //success.fire();
            return false;
        }
        value = value.toLowerCase();
        if ($.constants.shenfenzheng.test(value)) {
            var numbers = value.toLowerCase().split('');
            // 验证地区
            var aCity = {
                11: '北京',
                12: '天津',
                13: '河北',
                14: '山西',
                15: '内蒙古',
                21: '辽宁',
                22: '吉林',
                23: '黑龙江',
                31: '上海',
                32: '江苏',
                33: '浙江',
                34: '安徽',
                35: '福建',
                36: '江西',
                37: '山东',
                41: '河南',
                42: '湖北',
                43: '湖南',
                44: '广东',
                45: '广西',
                46: '海南',
                50: '重庆',
                51: '四川',
                52: '贵州',
                53: '云南',
                54: '西藏',
                61: '陕西',
                62: '甘肃',
                63: '青海',
                64: '宁夏',
                65: '新疆',
                71: '台湾',
                81: '香港',
                82: '澳门',
                91: '国外'
            };
            if (!aCity[numbers[0] + numbers[1]]) {
                //fail.fire('身份证格式有误，请检查后重新输入！');
                return false;
            }
            var wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
            var check = ['1', '0', 'x', '9', '8', '7', '6', '5', '4', '3', '2'];
            var _sum = 0;
            for (var i = 0; i < 17; i++) {
                _sum += +numbers[i] * +wi[i];
            }
            if (numbers[17] != check[_sum % 11]) {
                //fail.fire('身份证格式有误，请检查后重新输入！');
                return false;
            }
            //success.fire();
            return true;
        }
        else {
            //fail.fire('请输入18位身份证！');
            return false;
        }
    };

    $.fn.lessThan =  function (condition) {
        if (this.val() === '') {
            return false;
        }
        var length = condition;
        if (length) {
            if (this.val().length >= length) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return true;
        }
    };

    $.fn.between = function(a,b){
        var value = this.val();
        if (value === '') {
            return false;
        }
        if(value >b || value <a){
            return false;
        }
        else {
            return true;
        }
    };

    $.fn.moreThan = function (condition) {
        var length = condition;
        if (length) {
            if (this.val().length <= length) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return true;
        }
    };

    $.fn.isNumber= function () {
        var value = this.val();
        if (value === '') {
            return false;
        }
        if (!$.constants.number.test(value)) {
            return false;
        }
        return true;
    };

    $.fn.isPassword = function(partten){
        var value = this.val();
        if(value===''){
            return false;
        }
        if(partten!=null){
            if(!partten.test(value)){
                return false;
            }
        }else{
            if(!$.constants.password.test(value)){
                return false;
            }
        }
        return true;
    };

    $.fn.isMobile = function () {
        var value = this.val();
        if (value === '') {
            return false;
        }
        if (!$.constants.mobile.test(value)) {
            return false;
        }
        return true;
    };

    $.fn.isUrl= function () {
        var value = this.val();
        if (value === '') {
            return false;
        }
        if (!$.constants.url.test(value.toLowerCase())) {
            return false;
        }
        return true;
    };

    $.fn.isEmail=function () {
        var value = this.val();
        if (value === '') {
            return false;
        }
        if (!$.constants.email.test(value)) {
            return false;
        }
        success.fire();
        return true;
    };

    $.fn.isTelphone = function () {
        var value = this.val();
        if (value === '') {
            return false;
        }
        if (!$.constants.telephone.test(value)) {
            return false;
        }
        return true;
    };
    $.fn.isTaibaozheng= function () {
        var value = this.val();
        if (value === '') {
            return false;
        }
        if (!$.constants.taibaozheng.test(value)) {
            return false;
        }
        return true;
    };

    $.fn.isZhizhao = function () {
        var value = this.val();
        if (value === '') {
            return false;
        }
        if ($.constants.zhizhao.test(value)) {
            var p = 10;
            var s;
            var a;
            var numbers = value.split('');
            for (var i = 0; i < 15; i++) {
                a = +numbers[i];
                s = (p % 11) + a;
                p = (s % 10) * 2;
                if (p === 0) {
                    p = 20;
                }
            }
            if (s % 10 !== 1) {
                return false;
            }
            return true;
        }
        else {
            return false;
        }
    };
}(jQuery));
