package com.ulewo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2013-11-27 下午7:05:07
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class Spider {

	private static DefaultHttpClient httpclient = new DefaultHttpClient();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> images = new ArrayList<String>();
		StringBuilder content = new StringBuilder();
		getHtml2(1, images, content);
		//System.out.println(images.size());
		String resultContent = content.toString();
		resultContent = resultContent.replaceAll("<a href[^>]*>", "");
		resultContent = resultContent.replaceAll("</a>", "");
		resultContent = resultContent.replaceAll("<img[^>]*/>", " ");
		System.out.println(resultContent);
	}

	public static String getQiushibaikeHTML(String url) throws ClientProtocolException, IOException {

		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.addHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:15.0) Gecko/20100101 Firefox/15.0.1");
		HttpResponse response = httpclient.execute(httpGet);
		InputStream in = response.getEntity().getContent();
		String html = convertStreamToString(in, "utf-8");
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
	public static String convertStreamToString(InputStream is, String charset) throws UnsupportedEncodingException {
		InputStreamReader inreader = null;
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			inreader = new InputStreamReader(is, charset);
			reader = new BufferedReader(inreader);
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (null != inreader) {
					inreader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 抓住列表页面
	 */
	public static void getHtml() {
		String html;
		try {
			//html = getQiushibaikeHTML("www.ulewo.com");
			HtmlCleaner htmlCleaner = new HtmlCleaner();
			TagNode allNode = htmlCleaner.clean(new File("E:\\test.htm"), "UTF-8");
			TagNode ptag = allNode.getElementsByAttValue("class", "channelLeftPart", true, true)[0]; //通过属性查找
			TagNode conTag = ptag.getElementsByName("div", true)[1];
			TagNode[] lis = conTag.getElementsByName("li", true);//通过标签查找
			for (TagNode li : lis) {
				TagNode[] liTag = li.getElementsByName("span", true);
				TagNode alink = liTag[0].getElementsByName("a", true)[0];
				String link = alink.getAttributeByName("href");
				String title = alink.getText().toString();
				String date = liTag[1].getText().toString();
				System.out.println(title + "->" + link + "->" + date);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void getHtml2(int page, List<String> images, StringBuilder content) {
		try {
			//html = getQiushibaikeHTML("www.ulewo.com");
			HtmlCleaner htmlCleaner = new HtmlCleaner();
			TagNode allNode = null;
			if (page == 1) {
				allNode = htmlCleaner.clean(new File("E:\\test.htm"), "UTF-8");
			} else {
				allNode = htmlCleaner.clean(new File("E:\\test" + page + ".htm"), "UTF-8");
			}
			TagNode ptag = allNode.getElementsByAttValue("id", "ArticleContent", true, true)[0]; //通过属性查找
			TagNode[] conTags = ptag.getElementsByName("div", true);
			TagNode leftTag = conTags[0];
			TagNode[] pTag = leftTag.getElementsByName("p", true);
			TagNode centerP = pTag[0];
			TagNode image = centerP.getElementsByName("img", true)[0];
			String imageUrl = image.getAttributeByName("src");
			TagNode contenTag = new TagNode("p");
			TagNode[] pictext = leftTag.getElementsByAttValue("class", "pictext", true, true);
			int start = 1;
			if (pictext.length > 0) {
				start = 2;
			}
			for (int i = start, length = pTag.length; i < length; i++) {
				contenTag.addChild(pTag[i]);
			}
			int pageTotal = 0;
			if (page == 1) {
				TagNode mpagecount = allNode.getElementsByAttValue("id", "mpagecount", true, true)[0];
				pageTotal = Integer.parseInt(mpagecount.getText().toString());
			}
			images.add(imageUrl);
			String conTemp = htmlCleaner.getInnerHtml(contenTag);
			if (!content.toString().contains(conTemp)) {
				content.append(conTemp);
			}
			for (int i = 2; i <= pageTotal; i++) {
				getHtml2(i, images, content);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
