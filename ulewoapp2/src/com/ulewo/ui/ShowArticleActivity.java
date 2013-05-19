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
import com.ulewo.bean.Article;
import com.ulewo.common.UIHelper;

public class ShowArticleActivity extends BaseActivity {

	private LinearLayout progressBar = null;

	private Button backBtn = null;
	private LinearLayout recommentBtn = null;
	private TextView recommentCount = null;
	private WebView showView = null;
	private TextView titleView = null;
	private TextView authorView = null;
	private TextView timeView = null;
	private TextView recountView = null;
	private int articleId = 0;

	private Handler handler = null;
	private AppContext appContext;
	Article article = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.show_article);
		appContext = (AppContext) getApplication();
		initView();
		initData();
	}

	private void initView() {

		progressBar = (LinearLayout) super.findViewById(R.id.myprogressbar);
		progressBar.setVisibility(View.VISIBLE);
		progressBar.setOnClickListener(UIHelper.noOnclick(this));
		backBtn = (Button) super.findViewById(R.id.head_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(UIHelper.finish(this));
		recommentBtn = (LinearLayout) super
				.findViewById(R.id.article_recomment_btn);
		recommentBtn.setVisibility(View.VISIBLE);

		recommentCount = (TextView) super
				.findViewById(R.id.article_recomment_count);
		showView = (WebView) findViewById(R.id.show_article_id);
		showView.getSettings().setJavaScriptEnabled(false);
		showView.getSettings().setSupportZoom(true);
		showView.getSettings().setBuiltInZoomControls(true);
		showView.getSettings().setDefaultFontSize(13);

		titleView = (TextView) findViewById(R.id.show_article_title);
		authorView = (TextView) findViewById(R.id.article_author);
		timeView = (TextView) findViewById(R.id.article_time);
		recountView = (TextView) findViewById(R.id.article_recount);

		recommentBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {

				Intent intent = new Intent();
				intent.putExtra("id", articleId);
				intent.setClass(ShowArticleActivity.this,
						ReArticleActivity.class);
				startActivity(intent);
			}
		});
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.show_article);
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
					article = (Article) msg.obj;
					recommentCount.setText(article.getReNumber() + "");
					titleView.setText(article.getTitle());
					TextPaint paint1 = authorView.getPaint();
					paint1.setFakeBoldText(true);
					authorView.setText(article.getAuthorName());
					timeView.setText(article.getPostTime());
					recountView.setText(article.getReNumber() + "");
					String body = UIHelper.WEB_STYLE + article.getContent();

					showView.loadDataWithBaseURL(null, body, "text/html",
							"utf-8", null);
					showView.setWebViewClient(UIHelper.getWebViewClient());

				} else {
					((AppException) msg.obj)
							.makeToast(ShowArticleActivity.this);
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
			UIHelper.showUserCenter(v.getContext(), article.getAuthorId(),
					article.getAuthorName());
		}
	};
}
