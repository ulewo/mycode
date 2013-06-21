package com.ulewo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.GroupDao;
import com.ulewo.dao.MemberDao;
import com.ulewo.dao.UserDao;
import com.ulewo.entity.Group;
import com.ulewo.entity.Member;
import com.ulewo.enums.JoinPerm;
import com.ulewo.enums.MemberStatus;
import com.ulewo.enums.QueryOrder;
import com.ulewo.service.MemberService;
import com.ulewo.util.Constant;
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

	private final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

	public List<Member> queryMembersIndex(String gid, MemberStatus memberStatus, QueryOrder queryOrder, int offset,
			int pageSize) {

		List<Member> list = memberDao.queryMembers(gid, memberStatus, queryOrder, offset, pageSize);
		return list;
	}

	public List<Member> queryMembersActiveIndex(String gid, MemberStatus memberStatus, int offset, int pageSize) {

		return memberDao.queryActiveMembers(gid, memberStatus, offset, pageSize);
	}

	public Map<String, String> existGroup(Member member) {

		Map<String, String> hashMap = new HashMap<String, String>();
		Group group = groupDao.getGroup(member.getGid());
		if (null == group) {
			hashMap.put("result", "fail");
			hashMap.put("message", "请求参数错误");
			return hashMap;
		}
		if (member.getUserId().equals(group.getGroupAuthor())) {
			hashMap.put("result", "fail");
			hashMap.put("message", "你是管理员不能退出");
			return hashMap;
		}
		hashMap.put("result", "success");
		memberDao.deleteMember(member.getGid(), member.getUserId());
		return hashMap;
	}

	public Map<String, String> joinGroup(Member member) {

		Map<String, String> hashMap = new HashMap<String, String>();
		Member resultMember = memberDao.queryMemberByGidAndUserId(member.getGid(), member.getUserId());
		if (null != resultMember) {
			if (Constant.ISMEMBER.equals(resultMember.getIsMember())) {
				hashMap.put("result", "fail");
				hashMap.put("message", "你已经是成员了");
			}
			else {
				hashMap.put("result", "fail");
				hashMap.put("message", "你已经申请加入，请等待管理员的审批");
			}

		}
		else {
			Group group = groupDao.getGroup(member.getGid());
			if (null == group) {
				hashMap.put("result", "fail");
				hashMap.put("message", "请求参数错误");
				return hashMap;
			}
			hashMap.put("result", "success");
			String isMember = Constant.ISMEMBER;
			//判断加入权限
			if (group.getJoinPerm().equals(JoinPerm.ALL.getValue())) {
				hashMap.put("message", "你已经申请加入，请等待管理员的审批");
			}
			else {
				isMember = Constant.NOTMEMBER;
				hashMap.put("message", "你已经申请加入，请等待管理员的审批");
			}
			hashMap.put("memberStatus", isMember);
			member.setIsMember(isMember);
			member.setJoinTime(formate.format(new Date()));
			memberDao.addMember(member);
		}

		return hashMap;
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

	@Override
	public Member queryMemberByGidAndUserId(String gid, String userId) {

		return memberDao.queryMemberByGidAndUserId(gid, userId);
	}

}
