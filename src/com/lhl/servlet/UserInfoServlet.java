package com.lhl.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhl.entity.User;
import com.lhl.service.UserService;
import com.lhl.vo.UserVo;

public class UserInfoServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String uid = request.getParameter("uid");
		User user = null;
		UserVo userVo = new UserVo();

		try {
			user = UserService.getInstance().queryUserByUid(uid);
		} catch (Exception e) {

		}
		if (null == user) {
			response.sendRedirect("/error.jsp");
		} else {
			userVo.setUid(user.getUid());
			userVo.setAvatar(user.getAvatar());
			userVo.setUserName(user.getUserName());
			request.setAttribute("userVo", userVo);
			request.getRequestDispatcher("/user.jsp")
					.forward(request, response);
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		super.service(req, resp);
	}
}
