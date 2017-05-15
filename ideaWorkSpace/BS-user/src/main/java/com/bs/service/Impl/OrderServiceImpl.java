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
        criteria.andAccountIdEqualTo(account.getId());
        if(orderInfo.getOrderStatus().equals(SysParamDetailConstant.ORDER_STATUS_OVER)){
            criteria.andOrderStatusEqualTo(orderInfo.getOrderStatus());
        }else{
            criteria.andOrderStatusNotEqualTo(SysParamDetailConstant.ORDER_STATUS_OVER);
        }
        orderInfoExtExample.setOrderByClause(" a.CREATE_TIME desc ");
        List<OrderInfoExt> orderInfoExts = orderInfoExtMapper.qryOrderListByAccountIdAndStatus(orderInfoExtExample);
        return orderInfoExts;
    }

    public OrderInfo orderBooking(OrderInfo orderInfo, OrderBooking orderBooking, Account account) {
        orderInfo.setOrderCode(genOrderCode(orderInfo.getTypeId()));
        orderInfo.setAccountId(account.getId());
        orderInfo.setOrderStatus(SysParamDetailConstant.ORDER_STATUS_NEW);
        orderInfo.setCreateTime(DateUtil.getTimestamp());
        orderInfo.setUpdateTime(DateUtil.getTimestamp());
        orderInfoMapper.insertSelective(orderInfo);
        orderBooking.setOrderId(orderInfo.getId());
        List<String> fileIdList = new ArrayList();
        if (orderBooking.getFileids() != null && orderBooking.getFileids().length()>0) {
            fileIdList = new ArrayList(Arrays.asList(orderBooking.getFileids().split(",")));
        }
        // 把文件转换成JSON格式
        try {
            orderBooking.setFileids(JackonUtil.writeEntity2JSON(fileIdList));
        } catch (Exception ex) {
            throw new ApplicationException("系统异常", ex);
        }
        orderBookingMapper.insertSelective(orderBooking);
        return orderInfo;
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

    public void orderFinish(OrderInfo orderInfo) {
        orderInfo = orderInfoMapper.selectByPrimaryKey(orderInfo.getId());
        orderInfo.setUpdateTime(DateUtil.getTimestamp());
        orderInfo.setOrderStatus(SysParamDetailConstant.ORDER_STATUS_OVER);
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

    public OrderInfo getOrderByCode(String orderCode) {
        OrderInfoExample orderInfoExample =new OrderInfoExample();
        orderInfoExample.createCriteria().andOrderCodeEqualTo(orderCode);
        List<OrderInfo> orderInfos = orderInfoMapper.selectByExample(orderInfoExample);
        return orderInfos.get(0);
    }

    private String genOrderCode(String orderType) {
        StringBuffer code = new StringBuffer();

        code.append(orderType);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        code.append(formatter.format(new Date()));

        /*
		 * 为了防止在一毫秒内有并发，还需要带上两位序列号
		 */
        double idx = ad.getAndAdd(1);

        idx = idx % 99;
        DecimalFormat df = new DecimalFormat("00");
        code.append(df.format(idx));

        return code.toString();
    }
}
