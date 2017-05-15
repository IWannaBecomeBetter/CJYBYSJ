package com.bs.common.entity;

/**
 * AJAX返回结果
 * Created by fusj on 16/2/1.
 */
public class ResultBean {
    /**
     * 处理标志
     */
    private Boolean flag;

    /**
     * 处理描述
     */
    private String msg;

    /**
     * 返回结果
     */
    private Object data;

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }

    /**
     * 返回结果扩展
     */
    private Object extData;

    public ResultBean(Boolean flag) {
        this.flag = flag;
    }

    public ResultBean(Boolean flag, String msg) {
        this(flag);
        this.msg = msg;
    }

    public ResultBean(Boolean flag, Object data) {
        this(flag);
        this.data = data;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
