package com.ulewo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.ulewo.model.SessionUser;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2013-10-31 下午3:40:09
 * @version 0.1.0
 * @copyright yougou.com
 */
public class BaseAction {
	public Map<String, String> builderParams(HttpServletRequest request,
			boolean isDecode) throws UnsupportedEncodingException {
		Map params = request.getParameterMap();
		Map<String, String> resultMap = new HashMap<String, String>();
		if (params != null && params.size() > 0) {
			for (Iterator iterator = params.entrySet().iterator(); iterator
					.hasNext();) {
				Entry p = (Entry) iterator.next();
				if (p.getValue() != null
						&& !StringUtils.isEmpty(p.getValue().toString())) {
					String values[] = (String[]) p.getValue();
					// p.setValue(values[0]);
					if (isDecode) {
						resultMap.put(String.valueOf(p.getKey()),
								URLDecoder.decode(values[0], "UTF-8"));
					} else {
						resultMap.put(String.valueOf(p.getKey()), values[0]);
					}

				}
			}
		}
		return resultMap;
	}

	public SessionUser getSessionUser(HttpSession session) {
		Object user = session.getAttribute("user");
		if (null == user) {
			return null;
		} else {
			return (SessionUser) user;
		}
	}

	public Integer getSessionUserId(HttpSession session) {
		Object user = session.getAttribute("user");
		if (null == user) {
			return null;
		} else {
			return ((SessionUser) user).getUserId();
		}
	}
}
