package com.ulewo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ulewo.entity.Article;
import com.ulewo.entity.ArticleItem;
import com.ulewo.entity.Group;
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
@RequestMapping("/groupManage")
public class GroupMagageAction {
	@Autowired
	GroupService groupService;

	@Autowired
	ArticleItemService articleItemService;

	@Autowired
	ArticleService articleService;

	@Autowired
	ReArticleService reArticleService;

	@Autowired
	MemberService memberService;

	private final static int GROUPNAEM_LENGTH = 50;

	private final static int GROUPDESC_LENGTH = 500;

	private final static int TITLE_LENGTH = 150, KEYWORD_LENGTH = 150;

	@RequestMapping(value = "/{gid}/manage", method = RequestMethod.GET)
	public ModelAndView manage(@PathVariable String gid, HttpSession session) {

		ModelAndView mv = new ModelAndView();

		try {
			SessionUser user = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			Group group = groupService.queryGorup(gid);
			group.setGroupDesc(StringUtils.reFormateHtml(group.getGroupDesc()));
			mv.addObject("group", group);
			mv.addObject("gid", gid);
			mv.setViewName("/groupmanage/edit_group");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/{gid}/editGroup.action", method = RequestMethod.POST)
	public Map<String, Object> editGroup(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			SessionUser user = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			String groupName = request.getParameter("groupName");
			String groupDesc = request.getParameter("groupDesc");
			String joinPerm = request.getParameter("joinPerm");
			String postPerm = request.getParameter("postPerm");
			if (groupName == "" || groupName.length() > GROUPNAEM_LENGTH) {
				modelMap.put("result", "fail");
				modelMap.put("message", "窝窝名称为空，或者超过规定长度");
				return modelMap;
			}
			if (groupDesc == "" || groupDesc.length() > GROUPDESC_LENGTH) {
				modelMap.put("result", "fail");
				modelMap.put("message", "窝窝简介为空，或者超过规定长度");
				return modelMap;
			}
			Group group = new Group();
			group.setId(gid);
			group.setGroupName(groupName);
			group.setGroupDesc(groupDesc);
			group.setJoinPerm(joinPerm);
			group.setPostPerm(postPerm);
			groupService.updateGroup(group);
			modelMap.put("result", "success");
			modelMap.put("gid", gid);
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	/**
	 * 公告管理
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{gid}/group_notice", method = RequestMethod.GET)
	public ModelAndView groupNotice(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			SessionUser user = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			Group group = groupService.queryGorup(gid);
			group.setGroupNotice(StringUtils.reFormateHtml(group.getGroupNotice()));
			mv.addObject("group", group);
			mv.addObject("gid", gid);
			mv.setViewName("/groupmanage/group_notice");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/{gid}/editNotice.action", method = RequestMethod.POST)
	public Map<String, Object> editNotice(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			SessionUser user = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			String groupNotice = request.getParameter("groupNotice");
			Group group = new Group();
			group.setId(gid);
			group.setGroupNotice(groupNotice);
			groupService.updateGroup(group);
			modelMap.put("result", "success");
			modelMap.put("gid", gid);
			return modelMap;
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("result", "fail");
			modelMap.put("message", "系统异常");
			return modelMap;
		}
	}

	/**
	 * 分类管理
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{gid}/group_item", method = RequestMethod.GET)
	public ModelAndView groupItem(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			List<ArticleItem> itemList = articleItemService.queryItemAndTopicCountByGid(gid);
			mv.addObject("imtes", itemList);
			mv.addObject("gid", gid);
			mv.setViewName("groupmanage/group_item");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	@RequestMapping(value = "/saveItem.do", method = RequestMethod.POST)
	public ModelAndView saveItem(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			String gid = request.getParameter("gid");
			String itemName = request.getParameter("itemName");
			String itemCode = request.getParameter("itemCode");
			if(StringUtils.isEmpty(itemName)){
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			if(StringUtils.isEmpty(itemCode)){
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			itemName = itemName.trim();
			itemCode = itemCode.trim();
			String id = request.getParameter("id");
			if((StringUtils.isNotEmpty(id)&&!StringUtils.isNumber(id))||!StringUtils.isNumber(itemCode)){
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			int id_int = 0;
			if(StringUtils.isNotEmpty(id)){
				id_int = Integer.parseInt(id);
			}
			int itemCode_int = Integer.parseInt(itemCode);
			ArticleItem item = new ArticleItem();
			item.setId(id_int);
			item.setGid(gid);
			item.setItemCode(itemCode_int);
			item.setItemName(itemName);
			if(articleItemService.saveItem(item)){
				mv.setViewName("redirect:"+gid+"/group_item");
				return mv;
			}else{
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}
	
	@RequestMapping(value = "/{gid}/deleteItem", method = RequestMethod.GET)
	public ModelAndView deleteItem(@PathVariable String gid,HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			if(!checkPerm(gid, userId)){
				mv.setViewName("redirect:"+gid+"/group_item");
				return mv;
			}
			String id = request.getParameter("id");
			if(StringUtils.isEmpty(id)||!StringUtils.isNumber(id)){
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			int id_int = 0;
			if(StringUtils.isNotEmpty(id)){
				id_int = Integer.parseInt(id);
			}
			if(articleItemService.delete(id_int)){
				mv.setViewName("redirect:group_item");
				return mv;
			}else{
				mv.setViewName("redirect:" + Constant.WEBSTIE);
				return mv;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}
	
	/**
	 * 文章管理
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{gid}/group_article", method = RequestMethod.GET)
	public ModelAndView groupArticle(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			String itemId = request.getParameter("itemId");
			int itemId_int = 0;
			if (StringUtils.isNumber(itemId)) {
				itemId_int = Integer.parseInt(itemId);
			}
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			List<ArticleItem> itemList = articleItemService.queryItemAndTopicCountByGid(gid);
			PaginationResult result = articleService.queryTopicOrderByGradeAndLastReTime(gid, itemId_int, page_int,
					Constant.pageSize20);
			mv.addObject("itemList", itemList);
			mv.addObject("itemId", itemId_int);
			mv.addObject("result", result);
			mv.addObject("gid", gid);
			mv.setViewName("groupmanage/group_article");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	@RequestMapping(value = "/{gid}/edit_article", method = RequestMethod.GET)
	public ModelAndView editArticle(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			String id = request.getParameter("id");

			if (!StringUtils.isNumber(id)) {
				mv.setViewName("redirect:" + Constant.WEBSTIE);
			}
			int id_int = Integer.parseInt(id);
			Article article = articleService.queryTopicById(id_int);
			if (article == null) {
				mv.setViewName("redirect:" + Constant.WEBSTIE);
			}
			article.setContent(article.getContent().replace("\r\n", ""));
			List<ArticleItem> list = articleItemService.queryItemByGid(gid);
			mv.addObject("article", article);
			mv.addObject("gid", gid);
			mv.addObject("list", list);
			mv.setViewName("groupmanage/edit_article");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/saveArticle.action", method = RequestMethod.POST)
	public Map<String, Object> saveArticle(HttpSession session, HttpServletRequest request) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			String gid = request.getParameter("gid");
			String title = request.getParameter("title");
			String itemId = request.getParameter("itemId");
			int itemId_int = 0;
			String keyWord = request.getParameter("keyWord");
			String content = request.getParameter("content");
			String image = request.getParameter("image");
			if (StringUtils.isEmpty(gid)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "请求参数错误");
				return modelMap;
			}
			if (!StringUtils.isNumber(id)) {
				modelMap.put("result", "fail");
				modelMap.put("message", "请求参数错误");
				return modelMap;
			}
			int id_int = Integer.parseInt(id);

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
			// SessionUser user = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			Article article = new Article();
			article.setId(id_int);
			article.setGid(gid);
			article.setItemId(itemId_int);
			article.setTitle(title);
			article.setKeyWord(keyWord);
			article.setContent(content);
			article.setAuthorId(userId);
			article.setImage(image);
			// 添加附件
			articleService.updateArticle(article, userId);
			modelMap.put("result", "success");
			return modelMap;
		} catch (Exception e) {
			modelMap.put("result", "fail");
			return modelMap;
		}
	}

	@RequestMapping(value = "/{gid}/manageArticle", method = RequestMethod.POST)
	public ModelAndView manageArticle(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			String itemId = request.getParameter("itemId");
			int itemId_int = 0;
			if (StringUtils.isNumber(itemId)) {
				itemId_int = Integer.parseInt(itemId);
			}
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			String type = request.getParameter("type");
			String[] id = request.getParameterValues("id");
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			String userId = "10001";
			articleService.manangeArticle(userId, gid, id, type);
			mv.addObject("itemId", itemId_int);
			mv.addObject("gid", gid);
			mv.setViewName("groupmanage/group_article");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	@RequestMapping(value = "/{gid}/article_reply", method = RequestMethod.GET)
	public ModelAndView articleReply(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = reArticleService.queryReArticleByGid(gid, page_int, Constant.pageSize25);
			mv.addObject("gid", gid);
			mv.addObject("result", result);
			mv.setViewName("groupmanage/article_reply");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	@RequestMapping(value = "/{gid}/member", method = RequestMethod.GET)
	public ModelAndView member(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = memberService.queryMembers(gid, MemberStatus.ISMEMBER, QueryOrder.ASC, page_int,
					Constant.pageSize20);
			mv.addObject("gid", gid);
			mv.addObject("result", result);
			mv.setViewName("groupmanage/member");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}

	@RequestMapping(value = "/{gid}/member_apply", method = RequestMethod.GET)
	public ModelAndView memberApply(@PathVariable String gid, HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			String page = request.getParameter("page");
			int page_int = 0;
			if (StringUtils.isNumber(page)) {
				page_int = Integer.parseInt(page);
			}
			PaginationResult result = memberService.queryMembers(gid, MemberStatus.NOTMEMBER, QueryOrder.ASC, page_int,
					Constant.pageSize20);
			mv.addObject("gid", gid);
			mv.addObject("result", result);
			mv.setViewName("groupmanage/member_apply");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:" + Constant.WEBSTIE);
			return mv;
		}
	}
	
	private boolean checkPerm(String gid, String groupAuthor) {

		Group group = groupService.queryGroupBaseInfo(gid);
		if (null == group) {
			return false;
		}
		if (!group.getGroupAuthor().equals(groupAuthor)) {
			return false;
		}
		return true;
	}
}
