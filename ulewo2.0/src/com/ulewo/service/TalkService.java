package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.Talk;
import com.ulewo.util.PaginationResult;

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
	public PaginationResult queryLatestTalkByPag(int page, int pageSize);

	public List<Talk> queryLatestTalk(int offset, int total);

	public int queryTalkCount();

	public List<Talk> queryLatestTalkByUserId(int offset, int total, List<String> userIds);

	public int queryTalkCountByUserId(List<String> userIds);

	public PaginationResult queryTalkByUserIdByPag(int page, int pageSize, String userId, Object sessionUser, int type);

	public Talk queryDetail(int talkId);
}
