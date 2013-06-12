package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ulewo.entity.Member;
import com.ulewo.enums.MemberStatus;
import com.ulewo.enums.QueryOrder;
import com.ulewo.util.Constant;

@Component
public class MemberDao extends BaseDao {

	/**
	 * 添加成员
	 * 
	 * @param member
	 *            @
	 */
	public void addMember(Member member) {

		this.getSqlMapClientTemplate().insert("member.addMember", member);
	}

	/**
	 * 删除成员
	 * 
	 * @param id
	 */
	public void deleteMember(int id) {

		this.getSqlMapClientTemplate().delete("member.deleteMember", id);
	}

	/**
	 * 更新成员
	 * 
	 * @param member
	 */
	public void updateMemberSelective(Member member) {

		this.getSqlMapClientTemplate().update("member.updateMember_selective",
				member);
	}

	public Member getMember(int id) {

		return (Member) this.getSqlMapClientTemplate().queryForObject(
				"member.getMember", id);
	}

	/**
	 * 
	 * description: 通过群组编号查询群成员
	 * 
	 * @param queryOrder
	 *            TODO
	 * @param groupNum
	 * @param itemId
	 * @param pageNumber
	 * @param pageSize
	 * 
	 * @return
	 * @author luohl
	 */
	public List<Member> queryMembers(String gid, MemberStatus memberStatus,
			QueryOrder queryOrder, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("ismember", memberStatus.getValue());
		parmMap.put("order", queryOrder.getValue());
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList(
				"member.queryMembers", parmMap);
	}

	/**
	 * 查询成员数
	 * 
	 * @param status
	 * @param grade
	 *            TODO
	 * @param groupNum
	 * @return
	 */
	public int queryMemberCount(String gid, MemberStatus memberStatus) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("ismember", memberStatus.getValue());
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"member.queryMemberCount", parmMap);
	}

	/**
	 * 根据成员活跃度，即发帖数量 排序
	 * 
	 * @param gid
	 * @param status
	 * @param offset
	 * @param total
	 * @return
	 */
	public List<Member> queryActiveMembers(String gid,
			MemberStatus memberStatus, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("ismember", memberStatus.getValue());
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList(
				"member.queryActiveMembers", parmMap);
	}

	/**
	 * 更具等级查询群成员
	 * 
	 * @param gid
	 * @param status
	 *            TODO
	 * @param status
	 * @param offset
	 *            TODO
	 * @param total
	 *            TODO
	 * @param offset
	 * @param total
	 * @return
	 */
	public List<Member> queryMembersByGrade(String gid, String status,
			String grade, int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("grade", grade);
		parmMap.put("ismember", status);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList(
				"member.queryMembersByGrade", parmMap);
	}

	/**
	 * 查询群管理员
	 */
	public List<Member> queryAdmins(String gid) {

		return this.getSqlMapClientTemplate().queryForList(
				"member.queryAdmins", gid);
	}

	/**
	 * 查询成员userid
	 */

	public List<String> queryMembersIdByGrade(String gid, int grade) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("grade", grade);
		parmMap.put("isMember", Constant.ISVALIDY);
		return this.getSqlMapClientTemplate().queryForList(
				"member.queryMemberUserIdByGrade", parmMap);
	}

	/**
	 * 更具等级查询成员数
	 * 
	 * @param gid
	 * @param grade
	 * @return
	 */
	public int queryMemberCountByGrade(String gid, int grade) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("grade", grade);
		parmMap.put("isMember", Constant.ISVALIDY);
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"member.queryCountByGrade", parmMap);
	}

	public List<Member> queryAllAdmins() {

		return this.getSqlMapClientTemplate().queryForList(
				"member.queryAllAdmins");
	}

	public Member queryMemberByGidAndUserId(String gid, String userId) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("userId", userId);
		return (Member) this.getSqlMapClientTemplate().queryForObject(
				"member.queryMemberByGidAndUserId", parmMap);
	}
}
