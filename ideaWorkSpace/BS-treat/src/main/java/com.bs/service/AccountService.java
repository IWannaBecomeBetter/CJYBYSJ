package com.bs.service;

import com.bs.dao.entity.Account;

/**
 * Created by Administrator on 2017/3/31.
 */
public interface AccountService {
    /**
     * 患者登录
     * @param account
     * @return
     */
    Account login(Account account);

    /**
     * 患者注册
     * @param account
     * @return
     */
    Account register(Account account);

    /**
     * 根据主键获取账户对象
     * @param accountId
     * @return
     */
    Account getById(Integer accountId);

    /**
     * 账户设置头像信息
     * @param accountId
     * @param head
     */
    void setHead(Integer accountId, String head);

    /**
     * 修改账户密码
     * @param accountId
     * @param password
     */
    void updatePassword(Integer accountId, String password);

    /**
     * 修改或绑定邮箱
     * @param accountId
     * @param email
     */
    void bindEmail(Integer accountId, String email);
}
