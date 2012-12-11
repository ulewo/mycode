package com.lhl.blog.util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class StringUtil
{
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

	private final static SimpleDateFormat format2 = new SimpleDateFormat("yyyy年MM月");

	// MD5加密
	public static String encodeByMD5(String originString)
	{

		if (originString != null)
		{
			try
			{
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] results = md.digest(originString.getBytes());
				String resultString = byteArrayToHexString(results);
				return resultString.toUpperCase();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return null;
	}

	private static String byteArrayToHexString(byte[] b)
	{

		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
		{
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b)
	{

		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	// 时间对比
	public static boolean BalanceDate(String day1, String day2) throws Exception
	{

		boolean flag = true;
		DateFormat df = DateFormat.getDateInstance();
		try
		{
			flag = df.parse(day1).before(df.parse(day2));
		}
		catch (ParseException e)
		{
			throw e;
		}
		return flag;
	}

	public static String clearHtml(String str)
	{

		if (isNotEmpty(str))
		{
			return str.replaceAll("<[.[^<]]*>", "").replaceAll("[\\n|\\r]", "").replaceAll("&nbsp;", "");
		}
		else
		{
			return str;
		}
	}

	public static String formateHtml(String html)
	{

		html = html.replaceAll(" ", "&nbsp;");
		html = html.replaceAll("<", "&lt;");
		html = html.replaceAll("\n", "<br>");
		return html;
	}

	// 获取字符长度
	public static int getRealLength(String str)
	{

		int len = 0;
		String[] arrayVal = str.split("");
		for (int i = 1; i < arrayVal.length; i++)
		{
			if (arrayVal[i].matches("[^\\x00-\\xff]")) // 全角
				len += 2;
			else
				len += 1;
		}
		return len;
	}

	/**
	 * 判断不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str)
	{

		if (null != str && !"".equals(str))
		{
			return true;
		}
		return false;
	}

	/**
	 * 判断为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str)
	{

		if (null == str || "".equals(str))
		{
			return true;
		}
		return false;
	}

	// 是否是数字
	public static boolean isNumber(String str)
	{

		String checkNumber = "^[0-9]+$";
		if (null != str)
		{
			return str.matches(checkNumber);
		}
		else
		{
			return false;
		}
	}

	public static void main(String args[])
	{

		Calendar calendar = Calendar.getInstance();
		String date = "2010-10-1 12:12:25";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = null;
		try
		{
			curDate = format.parse(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	public static String getIpAddress(HttpServletRequest request)
	{

		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown"))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown"))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown"))
		{
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown"))
		{
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown"))
		{
			ip = request.getRemoteAddr();
		}
		if (StringUtils.isNotBlank(ip) && StringUtils.indexOf(ip, ",") > 0)
		{
			String[] ipArray = StringUtils.split(ip, ",");
			ip = ipArray[0];
		}
		return ip;
	}

	public static String formateTime(String str)
	{

		Date date = null;
		try
		{
			date = format.parse(str);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return format2.format(date);
	}

	public static String getNextMonth(String time)
	{

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try
		{
			date = format.parse(time);
		}
		catch (ParseException e)
		{
			return format.format(new Date());
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, +1);
		return format.format(calendar.getTime());
	}
}
