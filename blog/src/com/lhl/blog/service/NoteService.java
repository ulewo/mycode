package com.lhl.blog.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lhl.blog.dao.NoteDao;
import com.lhl.blog.entity.Note;
import com.lhl.blog.util.StringUtil;

public class NoteService
{
	private NoteService()
	{

	}

	private static NoteService instance;

	/**
	 * 构造实例
	 * 
	 * @return
	 */
	public synchronized static NoteService getInstance()
	{

		if (instance == null)
		{
			instance = new NoteService();
		}
		return instance;
	}

	public Note addNote(Note note)
	{

		String userName = note.getUserName();
		//String content = note.getContent();
		note.setUserName(StringUtil.formateHtml(userName));
		//note.setContent(StringUtil.formateHtml(content));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		note.setPostTime(format.format(new Date()));
		NoteDao.getInstance().addNote(note);
		return note;
	}

	public void deleteNote(int id)
	{

		NoteDao.getInstance().deleteNote(id);
	}

	public List<Note> queryNotes(int noStart, int pageSize)
	{

		return NoteDao.getInstance().queryNotes(noStart, pageSize);
	}

	public int queryNoteCount()
	{

		return NoteDao.getInstance().queryNoteCount();
	}
}
