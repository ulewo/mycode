package com.lhl.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.lhl.blog.entity.Item;
import com.lhl.blog.service.ItemService;
import com.lhl.blog.util.Constant;
import com.lhl.blog.util.StringUtil;

public class AdminItemServlet extends HttpServlet
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

		String method = request.getParameter("method");
		if (Constant.ADMIN_METHOD_LIST.equals(method))
		{
			queryItem(request, response);
		}
		else if (Constant.ADMIN_METHOD_SAVE.equals(method))
		{
			saveItem(request, response);
		}
		else if (Constant.ADMIN_METHOD_DELETE.equals(method))
		{
			deleteItem(request, response);
		}
		else
		{
			queryItem(request, response);
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}

	private void queryItem(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		List<Item> list = ItemService.getInstance().queryItems();
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	private void saveItem(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String idstr = request.getParameter("id");
		String itemName = request.getParameter("itemName");
		String rangStr = request.getParameter("rang");
		int rang = 0;
		int id = 0;
		if (StringUtil.isNumber(rangStr))
		{
			rang = Integer.parseInt(rangStr);
		}
		if (StringUtil.isNumber(idstr))
		{
			id = Integer.parseInt(idstr);
		}
		Item item = new Item();
		item.setId(id);
		item.setItemName(itemName);
		item.setRang(rang);
		ItemService.getInstance().saveItem(item);
		JSONObject obj = new JSONObject();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String idStr = request.getParameter("id");
		int id = 0;
		if (StringUtil.isNumber(idStr))
		{
			id = Integer.parseInt(idStr);
		}
		ItemService.getInstance().deleteItem(id);
		JSONObject obj = new JSONObject();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	private void updateItem(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String idStr = request.getParameter("id");
		int id = 0;
		if (StringUtil.isNumber(idStr))
		{
			id = Integer.parseInt(idStr);
		}
		String itemName = request.getParameter("itemName");
		String rangStr = request.getParameter("rang");
		int rang = 0;
		if (StringUtil.isNumber(rangStr))
		{
			rang = Integer.parseInt(rangStr);
		}
		Item item = new Item();
		item.setItemName(itemName);
		item.setRang(rang);
		item.setId(id);
		ItemService.getInstance().updateItem(item);
		JSONObject obj = new JSONObject();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}
}
