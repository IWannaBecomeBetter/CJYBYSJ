package com.bs.common.entity;


import java.util.Date;

/**
 * Created by joeshao on 16/9/23.
 */
public class ImageDetail {

    /**
     * 图片url
     */
    private String src;

    /**
     * 服务器FileStorageInfo对象的id
     */
    private String serverId;

    /**
     * 七牛key
     */
    private String key;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getSrc() {
        return src;
    }

    public ImageDetail(String src, String serverId, String key, Date createTime) {
        this.src = src;
        this.serverId = serverId;
        this.key = key;
        this.createTime = createTime;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
