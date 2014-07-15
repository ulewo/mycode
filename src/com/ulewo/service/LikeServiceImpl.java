package com.ulewo.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ulewo.enums.LikeType;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.BlastMapper;
import com.ulewo.mapper.BlogMapper;
import com.ulewo.mapper.LikeMapper;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.model.Blast;
import com.ulewo.model.Blog;
import com.ulewo.model.Like;
import com.ulewo.model.SessionUser;
import com.ulewo.model.Topic;
import com.ulewo.util.StringUtils;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-5 下午5:34:22
 * @version 3.0
 * @copyright www.ulewo.com
 */
@Service("likeService")
public class LikeServiceImpl implements LikeService {

	@Resource
	private TopicMapper<Topic> topicMapper;
	@Resource
	private BlogMapper<Blog> blogMapper;
	@Resource
	private BlastMapper<Blast> blastMapper;

	@Resource
	private LikeMapper<Like> likeMapper;

	@Override
	public void doLike(Map<String, String> map, SessionUser sessionUser)
			throws BusinessException {
		String opid = map.get("opid");
		String type = map.get("type");
		if (!StringUtils.isNumber(opid)) {
			throw new BusinessException("参数错误!");
		}
		if (LikeType.TOPIC.getValue().equals(type)) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("topicId", opid);
			if (topicMapper.selectBaseInfo(param) == null) {
				throw new BusinessException("主题不存在!");
			}
		} else if (LikeType.BLOG.getValue().equals(type)) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("blogId", opid);
			if (blogMapper.selectBaseInfo(param) == null) {
				throw new BusinessException("博客不存在!");
			}
		} else if (LikeType.BLAST.getValue().equals(type)) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("blastId", opid);
			if (blastMapper.selectBaseInfoCount(param) == 0) {
				throw new BusinessException("吐槽不存在!");
			}
		} else {
			throw new BusinessException("参数错误!");
		}
		Like like = new Like();
		like.setOpId(Integer.parseInt(opid));
		like.setType(type);
		like.setUserId(sessionUser.getUserId());
		int count = likeMapper.getLikeCount(like);
		if (count > 0) {
			throw new BusinessException("你已经赞过了，如果很喜欢，可以评论^_^");
		}
		likeMapper.save(like);
	}
}
