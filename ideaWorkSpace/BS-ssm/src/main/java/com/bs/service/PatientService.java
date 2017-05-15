package com.bs.service;

import com.bs.common.entity.AccountDataTableQuery;
import com.bs.common.entity.DataTableResult;
import com.bs.dao.entity.Account;
import com.bs.dao.entity.PatientExt;

/**
 * Created by Administrator on 2017/4/20.
 */
public interface PatientService {
    /**
     * 根据账户编号查询患者信息
     * @param accountId
     * @return
     */
    PatientExt getPatientByAccountId(String accountId);

    /**
     * 患者账户认证审核
     * @param account
     * @param auditType
     */
    void audit(Account account,String auditType);
}
