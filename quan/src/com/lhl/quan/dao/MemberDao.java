package com.lhl.quan.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhl.entity.Member;
import com.lhl.util.Constant;

public class MemberDao extends SqlMapClientDaoSupport {

	/**
	 * 添加成员
	 * 
	 * @param member
	 * @throws Exception
	 */
	public void addMember(Member member) throws Exception {

		this.getSqlMapClientTemplate().insert("member.addMember", member);
	}

	/**
	 * 删除成员
	 * 
	 * @param id
	 */
	public void deleteMember(int id) throws Exception {

		this.getSqlMapClientTemplate().delete("member.deleteMember", id);
	}

	/**
	 * 更新成员
	 * 
	 * @param member
	 */
	public void updateMemberSelective(Member member) throws Exception {

		this.getSqlMapClientTemplate().update("member.updateMember_selective", member);
	}

	public Member getMember(int id) throws Exception {

		return (Member) this.getSqlMapClientTemplate().queryForObject("member.queryCount", id);
	}

	/**
	 * 
	 * description: 通过群组编号查询群成员
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
	public List<Member> queryMembers(String gid, String isMember, String grade, String order, int offset, int total)
			throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("ismember", isMember);
		parmMap.put("grade", grade);
		parmMap.put("order", order);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("member.queryMembers", parmMap);
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
	public List<Member> queryActiveMembers(String gid, String status, int offset, int total) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("ismember", status);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("member.queryActiveMembers", parmMap);
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
	public int queryMemberCount(String gid, String ismember, String grade) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("ismember", ismember);
		parmMap.put("grade", grade);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("member.queryCount", parmMap);
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
	public List<Member> queryMembersByGrade(String gid, String status, String grade, int offset, int total)
			throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("grade", grade);
		parmMap.put("ismember", status);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("member.queryMembersByGrade", parmMap);
	}

	/**
	 * 查询群管理员
	 */
	public List<Member> queryAdmins(String gid) throws Exception {

		return this.getSqlMapClientTemplate().queryForList("member.queryAdmins", gid);
	}

	/**
	 * 查询成员userid
	 */

	public List<String> queryMembersIdByGrade(String gid, int grade) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("grade", grade);
		parmMap.put("isMember", Constant.ISVALIDY);
		return this.getSqlMapClientTemplate().queryForList("member.queryMemberUserIdByGrade", parmMap);
	}

	/**
	 * 更具等级查询成员数
	 * 
	 * @param gid
	 * @param grade
	 * @return
	 */
	public int queryMemberCountByGrade(String gid, int grade) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("grade", grade);
		parmMap.put("isMember", Constant.ISVALIDY);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("member.queryCountByGrade", parmMap);
	}

	public List<Member> queryAllAdmins() throws Exception {

		return this.getSqlMapClientTemplate().queryForList("member.queryAllAdmins");
	}

	public Member queryMemberByGidAndUserId(String gid, String userId) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		parmMap.put("userId", userId);
		return (Member) this.getSqlMapClientTemplate().queryForObject("member.queryMemberByGidAndUserId", parmMap);
	}
}
