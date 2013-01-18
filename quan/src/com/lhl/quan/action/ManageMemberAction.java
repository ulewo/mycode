package com.lhl.quan.action;

import java.util.List;

import com.lhl.common.action.BaseAction;
import com.lhl.config.ErrMsgConfig;
import com.lhl.entity.Member;
import com.lhl.entity.User;
import com.lhl.exception.BaseException;
import com.lhl.quan.service.MemberService;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;

public class ManageMemberAction extends BaseAction
{
	private static final long serialVersionUID = 1L;

	private MemberService memberService;

	private int id;

	private int[] ids;

	private int[] member;

	private int[] admin;

	private String gid; // 群组ID

	private int page; // 页数

	private int pageTotal; // 总页数

	private int countNumber;

	private List<Member> memberList;

	private List<Member> adminList;

	private int adminCount;

	private String errMsg;

	/**
	 * 
	 * description:待审批成员
	 * 
	 * @return
	 * @author luohl
	 */
	public String applyMember()
	{

		try
		{
			User user = getSessionUser();
			if (user == null || !memberService.isAdmin(gid, user.getUserId(), Constant.ADMIN_TYPE_A))
			{
				return ERROR;
			}
			countNumber = memberService.queryMemberCount(gid, Constant.ISVALIDN);
			Pagination.setPageSize(Constant.pageSize20);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal)
			{
				page = pageTotal;
			}
			if (page < 1)
			{
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			memberList = memberService.queryMembers(gid, Constant.ISVALIDN, "desc", noStart, pageSize);
		}
		catch (BaseException e)
		{
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e)
		{
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 接受成员
	 * 
	 * @return
	 * @author luohl
	 */
	public String acceptMember()
	{

		try
		{
			User user = getSessionUser();
			if (user == null || !memberService.isAdmin(gid, user.getUserId(), Constant.ADMIN_TYPE_A))
			{
				return ERROR;
			}
			memberService.acceptMember(ids);
		}
		catch (BaseException e)
		{
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e)
		{
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 拒绝成员
	 * 
	 * @return
	 * @author luohl
	 */
	public String refuseMember()
	{

		try
		{
			User user = getSessionUser();
			if (user == null || !memberService.isAdmin(gid, user.getUserId(), Constant.ADMIN_TYPE_A))
			{
				return ERROR;
			}
			memberService.deleteMember(ids);
		}
		catch (BaseException e)
		{
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e)
		{
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;

	}

	/**
	 * 
	 * description: 查询成员 管理员和普通成员
	 * 
	 * @return
	 * @author luohl
	 */
	public String manageMember()
	{

		try
		{
			User user = getSessionUser();
			if (user == null || !memberService.isAdmin(gid, user.getUserId(), Constant.ADMIN_TYPE_A))
			{
				return ERROR;
			}
			adminList = memberService.queryAdmins(gid);
			adminCount = adminList.size();
			countNumber = memberService.queryComMemberCount(gid);
			Pagination.setPageSize(Constant.pageSize20);
			int pageSize = Pagination.getPageSize();
			pageTotal = Pagination.getPageTotal(countNumber);
			if (page > pageTotal)
			{
				page = pageTotal;
			}
			if (page < 1)
			{
				page = 1;
			}
			int noStart = (page - 1) * pageSize;
			memberList = memberService.queryComMembers(gid, noStart, pageSize);
		}
		catch (BaseException e)
		{
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e)
		{
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;

	}

	public String deleteMember()
	{

		try
		{
			User user = getSessionUser();
			if (user == null || !memberService.isAdmin(gid, user.getUserId(), Constant.ADMIN_TYPE_S))
			{
				return ERROR;
			}
			memberService.deleteMember(member);
		}
		catch (BaseException e)
		{
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e)
		{
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 设为管理员
	 * 
	 * @return
	 * @author luohl
	 */
	public String set2Admin()
	{

		try
		{
			User user = getSessionUser();
			if (user == null || !memberService.isAdmin(gid, user.getUserId(), Constant.ADMIN_TYPE_S))
			{
				return ERROR;
			}
			memberService.set2Admin(member);
		}
		catch (BaseException e)
		{
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e)
		{
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 取消管理员
	 * 
	 * @return
	 * @author luohl
	 */
	public String cancelAdmin()
	{

		try
		{
			User user = getSessionUser();
			if (user == null || !memberService.isAdmin(gid, user.getUserId(), Constant.ADMIN_TYPE_S))
			{
				return ERROR;
			}
			memberService.cancelAdmin(admin);
		}
		catch (BaseException e)
		{
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		}
		catch (Exception e)
		{
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public int getPage()
	{

		return page;
	}

	public void setPage(int page)
	{

		this.page = page;
	}

	public int getPageTotal()
	{

		return pageTotal;
	}

	public void setId(int id)
	{

		this.id = id;
	}

	public String getGid()
	{

		return gid;
	}

	public void setGid(String gid)
	{

		this.gid = gid;
	}

	public String getErrMsg()
	{

		return errMsg;
	}

	public int getCountNumber()
	{

		return countNumber;
	}

	public List<Member> getMemberList()
	{

		return memberList;
	}

	public void setMemberService(MemberService memberService)
	{

		this.memberService = memberService;
	}

	public List<Member> getAdminList()
	{

		return adminList;
	}

	public void setMember(int[] member)
	{

		this.member = member;
	}

	public void setAdmin(int[] admin)
	{

		this.admin = admin;
	}

	public int getAdminCount()
	{

		return adminCount;
	}

	public void setIds(int[] ids)
	{

		this.ids = ids;
	}

}
