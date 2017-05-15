package com.bs.controller;

import com.bs.cache.SessionCache;
import com.bs.common.constants.CommonConstant;
import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.entity.BaseController;
import com.bs.common.entity.PageInfo;
import com.bs.common.entity.ResultBean;
import com.bs.common.entity.SessionDetail;
import com.bs.common.util.CookieUtil;
import com.bs.common.util.JackonUtil;
import com.bs.common.util.StringUtil;
import com.bs.dao.entity.*;
import com.bs.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */
@Controller
@RequestMapping("order")
public class OrderController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionCache sessionCache;

    @Autowired
    private FileService fileService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private RecoverService recoverService;


    /**
     * 根据订单类型进入不同详情页面
     * @param type
     * @return
     */
    @RequestMapping(value = "orderType",method = RequestMethod.GET)
    public ModelAndView orderType(Model model,String type,HttpServletRequest request){
        Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
        SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
        Account account = accountService.getById(sessionDetail.getAccountId());
        model.addAttribute("account",account);
        if(type.equals(SysParamDetailConstant.ORDER_TYPE_NAOTAN)){
            return new ModelAndView(String.format(ORDER_VM_ROOT, "orderType1"));
        }else{
            return new ModelAndView(String.format(ORDER_VM_ROOT, "orderType2"));
        }

    }


    /**
     * 下单页面
     * @param type
     * @return
     */
    @RequestMapping(value = "toBooking",method = RequestMethod.GET)
    public ModelAndView toBooking(Model model,String type,HttpServletRequest request){

        Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
        SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
        Account account = accountService.getById(sessionDetail.getAccountId());
        Patient patient = patientService.getByAccountId(account.getId());
        String domain = fileService.getDomain();
        model.addAttribute("account",account);
        model.addAttribute("patient",patient);
        model.addAttribute("domain",domain);
        model.addAttribute("type",type);
        return new ModelAndView(String.format(ORDER_VM_ROOT,"orderBooking"));
    }


    /**
     * 订单预定
     * @param orderInfo
     * @param orderBooking
     * @param request
     * @return
     */
    @RequestMapping(value = "orderBooking",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean orderBooking(OrderInfo orderInfo, OrderBooking orderBooking,HttpServletRequest request){
        try{
            Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
            SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
            Account account = accountService.getById(sessionDetail.getAccountId());
            orderInfo = orderService.orderBooking(orderInfo,orderBooking,account);
            ResultBean resultBean = new ResultBean(true);
            resultBean.setData(orderInfo);
            return resultBean;
        }catch (Exception ex){
            return ajaxException(ex);
        }

    }


    /**
     * 预约成功页面
     * @param model
     * @return
     */
    @RequestMapping(value = "bookingSuccess",method = RequestMethod.GET)
    public ModelAndView bookingSuccess(Model model,String orderCode){
        OrderInfo orderInfo = orderService.getOrderByCode(orderCode);
        model.addAttribute("orderCode",orderCode);
        model.addAttribute("orderInfo",orderInfo);
        return new ModelAndView(String.format(ORDER_VM_ROOT,"bookingSuccess"));
    }



    /**
     * 我的订单
     * @return
     */
    @RequestMapping(value = "myOrder",method = RequestMethod.GET)
    public String myOrder(){
        return String.format(ORDER_VM_ROOT, "myOrder");
    }


    /**
     * 订单列表
     * @param orderInfo
     * @return
     */
    @RequestMapping(value = "orderList",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView orderList(Model model,OrderInfo orderInfo, HttpServletRequest request){
        Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
        SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
        Account account = accountService.getById(sessionDetail.getAccountId());
        List<OrderInfoExt> orderInfoExts = orderService.orderListByStatus(orderInfo,account);
        model.addAttribute("orderInfoExts",orderInfoExts);
        return new ModelAndView(String.format(ORDER_VM_ROOT,"orderList"));
    }


    @RequestMapping(value = "orderDetail",method = RequestMethod.GET)
    public ModelAndView orderDetail(Model model,OrderInfo orderInfo){
        orderInfo = orderService.getOrderInfoById(orderInfo.getId());
        OrderBooking orderBooking = orderService.getOrderBookingByOrderId(orderInfo.getId());
        if(StringUtil.isNotEmptyObject(orderInfo.getTreatAccountId())){
            Account treat = accountService.getById(orderInfo.getTreatAccountId());
            model.addAttribute("treat",treat);
        }

        Account patient = accountService.getById(orderInfo.getAccountId());
        if(StringUtil.isNotEmptyObject(patient.getHead())){
            String headFile = null;
            try {
                headFile = fileService.getFileUrl(patient.getHead());
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("headFile",headFile);
        }
        List<Recover> recovers = recoverService.getByOrderId(orderInfo.getId());
        model.addAttribute("recovers",recovers);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("orderBooking",orderBooking);
        model.addAttribute("patient",patient);
        // 诊断证明
        if(StringUtil.isNotEmptyObject(orderBooking.getFileids())) {
            List<String> filePathList = new ArrayList();

            List<String> fileIdList = JackonUtil.readJson2Entity(orderBooking.getFileids(), ArrayList.class);

            for(String fileId : fileIdList) {
                try {
                    filePathList.add(fileService.getFileUrl(fileId));
                } catch (Exception ex) {
                }
            }

            model.addAttribute("filePathList", filePathList);
        }
        return new ModelAndView(String.format(ORDER_VM_ROOT,"orderDetail"));
    }


    /**
     * 结束订单
     * @param orderInfo
     * @return
     */
    @RequestMapping(value = "finish",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean orderFinish(OrderInfo orderInfo){
        orderService.orderFinish(orderInfo);
        return new ResultBean(true);
    }}
