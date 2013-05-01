package com.ulewo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ulewo.AppContext;
import com.ulewo.AppException;
import com.ulewo.R;
import com.ulewo.bean.Blog;
import com.ulewo.common.UIHelper;

public class ShowBlogActivity extends BaseActivity {

	private LinearLayout progressBar = null;

	private Button backBtn = null;

	private int articleId = 0;

	private Handler handler = null;

	WebView showView = null;
	TextView titleView = null;
	TextView authorView = null;
	TextView timeView = null;
	TextView recountView = null;
	private LinearLayout recommentBtn = null;
	private TextView recommentCount = null;

	private AppContext appContext;
	Blog blog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.show_article);
		appContext = (AppContext) getApplication();
		initView();
		initData();
	}

	private void initView() {
		recommentBtn = (LinearLayout) super
				.findViewById(R.id.article_recomment_btn);
		recommentBtn.setVisibility(View.VISIBLE);
		recommentBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {

				Intent intent = new Intent();
				intent.putExtra("id", articleId);
				intent.setClass(ShowBlogActivity.this, ReBlogActivity.class);
				startActivity(intent);
			}
		});

		recommentCount = (TextView) super
				.findViewById(R.id.article_recomment_count);

		titleView = (TextView) findViewById(R.id.show_article_title);
		authorView = (TextView) findViewById(R.id.article_author);
		TextPaint paint1 = authorView.getPaint();
		paint1.setFakeBoldText(true);

		timeView = (TextView) findViewById(R.id.article_time);
		recountView = (TextView) findViewById(R.id.article_recount);
		showView = (WebView) findViewById(R.id.show_article_id);
		showView.getSettings().setJavaScriptEnabled(false);
		showView.getSettings().setSupportZoom(true);
		showView.getSettings().setBuiltInZoomControls(true);
		showView.getSettings().setDefaultFontSize(13);
		progressBar = (LinearLayout) super.findViewById(R.id.myprogressbar);
		progressBar.setVisibility(View.VISIBLE);
		progressBar.setOnClickListener(UIHelper.noOnclick(this));
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

		authorView.setOnClickListener(authorClickListener);

	}

	private void initData() {

		Intent intent = getIntent();
		Bundle bunde = intent.getExtras();
		articleId = Integer.parseInt(bunde.getString("articleId"));

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				progressBar.setVisibility(View.GONE);
				if (msg.what != -1 && null != msg.obj) {
					blog = (Blog) msg.obj;
					titleView.setText(blog.getTitle());
					authorView.setText(blog.getAuthorName());
					timeView.setText(blog.getPostTime());
					recountView.setText(blog.getReNumber() + "");
					String body = UIHelper.WEB_STYLE + blog.getContent();
					showView.loadDataWithBaseURL(null, body, "text/html",
							"utf-8", null);
					showView.setWebViewClient(UIHelper.getWebViewClient());
					recommentCount.setText(blog.getReNumber() + "");
				} else {
					((AppException) msg.obj).makeToast(ShowBlogActivity.this);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {

				Message msg = new Message();
				try {
					Blog blog = appContext.getBlog(articleId);
					msg.what = 0;
					msg.obj = blog;
				} catch (AppException e) {
					msg.what = -1;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	private View.OnClickListener authorClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			UIHelper.showUserCenter(v.getContext(), blog.getAuthorId(),
					blog.getAuthorName());
		}
	};
}
