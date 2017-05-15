package com.bs.service.Impl;

import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.exception.ApplicationException;
import com.bs.common.util.DateUtil;
import com.bs.common.util.JackonUtil;
import com.bs.dao.entity.*;
import com.bs.dao.mapping.OrderBookingMapper;
import com.bs.dao.mapping.OrderInfoExtMapper;
import com.bs.dao.mapping.OrderInfoMapper;
import com.bs.service.OrderService;
import com.google.common.util.concurrent.AtomicDouble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    private static final AtomicDouble ad = new AtomicDouble(95);

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderBookingMapper orderBookingMapper;

    @Autowired
    private OrderInfoExtMapper orderInfoExtMapper;

    public List<OrderInfoExt> orderListByStatus(OrderInfo orderInfo, Account account) {
        OrderInfoExtExample orderInfoExtExample = new OrderInfoExtExample();
        OrderInfoExample orderInfoExample = orderInfoExtExample.getOrderInfoExample();
        OrderInfoExample.Criteria criteria = orderInfoExample.createCriteria();
        criteria.andTreatAccountIdEqualTo(account.getId());
        if(orderInfo.getOrderStatus().equals(SysParamDetailConstant.ORDER_STATUS_OVER)){
            criteria.andOrderStatusEqualTo(orderInfo.getOrderStatus());
        }else{
            criteria.andOrderStatusNotEqualTo(SysParamDetailConstant.ORDER_STATUS_OVER);
        }
        orderInfoExtExample.setOrderByClause(" a.CREATE_TIME desc ");
        List<OrderInfoExt> orderInfoExts = orderInfoExtMapper.qryOrderListByAccountIdAndStatus(orderInfoExtExample);
        return orderInfoExts;
    }

    public OrderInfo getOrderInfoById(Integer id) {
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(id);
        return orderInfo;
    }

    public OrderBooking getOrderBookingByOrderId(Integer orderId) {
        OrderBookingExample orderBookingExample = new OrderBookingExample();
        orderBookingExample.createCriteria().andOrderIdEqualTo(orderId);
        List<OrderBooking> orderBookings = orderBookingMapper.selectByExample(orderBookingExample);
        return orderBookings.get(0);
    }

    public void refuse(Integer orderId, String refuseReason) {
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        orderInfo.setOrderStatus(SysParamDetailConstant.ORDER_STATUS_TREAT_REFUSE);
        orderInfo.setRefuseReason(refuseReason);
        orderInfo.setUpdateTime(DateUtil.getTimestamp());
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }


    public void accept(Integer orderId){
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        orderInfo.setOrderStatus(SysParamDetailConstant.ORDER_STATUS_TREAT_ACCEPT);
        orderInfo.setUpdateTime(DateUtil.getTimestamp());
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

}
