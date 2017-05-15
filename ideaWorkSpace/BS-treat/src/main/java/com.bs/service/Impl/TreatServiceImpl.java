package com.bs.service.Impl;

import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.exception.ApplicationException;
import com.bs.common.util.DateUtil;
import com.bs.common.util.IDCardValidate;
import com.bs.common.util.JackonUtil;
import com.bs.dao.entity.Account;
import com.bs.dao.entity.Treat;
import com.bs.dao.entity.TreatExample;
import com.bs.dao.mapping.AccountMapper;
import com.bs.dao.mapping.TreatMapper;
import com.bs.service.TreatService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */
@Service
@Transactional
public class TreatServiceImpl implements TreatService {
    @Autowired
    private TreatMapper treatMapper;
    @Autowired
    private AccountMapper accountMapper;
    public void identification(Treat treat, Account account) {
        account.setUpdateTime(DateUtil.getTimestamp());
        accountMapper.updateByPrimaryKeySelective(account);
        TreatExample treatExample = new TreatExample();
        TreatExample.Criteria criteria = treatExample.createCriteria();
        criteria.andAccountIdEqualTo(account.getId());
        List<Treat> treats = treatMapper.selectByExample(treatExample);
        Treat treatData = new Treat();
        if(CollectionUtils.isNotEmpty(treats)){
            treatData = treats.get(0);
            treatData.setCreateTime(DateUtil.getTimestamp());
            treatData.setUpdateTime(DateUtil.getTimestamp());
        }else{
            treatData.setCreateTime(DateUtil.getTimestamp());
            treatData.setUpdateTime(DateUtil.getTimestamp());
        }
        treatData.setAccountId(account.getId());
        treatData.setName(treat.getName());
        treatData.setIdCode(treat.getIdCode());
        treatData.setFace(treat.getFace());
        treatData.setBack(treat.getBack());
        treatData.setStatus(SysParamDetailConstant.TREAT_STATUS_IDENTIFY);
        // 根据身份证获取默认年龄+性别
        treatData.setAge(String.valueOf(IDCardValidate.getAgeFromIdcard(treat.getIdCode())));
        if (IDCardValidate.maleOrFemalByIdCard(treat.getIdCode()).equals("female")){
            treatData.setSex("2");
        } else {
            treatData.setSex("1");
        }

        // 新增or更新
        if(CollectionUtils.isEmpty(treats)) {
            treatMapper.insertSelective(treatData);
        } else {
            treatMapper.updateByPrimaryKeySelective(treatData); // 使用patientData更新
        }
    }

    public void personalInfo(Treat treat, Account account) {
        account.setUpdateTime(DateUtil.getTimestamp());
        account.setStatus(SysParamDetailConstant.ACCOUNT_STATUS_AUTHEN);
        accountMapper.updateByPrimaryKeySelective(account);

        TreatExample treatExample = new TreatExample();
        TreatExample.Criteria criteria = treatExample.createCriteria();
        criteria.andAccountIdEqualTo(account.getId());
        List<Treat> treats = treatMapper.selectByExample(treatExample);

        Treat treatData = treats.get(0);
        treatData.setUpdateTime(DateUtil.getTimestamp());
        treatData.setLevel(treat.getLevel());
        treatData.setAddress(treat.getAddress());
        treatData.setWorkYears(treat.getWorkYears());
        treatData.setServiceExp(treat.getServiceExp());
        treatData.setStatus(SysParamDetailConstant.TREAT_STATUS_PERSON);
        List<String> fileIdList = new ArrayList();
        if (treat.getFileids() != null && treat.getFileids().length()>0) {
            fileIdList = new ArrayList(Arrays.asList(treat.getFileids().split(",")));
        }
        // 把文件转换成JSON格式
        try {
            treat.setFileids(JackonUtil.writeEntity2JSON(fileIdList));
        } catch (Exception ex) {
            throw new ApplicationException("系统异常", ex);
        }
        treatData.setFileids(treat.getFileids());
        treatMapper.updateByPrimaryKeySelective(treatData); // 使用treatData更新
    }

    public Treat getByAccountId(Integer accountId) {
        TreatExample treatExample = new TreatExample();
        treatExample.createCriteria().andAccountIdEqualTo(accountId);
        List<Treat> treats = treatMapper.selectByExample(treatExample);
        if(CollectionUtils.isNotEmpty(treats)){
            return treats.get(0);
        }else{
            return new Treat();
        }
    }
}
