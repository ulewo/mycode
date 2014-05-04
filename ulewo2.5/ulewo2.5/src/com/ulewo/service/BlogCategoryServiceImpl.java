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
import com.ulewo.mapper.BlogCategoryMapper;
import com.ulewo.model.BlogCategory;
import com.ulewo.model.SessionUser;
import com.ulewo.util.StringUtils;

@Service("blogItemService")
public class BlogCategoryServiceImpl implements BlogCategoryService {
    @Resource
    private BlogCategoryMapper<BlogCategory> blogCategoryMapper;

    @Override
    public BlogCategory findCategoryById(Map<String, String> map,
	    SessionUser sessionUser) throws BusinessException {
	String categoryId = map.get("categoryId");
	String userIdStr = map.get("userId");

	if (!StringUtils.isNumber(userIdStr)) {
	    throw new BusinessException("参数错误");
	}
	if (!StringUtils.isNumber(categoryId)) {
	    throw new BusinessException("参数错误");
	}
	return this.blogCategoryMapper.selectBaseInfo(map);
    }

    @Override
    public List<BlogCategory> selectCategoryWithBlogCount(Integer userId) {
	BlogCategory blogCategory = new BlogCategory();
	blogCategory.setUserId(userId);
	return blogCategoryMapper.selectCategoryWithBlogCount(blogCategory);
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    @Override
    public void saveCategory(Map<String, String> map, Integer userId)
	    throws BusinessException {
	try {
	    String insertRows = map.get("inserted");
	    String updateRows = map.get("updated");
	    String deleteRows = map.get("deleted");
	    JSONArray insertArray = JSONArray.fromObject(URLDecoder.decode(
		    insertRows, "UTF-8"));
	    JSONArray updateArray = JSONArray.fromObject(URLDecoder.decode(
		    updateRows, "UTF-8"));
	    JSONArray deleteArray = JSONArray.fromObject(URLDecoder.decode(
		    deleteRows, "UTF-8"));
	    List<BlogCategory> insertList = JSONArray.toList(insertArray,
		    new BlogCategory(), new JsonConfig());
	    List<BlogCategory> updateList = JSONArray.toList(updateArray,
		    new BlogCategory(), new JsonConfig());
	    List<BlogCategory> deleteList = JSONArray.toList(deleteArray,
		    new BlogCategory(), new JsonConfig());
	    addCategory(insertList, userId);
	    updateCategory(updateList, userId);
	    deleteCategory(deleteList, userId);
	} catch (UnsupportedEncodingException e) {
	    throw new BusinessException("参数错误!");
	}
    }

    private void addCategory(List<BlogCategory> insertList, Integer userId)
	    throws BusinessException {
	for (BlogCategory category : insertList) {
	    category.setUserId(userId);
	    Map<String, String> param = new HashMap<String, String>();
	    param.put("userId", String.valueOf(userId));
	    if (StringUtils.isEmpty(category.getName())
		    || category.getName().length() > MaxLengthEnums.MAXLENGTH50
			    .getLength()) {
		throw new BusinessException("分类名不能超过50字符");
	    }

	    this.blogCategoryMapper.insert(category);
	}
    }

    private void updateCategory(List<BlogCategory> updateList, Integer userId)
	    throws BusinessException {
	for (BlogCategory category : updateList) {
	    if (StringUtils.isEmpty(category.getName())
		    || category.getName().length() > MaxLengthEnums.MAXLENGTH50
			    .getLength()) {
		throw new BusinessException("分类名不能超过50字符");
	    }
	    category.setUserId(userId);
	    this.blogCategoryMapper.updateSelective(category);
	}
    }

    private void deleteCategory(List<BlogCategory> deleteList, Integer userId)
	    throws BusinessException {
	for (BlogCategory category : deleteList) {
	    Map<String, String> param = new HashMap<String, String>();
	    param.put("categoryId", String.valueOf(category.getCategoryId()));
	    param.put("userId", String.valueOf(userId));
	    BlogCategory categorytemp = blogCategoryMapper
		    .selectBaseInfo(param);
	    if (categorytemp == null) {
		throw new BusinessException("分类不存在");
	    }
	    if (categorytemp.getUserId().intValue() != userId.intValue()) {
		throw new BusinessException("你没权限删除此分类");
	    }
	    if (category.getBlogCount() > 0) {
		throw new BusinessException("分类下有文章不能删除");
	    }
	    blogCategoryMapper.delete(param);
	}
    }

}
