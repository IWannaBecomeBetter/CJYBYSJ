package com.bs.common.entity;

/**
 * Created by 93701 on 2016/9/13.
 */
public class AccountDataTableQuery extends DataTableQuery {
    /**
     * 账户状态
     */
    private String status;

    /**
     * 账户类型
     */
    private String type;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
