package com.lhl.quan.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.lhl.common.action.BaseAction;
import com.lhl.config.ErrMsgConfig;
import com.lhl.entity.Group;
import com.lhl.entity.User;
import com.lhl.exception.BaseException;
import com.lhl.quan.service.GroupService;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;

public class ManageGroupAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private GroupService groupService;

	private int countNumber;

	private int groupCount;

	private int pageTotal;

	private int page;

	Group group;

	private String gid;

	private String groupName;

	private String groupDesc;

	private String joinPerm;

	private String postPerm;

	private String groupIcon;

	private String groupHeadIcon;

	private String errMsg;

	private List<Group> list = new ArrayList<Group>();

	/**
	 * 编辑群组
	 * @return
	 */
	public String groupManage() {

		try {
			User sessionUser = (User) getSession().getAttribute("user");
			int grade = CheckRole.getMemberGrade(gid, sessionUser.getUserId());
			if (Constant.grade1 == grade || Constant.grade2 == grade
					|| Constant.SUPERADMIN.equals(sessionUser.getUserId())) {
				group = groupService.queryGorup(gid);
			}
			else {
				errMsg = ErrMsgConfig.getErrMsg(10002);
				return ERROR;
			}
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 更新群信息
	 * @return
	 */
	public String updateGroup() {

		try {
			User sessionUser = (User) getSession().getAttribute("user");
			int grade = CheckRole.getMemberGrade(gid, sessionUser.getUserId());
			if (Constant.grade1 == grade || Constant.grade2 == grade
					|| Constant.SUPERADMIN.equals(sessionUser.getUserId())) {
				group = groupService.queryGorup(gid);
				group.setGroupName(groupName);
				group.setGroupDesc(groupDesc);
				group.setJoinPerm(joinPerm);
				group.setPostPerm(postPerm);
				groupService.updateGroup(group);
			}
			else {
				errMsg = ErrMsgConfig.getErrMsg(10002);
				return ERROR;
			}
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 编辑群logo
	 * @return
	 */
	public String getLogo() {

		try {
			User sessionUser = (User) getSession().getAttribute("user");
			int grade = CheckRole.getMemberGrade(gid, sessionUser.getUserId());
			if (Constant.grade1 == grade || Constant.grade2 == grade
					|| Constant.SUPERADMIN.equals(sessionUser.getUserId())) {
				group = groupService.queryGorup(gid);
			}
			else {
				errMsg = ErrMsgConfig.getErrMsg(10002);
				return ERROR;
			}
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 更新群logo
	 * @return
	 */
	public void updateLogo() {

		String result = "ok";
		try {
			User sessionUser = (User) getSession().getAttribute("user");
			int grade = CheckRole.getMemberGrade(gid, sessionUser.getUserId());
			if (Constant.grade1 == grade || Constant.grade2 == grade
					|| Constant.SUPERADMIN.equals(sessionUser.getUserId())) {
				group = groupService.queryGorup(gid);
				group.setGroupIcon(groupIcon);
				groupService.updateGroup(group);
			}
			else {
				result = "error";
			}
		}
		catch (Exception e) {
			result = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 编辑群顶部图片
	 * @return
	 */
	public String getHeadImag() {

		try {
			User sessionUser = (User) getSession().getAttribute("user");
			int grade = CheckRole.getMemberGrade(gid, sessionUser.getUserId());
			if (Constant.grade1 == grade || Constant.grade2 == grade
					|| Constant.SUPERADMIN.equals(sessionUser.getUserId())) {
				group = groupService.queryGorup(gid);
			}
			else {
				errMsg = ErrMsgConfig.getErrMsg(10002);
				return ERROR;
			}
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 更新群顶部图片
	 * @return
	 */
	public void updateHeadImag() {

		String result = "ok";
		try {
			User sessionUser = (User) getSession().getAttribute("user");
			int grade = CheckRole.getMemberGrade(gid, sessionUser.getUserId());
			if (Constant.grade1 == grade || Constant.grade2 == grade
					|| Constant.SUPERADMIN.equals(sessionUser.getUserId())) {
				group = groupService.queryGorup(gid);
				group.setGroupHeadIcon(groupHeadIcon);
				groupService.updateGroup(group);
			}
			else {
				result = "error";
			}
		}
		catch (Exception e) {
			result = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		getOut().print(String.valueOf(obj));
	}

	/**
	 * 
	 * description: 加入群
	 * @return
	 * @author luohl
	 */
	public String addGroup() {

		return null;
	}

	/**
	 * 
	 * description: 解散群组
	 * @return
	 * @author luohl
	 */
	public String dismissGroup() {

		return null;
	}

	/**
	 * 
	 * description: 加入的群组
	 * @return
	 * @author luohl
	 */
	public String joinedGroups() {

		return null;
	}

	/**
	 * 
	 * description: 退出群
	 * @return
	 * @author luohl
	 */
	public String quitGroup() {

		return null;
	}

	/**
	 * 
	 * description: 查询群成员
	 * @return
	 * @author luohl
	 */
	public String members() {

		return null;
	}

	public String groups() {

		try {
			User sessionUser = (User) getSession().getAttribute("user");
			int grade = CheckRole.getMemberGrade(gid, sessionUser.getUserId());
			if (Constant.grade1 == grade || Constant.grade2 == grade
					|| Constant.SUPERADMIN.equals(sessionUser.getUserId())) {
				countNumber = groupService.queryGroupsCount();
				Pagination.setPageSize(10);
				int pageSize = Pagination.getPageSize();
				pageTotal = Pagination.getPageTotal(countNumber);
				if (page > pageTotal) {
					page = pageTotal;
				}
				if (page < 1) {
					page = 1;
				}
				int noStart = (page - 1) * pageSize;
				list = groupService.queryGroupsOderArticleCount(noStart, pageSize);
			}
			else {
				errMsg = ErrMsgConfig.getErrMsg(10002);
				return ERROR;
			}
		}
		catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e) {
			e.printStackTrace();
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

	public Group getGroup() {

		return group;
	}

	public String getGid() {

		return gid;
	}

	public void setGid(String gid) {

		this.gid = gid;
	}

	public void setGroupName(String groupName) {

		this.groupName = groupName;
	}

	public void setJoinPerm(String joinPerm) {

		this.joinPerm = joinPerm;
	}

	public void setGroupDesc(String groupDesc) {

		this.groupDesc = groupDesc;
	}

	public void setPostPerm(String postPerm) {

		this.postPerm = postPerm;
	}

	public void setGroupIcon(String groupIcon) {

		this.groupIcon = groupIcon;
	}

	public void setGroupHeadIcon(String groupHeadIcon) {

		this.groupHeadIcon = groupHeadIcon;
	}

	public String getErrMsg() {

		return errMsg;
	}

}
