package com.lhl.spider;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import com.lhl.dao.ArticleDao;
import com.lhl.entity.Article;
import com.lhl.util.Constant;
import com.lhl.util.DrowImage;
import com.lhl.util.StringUtil;
import com.lhl.util.UpYun;

/**
 * 
 * 爬虫通用类
 * 
 * @author prestlhh
 * @since 2012-10-06
 * @version 1.0
 */
public class Spider
{
	public static final int width = 700;

	public static final int height = 700;

	public static final int small_width = 150;

	public static final int small_height = 150;

	/**
	 * 日志实例
	 */
	private static Logger log = Logger.getLogger(Spider.class);

	/**
	 * http客户端实例
	 */
	private static DefaultHttpClient httpclient = new DefaultHttpClient();

	/**
	 * 
	 * 从糗事百科获取html(页面编码是utf-8)
	 * 
	 * @param 页面url
	 * @return 返回的html内容
	 * 
	 */
	public static String getQiushibaikeHTML(String url) throws ClientProtocolException, IOException
	{

		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.addHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("Host", "www.qiushibaike.com");
		httpGet.addHeader("Referer", "http://www.qiushibaike.com");
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:15.0) Gecko/20100101 Firefox/15.0.1");
		HttpResponse response = httpclient.execute(httpGet);
		InputStream in = response.getEntity().getContent();
		String html = convertStreamToString(in, "utf-8");
		return html;
	}

	/**
	 * 
	 * 从哈哈MX获取html(页面编码是utf-8)
	 * 
	 * @param 页面url
	 * @return 返回的html内容
	 * 
	 */
	public static String getHahamxHTML(String url) throws ClientProtocolException, IOException
	{

		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("Host", "www.haha.mx");
		httpGet.addHeader("cookie", "MAXAUTH=NO");
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:15.0) Gecko/20100101 Firefox/15.0.1");
		HttpResponse response = httpclient.execute(httpGet);
		InputStream in = response.getEntity().getContent();
		String html = convertStreamToString(in, "utf-8");
		return html;
	}

	/**
	 * 
	 * 从捧腹网获取html(页面编码是gbk)
	 * 
	 * @param 页面url
	 * @return 返回的html内容
	 * 
	 */
	public static String getPengfuHTML(String url) throws ClientProtocolException, IOException
	{

		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("Host", "www.pengfu.com");
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:15.0) Gecko/20100101 Firefox/15.0.1");
		HttpResponse response = httpclient.execute(httpGet);
		InputStream in = response.getEntity().getContent();
		String html = convertStreamToString(in, "gbk");
		return html;
	}

	/**
	 * 为了将InputStream转换成String我们使用函数BufferedReader.readLine().
	 * 我们迭代调用BufferedReader直到其返回null, null意味着没有其他的数据要读取了.
	 * 每一行将会追加到StringBuilder的末尾, StringBuilder将作为String返回。
	 * 
	 * @param 输入流
	 * @param 字符编码
	 * @return 编码后的字符串
	 */
	public static String convertStreamToString(InputStream is, String charset) throws UnsupportedEncodingException
	{

		BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 从糗事百科html中获取article对象集合
	 * 
	 * @param html
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Article> getQiushibaikeContent(String html)
	{

		List<Article> list = new ArrayList<Article>();
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(html);
		List<TagNode> objList = allNode.getElementListByAttValue("class", "block untagged", true, true);
		/*
		 * 不用获取总页数，抓取的页数从前端传过来 // 总页数 TagNode pageNode =
		 * allNode.findElementByAttValue("class", "pagebar", true, true);
		 * List<TagNode> linkList = pageNode.getChildTagList(); int totalPage =
		 * 0; for (TagNode link : linkList) { String pageNumber =
		 * link.getText().toString(); if (pageNumber != null &&
		 * pageNumber.matches("[0-9]+")) { totalPage = Math.max(totalPage,
		 * Integer.parseInt(pageNumber)); } }
		 */
		for (TagNode obj : objList)
		{
			try
			{
				String id = obj.getAttributeByName("id");
				if (id == null)
				{
					continue;
				}
				// 构造Article对象
				Article article = new Article();
				// 设置来源为糗事百科
				article.setSourceFrom("Q");
				// articleId
				String articleId = id.substring(id.lastIndexOf("_") + 1);
				// 设置来源ID(来源+articleId)
				article.setSourceId("Q" + articleId);
				// 好评
				TagNode up = allNode.findElementByAttValue("id", "up-" + articleId, true, true);
				int upCount = Integer.parseInt(String.valueOf(up.getText()));
				article.setUp(upCount);
				// 差评
				TagNode dn = allNode.findElementByAttValue("id", "dn-" + articleId, true, true);
				// 取绝对值
				article.setDown(Math.abs(Integer.parseInt(String.valueOf(dn.getText()))));
				// content 内容节点
				TagNode ct = obj.findElementByAttValue("class", "content", true, true);
				// 发布时间
				String time = String.valueOf(ct.getAttributeByName("title"));
				if (upCount <= Constant.MIN_UP_COUNT)
				{
					return list;
				}
				// 内容
				String content = String.valueOf(ct.getText()).trim();
				// 发布时间
				article.setPostTime(time);
				// 来源时间
				article.setSourceTime(time);
				// 内容
				article.setContent(content);
				// imgURL 图片
				TagNode thumbNode = obj.findElementByAttValue("class", "thumb", true, true);
				if (thumbNode != null)
				{
					TagNode imgNode = thumbNode.findElementByName("img", true);
					if (imgNode != null)
					{
						String imgUrl = String.valueOf(imgNode.getAttributeByName("src")).trim();
						article.setImgUrl(imgUrl);
					}
				}
				// tags 标签
				TagNode tagsNode = obj.findElementByAttValue("class", "tags", true, true);
				String tagStr = "";
				if (tagsNode != null)
				{
					List<TagNode> tags = tagsNode.getElementListByName("a", true);
					for (int i = 0; i < tags.size(); i++)
					{
						TagNode tagNode = tags.get(i);
						if (i == 0)
						{
							tagStr = String.valueOf(tagNode.getText()).trim();
						}
						else
						{
							tagStr = tagStr + "," + String.valueOf(tagNode.getText()).trim();
						}
					}
				}
				// 设置tag
				article.setTag(tagStr);
				list.add(article);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				continue;
			}
		}
		return list;
	}

	/**
	 * 从哈哈MX页html中获取article对象集合
	 * 
	 * @param html
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Article> getHahamxContent(String html)
	{

		List<Article> list = new ArrayList<Article>();
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(html);
		List<TagNode> objList = allNode.getElementListByAttValue("class", "item", true, true);
		/*
		 * // 总页数 TagNode pageNode = allNode.findElementByAttValue("class",
		 * "paging", true, true); List<TagNode> linkList =
		 * pageNode.getElementListByName("a", true); int totalPage = 0; for
		 * (TagNode link : linkList) { String pageNumber =
		 * link.getText().toString(); if (pageNumber != null &&
		 * pageNumber.matches("[0-9]+")) { totalPage = Math.max(totalPage,
		 * Integer.parseInt(pageNumber)); } }
		 */
		for (TagNode tag : objList)
		{
			try
			{
				String id = tag.getAttributeByName("id");
				if (id == null)
				{
					continue;
				}
				// 构造Article对象
				Article article = new Article();
				// 设置来源为哈哈MX
				article.setSourceFrom("H");
				String articleId = id.substring(id.lastIndexOf("-") + 1);
				// 设置来源ID(来源+articleId)
				article.setSourceId("H" + articleId);
				TagNode up = allNode.findElementByAttValue("id", "good-number-" + articleId, true, true);
				int upCount = Integer.parseInt(String.valueOf(up.getText()).trim());
				// 根据顶的数量判断
				if (upCount <= Constant.MIN_UP_COUNT)
				{
					return list;
				}
				article.setUp(upCount);
				TagNode dn = allNode.findElementByAttValue("id", "bad-number-" + articleId, true, true);
				article.setDown(Integer.parseInt(String.valueOf(dn.getText()).trim()));
				TagNode context = allNode.findElementByAttValue("id", "listText-" + articleId, true, true);
				// 内容
				String content = String.valueOf(context.getText()).trim();
				// 内容
				article.setContent(content);
				TagNode pic = allNode.findElementByAttValue("id", "list-pic-" + articleId, true, true);
				if (pic != null)
				{
					String imgURL = "http://image.haha.mx/" + pic.getAttributeByName("path") + "middle/"
							+ pic.getAttributeByName("pic_name");
					article.setImgUrl(imgURL);
				}
				TagNode tn = tag.findElementByAttValue("class", "infor-text", true, true);
				List<TagNode> tns = tn.getElementListByName("span", true);
				for (TagNode time : tns)
				{
					if (time.getText().toString().indexOf("发布") != -1)
					{
						String pubTime = time.getText().toString();
						String postTime = pubTime.substring(0, 19);
						// 发布时间
						article.setPostTime(postTime);
						// 来源时间
						article.setSourceTime(postTime);
						break;
					}
				}
				TagNode tagList = tag.findElementByAttValue("class", "tag", true, true);
				String tagStr = "";
				if (tagList != null)
				{
					List<TagNode> tags = tagList.getElementListByName("a", true);
					for (int i = 0; i < tags.size(); i++)
					{
						TagNode tagNode = tags.get(i);
						if (i == 0)
						{
							tagStr = String.valueOf(tagNode.getText()).trim();
						}
						else
						{
							tagStr = tagStr + "," + String.valueOf(tagNode.getText()).trim();
						}
					}
				}
				// 设置tag
				article.setTag(tagStr);
				// 设置页数
				list.add(article);
			}
			catch (Exception e)
			{
				log.error("从哈哈MX构造article出错..." + e.getMessage());
				continue;
			}
		}
		return list;
	}

	/**
	 * 从捧腹页html中获取article对象集合
	 * 
	 * @param html
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Article> getPengfuContent(String html)
	{

		List<Article> list = new ArrayList<Article>();
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(html);
		List<TagNode> objList = allNode.getElementListByAttValue("class", "post", true, true);
		/*
		 * // 总页数 TagNode pageNode = allNode.findElementByAttValue("class",
		 * "page", true, true); List<TagNode> linkList =
		 * pageNode.getElementListByName("a", true); int totalPage = 0; for
		 * (TagNode link : linkList) { String href =
		 * link.getAttributeByName("href"); String pageNumber =
		 * href.substring(href.indexOf("index_") + 6, href.indexOf(".html")); if
		 * (pageNumber != null && pageNumber.matches("[0-9]+")) { totalPage =
		 * Math.max(totalPage, Integer.parseInt(pageNumber)); } }
		 */
		for (TagNode tag : objList)
		{
			try
			{
				TagNode obj = tag.findElementByAttValue("class", "bdshare_t bds_tools get-codes-bdshare", true, true);
				if (obj == null)
				{
					continue;
				}
				String data = obj.getAttributeByName("data");
				JSONObject json = JSONObject.fromObject(data);
				String url = json.getString("url").trim();
				// 构造Article对象
				Article article = new Article();
				// 设置来源为捧腹
				article.setSourceFrom("P");
				String articleId = url.substring(url.indexOf("indexid_") + 8, url.indexOf(".html"));
				// 设置来源ID
				article.setSourceId("P" + articleId);
				TagNode picText = tag.findElementByAttValue("class", "pic_text", true, true);
				TagNode picNode = picText.findElementByName("img", true);
				if (picNode != null)
				{
					String pic = picNode.getAttributeByName("src");
					if (pic == null || "".equals(pic.trim()))
					{
						continue;
					}
					article.setImgUrl(pic);
				}
				else
				{
					continue;
				}
				TagNode timeTn = tag.findElementByAttValue("class", "time", true, true);
				String time = String.valueOf(timeTn.getText()).trim();
				TagNode h4 = tag.findElementByName("h4", true);
				TagNode contentTag = h4.findElementByName("a", true);
				// 内容
				String content = String.valueOf(contentTag.getText()).trim();
				// 内容
				article.setContent(content);
				TagNode up = allNode.findElementByAttValue("id", "span_SupportNum" + articleId, true, true);
				int upCount = Integer.parseInt(String.valueOf(up.getText()).trim());
				article.setUp(upCount);
				if (upCount <= Constant.MIN_UP_COUNT)
				{
					return list;
				}
				if (time != null && !"".equals(time))
				{
					time += ":00";
					// 长整型的来源时间
					article.setPostTime(time);
					article.setSourceTime(time);
				}
				TagNode dn = allNode.findElementByAttValue("id", "span_OpposeNum" + articleId, true, true);
				article.setDown(Integer.parseInt(String.valueOf(dn.getText()).trim()));
				// tag
				TagNode tagList = allNode.findElementByAttValue("id", "divTagHtml_" + articleId, true, true);
				String tagStr = "";
				if (tagList != null)
				{
					List<TagNode> tags = tagList.getElementListByName("a", true);
					for (int i = 0; i < tags.size(); i++)
					{
						TagNode tagNode = tags.get(i);
						String tmp = String.valueOf(tagNode.getText()).trim();
						if (i == 0)
						{
							tagStr = tmp;
						}
						else
						{
							tagStr = tagStr + "," + tmp;
						}
					}
				}
				// 设置tag
				article.setTag(tagStr);
				// 设置页数
				list.add(article);
			}
			catch (Exception e)
			{
				log.error("从捧腹页构造article出错..." + e.getMessage());
				continue;
			}
		}
		return list;
	}

	/**
	 * 
	 * @param list
	 * @param bigPath
	 * @param smallPath
	 */
	public static int addToBataBase(List<Article> list, String bigPath, String smallPath)
	{

		int count = 0;
		for (Article article : list)
		{
			if (StringUtil.isNotEmpty(article.getImgUrl()))
			{
				String sourceArticleId = article.getSourceId();
				String imgURL = article.getImgUrl();
				// 如果图片抓取失败，就取下一条
				try
				{
					String realImgUrl = uploadPicture2UpYun(sourceArticleId, imgURL, bigPath, smallPath);
					if (StringUtil.isEmpty(realImgUrl))// 路径为null 说明图片太大
					{
						continue;
					}
					article.setImgUrl(realImgUrl);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					continue;
				}
			}
			article.setStatus(Constant.STATUS_Y);
			String sourceTime = article.getSourceTime();
			article.setSourceTime(sourceTime);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 如果时间转换失败 发布时间就取来源时间
			try
			{
				long time = dateFormat.parse(sourceTime).getTime();
				int random = (int) (Math.random() * 1000000);
				time = time - random;
				String postTime = dateFormat.format(new Date(time));
				article.setPostTime(postTime);
			}
			catch (Exception e)
			{
				article.setPostTime(sourceTime);
			}
			// 添加到数据库，如果有异常就继续下一条。
			try
			{
				ArticleDao.getInstance().addArticle(article);
			}
			catch (Exception e)
			{
				continue;
			}
			count++;
		}
		return count;
	}

	public static String getMaxSourceTime(String sourceFrom)
	{

		String sourceTime = ArticleDao.getInstance().queryMaxSourceTime(sourceFrom);
		return sourceTime;
	}

	/**
	 * @param sourceArticleId
	 * @param httpUrl
	 * @param bigPath
	 * @param smallPath
	 * @return
	 * @throws Exception
	 */
	private static String uploadPicture2UpYun(String sourceArticleId, String httpUrl, String bigPath, String smallPath)
			throws Exception
	{

		URL url;
		InputStream in = null;
		ByteArrayOutputStream out = null;
		String imgName = "";
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		sourceArticleId = StringUtil.encodeByMD5(sourceArticleId);
		try
		{
			String type = httpUrl.substring(httpUrl.lastIndexOf("."));
			String bigDirPath = "/upload/big" + "/" + year + "/" + month + "/" + day;
			String bigFilePath = bigDirPath + "/" + sourceArticleId + type;// 图片存储的位置

			String smallDirPath = "/upload/small" + "/" + year + "/" + month + "/" + day;
			String samllFilePath = smallDirPath + "/" + sourceArticleId + type;

			imgName = year + "/" + month + "/" + day + "/" + sourceArticleId + type;

			url = new URL(httpUrl);
			in = url.openStream();
			out = new ByteArrayOutputStream();
			int len = 0;
			byte[] b = new byte[1024 * 500];
			while ((len = in.read(b)) != -1)
			{
				out.write(b, 0, len);
			}
			out.flush();
			byte[] data = out.toByteArray();
			if (data.length > 1024 * 1024 || data.length == 0)// 图片大于1M就不要了
			{
				return null;
			}
			// 将大图写入到upyun服务器
			UpYun upYun = UpYun.getInstance(Constant.UPYUN_HOST, Constant.UPYUN_HOST, Constant.UPYUN_PWD);
			upYun.writeFile(bigFilePath, data, true);

			// 画小图
			BufferedImage srcImage = ImageIO.read(url);
			int sw = srcImage.getWidth();
			int sh = srcImage.getHeight();
			// 如果图片大于150
			if (sw > small_width || sh > small_height || data.length > 1024 * 500)
			{
				srcImage = DrowImage.resize(srcImage, small_width, small_height);
			}
			out = new ByteArrayOutputStream();
			ImageIO.write(srcImage, type.replace(".", ""), out);
			byte[] datasmall = out.toByteArray();
			upYun.writeFile(samllFilePath, datasmall, true);
		}
		catch (Exception e)
		{
			System.out.println(httpUrl + ".............");
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (in != null)
			{
				in.close();
				in = null;
			}
			if (out != null)
			{
				out.close();
				out = null;
			}
		}
		return imgName;
	}

	/**
	 * @param sourceArticleId
	 * @param httpUrl
	 * @param bigPath
	 * @param smallPath
	 * @return
	 * @throws Exception
	 */
	private static String uploadPicture2Local(String sourceArticleId, String httpUrl, String bigPath, String smallPath)
			throws Exception
	{

		URL url;
		InputStream in = null;
		OutputStream out = null;
		String imgName = "";
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		sourceArticleId = StringUtil.encodeByMD5(sourceArticleId);
		try
		{
			String type = httpUrl.substring(httpUrl.lastIndexOf("."));
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
			String samllFilePath = smallDirPath + "/" + sourceArticleId + type;

			imgName = year + "/" + month + "/" + day + "/" + sourceArticleId + type;

			url = new URL(httpUrl);
			in = url.openStream();
			File file = new File(bigFilePath);
			out = new FileOutputStream(file);
			int len = 0;
			byte[] b = new byte[1024 * 5];
			while ((len = in.read(b)) != -1)
			{
				out.write(b, 0, len);
			}
			out.flush();
			if (file.length() > 1024 * 1024)
			{// 图片太大就不要文章了
				file.delete();
				return null;
			}
			DrowImage.saveImageAsJpg(bigFilePath, samllFilePath, small_width, small_height, true);
		}
		catch (Exception e)
		{
			System.out.println(httpUrl + ".............");
			e.printStackTrace();
			imgName = "";
			throw e;
		}
		finally
		{
			if (in != null)
			{
				in.close();
				in = null;
			}
			if (out != null)
			{
				out.close();
				out = null;
			}
		}
		return imgName;
	}

	public static void main(String[] args)
	{

		try
		{
			uploadPicture2Local("9204877", "http://img.qiushibaike.com/system/pictures/920/9204877/medium/9204877.jpg",
					"E:/workplace/myhaha/WebRoot/upload/big", "E:/workplace/myhaha/WebRoot/upload/small");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
