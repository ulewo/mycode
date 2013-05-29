package com.ulewo.util;

import java.util.List;

public class Pagination {
	private int page;
	private int offSet;
	private int countTotal;
	private int pageSize;
	private int pageTotal;

	Pagination(int page, int countTotal, int pageSize) {
		this.page = page;
		this.countTotal = countTotal;
		this.pageSize = countTotal;
	}

	public void action() {
		pageTotal = countTotal % pageSize == 0 ? countTotal / pageSize
				: countTotal / pageSize + 1;

		if (page <= 1) {
			page = 1;
		}
		if (page > pageTotal) {
			page = pageTotal;
		}
		offSet = (page - 1) * pageSize;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public int getOffSet() {
		return offSet;
	}
}

class PaginationRsult {
	private int pageTotal;
	private List<?> list;
	private int page;

	PaginationRsult(int page, int pageTotal, List<?> list) {
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
