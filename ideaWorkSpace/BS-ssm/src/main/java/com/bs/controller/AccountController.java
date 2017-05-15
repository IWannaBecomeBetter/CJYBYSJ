package com.bs.controller;

import com.bs.cache.SessionCache;
import com.bs.common.annotation.Check;
import com.bs.common.constants.CommonConstant;
import com.bs.common.entity.BaseController;
import com.bs.common.entity.ResultBean;
import com.bs.common.entity.SessionDetail;
import com.bs.common.util.CookieUtil;
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
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/31.
 */
@Controller
@RequestMapping(value = {"account",""})
public class AccountController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private SessionCache sessionCache;
    @Autowired
    private AccountService accountService;

    /**
     * 登陆页面
     * @return
     */
    @RequestMapping(value = {"", "/", "login"}, method = RequestMethod.GET)
    @Check(loginCheck = false)
    public String login() {
        return String.format(MANAGER_VM_ROOT, "login");
    }

    /**
     * 账户登录
     * @param account
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    @Check(loginCheck = false)
    public ResultBean login(Account account, HttpServletRequest request, HttpServletResponse response) {
        try{
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

                // 把redis的key存入cookie，有效期1天
                Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
                String value;
                if(cookie != null) {
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
        }catch (Exception ex){
            return ajaxException(ex);
        }

    }




    /**
     * 退出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    @Check(loginCheck = false)
    public ResultBean logout(HttpServletRequest request, HttpServletResponse response) {
        try {

            Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
            if(null != cookie) {
                String uid = cookie.getValue();
                cookie.setMaxAge(0);

                sessionCache.evict(uid);
            }

            ResultBean resultBean = new ResultBean(true);

            return resultBean;
        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }
}
