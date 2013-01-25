package com.lhl.quan.action;

import net.sf.json.JSONObject;

import com.lhl.common.action.BaseAction;
import com.lhl.entity.User;
import com.lhl.quan.service.NoticeService;

public class NoticeAction extends BaseAction
{
	private NoticeService noticeService;

	public void setNoticeService(NoticeService noticeService)
	{

		this.noticeService = noticeService;
	}

	int noticeCount = 0;

	public void queryNotice()
	{

		User sessionUser = (User) getSession().getAttribute("user");
		noticeCount = noticeService.queryNoticeCountByUserId(sessionUser.getUserId(), "N");
		JSONObject obj = new JSONObject();
		obj.put("noticeCount", noticeCount);
		getOut().print(String.valueOf(obj));
	}

	public int getNoticeCount()
	{

		return noticeCount;
	}

}
