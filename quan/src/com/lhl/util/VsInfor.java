package com.lhl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class VsInfor {
	private static URLConnection connection;

	public static Map<String, String> getVsInfo(String vsname) {

		connect("http://www.vsa.com.cn/user/user_war/check/check_user_situation.jsp?userName=" + vsname);
		Map<String, String> map = readContents();
		return map;
	}

	private static void connect(String urlString) {

		try {
			URL url = new URL(urlString);
			connection = url.openConnection();
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Map<String, String> readContents() {

		Map<String, String> map = new HashMap<String, String>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
			StringBuffer sb = new StringBuffer();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			String pageCon = sb.toString();
			Document doc = Jsoup.parse(pageCon);
			Elements tables = doc.getElementsByTag("table");
			if (tables.size() > 5) {
				Elements tbodys = tables.get(5).getElementsByTag("tbody");
				Elements trs = tbodys.get(2).getElementsByTag("tr");
				Elements tds1 = trs.get(0).getElementsByTag("td");
				map.put("vsname", tds1.get(1).text());//	用户名
				map.put("vsteam", tds1.get(3).text());//    战队

				Elements tds2 = trs.get(1).getElementsByTag("td");
				map.put("vsnumber", tds2.get(1).text());//	  vsnumber 用户vs号

				Elements tds3 = trs.get(2).getElementsByTag("td");
				map.put("vsexperience", tds3.get(3).text());//	  经验值

				Elements tds4 = trs.get(4).getElementsByTag("td");
				map.put("vsgrade11", tds4.get(1).text());//	    1v1等级
				map.put("vsgrade22", tds4.get(3).text());//	    2v2等级

				Elements tds5 = trs.get(5).getElementsByTag("td");
				map.put("vsintegral11", tds5.get(1).text());//	  1v1积分
				map.put("vsintegral22", tds5.get(3).text());//	  2v2积分

				Elements tds6 = trs.get(6).getElementsByTag("td");
				map.put("vsrank11", tds6.get(1).text());//	  vs 1v1 排名
				map.put("vsrank22", tds6.get(3).text());//	  vs 2v2 排名
			}
			else {
				map = null;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			map = null;
		}
		finally {
			try {
				if (null != in) {
					in.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
