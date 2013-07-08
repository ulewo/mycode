package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ulewo.entity.ReTalk;

@Component
public class ReTalkDao extends BaseDao {
	/**
	 * 添加说说
	 * 
	 * @param talk
	 * @throws Exception
	 */
	public void addReTalk(ReTalk retalk) {

		this.getSqlMapClientTemplate().insert("retalk.addretalk", retalk);
	}

	/**
	 * 查询最新说说
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReTalk> queryReTalk(int offset, int total, int talkid) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		parmMap.put("talkId", talkid);
		return (List<ReTalk>) this.getSqlMapClientTemplate().queryForList("retalk.queryReTalk", parmMap);
	}

	/**
	 * 查询数量
	 * 
	 * @return
	 */
	public int queryReTalkCount(int talkId) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("retalk.queryCount", talkId);
	}

	/**
	 * 删除回复
	 * 
	 * @param reTalkId
	 */
	public void deleteReTalk(int reTalkId) {

		this.getSqlMapClientTemplate().delete("retalk.delteReTalk", reTalkId);
	}

	public List<ReTalk> queryReTalkByUserId(int offset, int total, List<String> userId) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		parmMap.put("userIds", userId);
		return (List<ReTalk>) this.getSqlMapClientTemplate().queryForList("retalk.queryReTalkByUserId", parmMap);
	}

	public int queryReTalkCountByUserId(List<String> userIds) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("retalk.queryReTalkCountByUserId", userIds);
	}
}
