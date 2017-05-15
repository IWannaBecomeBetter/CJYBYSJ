package com.bs.controller;

import com.bs.cache.SessionCache;
import com.bs.common.constants.CommonConstant;
import com.bs.common.entity.BaseController;
import com.bs.common.entity.ResultBean;
import com.bs.common.entity.SessionDetail;
import com.bs.common.util.CookieUtil;
import com.bs.dao.entity.Suggestion;
import com.bs.service.FeedbackService;
import com.bs.service.FileService;
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
 * Created by Administrator on 2017/4/18.
 */
@Controller
@RequestMapping(value="feedback")
public class FeedBackController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(FeedBackController.class);

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private SessionCache sessionCache;
    @Autowired
    private FileService fileService;

    @RequestMapping(value="index")
    public ModelAndView index(Model model){
        String domain = fileService.getDomain();
        model.addAttribute("domain",domain);
        return new ModelAndView(String.format(PATIENT_VM_ROOT,"feedback"));
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean add(Suggestion suggestion, HttpServletRequest request){
        try{
            Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
            SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
            feedbackService.add(suggestion,sessionDetail.getAccountId());
            return new ResultBean(true);
        }catch (Exception ex){
            return ajaxException(ex);
        }
    }
}
