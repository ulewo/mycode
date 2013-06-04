package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ulewo.entity.Notice;
@Component
public class NoticeDao extends BaseDao {

	/**
	 * 创建消息
	 * 
	 * @param member
	 * @throws Exception
	 */
	public void createNotice(Notice notice) {

		this.getSqlMapClientTemplate().insert("notice.createNotice", notice);
	}

	/**
	 * 删除消息
	 * 
	 * @param id
	 */
	public void deleteNotice(int id) {

		this.getSqlMapClientTemplate().delete("notice.deleteNotice", id);
	}

	/**
	 * 更新消息
	 * 
	 * @param member
	 */
	public void updateNotice(Notice notice) {

		this.getSqlMapClientTemplate().update("notice.updateNotice", notice);
	}

	/**
	 * 
	 * description: 查询消息
	 * 
	 * @param grade
	 *            TODO
	 * @param order
	 *            更具加入时间倒序，顺序排列
	 * @param groupNum
	 * @param itemId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author luohl
	 */
	public List<Notice> queryNoticeByUserId(String userId, String status) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("status", status);
		return this.getSqlMapClientTemplate().queryForList("notice.queryNoticeByUserId", parmMap);
	}

	public Notice getNotice(int id) {

		return (Notice) this.getSqlMapClientTemplate().queryForObject("notice.getNotice", id);
	}

	/**
	 * 查询消息数
	 * 
	 * @param status
	 * @param grade
	 *            TODO
	 * @param groupNum
	 * @return
	 */
	public int queryNoticeCountByUserId(String userId, String status) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userId", userId);
		parmMap.put("status", status);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("notice.queryNoticeCountByUserId", parmMap);
	}
}
