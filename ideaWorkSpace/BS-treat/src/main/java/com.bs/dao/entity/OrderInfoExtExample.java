package com.bs.dao.entity;

/**
 * Created by Administrator on 2017/4/26.
 */
public class OrderInfoExtExample {

    private OrderInfoExample orderInfoExample;

    private OrderBookingExample orderBookingExample;

    protected String orderByClause;

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public OrderInfoExtExample() {
        this.orderInfoExample = new OrderInfoExample();
        this.orderBookingExample = new OrderBookingExample();
    }

    public OrderInfoExample getOrderInfoExample() {
        return orderInfoExample;
    }

    public void setOrderInfoExample(OrderInfoExample orderInfoExample) {
        this.orderInfoExample = orderInfoExample;
    }

    public OrderBookingExample getOrderBookingExample() {
        return orderBookingExample;
    }

    public void setOrderBookingExample(OrderBookingExample orderBookingExample) {
        this.orderBookingExample = orderBookingExample;
    }
}
