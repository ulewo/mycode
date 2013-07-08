package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ulewo.entity.Talk;

@Component
public class TalkDao extends BaseDao {
	/**
	 * 添加说说
	 * 
	 * @param talk
	 * @throws Exception
	 */
	public void addTalk(Talk talk) {

		this.getSqlMapClientTemplate().insert("talk.addtalk", talk);
	}

	/**
	 * 查询最新说说
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Talk> queryLatestTalk(int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return (List<Talk>) this.getSqlMapClientTemplate().queryForList("talk.queryLatestTalk", parmMap);
	}

	/**
	 * 查询数量
	 * 
	 * @return
	 */
	public int queryTalkCount() {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("talk.queryCount");
	}

	public Talk queryDetail(int talkId) {

		return (Talk) this.getSqlMapClientTemplate().queryForObject("talk.queryDetail", talkId);
	}

	public List<Talk> queryTalkByUserId(int offset, int total, List<String> userId) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		parmMap.put("userIds", userId);
		return (List<Talk>) this.getSqlMapClientTemplate().queryForList("talk.queryTalkByUserId", parmMap);
	}

	public int queryTalkCountByUserId(List<String> userIds) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("talk.queryCountByUserId", userIds);
	}
}
