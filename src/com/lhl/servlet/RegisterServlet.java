package com.lhl.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhl.entity.User;
import com.lhl.service.UserService;
import com.lhl.util.Constant;
import com.lhl.util.MyCookie;
import com.lhl.util.StringUtil;

public class RegisterServlet extends HttpServlet
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

		String sessionCcode = String.valueOf(request.getSession().getAttribute("checkCode"));
		/*
		 * var checkUserId = /^(?!\d)\w+$/; //只能是数字，字母，下划线，不能以数字开头; var
		 * checkUserName = /^(?!\d)[\w\u4e00-\u9fa5]+$/; //只能是数字，字母，下划线，中文。 var
		 * checkPassWord = /^[0-9a-zA-Z]+$/; //只能是数字，字母 var checkEmail =
		 * /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;// 验证email
		 */
		String checkEmail = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
		String checkUserName = "^[\\w\\u4e00-\\u9fa5]+$";
		String checkPassWord = "^[0-9a-zA-Z]+$";
		String checkCode = request.getParameter("checkCode");
		String email = request.getParameter("email");
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		String message = "";
		UserService userService = UserService.getInstance();
		String uid = "";
		try
		{
			if (StringUtil.isEmpty(checkCode))
			{
				message = "验证码错误";
			}
			else if (StringUtil.isEmpty(sessionCcode) || !sessionCcode.equalsIgnoreCase(checkCode))
			{
				message = "验证码错误";
			}
			else if (!email.matches(checkEmail) || StringUtil.isEmpty(email))
			{
				message = "邮箱地址不符合规范";
			}
			else if (!userName.matches(checkUserName) || StringUtil.isEmpty(userName)
					|| StringUtil.getRealLength(userName) < 1 || StringUtil.getRealLength(userName) > 20)
			{
				message = "用户名不符合规范";
			}
			else if (!passWord.matches(checkPassWord) || StringUtil.isEmpty(passWord) || passWord.length() < 6
					|| passWord.length() > 16)
			{
				message = "密码不符合规范";
			}
			else if (null != userService.queryUserByUserName(userName))
			{ // 后台检测用户昵称是否唯一
				message = "用户名已经被占用";
			}
			else
			{
				passWord = StringUtil.encodeByMD5(passWord);
				User user = new User();
				user.setUserName(userName);
				user.setPassword(passWord);
				user.setEmail(email);
				user.setAvatar(Constant.DEFALT_AVATAR);
				uid = userService.register(user);
				if (null != uid)
				{
					User loginUser = new User();
					loginUser.setUid(uid);
					loginUser.setUserName(userName);
					loginUser.setAvatar(user.getAvatar());
					request.getSession().setAttribute("user", loginUser);
					MyCookie.getInstance().addCookie(response, "uid", uid, 365 * 24 * 60 * 60);
					MyCookie.getInstance().addCookie(response, "password", passWord, 365 * 24 * 60 * 60);
				}
				else
				{
					message = "系统异常，请稍后再试";
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			message = "系统异常，请稍后再试";
		}
		if (!"".equals(message))
		{
			message = URLEncoder.encode(URLEncoder.encode(message, "UTF-8"));
			response.sendRedirect("register.jsp?message=" + message);
		}
		else
		{
			response.sendRedirect("user?uid=" + uid);
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}
}
