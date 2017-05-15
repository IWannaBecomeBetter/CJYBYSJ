package com.bs.controller;

import com.bs.common.entity.*;
import com.bs.dao.entity.Suggestion;
import com.bs.service.SuggestionService;
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
 * Created by Administrator on 2017/4/27.
 */
@Controller
@RequestMapping(value = "feedback")
public class FeedBackController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(FeedBackController.class);

    // 反馈意见vm
    private static final String F_VM_ROOT_PATH = String.format(MANAGER_VM_ROOT, "feedback/suggestion/%s");


    @Autowired
    private SuggestionService suggestionService;

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView(String.format(F_VM_ROOT_PATH,"suggestionList"));
    }


    /**
     * 意见及建议列表
     * @param model
     * @param suggestionDataTableQuery
     * @return
     */
    @RequestMapping(value = "suggestionList",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean suggestionList(Model model, SuggestionDataTableQuery suggestionDataTableQuery){
        ResultBean resultBean = new ResultBean(true);
        DataTableResult dataTableResult = suggestionService.suggestionList(suggestionDataTableQuery);
        resultBean.setData(dataTableResult);
        return resultBean;
    }


    /**
     * 获取反馈详情
     * @param suggestion
     * @return
     */
    @RequestMapping(value = "getFeedbackDetail",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ResultBean getFeedbackDetail(Suggestion suggestion){
        ResultBean resultBean = new ResultBean(true);
        suggestion = suggestionService.getFeedBackDetail(suggestion.getId());
        resultBean.setData(suggestion);
        return resultBean;
    }


    /**
     * 意见反馈处理
     * @param suggestion
     * @return
     */
    @RequestMapping(value = "audit",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean audit(Suggestion suggestion){
        suggestionService.audit(suggestion);
        return new ResultBean(true);
    }
}
