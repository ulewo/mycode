package com.ulewo.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.CollectionTypeEnums;
import com.ulewo.enums.LengthEnums;
import com.ulewo.enums.LikeType;
import com.ulewo.enums.MarkEnums;
import com.ulewo.enums.MaxLengthEnums;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.PageSize;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.BlogMapper;
import com.ulewo.mapper.CollectionMapper;
import com.ulewo.mapper.UserMapper;
import com.ulewo.model.Blog;
import com.ulewo.model.Collection;
import com.ulewo.model.Like;
import com.ulewo.model.NoticeParam;
import com.ulewo.model.SessionUser;
import com.ulewo.model.User;
import com.ulewo.util.Constant;
import com.ulewo.util.ScaleFilter2;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.vo.BlogVo;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

	@Resource
	private BlogMapper<Blog> blogMapper;

	@Resource
	private UserMapper<User> userMapper;
	@Resource
	private LikeService likeService;
	@Resource
	private CollectionMapper<Collection> collectionMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public Blog saveBlog(Map<String, String> map, SessionUser sessionUser,
			HttpServletRequest request) throws BusinessException {
		Integer userId = sessionUser.getUserId();
		String title = map.get("title");
		String categoryId = map.get("categoryId");
		String keyword = map.get("keyWord");
		String content = map.get("content");
		String id = map.get("id");
		int id_int = 0;
		if (!StringUtils.isEmpty(id) && !StringUtils.isNumber(id)) {
			throw new BusinessException("参数错误");
		} else if (!StringUtils.isEmpty(id) && StringUtils.isNumber(id)) {
			id_int = Integer.parseInt(id);
		}

		if (StringUtils.isEmpty(title)
				|| title.length() > MaxLengthEnums.MAXLENGTH150.getLength()) {
			throw new BusinessException("标题不符合规范");
		}

		int categoryId_int = 0;
		if (StringUtils.isNumber(categoryId)) {
			categoryId_int = Integer.parseInt(categoryId);
		}

		Blog blog = new Blog();
		blog.setBlogId(id_int);
		blog.setTitle(StringUtils.clearHtml(title));
		blog.setCategoryId(categoryId_int);
		blog.setKeyWord(StringUtils.formateHtml(keyword));
		// 获取文章中的图片
		String blogImage = StringUtils.getImages(content);
		blog.setBlogImage(blogImage);
		String blogImageSmall = getBlogImageSmall(blogImage, request);
		blog.setBlogImageSmall(blogImageSmall);
		List<Integer> userIds = new ArrayList<Integer>();
		/**
		 * 安卓客户端保存图片
		 */
		if (!StringUtils.isEmpty(map.get("imgUrl"))) {
			String[] images = map.get("imgUrl").split(",");
			for (String image : images) {
				content = "<div style='text-align:left;margin-top:5px;'><img src='"
						+ image + "'/></div>" + content;
			}
		}
		String formatContent = FormatAt.getInstance(Constant.TYPE_TALK)
				.GenerateRefererLinks(userMapper, content, userIds);

		blog.setContent(formatContent);
		content = StringUtils.clearHtml(content);
		if (content.length() > LengthEnums.Length100.getLength()) {
			content = content.substring(0, LengthEnums.Length100.getLength())
					+ "......";
		}
		blog.setSummary(content);
		blog.setUserId(userId);
		if (blog.getBlogId().intValue() == 0) {
			blog.setCreateTime(StringUtils.dateFormater.format(new Date()));
			blogMapper.insert(blog);
			User user = this.userMapper.selectUserByUserId(sessionUser
					.getUserId());
			user.setMark(user.getMark() + MarkEnums.MARK5.getMark());
			this.userMapper.updateSelective(user);
			blog.setNewBlog(true);
			// 加分
		} else {
			blogMapper.updateSelective(blog);
		}

		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(blog.getBlogId());
		noticeParm.setNoticeType(NoticeType.ATINBLOG);
		noticeParm.setAtUserIds(userIds);
		noticeParm.setSendUserId(sessionUser.getUserId());
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();

		return blog;
	}

	private String getBlogImageSmall(String topicImage,
			HttpServletRequest request) {
		StringBuilder topicImageSmall = new StringBuilder();
		if (topicImage != null) {
			String port = request.getServerPort() == 80 ? "" : ":"
					+ request.getServerPort();
			String hostPath = "http://" + request.getServerName() + port
					+ request.getContextPath();
			String realPath = request.getSession().getServletContext()
					.getRealPath("");
			String[] topoicImages = topicImage.split("\\|");
			for (String img : topoicImages) {
				if (StringUtils.isEmpty(img)) {
					continue;
				}
				String imagePath = img.replaceAll(hostPath, "");
				String sourcePath = realPath + imagePath;
				String smallPath = sourcePath + ".small.jpg";
				String smallSavePath = hostPath + imagePath + ".small.jpg";
				BufferedImage src = null;
				try {
					src = ImageIO.read(new File(sourcePath));
					BufferedImage dst = new ScaleFilter2(120).filter(src, null);
					ImageIO.write(dst, "JPEG", new File(smallPath));
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
				topicImageSmall.append(smallSavePath).append("|");
			}
		}
		return topicImageSmall.toString();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteBlogBatch(Map<String, String> map, SessionUser user)
			throws BusinessException {
		String keystr = map.get("key");
		if (StringUtils.isEmpty(keystr)) {
			throw new BusinessException("参数错误");

		}
		String[] kyes = keystr.split(",");
		for (String key : kyes) {
			if (!StringUtils.isNumber(key)) {
				throw new BusinessException("参数错误");
			}
			map.put("blogId", key);
			Blog blog = blogMapper.selectBaseInfo(map);
			if (null == blog) {
				throw new BusinessException("博客不存在");
			}
			if (!blog.getUserId().equals(user.getUserId())) {
				throw new BusinessException("你没权限删除此博客");
			}
			blogMapper.delete(map);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void deleteBlogByAdmin(Map<String, String> map)
			throws BusinessException {
		String keystr = map.get("key");
		if (StringUtils.isEmpty(keystr)) {
			throw new BusinessException("参数错误");

		}
		String[] kyes = keystr.split(",");
		for (String key : kyes) {
			if (!StringUtils.isNumber(key)) {
				throw new BusinessException("参数错误");
			}
			map.put("blogId", key);
			blogMapper.delete(map);
		}
	}

	@Override
	public UlewoPaginationResult<Blog> queryBlogByUserId(Map<String, String> map)
			throws BusinessException {
		if (!StringUtils.isNumber(map.get("userId"))) {
			throw new BusinessException("参数错误");
		}
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		if (!StringUtils.isEmpty(map.get("categoryId"))
				&& !StringUtils.isNumber(map.get("categoryId"))) {
			throw new BusinessException("参数错误");
		}
		int countTotal = blogMapper.selectBaseInfoCount(map);
		SimplePage page = new SimplePage(page_no, countTotal,
				PageSize.SIZE20.getSize());
		List<Blog> list = blogMapper.selectBaseInfoList(map, page);
		UlewoPaginationResult<Blog> result = new UlewoPaginationResult<Blog>(
				page, list);
		return result;
	}

	@Override
	public List<Blog> queryBlogByUserId4List(Map<String, String> map) {
		SimplePage page = new SimplePage(0, PageSize.SIZE5.getSize());
		return blogMapper.selectBaseInfoList(map, page);
	}

	@Override
	public Blog showBlogById(Map<String, String> map) throws BusinessException {
		if (!StringUtils.isNumber(map.get("blogId"))
				&& !StringUtils.isNumber(map.get("userId"))) {
			throw new BusinessException("参数错误");
		}
		Blog result = blogMapper.selectDetail(map);
		if (null == result) {
			throw new BusinessException("博客不存在!");
		}
		result.setReadCount(result.getReadCount() + 1);
		blogMapper.updateSelective(result);
		return result;
	}

	@Override
	public int selectBaseInfoCount(Map<String, String> map) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", map.get("userId"));
		return this.blogMapper.selectBaseInfoCount(param);
	}

	@Override
	public List<Blog> queryLatestBlog4Index() throws BusinessException {
		SimplePage page = new SimplePage(0, PageSize.SIZE8.getSize());
		return this.blogMapper.selectBaseInfoList(null, page);
	}

	@Override
	public UlewoPaginationResult<Blog> queryLatestBlog(Map<String, String> map)
			throws BusinessException {
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		if (!StringUtils.isEmpty(map.get("categoryId"))
				&& !StringUtils.isNumber(map.get("categoryId"))) {
			throw new BusinessException("参数错误");
		}
		int countTotal = blogMapper.selectBaseInfoCount(map);
		int pageSize = PageSize.SIZE20.getSize();
		if (StringUtils.isNumber(map.get("rows"))) {
			pageSize = Integer.parseInt(map.get("rows"));
		}
		SimplePage page = new SimplePage(page_no, countTotal, pageSize);
		List<Blog> list = blogMapper.selectBaseInfoList(map, page);
		UlewoPaginationResult<Blog> result = new UlewoPaginationResult<Blog>(
				page, list);
		return result;
	}

	@Override
	public Map<String, Object> queryLatestBlog4Api(Map<String, String> map)
			throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		int page_no = 0;
		if (StringUtils.isNumber(map.get("pageIndex"))) {
			page_no = Integer.parseInt(map.get("pageIndex"));
		}
		int countTotal = blogMapper.selectBaseInfoCount(map);
		int pageSize = PageSize.SIZE20.getSize();
		if (StringUtils.isNumber(map.get("rows"))) {
			pageSize = Integer.parseInt(map.get("rows"));
		}
		SimplePage page = new SimplePage(page_no, countTotal, pageSize);
		List<Blog> list = blogMapper.selectBaseInfoList(map, page);
		List<BlogVo> resultList = new ArrayList<BlogVo>();
		BlogVo vo = null;
		for (Blog blog : list) {
			vo = new BlogVo();
			vo.setBlogId(blog.getBlogId());
			vo.setTitle(blog.getTitle());
			vo.setCreateTime(blog.getShowCreateTime());
			vo.setReadCount(blog.getReadCount());
			vo.setCommentCount(blog.getCommentCount());
			vo.setUserId(blog.getUserId());
			vo.setUserIcon(blog.getUserIcon());
			vo.setUserName(blog.getUserName());
			resultList.add(vo);
		}
		result.put("page", page);
		result.put("list", resultList);
		return result;
	}

	@Override
	public Map<String, Object> queryBlogByUserId4Api(Map<String, String> map,
			Integer userId) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		map.put("userId", String.valueOf(userId));
		int page_no = 0;
		if (StringUtils.isNumber(map.get("pageIndex"))) {
			page_no = Integer.parseInt(map.get("pageIndex"));
		}
		int countTotal = blogMapper.selectBaseInfoCount(map);
		SimplePage page = new SimplePage(page_no, countTotal,
				PageSize.SIZE20.getSize());
		List<Blog> list = blogMapper.selectBaseInfoList(map, page);
		List<BlogVo> resultList = new ArrayList<BlogVo>();
		BlogVo vo = null;
		for (Blog blog : list) {
			vo = new BlogVo();
			vo.setBlogId(blog.getBlogId());
			vo.setTitle(blog.getTitle());
			vo.setCreateTime(blog.getShowCreateTime());
			vo.setReadCount(blog.getReadCount());
			vo.setCommentCount(blog.getCommentCount());
			vo.setUserId(blog.getUserId());
			vo.setUserIcon(blog.getUserIcon());
			vo.setUserName(blog.getUserName());
			vo.setLickCount(blog.getLikeCount());
			vo.setCollectionCount(blog.getCollectionCount());
			resultList.add(vo);
		}
		result.put("page", page);
		result.put("list", resultList);
		return result;
	}

	@Override
	public Map<String, Object> blogDetail4Api(Map<String, String> map,
			Integer userId) throws BusinessException {
		if (StringUtils.isEmpty(map.get("blogId"))) {
			throw new BusinessException("参数错误");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		Blog blog = blogMapper.selectDetail(map);
		blog.setReadCount(blog.getReadCount() + 1);
		blogMapper.updateSelective(blog);
		BlogVo vo = new BlogVo();

		if (null != userId) {
			Like like = new Like();
			like.setOpId(blog.getBlogId());
			like.setUserId(userId);
			like.setType(LikeType.BLOG.getValue());
			int likeCount = likeService.getLikeCount(like);
			if (likeCount > 0) {
				vo.setLike(true);
			} else {
				vo.setLike(false);
			}
			Map<String, String> param = new HashMap<String, String>();
			param.put("userId", String.valueOf(userId));
			param.put("type", CollectionTypeEnums.BLOG.getValue());
			param.put("articleId", String.valueOf(blog.getBlogId()));
			int cCount = collectionMapper.selectBlogInfoCount(param);
			if (cCount > 0) {
				vo.setCollection(true);
			} else {
				vo.setCollection(false);
			}
		} else {
			vo.setLike(false);
			vo.setCollection(false);
		}

		vo.setBlogId(blog.getBlogId());
		vo.setTitle(blog.getTitle());
		vo.setCreateTime(blog.getShowCreateTime());
		vo.setReadCount(blog.getReadCount());
		vo.setCommentCount(blog.getCommentCount());
		vo.setUserId(blog.getUserId());
		vo.setUserIcon(blog.getUserIcon());
		vo.setUserName(blog.getUserName());
		vo.setLickCount(blog.getLikeCount());
		vo.setCollectionCount(blog.getCollectionCount());
		vo.setContent(blog.getContent());
		result.put("blog", vo);
		return result;
	}
}
