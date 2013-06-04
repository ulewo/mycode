package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.Member;
import com.ulewo.enums.MemberGrade;
import com.ulewo.util.PaginationResult;

public interface MemberServicebat2 {

	/**
	 * 
	 * description: 通过圈子id查询成员
	 * @param offset
	 * @param total
	 * @param gid
	 * @return
	 * @author luohl
	 */
	public PaginationResult queryMembersByGid(int offset, int total, String gid, MemberGrade memberGrade);

	/**
	 * 
	 * description: 查询圈子成员数
	 * @param gid
	 * @return
	 * @author luohl
	 */
	public int queryMemberTotalByGid(String gid, MemberGrade memberGrade);

	/**
	 * 
	 * description: 根据等级查询成员
	 * @param grade
	 * @return
	 * @author luohl
	 */
	public List<Member> queryMemberByGrade(int grade);

	/**
	 * 
	 * description: 增加成员
	 * @param member
	 * @author luohl
	 */
	public void addMember(Member member);

	/**
	 * 
	 * description: 更新成员
	 * @param member
	 * @author luohl
	 */
	public void updateMember(Member member);

	/**
	 * 
	 * description: 删除成员
	 * @param id
	 * @author luohl
	 */
	public void deleteMember(int id);

}
