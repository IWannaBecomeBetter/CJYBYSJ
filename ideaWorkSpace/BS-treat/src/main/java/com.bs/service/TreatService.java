package com.bs.service;

import com.bs.common.entity.PageInfo;
import com.bs.dao.entity.Account;
import com.bs.dao.entity.Treat;

/**
 * Created by Administrator on 2017/4/28.
 */
public interface TreatService {
    /**
     * 康复师实名认证
     * @param treat
     */
    void identification(Treat treat, Account account);


    /**
     * 康复师完善个人简介
     * @param treat
     */
    void personalInfo(Treat treat, Account account);


    /**
     * 根据账户编号获取康复师信息
     * @param accountId
     * @return
     */
    Treat getByAccountId(Integer accountId);
}
