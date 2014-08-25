package com.ulewo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.PageSize;
import com.ulewo.enums.QueryUserType;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.SignInMapper;
import com.ulewo.model.Calendar4Signin;
import com.ulewo.model.DaySignInfo;
import com.ulewo.model.SessionUser;
import com.ulewo.model.SignIn;
import com.ulewo.model.User;
import com.ulewo.util.Constant;
import com.ulewo.util.SimplePage;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;
import com.ulewo.vo.SignInVo;

@Service("signInService")
public class SignInServiceImpl implements SignInService {
	@Resource
	private SignInMapper<SignIn> signInMapper;

	@Resource
	private UserService userService;

	private static final int startYear = 2013;

	@Override
	public Map<String, Object> signInInfo(SessionUser sessionUser)
			throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		String curDate = StringUtils.dateFormater2.format(new Date());

		if (null == sessionUser) {
			result.put("isSignIn", false);
			result.put("mySignInCount", 0);
		} else {
			Map<String, String> param = new HashMap<String, String>();
			param.put("userId", String.valueOf(sessionUser.getUserId()));
			// 查询用户所有的签到数量
			int mySignInCount = this.signInMapper.selectBaseInfoCount(param);
			param.put("signDate", curDate);
			SignIn signIn = this.signInMapper.selectBaseInfo(param);
			if (null != signIn) {
				result.put("isSignIn", true);
				result.put("mySignInCount", mySignInCount);
			} else {
				result.put("isSignIn", false);
				result.put("mySignInCount", mySignInCount);
			}
		}
		Map<String, String> param2 = new HashMap<String, String>();
		param2.put("signDate", curDate);
		// 查询当天的签到数量
		int count = this.signInMapper.selectBaseInfoCount(param2);
		result.put("allSignInCount", count);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public Map<String, Object> doSignIn(SessionUser sessionUser,
			String sourceFrom) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer userId = sessionUser.getUserId();
		SimpleDateFormat format = StringUtils.dateFormater;
		SimpleDateFormat format2 = StringUtils.dateFormater2;
		String endDate = format2.format(new Date());
		// 判断今天是否已经签到
		Map<String, String> params = new HashMap<String, String>();
		params.put("signDate", endDate);
		params.put("userId", String.valueOf(userId));
		int count = this.signInMapper.selectBaseInfoCount(params);
		if (count > 0) {
			throw new BusinessException("今天已经签到!");
		}
		// 签到
		SignIn signIn = new SignIn();
		signIn.setUserId(userId);
		signIn.setSignDate(endDate);
		signIn.setSignTime(format.format(new Date()));
		signIn.setSourceFrom(sourceFrom);
		this.signInMapper.insert(signIn);

		// 判断是否是连续7天签到
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -6);
		Date date = c.getTime();
		String startDate = format2.format(date);
		params = new HashMap<String, String>();
		params.put("startTm", startDate);
		params.put("endTm", endDate);
		params.put("userId", String.valueOf(userId));
		count = this.signInMapper.selectBaseInfoCount(params);
		int reamrk = 2;
		if (count >= Constant.DAYNUM) {
			reamrk = 10;
			result.put("countinueSignIn", true);
		} else {
			result.put("countinueSignIn", false);
		}
		User user = userService.findUser(String.valueOf(userId),
				QueryUserType.USERID);
		user.setMark(user.getMark() + reamrk);
		userService.updateSelective(user);
		return result;
	}

	@Override
	public List<Calendar4Signin> queryUserSigin(SessionUser sessionUser,
			Map<String, String> map) throws BusinessException {
		String yearStr = map.get("year");
		if (StringUtils.isEmpty(yearStr)) {
			yearStr = map.get("maxYear");
		}
		int year = Integer.parseInt(yearStr);
		if (year > Integer.parseInt(map.get("maxYear")) || year < startYear) {
			throw new BusinessException("参数错误");
		}
		map.put("userId", String.valueOf(sessionUser.getUserId()));
		List<SignIn> list = this.signInMapper.selectAllSignInByYear(map);
		Map<String, String> siginMap = new HashMap<String, String>();
		// 签到情况放到map中
		for (SignIn sign : list) {
			siginMap.put(sign.getSignDate(), sign.getSignDate());
		}
		// 获取日历信息
		List<Calendar4Signin> resultlist = new ArrayList<Calendar4Signin>();
		SimpleDateFormat formate = StringUtils.dateFormater2;
		for (int i = 1; i <= 12; i++) {
			Calendar4Signin sigin = new Calendar4Signin();
			resultlist.add(sigin);
			Map<String, Integer> dayMonth = StringUtils
					.getTotalDayAndFirstDay4Month(year, i, 1);
			int thisMonthDays = dayMonth.get("totalDay");
			int fristDay = dayMonth.get("firstDay");
			if (i < 10) {
				sigin.setMonth("0" + i);
			} else {
				sigin.setMonth("" + i);
			}
			sigin.setFristDay(fristDay);
			sigin.setMonthDays(thisMonthDays);

			List<DaySignInfo> dayList = new ArrayList<DaySignInfo>();
			sigin.setDayInfos(dayList);
			boolean beforeNowDate = true;
			// 便利日期天数
			for (int j = 1; j <= thisMonthDays; j++) {
				DaySignInfo info = new DaySignInfo();
				dayList.add(info);
				String day = "";
				if (j < 10) {
					day = "0" + j;
				} else {
					day = j + "";
				}
				String curDay = yearStr + "-" + sigin.getMonth() + "-" + day;
				if (curDay.equals(formate.format(new Date()))) {
					info.setCurDay(true);
				} else {
					info.setCurDay(false);
				}
				info.setDay(day);
				if (siginMap.get(curDay) != null) {
					info.setSigninType(true);
				} else {
					info.setSigninType(false);
				}
				if (!beforeNowDate) {
					info.setBeforeNowDate(false);
				} else {
					if (StringUtils.beforeNowDate(curDay)) {
						info.setBeforeNowDate(true);
					} else {
						info.setBeforeNowDate(false);
						beforeNowDate = false;
					}
				}
			}
		}
		return resultlist;
	}

	@Override
	public UlewoPaginationResult<SignIn> queryCurDaySigin(
			Map<String, String> map) {
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		String curDate = StringUtils.dateFormater2.format(new Date());
		map.put("startTm", curDate);
		map.put("endTm", curDate);
		int count = this.signInMapper.selectBaseInfoCount(map);
		SimplePage page = new SimplePage(page_no, count,
				PageSize.SIZE20.getSize());
		List<SignIn> list = this.signInMapper.selectBaseInfoList(map, page);
		UlewoPaginationResult<SignIn> result = new UlewoPaginationResult<SignIn>(
				page, list);
		return result;
	}

	@Override
	public Map<String, Object> api_queryCurDaySigin(Map<String, String> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		int page_no = 0;
		if (StringUtils.isNumber(map.get("pageIndex"))) {
			page_no = Integer.parseInt(map.get("pageIndex"));
		}
		String curDate = StringUtils.dateFormater2.format(new Date());
		map.put("startTm", curDate);
		map.put("endTm", curDate);
		int count = this.signInMapper.selectBaseInfoCount(map);
		SimplePage page = new SimplePage(page_no, count,
				PageSize.SIZE20.getSize());
		List<SignIn> list = this.signInMapper.selectBaseInfoList(map, page);
		List<SignInVo> listVo = new ArrayList<SignInVo>();
		SignInVo signInVo = null;
		for (SignIn signIn : list) {
			signInVo = new SignInVo();
			signInVo.setSignTime(signIn.getShowSignTime());
			signInVo.setUserIcon(signIn.getUserIcon());
			signInVo.setUserId(signIn.getUserId());
			signInVo.setUserName(signIn.getUserName());
			listVo.add(signInVo);
		}
		result.put("page", page);
		result.put("list", listVo);
		return result;
	}

	public SignIn api_signInInfo(SessionUser sessionUser)
			throws BusinessException {
		String curDate = StringUtils.dateFormater2.format(new Date());
		if (null == sessionUser) {
			return null;
		} else {
			Map<String, String> param = new HashMap<String, String>();
			param.put("userId", String.valueOf(sessionUser.getUserId()));
			param.put("signDate", curDate);
			SignIn signIn = this.signInMapper.selectBaseInfo(param);
			return signIn;
		}
	}

	public List<DaySignInfo> queryUserSigin4Api(SessionUser sessionUser,
			Map<String, String> map) throws BusinessException {
		String yearStr = map.get("year");
		if (StringUtils.isEmpty(yearStr)) {
			yearStr = map.get("maxYear");
		}
		int year = Integer.parseInt(yearStr);
		if (year > Integer.parseInt(map.get("maxYear")) || year < startYear) {
			throw new BusinessException("参数错误");
		}
		map.put("userId", String.valueOf(sessionUser.getUserId()));
		String monthStr = map.get("month");
		if (StringUtils.isEmpty(monthStr)) {
			throw new BusinessException("参数错误");
		}
		int month = Integer.parseInt(monthStr);
		if (month < 1 || month > 12) {
			throw new BusinessException("参数错误");
		}
		List<SignIn> list = this.signInMapper.selectAllSignInByYear(map);
		Map<String, String> siginMap = new HashMap<String, String>();
		// 签到情况放到map中
		for (SignIn sign : list) {
			siginMap.put(sign.getSignDate(), sign.getSignDate());
		}
		// 获取日历信息
		SimpleDateFormat formate = StringUtils.dateFormater2;
		Map<String, Integer> dayMonth = StringUtils
				.getTotalDayAndFirstDay4Month(year, month, 1);
		int thisMonthDays = dayMonth.get("totalDay");
		List<DaySignInfo> dayList = new ArrayList<DaySignInfo>();
		boolean beforeNowDate = true;
		if (month < 10) {
			monthStr = "0" + month;
		} else {
			monthStr = month + "";
		}
		// 便利日期天数
		for (int j = 1; j <= thisMonthDays; j++) {
			DaySignInfo info = new DaySignInfo();
			dayList.add(info);
			String day = "";
			if (j < 10) {
				day = "0" + j;
			} else {
				day = j + "";
			}
			String curDay = yearStr + "-" + monthStr + "-" + day;
			if (curDay.equals(formate.format(new Date()))) {
				info.setCurDay(true);
			} else {
				info.setCurDay(false);
			}
			info.setDay(curDay);
			if (siginMap.get(curDay) != null) {
				info.setSigninType(true);
			} else {
				info.setSigninType(false);
			}
			if (!beforeNowDate) {
				info.setBeforeNowDate(false);
			} else {
				if (StringUtils.beforeNowDate(curDay)) {
					info.setBeforeNowDate(true);
				} else {
					info.setBeforeNowDate(false);
					beforeNowDate = false;
				}
			}
		}
		return dayList;
	}
}
