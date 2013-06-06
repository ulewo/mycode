package com.ulewo.util;

import java.util.List;

public class PaginationResult {
	private int pageTotal;

	private List<?> list;

	private int page;

	private int countTotal;

	public PaginationResult(int page, int pageTotal, int countTotal, List<?> list) {

		this.pageTotal = pageTotal;
		this.list = list;
		this.page = page;
		this.countTotal = countTotal;
	}

	public PaginationResult() {

	}

	public void setPage(int page) {

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

	public int getCountTotal() {

		return countTotal;
	}

}
