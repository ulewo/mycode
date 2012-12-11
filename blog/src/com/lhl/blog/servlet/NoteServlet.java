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

public class NoteServlet extends HttpServlet
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
		if ("add".equals(method))
		{
			addNote(request, response);
		}
		else
		{
			noteList(request, response);
		}

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}

	private void addNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		String userName = request.getParameter("userName");
		String content = request.getParameter("content");
		String quote = request.getParameter("quote");
		Note note = new Note();
		String sessionName = String.valueOf(request.getSession().getAttribute("name"));

		if (Constant.SESSIONID.equals(sessionName))
		{
			note.setUserName(Constant.ADMIN_USER_NAME);
			note.setType("A");
		}
		else
		{
			note.setUserName(userName);
			note.setType("B");
		}
		note.setContent(quote + StringUtil.formateHtml(content));
		note = NoteService.getInstance().addNote(note);
		JSONObject obj = new JSONObject();
		obj.put("note", note);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	private void noteList(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String pageStr = request.getParameter("page");
		List<Note> list = new ArrayList<Note>();
		int page = 1;
		if (StringUtil.isNumber(pageStr))
		{
			page = Integer.parseInt(pageStr);
		}
		int pageTotal = 0;
		try
		{
			if (page <= 1)
			{
				page = 1;
			}
			int pageSize = Constant.PAGE_SIZE20;
			int countTotal = NoteService.getInstance().queryNoteCount();
			Pagination.setPageSize(pageSize);
			pageTotal = Pagination.getPageTotal(countTotal);
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
}
