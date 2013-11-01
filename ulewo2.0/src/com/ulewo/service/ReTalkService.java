package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.ReTalk;
import com.ulewo.util.PaginationResult;

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

	public PaginationResult queryReTalkByPag(int page, int pageSize, int talkId);

	public int queryReTalkCount(int talkId);

	public void deleteReTalk(int reTalkId);
	
	public PaginationResult queryAllReTalkByPag(int page, int pageSize);
	
	public void deleteReTalkBatch(String keyStr);
}
