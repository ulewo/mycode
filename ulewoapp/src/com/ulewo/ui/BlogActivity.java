package com.ulewo.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ulewo.R;
import com.ulewo.Enum.ResultEnum;
import com.ulewo.api.ApiClient;
import com.ulewo.bean.RequestResult;

public class BlogActivity extends Activity {

	private static final String path = "http://192.168.0.224:8080/ulewo/android/fetchBlog.jspx";

	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.article);
		ImageView imageView = (ImageView) findViewById(R.id.main_head_logo);
		imageView.setImageResource(R.drawable.article);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_article);
		try {
			//请求网络资源
			RequestResult requestResult = ApiClient.getUlewoInfo(path);
			if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
				JSONObject jsonObj = requestResult.getJsonObject();
				JSONArray jsonArray = new JSONArray(String.valueOf(jsonObj.get("list")));
				int jsonLength = jsonArray.length();
				for (int i = 0; i < jsonLength; i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", obj.get("id"));
					map.put("article_title", obj.get("title"));
					map.put("article_time", String.valueOf(obj.get("postTime")).substring(0, 16));
					map.put("article_recount", obj.get("reCount"));
					map.put("article_author", obj.get("userName"));
					list.add(map);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.article_item, new String[] { "article_title",
				"article_author", "article_time", "article_recount" }, new int[] { R.id.article_title,
				R.id.article_author, R.id.article_time, R.id.article_recount });

		ListView listView = (ListView) findViewById(R.id.article_list_view_id);
		listView.setAdapter(adapter);

		// 添加点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {

				Map<String, Object> map = BlogActivity.this.list.get(postion);
				String articleId = String.valueOf(map.get("id"));
				Intent intent = new Intent();
				intent.putExtra("articleId", articleId);
				intent.setClass(BlogActivity.this, ShowBlogActivity.class);
				startActivity(intent);
			}
		});
	}

}
