package com.bs.service.Impl;

import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.exception.ApplicationException;
import com.bs.common.util.DateUtil;
import com.bs.common.util.JackonUtil;
import com.bs.dao.entity.Account;
import com.bs.dao.entity.AccountExample;
import com.bs.dao.entity.Suggestion;
import com.bs.dao.mapping.AccountMapper;
import com.bs.dao.mapping.SuggestionMapper;
import com.bs.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
@Service
@Transactional
public class FeedBackServiceImpl implements FeedbackService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SuggestionMapper suggestionMapper;

    public void add(Suggestion suggestion, Integer accountId) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andIdEqualTo(accountId);
        List<Account> accounts = accountMapper.selectByExample(accountExample);
        Account account = accounts.get(0);
        suggestion.setAccountId(account.getId());
        suggestion.setName(account.getName());
        suggestion.setTelephone(account.getTelephone());
        suggestion.setCreateTime(DateUtil.getTimestamp());
        suggestion.setUpdateTime(DateUtil.getTimestamp());
        suggestion.setStatus(SysParamDetailConstant.FEEDBACK_STATUS_NEW);//新增反馈
        List<String> fileIdList = new ArrayList();
        if (suggestion.getFileids() != null && suggestion.getFileids().length()>0) {
            fileIdList = new ArrayList(Arrays.asList(suggestion.getFileids().split(",")));
        }
        // 把文件转换成JSON格式
        try {
            suggestion.setFileids(JackonUtil.writeEntity2JSON(fileIdList));
        } catch (Exception ex) {
            throw new ApplicationException("系统异常", ex);
        }
        suggestionMapper.insertSelective(suggestion);
    }
}
