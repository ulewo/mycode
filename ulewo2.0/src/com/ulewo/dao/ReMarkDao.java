package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ulewo.entity.ReMark;

@Component
public class ReMarkDao extends BaseDao {
	/**
	 * 签到
	 * 
	 * @param talk
	 * @throws Exception
	 */
	public void addReMark(ReMark reMark) {

		this.getSqlMapClientTemplate().insert("remark.addReMark", reMark);
	}

	public boolean isMark(String userId, String markDate) {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("markDate", markDate);
		int remarkCount = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("remark.queryReMarkByUser", parmMap);
		if (remarkCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询签到
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReMark> queryReMarkByTime(String markDate) {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("markDate", markDate);
		return (List<ReMark>) this.getSqlMapClientTemplate().queryForList(
				"remark.queryReMarkByTime", parmMap);
	}

	/**
	 * 查询签到数量
	 * 
	 * @param userId
	 * @return
	 */
	public int userMarkCount(String userId) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"remark.userMarkCount", userId);
	}

	/**
	 * 查询用户签到情况
	 * 
	 * @param userId
	 * @return
	 */
	public List<ReMark> userMarkInfo(String userId) {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		return (List<ReMark>) this.getSqlMapClientTemplate().queryForList(
				"remark.userMarkInfo", parmMap);
	}

	public int allMarkCount(String markDate) {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("markDate", markDate);
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"remark.allMarkCount", parmMap);
	}

	public int getCountBetweenTime(String userId, String startDate,
			String endDate) {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("startDate", startDate);
		parmMap.put("endDate", endDate);
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"remark.getCountBetweenTime", parmMap);
	}
}
