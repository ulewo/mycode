package com.ulewo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ulewo.entity.Member;

@Component
public class MemberDao extends BaseDao {

	/**
	 * 
	 * description: 多笔查询
	 * 
	 * @return
	 * @author haley
	 */
	@SuppressWarnings("unchecked")
	public List<Member> queryList() {

		return (List<Member>) this.getSqlMapClientTemplate().queryForList(
				"member.selectAll");
	}

	/**
	 * 
	 * description: 单笔查询
	 * 
	 * @return
	 * @author haley
	 */
	public Member findMember(int id) {

		return (Member) this.getSqlMapClientTemplate().queryForObject(
				"member.selectById", id);
	}

	/**
	 * 
	 * description: 删除
	 * 
	 * @param id
	 * @author haley
	 */
	public void delete(int id) {

		this.getSqlMapClientTemplate().delete("member.delete");
	}

	/**
	 * 
	 * description: 更新
	 * 
	 * @author haley
	 */
	public void update(Member member) {

		this.getSqlMapClientTemplate().update("member.update", member);
	}

	public void add(Member member) {

	}
}
