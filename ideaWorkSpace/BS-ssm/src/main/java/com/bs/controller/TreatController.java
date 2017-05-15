package com.bs.controller;

import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.entity.AccountDataTableQuery;
import com.bs.common.entity.BaseController;
import com.bs.common.entity.DataTableResult;
import com.bs.common.entity.ResultBean;
import com.bs.dao.entity.Account;
import com.bs.dao.entity.Treat;
import com.bs.service.AccountService;
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

import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/21.
 */
@Controller
@RequestMapping(value = "treat")
public class TreatController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(TreatController.class);
    // 康复师vm
    private static final String T_VM_ROOT_PATH = String.format(MANAGER_VM_ROOT, "user/treat/%s");

    @Autowired
    private AccountService accountService;
    @Autowired
    private TreatService treatService;


    @RequestMapping(value = "index",method = RequestMethod.GET)
    public ModelAndView treatIndex(Model model){
        return new ModelAndView(String.format(T_VM_ROOT_PATH,"treatList"));
    }

    @RequestMapping(value = "list",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean treatList(AccountDataTableQuery accountDataTableQuery){
        try{
            ResultBean resultBean = new ResultBean(true);
            accountDataTableQuery.setType(SysParamDetailConstant.ACCOUNT_TYPE_TREAT);
            DataTableResult result = accountService.accountList(accountDataTableQuery);
            resultBean.setData(result);
            return resultBean;
        }catch (Exception ex){
            return ajaxException(ex);
        }
    }


    /**
     * 根据账户编号查询康复师信息
     * @param accountId
     * @return
     */
    @RequestMapping(value = "getTreatByAccountId",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean getPatientByAccountId(String accountId){
        try{
            ResultBean resultBean = new ResultBean(true);
            Treat treat = treatService.getTreatByAccountId(accountId);
            Account account = accountService.getById(Integer.parseInt(accountId));
            HashMap<String,Object> map = new HashMap<>();
            map.put("account",account);
            map.put("treat",treat);
            resultBean.setData(map);
            return resultBean;
        }catch (Exception ex){
            return ajaxException(ex);
        }
    }


    /**
     * 康复师审核
     *
     * @param accountId
     * @param auditType 审核类型 1:通过  0:不通过
     * @return
     */
    @RequestMapping(value = "audit", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean treatAudit(String accountId, String auditType ) {
        try {
            treatService.audit(accountId, auditType);
            ResultBean resultBean = new ResultBean(true);
            return resultBean;
        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }
}
