package com.bs.service;

import com.bs.common.entity.PageInfo;
import com.bs.dao.entity.Account;
import com.bs.dao.entity.OrderBooking;
import com.bs.dao.entity.OrderInfo;
import com.bs.dao.entity.OrderInfoExt;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */
public interface OrderService {
    /**
     * 根据用户信息和订单状态获取订单列表
     * @param orderInfo
     * @param account
     * @return
     */
    List<OrderInfoExt> orderListByStatus(OrderInfo orderInfo, Account account);

    /**
     * 订单预定
     * @param orderInfo
     * @param orderBooking
     * @param account
     */
    OrderInfo orderBooking(OrderInfo orderInfo, OrderBooking orderBooking,Account account);

    /**
     * 通过主键获取订单信息
     * @param id
     * @return
     */
    OrderInfo getOrderInfoById(Integer id);

    /**
     * 通过订单主键获取预定信息
     * @param orderId
     * @return
     */
    OrderBooking getOrderBookingByOrderId(Integer orderId);

    /**
     * 订单结束
     * @param orderInfo
     */
    void orderFinish(OrderInfo orderInfo);

    /**
     * 通过订单号获取订单
     * @param orderCode
     * @return
     */
    OrderInfo getOrderByCode(String orderCode);
}
