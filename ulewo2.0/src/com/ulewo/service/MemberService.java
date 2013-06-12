package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.Member;
import com.ulewo.enums.MemberStatus;
import com.ulewo.enums.QueryOrder;
import com.ulewo.util.PaginationResult;

/**
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
public interface MemberService {
	public void addMember(Member member);

	/**
	 * 删除成员
	 * 
	 * @param id
	 */
	public void deleteMember(int[] id);

	/**
	 * 更新成员
	 * 
	 * @param member
	 */
	public void updateMember(Member member);

	/**
	 * 
	 * description: 接受成员
	 * 
	 * @param id
	 *            @
	 * @author luohl
	 */
	public void acceptMember(int[] id);

	/**
	 * 单笔查询成员 description: 函数的目的/功能
	 * 
	 * @param id
	 * @return @
	 * @author luohl
	 */
	public Member getMember(int id);;

	/**
	 * 
	 * description: 通过群组编号查询所有成员 根据加入时间倒叙，顺序排列
	 * 
	 * @param status
	 *            TODO
	 * @param order
	 *            根据加入时间倒序，顺序排列
	 * @param groupNum
	 * @param itemId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author luohl
	 */
	public PaginationResult queryMembers(String gid, MemberStatus memberStatus,
			QueryOrder queryOrder, int page, int pageSize);

	/**
	 * 首页查询成员
	 * 
	 * @param gid
	 * @param memberStatus
	 * @param queryOrder
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List<Member> queryMembersIndex(String gid,
			MemberStatus memberStatus, QueryOrder queryOrder, int offset,
			int pageSize);

	public List<Member> queryMembersActiveIndex(String gid,
			MemberStatus memberStatus, int offset, int pageSize);

	/**
	 * 查询成员数
	 * 
	 * @param status
	 *            TODO
	 * @param groupNum
	 * @return
	 */
	public int queryMemberCount(String gid, MemberStatus memberStatus);

	/**
	 * 根据成员活跃度，即发帖数量 排序
	 * 
	 * @param gid
	 * @param offset
	 * @param total
	 * @return
	 */
	public List<Member> queryActiveMembers(String gid, int offset, int total);

}
