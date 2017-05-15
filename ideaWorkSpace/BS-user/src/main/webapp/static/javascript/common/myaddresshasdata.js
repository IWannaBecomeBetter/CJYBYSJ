/**
 * 多级联动select id 分别为province、city、country 、area
 */

/**
 * 地址多级选择初始化
 */
function addressInitHasData(viewId,province,city,country,area) {

    var url = base + '/select/provinceSelect';
    var address = new Object();
    address.viewId = viewId;
    address.province = province;
    address.city = city;
    address.country = country;
    address.area = area;
    doAjaxWithObject(url,'GET','',getProvinceSuccessHasData,address);
}

/**
 * 获取省份成功
 * @param data 省份列表
 * @param viewId 需要操作的对象
 * @param mydata 需要赋的值
 */
function getProvinceSuccessHasData(data,address) {
    $.each(data, function (k, v) {
        $(address.viewId).find(".province")[0].options.add(new Option(v.name, v.code));
    });
    if(address.province){
        $(address.viewId).find(".province").selectpicker();
        $(address.viewId).find(".province").selectpicker('val',address.province);
    } else {
        address.province = $(address.viewId).find(".province").val();
    }
    getCityHasData(address);
}

/**
 * 获取二级地址
 * @param province
 */
function getCityHasData(address) {
    var url = base + '/select/citySelect';
    var dataForm = {'provinceCode': address.province};
    doAjaxWithObject(url,'GET',dataForm,getCitySuccessHasData,address);
}


/**
 * 获取二级地址成功
 * @param data 二级地址的列表
 * @param viewId 需要进行操作的对象
 * @param mydata 需要赋的值
 */
function getCitySuccessHasData(data,address) {
    $.each(data, function (k, v) {
        $(address.viewId).find(".city")[0].options.add(new Option(v.name, v.code));
    });
    if(address.city){
        $(address.viewId).find(".city").selectpicker();
        $(address.viewId).find(".city").selectpicker('val',address.city);
    }else{
        address.city = $(address.viewId).find(".city").val();
    }
    getCountryHasData(address);

}

/**
 * 获取三级地址
 * @param city
 */
function getCountryHasData(address) {
    var url = base + '/select/countrySelect';
    var dataForm = {'cityCode': address.city};
    doAjaxWithObject(url,'GET',dataForm,getCountrySuccessHasData,address);
}


/**
 * 获取三级地址成功
 * @param data 三级地址列表
 * @param viewId 需要进行操作的对象
 * @param mydata 需要赋的值
 */
function getCountrySuccessHasData(data,address) {
    $.each(data, function (k, v) {
        $(address.viewId).find(".country")[0].options.add(new Option(v.name, v.code));
    });
    if(address.country){
        $(address.viewId).find(".country").selectpicker();
        $(address.viewId).find(".country").selectpicker('val',address.country);
    }else{
        address.country = $(address.viewId).find(".country").val();
    }

    getAreaHasData(address);
}


/**
 * 获取四级地址
 * @param country
 */
function getAreaHasData(address){
    var url = base + '/select/areaSelect';
    var dataForm = {'countryCode': address.country};
    doAjaxWithObject(url,'GET',dataForm,getAreaSuccessHasData,address);
}

/**
 * 获取四级地址成功
 * @param data
 * @param viewId
 * @param mydata
 */
function getAreaSuccessHasData(data,address){
    if($(address.viewId).find(".area")[0]){
        $(address.viewId).find(".area")[0].length=0;

        $.each(data, function (k, v) {
            $(address.viewId).find(".area")[0].options.add(new Option(v.name, v.code));
        });

        if(address.area){
            $(address.viewId).find(".area").selectpicker('refresh');
            $(address.viewId).find(".area").selectpicker('val',address.area);
        }else {
            address.area = $(address.viewId).find(".area").val();
            $(address.viewId).find(".area").selectpicker('refresh');
        }

        $(address.viewId).show();
    }
}


//封装函数当地址select改变时对应的子地址改变
function onAddressChange(viewId){
    var address = new Object();
    address.viewId = viewId;
    address.country = $(address.viewId).find(".country").val();
    getAreaHasData(address);
}