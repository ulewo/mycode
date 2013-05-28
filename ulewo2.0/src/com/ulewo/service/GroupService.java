package com.ulewo.service;

import com.ulewo.entity.Group;
import com.ulewo.entity.PaginationResult;

public interface GroupService {

	/**
	 * 
	 * description: 分页查询所有群组
	 * @param offset
	 * @param total
	 * @return
	 * @author luohl
	 */
	public PaginationResult queryAllGroups(int offset, int total);

	/**
	 * description: 所有群组数量
	 * @return
	 * @author luohl
	 */
	public int queryGroupTotal();

	/**
	 * 
	 * description: 通过圈子ID获取圈子详情
	 * @return
	 * @author luohl
	 */
	public Group queryGroupByGid(String gid);

	/**
	 * 
	 * description: 新增圈子
	 * @param group
	 * @author luohl
	 */
	public boolean addGroup(Group group);

	/**
	 * 
	 * description: 删除圈子
	 * @param gid
	 * @return
	 * @author luohl
	 */
	public boolean deleteGroup(String gid);

}
