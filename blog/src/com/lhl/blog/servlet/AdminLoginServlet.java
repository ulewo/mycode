package com.lhl.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.lhl.blog.util.Constant;

public class AdminLoginServlet extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		JSONObject obj = new JSONObject();
		String result = "success";
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		if (Constant.ADMIN_NAME.equals(name) && Constant.ADMIN_PASSWORD.equals(password))
		{
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(5184000);
			session.setAttribute("name", Constant.SESSIONID);
			obj.put("result", result);
		}
		else
		{
			obj.put("result", "error");
			obj.put("msg", "还试，就等着你电脑挂掉吧，哈哈.....");
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}
}
