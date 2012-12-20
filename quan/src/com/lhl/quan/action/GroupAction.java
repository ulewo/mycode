package com.lhl.quan.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.lhl.common.action.BaseAction;
import com.lhl.config.ErrMsgConfig;
import com.lhl.entity.Article;
import com.lhl.entity.ArticleItem;
import com.lhl.entity.FriendGroup;
import com.lhl.entity.Group;
import com.lhl.entity.Member;
import com.lhl.entity.User;
import com.lhl.exception.BaseException;
import com.lhl.quan.service.ArticleItemService;
import com.lhl.quan.service.ArticleService;
import com.lhl.quan.service.FriendGroupService;
import com.lhl.quan.service.GroupService;
import com.lhl.quan.service.MemberService;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;

public class GroupAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private GroupService groupService;

	private ArticleService articleService;

	private ArticleItemService articleItemService;

	private MemberService memberService;

	private FriendGroupService friendGroupService;

	private String gid;

	private String groupName;

	private String groupDesc;

	private String groupIcon;

	private String groupHeadIcon;

	private String joinPerm;

	private String postPerm;

	private int countNumber;

	private int groupCount;

	private int pageTotal;

	private int page;

	private Group group;

	private int showType; // 菜单选中

	private List<Article> articleList = new ArrayList<Article>();

	private List<ArticleItem> itemList = new ArrayList<ArticleItem>();

	private List<Member> memberList = new ArrayList<Member>();

	private List<Group> list = new ArrayList<Group>();

	private String showManageGroup = "N";

	String errMsg = "";

	private int todayCount = 0;

	// 圈主
	private Member admin;

	// 管理员
	private List<Member> adminList = new ArrayList<Member>();

	// 最新成员
	private List<Member> newsMembers = new ArrayList<Member>();

	// 最活跃成员
	private List<Member> activeMembers = new ArrayList<Member>();

	// 所有成员
	private List<Member> members = new ArrayList<Member>();

	// 友情圈子
	private List<FriendGroup> friendGroupList = new ArrayList<FriendGroup>();

	/**
	 * 
	 * description: 创建群组
	 * 
	 * @return
	 * @author luohl
	 */
	public String createGroup() {

		try {
			Object sessionObj = getSession().getAttribute("user");
			if (sessionObj != null) {
				User sessionUser = (User) sessionObj;
				Group group = new Group();
				group.setGroupName(groupName);
				group.setGroupAuthor(sessionUser.getUserId());
				group.setGroupDesc(groupDesc);
				group.setGroupIcon(groupIcon);
				group.setGroupHeadIcon(groupHeadIcon);
				group.setJoinPerm(joinPerm);
				group.setPostPerm(postPerm);
				gid = groupService.createGroup(group);
			}
			else {
				errMsg = ErrMsgConfig.getErrMsg(10000);
				return ERROR;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			errMsg = ErrMsgConfig.getErrMsg(10000);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 群首页
	 * 
	 * @return
	 * @author luohl
	 */
	public String group() {

		try {
			Object obj = getSession().getAttribute("user");
			if (null == obj) {
				showManageGroup = "N";
			}
			else {
				User sessionUser = (User) obj;
				int grade = CheckRole.getMemberGrade(gid, sessionUser.getUserId());
				if (Constant.grade1 == grade || Constant.grade2 == grade
						|| Constant.SUPERADMIN.equals(sessionUser.getUserId())) {
					showManageGroup = "Y";
				}
				else {
					showManageGroup = "N";
				}
			}

			group = groupService.queryGroupExtInfo(gid);
			if (null == group) {
				return ERROR;
			}
			articleList = articleService.queryTopicOrderByGradeAndLastReTime(gid, 0, Constant.ISVALIDY, 0, 30);
			todayCount = articleService.queryTopicCountByTime(gid);
			itemList = articleItemService.queryItemByGid(gid);
			admin = memberService.queryAdmin(gid);
			adminList = memberService.queryAdmins(gid);
			newsMembers = memberService.queryMembers(gid, Constant.ISVALIDY, "desc", 0, 6);
			activeMembers = memberService.queryActiveMembers(gid, 0, 6);
			friendGroupList = friendGroupService.queryFriendGroups(gid);
			showType = 1;
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		// TODO 查询群信息
		// TODO 查询群组最新的文章
		// TODO 查询群最新加入成员
		return SUCCESS;
	}

	/**
	 * 
	 * description: 加入群
	 * 
	 * @return
	 * @author luohl
	 */
	public void addGroup() {

		String msg = "";
		JSONObject obj = new JSONObject();
		try {
			Object userObj = getSession().getAttribute("user");
			if (null == userObj) {
				msg = "nologin";
			}
			else {
				User sessionUser = (User) userObj;
				Member member = memberService.getMember(gid, sessionUser.getUserId());
				if (null != member && Constant.ISVALIDY.equals(member.getIsMember())) {
					msg = "isMemeber";
				}
				else if (null != member && Constant.ISVALIDN.equals(member.getIsMember())) {
					msg = "addNeedCheck";
				}
				else {
					Group group = groupService.queryGorup(gid);
					Member adMember = new Member();
					adMember.setGid(gid);
					adMember.setUserId(sessionUser.getUserId());
					if (Constant.ISVALIDY.equals(group.getJoinPerm())) {
						adMember.setIsMember(Constant.ISVALIDY);
						msg = "addOk";
					}
					else {
						adMember.setIsMember(Constant.ISVALIDN);
						msg = "addNeedCheck";
					}
					memberService.addMember(adMember);
				}
			}
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
		}
		catch (Exception e) {
		}
		obj.put("msg", msg);
		getOut().print(String.valueOf(obj));
	}

	public void checkUserPerm() {

		String msg = "";
		JSONObject obj = new JSONObject();
		try {
			Object userObj = getSession().getAttribute("user");
			if (null == userObj) {
				msg = "nologin";
			}
			else {
				User sessionUser = (User) userObj;
				Member member = memberService.getMember(gid, sessionUser.getUserId());
				//是成员
				if (null != member && Constant.ISVALIDY.equals(member.getIsMember())) {
					msg = "havePerm";
				}
				else {
					Group group = groupService.queryGorup(gid);
					if (Constant.ISVALIDY.equals(group.getJoinPerm())) {
						msg = "havePerm";
					}
					else {
						msg = "noPerm";
					}
				}
			}
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
		}
		catch (Exception e) {
		}
		obj.put("msg", msg);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 加入的群组
	 * 
	 * @return
	 * @author luohl
	 */
	public String joinedGroups() {

		return null;
	}

	/**
	 * 
	 * description: 退出群
	 * 
	 * @return
	 * @author luohl
	 */
	public String quitGroup() {

		return null;
	}

	/**
	 * 
	 * description: 查询群成员
	 * 
	 * @return
	 * @author luohl
	 */
	public String members() {

		try {
			group = groupService.queryGorup(gid);
			int countNumber = memberService.queryMemberCount(gid, Constant.ISVALIDY);
			Pagination.setPageSize(Constant.pageSize20);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal) {
				page = pageTotal;
			}
			if (page < 1) {
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			memberList = memberService.queryMembers(gid, Constant.ISVALIDY, "asc", noStart, pageSize);
			showType = 3;
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public void setGroupService(GroupService groupService) {

		this.groupService = groupService;
	}

	public int getPageTotal() {

		return pageTotal;
	}

	public List<Group> getList() {

		return list;
	}

	public int getCountNumber() {

		return countNumber;
	}

	public int getPage() {

		return page;
	}

	public void setPage(int page) {

		this.page = page;
	}

	public void setGroupName(String groupName) {

		this.groupName = groupName;
	}

	public void setGroupDesc(String groupDesc) {

		this.groupDesc = groupDesc;
	}

	public void setGroupIcon(String groupIcon) {

		this.groupIcon = groupIcon;
	}

	public void setGroupHeadIcon(String groupHeadIcon) {

		this.groupHeadIcon = groupHeadIcon;
	}

	public void setJoinPerm(String joinPerm) {

		this.joinPerm = joinPerm;
	}

	public void setPostPerm(String postPerm) {

		this.postPerm = postPerm;
	}

	public String getGid() {

		return gid;
	}

	public void setGid(String gid) {

		this.gid = gid;
	}

	public Group getGroup() {

		return group;
	}

	public List<Article> getArticleList() {

		return articleList;
	}

	public void setArticleService(ArticleService articleService) {

		this.articleService = articleService;
	}

	public int getShowType() {

		return showType;
	}

	public void setArticleItemService(ArticleItemService articleItemService) {

		this.articleItemService = articleItemService;
	}

	public List<ArticleItem> getItemList() {

		return itemList;
	}

	public MemberService getMemberService() {

		return memberService;
	}

	public void setMemberService(MemberService memberService) {

		this.memberService = memberService;
	}

	public Member getAdmin() {

		return admin;
	}

	public List<Member> getAdminList() {

		return adminList;
	}

	public List<Member> getNewsMembers() {

		return newsMembers;
	}

	public List<Member> getActiveMembers() {

		return activeMembers;
	}

	public List<FriendGroup> getFriendGroupList() {

		return friendGroupList;
	}

	public void setFriendGroupService(FriendGroupService friendGroupService) {

		this.friendGroupService = friendGroupService;
	}

	public String getErrMsg() {

		return errMsg;
	}

	public String getShowManageGroup() {

		return showManageGroup;
	}

	public List<Member> getMemberList() {

		return memberList;
	}

	public int getTodayCount() {

		return todayCount;
	}

}
