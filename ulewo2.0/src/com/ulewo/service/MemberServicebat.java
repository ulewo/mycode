package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.Member;
import com.ulewo.enums.MemberGrade;

public interface MemberServicebat {
	public List<Member> queryList();

	public List<Member> queryMemberByGrade(String gid, MemberGrade memberGrade);

	public Member queryMember(int id);

	public void delete(int id);

	public void update(Member member);

	public void addMember(Member member);
}
