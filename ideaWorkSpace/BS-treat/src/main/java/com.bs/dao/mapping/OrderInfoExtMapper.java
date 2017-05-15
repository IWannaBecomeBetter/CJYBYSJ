package com.bs.dao.mapping;

import com.bs.dao.entity.OrderInfoExtExample;

import java.util.List;

/**
 * 订单查询扩展MAPPER
 * Created by 陈继赟 on 16/5/2.
 */
public interface OrderInfoExtMapper {

    /**
     * 根据订单状态和账户统计
     * @param orderInfoExtExample
     * @return
     */
    int qryOrderNumByAccountIdAndStatus(OrderInfoExtExample orderInfoExtExample);

    /**
     * 根据订单状态和账户查询列表数据
     * @param orderInfoExtExample
     * @return
     */
    List qryOrderListByAccountIdAndStatus(OrderInfoExtExample orderInfoExtExample);
}
