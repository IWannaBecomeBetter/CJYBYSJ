package com.bs.common.entity;

import java.util.List;

/**
 * jQuery.DataTables返回对象
 * Created by joeshao on 16/9/9.
 */
public class DataTableResult {

    /**
     * 实际的行数
     */
    private Integer iTotalRecords;

    /**
     * 过滤之后实际的行数
     * 目前由于自定义查询,iTotalDisplayRecords=iTotalRecords,即总条数
     */
    private Integer iTotalDisplayRecords;

    /**
     * 来自客户端的sEcho的没有变化的复制品
     */
    private String sEcho;

    /**
     * 数组的数据,表格中的实际数据
     */
    private List aaData;

    public Integer getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(Integer iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public Integer getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public List getAaData() {
        return aaData;
    }

    public void setAaData(List aaData) {
        this.aaData = aaData;
    }
}
