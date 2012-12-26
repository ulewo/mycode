package com.lhl.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lhl.entity.User;
import com.lhl.quan.service.UserService;
import com.lhl.quan.service.impl.UserServiceImpl;
import com.lhl.util.Tools;

public class AuthFilter extends HttpServlet implements Filter
{
	public void destroy()
	{

	}

	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException,
			ServletException
	{

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rep;
		String currentURL = request.getRequestURI();
		Object userObj = request.getSession().getAttribute("user");
		/*if (currentURL.contains("cms")) {
			if (user == null || !"10000".equals(user.getUserId())) {
				response.sendRedirect("../index.jspx");
				return;
			}
		}*/
		if (currentURL.contains("jspx"))
		{
			if (userObj == null)
			{
				autoLogin(request);
			}
		}
		if ((currentURL.contains("reArticle") || currentURL.contains("subReArticle") || currentURL.contains("manage"))
				&& null == userObj)
		{
			//response.sendRedirect("../error/error.jsp");
			//return;
		}

		if (currentURL.contains("admin"))
		{
			if (null == userObj)
			{
				response.sendRedirect("../error/error.jsp");
				return;
			}
			else
			{
				User user = (User) userObj;
				if (!"10000".equals(user.getUserId()))
				{
					response.sendRedirect("../error/error.jsp");
					return;
				}
			}

		}
		/*
		 * if (currentURL.contains("vsgame")) { if
		 * (currentURL.contains("/vsgame/vsgame.jspx") ||
		 * currentURL.contains("/vsgame/relation.jspx")) { if (user == null) {
		 * response.sendRedirect("../common/nologin.jsp"); return; } } else { if
		 * (user == null || !"Y".equals(user.getIsRelatVs())) {
		 * response.sendRedirect("../index.jspx"); return; } } }
		 */
		chain.doFilter(request, rep);
	}

	public void init(FilterConfig filterConfig) throws ServletException
	{

	}

	private void autoLogin(HttpServletRequest req)
	{

		try
		{
			Cookie cookieInfo = getCookieByName(req, "cookieInfo");
			if (cookieInfo != null)
			{
				String info = URLDecoder.decode(cookieInfo.getValue(), "utf-8");
				if (info != null && !"".equals(info))
				{
					String infos[] = info.split(",");
					WebApplicationContext webApplicationContext = WebApplicationContextUtils
							.getWebApplicationContext(req.getSession().getServletContext());
					UserService userService = (UserServiceImpl) webApplicationContext.getBean("userService");
					User user = userService.login(infos[0]);
					if (user != null && Tools.encodeByMD5(infos[1]).equals(user.getPassword()))
					{
						User loginUser = new User();
						loginUser.setUserId(user.getUserId());
						loginUser.setUserName(user.getUserName());
						loginUser.setUserLittleIcon(user.getUserLittleIcon());
						req.getSession().setAttribute("user", loginUser);
					}
				}
			}
		}
		catch (Exception e)
		{
		}

	}

	public Cookie getCookieByName(HttpServletRequest request, String name)
	{

		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name))
		{
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		}
		else
		{
			return null;
		}
	}

	private Map<String, Cookie> ReadCookieMap(HttpServletRequest request)
	{

		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies)
		{
			for (Cookie cookie : cookies)
			{
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
