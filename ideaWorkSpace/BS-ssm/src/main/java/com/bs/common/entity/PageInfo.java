package com.bs.common.entity;

import java.util.List;

/**
 * 分页参数
 * Created by fusj on 16/2/1.
 */
public class PageInfo {
    /**
     * 数据开始
     */
    private Integer pageStart;

    /**
     * 页显示数量
     */
    private Integer pageCount = Integer.valueOf(10);

    /**
     * 总数据量
     */
    private Integer totalCount;

    /**
     * 显示页数
     */
    private Integer pageNumber = Integer.valueOf(1);

    /**
     * 总页数
     */
    private Integer pageSize;

    /**
     * 查询数据
     */
    List list;

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
        this.pageNumber = pageStart;
    }

    public Integer getPageStart() {
        return this.pageStart != null && this.pageStart.intValue() > 1?Integer.valueOf((this.pageStart.intValue() - 1) * this.pageCount.intValue()):Integer.valueOf(0);
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = this.pageStart;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageCount() {
        return this.pageCount == null?Integer.valueOf(10):this.pageCount;
    }

    public void setPageSize(Integer pageSize) {
        if(pageSize.intValue() < 1) {
            pageSize = Integer.valueOf(1);
        }

        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        this.pageSize = Integer.valueOf(this.totalCount.intValue() / this.pageCount.intValue());
        if(this.totalCount.intValue() % this.pageCount.intValue() > 0) {
            this.pageSize = Integer.valueOf(this.pageSize.intValue() + 1);
        }

        return this.pageSize;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
