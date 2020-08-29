package com.ygb.util;

import org.springframework.stereotype.Component;

import java.util.List;

//分页
@Component
public class PageObject {
    private Integer cur_page; //总条数
    public  Integer pageRow =8;  //默认显示500条
    private Integer totalRows; //总页数
    private Integer startRow = 1;  //起始页
    private List datas;  //保存数据

    public PageObject() {
    }

    public Integer getCur_page() {
        return cur_page;
    }

    public void setCur_page(Integer cur_page) {
        this.cur_page = cur_page;
    }

    public Integer getPageRow() {
        return pageRow;
    }

    public void setPageRow(Integer pageRow) {
        this.pageRow = pageRow;
    }

    public Integer getTotalRows() {
        this.totalRows = totalRows % pageRow == 0 ? totalRows / pageRow : totalRows / pageRow + 1;
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getStartRow() {

        return startRow;
    }

    public void setStartRow(Integer startRow) {
         this.startRow  = (startRow - 1) * pageRow;

    }

    public List getDatas() {

        return datas;
    }

    public void setDatas(List datas) {
        this.datas = datas;
    }
}
