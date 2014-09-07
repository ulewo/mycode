package com.ulewo.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.LengthEnums;
import com.ulewo.enums.SpiderStatus;
import com.ulewo.enums.SpiderType;
import com.ulewo.enums.TopicTypeEnums;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.SpiderMapper;
import com.ulewo.mapper.TopicMapper;
import com.ulewo.model.Spider;
import com.ulewo.model.Topic;
import com.ulewo.util.ScaleFilter2;
import com.ulewo.util.SimplePage;
import com.ulewo.util.SpiderUtil;
import com.ulewo.util.StringUtils;
import com.ulewo.util.UlewoPaginationResult;

/**
 * TODO: osc爬虫
 * 
 * @author luo.hl
 * @date 2014-6-4 上午10:46:01
 * @version 0.1.0
 * @copyright yougou.com
 */
@Service("spiderService")
public class SpiderServiceImpl implements SpidrService {

	@Autowired
	private SpiderMapper<Spider> spiderMapper;

	private HttpServletRequest request;

	@Resource
	private TopicMapper<Topic> topicMapper;

	private static final String GBTYPE_1 = "type-1.html";
	private static final String GBTYPE_2 = "type-2.html";
	private static final String GBTYPE_3 = "type-3.html";
	private static final String GBTYPE_4 = "type-4.html";
	private static final String GBTYPE_5 = "type-5.html";
	private static final String GBTYPE_6 = "type-6.html";

	@Override
	public UlewoPaginationResult<Spider> querySpiderList(Map<String, String> map)
			throws BusinessException {
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		int pageSize = StringUtils.isEmpty(map.get("rows")) ? 20 : Integer
				.parseInt(map.get("rows"));
		int countTotal = spiderMapper.selectBaseInfoCount(map);
		SimplePage page = new SimplePage(page_no, countTotal, pageSize);
		List<Spider> list = spiderMapper.selectBaseInfoList(map, page);
		UlewoPaginationResult<Spider> result = new UlewoPaginationResult<Spider>(
				page, list);
		return result;
	}

	@Override
	public void spiderArticle(String type) throws BusinessException {
		try {

			List<Spider> list = new ArrayList<Spider>();
			if (SpiderType.OSCHINA.getType().equals(type)) { // 开源中国
				list = getSpiderList4Osc("http://www.oschina.net/news/list");
			} else if (SpiderType.XINWENGE.getType().equals(type)) { // 新闻哥
				list = getSpiderList4Xwg(SpiderType.XINWENGE.getUrl());
			} else if (SpiderType.CNBLOG.getType().equals(type)) { // 博客园
				list = getSpiderList4Cnblog(SpiderType.CNBLOG.getUrl());
			} else if (SpiderType.QILU.getType().equals(type)) {
				// list =
				// getSpiderList4Qilu("http://ent.iqilu.com/film/news",
				// "qilu_new");
				list.addAll(getSpiderList4Qilu(
						"http://news.iqilu.com/shehui/huahuashijie",
						"qilu_life"));
				// list.addAll(getSpiderList4Qilu(
				// "http://ent.iqilu.com/star/dongtai", "qilu_star"));
			} else if (SpiderType.GB.getType().equals(type)) {

				list.addAll(getSpiderList4Gb(SpiderType.GB.getUrl()
						+ "Index/page-1.html"));
				list.addAll(getSpiderList4Gb(SpiderType.GB.getUrl()
						+ "Index/page-2.html"));
			}
			if (list.size() > 0) {
				SimplePage page = new SimplePage();
				page.setStart(0);
				page.setEnd(50);
				Map<String, String> map = new HashMap<String, String>();
				String date = StringUtils.dateFormater2.format(new Date());
				map.put("createTime", date);
				map.put("type", type);
				List<Spider> resultSpider = spiderMapper.selectBaseInfoList(
						map, page);
				if (SpiderType.QILU.getType().equals(type)) {
					map.put("type", "qilu_new");
					resultSpider.addAll(spiderMapper.selectBaseInfoList(map,
							page));
					map.put("type", "qilu_life");
					resultSpider.addAll(spiderMapper.selectBaseInfoList(map,
							page));
					map.put("type", "qilu_star");
					resultSpider.addAll(spiderMapper.selectBaseInfoList(map,
							page));
				}

				Map<String, String> tempMap = new HashMap<String, String>();
				for (Spider temp : resultSpider) {
					if (tempMap.get(temp.getId() + temp.getType()) == null) {
						tempMap.put(temp.getId() + temp.getType(), temp.getId()
								+ temp.getType());
					}
				}
				Iterator<Spider> iterator = list.iterator();
				while (iterator.hasNext()) {
					Spider spider = iterator.next();
					if (tempMap.get(spider.getId() + spider.getType()) != null) {
						iterator.remove();
					}
				}
				if (list.size() > 0) {
					spiderMapper.insertBatch(list);
				}
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * 获取爬取的文章列表
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private List<Spider> getSpiderList4Osc(String url)
			throws ClientProtocolException, IOException {
		String html = SpiderUtil.getHtml(url);
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(html);

		TagNode newsList = allNode.getElementsByAttValue("id",
				"RecentNewsList", true, true)[0];
		TagNode ul = newsList.getChildTags()[1];
		TagNode[] lis = ul.getElementsByName("li", true);
		List<Spider> spiderList = new ArrayList<Spider>();
		String date = StringUtils.dateFormater2.format(new Date());
		for (TagNode li : lis) {
			TagNode h2 = li.getElementsByName("h2", true)[0];
			TagNode a = h2.getElementsByName("a", true)[0];
			String link = a.getAttributeByName("href");
			if (!link.contains("http://")) {
				try {
					Spider spider = getSpiderContent4Osc(SpiderType.OSCHINA
							.getUrl() + link);
					spider.setType(SpiderType.OSCHINA.getType());
					spider.setCreateTime(date);
					spiderList.add(spider);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

			}
		}
		return spiderList;
	}

	/**
	 * 获取内容
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private Spider getSpiderContent4Osc(String url)
			throws ClientProtocolException, IOException {
		String html = SpiderUtil.getHtml(url);
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(html);
		TagNode newsEntity = allNode.getElementsByAttValue("class",
				"NewsEntity", true, true)[0];
		TagNode titleNode = newsEntity.getElementsByAttValue("class",
				"OSCTitle", true, true)[0];
		String title = String.valueOf(titleNode.getText());
		TagNode contentTag = newsEntity.getElementsByAttValue("class",
				"Body NewsContent TextContent", true, true)[0];
		TagNode p = contentTag.getElementsByName("p", true)[0];
		if (p.getText().toString().contains("开源中国")) {
			contentTag.removeChild(p);
		}
		String style = p.getAttributeByName("style");
		if (!StringUtils.isEmpty(style)) {
			contentTag.removeChild(p);
		}
		String content = htmlCleaner.getInnerHtml(contentTag);
		Spider spider = new Spider();
		spider.setContent(content);
		spider.setTitle(title);
		return spider;
	}

	private List<Spider> getSpiderList4Xwg(String url)
			throws ClientProtocolException, IOException {
		String html = SpiderUtil.getHtml(url);
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(html);

		TagNode newsList = allNode.getElementsByAttValue("class",
				"historyList", true, true)[0];
		TagNode ul = newsList.getChildTags()[0];
		TagNode[] lis = ul.getElementsByName("li", true);
		List<Spider> spiderList = new ArrayList<Spider>();
		String date = StringUtils.dateFormater2.format(new Date());
		for (TagNode li : lis) {
			TagNode a = li.getElementsByName("a", true)[0];
			String link = a.getAttributeByName("href");
			Spider spider = getSpiderContent4Xwg(link);
			spider.setType(SpiderType.XINWENGE.getType());
			spider.setCreateTime(date);
			spiderList.add(spider);
		}
		return spiderList;
	}

	private Spider getSpiderContent4Xwg(String url)
			throws ClientProtocolException, IOException {
		String html = SpiderUtil.getHtml(url);
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(html);
		TagNode newsEntity = allNode.getElementsByAttValue("class",
				"article small", true, true)[0];
		TagNode titleNode = newsEntity.getElementsByAttValue("class", "title",
				true, true)[0];
		String title = String.valueOf(titleNode.getText());
		TagNode contentTag = newsEntity.getElementsByAttValue("class", "cont",
				true, true)[0];
		String content = htmlCleaner.getInnerHtml(contentTag);
		Spider spider = new Spider();
		spider.setContent(content);
		spider.setTitle(title);
		return spider;
	}

	/**
	 * 博客园
	 */

	private List<Spider> getSpiderList4Cnblog(String url)
			throws ClientProtocolException, IOException {
		String html = SpiderUtil.getHtml(url);
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(html);

		TagNode newsList = allNode.getElementsByAttValue("id", "news_list",
				true, true)[0];
		TagNode[] divs = newsList.getElementsByAttValue("class", "news_block",
				true, true);
		List<Spider> spiderList = new ArrayList<Spider>();
		String date = StringUtils.dateFormater2.format(new Date());
		for (TagNode li : divs) {
			TagNode a = li.getElementsByName("a", true)[0];
			String link = "http://news.cnblogs.com"
					+ a.getAttributeByName("href");
			Spider spider = getSpiderContent4Cnblog(link);
			if (null == spider) {
				continue;
			}
			spider.setType(SpiderType.CNBLOG.getType());
			spider.setCreateTime(date);
			spiderList.add(spider);
		}
		return spiderList;
	}

	/**
	 * 爬去逛吧
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private List<Spider> getSpiderList4Gb(String url)
			throws ClientProtocolException, IOException {
		String html = SpiderUtil.getHtml(url);
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(html);
		TagNode[] divs = allNode.getElementsByAttValue("class", "media", true,
				true);
		List<Spider> spiderList = new ArrayList<Spider>();
		String date = StringUtils.dateFormater2.format(new Date());
		for (TagNode li : divs) {
			try {
				TagNode[] media_body = li.getElementsByAttValue("class",
						"media-body", true, true);
				if (media_body.length == 0) {
					continue;
				}
				TagNode h4 = media_body[0].getElementsByName("h4", true)[0];
				TagNode parta = h4.getElementsByName("a", true)[0];
				TagNode linka = h4.getElementsByName("a", true)[1];
				String partUrl = parta.getAttributeByName("href");
				String part = partUrl.substring(partUrl.lastIndexOf("/") + 1);
				String linkUrl = linka.getAttributeByName("href");
				String link = "http://www.gb1.cn/" + linkUrl;
				String title = linka.getAttributeByName("title");
				Spider spider = getSpiderContent4Gb(link);
				if (null == spider) {
					continue;
				}
				if (GBTYPE_1.equals(part)) {
					spider.setType(SpiderType.GBPIC.getType());
				} else if (GBTYPE_2.equals(part)) {
					spider.setType(SpiderType.GBGAME.getType());
				} else if (GBTYPE_3.equals(part)) {
					spider.setType(SpiderType.GBMOVIE.getType());
				} else if (GBTYPE_4.equals(part)) {
					spider.setType(SpiderType.GBTOPIC.getType());
				} else if (GBTYPE_5.equals(part)) {
					spider.setType(SpiderType.GBTALK.getType());
				} else if (GBTYPE_6.equals(part)) {
					spider.setType(SpiderType.GBJOKE.getType());
				}
				spider.setTitle(title);
				spider.setCreateTime(date);
				spiderList.add(spider);
			} catch (Exception e) {
				continue;
			}

		}
		return spiderList;
	}

	private Spider getSpiderContent4Cnblog(String url)
			throws ClientProtocolException, IOException {
		try {
			String html = SpiderUtil.getHtml(url);
			HtmlCleaner htmlCleaner = new HtmlCleaner();
			TagNode allNode = htmlCleaner.clean(html);
			TagNode titleNode = allNode.getElementsByAttValue("id",
					"news_title", true, true)[0];
			TagNode alink = titleNode.getElementsByName("a", true)[0];
			String title = String.valueOf(alink.getText());

			TagNode contentTag = allNode.getElementsByAttValue("id",
					"news_body", true, true)[0];
			String content = htmlCleaner.getInnerHtml(contentTag);
			Spider spider = new Spider();
			spider.setContent(content);
			spider.setTitle(title);
			return spider;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 齐鲁网
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private List<Spider> getSpiderList4Qilu(String url, String type)
			throws ClientProtocolException, IOException {
		String html = SpiderUtil.getHtml(url);
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(html);

		TagNode newsList = allNode.getElementsByAttValue("id", "nr_left", true,
				true)[0];
		TagNode[] divs = newsList.getElementsByAttValue("class", "list_box",
				true, true);
		List<Spider> spiderList = new ArrayList<Spider>();
		String date = StringUtils.dateFormater2.format(new Date());
		for (int i = 1, length = divs.length; i < length; i++) {
			TagNode node = divs[i];
			TagNode a = node.getElementsByName("a", true)[0];
			String link = a.getAttributeByName("href");
			Spider spider = getSpiderContent4Qilu(link);
			if (spider == null) {
				continue;
			}
			spider.setType(type);
			spider.setCreateTime(date);
			spiderList.add(spider);
		}
		return spiderList;
	}

	private Spider getSpiderContent4Qilu(String url)
			throws ClientProtocolException, IOException {
		try {
			String html = SpiderUtil.getHtml(url);
			HtmlCleaner htmlCleaner = new HtmlCleaner();
			TagNode allNode = htmlCleaner.clean(html);
			TagNode titleNode = allNode.getElementsByAttValue("id", "nr_left",
					true, true)[0];
			TagNode alink = titleNode.getElementsByName("h1", true)[0];
			String title = String.valueOf(alink.getText());

			TagNode contentTag = allNode.getElementsByAttValue("id", "context",
					true, true)[0];
			TagNode[] pTag = contentTag.getElementsByName("p", true);
			TagNode divTag = new TagNode("div");
			for (TagNode p : pTag) {
				divTag.addChild(p);
			}
			String content = htmlCleaner.getInnerHtml(divTag);
			Spider spider = new Spider();
			spider.setContent(content);
			spider.setTitle(title);

			TagNode[] pageUpDown1 = contentTag.getElementsByAttValue("id",
					"pageUpDown1", true, true);
			if (pageUpDown1.length > 0) {
				TagNode pageNode = pageUpDown1[0].getElementsByAttValue("id",
						"page", true, true)[0];
				TagNode liNode = pageNode.getElementsByName("li", true)[0];
				TagNode liNodea = liNode.getElementsByName("a", true)[0];
				String pageStr = liNodea.getText().toString();
				int pageCount = Integer.parseInt(pageStr.substring(1,
						pageStr.length() - 3));
				String newUrl = url.substring(0, url.lastIndexOf("."));
				for (int i = 2; i <= pageCount; i++) {
					getSpiderContent4QiluSub(spider, newUrl + "_" + i
							+ ".shtml");
				}
			}
			return spider;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void getSpiderContent4QiluSub(Spider spider, String url)
			throws ClientProtocolException, IOException {
		String html = SpiderUtil.getHtml(url);
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(html);
		TagNode contentTag = allNode.getElementsByAttValue("id", "context",
				true, true)[0];
		TagNode[] pTag = contentTag.getElementsByName("p", true);
		TagNode divTag = new TagNode("div");
		for (TagNode p : pTag) {
			divTag.addChild(p);
		}
		String content = htmlCleaner.getInnerHtml(divTag);
		spider.setContent(spider.getContent() + content);
	}

	private Spider getSpiderContent4Gb(String url)
			throws ClientProtocolException, IOException {
		try {
			System.out.println("抓取的url" + url);
			String html = SpiderUtil.getHtml(url);
			HtmlCleaner htmlCleaner = new HtmlCleaner();
			TagNode allNode = htmlCleaner.clean(html);
			TagNode[] contentNodes = allNode.getElementsByAttValue("class",
					"list-group", true, true);
			if (contentNodes.length == 0) {
				return null;
			}
			TagNode[] contentTags = contentNodes[0].getElementsByName("div",
					true);
			if (contentTags.length == 0) {
				return null;
			}
			String content = htmlCleaner.getInnerHtml(contentTags[1]);
			Spider spider = new Spider();
			spider.setContent(content);
			return spider;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 发布文章
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void sendTopic(Map<String, String> map, HttpServletRequest request)
			throws BusinessException {
		this.request = request;
		String keyStr = map.get("key");
		String[] keys = keyStr.split(",");
		String gidStr = map.get("gid");
		String categoryIdStr = map.get("categoryId");
		if (!StringUtils.isNumber(gidStr)) {
			throw new BusinessException("参数错误");
		}
		if (!StringUtils.isNumber(categoryIdStr)) {
			throw new BusinessException("参数错误");
		}
		List<String> list = Arrays.asList(keys);
		List<Spider> resultList = spiderMapper.selectSpiderByIds(list);
		List<Topic> topicList = new ArrayList<Topic>();
		for (Spider spider : resultList) {
			Topic topic = getTopicFromSpider(spider);
			topic.setGid(Integer.parseInt(gidStr));
			topic.setCategoryId(Integer.parseInt(categoryIdStr));
			String content = topic.getContent();
			content = content.replace("iframe", "embed");
			topic.setContent(content);
			String summary = StringUtils.clearHtml(content);
			if (summary.length() > LengthEnums.Length200.getLength()) {
				summary = summary.substring(0,
						LengthEnums.Length100.getLength())
						+ "......";
			}
			topic.setSummary(summary);

			topic.setUserId(10000);
			String curDate = StringUtils.dateFormater.format(new Date());
			topic.setCreateTime(curDate);
			topic.setLastCommentTime(curDate);
			topic.setTopicType(TopicTypeEnums.COMMON.getValue());
			String topicImage = StringUtils.getImages(content);
			topic.setTopicImage(topicImage);
			String topicImageSmall = getTopicImageSmall(topicImage, request);
			topic.setTopicImageSmall(topicImageSmall);
			topicList.add(topic);
			// 更新spider状态
		}
		// 批量插入帖子
		topicMapper.insertBatch(topicList);
		spiderMapper.updateBatch(resultList, SpiderStatus.STATUS1.getStatus());
	}

	/**
	 * 替换网络图片为本地图片
	 * 
	 * @param spider
	 * @return
	 */
	private Topic getTopicFromSpider(Spider spider) {
		String content = spider.getContent();

		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(content);
		TagNode[] images = allNode.getElementsByName("img", true);
		if (images.length > 0) {
			for (TagNode tag : images) {
				try {
					String src = tag.getAttributeByName("src");
					String newSrc = null;
					if (src.contains("http")) {
						newSrc = uploadImage(src);
					} else if (spider.getType().contains("gb")) {
						newSrc = uploadImage(SpiderType.GB.getUrl() + src);
					}
					if (!StringUtils.isEmpty(newSrc)) {
						tag.setAttribute("src", newSrc);
					}
				} catch (Exception e) {

					e.printStackTrace();
					continue;
				}

			}
		}

		Topic topic = new Topic();
		topic.setTitle(spider.getTitle());
		topic.setContent(htmlCleaner.getInnerHtml(allNode.getChildTags()[1]));
		return topic;
	}

	public void deleteNode(TagNode allNode, TagNode delNode) {
		List<TagNode> childNodes = allNode.getChildTagList();
		for (TagNode node : childNodes) {
			if (node instanceof TagNode) {
				if (node == delNode) {
					allNode.removeChild(delNode);
				}
				deleteNode(node, delNode);
			}
		}
	}

	/**
	 * 上传图片
	 * 
	 * @param oldsrc
	 * @return
	 */
	private String uploadImage(String oldsrc) {
		HttpURLConnection.setFollowRedirects(false);
		InputStream is = null;
		OutputStream os = null;
		String filePath = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(oldsrc)
					.openConnection();
			String type = this.getFileType(oldsrc);
			String saveName = Long.toString(new Date().getTime()) + type;
			filePath = this.getFolder("upload") + "/" + saveName;
			File savetoFile = new File(getPhysicalPath(filePath));

			is = conn.getInputStream();
			os = new FileOutputStream(savetoFile);
			int b;
			while ((b = is.read()) != -1) {
				os.write(b);
			}
			os.flush();
			// 这里处理 inputStream
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (null != filePath) {
			return getRealPath() + "/" + filePath;
		} else {
			return null;
		}
	}

	/**
	 * 获取文件目录
	 * 
	 * @param path
	 * @return
	 */
	private String getFolder(String path) {

		SimpleDateFormat formater = new SimpleDateFormat("yyyyMM");
		path += "/" + formater.format(new Date());
		File dir = new File(this.getPhysicalPath(path));
		if (!dir.exists()) {
			try {
				dir.mkdirs();
			} catch (Exception e) {
				return "";
			}
		}
		return path;
	}

	/**
	 * 获取绝对路径
	 * 
	 * @param path
	 * @return
	 */
	private String getPhysicalPath(String path) {
		String realPath = this.request.getSession().getServletContext()
				.getRealPath("/");
		return realPath + path;
	}

	/**
	 * 图片类型
	 * 
	 * @param fileName
	 * @return
	 */
	public String getFileType(String fileName) {

		String[] fileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp", ".GIF",
				".PNG", ".JPG", ".JPEG", ".BMP" };
		Iterator<String> type = Arrays.asList(fileType).iterator();
		while (type.hasNext()) {
			String t = type.next();
			if (fileName.endsWith(t)) {
				return t;
			}
		}
		return ".jpg";
	}

	private String getRealPath() {
		String port = request.getServerPort() == 80 ? "" : ":"
				+ request.getServerPort();
		String realPath = "http://" + request.getServerName() + port
				+ request.getContextPath();
		return realPath;
	}

	/**
	 * 生成文章中的缩略图
	 * 
	 * @param topicImage
	 * @param request
	 * @return
	 */
	private String getTopicImageSmall(String topicImage,
			HttpServletRequest request) {
		StringBuilder topicImageSmall = new StringBuilder();
		if (topicImage != null) {
			String port = request.getServerPort() == 80 ? "" : ":"
					+ request.getServerPort();
			String hostPath = "http://" + request.getServerName() + port
					+ request.getContextPath();
			String realPath = request.getSession().getServletContext()
					.getRealPath("");
			String[] topoicImages = topicImage.split("\\|");
			for (String img : topoicImages) {
				if (StringUtils.isEmpty(img)) {
					continue;
				}
				String imagePath = img.replaceAll(hostPath, "");
				String sourcePath = realPath + imagePath;
				String smallPath = sourcePath + ".small.jpg";
				String smallSavePath = hostPath + imagePath + ".small.jpg";
				BufferedImage src = null;
				try {
					src = ImageIO.read(new File(sourcePath));
					if (src.getHeight() > 2000) {
						continue;
					}
					BufferedImage dst = new ScaleFilter2(120).filter(src, null);
					ImageIO.write(dst, "JPEG", new File(smallPath));
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				topicImageSmall.append(smallSavePath).append("|");
			}
		}
		return topicImageSmall.toString();
	}

	public void sendTopicLocal(Map<String, String> map,
			HttpServletRequest request) throws BusinessException {
		this.request = request;
		String gidStr = map.get("gid");
		String categoryIdStr = map.get("categoryId");
		if (!StringUtils.isNumber(gidStr)) {
			throw new BusinessException("参数错误");
		}
		if (!StringUtils.isNumber(categoryIdStr)) {
			throw new BusinessException("参数错误");
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("type", map.get("type"));
		List<Spider> resultList = spiderMapper.selectBaseInfoList(param,
				new SimplePage());
		List<Topic> topicList = new ArrayList<Topic>();
		List<Spider> spiderList = new ArrayList<Spider>();
		Spider spider = null;
		for (int i = 0, _len = resultList.size(); i < _len; i++) {
			spider = resultList.get(i);
			Topic topic = getTopicFromSpider(spider);
			topic.setGid(Integer.parseInt(gidStr));
			topic.setCategoryId(Integer.parseInt(categoryIdStr));
			String content = topic.getContent();
			String summary = StringUtils.clearHtml(content);
			if (summary.length() > LengthEnums.Length200.getLength()) {
				summary = summary.substring(0,
						LengthEnums.Length100.getLength())
						+ "......";
			}
			topic.setSummary(summary);
			topic.setUserId(10000);
			String curDate = StringUtils.dateFormater.format(new Date());
			topic.setCreateTime(curDate);
			topic.setLastCommentTime(curDate);
			topic.setTopicType(TopicTypeEnums.COMMON.getValue());
			String topicImage = StringUtils.getImages(content);
			topic.setTopicImage(topicImage);
			String topicImageSmall = getTopicImageSmall(topicImage, request);
			topic.setTopicImageSmall(topicImageSmall);
			topicList.add(topic);
			spiderList.add(spider);
			if (i > 0 && i % 100 == 0) {
				topicMapper.insertBatch(topicList);
				spiderMapper.updateBatch(resultList,
						SpiderStatus.STATUS1.getStatus());
				topicList.clear();
				spiderList.clear();
			}
			if (i == _len - 1) {
				topicMapper.insertBatch(topicList);
				spiderMapper.updateBatch(resultList,
						SpiderStatus.STATUS1.getStatus());
				topicList.clear();
				spiderList.clear();
			}
		}
	}
}
