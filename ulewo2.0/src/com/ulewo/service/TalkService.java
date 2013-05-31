package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.Talk;

public interface TalkService {

	/**
	 * 添加说说
	 * 
	 * @param talk
	 * @throws Exception
	 */
	public void addTalk(Talk talk);

	/**
	 * 查询最新说说
	 * 
	 * @param offset
	 *            TODO
	 * @param total
	 *            TODO
	 * 
	 * @return
	 */
	public List<Talk> queryLatestTalk(int offset, int total);

	public int queryTalkCount();

	public List<Talk> queryLatestTalkByUserId(int offset, int total, String userId);

	public int queryTalkCountByUserId(String userId);

	public Talk queryDetail(int talkId);
}
