package com.ulewo.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ulewo.R;
import com.ulewo.adapter.ArticleListAdapter;
import com.ulewo.bean.Article;
import com.ulewo.bean.Task;
import com.ulewo.enums.TaskType;
import com.ulewo.logic.MainService;

public class ArticleActivity extends Activity implements IMainActivity {

	private LinearLayout progressBar = null;

	private ArticleListAdapter adapter = null;

	private View loadMoreView = null;

	private int page = 1;

	private TextView loadmoreTextView = null;

	private LinearLayout loadmore_prgressbar = null;

	ListView listView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.article);

		ImageView imageView = (ImageView) findViewById(R.id.main_head_logo);
		imageView.setImageResource(R.drawable.article);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_article);

		progressBar = (LinearLayout) findViewById(R.id.myprogressbar);

		loadMoreView = View.inflate(this, R.layout.loadmore, null);

		listView = (ListView) findViewById(R.id.article_list_view_id);
		listView.addFooterView(loadMoreView);

		loadmore_prgressbar = (LinearLayout) findViewById(R.id.loadmore_progressbar);
		loadmoreTextView = (TextView) findViewById(R.id.loadmoretextview);

		loadmoreTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadmoreTextView.setVisibility(View.GONE);
				loadmore_prgressbar.setVisibility(View.VISIBLE);
				Intent service = new Intent(ArticleActivity.this,
						MainService.class);
				startService(service);
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("page", ++page);
				Task task = new Task(TaskType.QUERYARTICLES, param,
						ArticleActivity.this);
				MainService.newTask(task);
			}
		});

		Intent service = new Intent(this, MainService.class);
		startService(service);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("page", page);
		Task task = new Task(TaskType.QUERYARTICLES, param, this);
		MainService.newTask(task);
		// MainService.addActivity(this);
		/*
		 * myHandler = new MyHandler(); MyThread m = new MyThread(); new
		 * Thread(m).start();
		 */
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... obj) {
		progressBar.setVisibility(View.GONE);
		List<Article> list = (ArrayList<Article>) obj[0];
		if (adapter == null) {
			adapter = new ArticleListAdapter(this, list);
			listView.setAdapter(adapter);
		} else {
			loadmore_prgressbar.setVisibility(View.GONE);
			loadmoreTextView.setVisibility(View.VISIBLE);
			adapter.loadMore(list);
		}
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int postion, long id) {
				String articleId = String.valueOf(adapter.getItemId(postion));
				if (!"0".equals(articleId)) {
					Intent intent = new Intent();
					intent.putExtra("articleId", articleId);
					intent.setClass(ArticleActivity.this,
							ShowArticleActivity.class);
					startActivity(intent);
				}
			}
		});
	}
}
