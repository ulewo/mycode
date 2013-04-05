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
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.R;
import com.ulewo.api.ApiClient;
import com.ulewo.bean.RequestResult;
import com.ulewo.enums.ResultEnum;

public class BlogActivity extends Activity {

	private static final String path = "http://192.168.2.224:8080/ulewo/android/fetchBlog.jspx";

	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	MyHandler myHandler = null;

	RequestResult requestResult = null;
	SimpleAdapter adapter = null;
	private int page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.article);
		ImageView imageView = (ImageView) findViewById(R.id.main_head_logo);
		imageView.setImageResource(R.drawable.blog);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_blog);

		myHandler = new MyHandler();
		MyThread m = new MyThread();
		new Thread(m).start();
	}

	class MyHandler extends Handler {
		public MyHandler() {

		}

		public MyHandler(Looper L) {

			super(L);
		}

		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			try {
				// 请求网络资源
				ProgressBar progressBar = (ProgressBar) findViewById(R.id.article_load);
				progressBar.setVisibility(8);

				if (requestResult.getResultEnum() == ResultEnum.SUCCESS) {
					JSONObject jsonObj = requestResult.getJsonObject();
					JSONArray jsonArray = new JSONArray(String.valueOf(jsonObj
							.get("list")));
					int jsonLength = jsonArray.length();
					for (int i = 0; i < jsonLength; i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", obj.get("id"));
						map.put("article_title", obj.get("title"));
						map.put("article_time",
								String.valueOf(obj.get("postTime")).substring(
										0, 16));
						map.put("article_recount", obj.get("reCount"));
						map.put("article_author", obj.get("userName"));
						list.add(map);
					}

					adapter = new SimpleAdapter(BlogActivity.this, list,
							R.layout.article_item, new String[] {
									"article_title", "article_author",
									"article_time", "article_recount" },
							new int[] { R.id.article_title,
									R.id.article_author, R.id.article_time,
									R.id.article_recount });
					ListView listView = (ListView) findViewById(R.id.article_list_view_id);
					listView.setAdapter(adapter);
					// 添加点击事件
					listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent,
								View view, int postion, long id) {

							Map<String, Object> map = BlogActivity.this.list
									.get(postion);
							String articleId = String.valueOf(map.get("id"));
							Intent intent = new Intent();
							intent.putExtra("articleId", articleId);
							intent.setClass(BlogActivity.this,
									ShowBlogActivity.class);
							startActivity(intent);
						}
					});
					/*
					 * listView.setOnScrollListener(new OnScrollListener() {
					 * 
					 * @Override public void onScrollStateChanged(AbsListView
					 * paramAbsListView, int scrollState) {
					 * 
					 * int itemsLastIndex = adapter.getCount() - 1; //数据集最后一项的索引
					 * int lastIndex = itemsLastIndex + 1; //加上底部的loadMoreView项
					 * if (scrollState == OnScrollListener.SCROLL_STATE_IDLE &&
					 * visibleLastIndex == lastIndex) {
					 * //如果是自动加载,可以在这里放置异步加载数据的代码 page = page + 1; MyThread m =
					 * new MyThread(); new Thread(m).start(); }
					 * 
					 * }
					 * 
					 * @Override public void onScroll(AbsListView
					 * paramAbsListView, int firstVisibleItem, int
					 * visibleItemCount, int totalItemCount) {
					 * 
					 * ArticleActivity.this.visibleItemCount = visibleItemCount;
					 * visibleLastIndex = firstVisibleItem + visibleItemCount -
					 * 1;
					 * 
					 * } });
					 */
				} else {
					Toast.makeText(BlogActivity.this, R.string.request_timeout,
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class MyThread implements Runnable {
		public void run() {

			requestResult = ApiClient.getUlewoInfo(path + "?page=" + page);
			Message msg = new Message();
			BlogActivity.this.myHandler.sendMessage(msg);

		}
	}

}
