package com.ulewo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ulewo.AppContext;
import com.ulewo.AppException;
import com.ulewo.R;
import com.ulewo.bean.Article;
import com.ulewo.bean.Blog;
import com.ulewo.handler.MxgsaTagHandler;

public class ShowBlogActivity extends BaseActivity {

	private LinearLayout progressBar = null;

	private Button backBtn = null;

	private int articleId = 0;

	private Handler handler = null;

	TextView showView = null;

	TextView titleView = null;

	TextView authorView = null;

	TextView timeView = null;

	TextView recountView = null;

	private AppContext appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.show_article);
		appContext = (AppContext) getApplication();
		initView();
		initData();
	}

	private void initView() {

		titleView = (TextView) findViewById(R.id.show_article_title);
		authorView = (TextView) findViewById(R.id.article_author);
		timeView = (TextView) findViewById(R.id.article_time);
		recountView = (TextView) findViewById(R.id.article_recount);
		showView = (TextView) findViewById(R.id.show_article_id);

		progressBar = (LinearLayout) super.findViewById(R.id.myprogressbar);
		backBtn = (Button) super.findViewById(R.id.head_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {

				ShowBlogActivity.this.finish();
			}
		});
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.show_blog);

		progressBar = (LinearLayout) findViewById(R.id.myprogressbar);
	}

	private void initData() {

		Intent intent = getIntent();
		Bundle bunde = intent.getExtras();
		articleId = Integer.parseInt(bunde.getString("articleId"));

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				progressBar.setVisibility(View.GONE);
				if (msg.what != -1) {
					Blog blog = (Blog) msg.obj;
					titleView.setText(blog.getTitle());
					authorView.setText(blog.getAuthorName());
					timeView.setText(blog.getPostTime());
					recountView.setText(blog.getReNumber() + "");
					showView.setText(Html.fromHtml(blog.getContent(), null, new MxgsaTagHandler(ShowBlogActivity.this)));
				}
				else {
					((AppException) msg.obj).makeToast(ShowBlogActivity.this);
					progressBar.setVisibility(View.GONE);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {

				Message msg = new Message();
				try {
					Article article = appContext.getArticle(articleId);
					msg.what = 0;
					msg.obj = article;
				}
				catch (AppException e) {
					msg.what = -1;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}
}
