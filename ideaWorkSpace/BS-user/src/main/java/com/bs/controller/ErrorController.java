package com.bs.controller;


import com.bs.common.entity.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 错误页面
 */
@Controller
@RequestMapping("error")
public class ErrorController extends BaseController {

    protected static final String MANAGER_ERROR_VM_ROOT = "/error/%s";

    public static final String MANAGER_ERROR_400 = "/error/400";
    public static final String MANAGER_ERROR_403 = "/error/403";
    public static final String MANAGER_ERROR_404 = "/error/404";
    public static final String MANAGER_ERROR_500 = "/error/500";
    public static final String MANAGER_ERROR_503 = "/error/503";

    /**
     * 错误页面
     * @return
     */
    @RequestMapping(value = {"/400"}, method = RequestMethod.GET)
    public String error400() {

        return MANAGER_ERROR_400;
    }

    /**
     * 错误页面
     * @return
     */
    @RequestMapping(value = {"/403"}, method = RequestMethod.GET)
    public String error403() {

        return MANAGER_ERROR_403;
    }

    /**
     * 错误页面
     * @return
     */
    @RequestMapping(value = {"/404"}, method = RequestMethod.GET)
    public String error404() {

        return MANAGER_ERROR_404;
    }

    /**
     * 错误页面
     * @return
     */
    @RequestMapping(value = {"/500"}, method = RequestMethod.GET)
    public String error500() {

        return MANAGER_ERROR_500;
    }

    /**
     * 错误页面；
     * @return
     */
    @RequestMapping(value = {"/503"}, method = RequestMethod.GET)
    public String error503() {

        return MANAGER_ERROR_503;
    }
}
