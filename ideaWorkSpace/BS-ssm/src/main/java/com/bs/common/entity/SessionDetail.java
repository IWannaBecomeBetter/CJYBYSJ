package com.bs.common.entity;

import java.io.Serializable;

/**
 * 用户登录信息
 */
public class SessionDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private Integer accountId;

    /**
     * 用户类型
     */
    private String type;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 名称
     */
    private String name;

    /**
     * 账号状态
     */
    private String status;

    /**
     * 头像地址
     */
    private String headPath;

    /**
     * 联系人下拉列表
     */
    private String linkSelect;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getLinkSelect() {
        return linkSelect;
    }

    public void setLinkSelect(String linkSelect) {
        this.linkSelect = linkSelect;
    }
}
