package com.bs.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * 七牛云存储配置
 * Created by joeshao on 16/7/14.
 */
@Repository
public class QiniuSetting {

    /**
     * 域名
     */
    @Value("#{propertiesReader['qiniu.domain']}")
    private String domain;

    /**
     * 资源空间名
     */
    @Value("#{propertiesReader['qiniu.bucket']}")
    private String bucket;

    /**
     * AccessKey
     */
    @Value("#{propertiesReader['qiniu.accessKey']}")
    private String accessKey;

    /**
     * SecretKey
     */
    @Value("#{propertiesReader['qiniu.secretKey']}")
    private String secretKey;

    public String getDomain() {return domain;}

    public String getBucket() {return bucket;}

    public String getAccessKey() {return accessKey;}

    public String getSecretKey() {return secretKey;}
}
