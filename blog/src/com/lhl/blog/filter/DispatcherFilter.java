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

import com.lhl.blog.util.Constant;

public class DispatcherFilter implements Filter
{

	protected FilterConfig filterConfig = null;

	/**
	 * 过滤处理方法
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException
	{

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String currentURL = request.getRequestURI();
		Object sessionObj = request.getSession().getAttribute("name");
		String name = String.valueOf(sessionObj);
		if (currentURL.contains("admin") && !Constant.SESSIONID.equals(name))
		{
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void destroy()
	{

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{

	}
}