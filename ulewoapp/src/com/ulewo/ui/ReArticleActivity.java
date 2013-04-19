package com.ulewo.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.AppContext;
import com.ulewo.AppException;
import com.ulewo.R;
import com.ulewo.adapter.ReArticleListAdapter;
import com.ulewo.bean.ReArticle;
import com.ulewo.bean.ReArticleList;
import com.ulewo.bean.ReArticleResult;
import com.ulewo.util.StringUtils;

public class ReArticleActivity extends BaseActivity {

	private ReArticleListAdapter adapter = null;

	private View loadMoreView = null;

	private int page = 1;

	private TextView loadmoreTextView = null;

	private LinearLayout loadmore_prgressbar = null;

	ListView listView = null;

	private ImageButton refreshBtn = null;

	private Button backBtn = null;

	private LinearLayout progressBar = null;

	private int articleId = 0;

	private EditText recomment_input = null;

	private Button subrecomment = null;

	private RelativeLayout reSubPanel = null;

	Handler handler = null;

	Handler subCommentHandler = null;

	AppContext appContext = null;

	boolean isRefresh = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.article_recomment);
		appContext = (AppContext) getApplication();
		initView();
		initData();
	}

	private void initView() {

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		articleId = Integer.parseInt(String.valueOf(bundle.get("id")));

		progressBar = (LinearLayout) super.findViewById(R.id.myprogressbar);
		backBtn = (Button) super.findViewById(R.id.head_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {

				ReArticleActivity.this.finish();
			}
		});
		reSubPanel = (RelativeLayout) findViewById(R.id.article_subrepanel);
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
				isRefresh = true;
				initData();
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
				page++;
				initData();
			}
		});
		recomment_input = (EditText) super.findViewById(R.id.recomment_input);
		subrecomment = (Button) super.findViewById(R.id.subrecomment);
		subrecomment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {

				if (null == AppContext.getSessionId()) {
					Toast.makeText(ReArticleActivity.this, R.string.pleaselogin, Toast.LENGTH_LONG).show();
					return;
				}
				String content = String.valueOf(recomment_input.getText()).trim();
				if (StringUtils.isEmpty(content)) {
					Toast.makeText(ReArticleActivity.this, R.string.nocontent, Toast.LENGTH_LONG).show();
					return;
				}
				subComment(content);
			}
		});
	}

	private void initData() {

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				progressBar.setVisibility(View.GONE);
				if (msg.what != -1) {
					ReArticleList list = (ReArticleList) msg.obj;
					if (adapter == null || page == 1) {
						adapter = new ReArticleListAdapter(ReArticleActivity.this, list.getReArticleList(), reSubPanel);
						listView.setAdapter(adapter);
						if (page < msg.arg1) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					}
					else {
						loadmore_prgressbar.setVisibility(View.GONE);
						adapter.loadMore(list.getReArticleList());
						if (page < msg.arg1) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					}

				}
				else {
					((AppException) msg.obj).makeToast(ReArticleActivity.this);
					progressBar.setVisibility(View.GONE);
					loadmoreTextView.setVisibility(View.VISIBLE);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {

				Message msg = new Message();
				try {
					ReArticleList list = appContext.getReArticleList(articleId, page, isRefresh);
					msg.what = 0;
					msg.obj = list;
				}
				catch (AppException e) {
					msg.what = -1;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	private void subComment(final String content) {

		subCommentHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				if (msg.what != -1) {
					ReArticleResult result = (ReArticleResult) msg.obj;
					if (result.isLogin()) {
						ArrayList<ReArticle> list = new ArrayList<ReArticle>();
						list.add(result.getReArticle());
						adapter.loadMore(list);
						recomment_input.setText("");
						recomment_input.setFocusable(false);
					}
					else {
						Intent intent = new Intent();
						intent.setClass(ReArticleActivity.this, UserActivity.class);
						startActivity(intent);
					}
				}
				else {
					((AppException) msg.obj).makeToast(ReArticleActivity.this);
					progressBar.setVisibility(View.GONE);
					loadmoreTextView.setVisibility(View.VISIBLE);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {

				Message msg = new Message();
				try {
					ReArticleResult result = appContext.addReArticle(content, articleId);
					msg.what = 0;
					msg.obj = result;
				}
				catch (AppException e) {
					msg.what = -1;
					msg.obj = e;
				}
				subCommentHandler.sendMessage(msg);
			}
		}.start();
	}
}
