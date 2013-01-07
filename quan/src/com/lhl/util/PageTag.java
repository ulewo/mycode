package com.lhl.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class PageTag extends BodyTagSupport {
	/**
	 * 提交的地址
	 */
	private String url = "";

	/**
	 * 当前页
	 */
	private int page;

	/**
	 * 总页数
	 */
	private int pageTotal;

	/**
	 * 起始页
	 */
	public static int beginNum = 0;

	/**
	 * 结尾页
	 */
	public static int endNum = 0;

	/**
	 * 分页显示的大数量
	 */
	public static final int pageNum = 10;

	/**
	 * 标签处理程序
	 */
	public int doStartTag() throws JspException {

		// 总页码
		int totalPage = 1;
		// 要输出到页面的HTML文本
		StringBuffer sb = new StringBuffer();
		if (pageNum > pageTotal) {
			beginNum = 1;
			endNum = pageTotal;
		}

		if (page - pageNum / 2 < 1) {
			beginNum = 1;
			endNum = pageNum;
		} else {
			beginNum = page - pageNum / 2 + 1;
			endNum = page + pageNum / 2;
		}

		if (pageTotal - page < pageNum / 2) {
			beginNum = pageTotal - pageNum + 1;
		}
		if (beginNum < 1) {
			beginNum = 1;
		}
		if (endNum > pageTotal) {
			endNum = pageTotal;
		}
		sb.append("<ul>");
		if (page > 1) {
			sb.append("<li><a href='" + url
					+ "1' class='prePage'>首&nbsp;&nbsp;页</a></li>");
			sb.append("<li><a href='" + url + (page - 1) + "'><</a></li>");
		}
		for (int i = beginNum; i <= endNum; i++) {
			if (pageTotal > 1) {
				if (i == page) {
					sb.append("<li id='nowPage'>" + page + "</li>");
				} else {
					sb.append("<li><a href='" + url + i + " '>" + i
							+ "</a></li>");
				}
			}
		}
		if (page < pageTotal) {
			sb.append("<li><a href='" + url + (page + 1) + "'>></a></li>");
			sb.append("<li><a href='" + url + pageTotal
					+ " ' class='prePage'>尾&nbsp;&nbsp;页</a></li>");
		}
		sb.append("</ul>");
		if (pageTotal > 1) {
			sb.append("<div style='float:right;width:50px;padding-top:5px'>"
					+ page + "/" + pageTotal + "</div>");
		}

		try {
			if (pageTotal > 0) {
				pageContext.getOut().println(sb.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		if (url.indexOf("?") > 0) {
			this.url = url + "&page=";
		} else {
			this.url = url + "?page=";
		}
	}

	public int getPage() {

		return page;
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

}