package com.ulewo.entity;

import java.util.List;

public class PaginationResult {
	private int pageTotal;

	private int page;

	private List<?> list;

	public int getPageTotal() {

		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {

		this.pageTotal = pageTotal;
	}

	public int getPage() {

		return page;
	}

	public void setPage(int page) {

		this.page = page;
	}

	public List<?> getList() {

		return list;
	}

	public void setList(List<?> list) {

		this.list = list;
	}

}
