package com.bs.dao.entity;

/**
 * Created by Administrator on 2017/4/26.
 */
public class OrderInfoExt {

    private String id;
    /**
     * 订单信息
     */
    private OrderInfo orderInfo;

    /**
     * 订单拓展信息
     */
    private OrderBooking orderBooking;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public OrderBooking getOrderBooking() {
        return orderBooking;
    }

    public void setOrderBooking(OrderBooking orderBooking) {
        this.orderBooking = orderBooking;
    }
}
