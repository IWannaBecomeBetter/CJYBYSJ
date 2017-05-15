package com.bs.controller;

import com.bs.cache.SessionCache;
import com.bs.common.annotation.Check;
import com.bs.common.constants.CommonConstant;
import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.entity.BaseController;
import com.bs.common.entity.ResultBean;
import com.bs.common.entity.SessionDetail;
import com.bs.common.util.CookieUtil;
import com.bs.common.util.JackonUtil;
import com.bs.dao.entity.Account;
import com.bs.service.AccountService;
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
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2017/3/5.
 */
@Controller
@RequestMapping("")
public class MainController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private SessionCache sessionCache;

    @Autowired
    private FileService fileService;

    @Autowired
    private AccountService accountService;


    /**
     * 主页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "manager_index", method = RequestMethod.GET)
    public ModelAndView index(Model model,HttpServletRequest request) {
        //个人信息
        Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
        SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());
        model.addAttribute("myself",sessionDetail);
        return new ModelAndView(String.format(MANAGER_VM_ROOT, "manager_index"));
    }



    /**
     * 获取图片文件url---单图
     *
     * @param model
     * @param fileId
     * @return
     */
    @RequestMapping(value = "getFileUrl", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean getFileUrl(Model model, String fileId) {

        try {

            String filePath = fileService.getFileUrl(fileId);

            Map<String, Object> map = new HashMap<>();
            map.put("fileUrl", filePath);

            ResultBean resultBean = new ResultBean(true);
            resultBean.setData(map);

            return resultBean;
        } catch ( Exception ex) {
            return ajaxException(ex);
        }
    }

    /**
     * 获取图片文件urls---多图
     *
     * @param model
     * @param fileIds
     * @return
     */
    @RequestMapping(value = "getFileUrls", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean getFileUrls(Model model, String fileIds) {

        try {

            List<String> fileList = JackonUtil.readJson2Entity(fileIds, ArrayList.class);

            List<String> filePathList = new ArrayList<>();

            for (String fileId : fileList) {
                String filePath = fileService.getFileUrl(fileId);
                filePathList.add(filePath);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("fileUrls", filePathList);

            ResultBean resultBean = new ResultBean(true);
            resultBean.setData(map);

            return resultBean;
        } catch ( Exception ex) {
            return ajaxException(ex);
        }
    }
}
