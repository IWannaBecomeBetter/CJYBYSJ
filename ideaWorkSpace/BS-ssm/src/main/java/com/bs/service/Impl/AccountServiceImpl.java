package com.bs.service.Impl;

import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.entity.AccountDataTableQuery;
import com.bs.common.entity.DataTableResult;
import com.bs.common.entity.PageInfo;
import com.bs.common.exception.ApplicationException;
import com.bs.common.util.DateUtil;
import com.bs.common.util.StringUtil;
import com.bs.dao.entity.Account;
import com.bs.dao.entity.AccountExample;
import com.bs.dao.entity.Treat;
import com.bs.dao.entity.TreatExample;
import com.bs.dao.mapping.AccountMapper;
import com.bs.service.AccountService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */
@Transactional
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account login(Account account) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andTelephoneEqualTo(account.getTelephone())
                .andPasswordEqualTo(account.getPassword())
                .andTypeEqualTo(SysParamDetailConstant.ACCOUNT_TYPE_MANAGER);
        List<Account> accountList = accountMapper.selectByExample(accountExample);
        if(CollectionUtils.isNotEmpty(accountList)){
            return accountList.get(0);
        }else{
            throw new ApplicationException("登录失败，请核对信息后重试");
        }
    }

    @Override
    public DataTableResult accountList(AccountDataTableQuery accountDataTableQuery) {
        AccountExample accountExample = new AccountExample();
        AccountExample.Criteria criteria = accountExample.createCriteria();
        if(StringUtil.isNotEmptyObject(accountDataTableQuery.getType())){
            criteria.andTypeEqualTo(accountDataTableQuery.getType());
        }
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
        accountExample.setOrderByClause(" CREATE_TIME desc ");
        int total = accountMapper.countByExample(accountExample);
        List<Account> accounts = accountMapper.selectByExample(accountExample);
        DataTableResult dataTableResult = new DataTableResult();
        dataTableResult.setAaData(accounts);
        dataTableResult.setiTotalDisplayRecords(total);
        dataTableResult.setiTotalRecords(total);
        return dataTableResult;
    }

    @Override
    public PageInfo allocateTreatList(Treat treat, PageInfo pageInfo) {
        AccountExample accountExample = new AccountExample();
        AccountExample.Criteria criteria = accountExample.createCriteria();
        criteria.andTypeEqualTo(SysParamDetailConstant.ACCOUNT_TYPE_TREAT).andStatusEqualTo(SysParamDetailConstant.ACCOUNT_STATUS_CONFIRM);
        if(StringUtil.isNotEmptyObject(treat.getName())){
            criteria.andNameLike("%"+treat.getName()+"%");
        }
        accountExample.setLimitClauseStart(pageInfo.getPageStart());
        accountExample.setLimitClauseCount(pageInfo.getPageCount());
        List<Account> accounts = accountMapper.selectByExample(accountExample);
        if(CollectionUtils.isNotEmpty(accounts)){
            pageInfo.setList(accounts);
            pageInfo.setPageCount(accountMapper.countByExample(accountExample));

        }else{
            pageInfo.setList(new ArrayList());
            pageInfo.setPageCount(0);
        }
        return pageInfo;
    }

    @Override
    public Account getById(Integer id) {
        Account account = accountMapper.selectByPrimaryKey(id);
        return account;
    }
}
