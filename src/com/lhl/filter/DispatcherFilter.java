package com.lhl.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhl.entity.User;
import com.lhl.service.UserService;
import com.lhl.util.Constant;
import com.lhl.util.MyCookie;
import com.lhl.util.StringUtil;

public class DispatcherFilter implements Filter {

	protected FilterConfig filterConfig = null;

	/**
	 * 过滤处理方法
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String currentURL = request.getRequestURI();

		if (!currentURL.endsWith(".css") && !currentURL.endsWith(".js")
				&& !currentURL.endsWith(".jpg") && !currentURL.endsWith(".png")
				&& !currentURL.endsWith(".gif")) {
			Object sessionObj = request.getSession().getAttribute("user");
			if (null == sessionObj) {// 自动登录
				String uid = MyCookie.getInstance().getValueByKey(request,
						"uid");
				String pwd = MyCookie.getInstance().getValueByKey(request,
						"password");
				if (StringUtil.isNotEmpty(uid) && StringUtil.isNotEmpty(pwd)) {

					try {
						User user = UserService.getInstance().queryUserByUid(
								uid);
						if (null != user && user.getPassword().equals(pwd)) {
							User loginUser = new User();
							loginUser.setUid(user.getUid());
							loginUser.setUserName(user.getUserName());
							loginUser.setAvatar(user.getAvatar());
							request.getSession()
									.setAttribute("user", loginUser);
							sessionObj = loginUser;
						}
					} catch (Exception e) {
					}
				}
			}
			if (currentURL.contains("admin")) {
				if (null == sessionObj) {
					request.getRequestDispatcher("error.jsp").forward(request,
							response);
					return;
				} else {
					User user = (User) sessionObj;
					if (!user.getUid().equals("10000")) {
						request.getRequestDispatcher("error.jsp").forward(
								request, response);
						return;
					}
				}
			}
			String pageStr = request.getParameter("page");
			int page = 1;
			if (StringUtil.isNumber(pageStr)) {
				page = Integer.parseInt(pageStr);
			}
			if (currentURL.endsWith(Constant.TIME_RANGE_DAY)) { // 首页

				request.getRequestDispatcher(
						"article?type=" + Constant.TYPE_INDEX + "&timeRange="
								+ Constant.TIME_RANGE_DAY + "&page=" + page)
						.forward(request, response);
				return;
			}
			if (currentURL.endsWith(Constant.TIME_RANGE_WEEK)) { // 一周
				request.getRequestDispatcher(
						"article?type=" + Constant.TYPE_INDEX + "&timeRange="
								+ Constant.TIME_RANGE_WEEK + "&page=" + page)
						.forward(request, response);
				return;
			}
			if (currentURL.endsWith(Constant.TIME_RANGE_MONTH)) { // 一月
				request.getRequestDispatcher(
						"article?type=" + Constant.TYPE_INDEX + "&timeRange="
								+ Constant.TIME_RANGE_MONTH + "&page=" + page)
						.forward(request, response);
				return;
			}
			if (currentURL.endsWith(Constant.TIME_RANGE_YEAR)) { // 一年
				request.getRequestDispatcher(
						"article?type=" + Constant.TYPE_INDEX + "&timeRange="
								+ Constant.TIME_RANGE_YEAR + "&page=" + page)
						.forward(request, response);
				return;
			}
			if (currentURL.endsWith("new")) {
				request.getRequestDispatcher(
						"article?type=" + Constant.TYPE_NEW + "&page=" + page)
						.forward(request, response);
				return;
			}
			if (currentURL.endsWith("hot")) {
				request.getRequestDispatcher(
						"article?type=" + Constant.TYPE_HOT + "&page=" + page)
						.forward(request, response);
				return;
			}
			if (currentURL.endsWith("pic")) {
				request.getRequestDispatcher("pic.jsp").forward(request,
						response);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}