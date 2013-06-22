package com.ulewo.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ulewo.entity.SessionUser;
import com.ulewo.entity.User;
import com.ulewo.service.UserService;
import com.ulewo.service.impl.UserServiceImpl;
import com.ulewo.util.Constant;

public class AuthorityFilter implements Filter {
	private final static String[] static_ext = { "js", "css", "jpg", "png",
			"gif", "html", "ico", "vm", "swf" };

	private final SimpleDateFormat formate = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String req_uri = request.getRequestURI();
		String type = req_uri.substring(req_uri.lastIndexOf('.') + 1);
		// 静态资源忽略
		if (ArrayUtils.contains(static_ext, type)) {
			chain.doFilter(req, res);
			return;
		}
		// 自动登录
		Object userObj = request.getSession().getAttribute("user");
		if (!req_uri.contains("error") && null == userObj) {
			autoLogin(request);
			// 登录后重新给对象赋值
			userObj = request.getSession().getAttribute("user");
		}
		// 以action结尾的没有登录，直接跳闸un到错误页面
		if (null == userObj
				&& ("action".equals(type) || req_uri.contains("manage") || req_uri
						.contains("groupManage"))) {
			response.sendRedirect(Constant.ERRORPAGE);
			return;
		}
		chain.doFilter(request, response);
	}

	private void autoLogin(HttpServletRequest req) {

		try {
			Cookie cookieInfo = getCookieByName(req, "cookieInfo");
			if (cookieInfo != null) {
				String info = URLDecoder.decode(cookieInfo.getValue(), "utf-8");
				if (info != null && !"".equals(info)) {
					String infos[] = info.split(",");
					WebApplicationContext webApplicationContext = WebApplicationContextUtils
							.getWebApplicationContext(req.getSession()
									.getServletContext());
					UserService userService = (UserServiceImpl) webApplicationContext
							.getBean("userService");
					User user = userService.login(infos[0], infos[1]);
					if (user != null) {
						SessionUser loginUser = new SessionUser();
						loginUser.setUserId(user.getUserId());
						loginUser.setUserName(user.getUserName());
						loginUser.setUserLittleIcon(user.getUserLittleIcon());
						req.getSession().setAttribute("user", loginUser);
						user.setPrevisitTime(formate.format(new Date()));
						userService.updateUser(user);
					}
				}
			}
		} catch (Exception e) {
		}

	}

	public Cookie getCookieByName(HttpServletRequest request, String name) {

		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	private Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {

		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}