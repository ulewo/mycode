package com.lhl.quan.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhl.entity.FriendGroup;

public class FriendGroupDao extends SqlMapClientDaoSupport {

	/**
	 * 
	 * description: 添加友情圈子
	 * @param friendGroup
	 * @throws Exception
	 * @author luohl
	 */
	public void addFriendGroup(FriendGroup friendGroup) throws Exception {

		this.getSqlMapClientTemplate().insert("friendGroup.addFriendGroup", friendGroup);
	}

	/**
	 * 
	 * description: 删除友情圈
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public void deleteFriendGroup(int id) throws Exception {

		this.getSqlMapClientTemplate().delete("friendGroup.deleteFriendGroup", id);
	}

	/**
	 * 
	 * description: 单笔查询友情圈子
	 * @param id
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public FriendGroup getFriendGroup(int id) throws Exception {

		return (FriendGroup) this.getSqlMapClientTemplate().queryForObject("friendGroup.queryFriendGroup", id);
	}

	/**
	 * 
	 * description: 通过群组编号查询友情圈
	 * @param groupNum
	 * @param itemId
	 * @param pageNumber
	 * @param pageSize
	 * @param order     更具加入时间倒序，顺序排列
	 * @return
	 * @author luohl
	 */
	public List<FriendGroup> queryFriendGroups(String gid) throws Exception {

		return this.getSqlMapClientTemplate().queryForList("friendGroup.queryFriendGroups", gid);
	}

	/**
	 * 
	 * description: 查询友情圈数量
	 * @param gid
	 * @param status
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public int queryFriendGroupsCount(String gid) throws Exception {

		return (Integer) this.getSqlMapClientTemplate().queryForObject("friendGroup.queryFriendGroupsCount", gid);
	}

}
