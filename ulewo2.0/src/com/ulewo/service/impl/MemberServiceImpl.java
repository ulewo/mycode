package com.ulewo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.MemberDao;
import com.ulewo.entity.Member;
import com.ulewo.service.MemberService;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDao memberDao;

	public void setMessageDao(MemberDao memberDao) {

		this.memberDao = memberDao;
	}

	@Override
	public List<Member> queryList() {

		return memberDao.queryList();
	}

	@Override
	public Member queryMessage(int id) {

		return memberDao.findMember(id);
	}

	@Override
	public void delete(int id) {

		memberDao.delete(id);
	}

	@Override
	public void update(Member member) {

		memberDao.update(member);
	}

	@Override
	public void addMember(Member member) {
		// TODO 注册新用户

	}
}
