package com.bs.service.Impl;

import com.bs.common.util.DateUtil;
import com.bs.dao.entity.Recover;
import com.bs.dao.entity.RecoverExample;
import com.bs.dao.mapping.RecoverMapper;
import com.bs.service.RecoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */
@Service
@Transactional
public class RecoverServiceImpl implements RecoverService {

    @Autowired
    private RecoverMapper recoverMapper;

    public void addRecover(Recover recover) {
        recover.setCreateTime(DateUtil.getTimestamp());
        recoverMapper.insertSelective(recover);
    }

    public List<Recover> getByOrderId(Integer orderId) {
        RecoverExample recoverExample = new RecoverExample();
        recoverExample.createCriteria().andOrderIdEqualTo(orderId);
        List<Recover> recovers = recoverMapper.selectByExample(recoverExample);
        return recovers;
    }
}
