package com.ulewo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ulewo.entity.Message;

@Component
public class MessageDao extends BaseDao {

	/**
	 * 
	 * description: 多笔查询
	 * 
	 * @return
	 * @author haley
	 */
	@SuppressWarnings("unchecked")
	public List<Message> queryList() {

		return (List<Message>) this.getSqlMapClientTemplate().queryForList("message.selectAll");
	}

	/**
	 * 
	 * description: 单笔查询
	 * 
	 * @return
	 * @author haley
	 */
	public Message queryMessage(int id) {

		return (Message) this.getSqlMapClientTemplate().queryForObject("message.selectById", id);
	}

	/**
	 * 
	 * description: 删除
	 * 
	 * @param id
	 * @author haley
	 */
	public void delete(int id) {

		this.getSqlMapClientTemplate().delete("message.delete");
	}

	/**
	 * 
	 * description: 更新
	 * 
	 * @author haley
	 */
	public void update(Message message) {

		this.getSqlMapClientTemplate().update("message.update", message);
	}
}
