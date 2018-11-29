package com.hsh.common.utils;

import lombok.Data;

import java.util.List;

/**
 * @author hushihai
 * @version V1.0, 2018/11/12
 */
@Data
public class Pagination<T> {
    private int totalSize;
    private List<T> list;//数据列表
    private int pageIndex;
    private int pageSize;

    public Pagination(){}

    public Pagination(int totalSize, List<T> list, int pageIndex, int pageSize) {
        this.totalSize = totalSize;
        this.list = list;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    //get、set方法
}