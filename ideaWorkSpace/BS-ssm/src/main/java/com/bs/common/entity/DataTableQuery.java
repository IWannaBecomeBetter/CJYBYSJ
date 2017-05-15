package com.bs.common.entity;

/**
 * jQuery.DataTables查询类
 * 包含通用查询属性
 * Created by joeshao on 16/9/8.
 */
public class DataTableQuery {

    /**
     * 搜索关键字
     */
    private String search;

    /**
     * DataTables用来生成的消息
     */
    private String sEcho;

    /**
     * 显示的起始索引
     */
    private Integer iDisplayStart;

    /**
     * 显示的行数
     */
    private Integer iDisplayLength;

    /**
     * 显示的列数
     */
    private Integer iColumns;


    public Integer getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(Integer iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public Integer getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(Integer iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public Integer getiColumns() {
        return iColumns;
    }

    public void setiColumns(Integer iColumns) {
        this.iColumns = iColumns;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
