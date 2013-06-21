package com.ulewo.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.entity.Article;
import com.ulewo.entity.ArticleItem;
import com.ulewo.entity.AttachedFile;
import com.ulewo.entity.Group;
import com.ulewo.entity.Member;
import com.ulewo.entity.ReArticle;
import com.ulewo.entity.SessionUser;
import com.ulewo.enums.MemberStatus;
import com.ulewo.enums.QueryOrder;
import com.ulewo.service.ArticleItemService;
import com.ulewo.service.ArticleService;
import com.ulewo.service.GroupService;
import com.ulewo.service.MemberService;
import com.ulewo.service.ReArticleService;
import com.ulewo.util.Constant;
import com.ulewo.util.PaginationResult;
import com.ulewo.util.StringUtils;

@Controller
@RequestMapping("/group")
public class GroupAction {
	@Autowired
	GroupService groupService;

	@Autowired
	ArticleService articleService;

	@Autowired
	ArticleItemService articleItemService;

	@Autowired
	MemberService memberService;

	@Autowired
	ReArticleService reArticleService;

	private final static int MAX_FILE = 1024 * 200;

	private final static int TITLE_LENGTH = 150, KEYWORD_LENGTH = 150;

	/**
	 * 所有圈子
	 * 
	 * @param gid
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView allGroups(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		String page = request.getParameter("page");
		ModelAndView mv = new ModelAndView();
		int page_int = 0;
		if (StringUtils.isNumber(page)) {
			page_int = Integer.parseInt(page);
		}
		try {
			// 所有群组
			PaginationResult articleResult = groupService.queryGroupsOderArticleCount(page_int, Constant.pageSize10);
			mv.addObject("result", articleResult);
			mv.setViewName("group/allgroups");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/../error");
			return mv;
		}
		return mv;
	}

	/**
	 * 窝窝首页
	 * 
	 * @param gid
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{gid}", method = RequestMethod.GET)
	public ModelAndView queryUserInfo(@PathVariable String gid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		String itemId = request.getParameter("itemId");
		String page = request.getParameter("page");
		int itemId_int = 0;
		int page_int = 1;
		if (StringUtils.isNumber(itemId)) {
			itemId_int = Integer.parseInt(itemId);
		}
		if (StringUtils.isNumber(page)) {
			page_int = Integer.parseInt(page);
		}
		try {
			if (StringUtils.isEmpty(gid)) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			Group group = groupService.queryGorup(gid);
			if (null == group) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			// 查询文章
			PaginationResult articleResult = articleService.queryTopicOrderByGradeAndLastReTime(gid, itemId_int,
					page_int, Constant.pageSize15);
			// 查询分类
			List<ArticleItem> itemList = articleItemService.queryItemByGid(gid);
			//	List<Article> hotArticlelist = articleService.queryHotArticle(0, 10);
			mv.addObject("memberStatus", memberStatus(session, gid));
			mv.addObject("group", group);
			mv.addObject("articles", articleResult);
			//	mv.addObject("hotArticlelist", hotArticlelist);
			mv.addObject("itemList", itemList);
			mv.addObject("itemId", itemId_int);
			mv.addObject("page", page_int);
			mv.addObject("gid", gid);
			if (itemId_int == 0) {
				mv.setViewName("group/group");
			}
			else {
				mv.setViewName("group/group_articles");
			}

		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/../error");
			return mv;
		}
		return mv;
	}

	private String memberStatus(HttpSession session, String gid) {

		Object sessionObj = session.getAttribute("user");
		if (null == sessionObj) {
			return "";
		}
		else {
			String userId = ((SessionUser) sessionObj).getUserId();
			Member member = memberService.queryMemberByGidAndUserId(gid, userId);
			if (null == member) {
				return "";
			}
			else {
				return member.getIsMember();
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/{gid}/loadMembers", method = RequestMethod.GET)
	public Map<String, Object> loadMembers(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			int total = 12;
			int total2 = 20;
			List<Member> activerList = memberService.queryMembersActiveIndex(gid, MemberStatus.ISMEMBER, 0, total);
			List<Member> memberList = memberService.queryMembersIndex(gid, MemberStatus.ISMEMBER, QueryOrder.ASC, 0,
					total2);
			modelMap.put("memberList", memberList);
			modelMap.put("activerList", activerList);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@RequestMapping(value = "/{gid}/img", method = RequestMethod.GET)
	public ModelAndView queryImage(@PathVariable String gid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			if (StringUtils.isEmpty(gid)) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			Group group = groupService.queryGorup(gid);
			if (null == group) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			int page_int = 0;
			String page = request.getParameter("page");
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}

			PaginationResult result = articleService.queryImageArticle2PagResult(group.getId(), page_int,
					Constant.pageSize30);
			List<Article> list = (List<Article>) result.getList();
			List<Article> square1 = new ArrayList<Article>();
			List<Article> square2 = new ArrayList<Article>();
			List<Article> square3 = new ArrayList<Article>();
			List<Article> square4 = new ArrayList<Article>();
			set2Square(square1, square2, square3, square4, list);
			mv.addObject("square1", square1);
			mv.addObject("square2", square2);
			mv.addObject("square3", square3);
			mv.addObject("square4", square4);
			mv.addObject("page", result.getPage());
			mv.addObject("pageTotal", result.getPageTotal());
			mv.setViewName("square");
			mv.addObject("memberStatus", memberStatus(session, gid));
			mv.addObject("group", group);
			mv.addObject("gid", gid);
			mv.setViewName("group/group_img");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/../error");
			return mv;
		}
		return mv;
	}

	private void set2Square(List<Article> square1, List<Article> square2, List<Article> square3, List<Article> square4,
			List<Article> squareList) {

		int num = 0;
		int j = 0;
		for (Article article : squareList) {
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

	/*****
	 * 加入圈子
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/joinGroup.action", method = RequestMethod.GET)
	public Map<String, String> joinGroup(HttpSession session, HttpServletRequest request) {

		Map<String, String> modelMap = new HashMap<String, String>();
		try {
			String gid = request.getParameter("gid");
			String userId = ((SessionUser) session.getAttribute("user")).getUserId();
			Member member = new Member();
			member.setGid(gid);
			member.setUserId(userId);
			return memberService.joinGroup(member);
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/existGroup.action", method = RequestMethod.GET)
	public Map<String, String> existGroup(HttpSession session, HttpServletRequest request) {

		Map<String, String> modelMap = new HashMap<String, String>();
		try {
			String gid = request.getParameter("gid");
			String userId = ((SessionUser) session.getAttribute("user")).getUserId();
			Member member = new Member();
			member.setGid(gid);
			member.setUserId(userId);
			return memberService.existGroup(member);
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	@RequestMapping(value = "/{gid}/member", method = RequestMethod.GET)
	public ModelAndView queryMember(@PathVariable String gid, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		String page = request.getParameter("page");
		int page_int = 1;
		if (StringUtils.isNumber(page)) {
			page_int = Integer.parseInt(page);
		}
		try {
			if (StringUtils.isEmpty(gid)) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			Group group = groupService.queryGorup(gid);
			if (null == group) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			PaginationResult result = memberService.queryMembers(gid, MemberStatus.ISMEMBER, QueryOrder.ASC, page_int,
					Constant.pageSize30);
			mv.addObject("gid", gid);
			mv.addObject("memberStatus", memberStatus(session, gid));
			mv.addObject("group", group);
			mv.addObject("result", result);
			mv.setViewName("group/group_member");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/../error");
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/{gid}/topic/{id}", method = RequestMethod.GET)
	public ModelAndView showDetail(@PathVariable String gid, @PathVariable String id, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		int id_int = 0;
		if (StringUtils.isNumber(id)) {
			id_int = Integer.parseInt(id);
		}
		try {
			if (StringUtils.isEmpty(gid)) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			Group group = groupService.queryGorup(gid);
			if (null == group) {
				mv.setViewName("redirect:" + Constant.ERRORPAGE);
				return mv;
			}
			Article article = articleService.showArticle(id_int);
			if (null == article || !article.getGid().equals(gid)) {
				mv.setViewName("redirect:/../error");
				return mv;
			}
			// 查询分类
			List<ArticleItem> itemList = articleItemService.queryItemByGid(gid);
			mv.addObject("memberStatus", memberStatus(session, gid));
			mv.addObject("group", group);
			mv.addObject("gid", gid);
			mv.addObject("itemList", itemList);
			mv.addObject("article", article);
			mv.setViewName("group/show_detail");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/../error");
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public ModelAndView fileupload(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView();
		try {
			Object user = session.getAttribute("user");
			if (null == user) {
				mv.addObject("result", "fail");
				mv.addObject("message", "你登陆已超时，请重新登陆");
				mv.setViewName("group/fileupload");
				return mv;
			}
			String realPath = session.getServletContext().getRealPath("/");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("file");
			long size = multipartFile.getSize();
			if (size > MAX_FILE) {
				mv.addObject("result", "fail");
				mv.addObject("message", "文件超过200kb");
				mv.setViewName("group/fileupload");
				return mv;
			}
			String fileName = multipartFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			if ("rar".equalsIgnoreCase(suffix)) {
				mv.addObject("result", "fail");
				mv.addObject("message", "文件类型只能是.rar 压缩文件");
				mv.setViewName("group/fileupload");
				return mv;
			}
			SimpleDateFormat formater = new SimpleDateFormat("yyyyMM");
			String saveDir = formater.format(new Date());
			String savePath = saveDir + "/" + fileName;
			String fileDir = realPath + "upload" + "/" + saveDir;
			File dir = new File(fileDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String filePath = fileDir + "/" + fileName;
			File file = new File(filePath);
			multipartFile.transferTo(file);
			mv.addObject("result", "success");
			mv.addObject("savePath", savePath);
			mv.setViewName("group/fileupload");
			return mv;
		} catch (Exception e) {
			mv.addObject("result", "fail");
			mv.setViewName("group/fileupload");
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteFile.action", method = RequestMethod.POST)
	public Map<String, Object> deleteFile(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String fileName = request.getParameter("fileName");
			String userId = request.getParameter("userId");
			Object user = session.getAttribute("user");
			if (user == null || !((SessionUser) user).getUserId().equals(userId)) {
				modelMap.put("result", "fail");
				return modelMap;
			}
			if (StringUtils.isEmpty(fileName)) {
				modelMap.put("result", "fail");
				return modelMap;
			}
			String realPath = session.getServletContext().getRealPath("/") + "upload/";
			File file = new File(realPath + fileName);
			if (file.exists()) {
				file.delete();
			}
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/addArticle.action", method = RequestMethod.POST)
	public Map<String, Object> addArticle(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String gid = request.getParameter("gid");
			String title = request.getParameter("title");
			String itemId = request.getParameter("itemId");
			int itemId_int = 0;
			String keyWord = request.getParameter("keyWord");
			String attached_file = request.getParameter("attached_file");
			String content = request.getParameter("content");
			String image = request.getParameter("image");
			if (StringUtils.isEmpty(gid)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "请求参数错误");
				return modelMap;
			}

			if (StringUtils.isNumber(itemId)) {
				itemId_int = Integer.parseInt(itemId);
			}

			if (StringUtils.isEmpty(title) || title.length() > TITLE_LENGTH) {
				modelMap.put("result", "fail");
				modelMap.put("message", "标题为空，或者超过长度");
				return modelMap;
			}
			if (StringUtils.isNotEmpty(keyWord) && keyWord.length() > TITLE_LENGTH) {
				modelMap.put("result", "fail");
				modelMap.put("message", "关键字太长");
				return modelMap;
			}
			SessionUser user = (SessionUser) session.getAttribute("user");
			String userId = user.getUserId();
			Article article = new Article();
			article.setGid(gid);
			article.setItemId(itemId_int);
			article.setTitle(title);
			article.setKeyWord(keyWord);
			article.setContent(content);
			article.setAuthorId(userId);
			article.setImage(image);
			// 添加附件
			if (StringUtils.isNotEmpty(attached_file)) {
				AttachedFile file = new AttachedFile();
				file.setFileUrl(attached_file);
				file.setGid(gid);
				article.setFile(file);
			}

			articleService.addArticle(article);
			modelMap.put("result", "success");
			modelMap.put("article", article);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loadReComment", method = RequestMethod.GET)
	public Map<String, Object> loadReComment(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			int id_int = 0;
			if (!StringUtils.isNumber(id)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "参数错误");
				return modelMap;
			}
			id_int = Integer.parseInt(id);
			PaginationResult result = reArticleService.queryReArticles(id_int, page_int, Constant.pageSize20);
			modelMap.put("result", result);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/addReComment.action", method = RequestMethod.POST)
	public Map<String, Object> addReComment(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String content = request.getParameter("content");
			if (StringUtils.isEmpty(content)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "回复内容不能为空");
				return modelMap;
			}
			String articleId = request.getParameter("articleId");
			if (!StringUtils.isNumber(articleId)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "请求参数错误");
				return modelMap;
			}
			String gid = request.getParameter("gid");
			int articleId_int = Integer.parseInt(articleId);

			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			ReArticle reArticle = new ReArticle();
			reArticle.setArticleId(articleId_int);
			reArticle.setContent(content);
			reArticle.setGid(gid);
			reArticle.setAuthorid(sessionUser.getUserId());
			reArticle.setAuthorName(sessionUser.getUserName());
			reArticle.setAuthorIcon(sessionUser.getUserLittleIcon());
			ReArticle re = reArticleService.addReArticle(reArticle);
			modelMap.put("reArticle", re);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/addSubReComment.action", method = RequestMethod.POST)
	public Map<String, Object> addSubReComment(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String content = request.getParameter("content");
			if (StringUtils.isEmpty(content)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "回复内容不能为空");
				return modelMap;
			}
			String articleId = request.getParameter("articleId");
			if (!StringUtils.isNumber(articleId)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "请求参数错误");
				return modelMap;
			}
			String pid = request.getParameter("pid");
			if (!StringUtils.isNumber(pid)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "请求参数错误");
				return modelMap;
			}
			int pid_int = Integer.parseInt(pid);
			String gid = request.getParameter("gid");
			int articleId_int = Integer.parseInt(articleId);

			String atUserId = request.getParameter("atUserId");

			String atUserName = request.getParameter("atUserName");

			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			ReArticle reArticle = new ReArticle();
			reArticle.setArticleId(articleId_int);
			reArticle.setContent(StringUtils.formateHtml(content));
			reArticle.setGid(gid);
			reArticle.setAtUserId(atUserId);
			reArticle.setAtUserName(atUserName);
			reArticle.setPid(pid_int);

			reArticle.setAuthorid(sessionUser.getUserId());
			reArticle.setAuthorName(sessionUser.getUserName());
			reArticle.setAuthorIcon(sessionUser.getUserLittleIcon());
			ReArticle re = reArticleService.addReArticle(reArticle);
			modelMap.put("reArticle", re);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}
}
