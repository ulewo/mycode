package com.lhl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.lhl.util.MyCookie;

public class QuitServlet extends HttpServlet
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

		String result = "success";
		try
		{
			HttpSession session = request.getSession();
			Enumeration em = session.getAttributeNames();
			while (em.hasMoreElements())
			{
				session.removeAttribute(em.nextElement().toString());
			}
			MyCookie.getInstance().addCookie(response, "uid", null, 0);
			MyCookie.getInstance().addCookie(response, "password", null, 0);
		}
		catch (Exception e)
		{
			result = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
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
