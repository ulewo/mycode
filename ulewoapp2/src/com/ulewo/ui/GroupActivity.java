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
import com.ulewo.adapter.GroupListAdapter;
import com.ulewo.bean.Group;
import com.ulewo.bean.GroupList;
import com.ulewo.cache.AsyncImageLoader;

public class GroupActivity extends BaseActivity {

	private LinearLayout progressBar = null;
	private View loadMoreView = null;
	private TextView loadmoreTextView = null;
	private LinearLayout loadmore_prgressbar = null;
	ListView listView = null;
	private ImageButton refreshBtn = null;

	private int page = 1;
	private boolean isRefresh;
	private String gid;

	private GroupListAdapter adapter = null;
	private AppContext appContext;
	private Handler handler = null;

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
		textView.setText(R.string.name_wowo);

		progressBar = (LinearLayout) findViewById(R.id.myprogressbar);
		progressBar.setVisibility(View.VISIBLE);
		loadMoreView = View.inflate(this, R.layout.loadmore, null);
		listView = (ListView) findViewById(R.id.article_list_view_id);
		listView.addFooterView(loadMoreView);

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
					GroupList list = (GroupList) msg.obj;
					if (adapter == null || page == 1) {
						adapter = new GroupListAdapter(GroupActivity.this, list.getGroupList(), new AsyncImageLoader(),
								listView);
						listView.setAdapter(adapter);
						if (page < list.getPageTotal()) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					}
					else {
						loadmore_prgressbar.setVisibility(View.GONE);
						adapter.loadMore(list.getGroupList());
						if (page < list.getPageTotal()) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					}
					listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {

							Group group = (Group) adapter.getItem(postion);
							String gid = group.getGid();
							String groupIcon = group.getGroupIcon();
							String gName = group.getgName();
							String gUserName = group.getgAuthorName();
							int gMember = group.getgMember();
							int gArticleCount = group.getgArticleCount();
							if (!"0".equals(gid)) {
								Intent intent = new Intent();
								intent.putExtra("gid", gid);
								intent.putExtra("groupIcon", groupIcon);
								intent.putExtra("gName", gName);
								intent.putExtra("gUserName", gUserName);
								intent.putExtra("gMember", gMember);
								intent.putExtra("gArticleCount", gArticleCount);
								intent.setClass(GroupActivity.this, ShowGroupActivity.class);
								startActivity(intent);
							}
						}
					});
				}
				else {
					((AppException) msg.obj).makeToast(GroupActivity.this);
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
					GroupList list = appContext.getGroupList(page, isRefresh);
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isExit();
		}
		return super.onKeyDown(keyCode, event);
	}
}
