package com.bs.controller;

import com.bs.common.entity.BaseController;
import com.bs.common.entity.ResultBean;
import com.bs.dao.entity.Recover;
import com.bs.service.RecoverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by Administrator on 2017/5/8.
 */
@Controller
@RequestMapping("recover")
public class RecoverController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(RecoverController.class);

    @Autowired
    private RecoverService recoverService;

    @RequestMapping(value = "addRecover",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean addRecover(Recover recover){
        recoverService.addRecover(recover);
        return new ResultBean(true);
    }
}
