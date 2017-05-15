package com.bs.controller;

import com.bs.cache.SessionCache;
import com.bs.common.constants.CommonConstant;
import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.entity.BaseController;
import com.bs.common.entity.ResultBean;
import com.bs.common.entity.SessionDetail;
import com.bs.common.util.CookieUtil;
import com.bs.common.util.StringUtil;
import com.bs.dao.entity.Account;
import com.bs.service.AccountService;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/31.
 */
@Controller
@RequestMapping(value = {"account", ""})
public class AccountController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionCache sessionCache;


    /**
     * 登陆页面
     *
     * @return
     */
    @RequestMapping(value = {"", "/", "login"}, method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
        if (StringUtil.isNotEmptyObject(cookie)) {
            SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
            if (sessionDetail.getAutoLogin().equals(SysParamDetailConstant.ACCOUNT_AUTOLOGIN_TRUE)) {
                response.sendRedirect("/patient/home");
//                return String.format(PATIENT_VM_ROOT, "home");
            } else {
                return String.format(PATIENT_VM_ROOT, "login");
            }
        }
        return String.format(PATIENT_VM_ROOT, "login");
    }


    /**
     * 用户登录
     *
     * @param account
     * @param autoLogin
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResultBean login(Account account, String autoLogin, HttpServletRequest request, HttpServletResponse
            response) {
        try {
            account = accountService.login(account);
            // session缓存失败，不影响用户注册
            try {
                // session到redis的对象
                SessionDetail sessionDetail = new SessionDetail();
                sessionDetail.setAccountId(account.getId());
                sessionDetail.setName(account.getName());
                sessionDetail.setTelephone(account.getTelephone());
                sessionDetail.setType(account.getType());
                sessionDetail.setStatus(account.getStatus());
                sessionDetail.setAutoLogin(autoLogin);

                // 把redis的key存入cookie，有效期1天
                Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
                String value;
                if (cookie != null) {
                    value = cookie.getValue();
                } else {
                    value = UUID.randomUUID().toString();
                }
                CookieUtil.addCookie(response, CommonConstant.COOKIE_VALUE, value, CommonConstant.SESSION_TIME_OUT_DAY);

                // 把用户登陆信息存入redis
                sessionCache.put(value, sessionDetail, CommonConstant.SESSION_TIME_OUT_DAY);

            } catch (Exception ex) {
                logger.error("缓存redis异常:" + ex.getMessage(), ex);
            }
            ResultBean resultBean = new ResultBean(true);
            resultBean.setData(account);
            return resultBean;
        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }

    /**
     * 注册页面
     *
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register() {
        return String.format(PATIENT_VM_ROOT, "register");
    }


    /**
     * 新用户注册
     * @param account
     * @return
     */
    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean register(Account account,HttpServletRequest request,HttpServletResponse response){
        try {
            account = accountService.register(account);
            // session缓存失败，不影响用户注册
            try {
                // session到redis的对象
                SessionDetail sessionDetail = new SessionDetail();
                sessionDetail.setAccountId(account.getId());
                sessionDetail.setName(account.getName());
                sessionDetail.setTelephone(account.getTelephone());
                sessionDetail.setType(account.getType());
                sessionDetail.setStatus(account.getStatus());
                sessionDetail.setAutoLogin(SysParamDetailConstant.ACCOUNT_AUTOLOGIN_FALSE);

                // 把redis的key存入cookie，有效期1天
                Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
                String value;
                if (cookie != null) {
                    value = cookie.getValue();
                } else {
                    value = UUID.randomUUID().toString();
                }
                CookieUtil.addCookie(response, CommonConstant.COOKIE_VALUE, value, CommonConstant.SESSION_TIME_OUT_DAY);

                // 把用户登陆信息存入redis
                sessionCache.put(value, sessionDetail, CommonConstant.SESSION_TIME_OUT_DAY);

            } catch (Exception ex) {
                logger.error("缓存redis异常:" + ex.getMessage(), ex);
            }
            ResultBean resultBean = new ResultBean(true);
            resultBean.setData(account);
            return resultBean;
        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }


    /**
     * 用户协议页面
     *
     * @return
     */
    @RequestMapping(value = "rule", method = RequestMethod.GET)
    public String rule() {
        return String.format(PATIENT_VM_ROOT, "rule");
    }


    /**
     * 设置头像
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "setHead", method = RequestMethod.POST)
    public ResultBean setHead(HttpServletRequest request,String head) {
        try{
            Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
            SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
            accountService.setHead(sessionDetail.getAccountId(),head);
            return new ResultBean(true);
        }catch (Exception ex){
            return ajaxException(ex);
        }
    }


    /**
     * 修改密码界面
     * @return
     */
    @RequestMapping(value = "updatePassword",method = RequestMethod.GET)
    public ModelAndView updatePassword(Model model,HttpServletRequest request){
        Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
        SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
        Account account = accountService.getById(sessionDetail.getAccountId());
        model.addAttribute("account",account);
        return new ModelAndView(String.format(PATIENT_VM_ROOT, "updatepassword"));
    }


    /**
     * 修改密码
     * @param request
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public ResultBean updatePassword(HttpServletRequest request,String password) {
        try{
            Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
            SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
            accountService.updatePassword(sessionDetail.getAccountId(),password);
            return new ResultBean(true);
        }catch (Exception ex){
            return ajaxException(ex);
        }
    }



    /**
     * 绑定邮箱界面
     * @return
     */
    @RequestMapping(value = "bindEmail",method = RequestMethod.GET)
    public ModelAndView bindEmail(Model model,HttpServletRequest request){
        Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
        SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
        Account account = accountService.getById(sessionDetail.getAccountId());
        model.addAttribute("account",account);
        return new ModelAndView(String.format(PATIENT_VM_ROOT, "bindemail"));
    }


    /**
     * 绑定或修改邮箱
     * @param request
     * @param email
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "bindEmail", method = RequestMethod.POST)
    public ResultBean bindEmail(HttpServletRequest request,String email) {
        try{
            Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
            SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
            accountService.bindEmail(sessionDetail.getAccountId(),email);
            return new ResultBean(true);
        }catch (Exception ex){
            return ajaxException(ex);
        }
    }


    /**
     * 用户退出
     * @param request
     * @return
     */
    @RequestMapping(value = "exit" ,method = RequestMethod.GET)
    public String exit(HttpServletRequest request){
        try{
            //删除redis中的用户信息和cookie
            Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
            sessionCache.evict(cookie.getValue());
            cookie.setMaxAge(0);
        }catch(Exception ex){
            logger.error("缓存redis异常:" + ex.getMessage(), ex);
        }
        return String.format(PATIENT_VM_ROOT, "login");
    }
}
