package com.lhl.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.lhl.blog.entity.Note;
import com.lhl.blog.service.NoteService;
import com.lhl.blog.util.Constant;
import com.lhl.blog.util.Pagination;
import com.lhl.blog.util.StringUtil;

public class AdminNoteServlet extends HttpServlet
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
			queryNote(request, response);
		}
		else if (Constant.ADMIN_METHOD_DELETE.equals(method))
		{
			deleteNote(request, response);
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}

	private void queryNote(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String pageStr = request.getParameter("page");
		List<Note> list = new ArrayList<Note>();
		int page = 1;
		if (StringUtil.isNumber(pageStr))
		{
			page = Integer.parseInt(pageStr);
		}
		if (page <= 1)
		{
			page = 1;
		}
		int pageSize = Constant.PAGE_SIZE20;
		int pageTotal = 0;
		try
		{
			int count = NoteService.getInstance().queryNoteCount();
			if (page <= 1)
			{
				page = 1;
			}
			Pagination.setPageSize(pageSize);
			pageTotal = Pagination.getPageTotal(count);
			if (page > pageTotal)
			{
				page = pageTotal;
			}
			int noStart = (page - 1) * pageSize;
			list = NoteService.getInstance().queryNotes(noStart, pageSize);
		}
		catch (Exception e)
		{
		}
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		obj.put("pageTotal", pageTotal);
		obj.put("page", page);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	private void deleteNote(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String idstr = request.getParameter("id");
		int id = 0;
		if (StringUtil.isNumber(idstr))
		{
			id = Integer.parseInt(idstr);
		}
		NoteService.getInstance().deleteNote(id);
		JSONObject obj = new JSONObject();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}
}
