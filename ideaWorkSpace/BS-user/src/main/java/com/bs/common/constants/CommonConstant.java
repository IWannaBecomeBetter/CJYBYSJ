package com.bs.common.constants;

/**
 * 常用常量
 * Created by fusj on 16/3/9.
 */
public class CommonConstant {

    /**
     * 存入cookie的value
     */
    public static final String COOKIE_VALUE = "CJY_SESSION_PATIENT_ID";

    /**
     * cookie生命周期，单位秒
     */
    public static final Integer SESSION_TIME_OUT = 30 *  60;


    public static final String IP_CACHE_KEY = "IP_CACHE_KEY";
    public static final String PHONE_CACHE_KEY = "PHONE_CACHE_KEY";

    /**
     * 24小时过期
     */
    public static final Integer SESSION_TIME_OUT_10Min = 10 * 60;

    /**
     * 24小时过期
     */
    public static final Integer SESSION_TIME_OUT_DAY = 24 * 60 * 60;

    /**
     * 一个星期过期
     */
    public static final Integer SESSION_TIME_OUT_WEEK = 7 * 24 * 60 * 60;

    /**
     * 半个月过期
     */
    public static final Integer SESSION_TIME_OUT_HELF_MONTH = 15 * 24 * 60 * 60;

    /**
     * 一个月过期
     */
    public static final Integer SESSION_TIME_OUT_MONTH = 30 * 24 * 60 * 60;

    /**
     * 微信公众号access_token存放到redis的key
     */
    public static final String ACCESS_TOKEN_VALUE = "ACCESS_TOKEN";

    /**
     * 订阅号公众号
     */
    public static final String ACCESS_TOKEN_VALUE_FOR_SUB = "ACCESS_TOKEN_SUB";

    /**
     * 微信公众号jsapi_ticket存放到redis的key
     */
    public static final String JSAPI_TICKET_VALUE = "JSAPI_TICKET";

    /**
     * access_token是否正在加载
     */
    public static final String ACCESS_TOKEN_IS_LOADING = "IS_LOADING";

    /**
     * model返回pageInfo的key
     */
    public static final String PAGE_INFO = "pageInfo";

    /**
     * 密码加密密钥
     */
    public static final String SALT = "iCare";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PWD = "123456";

    /**
     * 验证码过期时间
     */
    public static final Integer IDENTIFY_CODE_INVALID = 15;

    /**
     * 在验证码过期时间内连续发送超时次数
     */
    public static final Integer SEND_IDENTIFY_TIMES = 10;

    /**
     * 查询单个
     */
    public static final String SINGLE_TYPE = "SINGLE_";

    /**
     * 查询列表
     */
    public static final String LIST_TYPE = "LIST_";

    /**
     * 数据库加密密钥
     */
    public static final String DB_SALT = "024ff0ff-3100-4d6c-8927-2c559ceb63f3";

    /**
     * 验证码
     */
    public static final String DEFAULT_IDENTIFY_CODE = "123456";

    /**
     * 行政区域缓存KEY
     */
    public static final String ZONE_SELECT_CACHE_VALUE = "ZONE_SELECT";

    /**
     * 省下拉列表缓存前缀
     */
    public static final String PROVICE_SELECT_PREFIX = "PROVICE_SELECT_P_";

    /**
     * 市下拉列表缓存前缀
     */
    public static final String CITY_SELECT_PREFIX = "CITY_SELECT_P_";

    /**
     * 县区下拉列表缓存前缀
     */
    public static final String COUNTRY_SELECT_PREFIX = "COUNTRY_SELECT_P_";

    /**
     * 地区下拉列表缓存前缀
     */
    public static final String AREA_SELECT_PREFIX = "AREA_SELECT_P_";

    /**
     * 单个地市前缀
     */
    public static final String ZONE_SINGLE_PREFIX = "ZONE_SINGLE_";
}
