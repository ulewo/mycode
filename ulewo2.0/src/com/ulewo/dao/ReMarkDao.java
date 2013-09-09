package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ulewo.entity.ReMark;
import com.ulewo.entity.Talk;

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

	public boolean isMark(String userId,String time){
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("time", time);
		int remarkCount = (Integer)this.getSqlMapClientTemplate().queryForObject("remark.queryReMarkByUser",parmMap);
		if(remarkCount>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 *查询签到
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReMark> queryReMarkByTime(String time) {
		return (List<ReMark>) this.getSqlMapClientTemplate().queryForList("remark.queryReMarkByTime", time);
	}

}
