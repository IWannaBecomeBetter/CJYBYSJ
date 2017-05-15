package com.bs.common.entity;

/**
 * 分页参数
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
}
