package com.ulewo.util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class StringUtils {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private final static String[] static_ext = { "jpg", "png", "gif", "bmp",
			"JPG", "PNG", "GIF", "BMP" };

	public final static SimpleDateFormat dateFormater = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public final static SimpleDateFormat dateFormater2 = new SimpleDateFormat(
			"yyyy-MM-dd");

	public final static SimpleDateFormat dateFormater3 = new SimpleDateFormat(
			"yyyy");
	private static final SimpleDateFormat dateFormater4 = new SimpleDateFormat(
			"HH:mm");

	private static final SimpleDateFormat dateFormater5 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;
	private static final long ONE_DAY = 86400000L;

	private static final String JUST_NOW = "刚刚";
	// private static final String ONE_SECOND_AGO = "秒前";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";
	private static final String ONE_DAY_AGO = "天前";

	// MD5加密
	public static String encodeByMD5(String originString) {

		if (originString != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] results = md.digest(originString.getBytes());
				String resultString = byteArrayToHexString(results);
				return resultString.toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	private static String byteArrayToHexString(byte[] b) {

		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {

		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	// 时间对比
	public static boolean BalanceDate(String day1, String day2)
			throws Exception {

		boolean flag = true;
		DateFormat df = DateFormat.getDateInstance();
		try {
			flag = df.parse(day1).before(df.parse(day2));
		} catch (ParseException e) {
			throw e;
		}
		return flag;
	}

	public static String clearHtml(String str) {

		if (isNotEmpty(str)) {
			return str.replaceAll("<[.[^<]]*>", "").replaceAll("[\\n|\\r]", "")
					.replaceAll("&nbsp;", "");
		} else {
			return str;
		}
	}

	public static String formateHtml(String html) {

		if (isNotEmpty(html)) {
			html = html.replaceAll(" ", "&nbsp;");
			html = html.replaceAll("<", "&lt;");
			html = html.replaceAll("\n", "<br>");
			return html;
		}
		return html;
	}

	public static String reFormateHtml(String html) {

		if (isNotEmpty(html)) {
			html = html.replaceAll("&nbsp;", " ");
			html = html.replaceAll("&lt;", "<");
			html = html.replaceAll("<br>", "\n");
			return html;
		}
		return html;
	}

	// 获取字符长度
	public static int getRealLength(String str) {

		int len = 0;
		String[] arrayVal = str.split("");
		for (int i = 1; i < arrayVal.length; i++) {
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
	public static boolean isNotEmpty(String str) {

		if (null != str && !"".equals(str)) {
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
	public static boolean isEmpty(String str) {

		if (null == str || "".equals(str)) {
			return true;
		} else if ("".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String dateStr) {
		Date date = null;
		Date day = null;
		Date curDate = new Date();
		Date curDay = null;
		try {
			date = dateFormater.parse(dateStr);
			day = dateFormater2.parse(dateStr);
			curDay = dateFormater2.parse(dateFormater2.format(curDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long delta = curDay.getTime() - day.getTime();
		// 如果是当天，精确到时分秒
		if (delta == 0) {
			delta = curDate.getTime() - date.getTime();
		}
		if (delta < 1L * ONE_MINUTE) {
			// long seconds = toSeconds(delta);
			// return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
			return JUST_NOW;
		}
		if (delta < 45L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
		}
		if (delta < 48L * ONE_HOUR) {
			return "昨天" + "  " + dateFormater4.format(date);
		}
		if (delta < 72L * ONE_HOUR) {
			return "前天" + "  " + dateFormater4.format(date);
		}
		if (delta < 30L * ONE_DAY) {
			long days = toDays(delta);
			if (days > 2 && days <= 7) {
				return days + ONE_DAY_AGO;
			}
		}
		return dateFormater5.format(date);
	}

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	private static long toDays(long date) {
		return toHours(date) / 24L;
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {

		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.format(today);
			String timeDate = dateFormater2.format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {

		try {
			return dateFormater.parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String pathRelative2Absolutely(String con) {

		if (null != con) {
			// con = con.replace("../", Constant.WEBSTIE);
			return con;
		} else {
			return "";
		}

	}

	public static boolean isNumber(String str) {

		String checkPassWord = "^[0-9]+$";
		if (null == str) {
			return false;
		}
		if (!str.matches(checkPassWord)) {
			return false;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	public static String getImages(String content) {

		StringBuffer sbf = new StringBuffer();
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(content);
		List<TagNode> nodeList = allNode.getElementListByName("img", true);
		String image = "";
		if (nodeList != null) {
			for (TagNode node : nodeList) {
				image = String.valueOf(node.getAttributeByName("src")).trim();
				if (!image.contains("emotion")
						&& !image.contains("grey.gif")
						&& image.contains(".")
						&& ArrayUtils.contains(static_ext,
								image.substring(image.lastIndexOf(".") + 1))) {
					sbf.append(image + "|");
				}
			}
		}
		if (sbf.length() > 0) {
			sbf.substring(sbf.lastIndexOf("|"));
		}
		return sbf.toString();
	}

	public static boolean checkEmail(String email) {
		String checkEmail = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
		if (!isEmpty(email)) {
			return email.matches(checkEmail);
		} else {
			return false;
		}
	}

	public static boolean checkUserName(String userName) {
		String checkUserName = "^[\\w\\u4e00-\\u9fa5]+$";
		if (!isEmpty(userName)) {
			return userName.matches(checkUserName);
		} else {
			return false;
		}
	}

	public static boolean checkPassWord(String password) {
		String checkPassWord = "^[0-9a-zA-Z]+$";
		if (!isEmpty(password)) {
			return password.matches(checkPassWord);
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		/*try {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, 1);
			Date curDate = c.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
			String curDatestr = format.format(curDate);
			curDate = format.parse(curDatestr);
			Date d = format.parse("2014.09.06");
			boolean flag = d.after(curDate);
			System.out.println(flag);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		System.out.println(cleanJs("wewe<script type=\"text/javascript\" id=\"wumiiRelatedItems\"/>"));
	}
	
	public static boolean isYesterday(String date) {

		try {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -1);
			Date curDate = c.getTime();
			SimpleDateFormat format = dateFormater2;
			String curDatestr = format.format(curDate);
			curDate = format.parse(curDatestr);
			Date d = format.parse(date);
			int a = d.compareTo(curDate);
			if(a==0){
				return true;
			}
			return false;
		} catch (ParseException e) {
			return false;
		}
	}
	
	public static boolean beforeNowDate(String date) {

		try {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, 1);
			Date curDate = c.getTime();
			SimpleDateFormat format = dateFormater2;
			String curDatestr = format.format(curDate);
			curDate = format.parse(curDatestr);
			Date d = format.parse(date);
			boolean flag = d.before(curDate);
			return flag;
		} catch (ParseException e) {
			return false;
		}
	}

	public static Map<String, Integer> getTotalDayAndFirstDay4Month(int year,
			int month, int day) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		// 获取一个月有多少天
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);// Java月份才0开始算 1代表上一个月2月
		int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
		// 获取当前日期是星期几
		cal.set(Calendar.DATE, day);
		int w = cal.get(Calendar.DAY_OF_WEEK);
		result.put("totalDay", dateOfMonth);
		result.put("firstDay", w);
		return result;
	}
	
	public static String cleanJs(String htmlStr){
		//String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; 
		String regEx_script = "<script[^>]*?\\/>"; 
		 Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
	        Matcher m_script = p_script.matcher(htmlStr);  
	        htmlStr = m_script.replaceAll(""); // 过滤script标签  
	        return htmlStr;
	}
}
