package com.ulewo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.GroupDao;
import com.ulewo.dao.MemberDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.Member;
import com.ulewo.enums.MemberStatus;
import com.ulewo.enums.QueryOrder;
import com.ulewo.service.MemberService;
import com.ulewo.util.Pagination;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

/**
 * @Title:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
@Service("memberService")
public class MemberServiceImp implements MemberService {
	@Autowired
	private MemberDao memberDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private GroupDao groupDao;

	@Override
	public void addMember(Member member) {

		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMember(int[] id) {

		// TODO Auto-generated method stub

	}

	@Override
	public void updateMember(Member member) {

		// TODO Auto-generated method stub

	}

	@Override
	public void acceptMember(int[] id) {

		// TODO Auto-generated method stub

	}

	@Override
	public Member getMember(int id) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult queryMembers(String gid, MemberStatus memberStatus, QueryOrder queryOrder, int page,
			int pageSize) {

		int count = memberDao.queryMemberCount(gid, memberStatus);
		Pagination pagination = new Pagination(page, count, pageSize);
		pagination.action();
		List<Member> list = memberDao.queryMembers(gid, memberStatus, queryOrder, pagination.getOffSet(), pageSize);
		for (Member member : list) {
			member.setJoinTime(StringUtils.friendly_time(member.getJoinTime()));
		}
		PaginationResult result = new PaginationResult(pagination.getPage(), pagination.getPageTotal(), count, list);
		return result;
	}

	@Override
	public int queryMemberCount(String gid, MemberStatus memberStatus) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Member> queryActiveMembers(String gid, int offset, int total) {

		// TODO Auto-generated method stub
		return null;
	}

}
