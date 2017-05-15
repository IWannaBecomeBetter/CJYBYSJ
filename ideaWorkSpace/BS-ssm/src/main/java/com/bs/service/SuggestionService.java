package com.bs.service;

import com.bs.common.entity.DataTableResult;
import com.bs.common.entity.SuggestionDataTableQuery;
import com.bs.dao.entity.Suggestion;

/**
 * Created by Administrator on 2017/5/2.
 */
public interface SuggestionService {
    /**
     * 反馈列表
     * @param suggestionDataTableQuery
     * @return
     */
    DataTableResult suggestionList(SuggestionDataTableQuery suggestionDataTableQuery);

    /**
     * 通过主键获取对象详情
     * @param id
     * @return
     */
    Suggestion getFeedBackDetail(Integer id);

    /**
     * 反馈处理
     * @param suggestion
     */
    void audit(Suggestion suggestion);
}
