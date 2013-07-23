package com.lhl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;

import com.lhl.entity.Article;
import com.lhl.spider.Spider;
import com.lhl.util.StringUtil;

public class SpiderServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String uploadPath = request.getSession().getServletContext()
				.getRealPath("/upload");
		String source = request.getParameter("source");
		int totalPage = Integer.parseInt(request.getParameter("totalPage"));
		int result = 0;
		// 抓全部
		if (StringUtil.isEmpty(source)) {
			result = result + getQiushibaike(uploadPath, totalPage);
			result = result + getHaHa(uploadPath, totalPage);
			result = result + getPengfu(uploadPath, totalPage);
			// 抓糗事百科
		} else if ("Q".equals(source)) {
			result = result + getQiushibaike(uploadPath, totalPage);
			// 抓哈哈MX
		} else if ("H".equals(source)) {
			result = result + getHaHa(uploadPath, totalPage);
			// 抓捧腹
		} else if ("P".equals(source)) {
			result = result + getPengfu(uploadPath, totalPage);
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	// 糗事百科
	private int getQiushibaike(String uploadPath, int totalPage) {

		int result = 0;
		// 抓糗事百科
		try {
			for (int i = 1; i <= totalPage; i++) {
				System.out.println("正在抓第" + i + "页");
				String tqHtml = Spider
						.getQiushibaikeHTML("http://www.qiushibaike.com/hot/page/"
								+ i);
				List<Article> tqList = Spider.getQiushibaikeContent(tqHtml);
				if (tqList == null || tqList.isEmpty()) {
					break;
				}
				result += Spider.addToBataBase(tqList, uploadPath + "/big",
						uploadPath + "/small");
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	// haha
	private int getHaHa(String uploadPath, int totalPage) {

		int result = 0;
		try {
			for (int i = 1; i <= totalPage; i++) {
				System.out.println("正在抓第" + i + "页");
				String thHtml = Spider
						.getHahamxHTML("http://www.haha.mx/good/day/" + i);
				List<Article> thList = Spider.getHahamxContent(thHtml);
				if (thList == null || thList.isEmpty()) {
					break;
				}
				result += Spider.addToBataBase(thList, uploadPath + "/big",
						uploadPath + "/small");
				try {
					Thread.sleep(2000);
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	// 捧腹
	private int getPengfu(String uploadPath, int totalPage) {

		int result = 0;
		try {
			// 捧腹
			for (int i = 1; i <= totalPage; i++) {
				String tpHtml = Spider
						.getPengfuHTML("http://www.pengfu.com/index_" + i
								+ ".html");
				List<Article> tpList = Spider.getPengfuContent(tpHtml);
				if (tpList == null || tpList.isEmpty()) {
					break;
				}
				result += Spider.addToBataBase(tpList, uploadPath + "/big",
						uploadPath + "/small");
				try {
					Thread.sleep(2000);
				} catch (Exception e) {

				}
			}

		} catch (Exception e) {

		}
		return result;
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		super.service(req, resp);
	}
}
