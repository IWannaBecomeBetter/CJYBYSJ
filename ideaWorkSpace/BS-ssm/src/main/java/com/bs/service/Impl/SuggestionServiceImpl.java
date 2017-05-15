package com.bs.service.Impl;

import com.bs.common.constants.SysParamDetailConstant;
import com.bs.common.entity.DataTableResult;
import com.bs.common.entity.SuggestionDataTableQuery;
import com.bs.common.util.DateUtil;
import com.bs.common.util.StringUtil;
import com.bs.dao.entity.Suggestion;
import com.bs.dao.entity.SuggestionExample;
import com.bs.dao.mapping.SuggestionMapper;
import com.bs.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */
@Service
@Transactional
public class SuggestionServiceImpl implements SuggestionService{

    @Autowired
    private SuggestionMapper suggestionMapper;

    @Override
    public DataTableResult suggestionList(SuggestionDataTableQuery suggestionDataTableQuery) {
        SuggestionExample suggestionExample = new SuggestionExample();
        SuggestionExample.Criteria criteria = suggestionExample.createCriteria();
        if(StringUtil.isNotEmptyObject(suggestionDataTableQuery.getStatus())){
            criteria.andStatusEqualTo(suggestionDataTableQuery.getStatus());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtil.isNotEmptyObject(suggestionDataTableQuery.getStartTime())){
            criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.getTimestamp(suggestionDataTableQuery.getStartTime(),dateFormat));
        }
        if(StringUtil.isNotEmptyObject(suggestionDataTableQuery.getEndTime())){
            criteria.andCreateTimeLessThanOrEqualTo(DateUtil.getTimestamp(suggestionDataTableQuery.getEndTime(),dateFormat));
        }
        if (null != suggestionDataTableQuery.getiDisplayStart() && suggestionDataTableQuery.getiDisplayLength() != null) {
            suggestionExample.setLimitClauseCount(suggestionDataTableQuery.getiDisplayLength());
            suggestionExample.setLimitClauseStart(suggestionDataTableQuery.getiDisplayStart());
        }
        suggestionExample.setOrderByClause(" CREATE_TIME desc ");
        int total = suggestionMapper.countByExample(suggestionExample);
        List<Suggestion> suggestions = suggestionMapper.selectByExample(suggestionExample);
        DataTableResult dataTableResult = new DataTableResult();
        dataTableResult.setAaData(suggestions);
        dataTableResult.setiTotalDisplayRecords(total);
        dataTableResult.setiTotalRecords(total);
        return dataTableResult;
    }

    @Override
    public Suggestion getFeedBackDetail(Integer id) {
        Suggestion suggestion = suggestionMapper.selectByPrimaryKey(id);
        return suggestion;
    }

    @Override
    public void audit(Suggestion suggestion) {
        suggestion.setUpdateTime(DateUtil.getTimestamp());
        suggestion.setStatus(SysParamDetailConstant.FEEDBACK_STATUS_CONFIRM);
        suggestionMapper.updateByPrimaryKeySelective(suggestion);
    }
}
