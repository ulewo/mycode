package com.ulewo.ui;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ulewo.R;
import com.ulewo.bean.Article;
import com.ulewo.bean.Task;
import com.ulewo.enums.TaskType;
import com.ulewo.handler.MxgsaTagHandler;
import com.ulewo.logic.MainService;

public class ShowArticleActivity extends Activity implements IMainActivity {

	private LinearLayout progressBar = null;

	private LinearLayout contentlayout = null;

	private ImageView backBtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.show_article);
		ImageView imageView = (ImageView) findViewById(R.id.main_head_logo);
		imageView.setImageResource(R.drawable.article);
		progressBar = (LinearLayout) super.findViewById(R.id.myprogressbar);
		backBtn = (ImageView) super.findViewById(R.id.head_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View paramView) {
				ShowArticleActivity.this.finish();
			}
		});
		/*
		 * contentlayout = (LinearLayout) super
		 * .findViewById(R.id.showarticle_content);
		 */
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_article);

		Intent intent = getIntent();
		Bundle bunde = intent.getExtras();
		String articleId = bunde.getString("articleId");

		progressBar = (LinearLayout) findViewById(R.id.myprogressbar);
		Intent service = new Intent(this, MainService.class);
		startService(service);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("articleId", articleId);
		Task task = new Task(TaskType.SHOWARTICLE, param, this);
		MainService.newTask(task);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... obj) {
		// 隐藏loding
		progressBar.setVisibility(View.GONE);
		// 显示正文
		// contentlayout.setVisibility(View.VISIBLE);
		TextView showView = (TextView) findViewById(R.id.show_article_id);
		TextView titleView = (TextView) findViewById(R.id.show_article_title);
		TextView authorView = (TextView) findViewById(R.id.article_author);
		TextView timeView = (TextView) findViewById(R.id.article_time);
		TextView recountView = (TextView) findViewById(R.id.article_recount);
		Article article = (Article) obj[0];
		titleView.setText(article.getTitle());
		authorView.setText(article.getAuthorName());
		timeView.setText(article.getPostTime());
		recountView.setText(article.getReNumber());
		showView.setText(Html.fromHtml(article.getContent(), null,
				new MxgsaTagHandler(this)));
	}

}
