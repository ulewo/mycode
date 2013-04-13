package com.ulewo.ui;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.R;
import com.ulewo.bean.Article;
import com.ulewo.bean.Task;
import com.ulewo.enums.TaskType;
import com.ulewo.handler.MxgsaTagHandler;
import com.ulewo.logic.MainService;
import com.ulewo.util.Constants;

public class ShowArticleActivity extends BaseActivity implements IMainActivity {

	private LinearLayout progressBar = null;

	private Button backBtn = null;

	private LinearLayout recommentBtn = null;

	private TextView recommentCount = null;

	private int articleId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.show_article);
		ExitApplication.getInstance().addActivity(this);
		progressBar = (LinearLayout) super.findViewById(R.id.myprogressbar);
		backBtn = (Button) super.findViewById(R.id.head_back);
		backBtn.setVisibility(View.VISIBLE);

		recommentBtn = (LinearLayout) super
				.findViewById(R.id.article_recomment_btn);
		recommentBtn.setVisibility(View.VISIBLE);

		recommentCount = (TextView) super
				.findViewById(R.id.article_recomment_count);

		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {

				ShowArticleActivity.this.finish();
			}
		});

		recommentBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				Intent intent = new Intent();
				intent.putExtra("id", articleId);
				intent.setClass(ShowArticleActivity.this,
						ArticleCommentActivity.class);
				startActivity(intent);
			}
		});

		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.show_article);

		Intent intent = getIntent();
		Bundle bunde = intent.getExtras();
		String articleId = bunde.getString("articleId");
		Intent service = new Intent(this, MainService.class);
		startService(service);
		HashMap<String, Object> param = new HashMap<String, Object>(1);
		param.put("articleId", articleId);
		Task task = new Task(TaskType.SHOWARTICLE, param, this);
		MainService.newTask(task);
	}

	@Override
	public void refresh(Object... obj) {

		// 隐藏loding
		progressBar.setVisibility(View.GONE);
		// 显示正文
		// contentlayout.setVisibility(View.VISIBLE);
		HashMap<String, Object> myobj = (HashMap<String, Object>) obj[0];
		if (null != myobj.get("article")
				&& Constants.RESULTCODE_SUCCESS.equals(String.valueOf(myobj
						.get("result")))) {
			TextView showView = (TextView) findViewById(R.id.show_article_id);
			TextView titleView = (TextView) findViewById(R.id.show_article_title);
			TextView authorView = (TextView) findViewById(R.id.article_author);
			TextView timeView = (TextView) findViewById(R.id.article_time);
			TextView recountView = (TextView) findViewById(R.id.article_recount);
			Article article = (Article) myobj.get("article");
			articleId = article.getId();
			recommentCount.setText(article.getReNumber() + "");
			titleView.setText(article.getTitle());
			authorView.setText(article.getAuthorName());
			timeView.setText(article.getPostTime());
			recountView.setText(article.getReNumber());
			showView.setText(Html.fromHtml(article.getContent(), null,
					new MxgsaTagHandler(this)));
		} else {
			Toast.makeText(ShowArticleActivity.this, R.string.request_timeout,
					Toast.LENGTH_LONG).show();
			progressBar.setVisibility(View.GONE);
		}
	}

}
