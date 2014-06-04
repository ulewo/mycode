package com.ulewo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ulewo.enums.SpiderType;
import com.ulewo.exception.BusinessException;
import com.ulewo.mapper.SpiderMapper;
import com.ulewo.model.Spider;
import com.ulewo.model.Topic;
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

	@Override
	public UlewoPaginationResult<Spider> querySpiderList(Map<String, String> map) throws BusinessException {
		int page_no = 0;
		if (StringUtils.isNumber(map.get("page"))) {
			page_no = Integer.parseInt(map.get("page"));
		}
		int pageSize = StringUtils.isEmpty(map.get("rows")) ? 20 : Integer.parseInt(map.get("rows"));
		int countTotal = spiderMapper.selectBaseInfoCount(map);
		SimplePage page = new SimplePage(page_no, countTotal, pageSize);
		List<Spider> list = spiderMapper.selectBaseInfoList(map, page);
		UlewoPaginationResult<Spider> result = new UlewoPaginationResult<Spider>(page, list);
		return result;
	}

	@Override
	public void spiderArticle(String type) throws BusinessException {
		try {
			List<Spider> list = new ArrayList<Spider>();
			if (SpiderType.OSCHINA.getType().equals(type)) {
				list = getSpiderList("http://www.oschina.net/news/list");
			}
			//List<Spider> resultList = new ArrayList<Spider>();
			if (list.size() > 0) {
				SimplePage page = new SimplePage();
				page.setStart(0);
				page.setEnd(50);
				Map<String, String> map = new HashMap<String, String>();
				String date = StringUtils.dateFormater2.get().format(new Date());
				map.put("createTime", date);
				map.put("type", SpiderType.OSCHINA.getType());
				List<Spider> resultSpider = spiderMapper.selectBaseInfoList(map, page);

				Map<String, String> tempMap = new HashMap<String, String>();
				for (Spider temp : resultSpider) {
					if (tempMap.get(temp.getId() + temp.getType()) == null) {
						tempMap.put(temp.getId() + temp.getType(), temp.getId() + temp.getType());
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
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

	private List<Spider> getSpiderList(String url) throws ClientProtocolException, IOException {
		String html = SpiderUtil.getHtml(url);
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(html);

		TagNode newsList = allNode.getElementsByAttValue("id", "RecentNewsList", true, true)[0];
		TagNode ul = newsList.getChildTags()[1];
		TagNode[] lis = ul.getElementsByName("li", true);
		List<Spider> spiderList = new ArrayList<Spider>();
		String date = StringUtils.dateFormater2.get().format(new Date());
		for (TagNode li : lis) {
			TagNode h2 = li.getElementsByName("h2", true)[0];
			TagNode a = h2.getElementsByName("a", true)[0];
			String link = a.getAttributeByName("href");
			if (!link.contains("http://")) {
				Spider spider = getSpiderContent(SpiderType.OSCHINA.getUrl() + link);
				spider.setType(SpiderType.OSCHINA.getType());
				spider.setCreateTime(date);
				spiderList.add(spider);
			}
		}
		return spiderList;
	}

	private Spider getSpiderContent(String url) throws ClientProtocolException, IOException {
		String html = SpiderUtil.getHtml(url);
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(html);
		TagNode newsEntity = allNode.getElementsByAttValue("class", "NewsEntity", true, true)[0];
		TagNode titleNode = newsEntity.getElementsByAttValue("class", "OSCTitle", true, true)[0];
		String title = String.valueOf(titleNode.getText());
		TagNode contentTag = newsEntity.getElementsByAttValue("class", "Body NewsContent TextContent", true, true)[0];
		String content = htmlCleaner.getInnerHtml(contentTag);
		Spider spider = new Spider();
		String id = title.substring(title.lastIndexOf("/") + 1);
		spider.setId(id);
		spider.setContent(content);
		spider.setTitle(title);
		return spider;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void sendTopic(Map<String, String> map, HttpServletRequest request) throws BusinessException {
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
			topicList.add(topic);
		}
	}

	private Topic getTopicFromSpider(Spider spider) {
		String content = spider.getContent();
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode allNode = htmlCleaner.clean(content);
		TagNode[] images = allNode.getElementsByName("img", true);
		if (images.length > 0) {
			for (TagNode tag : images) {
				String str = tag.getAttributeByName("src");

			}
		}
		Topic topic = new Topic();
		topic.setTitle(spider.getTitle());
		return topic;
	}
}
