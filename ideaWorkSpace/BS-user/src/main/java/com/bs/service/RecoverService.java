package com.bs.service;

import com.bs.dao.entity.Recover;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */
public interface RecoverService {
    /**
     * 新增康复训练
     * @param recover
     */
    void addRecover(Recover recover);

    /**
     * 通过订单编号获取康复训练列表
     * @param orderId
     * @return
     */
    List<Recover> getByOrderId(Integer orderId);
}
