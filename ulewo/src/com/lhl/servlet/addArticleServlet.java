package com.lhl.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.lhl.entity.Article;
import com.lhl.entity.User;
import com.lhl.service.ArticleService;
import com.lhl.util.Constant;
import com.lhl.util.DrowImage;
import com.lhl.util.StringUtil;
import com.lhl.util.UpYun;

public class addArticleServlet extends HttpServlet
{
	public static final String ATTACH_TEMP_DIR = System.getProperty("user.home") + "/temp/";

	public static final int width = 700;

	public static final int height = 700;

	public static final int small_width = 150;

	public static final int small_height = 150;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{

		String result = "success";
		String loginType = "login";
		try
		{
			Object userObj = request.getSession().getAttribute("user");
			User sessionUser = null;
			if (userObj != null)
			{
				sessionUser = (User) userObj;
			}
			String content = request.getParameter("content");
			String tag = request.getParameter("tag");
			String img = request.getParameter("img");
			String videoUrl = request.getParameter("video_url");
			String medioType = request.getParameter("medio_type");
			Article article = new Article();
			article.setContent(content);
			if (sessionUser != null)
			{
				article.setAvatar(sessionUser.getAvatar());
				article.setUid(sessionUser.getUid());
				article.setStatus(Constant.STATUS_Y);
			}
			else
			{
				article.setStatus(Constant.STATUS_N);
				loginType = "nologin";
			}
			article.setDown(0);
			article.setUp(0);
			article.setPostTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			article.setSourceFrom(Constant.SOURCEFROM_A);
			if (StringUtil.isNotEmpty(img) && Constant.MEDIO_TYPE_P.equals(medioType))
			{
				article.setImgUrl(uploadPicture2UpYun(request, img));
			}
			/*
			 * // 如果是视频 if (Constant.MEDIO_TYPE_V.equals(medioType)) { Video
			 * video = VideoUtil.getVideoInfo(videoUrl); if (video == null) { //
			 * 视频获取不到，给出错误提示，文章不保存 result = "error"; } else {
			 * article.setVideoUrl(video.getFlash()); } } else {
			 * 
			 * }
			 */
			article.setMedioType(medioType);
			article.setTag(tag);
			article.setVideoUrl(videoUrl);
			ArticleService.getInstance().addArticle(article);
		}
		catch (Exception e)
		{
			result = "error";
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		obj.put("loginType", loginType);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(String.valueOf(obj));
	}

	private String uploadPicture2UpYun(HttpServletRequest request, String imgUrl) throws Exception
	{

		String bigPath = "/upload/big";
		String smallPath = "/upload/small";
		InputStream in = null;
		ByteArrayOutputStream byteArrayOut = null;
		String imgName = "";
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String sourceArticleId = StringUtil.encodeByMD5((System.currentTimeMillis() + ""));
		String type = imgUrl.substring(imgUrl.lastIndexOf("."));
		File soruceFile = new File(ATTACH_TEMP_DIR + imgUrl);
		try
		{
			String bigDirPath = bigPath + "/" + year + "/" + month + "/" + day;
			File bigDir = new File(bigDirPath);
			if (!bigDir.exists())
			{
				bigDir.mkdirs();
			}
			String bigFilePath = bigDirPath + "/" + sourceArticleId + type;// 大图片存储的位置

			String smallDirPath = smallPath + "/" + year + "/" + month + "/" + day;
			File smallDir = new File(smallDirPath);
			if (!smallDir.exists())
			{
				smallDir.mkdirs();
			}
			String samllFilePath = smallDirPath + "/" + sourceArticleId + type; // 小图片存储的位置

			imgName = year + "/" + month + "/" + day + "/" + sourceArticleId + type;
			createTemp();
			in = new FileInputStream(soruceFile);
			BufferedImage srcImage = ImageIO.read(in);

			UpYun upYun = UpYun.getInstance(Constant.UPYUN_HOST, Constant.UPYUN_HOST, Constant.UPYUN_PWD);
			// 如果上传的图片宽度 或者高度大于700 或者大于1M的 需要重新画原图 和小图
			if (srcImage.getWidth() > width || soruceFile.length() > 1024 * 1024)
			{
				srcImage = DrowImage.resize2(srcImage, width);
				byteArrayOut = new ByteArrayOutputStream();
				ImageIO.write(srcImage, type.replace(".", ""), byteArrayOut);
				byte[] data = byteArrayOut.toByteArray();
				upYun.writeFile(bigFilePath, data, true);
				// DrowImage.saveImageAsJpg(ATTACH_TEMP_DIR + imgUrl,
				// bigFilePath, width, 0, false);
				// DrowImage.saveImageAsJpg(ATTACH_TEMP_DIR + imgUrl,
				// samllFilePath, small_width, small_height, true);
			}
			else
			// 如果图片不大把源图片拷贝到指定目录。
			{
				in = new FileInputStream(soruceFile);
				upYun.writeFile(bigFilePath, in, true);
			}
			// 画小图
			in = new FileInputStream(soruceFile);
			srcImage = ImageIO.read(in);
			int sw = srcImage.getWidth();
			int sh = srcImage.getHeight();
			// 如果图片大于150
			if (sw > small_width || sh > small_height)
			{
				srcImage = DrowImage.resize(srcImage, small_width, small_height);
			}
			byteArrayOut = new ByteArrayOutputStream();
			ImageIO.write(srcImage, type.replace(".", ""), byteArrayOut);
			byte[] datasmall = byteArrayOut.toByteArray();
			upYun.writeFile(samllFilePath, datasmall, true);

		}
		catch (Exception e)
		{
			imgName = "";
			throw e;
		}
		finally
		{
			try
			{
				if (null != in)
				{
					in.close();
					in = null;
				}
				if (null != byteArrayOut)
				{
					byteArrayOut.close();
				}
			}
			catch (IOException e)
			{
			}
			// 删除临时文件
			new File(ATTACH_TEMP_DIR + imgUrl).delete();
		}
		return imgName;
	}

	private String uploadPicture2Local(HttpServletRequest request, String imgUrl)
	{

		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		String bigPath = uploadPath + "/big";
		String smallPath = uploadPath + "/small";
		InputStream in = null;
		OutputStream out = null;
		String imgName = "";
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String sourceArticleId = StringUtil.encodeByMD5((System.currentTimeMillis() + ""));
		String type = imgUrl.substring(imgUrl.lastIndexOf("."));
		File soruceFile = new File(ATTACH_TEMP_DIR + imgUrl);
		try
		{
			String bigDirPath = bigPath + "/" + year + "/" + month + "/" + day;
			File bigDir = new File(bigDirPath);
			if (!bigDir.exists())
			{
				bigDir.mkdirs();
			}
			String bigFilePath = bigDirPath + "/" + sourceArticleId + type;//

			String smallDirPath = smallPath + "/" + year + "/" + month + "/" + day;
			File smallDir = new File(smallDirPath);
			if (!smallDir.exists())
			{
				smallDir.mkdirs();
			}
			String samllFilePath = smallDirPath + "/" + sourceArticleId + type; //

			imgName = year + "/" + month + "/" + day + "/" + sourceArticleId + type;
			createTemp();
			in = new FileInputStream(soruceFile);
			out = new FileOutputStream(new File(bigFilePath));
			int len = 0;
			byte[] b = new byte[1024 * 5];
			while ((len = in.read(b)) != -1)
			{
				out.write(b, 0, len);
			}
			out.flush();
			// 画小图
			in = new FileInputStream(soruceFile);
			BufferedImage srcImage = ImageIO.read(in);
			if (srcImage.getWidth() > small_width || soruceFile.length() > 1024 * 1024)
			{
				DrowImage.saveImageAsJpg(ATTACH_TEMP_DIR + imgUrl, samllFilePath, small_width, small_width, true);

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			soruceFile.delete();
		}
		return imgName;
	}

	private void createTemp()
	{

		File dir = new File(ATTACH_TEMP_DIR);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		super.service(req, resp);
	}
}
