package com.lhl.blog.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter
{

	protected FilterConfig filterConfig = null;

	/**
	 * 默认的编码为GBK
	 */
	private String encoding = "utf-8";

	public void init(FilterConfig filterConfig) throws ServletException
	{

		this.filterConfig = filterConfig;
		encoding = filterConfig.getInitParameter("encoding");
	}

	/**
	 * 过滤处理方法
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException
	{

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		request.setCharacterEncoding(encoding);
		filterChain.doFilter(request, response);
	}

	public void destroy()
	{

		filterConfig = null;
		encoding = null;
	}
}