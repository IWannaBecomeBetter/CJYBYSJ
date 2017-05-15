package com.bs.service.Impl;

import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.util.DateUtil;
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
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Override
    public Treat getTreatByAccountId(String accountId) {
        TreatExample treatExample = new TreatExample();
        treatExample.createCriteria().andAccountIdEqualTo(Integer.parseInt(accountId));
        List<Treat> treats = treatMapper.selectByExample(treatExample);
        if(CollectionUtils.isNotEmpty(treats)){
            return treats.get(0);
        }else{
            return new Treat();
        }
    }

    @Override
    public void audit(String accountId, String auditType) {
        Account account = accountMapper.selectByPrimaryKey(Integer.parseInt(accountId));
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
