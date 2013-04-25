package com.ulewo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ulewo.AppContext;
import com.ulewo.AppException;
import com.ulewo.R;
import com.ulewo.adapter.ArticleListAdapter;
import com.ulewo.bean.ArticleList;

public class ArticleActivity extends BaseActivity {

	//进度条
	private LinearLayout progressBar = null;
	//加载更多
	private View loadMoreView = null;
	//加载更多按钮
	private TextView loadmoreTextView = null;
	//加载更多进度条
	private LinearLayout loadmore_prgressbar = null;
	//刷新按钮
	private ImageButton refreshBtn = null;

	//全局页码
	private int page = 1;
	//全局变量是否刷新
	boolean isRefresh = false;

	ListView listView = null;
	private ArticleListAdapter adapter = null;
	private Handler handler = null;
	private AppContext appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.article);
		appContext = (AppContext) getApplication();
		initView();
		initData();
	}

	private void initView() {

		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_article);

		progressBar = (LinearLayout) findViewById(R.id.myprogressbar);
		progressBar.setVisibility(View.VISIBLE);
		listView = (ListView) findViewById(R.id.article_list_view_id);

		loadMoreView = View.inflate(this, R.layout.loadmore, null);
		listView.addFooterView(loadMoreView);

		//要先讲加载更多添加到listView中在获取，不然获取不到。
		loadmore_prgressbar = (LinearLayout) findViewById(R.id.loadmore_progressbar);
		loadmoreTextView = (TextView) findViewById(R.id.loadmoretextview);
		loadmoreTextView.setVisibility(View.GONE);
		loadmoreTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				loadmoreTextView.setVisibility(View.GONE);
				loadmore_prgressbar.setVisibility(View.VISIBLE);
				++page;
				initData();
			}
		});
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
	}

	private void initData() {

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				progressBar.setVisibility(View.GONE);
				if (msg.what != -1) {
					ArticleList list = (ArticleList) msg.obj;
					if (adapter == null || page == 1) {
						adapter = new ArticleListAdapter(ArticleActivity.this, list.getArticleList());
						listView.setAdapter(adapter);
						if (page < list.getPageTotal()) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					}
					else {
						loadmore_prgressbar.setVisibility(View.GONE);
						adapter.loadMore(list.getArticleList());
						if (page < list.getPageTotal()) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					}
					listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {

							String articleId = String.valueOf(adapter.getItemId(postion));
							if (!"0".equals(articleId)) {
								Intent intent = new Intent();
								intent.putExtra("articleId", articleId);
								intent.setClass(ArticleActivity.this, ShowArticleActivity.class);
								startActivity(intent);
							}
						}
					});
				}
				else {
					((AppException) msg.obj).makeToast(ArticleActivity.this);
					progressBar.setVisibility(View.GONE);
					loadmoreTextView.setVisibility(View.VISIBLE);
					loadmore_prgressbar.setVisibility(View.GONE);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {

				Message msg = new Message();
				try {
					ArticleList list = appContext.getArticleList(page, isRefresh);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isExit();
		}
		return super.onKeyDown(keyCode, event);
	}

}
