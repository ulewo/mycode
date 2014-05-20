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

import com.ulewo.enums.LengthEnums;
import com.ulewo.enums.MaxLengthEnums;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.TopicCategoryMapper;
import com.ulewo.model.SessionUser;
import com.ulewo.model.TopicCategory;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult4Json;

/**
 * @Title:
 * @Description: 分类业务实现
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
@Service("topicCategoryService")
public class TopicCategoryServiceImpl extends GroupAuthorityService implements TopicCategoryService {

	@Resource
	private TopicCategoryMapper<TopicCategory> topicCategoryMapper;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void saveCategroy(Map<String, String> map, Integer userId) throws BusinessException {
		try {
			String insertRows = map.get("inserted");
			String updateRows = map.get("updated");
			String deleteRows = map.get("deleted");
			String gid = map.get("gid");
			if (StringUtils.isEmpty(gid) || !StringUtils.isNumber(gid)) {
				throw new BusinessException("参数错误!");
			}
			this.checkGroupOpAuthority(Integer.parseInt(gid), userId);
			JSONArray insertArray = JSONArray.fromObject(URLDecoder.decode(insertRows, "UTF-8"));
			JSONArray updateArray = JSONArray.fromObject(URLDecoder.decode(updateRows, "UTF-8"));
			JSONArray deleteArray = JSONArray.fromObject(URLDecoder.decode(deleteRows, "UTF-8"));
			List<TopicCategory> insertList = JSONArray.toList(insertArray, new TopicCategory(), new JsonConfig());
			List<TopicCategory> updateList = JSONArray.toList(updateArray, new TopicCategory(), new JsonConfig());
			List<TopicCategory> deleteList = JSONArray.toList(deleteArray, new TopicCategory(), new JsonConfig());
			deleteCategory(deleteList, userId, gid);
			addCategory(insertList, userId, gid);
			updateCategory(updateList, userId, gid);

		} catch (UnsupportedEncodingException e) {
			throw new BusinessException("参数错误!");
		}

	}

	private void addCategory(List<TopicCategory> insertList, Integer userId, String gid) throws BusinessException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("gid", gid);
		int count = topicCategoryMapper.selectBaseInfoCount(param);
		if (count + insertList.size() > LengthEnums.Length8.getLength()) {
			throw new BusinessException("最多只能添加8个分类");
		}
		for (TopicCategory category : insertList) {
			if (StringUtils.isEmpty(category.getName())
					|| category.getName().length() > MaxLengthEnums.MAXLENGTH50.getLength()) {
				throw new BusinessException("分类名不能超过50字符");
			}
			category.setGid(Integer.parseInt(gid));
			this.topicCategoryMapper.insert(category);
		}
	}

	private void updateCategory(List<TopicCategory> updateList, Integer userId, String gid) throws BusinessException {
		for (TopicCategory category : updateList) {
			if (StringUtils.isEmpty(category.getName())
					|| category.getName().length() > MaxLengthEnums.MAXLENGTH50.getLength()) {
				throw new BusinessException("分类名不能超过50字符");
			}
			if (!gid.equals(String.valueOf(category.getGid()))) {
				throw new BusinessException("参数错误");
			}
			this.topicCategoryMapper.updateSelective(category);
		}
	}

	private void deleteCategory(List<TopicCategory> deleteList, Integer userId, String gid) throws BusinessException {
		for (TopicCategory category : deleteList) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("categoryId", String.valueOf(category.getCategoryId()));
			param.put("gid", gid);
			if (category.getTopicCount() > 0) {
				throw new BusinessException("分类下有文章不能删除");
			}
			if (!gid.equals(String.valueOf(category.getGid()))) {
				throw new BusinessException("参数错误");
			}
			topicCategoryMapper.delete(param);
		}
	}

	public TopicCategory getCategroy(Map<String, String> map) throws BusinessException {
		String gid = map.get("gid");
		String categoryId = map.get("categoryId");
		if (!StringUtils.isNumber(gid) || StringUtils.isEmpty(categoryId) || !StringUtils.isNumber(categoryId)) {
			throw new BusinessException("参数错误!");
		}
		return this.topicCategoryMapper.selectBaseInfo(map);
	}

	@Override
	public void updateCategory(Map<String, String> map, SessionUser sessionUser) throws BusinessException {
		String gid = map.get("gid");
		String name = map.get("name");
		String rang = map.get("rang");
		String categoryId = map.get("categoryId");
		if (!StringUtils.isNumber(gid) || StringUtils.isEmpty(name)
				|| StringUtils.getRealLength(name) > LengthEnums.Length5.getLength() || !StringUtils.isNumber(rang)
				|| StringUtils.isEmpty(categoryId) || !StringUtils.isNumber(categoryId)) {
			throw new BusinessException("参数错误!");
		}
		Integer gid_int = Integer.parseInt(gid);
		this.checkGroupOpAuthority(gid_int, sessionUser.getUserId());
		TopicCategory category = new TopicCategory();
		category.setCategoryId(Integer.parseInt(categoryId));
		category.setGid(gid_int);
		category.setName(name);
		category.setRang(Integer.parseInt(rang));
		this.topicCategoryMapper.updateSelective(category);
		// articleItemDao.update(item);
	}

	@Override
	public void deleteCategroy(Map<String, String> map, SessionUser sessionUser) throws BusinessException {
		String gid = map.get("gid");
		String categoryId = map.get("categoryId");
		if (!StringUtils.isNumber(gid) || StringUtils.isEmpty(categoryId) || !StringUtils.isNumber(categoryId)) {
			throw new BusinessException("参数错误!");
		}
		Integer gid_int = Integer.parseInt(gid);
		this.checkGroupOpAuthority(gid_int, sessionUser.getUserId());
		this.topicCategoryMapper.delete(map);
	}

	@Override
	public List<TopicCategory> queryCategoryAndTopicCount(Map<String, String> map) {
		TopicCategory category = new TopicCategory();
		String gid = map.get("gid");
		category.setGid(Integer.parseInt(gid));
		return this.topicCategoryMapper.selectCategory4ListAndTopicCount(category);
	}

	@Override
	public UlewoPaginationResult4Json<TopicCategory> queryCategory4Json(Map<String, String> map) {
		TopicCategory category = new TopicCategory();
		String gid = map.get("gid");
		category.setGid(Integer.parseInt(gid));
		int count = this.topicCategoryMapper.selectBaseInfoCount(map);
		List<TopicCategory> list = this.topicCategoryMapper.selectCategory4ListAndTopicCount(category);
		UlewoPaginationResult4Json<TopicCategory> result = new UlewoPaginationResult4Json<TopicCategory>();
		result.setRows(list);
		result.setTotal(count);
		return result;
	}
}
