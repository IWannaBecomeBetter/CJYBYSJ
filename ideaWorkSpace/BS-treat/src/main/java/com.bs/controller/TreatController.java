package com.bs.controller;

import com.bs.cache.SessionCache;
import com.bs.common.constants.CommonConstant;
import com.bs.common.entity.BaseController;
import com.bs.common.entity.ResultBean;
import com.bs.common.entity.SessionDetail;
import com.bs.common.util.CookieUtil;
import com.bs.common.util.StringUtil;
import com.bs.dao.entity.Account;
import com.bs.dao.entity.Patient;
import com.bs.dao.entity.Treat;
import com.bs.service.AccountService;
import com.bs.service.FileService;
import com.bs.service.PatientService;
import com.bs.service.TreatService;
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

/**
 * Created by Administrator on 2017/4/6.
 */
@Controller
@RequestMapping("treat")
public class TreatController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TreatController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private TreatService treatService;
    @Autowired
    private FileService fileService;
    @Autowired
    private SessionCache sessionCache;

    /**
     * 用户实名认证界面
     * @return
     */
    @RequestMapping(value = "identification",method = RequestMethod.GET)
    public ModelAndView identification(Model model){
        String domain = fileService.getDomain();
        model.addAttribute("domain",domain);
        return new ModelAndView(String.format(TREAT_VM_ROOT,"identification"));
    }


    @RequestMapping(value = "identification",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean identification(Treat treat, HttpServletRequest request){
        try{
            Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
            SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
            Account account = accountService.getById(sessionDetail.getAccountId());
            treatService.identification(treat,account);
            return new ResultBean(true);
        }catch (Exception ex){
            return ajaxException(ex);
        }
    }


    /**
     * 康复师完善实名信息
     * @return
     */
    @RequestMapping(value = "personalInfo",method = RequestMethod.GET)
    public ModelAndView personalInfo(Model model){
        String domain = fileService.getDomain();
        model.addAttribute("domain",domain);
        return new ModelAndView(String.format(TREAT_VM_ROOT,"personalInfo"));
    }


    @RequestMapping(value = "personalInfo",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean personalInfo(Treat treat, HttpServletRequest request){
        try{
            Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
            SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
            Account account = accountService.getById(sessionDetail.getAccountId());
            treatService.personalInfo(treat,account);
            return new ResultBean(true);
        }catch (Exception ex){
            return ajaxException(ex);
        }
    }


    /**
     * 患者首页
     * @return
     */
    @RequestMapping(value = "test",method = RequestMethod.GET)
    public ModelAndView test(Model model){
        String domain = fileService.getDomain();
        model.addAttribute("domain",domain);
        return new ModelAndView(String.format(TREAT_VM_ROOT, "test"));
    }



    /**
     * 患者首页
     * @return
     */
    @RequestMapping(value = "home",method = RequestMethod.GET)
    public String home(){
        return String.format(TREAT_VM_ROOT, "home");
    }



    /**
     * 服务页面
     * @return
     */
    @RequestMapping(value = "service",method = RequestMethod.GET)
    public String service(){
        return String.format(TREAT_VM_ROOT, "service");
    }



    /**
     * 服务页面
     * @return
     */
    @RequestMapping(value = "mine",method = RequestMethod.GET)
    public ModelAndView mine(Model model, HttpServletRequest request) throws Exception {
        String domain = fileService.getDomain();
        Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
        SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
        Account account = accountService.getById(sessionDetail.getAccountId());
        if(StringUtil.isNotEmptyObject(account.getHead())){
            String headFile = fileService.getFileUrl(account.getHead());
            model.addAttribute("headFile",headFile);
        }
        model.addAttribute("domain",domain);
        model.addAttribute("account",account);
        return new ModelAndView(String.format(TREAT_VM_ROOT, "mine"));
    }

}
