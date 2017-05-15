package com.bs.service;

import com.bs.dao.entity.Account;
import com.bs.dao.entity.Treat;

/**
 * Created by Administrator on 2017/5/3.
 */
public interface TreatService {
    /**
     * 根据账户编号查询康复师信息
     * @param accountId
     * @return
     */
    Treat getTreatByAccountId(String accountId);

    /**
     * 康复师账户认证审核
     * @param accountId
     * @param auditType
     */
    void audit(String accountId, String auditType);
}
