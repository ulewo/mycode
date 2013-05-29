package com.ulewo.util;

import java.util.List;

public class PaginationResult {
	private int pageTotal;
	private List<?> list;
	private int page;

	PaginationResult(int page, int pageTotal, List<?> list) {
		this.pageTotal = pageTotal;
		this.list = list;
		this.page = page;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getPage() {
		return page;
	}
}

