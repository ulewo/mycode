package com.lhl.quan.service.impl;

import com.lhl.entity.NoticeParam;
import com.lhl.enums.NoticeType;

public class NoticeThread implements Runnable {

	private NoticeParam noticeParm;

	public NoticeThread(NoticeParam noticeParm) {

		noticeParm = this.noticeParm;
	}

	@Override
	public void run() {

		NoticeType noticeType = noticeParm.getNoticeType();

		int articleId = noticeParm.getArticleId();

		String userId = noticeParm.getUserId();

		String userName = noticeParm.getUserId();

		String reUserId = noticeParm.getReUserId();

		String reUserName = noticeParm.getUserName();
		//回复主题
		if (NoticeType.REARTICLE == noticeType) {

		}
		else if (NoticeType.REARTICLE == noticeType) {

		}
	}
}
