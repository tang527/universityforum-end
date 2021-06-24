package com.zp.universityForum.bean.common;

import lombok.Data;

import java.util.List;

/**
 * @author zp
 * @date 2021-05-05 21:28
 */
@Data
public class ListEntity<T> {
    private List<T> list;

    private int total;
    private int pageNum;
    private int pageSize;
    private int pages;

    public ListEntity() {
    }
    public ListEntity(int total) {
        this.total = total;
    }

    public ListEntity(List<T> list) {
        this.list = list;
    }

    public ListEntity(int total, List<T> list) {
        this.total = total;
        this.list = list;
    }
}
