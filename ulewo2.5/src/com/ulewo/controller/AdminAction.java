package com.ulewo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ulewo.enums.ResultCode;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.Blast;
import com.ulewo.model.BlastComment;
import com.ulewo.model.Blog;
import com.ulewo.model.BlogComment;
import com.ulewo.model.Group;
import com.ulewo.model.GroupCategory;
import com.ulewo.model.Spider;
import com.ulewo.model.Topic;
import com.ulewo.model.TopicComment;
import com.ulewo.model.User;
import com.ulewo.service.BlastCommentService;
import com.ulewo.service.BlastService;
import com.ulewo.service.BlogCommentService;
import com.ulewo.service.BlogService;
import com.ulewo.service.GroupCategoryService;
import com.ulewo.service.GroupService;
import com.ulewo.service.Log;
import com.ulewo.service.SpidrService;
import com.ulewo.service.TopicCommentService;
import com.ulewo.service.TopicService;
import com.ulewo.service.UserService;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.util.UlewoPaginationResult4Json;

@Controller
@RequestMapping("/admin")
public class AdminAction extends BaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private BlastService blastService;

	@Autowired
	private BlastCommentService blastCommentService;

	@Autowired
	private TopicService topicService;

	@Autowired
	private TopicCommentService topicCommentService;

	@Autowired
	private BlogService blogService;

	@Autowired
	private BlogCommentService blogCommentService;

	@Autowired
	private GroupCategoryService groupCategoryService;

	@Autowired
	private SpidrService spiderService;

	@Log
	Logger log;

	/*********** 成员 **************/
	@RequestMapping(value = "/users")
	@ResponseBody
	public Map<String, Object> users(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			UlewoPaginationResult<User> result = this.userService
					.findAllUsers(map);
			resultObj.put("rows", result.getList());
			resultObj.put("total", result.getPage().getCountTotal());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return resultObj;
		}
	}

	@RequestMapping(value = "/deleteUsers")
	@ResponseBody
	public Map<String, Object> deleteUsers(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.userService.deleteUserBatch(map);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
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

	/*************** 窝窝 ********************/

	@RequestMapping(value = "/groups")
	@ResponseBody
	public Map<String, Object> groups(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("sort", "commend_grade");
			UlewoPaginationResult<Group> result = this.groupService
					.findAllGroup(map);
			resultObj.put("rows", result.getList());
			resultObj.put("total", result.getPage().getCountTotal());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return resultObj;
		}
	}

	@RequestMapping(value = "/deleteGroups")
	@ResponseBody
	public Map<String, Object> deleteGroups(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.groupService.deleteGroup(map);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
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

	@RequestMapping(value = "/saveGroupGrade.action")
	@ResponseBody
	public Map<String, Object> saveGroupGrade(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.groupService.saveGroupGrade(map);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
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

	@RequestMapping(value = "/updateGroupCategroy.action")
	@ResponseBody
	public Map<String, Object> updateGroupCategroy(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.groupService.updateGroup(map);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
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

	/*************** 吐槽 ********************/

	@RequestMapping(value = "/blasts")
	@ResponseBody
	public Map<String, Object> blasts(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			UlewoPaginationResult<Blast> result = this.blastService
					.queryAllBlast(map);
			resultObj.put("rows", result.getList());
			resultObj.put("total", result.getPage().getCountTotal());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return resultObj;
		}
	}

	@RequestMapping(value = "/deleteBlasts")
	@ResponseBody
	public Map<String, Object> deleteBlasts(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.blastService.deleteBlastByAdmin(map);
			resultObj.put("result", ResultCode.SUCCESS.getCode());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultObj.put("result", ResultCode.ERROR.getCode());
			return resultObj;
		}
	}

	@RequestMapping(value = "/blastComments")
	@ResponseBody
	public Map<String, Object> blastComments(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			UlewoPaginationResult<BlastComment> result = this.blastCommentService
					.queryBlastCommentByPag(map);
			resultObj.put("rows", result.getList());
			resultObj.put("total", result.getPage().getCountTotal());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return resultObj;
		}
	}

	@RequestMapping(value = "/deleteBlastComments")
	@ResponseBody
	public Map<String, Object> deleteBlastComments(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.blastCommentService.deleteComments(map);
			resultObj.put("result", ResultCode.SUCCESS.getCode());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultObj.put("result", ResultCode.ERROR.getCode());
			resultObj.put("msg", e.getMessage());
			return resultObj;
		}
	}

	/******************* 主题 ***********************/
	@RequestMapping(value = "/topics")
	@ResponseBody
	public Map<String, Object> topics(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("orderBy", "create_time desc");
			UlewoPaginationResult<Topic> result = this.topicService
					.findTopics(map);
			resultObj.put("rows", result.getList());
			resultObj.put("total", result.getPage().getCountTotal());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return resultObj;
		}
	}

	@RequestMapping(value = "/deleteTopics")
	@ResponseBody
	public Map<String, Object> deleteTopics(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.topicService.deleteTopicByAdmin(map);
			resultObj.put("result", ResultCode.SUCCESS.getCode());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultObj.put("result", ResultCode.ERROR.getCode());
			resultObj.put("msg", e.getMessage());
			return resultObj;
		}
	}

	@RequestMapping(value = "/createHot")
	@ResponseBody
	public Map<String, Object> createHot(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			List<Topic> list = this.topicService.hotTopics(map, request);
			resultObj.put("result", ResultCode.SUCCESS.getCode());
			resultObj.put("list", list);
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultObj.put("result", ResultCode.ERROR.getCode());
			resultObj.put("msg", e.getMessage());
			return resultObj;
		}
	}

	/*************** 主题评论 ******************/

	@RequestMapping(value = "/topicComments")
	@ResponseBody
	public UlewoPaginationResult4Json<TopicComment> topicComments(
			HttpSession session, HttpServletRequest request) {
		try {
			Map<String, String> map = this.builderParams(request, true);
			UlewoPaginationResult4Json<TopicComment> result = this.topicCommentService
					.queryComment4JsonByTopicId(map);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@RequestMapping(value = "/deleteTopicComments")
	@ResponseBody
	public Map<String, Object> deleteTopicComments(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.topicCommentService.deleteCommentByAdmin(map);
			resultObj.put("result", ResultCode.SUCCESS.getCode());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultObj.put("result", ResultCode.ERROR.getCode());
			resultObj.put("msg", e.getMessage());
			return resultObj;
		}
	}

	/*************** 博客 ******************/

	@RequestMapping(value = "/blogs")
	@ResponseBody
	public Map<String, Object> blogs(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			UlewoPaginationResult<Blog> result = this.blogService
					.queryLatestBlog(map);
			resultObj.put("rows", result.getList());
			resultObj.put("total", result.getPage().getCountTotal());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@RequestMapping(value = "/deleteBlogs")
	@ResponseBody
	public Map<String, Object> deleteBlogs(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.blogService.deleteBlogByAdmin(map);
			resultObj.put("result", ResultCode.SUCCESS.getCode());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultObj.put("result", ResultCode.ERROR.getCode());
			resultObj.put("msg", e.getMessage());
			return resultObj;
		}
	}

	/******************* 博客评论 *************************/
	@RequestMapping(value = "/blogComments")
	@ResponseBody
	public Map<String, Object> blogComments(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			UlewoPaginationResult<BlogComment> result = this.blogCommentService
					.queryBlogCommentByBlogId(map);
			resultObj.put("rows", result.getList());
			resultObj.put("total", result.getPage().getCountTotal());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@RequestMapping(value = "/deleteBlogComments")
	@ResponseBody
	public Map<String, Object> deleteBlogComments(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.blogCommentService.deleteByAdmin(map);
			resultObj.put("result", ResultCode.SUCCESS.getCode());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultObj.put("result", ResultCode.ERROR.getCode());
			resultObj.put("msg", e.getMessage());
			return resultObj;
		}
	}

	/**
	 * 加载窝窝分类
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadGroupCategory.action")
	public Map<String, Object> loadCategory(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> param = this.builderParams(request, false);
			List<GroupCategory> categoryList = groupCategoryService
					.selectGroupCategoryList(param);
			modelMap.put("rows", categoryList);
			modelMap.put("total", categoryList.size());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/saveCategory.action")
	public Map<String, Object> editItem(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			this.groupCategoryService.saveCategory(this.builderParams(request,
					true));
			modelMap.put("result", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", "系统异常!");
		}
		return modelMap;
	}

	@ResponseBody
	@RequestMapping(value = "/spider.action")
	public Map<String, Object> spider(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String type = request.getParameter("type");
			this.spiderService.spiderArticle(type);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error(this.getClass().getName()+"->spider.action "+e.getMessage(),e);
			modelMap.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(this.getClass().getName()+"->spider.action "+e.getMessage(),e);
			modelMap.put("msg", "系统异常!");
		}
		return modelMap;
	}

	@ResponseBody
	@RequestMapping(value = "/getSpiderList.action")
	public Map<String, Object> getSpiderList(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			UlewoPaginationResult<Spider> spiderList = spiderService
					.querySpiderList(this.builderParams(request, false));
			modelMap.put("rows", spiderList.getList());
			modelMap.put("total", spiderList.getPage().getCountTotal());
			return modelMap;
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", "系统异常!");
		}
		return modelMap;
	}

	@RequestMapping(value = "/allGroups.action")
	@ResponseBody
	public Map<String, Object> allGroups(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> resultObj = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("rows", 150 + "");
			UlewoPaginationResult<Group> result = this.groupService
					.findAllGroup(map);
			resultObj.put("list", result.getList());
			return resultObj;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return resultObj;
		}
	}

	@RequestMapping(value = "/sendTopic.action")
	@ResponseBody
	public Map<String, Object> sendTopic(HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, String> map = this.builderParams(request, true);
			this.spiderService.sendTopic(map, request);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("msg", "系统异常!");
		}
		return modelMap;
	}

}
