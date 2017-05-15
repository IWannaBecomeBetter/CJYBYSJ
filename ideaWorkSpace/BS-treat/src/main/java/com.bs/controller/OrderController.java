package com.bs.controller;

import com.bs.cache.SessionCache;
import com.bs.common.constants.CommonConstant;
import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.entity.BaseController;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */
@Controller
@RequestMapping("order")
public class OrderController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TreatController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionCache sessionCache;

    @Autowired
    private FileService fileService;

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
    public ModelAndView orderList(Model model, OrderInfo orderInfo, HttpServletRequest request){
        Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
        SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
        Account account = accountService.getById(sessionDetail.getAccountId());
        List<OrderInfoExt> orderInfoExts = orderService.orderListByStatus(orderInfo,account);
        model.addAttribute("orderInfoExts",orderInfoExts);
        return new ModelAndView(String.format(ORDER_VM_ROOT,"orderList"));
    }


    /**
     * 订单详情页面
     * @param model
     * @param orderInfo
     * @return
     */
    @RequestMapping(value = "orderDetail",method = RequestMethod.GET)
    public ModelAndView orderDetail(Model model,OrderInfo orderInfo){
        orderInfo = orderService.getOrderInfoById(orderInfo.getId());
        OrderBooking orderBooking = orderService.getOrderBookingByOrderId(orderInfo.getId());
        Account treat = accountService.getById(orderInfo.getTreatAccountId());
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
        model.addAttribute("treat",treat);
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
     * 康复师接单或者拒单
     * @param orderId
     * @param type
     * @param refuseReason
     * @return
     */
    @RequestMapping(value = "dealOrder",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean dealOrder(String orderId,String type,String refuseReason){
        try{
            if(type.equals("0")){//拒绝
                orderService.refuse(Integer.parseInt(orderId),refuseReason);
            }else{
                orderService.accept(Integer.parseInt(orderId));
            }
            return new ResultBean(true);
        }catch (Exception ex){
            return ajaxException(ex);
        }
    }
}
