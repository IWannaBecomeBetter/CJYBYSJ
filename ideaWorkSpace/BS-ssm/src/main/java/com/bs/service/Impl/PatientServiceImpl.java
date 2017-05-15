package com.bs.service.Impl;

import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.entity.AccountDataTableQuery;
import com.bs.common.entity.DataTableResult;
import com.bs.common.util.DateUtil;
import com.bs.common.util.StringUtil;
import com.bs.dao.entity.*;
import com.bs.dao.mapping.AccountMapper;
import com.bs.dao.mapping.PatientExtMapper;
import com.bs.service.PatientService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/4/20.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientExtMapper patientExtMapper;
    @Autowired
    private AccountMapper accountMapper;



    public DataTableResult patientList(AccountDataTableQuery accountDataTableQuery) {
        PatientExtExample patientExtExample = new PatientExtExample();
        AccountExample accountExample = patientExtExample.getAccountExample();
        AccountExample.Criteria criteria = accountExample.createCriteria();
        if(StringUtil.isNotEmptyObject(accountDataTableQuery.getSearch())){
            criteria.andTelephoneLike("%"+accountDataTableQuery.getSearch()+"%");
        }
        if(StringUtil.isNotEmptyObject(accountDataTableQuery.getStatus())){
            criteria.andStatusEqualTo(accountDataTableQuery.getStatus());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtil.isNotEmptyObject(accountDataTableQuery.getStartTime())){
            criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.getTimestamp(accountDataTableQuery.getStartTime(), dateFormat));
        }
        if(StringUtil.isNotEmptyObject(accountDataTableQuery.getEndTime())){
            criteria.andCreateTimeLessThanOrEqualTo(DateUtil.getTimestamp(accountDataTableQuery.getEndTime(), dateFormat));
        }
        if (null != accountDataTableQuery.getiDisplayStart() && accountDataTableQuery.getiDisplayLength() != null) {
            accountExample.setLimitClauseCount(accountDataTableQuery.getiDisplayLength());
            accountExample.setLimitClauseStart(accountDataTableQuery.getiDisplayStart());
        }
        int total = patientExtMapper.qryPatientExtNum(patientExtExample);
        List<PatientExt> patientExts = patientExtMapper.qryPatientExtList(patientExtExample);
        DataTableResult dataTableResult = new DataTableResult();
        dataTableResult.setAaData(patientExts);
        dataTableResult.setiTotalDisplayRecords(total);
        dataTableResult.setiTotalRecords(total);
        return dataTableResult;
    }

    @Override
    public PatientExt getPatientByAccountId(String accountId) {
        PatientExtExample patientExtExample = new PatientExtExample();
        AccountExample accountExample = patientExtExample.getAccountExample();
        accountExample.createCriteria().andIdEqualTo(Integer.parseInt(accountId));
        List<PatientExt> patientExts = patientExtMapper.qryPatientExtList(patientExtExample);
        if(CollectionUtils.isNotEmpty(patientExts)){
            return patientExts.get(0);
        }else{
            return new PatientExt();
        }
    }

    @Override
    public void audit(Account account, String auditType) {
        if(auditType.equals(SysParamDetailConstant.AUDIT_TYPE_OK)){
            account.setStatus(SysParamDetailConstant.ACCOUNT_STATUS_CONFIRM);
            account.setUpdateTime(DateUtil.getTimestamp());
        }else{
            account.setStatus(SysParamDetailConstant.ACCOUNT_STATUS_FAILED);
            account.setUpdateTime(DateUtil.getTimestamp());
        }
        accountMapper.updateByPrimaryKeySelective(account);
    }
}
