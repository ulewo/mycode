package com.doov.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.doov.entity.Notice;

@Component("noticeDao")
public class NoticeDao extends BaseDao {

	/**
	 * 
	 * description:多笔查询
	 * @return
	 * @author luohl
	 */
	@SuppressWarnings("unchecked")
	public List<Notice> selectAll(String title, int offset, int total) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("offset", offset);
		map.put("total", total);
		return (List<Notice>) this.getSqlMapClientTemplate().queryForList("notice.selectAll", map);
	}

	/**
	 * 
	 * description:查询总条数
	 * @return
	 * @author luohl
	 */
	public int getCount(String title) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("notice.selectCount", title);
	}

	/**
	 * 
	 * description:新增
	 * @param notice
	 * @return
	 * @author luohl
	 */
	public int add(Notice notice) {

		return (Integer) this.getSqlMapClientTemplate().insert("notice.add", notice);
	}

	/**
	 * 
	 * description: 单笔查询
	 * 
	 * @return
	 * @author haley
	 */
	public Notice queryNotice(int id) {

		return (Notice) this.getSqlMapClientTemplate().queryForObject("notice.selectById", id);
	}

	/**
	 * 
	 * description: 删除
	 * 
	 * @param id
	 * @author haley
	 */
	public void delete(int id) {

		this.getSqlMapClientTemplate().delete("notice.delete");
	}

	/**
	 * 
	 * description: 更新
	 * 
	 * @author haley
	 */
	public void update(Notice notice) {

		this.getSqlMapClientTemplate().update("notice.update", notice);
	}
}
