package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lhl.entity.ReTalk;
import com.lhl.quan.dao.ReTalkDao;
import com.lhl.quan.service.ReTalkService;
import com.lhl.util.Tools;

public class ReTalkServiceImpl implements ReTalkService {

	private ReTalkDao reTalkDao;

	public void setReTalkDao(ReTalkDao reTalkDao) {
		this.reTalkDao = reTalkDao;
	}

	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public void addReTalk(ReTalk retalk) {
		retalk.setContent(Tools.clearHtml(retalk.getContent()));
		retalk.setCreateTime(format.format(new Date()));
		reTalkDao.addReTalk(retalk);
	}

	@Override
	public List<ReTalk> queryReTalk(int offset, int total, int talkId) {
		List<ReTalk> list = reTalkDao.queryReTalk(offset, total, talkId);
		for (ReTalk retalk : list) {
			retalk.setCreateTime(Tools.friendly_time(retalk.getCreateTime()));
		}
		return list;
	}

	@Override
	public int queryReTalkCount(int talkId) {
		return reTalkDao.queryReTalkCount(talkId);
	}

	@Override
	public void deleteReTalk(int reTalkId) {
		reTalkDao.deleteReTalk(reTalkId);
	}

}
