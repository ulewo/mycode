package com.lhl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.lhl.entity.ReArticle;
import com.lhl.entity.User;
import com.lhl.service.ReArticleService;
import com.lhl.util.MyCookie;
import com.lhl.util.StringUtil;

public class AddReArticleListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "success";
		ReArticle article = new ReArticle();
		try {
			String articleId = request.getParameter("articleid");
			String content = request.getParameter("content");
			if (StringUtil.isNumber(articleId)) {
				String ip = request.getRemoteAddr();
				MyCookie myCookie = MyCookie.getInstance();
				SimpleDateFormat formate = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				// 获取当前用户对此条记录评论的记录数
				Cookie cookie = myCookie.getCookieByName(request, ip + "."
						+ articleId + ".recount");
				// cookie为空，那么就是第一次评论。
				boolean canReplay = true;
				if (null == cookie) {
					myCookie.addCookie(response, ip + "." + articleId
							+ ".recount", "1", 365 * 24 * 60 * 60);
					// 记录当前评论的时间
					myCookie.addCookie(response, ip + "." + articleId
							+ ".retime", formate.format(new Date()),
							365 * 24 * 60 * 60);
				} else {
					int reCount = Integer.parseInt(myCookie.getValueByKey(
							request, ip + "." + articleId + ".recount"));
					if (reCount < 5) {
						myCookie.addCookie(response, ip + "." + articleId
								+ ".recount", reCount + 1 + "",
								365 * 24 * 60 * 60);
					} else {
						// 如果评论数大于5，那么就获取文章第一评论的时间
						String firstReTime = myCookie.getValueByKey(request, ip
								+ "." + articleId + ".retime");
						Date firstDateTime = formate.parse(firstReTime);
						Date curDateTime = new Date();
						if (curDateTime.getTime() - firstDateTime.getTime() < 60 * 1000 * 10)// 10分钟回复记录数大于5那么就不能再回复
						{
							result = "frequently";
							canReplay = false;
						}
					}
				}
				if (canReplay) {
					Object userObj = request.getSession().getAttribute("user");
					User sessionUser = null;
					if (userObj != null) {
						sessionUser = (User) userObj;
					}
					article.setArticleId(Integer.parseInt(articleId));
					article.setContent(content);
					if (sessionUser != null) {
						article.setAvatar(sessionUser.getAvatar());
						article.setUid(sessionUser.getUid());
						article.setUserName(sessionUser.getUserName());
					}
					article.setReTime(new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(new Date()));
					ReArticleService.getInstance().addReArticle(article);
				}
			}
		} catch (Exception e) {
			result = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		obj.put("article", article);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		super.service(req, resp);
	}
}
