package com.lhl.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.lhl.service.ArticleService;
import com.lhl.util.Constant;
import com.lhl.util.MyCookie;
import com.lhl.util.StringUtil;

public class OperationServlet extends HttpServlet
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
			String type = request.getParameter("type");
			String articleId = request.getParameter("id");
			int id = 0;
			//如果ID是数字 并且类型是D 或者U 就执行操作否则不执行任何操作  防止从客户端直接调用
			if (StringUtil.isNumber(articleId) && (Constant.TYPE_DOWN.equals(type) || Constant.TYPE_UP.equals(type)))
			{
				if (!checkIsOperation(articleId, request, response))
				{
					id = Integer.parseInt(articleId);
					ArticleService.getInstance().updateDownOrUp(id, type);
				}
				else
				{
					result = "haveOp";
				}

			}
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

	//判断是否已经进行up 或者down操作
	private boolean checkIsOperation(String articleId, HttpServletRequest request, HttpServletResponse response)
	{

		String ip = request.getRemoteAddr();
		String key = ip + "." + articleId;
		String value = MyCookie.getInstance().getValueByKey(request, key);
		if (StringUtil.isNotEmpty(value))
		{
			return true;
		}
		else
		{
			MyCookie.getInstance().addCookie(response, key, articleId, 365 * 24 * 60 * 60);
			return false;
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}
}
