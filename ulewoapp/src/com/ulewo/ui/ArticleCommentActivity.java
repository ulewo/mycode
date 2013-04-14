package com.ulewo.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.R;
import com.ulewo.adapter.ArticleCommentListAdapter;
import com.ulewo.bean.ReArticle;
import com.ulewo.bean.Task;
import com.ulewo.enums.TaskType;
import com.ulewo.logic.MainService;
import com.ulewo.util.Constants;

public class ArticleCommentActivity extends BaseActivity implements
		IMainActivity {

	private ArticleCommentListAdapter adapter = null;

	private View loadMoreView = null;

	private int page = 1;

	private TextView loadmoreTextView = null;

	private LinearLayout loadmore_prgressbar = null;

	ListView listView = null;

	private ImageButton refreshBtn = null;

	private Button backBtn = null;
	private LinearLayout progressBar = null;
	private String articleId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.article_recomment);
		ExitApplication.getInstance().addActivity(this);
		init();

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		articleId = String.valueOf(bundle.get("id"));

		Intent service = new Intent(this, MainService.class);
		startService(service);

		HashMap<String, Object> param = new HashMap<String, Object>(2);
		param.put("page", 0);
		param.put("articleId", articleId);
		Task task = new Task(TaskType.ARTICLECOMMENT, param, this);
		MainService.newTask(task);
	}

	private void init() {
		progressBar = (LinearLayout) super.findViewById(R.id.myprogressbar);
		backBtn = (Button) super.findViewById(R.id.head_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				ArticleCommentActivity.this.finish();
			}
		});

		loadMoreView = View.inflate(this, R.layout.loadmore, null);
		listView = (ListView) super.findViewById(R.id.recoment_list_view_id);
		listView.addFooterView(loadMoreView);

		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.recomment_tit_web);
		refreshBtn = (ImageButton) findViewById(R.id.head_refresh);
		refreshBtn.setVisibility(View.VISIBLE);
		refreshBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				progressBar.setVisibility(View.VISIBLE);
				page = 1;
				HashMap<String, Object> param = new HashMap<String, Object>(1);
				param.put("page", page);
				param.put("articleId", articleId);
				Task task = new Task(TaskType.ARTICLECOMMENT, param,
						ArticleCommentActivity.this);
				MainService.newTask(task);
			}
		});
		loadmore_prgressbar = (LinearLayout) findViewById(R.id.loadmore_progressbar);
		loadmoreTextView = (TextView) findViewById(R.id.loadmoretextview);
		loadmoreTextView.setVisibility(View.GONE);
		loadmoreTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				loadmoreTextView.setVisibility(View.GONE);
				loadmore_prgressbar.setVisibility(View.VISIBLE);
				Intent service = new Intent(ArticleCommentActivity.this,
						MainService.class);
				startService(service);
				HashMap<String, Object> param = new HashMap<String, Object>(1);
				param.put("page", ++page);
				param.put("articleId", articleId);
				Task task = new Task(TaskType.ARTICLECOMMENT, param,
						ArticleCommentActivity.this);
				MainService.newTask(task);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... obj) {
		progressBar.setVisibility(View.GONE);
		refreshBtn.clearAnimation();
		HashMap<String, Object> myobj = (HashMap<String, Object>) obj[0];
		if (null != myobj.get("list")
				&& Constants.RESULTCODE_SUCCESS.equals(String.valueOf(myobj
						.get("result")))) {
			List<ReArticle> list = (ArrayList<ReArticle>) myobj.get("list");
			if (adapter == null || page == 1) {
				adapter = new ArticleCommentListAdapter(this, list);
				listView.setAdapter(adapter);
				if (page < Integer.parseInt(myobj.get("pageTotal").toString())) {
					loadmoreTextView.setVisibility(View.VISIBLE);
				}
			} else {
				loadmore_prgressbar.setVisibility(View.GONE);
				adapter.loadMore(list);
				if (page < Integer.parseInt(myobj.get("pageTotal").toString())) {
					loadmoreTextView.setVisibility(View.VISIBLE);
				}
			}
		} else {
			Toast.makeText(ArticleCommentActivity.this,
					R.string.request_timeout, Toast.LENGTH_LONG).show();
			progressBar.setVisibility(View.GONE);
			loadmoreTextView.setVisibility(View.VISIBLE);
		}
	}

}
