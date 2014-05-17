package com.ulewo.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ulewo.enums.PageSize;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.CommonDynamicMapper;
import com.ulewo.model.CommonDynamic;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-5-17 上午11:25:49
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@Service("commonDynamicService")
public class CommonDynamicServiceImpl implements CommonDynamicService {
	@Resource
	private CommonDynamicMapper<CommonDynamic> commonDynamicMapper;

	@Override
	public UlewoPaginationResult<CommonDynamic> selectDynamic(Map<String, String> map, Integer userId)
			throws BusinessException {
		String paramUserId = map.get("userId");
		if (!StringUtils.isNumber(paramUserId)) {
			throw new BusinessException("参数错误");
		}
		if (userId != null && userId.intValue() == Integer.parseInt(paramUserId)) {
			return this.selectFocusAllDynamic(map);
		} else {
			return this.selectUserAllDynamic(map);
		}
	}

	@Override
	public int selectFocusAllDynamicCount(Map<String, String> map) throws BusinessException {
		String userId = map.get("userId");
		if (!StringUtils.isNumber(userId)) {
			throw new BusinessException("参数错误");
		}
		int count = this.commonDynamicMapper.selectFocusAllDynamicCount(map);
		return count;
	}

	@Override
	public UlewoPaginationResult<CommonDynamic> selectFocusAllDynamic(Map<String, String> map) throws BusinessException {
		String userId = map.get("userId");
		if (!StringUtils.isNumber(userId)) {
			throw new BusinessException("参数错误");
		}
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		int pageSize = PageSize.SIZE20.getSize();
		if (StringUtils.isNumber(map.get("rows"))) {
			pageSize = Integer.parseInt(map.get("rows"));
		}
		int count = this.commonDynamicMapper.selectFocusAllDynamicCount(map);
		SimplePage page = new SimplePage(page_no, count, pageSize);
		List<CommonDynamic> list = this.commonDynamicMapper.selectFocusAllDynamic(map, page);
		UlewoPaginationResult<CommonDynamic> result = new UlewoPaginationResult<CommonDynamic>(page, list);
		return result;
	}

	@Override
	public int selectUserAllDynamicCount(Map<String, String> map) throws BusinessException {
		String userId = map.get("userId");
		if (!StringUtils.isNumber(userId)) {
			throw new BusinessException("参数错误");
		}
		int count = this.commonDynamicMapper.selectUserAllDynamicCount(map);
		return count;
	}

	@Override
	public UlewoPaginationResult<CommonDynamic> selectUserAllDynamic(Map<String, String> map) throws BusinessException {
		String userId = map.get("userId");
		if (!StringUtils.isNumber(userId)) {
			throw new BusinessException("参数错误");
		}
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		int pageSize = PageSize.SIZE20.getSize();
		if (StringUtils.isNumber(map.get("rows"))) {
			pageSize = Integer.parseInt(map.get("rows"));
		}
		int count = this.commonDynamicMapper.selectUserAllDynamicCount(map);
		SimplePage page = new SimplePage(page_no, count, pageSize);
		List<CommonDynamic> list = this.commonDynamicMapper.selectUserAllDynamic(map, page);
		UlewoPaginationResult<CommonDynamic> result = new UlewoPaginationResult<CommonDynamic>(page, list);
		return result;
	}
}
