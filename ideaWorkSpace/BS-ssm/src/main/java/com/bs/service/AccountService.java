package com.bs.service;

import com.bs.common.entity.AccountDataTableQuery;
import com.bs.common.entity.DataTableResult;
import com.bs.common.entity.PageInfo;
import com.bs.dao.entity.Account;
import com.bs.dao.entity.Treat;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */
public interface AccountService {
    /**
     * 管理员登录
     * @param account
     * @return
     */
    Account login(Account account);

    /**
     * 根据参数获取账户列表
     * @param accountDataTableQuery
     * @return
     */
    DataTableResult accountList(AccountDataTableQuery accountDataTableQuery);

    /**
     * 分派康复师获取列表
     * @param treat
     * @param pageInfo
     * @return
     */
    PageInfo allocateTreatList(Treat treat,PageInfo pageInfo);

    /**
     * 根据主键获取对象
     * @param id
     * @return
     */
    Account getById(Integer id);
}
