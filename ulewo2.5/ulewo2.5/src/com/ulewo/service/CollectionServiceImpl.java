package com.ulewo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.CollectionTypeEnums;
import com.ulewo.enums.PageSize;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.CollectionMapper;
import com.ulewo.model.Collection;
import com.ulewo.model.SessionUser;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;

@Service("collectionService")
public class CollectionServiceImpl implements CollectionService {

	@Resource
	private CollectionMapper<Collection> collectionMapper;

	public void addCollection(Map<String, String> map, SessionUser sessionUser) throws BusinessException {
		String articleId = map.get("articleId");
		String type = map.get("type");
		Integer userId = sessionUser.getUserId();
		if (!StringUtils.isNumber(articleId)) {
			throw new BusinessException("参数错误");
		}
		int articleId_int = Integer.parseInt(articleId);
		if (!CollectionTypeEnums.TOPIC.getValue().equals(type) && !CollectionTypeEnums.BLOG.getValue().equals(type)) {
			throw new BusinessException("参数错误");
		}

		map.put("userId", userId.toString());
		int count = 0;
		if (CollectionTypeEnums.TOPIC.getValue().equals(type)) {
			count = collectionMapper.selectTopicInfoCount(map);
		} else if (CollectionTypeEnums.BLOG.getValue().equals(type)) {
			count = collectionMapper.selectBlogInfoCount(map);
		}
		if (count > 0) {
			throw new BusinessException("此文章你已经收藏");
		}
		Collection collection = new Collection();
		collection.setArticleId(Integer.parseInt(articleId));
		collection.setUserId(userId);
		collection.setArticleId(articleId_int);
		collection.setArticleType(type);
		collection.setUserId(userId);
		collectionMapper.insert(collection);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteCollection(Map<String, String> map, SessionUser sessionUser) throws BusinessException {
		String keystr = map.get("key");
		if (keystr == null) {
			throw new BusinessException("参数错误");
		}
		String[] keys = keystr.split(",");
		for (String key : keys) {
			String articleId = key;

			if (!StringUtils.isNumber(articleId)) {
				throw new BusinessException("参数错误");
			}
			map.put("articleId", articleId);
			map.put("userId", String.valueOf(sessionUser.getUserId()));
			Collection collection = collectionMapper.selectBaseInfo(map);
			if (collection == null) {
				throw new BusinessException("收藏的文章不存在");
			}
			if (!collection.getUserId().equals(sessionUser.getUserId())) {
				throw new BusinessException("你没有权限删除此收藏的文章");
			}
			collectionMapper.delete(map);
		}

	}

	public UlewoPaginationResult<Collection> queryCollectionByUserId(Map<String, String> map, SessionUser sessionUser) {
		String pageStr = map.get("page");
		Integer pageNo = 0;
		if (StringUtils.isNumber(pageStr)) {
			pageNo = Integer.parseInt(pageStr);
		}
		map.put("userId", String.valueOf(sessionUser.getUserId()));
		int count = 0;
		if (CollectionTypeEnums.TOPIC.getValue().equals(map.get("type"))) {
			count = collectionMapper.selectTopicInfoCount(map);
		} else if (CollectionTypeEnums.BLOG.getValue().equals(map.get("type"))) {
			count = collectionMapper.selectBlogInfoCount(map);
		}
		SimplePage page = new SimplePage(pageNo, count, PageSize.SIZE20.getSize());
		List<Collection> list = new ArrayList<Collection>();
		if (CollectionTypeEnums.TOPIC.getValue().equals(map.get("type"))) {
			list = collectionMapper.selectTopicInfoList(map, page);
		} else if (CollectionTypeEnums.BLOG.getValue().equals(map.get("type"))) {
			list = collectionMapper.selectBlogInfoList(map, page);
		}
		UlewoPaginationResult<Collection> result = new UlewoPaginationResult<Collection>(page, list);
		return result;
	}

	@Override
	public Collection articleCollectionInfo(Map<String, String> map, SessionUser sessionUser) {
		String articleId = map.get("articleId");
		String type = map.get("type");
		Collection collection = new Collection();
		collection.setCollectionCount(0);
		collection.setHaveCollection(false);
		if (!StringUtils.isNumber(articleId)) {
			return collection;
		}
		if (StringUtils.isEmpty(type)) {
			return collection;
		}
		// 查询文章收藏总数
		int count = 0;
		if (CollectionTypeEnums.TOPIC.getValue().equals(type)) {
			count = collectionMapper.selectTopicInfoCount(map);
		} else if (CollectionTypeEnums.BLOG.getValue().equals(type)) {
			count = collectionMapper.selectBlogInfoCount(map);
		}
		collection.setCollectionCount(count);
		if (null != sessionUser) {
			map.put("userId", String.valueOf(sessionUser.getUserId()));
			if (CollectionTypeEnums.TOPIC.getValue().equals(type)) {
				count = collectionMapper.selectTopicInfoCount(map);
			} else if (CollectionTypeEnums.BLOG.getValue().equals(type)) {
				count = collectionMapper.selectBlogInfoCount(map);
			}
			if (count > 0) {
				collection.setHaveCollection(true);
			} else {
				collection.setHaveCollection(false);
			}
		} else {
			collection.setHaveCollection(false);
		}

		return collection;
	}
}
