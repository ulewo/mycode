package com.lhl.admin.action;

import java.util.ArrayList;
import java.util.List;

import com.lhl.admin.service.AdminGroupService;
import com.lhl.common.action.BaseAction;
import com.lhl.config.ErrMsgConfig;
import com.lhl.entity.Group;
import com.lhl.exception.BaseException;
import com.lhl.util.Constant;
import com.lhl.util.Pagination;

/**
 * @Title:
 * @Description:
 * @author 35sz
 * @date 2012-5-23
 * @version V1.0
 */
public class AdminGroupAction extends BaseAction {
	private String id;

	private String[] ids;

	private String keyWord;

	private int pageTotal;

	private int page;

	private List<Group> groupList = new ArrayList<Group>();

	private String errMsg;

	private String orderInfo;

	private String isValid;

	private AdminGroupService adminGroupService;

	/**
	 * 
	 * description: 所有组
	 * 
	 * @return
	 * @author 35sz
	 */
	public String queryGroups() {

		try {
			int countNumber = adminGroupService.queryGroupsCount(keyWord,
					Constant.ISVALIDY);
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
			groupList = adminGroupService.queryGroups(keyWord,
					Constant.ISVALIDY, orderInfo, noStart, pageSize);
		} catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 推荐组
	 * 
	 * @return
	 * @author 35sz
	 */
	public String validGroup() {

		try {
			Group group = new Group();
			group.setId(id);
			group.setIsValid(isValid);
			adminGroupService.validGroup(group);
		} catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * description: 删除组
	 * 
	 * @return
	 * @author 35sz
	 */
	public String deleteGroup() {

		try {
			adminGroupService.deleteGroups(ids);
		} catch (BaseException e) {
			errMsg = ErrMsgConfig.getErrMsg(e.getCode());
			e.printStackTrace();
			return ERROR;
		} catch (Exception e) {
			errMsg = ErrMsgConfig.getErrMsg(10000);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public int getPageTotal() {

		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {

		this.pageTotal = pageTotal;
	}

	public int getPage() {

		return page;
	}

	public void setPage(int page) {

		this.page = page;
	}

	public List<Group> getGroupList() {

		return groupList;
	}

	public void setId(String id) {

		this.id = id;
	}

	public void setIds(String[] ids) {

		this.ids = ids;
	}

	public void setKeyWord(String keyWord) {

		this.keyWord = keyWord;
	}

	public void setErrMsg(String errMsg) {

		this.errMsg = errMsg;
	}

	public void setAdminGroupService(AdminGroupService adminGroupService) {

		this.adminGroupService = adminGroupService;
	}

}
