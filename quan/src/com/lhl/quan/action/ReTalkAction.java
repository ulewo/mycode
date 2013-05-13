package com.lhl.quan.action;

import java.util.List;

import net.sf.json.JSONObject;

import com.lhl.common.action.BaseAction;
import com.lhl.entity.ReTalk;
import com.lhl.entity.User;
import com.lhl.quan.service.ReTalkService;
import com.lhl.util.Tools;

public class ReTalkAction extends BaseAction {
	private static final int MAXLENGTH = 250;

	private ReTalkService reTalkService;

	private int talkId;
	private int reTalkId;
	private String atUserId;
	private String atUserName;
	private String content;

	public void setReTalkService(ReTalkService reTalkService) {
		this.reTalkService = reTalkService;
	}

	public void setTalkId(int talkId) {
		this.talkId = talkId;
	}

	public void setReTalkId(int reTalkId) {
		this.reTalkId = reTalkId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAtUserId(String atUserId) {
		this.atUserId = atUserId;
	}

	public void setAtUserName(String atUserName) {
		this.atUserName = atUserName;
	}

	public void addReTalk() {
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
			ReTalk retalk = new ReTalk();
			retalk.setTalkId(reTalkId);
			retalk.setContent(content);
			retalk.setUserId(sessionUser.getUserId());
			retalk.setUserName(sessionUser.getUserName());
			retalk.setUserIcon(sessionUser.getUserLittleIcon());
			retalk.setAtUserId(atUserId);
			retalk.setAtUserName(atUserName);
			reTalkService.addReTalk(retalk);
			retalk.setCreateTime(Tools.friendly_time(retalk.getCreateTime()));
			obj.put("msg", msg);
			obj.put("retalk", retalk);
			getOut().print(String.valueOf(obj));
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("msg", "error");
			getOut().print(String.valueOf(obj));
		}
	}

	public void queryReTalk() {
		int countNumber = reTalkService.queryReTalkCount(talkId);
		List<ReTalk> list = reTalkService.queryReTalk(0, countNumber, talkId);
		JSONObject obj = new JSONObject();
		obj.put("list", list);
		getOut().print(String.valueOf(obj));
	}
}
