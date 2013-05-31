package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.ReTalk;

public interface ReTalkService {

	/**
	 * 添加说说回复
	 * 
	 * @param talk
	 * @throws Exception
	 */
	public void addReTalk(ReTalk talk);

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
	public List<ReTalk> queryReTalk(int offset, int total, int talkId);

	public int queryReTalkCount(int talkId);

	public void deleteReTalk(int reTalkId);
}
