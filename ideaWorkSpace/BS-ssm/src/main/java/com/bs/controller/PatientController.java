package com.bs.controller;

import com.bs.cache.SessionCache;
import com.bs.common.constants.CommonConstant;
import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.entity.*;
import com.bs.dao.entity.Account;
import com.bs.dao.entity.PatientExt;
import com.bs.service.AccountService;
import com.bs.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by Administrator on 2017/4/19.
 */
@Controller
@RequestMapping(value = "patient")
public class PatientController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SessionCache sessionCache;

    // 患者vm
    private static final String P_VM_ROOT_PATH = String.format(MANAGER_VM_ROOT, "user/patient/%s");

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public ModelAndView patientIndex(Model model){
        return new ModelAndView(String.format(P_VM_ROOT_PATH,"patientList"));
    }

    @RequestMapping(value = "list",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean patientList(AccountDataTableQuery accountDataTableQuery){
        try{
            ResultBean resultBean = new ResultBean(true);
            accountDataTableQuery.setType(SysParamDetailConstant.ACCOUNT_TYPE_PATIENT);
            DataTableResult result = accountService.accountList(accountDataTableQuery);
            resultBean.setData(result);
            return resultBean;
        }catch (Exception ex){
            return ajaxException(ex);
        }
    }

    /**
     * 根据账户编号查询患者信息
     * @param id
     * @return
     */
    @RequestMapping(value = "getPatientByAccountId",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean getPatientByAccountId(String id){
        try{
            ResultBean resultBean = new ResultBean(true);
            PatientExt patientExt = patientService.getPatientByAccountId(id);
            resultBean.setData(patientExt);
            return resultBean;
        }catch (Exception ex){
            return ajaxException(ex);
        }
    }



    /**
     * 患者审核
     *
     * @param account
     * @param auditType 审核类型 1:通过  0:不通过
     * @return
     */
    @RequestMapping(value = "audit", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean patientAudit(Account account, String auditType ) {
        try {

            patientService.audit(account, auditType);

            ResultBean resultBean = new ResultBean(true);

            return resultBean;
        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }
}
