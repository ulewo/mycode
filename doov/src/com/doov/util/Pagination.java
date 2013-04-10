package com.doov.util;

public class Pagination {
	private static int pageSize;

	public static int getPageSize() {
		return pageSize;
	}

	public static void setPageSize(int pageSize) {
		Pagination.pageSize = pageSize;
	}

	public static int getPageTotal(int recordsCount) {
		int pageTotal = 0;
		if (recordsCount % pageSize == 0) {
			pageTotal = recordsCount / pageSize;
		} else {
			pageTotal = recordsCount / pageSize + 1;
		}
		if (pageTotal == 0) {
			pageTotal = 1;
		}
		return pageTotal;
	}
}
