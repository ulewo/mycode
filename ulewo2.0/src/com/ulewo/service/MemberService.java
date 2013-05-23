package com.ulewo.service;

import java.util.List;

import com.ulewo.entity.Member;

public interface MemberService {
	public List<Member> queryList();

	public Member queryMessage(int id);

	public void delete(int id);

	public void update(Member member);

	public void addMember(Member member);
}
