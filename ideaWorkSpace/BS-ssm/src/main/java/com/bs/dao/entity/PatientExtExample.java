package com.bs.dao.entity;
/**
 * Created by 陈继赟 on 2016/6/10.
 */
public class PatientExtExample {
    //账户信息
    private AccountExample accountExample;

    //患者信息
    private PatientExample patientExample;

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    protected String orderByClause;

    public PatientExtExample() {
        this.accountExample = new AccountExample();
        this.patientExample = new PatientExample();
    }

    public AccountExample getAccountExample() {
        return accountExample;
    }

    public void setAccountExample(AccountExample accountExample) {
        this.accountExample = accountExample;
    }

    public PatientExample getPatientExample() {
        return patientExample;
    }

    public void setPatientExample(PatientExample patientExample) {
        this.patientExample = patientExample;
    }
}
