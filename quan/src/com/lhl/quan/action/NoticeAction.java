package com.lhl.quan.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.lhl.common.action.BaseAction;
import com.lhl.entity.Notice;
import com.lhl.entity.User;
import com.lhl.quan.service.NoticeService;

public class NoticeAction extends BaseAction {
	private NoticeService noticeService;

	public void setNoticeService(NoticeService noticeService) {

		this.noticeService = noticeService;
	}

	int noticeCount = 0;

	private List<Notice> list = new ArrayList<Notice>();

	private String userId;

	private int id;

	private String redirectUrl;

	public void queryNotice() {

		User sessionUser = (User) getSession().getAttribute("user");
		noticeCount = noticeService.queryNoticeCountByUserId(
				sessionUser.getUserId(), "N");
		JSONObject obj = new JSONObject();
		obj.put("noticeCount", noticeCount);
		getOut().print(String.valueOf(obj));
	}

	public String notice() {
		User sessionUser = (User) getSession().getAttribute("user");
		try {
			userId = sessionUser.getUserId();
			list = noticeService.queryNoticeByUserId(userId, "N");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String noticeDetail() {
		try {
			User sessionUser = (User) getSession().getAttribute("user");
			Notice notice = noticeService.getNotice(id);
			if (notice != null
					&& sessionUser.getUserId().equals(notice.getUserId())) {
				redirectUrl = notice.getUrl();
				noticeService.deleteNotice(id);
			} else {
				return ERROR;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public List<Notice> getList() {
		return list;
	}

	public String getUserId() {
		return userId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

}
