/**
 * Created by Administrator on 2017/4/7.
 */
/**
 * Created by Administrator on 2017/4/6.
 */
var home = {
    init:function () {
        var that = this;
        //初始化页面元素
        var gallery = mui('#slider1');
        gallery.slider({
            interval:1000//自动轮播周期，若为0则不自动播放，默认为0；
        });
        that.bindEvent();
    },
    bindEvent:function () {
        
    }
}


$(function () {
    home.init()
})