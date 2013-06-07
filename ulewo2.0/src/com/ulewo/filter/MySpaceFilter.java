package com.ulewo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class MySpaceFilter implements Filter {

	private String domain;

	private String[] subDomain;

	private final static String SPACE = "my";

	private final static String GROUP = "group";

	private final static String[] static_ext = { "jsp", "js", "css", "jpg", "png", "gif", "html", "ico", "vm", "swf" };

	private final static String[] static_jsp = { "new_blog" };

	private ServletContext sContext = null;

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String server = request.getServerName();
		String req_uri = request.getRequestURI();
		String[] paths = StringUtils.split(req_uri, '/');
		if (!ArrayUtils.contains(subDomain, server)
				|| (paths.length > 0 && "manage".equalsIgnoreCase(paths[0]) || ArrayUtils.contains(static_ext,
						req_uri.substring(req_uri.lastIndexOf('.') + 1)))) {
			chain.doFilter(req, res);
			return;
		}
		if (ArrayUtils.contains(static_jsp, req_uri.substring(req_uri.lastIndexOf('/') + 1))) {
			req_uri = req_uri + ".jsp";
			sContext.getRequestDispatcher(req_uri).forward(request, response);
		}
		int firstIndex = server.indexOf(domain);
		String newUrl = "";
		String subDomain = "";
		if (firstIndex != -1 && firstIndex != 0) {
			subDomain = server.substring(0, firstIndex - 1);
			if (SPACE.equals(subDomain) && !req_uri.contains("reblog")) {//访问空间
				newUrl = "/user" + req_uri;
			}
			else if (GROUP.equals(subDomain)) {

			}
			sContext.getRequestDispatcher(newUrl).forward(request, response);
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {

		domain = cfg.getInitParameter("domain");
		subDomain = StringUtils.split(cfg.getInitParameter("subdomain"), ',');
		sContext = cfg.getServletContext();
	}
}