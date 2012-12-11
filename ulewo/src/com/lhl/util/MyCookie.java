package com.lhl.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyCookie
{
	private MyCookie()
	{

	};

	private static MyCookie instance;

	public synchronized static MyCookie getInstance()
	{

		if (instance == null)
		{
			instance = new MyCookie();
		}
		return instance;
	}

	//增加
	public void addCookie(HttpServletResponse response, String name, String value, int maxAge)
	{

		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0)
			cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public String getValueByKey(HttpServletRequest request, String key)
	{

		Cookie[] cookies = request.getCookies();
		String value = "";
		if (null != cookies)
		{
			for (Cookie cookie : cookies)
			{
				if (key.equals(cookie.getName()))
				{
					value = cookie.getValue();
					break;
				}
			}
		}
		return value;
	}

	//通过key获取coocke
	public Cookie getCookieByName(HttpServletRequest request, String name)
	{

		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name))
		{
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		}
		else
		{
			return null;
		}
	}

	public Map<String, Cookie> ReadCookieMap(HttpServletRequest request)
	{

		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies)
		{
			for (Cookie cookie : cookies)
			{
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
