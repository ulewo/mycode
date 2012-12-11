package com.lhl.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.lhl.entity.User;
import com.lhl.service.UserService;
import com.lhl.util.MyCookie;
import com.lhl.util.StringUtil;

public class LoginServlet extends HttpServlet
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

		String type = request.getParameter("type");
		String userName = request.getParameter("userName");
		if ("check".equals(type)) //验证用户名
		{
			String result = "Y";
			try
			{
				User user = UserService.getInstance().queryUserByUserName(userName);
				if (null != user)
				{
					result = "N";
				}
			}
			catch (Exception e)
			{

			}
			JSONObject obj = new JSONObject();
			obj.put("result", result);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(String.valueOf(obj));
		}
		else
		//登录
		{
			String sessionCcode = String.valueOf(request.getSession().getAttribute("checkCode"));
			String passWord = request.getParameter("passWord");
			passWord = StringUtil.encodeByMD5(passWord);
			String autoLogin = request.getParameter("autoLogin");
			String checkCode = request.getParameter("checkCode");
			String result = "error";
			String msg = "";
			try
			{
				if (StringUtil.isEmpty(checkCode))
				{
					msg = "验证码错误";
				}
				else if (StringUtil.isEmpty(sessionCcode) || !sessionCcode.equalsIgnoreCase(checkCode))
				{
					msg = "验证码错误";
				}
				else
				{
					UserService userService = UserService.getInstance();
					User user = userService.queryUserByUserName(userName);
					if (null == user)
					{ //用户不存在
						msg = "用户不存在";
					}
					else
					{
						if (!user.getPassword().equals(passWord))
						{
							msg = "密码错误";
						}
						else
						{
							User loginUser = new User();
							loginUser.setUid(user.getUid());
							loginUser.setUserName(user.getUserName());
							loginUser.setAvatar(user.getAvatar());
							request.getSession().setAttribute("user", loginUser);
							if ("Y".equals(autoLogin))
							{
								MyCookie.getInstance().addCookie(response, "uid", user.getUid(), 365 * 24 * 60 * 60);
								MyCookie.getInstance().addCookie(response, "password", passWord, 365 * 24 * 60 * 60);
							}
							result = "success";
						}
					}
				}
			}
			catch (Exception e)
			{
			}
			JSONObject obj = new JSONObject();
			obj.put("result", result);
			obj.put("msg", msg);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(String.valueOf(obj));
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}
}
