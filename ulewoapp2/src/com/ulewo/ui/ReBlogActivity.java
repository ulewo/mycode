package com.ulewo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.AppContext;
import com.ulewo.AppException;
import com.ulewo.R;
import com.ulewo.adapter.ReBlogListAdapter;
import com.ulewo.bean.ReBlogList;
import com.ulewo.bean.ReBlogResult;
import com.ulewo.cache.AsyncImageLoader;
import com.ulewo.common.UIHelper;
import com.ulewo.util.Constants;
import com.ulewo.util.StringUtils;

public class ReBlogActivity extends BaseActivity {

	private ReBlogListAdapter adapter = null;
	private View loadMoreView = null;

	private TextView loadmoreTextView = null;
	private LinearLayout loadmore_prgressbar = null;
	ListView listView = null;
	private ImageButton refreshBtn = null;
	private Button backBtn = null;
	private LinearLayout progressBar = null;

	private int page = 1;
	private int articleId = 0;
	boolean isRefresh = false;

	private EditText recomment_input = null;
	private Button subrecomment = null;

	Handler handler = null;
	Handler subCommentHandler = null;
	Handler subReCommentHandler = null;
	AppContext appContext = null;

	private LayoutInflater subflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.article_recomment);
		appContext = (AppContext) getApplication();
		subflater = LayoutInflater.from(ReBlogActivity.this);
		initView();
		initData();
	}

	private void initView() {

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		articleId = Integer.parseInt(String.valueOf(bundle.get("id")));

		progressBar = (LinearLayout) super.findViewById(R.id.myprogressbar);
		progressBar.setOnClickListener(UIHelper.noOnclick(this));
		progressBar.setOnClickListener(UIHelper.noOnclick(this));
		backBtn = (Button) super.findViewById(R.id.head_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(UIHelper.finish(this));
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
					UIHelper.showLoginDialog(ReBlogActivity.this);
					return;
				}
				String content = String.valueOf(recomment_input.getText())
						.trim();
				if (StringUtils.isEmpty(content)) {
					Toast.makeText(ReBlogActivity.this, R.string.nocontent,
							Toast.LENGTH_LONG).show();
					return;
				}
				if (content.length() > Constants.MAXCONTENTLENGTH) {
					Toast.makeText(ReBlogActivity.this,
							R.string.contenttoolong, Toast.LENGTH_LONG).show();
					return;
				}
				progressBar.setVisibility(View.VISIBLE);
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
					ReBlogList list = (ReBlogList) msg.obj;
					if (adapter == null || page == 1) {
						adapter = new ReBlogListAdapter(ReBlogActivity.this,
								list.getReBlogList(), new AsyncImageLoader(),
								listView);
						listView.setAdapter(adapter);
						if (page < msg.arg1) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					} else {
						loadmore_prgressbar.setVisibility(View.GONE);
						adapter.loadMore(list.getReBlogList());
						if (page < msg.arg1) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					}
				}

				else {
					((AppException) msg.obj).makeToast(ReBlogActivity.this);
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
					ReBlogList list = appContext.getReBlogList(articleId, page);
					msg.what = 0;
					msg.obj = list;
				} catch (AppException e) {
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
				progressBar.setVisibility(View.GONE);
				if (msg.what != -1) {
					ReBlogResult result = (ReBlogResult) msg.obj;
					if (result.isLogin()) {
						adapter.addItem(result.getReblog());
						recomment_input.setText("");
						InputMethodManager inputMethodManager = (InputMethodManager) ReBlogActivity.this
								.getApplicationContext().getSystemService(
										Context.INPUT_METHOD_SERVICE);
						inputMethodManager.hideSoftInputFromWindow(
								recomment_input.getWindowToken(), 0);
					} else {
						Intent intent = new Intent();
						intent.setClass(ReBlogActivity.this, UserActivity.class);
						startActivity(intent);
					}
				} else {
					((AppException) msg.obj).makeToast(ReBlogActivity.this);
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
					ReBlogResult result = appContext.addReBlog(content,
							articleId);
					msg.what = 0;
					msg.obj = result;
				} catch (AppException e) {
					msg.what = -1;
					msg.obj = e;
				}
				subCommentHandler.sendMessage(msg);
			}
		}.start();
	}
}
