/**
 * Created by Administrator on 2016/9/7.
 */

/**
 * 三级联动select id 分别为province、city、country
 */

/**
 * 地址三级选择初始化
 * @param provinceId 省级select控件Id
 * @param cityId
 * @param countryId
 * @param areaId
 */
function addressInit(provinceId,cityId,countryId,areaId) {

    if (!provinceId){
        provinceId = 'province';
    }

    if (!cityId){
        cityId = 'city';
    }

    if (!countryId){
        countryId = 'country';
    }

    var addressViewId = new Object();
    addressViewId.provinceId = provinceId;
    addressViewId.cityId = cityId;
    addressViewId.countryId = countryId;
    addressViewId.areaId = areaId;

    getProvince(addressViewId);
}

/**
 * 获取二级地址
 * @param province
 */
function getProvince(addressViewId) {
    var url = base + '/select/provinceSelect';
    doAjaxWithObject(url,'GET','',function (data,addressViewId) {

        $.each(data, function (k, v) {
            $("#" + addressViewId.provinceId)[0].options.add(new Option(v.name, v.code));
        });

        getCity($("#" + addressViewId.provinceId).val(),addressViewId);

    },addressViewId);
}

/**
 * 获取二级地址
 * @param province
 */
function getCity(province,addressViewId) {
    var url = base + '/select/citySelect';
    var dataForm = {'provinceCode': province};
    doAjaxWithObject(url,'GET',dataForm,function (data,addressViewId) {

        $.each(data, function (k, v) {
            $("#" + addressViewId.cityId)[0].options.add(new Option(v.name, v.code));
        });

        getCountry($("#" + addressViewId.cityId).val(),addressViewId);

    },addressViewId);
}

/**
 * 获取二级地址成功
 * @param data
 */
function getCitySuccess(data,addressViewId) {

    $.each(data, function (k, v) {
        $("#" + addressViewId.cityId)[0].options.add(new Option(v.name, v.code));
    });

    getCountry($("#" + addressViewId.cityId).val(),addressViewId);

}

function getCountry(city,addressViewId) {
    var url = base + '/select/countrySelect';
    var dataForm = {'cityCode': city};
    doAjaxWithObject(url,'GET',dataForm,function (data,addressViewId) {

        $.each(data, function (k, v) {
            $("#" + addressViewId.countryId)[0].options.add(new Option(v.name, v.code));
        });
        $("#" + addressViewId.provinceId).selectpicker();
        $("#" + addressViewId.cityId).selectpicker();
        $("#" + addressViewId.countryId).selectpicker();
        $('.three-address').show();

        if (addressViewId.areaId){
            onCountryChange(data[0].code,addressViewId.areaId);
        }

    },addressViewId);
}

function onCountryChange(countryCode, areaViewId) {
    var url = base + '/select/areaSelect';
    var dataForm = {'countryCode': countryCode};
    doAjaxWithObject(url,'GET',dataForm,function (data,areaViewId) {

        $("#" + areaViewId).empty();

        $.each(data, function (k, v) {
            $("#" + areaViewId)[0].options.add(new Option(v.name, v.code));
        });
        $("#" + areaViewId).selectpicker('refresh');

    },areaViewId);
}

/**
 * 根据code获取地址信息
 * @param code
 */
function getAddressByCode(province,city,country,area,success,viewId) {
    var url = base + '/select/getAddressByCode';
    var dataForm = {
        'provinceCode': province,
        'cityCode': city,
        'countryCode': country,
        'areaCode' : area
    };
    doAjax(url,'GET',dataForm,success,viewId);
}