package com.ulewo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.enums.ResultCode;
import com.ulewo.enums.SourceFromEnums;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.Blog;
import com.ulewo.model.BlogCategory;
import com.ulewo.model.BlogComment;
import com.ulewo.service.BlogCategoryService;
import com.ulewo.service.BlogCommentService;
import com.ulewo.service.BlogService;
import com.ulewo.service.Log;
import com.ulewo.util.Constant;
import com.ulewo.util.ErrorReport;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.vo.UserVo;

@Controller
@RequestMapping("/user")
public class BlogAction extends BaseUserAction {

	@Autowired
	private BlogService blogService;

	@Autowired
	private BlogCategoryService blogCategoryService;

	@Autowired
	private BlogCommentService blogCommentService;

	@Log
	private Logger log;

	/**
	 * 所有博文
	 * 
	 * @param userId
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{userId}/blog", method = RequestMethod.GET)
	public ModelAndView blogList(@PathVariable String userId, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			UserVo userVo = this.checkUserInfo(userId, session);
			if (null == userVo) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			mv.addObject("userVo", userVo);
			Map<String, String> map = this.builderParams(request, true);
			map.put("userId", userId);
			Integer userId_int = userVo.getUserId();
			UlewoPaginationResult<Blog> result = blogService.queryBlogByUserId(map);
			List<BlogCategory> blogCategoryList = blogCategoryService.selectCategoryWithBlogCount(userId_int);
			int allBlogCount = blogService.selectBaseInfoCount(map);
			mv.addObject("result", result);
			mv.addObject("allBlogCount", allBlogCount);
			mv.addObject("blogItemList", blogCategoryList);
			mv.addObject("userId", userId);
			mv.addObject("totalCount", result.getPage().getCountTotal());
			mv.setViewName("/user/blog");

			return mv;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return mv;
		}
	}

	/**
	 * 分类博文
	 * 
	 * @param userId
	 * @param categoryId
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{userId}/blog/cateId/{categoryId}", method = RequestMethod.GET)
	public ModelAndView blogList(@PathVariable String userId, @PathVariable String categoryId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			UserVo userVo = checkUserInfo(userId, session);
			if (null == userVo) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
			}
			mv.addObject("userVo", userVo);
			Map<String, String> map = this.builderParams(request, true);
			map.put("categoryId", categoryId);
			map.put("userId", userId);
			Integer userId_int = userVo.getUserId();
			UlewoPaginationResult<Blog> result = blogService.queryBlogByUserId(map);
			List<BlogCategory> blogCategoryList = blogCategoryService.selectCategoryWithBlogCount(userId_int);
			int allBlogCount = blogService.selectBaseInfoCount(map);
			mv.addObject("result", result);
			mv.addObject("blogItemList", blogCategoryList);
			mv.addObject("allBlogCount", allBlogCount);
			mv.addObject("userId", userId);
			mv.addObject("totalCount", result.getPage().getCountTotal());
			mv.setViewName("/user/blog");
			return mv;
		} catch (Exception e) {
			String errorMethod = "UserAction-->blogList()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	/**
	 * 博客详情
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{userId}/blog/{blogId}", method = RequestMethod.GET)
	public ModelAndView blogDetail(@PathVariable String userId, @PathVariable String blogId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			UserVo userVo = checkUserInfo(userId, session);
			if (null == userVo) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
			}
			mv.addObject("userVo", userVo);
			Integer userId_int = userVo.getUserId();
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", userId);
			map.put("blogId", blogId);
			Blog blogArticle = blogService.showBlogById(map);
			List<BlogCategory> blogItemList = blogCategoryService.selectCategoryWithBlogCount(userId_int);
			int allBlogCount = blogService.selectBaseInfoCount(map);
			mv.addObject("allBlogCount", allBlogCount);
			mv.addObject("blogItemList", blogItemList);
			mv.addObject("blog", blogArticle);
			mv.addObject("userId", userId);
			mv.setViewName("user/blog_detail");
			return mv;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	/** 博客回复 ***/
	@ResponseBody
	@RequestMapping(value = "/blogComment", method = RequestMethod.POST)
	public Map<String, Object> blogComment(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> param = this.builderParams(request, false);
			UlewoPaginationResult<BlogComment> result = blogCommentService.queryBlogCommentByBlogId(param);
			modelMap.put("rowData", result);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			modelMap.put("msg", result.getMsg());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			return modelMap;
		}
	}

	/**
	 * 保存博客评论回复
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveComment.action", method = RequestMethod.POST)
	public Map<String, Object> saveBlogComment(HttpSession session, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		try {
			Map<String,String> map = this.builderParams(request, false);
			map.put("sourceFrom", SourceFromEnums.PC.getValue());
			BlogComment comment = this.blogCommentService.addBlogComment(map,
					this.getSessionUser(session));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			modelMap.put("comment", comment);
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}
}
