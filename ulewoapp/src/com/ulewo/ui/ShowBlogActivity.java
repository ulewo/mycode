package com.ulewo.ui;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.R;
import com.ulewo.bean.Blog;
import com.ulewo.bean.Task;
import com.ulewo.enums.TaskType;
import com.ulewo.handler.MxgsaTagHandler;
import com.ulewo.logic.MainService;

public class ShowBlogActivity extends Activity implements IMainActivity {

	private LinearLayout progressBar = null;

	private ImageButton backBtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.show_article);
		ImageView imageView = (ImageView) findViewById(R.id.main_head_logo);
		imageView.setImageResource(R.drawable.blog);
		progressBar = (LinearLayout) super.findViewById(R.id.myprogressbar);
		backBtn = (ImageButton) super.findViewById(R.id.head_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {

				ShowBlogActivity.this.finish();
			}
		});
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_blog);

		Intent intent = getIntent();
		Bundle bunde = intent.getExtras();
		String articleId = bunde.getString("articleId");
		progressBar = (LinearLayout) findViewById(R.id.myprogressbar);
		Intent service = new Intent(this, MainService.class);
		startService(service);
		HashMap<String, Object> param = new HashMap<String, Object>(1);
		param.put("articleId", articleId);
		Task task = new Task(TaskType.SHOWBLOG, param, this);
		MainService.newTask(task);
	}

	@Override
	public void refresh(Object... obj) {

		// 隐藏loding
		progressBar.setVisibility(View.GONE);
		if (obj[0] != null) {
			TextView showView = (TextView) findViewById(R.id.show_article_id);
			TextView titleView = (TextView) findViewById(R.id.show_article_title);
			TextView authorView = (TextView) findViewById(R.id.article_author);
			TextView timeView = (TextView) findViewById(R.id.article_time);
			TextView recountView = (TextView) findViewById(R.id.article_recount);
			Blog article = (Blog) obj[0];
			titleView.setText(article.getTitle());
			authorView.setText(article.getAuthorName());
			timeView.setText(article.getPostTime());
			recountView.setText(article.getReNumber());
			showView.setText(Html.fromHtml(article.getContent(), null, new MxgsaTagHandler(this)));
		}
		else {
			Toast.makeText(ShowBlogActivity.this, R.string.request_timeout, Toast.LENGTH_LONG).show();
		}
	}

}
