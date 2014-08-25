package com.ulewo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.enums.ResultCode;
import com.ulewo.enums.SearchTypeEnums;
import com.ulewo.exception.BusinessException;
import com.ulewo.model.Attachment;
import com.ulewo.model.Blast;
import com.ulewo.model.Blog;
import com.ulewo.model.Group;
import com.ulewo.model.GroupCategory;
import com.ulewo.model.Topic;
import com.ulewo.model.User;
import com.ulewo.service.AttachmentService;
import com.ulewo.service.BlastService;
import com.ulewo.service.BlogService;
import com.ulewo.service.GroupCategoryService;
import com.ulewo.service.GroupService;
import com.ulewo.service.LikeService;
import com.ulewo.service.Log;
import com.ulewo.service.SignInService;
import com.ulewo.service.TopicService;
import com.ulewo.service.UserService;
import com.ulewo.util.Constant;
import com.ulewo.util.ErrorReport;
import com.ulewo.util.SimplePage;
import com.ulewo.util.UlewoPaginationResult;

@Controller
public class HomeAction extends BaseAction {
	@Autowired
	GroupService groupService;

	@Autowired
	TopicService topicService;

	@Autowired
	UserService userService;

	@Autowired
	BlogService blogService;

	@Autowired
	BlastService blastService;

	@Autowired
	SignInService signinService;

	@Autowired
	AttachmentService attachmentService;

	@Autowired
	GroupCategoryService groupCategoryService;

	@Autowired
	LikeService likeService;

	@Log
	Logger log;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		return mv;
	}

	@RequestMapping(value = "/restpwd", method = RequestMethod.GET)
	public ModelAndView restPwd() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("rest_pwd");
		return mv;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("register");
		return mv;
	}

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {

			mv.addObject("topics", topicService.getTopic4Index());
			SimplePage page = new SimplePage();
			page.setStart(0);
			page.setEnd(8);
			List<Group> groups = groupService.findCommendGroupAndTopic(page);
			List<Blog> blogList = blogService.queryLatestBlog4Index();
			List<GroupCategory> groupCateGroy = groupCategoryService
					.selectGroupCategoryList4Index();

			// 网事

			mv.addObject("groups", groups);
			mv.addObject("blogList", blogList);
			mv.addObject("groupCateGroy", groupCateGroy);
			mv.setViewName("home");
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	@RequestMapping(value = "/square", method = RequestMethod.GET)
	public ModelAndView square(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			Map<String, String> map = this.builderParams(request, true);
			map.put("haveimg", "Y");
			map.put("orderBy", "create_time desc");
			UlewoPaginationResult<Topic> result = topicService.findTopics(map);
			List<Topic> list = (List<Topic>) result.getList();
			List<Topic> square1 = new ArrayList<Topic>();
			List<Topic> square2 = new ArrayList<Topic>();
			List<Topic> square3 = new ArrayList<Topic>();
			List<Topic> square4 = new ArrayList<Topic>();
			set2Square(square1, square2, square3, square4, list);
			mv.addObject("square1", square1);
			mv.addObject("square2", square2);
			mv.addObject("square3", square3);
			mv.addObject("square4", square4);
			mv.addObject("page", result.getPage().getPage());
			mv.addObject("pageTotal", result.getPage().getPageTotal());
			mv.setViewName("square");
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	private void set2Square(List<Topic> square1, List<Topic> square2,
			List<Topic> square3, List<Topic> square4, List<Topic> squareList) {

		int num = 0;
		int j = 0;
		for (Topic article : squareList) {
			j++;
			if (1 + num * 4 == j) {
				square1.add(article);
				continue;
			}
			if (2 + num * 4 == j) {
				square2.add(article);
				continue;
			}
			if (3 + num * 4 == j) {
				square3.add(article);
				continue;
			}
			if (4 + num * 4 == j) {
				square4.add(article);
				num++;
				continue;
			}
		}
	}

	@RequestMapping(value = "/blog", method = RequestMethod.GET)
	public ModelAndView blog(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			UlewoPaginationResult<Blog> result = this.blogService
					.queryLatestBlog(this.builderParams(request, true));
			mv.addObject("result", result);
			mv.setViewName("blog");
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	@RequestMapping(value = "/resource", method = RequestMethod.GET)
	public ModelAndView resource(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			UlewoPaginationResult<Attachment> result = this.attachmentService
					.attachedTopic(this.builderParams(request, false));
			mv.addObject("result", result);
			mv.setViewName("resource");
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	/**
	 * 最新的吐槽
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadLatestBlast")
	public Map<String, Object> loadTalk(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			List<Blast> list = blastService.queryLatestBlast();
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			modelMap.put("list", list);
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loadGroupAndMember", method = RequestMethod.GET)
	public Map<String, Object> loadGroupAndMember(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			// UlewoPaginationResult groupResult =
			// groupService.queryGroupsOderArticleCount(1, Constant.pageSize15);
			return modelMap;
		} catch (Exception e) {
			String errorMethod = "HomeAction-->loadGroupAndMember()<br>";
			ErrorReport report = new ErrorReport(errorMethod + e.getMessage());
			Thread thread = new Thread(report);
			thread.start();
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@RequestMapping(value = "/blast", method = RequestMethod.GET)
	public ModelAndView talk(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		try {
			mv.setViewName("blast");
			return mv;
		} catch (Exception e) {
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView error() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("error");
		return mv;
	}

	@RequestMapping(value = "/createWoWo", method = RequestMethod.GET)
	public ModelAndView createWoWo(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		if (null == session.getAttribute("user")) {
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
		mv.setViewName("create_group");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/loadGroupCategory")
	public Map<String, Object> loadGroupCategory(HttpSession session,
			HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			List<GroupCategory> groupCateGroy = groupCategoryService
					.selectGroupCategoryList4Index();
			modelMap.put("groupCateGroy", groupCateGroy);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", e.getMessage());
			return modelMap;
		}
	}

	@RequestMapping(value = "/downloadApp", method = RequestMethod.GET)
	public void downApp(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		InputStream in = null;
		BufferedInputStream bf = null;
		OutputStream toClient = null;
		try {
			// path是指欲下载的文件的路径。
			String realpath = session.getServletContext().getRealPath("/");
			ResourceBundle rb = ResourceBundle.getBundle("config.appConfig");
			String app_name = rb.getString("app_name");
			File file = new File(realpath + "app" + "/" + app_name);
			in = new FileInputStream(file);
			bf = new BufferedInputStream(in);
			byte[] buffer = new byte[bf.available()];
			bf.read(buffer);
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(app_name.getBytes("utf-8"), "ISO-8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
		} catch (Exception ex) {
			// ex.printStackTrace();
		} finally {
			if (toClient != null) {
				try {
					toClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@RequestMapping(value = "/ulewoapp", method = RequestMethod.GET)
	public ModelAndView fileupload(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("ulewoapp");
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			String type = request.getParameter("type");
			String keyWord = request.getParameter("q");
			if (null != keyWord) {
				keyWord = URLDecoder.decode(keyWord, "utf-8");
			}
			String page = request.getParameter("page");
			UlewoPaginationResult<?> result = null;
			Map<String, String> map = new HashMap<String, String>();
			map.put("keyWord", keyWord);
			map.put("page", page);
			if (SearchTypeEnums.BLOG.getValue().equals(type)) {
				result = blogService.queryLatestBlog(map);
				List<?> list = result.getList();
				for (Object t : list) {
					Blog blog = (Blog) t;
					blog.setTitle(blog.getTitle().replaceAll(keyWord,
							"<span class='hilight'>" + keyWord + "</span>"));
					blog.setSummary(blog.getSummary().replaceAll(keyWord,
							"<span class='hilight'>" + keyWord + "</span>"));
				}
			} else if (SearchTypeEnums.TOPIC.getValue().equals(type)) {
				result = topicService.findTopics(map);
				List<?> list = result.getList();
				for (Object t : list) {
					Topic topic = (Topic) t;
					topic.setTitle(topic.getTitle().replaceAll(keyWord,
							"<span class='hilight'>" + keyWord + "</span>"));
					topic.setSummary(topic.getSummary().replaceAll(keyWord,
							"<span class='hilight'>" + keyWord + "</span>"));
				}
			} else if (SearchTypeEnums.GROUP.getValue().equals(type)) {

			} else {
				throw new BusinessException("参数异常");
			}
			mv.addObject("result", result);
			mv.addObject("keyWord", keyWord);
			mv.addObject("type", type);
			mv.setViewName("search");
			return mv;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mv.setViewName("redirect:" + Constant.ERRORPAGE);
			return mv;
		}
	}

	@RequestMapping(value = "/userMarks", method = RequestMethod.GET)
	public ModelAndView userMarks(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("usermarks");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/loadUsersByMarks", method = RequestMethod.GET)
	public Map<String, Object> loadUsersByMarks(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			List<User> list = userService.selectUsersByMark();
			modelMap.put("list", list);
			modelMap.put("result", ResultCode.SUCCESS.getCode());
			return modelMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			modelMap.put("result", ResultCode.ERROR.getCode());
			modelMap.put("msg", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/like.action", method = RequestMethod.POST)
	public Map<String, Object> like(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			likeService.doLike(this.builderParams(request, false),
					this.getSessionUser(session));
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
}
