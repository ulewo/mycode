package com.ulewo.util;

public class Pagination {
	private int page;

	private int offSet;

	private int countTotal;

	private int pageSize;

	private int pageTotal;

	public Pagination(int page, int countTotal, int pageSize) {

		this.page = page;
		this.countTotal = countTotal;
		this.pageSize = pageSize;
	}

	public void action() {

		if (countTotal > 0) {
			pageTotal = countTotal % pageSize == 0 ? countTotal / pageSize : countTotal / pageSize + 1;
		}
		else {
			pageTotal = 1;
		}

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

	public int getPage() {

		return page;
	}
}
