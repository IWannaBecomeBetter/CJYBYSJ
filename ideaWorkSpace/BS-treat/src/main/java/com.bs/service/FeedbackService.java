package com.bs.service;

import com.bs.dao.entity.Suggestion;

/**
 * Created by Administrator on 2017/4/18.
 */
public interface FeedbackService {
    /**
     * 新增反馈信息
     * @param suggestion
     * @param accountId
     */
    void add(Suggestion suggestion, Integer accountId);
}
