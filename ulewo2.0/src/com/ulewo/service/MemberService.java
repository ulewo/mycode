package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.Member;

/** 
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
*/
public interface MemberService {
	public void addMember(Member member) throws Exception;

	/**
	 * 删除成员
	 * @param id
	 */
	public void deleteMember(int[] id) throws Exception;

	/**
	 * 更新成员
	 * @param member
	 */
	public void updateMember(Member member) throws Exception;

	/**
	 * 
	 * description: 接受成员
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public void acceptMember(int[] id) throws Exception;

	/**
	 * 
	 * description: 设为管理员
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public void set2Admin(int[] id) throws Exception;

	/**
	 * 
	 * description: 取消管理员
	 * @param id
	 * @throws Exception
	 * @author luohl
	 */
	public void cancelAdmin(int[] id) throws Exception;

	/**
	 * 单笔查询成员
	 * description: 函数的目的/功能
	 * @param id
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public Member getMember(int id) throws Exception;;

	/**
	 * 
	 * description: 通过群组编号查询所有成员  根据加入时间倒叙，顺序排列
	 * @param status TODO
	 * @param order     根据加入时间倒序，顺序排列
	 * @param groupNum
	 * @param itemId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author luohl
	 */
	public List<Member> queryMembers(String gid, String status, String order, int offset, int total) throws Exception;

	/**
	 * 查询成员数
	 * @param status TODO
	 * @param groupNum
	 * @return
	 */
	public int queryMemberCount(String gid, String status) throws Exception;

	/**
	 * 根据成员活跃度，即发帖数量   排序 
	 * @param gid
	 * @param offset
	 * @param total
	 * @return
	 */
	public List<Member> queryActiveMembers(String gid, int offset, int total) throws Exception;

	/**
	 * 查询普通成员   已经审批和待审批的
	 * @param gid
	 * @param offset TODO
	 * @param offset
	 * @param total TODO
	 * @param total
	 * @return
	 */
	public List<Member> queryComMembers(String gid, int offset, int total) throws Exception;

	/**
	 * 
	 * description: 查询普通成员数量
	 * @param gid
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public int queryComMemberCount(String gid) throws Exception;

	/**
	 * 
	 * description: 查询圈主
	 * @param gid
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public Member queryAdmin(String gid) throws Exception;

	/**
	 * description: 查询管理员
	 * @param gid
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public List<Member> queryAdmins(String gid) throws Exception;

	/**
	 * 
	 * description: 查询所有管理员
	 * @return
	 * @throws Exception
	 * @author 35sz
	 */
	public List<Member> queryAllAdmins() throws Exception;

	/***
	 * 
	 * description: 判断是否是成员
	 * @param gid
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author 35sz
	 */
	public boolean isMember(String gid, String userId) throws Exception;

	public Member getMember(String gid, String userId) throws Exception;

	public boolean isAdmin(String gid, String curUserId, String type);
}
