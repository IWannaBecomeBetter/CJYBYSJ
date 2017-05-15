package com.bs.controller;

import com.bs.common.constants.CommonConstant;
import com.bs.common.entity.*;
import com.bs.dao.entity.OrderBooking;
import com.bs.dao.entity.OrderInfo;
import com.bs.dao.entity.Treat;
import com.bs.service.AccountService;
import com.bs.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/27.
 */
@Controller
@RequestMapping(value = "order")
public class OrderController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    // 订单vm
    private static final String O_VM_ROOT_PATH = String.format(MANAGER_VM_ROOT, "order/orderinfo/%s");

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountService accountService;

    /**
     * 订单列表首页
     * @param model
     * @return
     */
    @RequestMapping(value = "index",method = RequestMethod.GET)
    public ModelAndView index(Model model){
        return new ModelAndView(String.format(O_VM_ROOT_PATH,"orderList"));
    }



    /**
     * 拒单列表首页
     * @param model
     * @return
     */
    @RequestMapping(value = "refuse",method = RequestMethod.GET)
    public ModelAndView refuse(Model model){
        return new ModelAndView(String.format(O_VM_ROOT_PATH,"refuseList"));
    }



    /**
     * 获取订单列表
     * @param orderDataTableQuery
     * @return
     */
    @RequestMapping(value = "getOrderList",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean orderList(OrderDataTableQuery orderDataTableQuery){
        ResultBean resultBean = new ResultBean(true);
        DataTableResult dataTableResult = orderService.orderList(orderDataTableQuery);
        resultBean.setData(dataTableResult);
        return resultBean;
    }



    /**
     * 获取拒单列表
     * @param orderDataTableQuery
     * @return
     */
    @RequestMapping(value = "refuseList",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean refuseList(OrderDataTableQuery orderDataTableQuery){
        ResultBean resultBean = new ResultBean(true);
        DataTableResult dataTableResult = orderService.refuseList(orderDataTableQuery);
        resultBean.setData(dataTableResult);
        return resultBean;
    }




    /**
     * 获取订单详情
     * @param id
     * @return
     */
    @RequestMapping(value = "getOrderBaseInfo",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean getOrderBaseInfo(String id){
        try{
            ResultBean resultBean = new ResultBean(true);
            OrderInfo orderInfo = orderService.getOrderInfoById(id);
            OrderBooking orderBooking = orderService.getOrderBookingByOrderId(id);
            Map<String, Object> map = new HashMap<>();
            map.put("orderInfo",orderInfo);
            map.put("orderBooking",orderBooking);
            resultBean.setData(map);
            return resultBean;
        }catch (Exception ex){
            return ajaxException(ex);
        }

    }

    /**
     * 订单审核
     * @param auditType
     * @param auditDesc
     * @param orderId
     * @return
     */
    @RequestMapping(value = "orderAudit",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean orderAudit(String auditType,String auditDesc,String orderId){
        try{
            orderService.orderAudit(auditType,auditDesc,orderId);
            return new ResultBean(true);
        }catch (Exception ex){
            return ajaxException(ex);
        }

    }


    /**
     * 获取康复师列表
     */
    @RequestMapping(value = "treatList",method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView treatList(Model model, PageInfo pageInfo, Treat treat){
        pageInfo = accountService.allocateTreatList(treat,pageInfo);
        model.addAttribute(CommonConstant.PAGE_INFO,pageInfo);
        return new ModelAndView(String.format(O_VM_ROOT_PATH,"treatList"));
    }


    /**
     * 分派康复师
     * @param orderId
     * @param accountId
     * @return
     */
    @RequestMapping(value = "treatAllocate",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean treatAllocate(String orderId,String accountId){
        orderService.treatAllocate(orderId,accountId);
        return new ResultBean(true);

    }}