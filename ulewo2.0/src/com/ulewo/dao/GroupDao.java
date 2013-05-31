package com.ulewo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ulewo.entity.Group;
import com.ulewo.util.Constant;

public class GroupDao extends SqlMapClientDaoSupport {

	/**
	 * 获取最大的id
	 * @return
	 * @throws Exception
	 */
	public int getMaxGId() throws Exception {

		Object obj = getSqlMapClientTemplate().queryForObject("group.getMaxGId");
		if (null == obj) {
			return 10000;
		}
		else {
			return (Integer) obj;
		}

	}

	/**
	 * 创建群组
	 * @param group
	 */
	public void createGroup(Group group) throws Exception {

		getSqlMapClientTemplate().insert("group.createGroup", group);
	}

	/**
	 * 获取群组信息
	 * @param gid
	 * @return
	 */
	public Group getGroup(String gid) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("gid", gid);
		return (Group) getSqlMapClientTemplate().queryForObject("group.getGroup", parmMap);
	}

	/**
	 * 更新群组(全更新)
	 * @param group
	 */
	public void updateGroup(Group group) throws Exception {

		getSqlMapClientTemplate().update("group.updateGroup", group);
	}

	/**
	 * 更新群组非空信息
	 * @param group
	 */
	public void updateGroupSelective(Group group) throws Exception {

		getSqlMapClientTemplate().update("group.updateGroup_selective", group);
	}

	/**
	 * 
	 * description: 查询所有群组，按照文章数排序
	 * @param groupNum
	 * @param itemId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author luohl
	 */
	public List<Group> queryGroupsByArticleCount(int offset, int total) {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("group.queryGroupsByArticleCount", parmMap);
	}

	/**
	 *  查询所有群组，根据成员数排序
	 * @param isvalid
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Group> queryGroupsByMemberCount(int offset, int total) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("group.queryGroupsByMemberCount", parmMap);
	}

	/**
	 * 查询所有群组，根据创建时间，访问量
	 * @param orderBy
	 * @param offset
	 * @param total
	 * @return
	 * @throws Exception
	 */
	public List<Group> queryGroups(String orderBy, int offset, int total) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("offset", offset);
		parmMap.put("isValid", Constant.ISVALIDY);
		parmMap.put("total", total);
		parmMap.put("orderBy", orderBy);
		return this.getSqlMapClientTemplate().queryForList("group.queryGroups", parmMap);
	}

	/**
	 * 
	 * description: 创建的群组
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public List<Group> queryCreatedGroups(String userId) throws Exception {

		return this.getSqlMapClientTemplate().queryForList("group.queryCreatedGroups", userId);
	}

	/**
	 * 
	 * description: 参加的群组
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author lhl
	 */
	public List<Group> queryJoinedGroups(String userId) throws Exception {

		return this.getSqlMapClientTemplate().queryForList("group.queryJoinedGroups", userId);
	}

	/**
	 * 查询所有群组数
	 * @return
	 * @throws Exception
	 */
	public int queryGroupsCount() {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("isValid", Constant.ISVALIDY);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("group.queryGroupsCount", parmMap);
	}

	/**
	 * 
	 * description: 查询创建的群组
	 * @param userId
	 * @param offset
	 * @param total
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public List<Group> queryCreateGroupsByAuthorId(String userId, int offset, int total) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userid", userId);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("group.queryCreateGroupsByAuthorId", parmMap);
	}

	/**
	 * 
	 * description: 查询加入的群组
	 * @param userId
	 * @param offset
	 * @param total
	 * @return
	 * @throws Exception
	 * @author luohl
	 */
	public List<Group> queryJoinGroupsByAuthorId(String userId, int offset, int total) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("userid", userId);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		return this.getSqlMapClientTemplate().queryForList("group.queryTopicByReUserId", parmMap);
	}

	/*搜索群组*/
	public List<Group> searchGroup(String keyWord, int offset, int total) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("keyWord", keyWord);
		parmMap.put("offset", offset);
		parmMap.put("total", total);
		parmMap.put("isValid", Constant.ISVALIDY);
		return this.getSqlMapClientTemplate().queryForList("group.queryGroups", parmMap);
	}

	/*搜索群组数*/
	public int searchGroupCount(String keyWord) throws Exception {

		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("keyWord", keyWord);
		parmMap.put("isValid", Constant.ISVALIDY);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("group.queryGroupsCount", parmMap);
	}
}
