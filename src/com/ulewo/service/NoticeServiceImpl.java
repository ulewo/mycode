package com.ulewo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ulewo.enums.NoticeStatus;
import com.ulewo.enums.PageSize;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.NoticeMapper;
import com.ulewo.model.Notice;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	@Resource
	private NoticeMapper<Notice> noticeMapper;

	@Override
	public UlewoPaginationResult<Notice> queryNoticeByUserId(Map<String, String> map, Integer userId) {
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		map.put("userId", String.valueOf(userId));
		map.put("status", null);
		int count = queryNoticeCountByUserId(map);
		int pageSize = PageSize.SIZE20.getSize();
		if (StringUtils.isNumber(map.get("rows"))) {
			pageSize = Integer.parseInt(map.get("rows"));
		}
		SimplePage page = new SimplePage(page_no, count, pageSize);
		List<Notice> list = this.noticeMapper.selectBaseInfoList(map, page);
		UlewoPaginationResult<Notice> result = new UlewoPaginationResult<Notice>(page, list);
		return result;
	}

	@Override
	public int queryNoticeCountByUserId(Map<String, String> param) {
		return noticeMapper.selectBaseInfoCount(param);
	}

	public void deleteNotice(Map<String, String> map, Integer userId) throws BusinessException {
		if (StringUtils.isEmpty(map.get("key"))) {
			throw new BusinessException("参数错误!");
		}
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		Map<String, String> param = null;
		for (String key : keys) {
			param = new HashMap<String, String>();
			param.put("id", key);
			param.put("userId", String.valueOf(userId));
			int count = this.noticeMapper.delete(param);
			if (count == 0) {
				throw new BusinessException("没有找到相应的记录!");
			}
		}

	}

	public Notice readNotice(Map<String, String> map, Integer userId) {
		map.put("userId", String.valueOf(userId));
		Notice notice = this.noticeMapper.selectBaseInfo(map);
		notice.setStatus(NoticeStatus.STATUS1.getStauts());
		// 更新notice状态
		this.noticeMapper.updateSelective(notice);
		return notice;
	}

	@Override
	public void signNoticeRead(Map<String, String> map, Integer userId) throws BusinessException {
		if (StringUtils.isEmpty(map.get("key"))) {
			throw new BusinessException("参数错误!");
		}
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		Notice notice = null;
		for (String key : keys) {
			notice = new Notice();
			notice.setId(Integer.parseInt(key));
			notice.setReceivedUserId(userId);
			notice.setStatus(NoticeStatus.STATUS1.getStauts());
			int count = this.noticeMapper.updateSelective(notice);
			if (count == 0) {
				throw new BusinessException("没有找到相应的记录!");
			}
		}
	}
}
