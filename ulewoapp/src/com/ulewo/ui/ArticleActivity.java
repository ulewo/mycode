package com.ulewo.ui;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ulewo.R;

public class ArticleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.article);
		ImageView imageView = (ImageView) findViewById(R.id.main_head_logo);
		imageView.setImageResource(R.drawable.article);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_article);

		String myString = "";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			// 定义获取文件内容的URL
			URL myURL = new URL("http://ulewo.cloudfoundry.com/android/fetchArticle.jspx");
			// 打开URL链接
			URLConnection ucon = myURL.openConnection();
			// 使用InputStream，从URLConnection读取数据
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			// 用ByteArrayBuffer缓存
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			myString = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
			JSONObject jsonObj = new JSONObject(myString);
			JSONArray jsonArray = new JSONArray(String.valueOf(jsonObj.get("list")));
			int jsonLength = jsonArray.length();
			for (int i = 0; i < jsonLength; i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("article_title", obj.get("title"));
				map.put("article_time", String.valueOf(obj.get("postTime")).substring(0, 16));
				map.put("article_recount", obj.get("reNumber"));
				map.put("article_author", obj.get("authorName"));
				list.add(map);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			myString = e.getMessage();
		}
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.article_item, new String[] { "article_title",
				"article_author", "article_time", "article_recount" }, new int[] { R.id.article_title,
				R.id.article_author, R.id.article_time, R.id.article_recount });
		ListView listView = (ListView) findViewById(R.id.article_list_view_id);
		listView.setAdapter(adapter);
	}
}
