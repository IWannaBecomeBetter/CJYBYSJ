package com.bs.dao.entity;

/**
 * Created by 陈继赟 on 2016/6/10.
 */
public class PatientExt {
    private String id;

    //账户
    private Account account;

    //患者
    private Patient patient;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
