package com.ulewo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ulewo.enums.MarkEnums;
import com.ulewo.enums.MaxLengthEnums;
import com.ulewo.enums.NoticeType;
import com.ulewo.enums.PageSize;
import com.ulewo.enums.QueryUserType;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.BlastMapper;
import com.ulewo.mapper.UserMapper;
import com.ulewo.model.Blast;
import com.ulewo.model.NoticeParam;
import com.ulewo.model.SessionUser;
import com.ulewo.model.User;
import com.ulewo.util.Constant;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;

@Service("talkService")
public class BlastServiceImpl implements BlastService {

	@Resource
	private UserMapper<User> userMapper;

	@Resource
	private UserService userService;

	@Resource
	private BlastMapper<Blast> blastMapper;

	@Override
	public Blast addBlast(Map<String, String> map, SessionUser sessionUser) throws BusinessException {
		String content = map.get("content");
		String imgurl = map.get("imgUrl");
		if (StringUtils.isEmpty(content)) {
			throw new BusinessException("吐槽内容不能为空");
		}

		if (content.length() > MaxLengthEnums.MAXLENGTH250.getLength()) {
			throw new BusinessException("内容长度不能超过250字符");
		}
		String cotent = StringUtils.clearHtml(content);
		cotent = addLink(cotent);
		List<Integer> userIds = new ArrayList<Integer>();
		content = addLink(content);
		String formatContent = FormatAt.getInstance(Constant.TYPE_TALK).GenerateRefererLinks(userMapper, cotent,
				userIds);
		Blast blast = new Blast();
		blast.setContent(formatContent);
		blast.setCreateTime(StringUtils.dateFormater.get().format(new Date()));
		blast.setUserId(sessionUser.getUserId());
		blast.setImageUrl(imgurl);
		blastMapper.insert(blast);
		blast.setUserName(sessionUser.getUserName());
		blast.setUserIcon(sessionUser.getUserIcon());
		blast.setCommentCount(0);
		// 更新积分

		User paramUser = new User();
		paramUser.setUserId(sessionUser.getUserId());
		User curUser = userService.findUser(sessionUser.getUserId().toString(), QueryUserType.USERID);
		curUser.setMark(curUser.getMark() + MarkEnums.MARK2.getMark());
		userMapper.updateSelective(curUser);

		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setArticleId(blast.getBlastId());
		noticeParm.setNoticeType(NoticeType.ATINBLAST);
		noticeParm.setAtUserIds(userIds);
		noticeParm.setSendUserId(sessionUser.getUserId());
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();

		return blast;
	}

	private String addLink(String str) {
		String regex = "((http:|https:)//|www.)[^[A-Za-z0-9\\._\\?%&+\\-=/#]]*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		StringBuffer result = new StringBuffer();
		while (matcher.find()) {
			StringBuffer replace = new StringBuffer();
			if (matcher.group().contains("http")) {
				replace.append("<a href=").append(matcher.group());
			} else {
				replace.append("<a href=http://").append(matcher.group());
			}

			replace.append(" target=\"_blank\">" + matcher.group() + "</a>");
			matcher.appendReplacement(result, replace.toString());
		}
		matcher.appendTail(result);
		return result.toString();
	}

	@Override
	public List<Blast> queryLatestBlast() {
		SimplePage page = new SimplePage(0, 5);
		return blastMapper.selectBaseInfoList(null, page);
	}

	@Override
	public UlewoPaginationResult<Blast> queryAllBlast(Map<String, String> map) {
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		int count = this.blastMapper.selectBaseInfoCount(map);
		int pageSize = PageSize.SIZE20.getSize();
		if (StringUtils.isNumber(map.get("rows"))) {
			pageSize = Integer.parseInt(map.get("rows"));
		}
		SimplePage page = new SimplePage(page_no, count, pageSize);
		List<Blast> list = this.blastMapper.selectBaseInfoList(map, page);
		UlewoPaginationResult<Blast> result = new UlewoPaginationResult<Blast>(page, list);
		return result;
	}

	@Override
	public UlewoPaginationResult<Blast> queryBlastByUserId(Map<String, String> map) throws BusinessException {
		String userIdStr = map.get("userId");
		if (!StringUtils.isNumber(userIdStr)) {
			throw new BusinessException("参数错误");
		}
		int page_int = 0;
		String pageStr = map.get("page");
		if (StringUtils.isEmpty(pageStr) && StringUtils.isNumber(pageStr)) {
			page_int = Integer.parseInt(pageStr);
		}
		int count = blastMapper.selectBaseInfoCount(map);
		SimplePage page = new SimplePage(page_int, count, PageSize.SIZE20.getSize());
		List<Blast> list = this.blastMapper.selectBaseInfoList(map, page);
		UlewoPaginationResult<Blast> result = new UlewoPaginationResult<Blast>(page, list);
		return result;
	}

	@Override
	public void deleteBlast(Map<String, String> map, SessionUser sessionUser) {
		//this.blastMapper.delete(map);
	}

	@Override
	public void deleteBlastByAdmin(Map<String, String> map) {
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		for (String key : keys) {
			map.put("blastId", key);
			this.blastMapper.delete(map);
		}
	}

	@Override
	public Blast selectBlast(Map<String, String> map) throws BusinessException {
		String blastIdstr = map.get("blastId");
		if (StringUtils.isEmpty(blastIdstr) || !StringUtils.isNumber(blastIdstr)) {
			throw new BusinessException("参数错误!");
		}
		Blast blast = this.blastMapper.selectBaseInfo(map);
		return blast;
	}
}