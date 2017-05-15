package com.bs.service.Impl;

import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.entity.DataTableResult;
import com.bs.common.entity.OrderDataTableQuery;
import com.bs.common.util.DateUtil;
import com.bs.common.util.StringUtil;
import com.bs.dao.entity.*;
import com.bs.dao.mapping.OrderBookingMapper;
import com.bs.dao.mapping.OrderInfoMapper;
import com.bs.service.OrderService;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/4/27.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderBookingMapper orderBookingMapper;

    @Override
    public DataTableResult orderList(OrderDataTableQuery orderDataTableQuery) {
        OrderInfoExample orderInfoExample = new OrderInfoExample();
        OrderInfoExample.Criteria criteria = orderInfoExample.createCriteria();
        if(StringUtil.isNotEmptyObject(orderDataTableQuery.getStatus())){
            criteria.andOrderStatusEqualTo(orderDataTableQuery.getStatus());
        }
        if(StringUtil.isNotEmptyObject(orderDataTableQuery.getSearch())){
            criteria.andOrderCodeLike("%"+orderDataTableQuery.getSearch()+"%");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtil.isNotEmptyObject(orderDataTableQuery.getStartTime())){
            criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.getTimestamp(orderDataTableQuery.getStartTime(),dateFormat));
        }
        if(StringUtil.isNotEmptyObject(orderDataTableQuery.getEndTime())){
            criteria.andCreateTimeLessThanOrEqualTo(DateUtil.getTimestamp(orderDataTableQuery.getEndTime(),dateFormat));
        }
        if (null != orderDataTableQuery.getiDisplayStart() && orderDataTableQuery.getiDisplayLength() != null) {
            orderInfoExample.setLimitClauseCount(orderDataTableQuery.getiDisplayLength());
            orderInfoExample.setLimitClauseStart(orderDataTableQuery.getiDisplayStart());
        }
        orderInfoExample.setOrderByClause(" CREATE_TIME desc ");
        int total = orderInfoMapper.countByExample(orderInfoExample);
        List<OrderInfo> orderInfos = orderInfoMapper.selectByExample(orderInfoExample);
        DataTableResult dataTableResult = new DataTableResult();
        dataTableResult.setAaData(orderInfos);
        dataTableResult.setiTotalDisplayRecords(total);
        dataTableResult.setiTotalRecords(total);
        return dataTableResult;
    }

    @Override
    public DataTableResult refuseList(OrderDataTableQuery orderDataTableQuery) {
        OrderInfoExample orderInfoExample = new OrderInfoExample();
        OrderInfoExample.Criteria criteria = orderInfoExample.createCriteria();
        criteria.andOrderStatusEqualTo(SysParamDetailConstant.ORDER_STATUS_TREAT_REFUSE);
        if(StringUtil.isNotEmptyObject(orderDataTableQuery.getSearch())){
            criteria.andOrderCodeLike("%"+orderDataTableQuery.getSearch()+"%");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtil.isNotEmptyObject(orderDataTableQuery.getStartTime())){
            criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.getTimestamp(orderDataTableQuery.getStartTime(),dateFormat));
        }
        if(StringUtil.isNotEmptyObject(orderDataTableQuery.getEndTime())){
            criteria.andCreateTimeLessThanOrEqualTo(DateUtil.getTimestamp(orderDataTableQuery.getEndTime(),dateFormat));
        }
        if (null != orderDataTableQuery.getiDisplayStart() && orderDataTableQuery.getiDisplayLength() != null) {
            orderInfoExample.setLimitClauseCount(orderDataTableQuery.getiDisplayLength());
            orderInfoExample.setLimitClauseStart(orderDataTableQuery.getiDisplayStart());
        }
        orderInfoExample.setOrderByClause(" CREATE_TIME desc ");
        int total = orderInfoMapper.countByExample(orderInfoExample);
        List<OrderInfo> orderInfos = orderInfoMapper.selectByExample(orderInfoExample);
        DataTableResult dataTableResult = new DataTableResult();
        dataTableResult.setAaData(orderInfos);
        dataTableResult.setiTotalDisplayRecords(total);
        dataTableResult.setiTotalRecords(total);
        return dataTableResult;
    }

    @Override
    public OrderInfo getOrderInfoById(String orderId) {
        OrderInfoExample orderInfoExample = new OrderInfoExample();
        orderInfoExample.createCriteria().andIdEqualTo(Integer.parseInt(orderId));
        List<OrderInfo> orderInfos = orderInfoMapper.selectByExample(orderInfoExample);
        if(CollectionUtils.isNotEmpty(orderInfos)){
            return orderInfos.get(0);
        }else{
            return new OrderInfo();
        }
    }

    @Override
    public OrderBooking getOrderBookingByOrderId(String orderId) {
        OrderBookingExample orderBookingExample = new OrderBookingExample();
        orderBookingExample.createCriteria().andOrderIdEqualTo(Integer.parseInt(orderId));
        List<OrderBooking> orderBookings = orderBookingMapper.selectByExample(orderBookingExample);
        if(CollectionUtils.isNotEmpty(orderBookings)){
            return orderBookings.get(0);
        }else{
            return new OrderBooking();
        }
    }

    @Override
    public void orderAudit(String auditType, String auditDesc, String orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(Integer.parseInt(orderId));
        if(auditType.equals(SysParamDetailConstant.AUDIT_TYPE_OK)){
            orderInfo.setOrderStatus(SysParamDetailConstant.ORDER_STATUS_CONFIRM);
        }else{
            orderInfo.setOrderStatus(SysParamDetailConstant.ORDER_STATUS_FAILED);
        }
        orderInfo.setAuditDesc(auditDesc);
        orderInfo.setUpdateTime(DateUtil.getTimestamp());
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

    @Override
    public void treatAllocate(String orderId, String accountId) {
        OrderInfoExample orderInfoExample = new OrderInfoExample();
        orderInfoExample.createCriteria().andIdEqualTo(Integer.parseInt(orderId));
        List<OrderInfo> orderInfos = orderInfoMapper.selectByExample(orderInfoExample);
        OrderInfo orderInfo = orderInfos.get(0);
        orderInfo.setTreatAccountId(Integer.parseInt(accountId));
        orderInfo.setUpdateTime(DateUtil.getTimestamp());
        orderInfo.setOrderStatus(SysParamDetailConstant.ORDER_STATUS_DISPATCH_TREAT);
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }
}
