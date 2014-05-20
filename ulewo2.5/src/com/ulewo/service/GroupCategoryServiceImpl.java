package com.ulewo.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.MaxLengthEnums;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.GroupCategoryMapper;
import com.ulewo.model.GroupCategory;
import com.ulewo.model.SessionUser;
import com.ulewo.util.StringUtils;

@Service("groupCategoryService")
public class GroupCategoryServiceImpl implements GroupCategoryService {
	@Resource
	private GroupCategoryMapper<GroupCategory> groupCategoryMapper;

	@Override
	public GroupCategory findCategoryById(Map<String, String> map, SessionUser sessionUser) throws BusinessException {
		String groupCategoryId = map.get("groupCategoryId");
		if (!StringUtils.isNumber(groupCategoryId)) {
			throw new BusinessException("参数错误");
		}
		return this.groupCategoryMapper.selectBaseInfo(map);
	}

	@Override
	public List<GroupCategory> selectGroupCategoryList(Map<String, String> map) throws BusinessException {
		return this.groupCategoryMapper.selectBaseInfoList(map, null);
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	@Override
	public void saveCategory(Map<String, String> map) throws BusinessException {
		try {
			String insertRows = map.get("inserted");
			String updateRows = map.get("updated");
			String deleteRows = map.get("deleted");
			JSONArray insertArray = JSONArray.fromObject(URLDecoder.decode(insertRows, "UTF-8"));
			JSONArray updateArray = JSONArray.fromObject(URLDecoder.decode(updateRows, "UTF-8"));
			JSONArray deleteArray = JSONArray.fromObject(URLDecoder.decode(deleteRows, "UTF-8"));
			List<GroupCategory> insertList = JSONArray.toList(insertArray, new GroupCategory(), new JsonConfig());
			List<GroupCategory> updateList = JSONArray.toList(updateArray, new GroupCategory(), new JsonConfig());
			List<GroupCategory> deleteList = JSONArray.toList(deleteArray, new GroupCategory(), new JsonConfig());
			addCategory(insertList);
			updateCategory(updateList);
			deleteCategory(deleteList);
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException("参数错误!");
		}
	}

	private void addCategory(List<GroupCategory> insertList) throws BusinessException {
		for (GroupCategory category : insertList) {
			if (StringUtils.isEmpty(category.getName())
					|| category.getName().length() > MaxLengthEnums.MAXLENGTH50.getLength()) {
				throw new BusinessException("分类名不能超过100字符");
			}
			if (category.getPid() == null) {
				category.setPid(0);
			}
			this.groupCategoryMapper.insert(category);
		}
	}

	private void updateCategory(List<GroupCategory> updateList) throws BusinessException {
		for (GroupCategory category : updateList) {
			if (StringUtils.isEmpty(category.getName())
					|| category.getName().length() > MaxLengthEnums.MAXLENGTH50.getLength()) {
				throw new BusinessException("分类名不能超过50字符");
			}
			this.groupCategoryMapper.updateSelective(category);
		}
	}

	private void deleteCategory(List<GroupCategory> deleteList) throws BusinessException {
		for (GroupCategory category : deleteList) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("groupCategoryId", category.getGroupCategoryId().toString());
			groupCategoryMapper.delete(param);
		}
	}
}
