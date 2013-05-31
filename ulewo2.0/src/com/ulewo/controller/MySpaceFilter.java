package com.ulewo.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class MySpaceFilter implements Filter {

	private final static String G_SPACE = "g_space";

	private final static String G_USER = "g_user";

	private String[] domains;

	private String VM_ENTRY = "/WEB-INF/myspace/index.vm";

	private final static String[] static_ext = { "js", "css", "jpg", "png", "gif", ".html", "ico", "vm", "swf" };

	public void doFilter(ServletRequest req1, ServletResponse res1, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest req = (HttpServletRequest) req1;
		HttpServletResponse res = (HttpServletResponse) res1;
		String server = req.getServerName();
		String req_uri = req.getRequestURI();
		String[] paths = StringUtils.split(req_uri, '/');

		if (!ArrayUtils.contains(domains, server) || (paths.length > 0 && "action".equalsIgnoreCase(paths[0]))
				|| ArrayUtils.contains(static_ext, req_uri.substring(req_uri.lastIndexOf('.') + 1))) {
			chain.doFilter(req, res);
			return;
		}
		if (paths.length == 0) {
			res.sendRedirect("http://www.ulewo.com");
			return;
		}
		//通过第一个去获取用户名
		/*Space the_space = Space.GetByIdent(paths[0]);
		if (the_space == null) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}*/
		StringBuilder vm = new StringBuilder(VM_ENTRY);
		for (int i = 1; i < paths.length; i++) {
			vm.append((i == 1) ? '?' : '&');
			vm.append('p');
			vm.append(i);
			vm.append('=');
			vm.append(paths[i]);
		}
		req.setAttribute("request_uri", req.getRequestURI());
		//req.setAttribute(G_SPACE, the_space);
		//req.setAttribute(G_USER, User.GetLoginUser(req));
		RequestDispatcher rd = sContext.getRequestDispatcher(vm.toString());
		rd.forward(req, res);
	}

	private ServletContext sContext = null;

	public void init(FilterConfig cfg) throws ServletException {

		domains = StringUtils.split(cfg.getInitParameter("domain"), ',');
		sContext = cfg.getServletContext();

	}

	public void destroy() {

	}

}