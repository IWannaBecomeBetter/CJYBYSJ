package com.bs.service.Impl;

import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.exception.ApplicationException;
import com.bs.common.util.DateUtil;
import com.bs.dao.entity.Account;
import com.bs.dao.entity.AccountExample;
import com.bs.dao.mapping.AccountMapper;
import com.bs.service.AccountService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */
@Transactional
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    public Account login(Account account) {
        AccountExample accountExample = new AccountExample();
        AccountExample.Criteria criteria = accountExample.createCriteria();
        criteria.andTelephoneEqualTo(account.getTelephone())
                .andTypeEqualTo(SysParamDetailConstant.ACCOUNT_TYPE_TREAT);
        List<Account> accountList = accountMapper.selectByExample(accountExample);
        if(CollectionUtils.isEmpty(accountList)){
            throw new ApplicationException("账号不存在");
        }
        criteria.andPasswordEqualTo(account.getPassword());
        List<Account> accounts = accountMapper.selectByExample(accountExample);
        if(CollectionUtils.isEmpty(accounts)){
            throw new ApplicationException("密码错误，请核对后重试");
        }else{
            return accounts.get(0);
        }
    }

    public Account register(Account account) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andTelephoneEqualTo(account.getTelephone())
                .andTypeEqualTo(SysParamDetailConstant.ACCOUNT_TYPE_TREAT);
        List<Account> accounts = accountMapper.selectByExample(accountExample);
        if(CollectionUtils.isNotEmpty(accounts)){
            throw new ApplicationException("账户已存在");
        }
        account.setCreateTime(DateUtil.getTimestamp());
        account.setUpdateTime(DateUtil.getTimestamp());
        account.setType(SysParamDetailConstant.ACCOUNT_TYPE_TREAT);
        account.setStatus(SysParamDetailConstant.ACCOUNT_STATUS_NEW);
        accountMapper.insertSelective(account);
        return account;
    }

    public Account getById(Integer accountId) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andIdEqualTo(accountId);
        List<Account> accounts = accountMapper.selectByExample(accountExample);
        return accounts.get(0);
    }

    public void setHead(Integer accountId, String head) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andIdEqualTo(accountId);
        List<Account> accounts = accountMapper.selectByExample(accountExample);
        Account account = accounts.get(0);
        account.setHead(head);
        account.setUpdateTime(DateUtil.getTimestamp());
        accountMapper.updateByPrimaryKeySelective(account);
    }

    public void updatePassword(Integer accountId, String password) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andIdEqualTo(accountId);
        List<Account> accounts = accountMapper.selectByExample(accountExample);
        Account account = accounts.get(0);
        account.setPassword(password);
        account.setUpdateTime(DateUtil.getTimestamp());
        accountMapper.updateByPrimaryKeySelective(account);
    }

    public void bindEmail(Integer accountId, String email) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andIdEqualTo(accountId);
        List<Account> accounts = accountMapper.selectByExample(accountExample);
        Account account = accounts.get(0);
        account.setEmail(email);
        account.setUpdateTime(DateUtil.getTimestamp());
        accountMapper.updateByPrimaryKeySelective(account);
    }
}
