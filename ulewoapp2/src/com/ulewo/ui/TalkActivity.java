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
import com.ulewo.adapter.TalkListAdapter;
import com.ulewo.bean.Talk;
import com.ulewo.bean.TalkList;
import com.ulewo.cache.AsyncImageLoader;

public class TalkActivity extends BaseActivity {

	private TextView main_head_title = null;

	private LinearLayout progressBar = null;

	// 加载更多
	private View loadMoreView = null;

	// 加载更多按钮
	private TextView loadmoreTextView = null;

	// 加载更多进度条
	private LinearLayout loadmore_prgressbar = null;

	// 刷新按钮
	private ImageButton refreshBtn = null;

	// 发表吐槽按钮
	private ImageButton postBtn = null;

	// 全局页码
	private int page = 1;

	// 全局变量是否刷新
	boolean isRefresh = false;

	ListView listView = null;

	private TalkListAdapter adapter = null;

	private Handler handler = null;

	private AppContext appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.talk);
		appContext = (AppContext) getApplication();
		Intent intent = getIntent();
		Bundle bunde = intent.getExtras();
		if (bunde != null) {
			page = 1;
			isRefresh = true;
		}
		initView();
		initData();
	}

	@Override
	protected void onRestart() {

		super.onRestart();
	}

	private void initView() {

		main_head_title = (TextView) findViewById(R.id.main_head_title);
		main_head_title.setText(R.string.name_talk);
		progressBar = (LinearLayout) findViewById(R.id.myprogressbar);
		progressBar.setVisibility(View.VISIBLE);
		listView = (ListView) findViewById(R.id.talk_list_id);

		loadMoreView = View.inflate(this, R.layout.loadmore, null);
		listView.addFooterView(loadMoreView);

		// 要先讲加载更多添加到listView中在获取，不然获取不到。
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
		postBtn = (ImageButton) findViewById(R.id.head_post);
		postBtn.setVisibility(View.VISIBLE);
		postBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(TalkActivity.this, TalkPostActivity.class);
				startActivityForResult(intent, 20);
				// startActivity(intent);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 20) {
			progressBar.setVisibility(View.VISIBLE);
			page = 1;
			isRefresh = true;
			initData();
		}
	}

	private void initData() {

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				progressBar.setVisibility(View.GONE);
				if (msg.what != -1 && null != msg.obj) {
					final TalkList list = (TalkList) msg.obj;
					if (adapter == null || page == 1) {
						adapter = new TalkListAdapter(TalkActivity.this, list.getTalkList(), new AsyncImageLoader(),
								listView);
						listView.setAdapter(adapter);
						if (page < list.getPageTotal()) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					}
					else {
						loadmore_prgressbar.setVisibility(View.GONE);
						adapter.loadMore(list.getTalkList());
						if (page < list.getPageTotal()) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					}
					listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {

							String talkId = String.valueOf(adapter.getItemId(postion));
							Talk talk = list.getTalkList().get(postion);
							if (!"0".equals(talkId)) {
								Intent intent = new Intent();
								intent.putExtra("talkId", talkId);
								intent.putExtra("imgUrl", talk.getImgurl());
								intent.putExtra("username", talk.getUserName());
								intent.putExtra("usericon", talk.getUserIcon());
								intent.putExtra("userid", talk.getUserId());
								intent.putExtra("content", talk.getContent());
								intent.putExtra("time", talk.getCreateTime());
								intent.putExtra("recount", talk.getReCount());
								intent.setClass(TalkActivity.this, ShowTalkActivity.class);
								startActivity(intent);
							}
						}
					});
				}
				else {
					((AppException) msg.obj).makeToast(TalkActivity.this);
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
					TalkList list = appContext.getTalkList(page, isRefresh);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isExit();
		}
		return super.onKeyDown(keyCode, event);
	}
}
