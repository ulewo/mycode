package com.lhl.quan.service;

import java.util.List;

import com.lhl.entity.Notice;

/**
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
public interface NoticeService {
	public void updateNotice(Notice notice);

	public List<Notice> queryNoticeByUserId(String userId, String status);

	public int queryNoticeCountByUserId(String userId, String status);

	public Notice getNotice(int id);

	public void deleteNotice(int id);
}
