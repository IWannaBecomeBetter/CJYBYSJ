package com.bs.service;

import com.bs.common.entity.DataTableResult;
import com.bs.common.entity.OrderDataTableQuery;
import com.bs.dao.entity.OrderBooking;
import com.bs.dao.entity.OrderInfo;

/**
 * Created by Administrator on 2017/4/27.
 */
public interface OrderService {
    /**
     * 订单列表
     * @param orderDataTableQuery
     * @return
     */
    DataTableResult orderList(OrderDataTableQuery orderDataTableQuery);


    /**
     * 拒单列表
     * @param orderDataTableQuery
     * @return
     */
    DataTableResult refuseList(OrderDataTableQuery orderDataTableQuery);

    /**
     * 通过主键获取订单的基本信息
     * @param orderId
     * @return
     */
    OrderInfo getOrderInfoById(String orderId);

    /**
     * 通过订单编号获取预约信息
     * @param orderId
     * @return
     */
    OrderBooking getOrderBookingByOrderId(String orderId);

    /**
     * 订单审核
     * @param auditType
     * @param auditDesc
     * @param orderId
     */
    void orderAudit(String auditType,String auditDesc,String orderId);

    /**
     * 分派康复师
     * @param orderId
     * @param accountId
     */
    void treatAllocate(String orderId,String accountId);
}
