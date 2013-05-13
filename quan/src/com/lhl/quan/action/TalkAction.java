package com.lhl.quan.action;

import java.util.List;

import net.sf.json.JSONObject;

import com.lhl.common.action.BaseAction;
import com.lhl.entity.Talk;
import com.lhl.entity.User;
import com.lhl.quan.service.TalkService;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;
import com.lhl.util.Tools;

public class TalkAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private TalkService talkservice;
	private static final int MAXLENGTH = 250;

	public void setContent(String content) {
		this.content = content;
	}

	private String content;

	private int page;
	private String userId;
	private Talk talk;
	private int talkId;

	public void addTalk() {
		String msg = "success";
		JSONObject obj = new JSONObject();
		try {
			Object sessionObj = getSession().getAttribute("user");
			if (sessionObj == null) {
				msg = "nologin";
				obj.put("msg", msg);
				getOut().print(String.valueOf(obj));
				return;
			}
			if (Tools.isEmpty(content) || content.length() > MAXLENGTH) {
				msg = "contentError";
				obj.put("msg", msg);
				getOut().print(String.valueOf(obj));
				return;
			}
			User sessionUser = (User) sessionObj;
			Talk talk = new Talk();
			talk.setContent(content);
			talk.setUserId(sessionUser.getUserId());
			talk.setUserName(sessionUser.getUserName());
			talk.setUserIcon(sessionUser.getUserLittleIcon());
			talkservice.addTalk(talk);
			talk.setCreateTime(Tools.friendly_time(talk.getCreateTime()));
			obj.put("msg", msg);
			obj.put("talk", talk);
			getOut().print(String.valueOf(obj));
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("msg", "error");
			getOut().print(String.valueOf(obj));
		}
	}

	public void queryLatstTalk() {
		JSONObject obj = new JSONObject();
		try {
			int offset = 0;
			int total = 5;
			List<Talk> list = talkservice.queryLatestTalk(offset, total);
			obj.put("list", list);
			getOut().print(String.valueOf(obj));
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("msg", "error");
			getOut().print(String.valueOf(obj));
		}
	}

	public void queryTalk() {
		int countNumber = talkservice.queryTalkCount();
		Pagination.setPageSize(Constant.pageSize10);
		int pageSize = Pagination.getPageSize();
		int pageTotal = Pagination.getPageTotal(countNumber);
		if (page > pageTotal) {
			page = pageTotal;
		}
		if (page < 1) {
			page = 1;
		}
		int noStart = (page - 1) * pageSize;
		List<Talk> list = talkservice.queryLatestTalk(noStart, pageSize);
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		obj.put("pageTotal", pageTotal);
		obj.put("page", page);
		getOut().print(String.valueOf(obj));
	}

	public void queryUserTalk() {
		int countNumber = talkservice.queryTalkCountByUserId(userId);
		Pagination.setPageSize(5);
		int pageSize = Pagination.getPageSize();
		int pageTotal = Pagination.getPageTotal(countNumber);
		if (page > pageTotal) {
			page = pageTotal;
		}
		if (page < 1) {
			page = 1;
		}
		int noStart = (page - 1) * pageSize;
		List<Talk> list = talkservice.queryLatestTalkByUserId(noStart,
				pageSize, userId);
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		obj.put("pageTotal", pageTotal);
		obj.put("page", page);
		getOut().print(String.valueOf(obj));
	}

	public String talkDetail() {
		try {
			talk = talkservice.queryDetail(talkId);
			if (null == talk) {
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public void setTalkservice(TalkService talkservice) {
		this.talkservice = talkservice;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Talk getTalk() {
		return talk;
	}

	public void setTalkId(int talkId) {
		this.talkId = talkId;
	}

}
