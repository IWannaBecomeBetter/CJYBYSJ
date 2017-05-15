package com.bs.service.Impl;

import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.util.DateUtil;
import com.bs.common.util.IDCardValidate;
import com.bs.dao.entity.Account;
import com.bs.dao.entity.Patient;
import com.bs.dao.entity.PatientExample;
import com.bs.dao.mapping.AccountMapper;
import com.bs.dao.mapping.PatientMapper;
import com.bs.service.PatientService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 */
@Transactional
@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private AccountMapper accountMapper;
    public void identification(Patient patient, Account account) {
        account.setUpdateTime(DateUtil.getTimestamp());
        account.setStatus(SysParamDetailConstant.ACCOUNT_STATUS_AUTHEN);
        accountMapper.updateByPrimaryKeySelective(account);
        PatientExample patientExample = new PatientExample();
        PatientExample.Criteria criteria = patientExample.createCriteria();
        criteria.andAccountIdEqualTo(account.getId());
        List<Patient> patients = patientMapper.selectByExample(patientExample);
        Patient patientData = new Patient();
        if(CollectionUtils.isNotEmpty(patients)){
            patientData = patients.get(0);
            patientData.setCreatTime(DateUtil.getTimestamp());
            patientData.setUpdateTime(DateUtil.getTimestamp());
        }else{
            patientData.setCreatTime(DateUtil.getTimestamp());
            patientData.setUpdateTime(DateUtil.getTimestamp());
        }
        patientData.setAccountId(account.getId());
        patientData.setName(patient.getName());
        patientData.setIdCode(patient.getIdCode());
        patientData.setFace(patient.getFace());
        patientData.setBack(patient.getBack());

        // 根据身份证获取默认年龄+性别
        patientData.setAge(String.valueOf(IDCardValidate.getAgeFromIdcard(patient.getIdCode())));
        if (IDCardValidate.maleOrFemalByIdCard(patient.getIdCode()).equals("female")){
            patientData.setSex("2");
        } else {
            patientData.setSex("1");
        }

        // 新增or更新
        if(CollectionUtils.isEmpty(patients)) {
            patientMapper.insertSelective(patientData);
        } else {
            patientMapper.updateByPrimaryKeySelective(patientData); // 使用patientData更新
        }
    }

    public Patient getByAccountId(Integer accountId) {
        PatientExample patientExample = new PatientExample();
        patientExample.createCriteria().andAccountIdEqualTo(accountId);
        List<Patient> patients = patientMapper.selectByExample(patientExample);
        if(CollectionUtils.isNotEmpty(patients)){
            return patients.get(0);
        }else{
            return new Patient();
        }
    }
}
