package com.bs.common.constants;

/**
 *
 * 原则上常量应该是一些不可变对象 但是例如各种状态信息 不应该在字典数据和常量中维护两份
 * 正常的做法是只在数据字典中维护，通过键值对的方式存在，中间通过公共方法，后台只通过 key 访问
 *
 */
public class SysParamDetailConstant {

    /**
     * 用户类型
     */
    public static final String ACCOUNT_TYPE_PATIENT = "1"; // 患者

    public static final String ACCOUNT_TYPE_TREAT = "2"; // 康复治疗师

    /**
     * 账户状态
     */
    public static final String ACCOUNT_STATUS_NEW = "1"; // 新增

    public static final String ACCOUNT_STATUS_AUTHEN = "2"; // 提交认证

    public static final String ACCOUNT_STATUS_CONFIRM = "3"; // 认证确认

    public static final String ACCOUNT_STATUS_FAILED = "-1"; // 认证失败

    public static final String ACCOUNT_STATUS_INVALID = "0"; // 账号失效

    /**
     * 自动登录
     */
    public static final String ACCOUNT_AUTOLOGIN_TRUE = "1"; // 自动登录

    public static final String ACCOUNT_AUTOLOGIN_FALSE = "0"; // 不自动登录


    /**
     * 订单类型
     */
    public static final String ORDER_TYPE_NAOTAN = "1"; // 脑瘫

    public static final String ORDER_TYPE_NAOCUZHONG = "2"; // 脑卒中


    /**
     * 订单状态
     */
    public static final String ORDER_STATUS_NEW = "1";//新增

    public static final String ORDER_STATUS_CONFIRM = "2";//审核通过

    public static final String ORDER_STATUS_FAILED = "-2";//审核失败

    public static final String ORDER_STATUS_DISPATCH_TREAT = "3";//派单到康复师

    public static final String ORDER_STATUS_TREAT_ACCEPT = "4";//康复师接单

    public static final String ORDER_STATUS_TREAT_REFUSE = "-4";//康复师拒单

    public static final String ORDER_STATUS_OVER = "10";//完成


    /**
     * 反馈状态
     */
    public static final String FEEDBACK_STATUS_NEW = "1";//新增

    public static final String FEEDBACK_STATUS_CONFIRM = "2";//已确认

}
