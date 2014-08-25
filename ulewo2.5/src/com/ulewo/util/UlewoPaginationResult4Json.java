package com.ulewo.util;

import java.util.List;

/**
 * 分页查询公共类
 * 
 * @author luo.hl
 * @date 2013-12-9 下午3:09:30
 * @version 3.0
 * @copyright www.ulewo.com
 */
public class UlewoPaginationResult4Json<T> {
    private int total;
    private List<T> rows;

    public int getTotal() {
	return total;
    }

    public void setTotal(int total) {
	this.total = total;
    }

    public List<T> getRows() {
	return rows;
    }

    public void setRows(List<T> rows) {
	this.rows = rows;
    }

}
