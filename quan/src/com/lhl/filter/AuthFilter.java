package com.lhl.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
import com.lhl.util.Constant;
import com.lhl.util.Tools;

public class AuthFilter extends HttpServlet implements Filter
{
	private List<String> notCheckURLList = new ArrayList<String>();

	protected FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException
	{

		this.filterConfig = filterConfig;

		String notCheckURLListStr = filterConfig.getInitParameter("mustlogin");

		if (notCheckURLListStr != null)
		{
			StringTokenizer st = new StringTokenizer(notCheckURLListStr, ";");
			notCheckURLList.clear();
			while (st.hasMoreTokens())
			{
				notCheckURLList.add(st.nextToken());
			}
		}
	}

	public void destroy()
	{

		notCheckURLList.clear();
	}

	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException,
			ServletException
	{

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rep;
		Object userObj = request.getSession().getAttribute("user");

		String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
		if (!uri.contains("error.jsp"))
		{
			if (null == userObj && (checkRequestURIIntNotFilterList(request) || uri.contains("manage")))
			{
				response.sendRedirect("../error/error.jsp");
				return;
			}
			else
			{
				autoLogin(request);
				User user = (User) userObj;
				if (uri.contains("admin") && !Constant.SUPERADMIN.equals(user.getUserId()))
				{
					response.sendRedirect("../error/error.jsp");
					return;
				}

			}
		}
		chain.doFilter(request, rep);
	}

	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request)
	{

		String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
		return notCheckURLList.contains(uri);
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
