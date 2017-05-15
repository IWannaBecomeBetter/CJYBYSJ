package com.bs.service;

import com.bs.dao.entity.Account;
import com.bs.dao.entity.Patient;

/**
 * Created by Administrator on 2017/4/6.
 */
public interface PatientService {
    /**
     * 患者实名认证
     * @param patient
     */
    void identification(Patient patient, Account account);

    /**
     * 根据账户编号获取患者信息
     * @param accountId
     * @return
     */
    Patient getByAccountId(Integer accountId);
}
