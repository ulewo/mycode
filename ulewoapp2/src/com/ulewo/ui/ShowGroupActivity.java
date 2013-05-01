package com.ulewo.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ulewo.AppContext;
import com.ulewo.AppException;
import com.ulewo.R;
import com.ulewo.adapter.ArticleListAdapter;
import com.ulewo.bean.ArticleList;
import com.ulewo.cache.AsyncImageLoader;
import com.ulewo.cache.AsyncImageLoader.ImageCallback;
import com.ulewo.common.UIHelper;
import com.ulewo.util.StringUtils;

public class ShowGroupActivity extends BaseActivity {

	private LinearLayout progressBar = null;
	private View loadMoreView = null;
	private TextView loadmoreTextView = null;
	private LinearLayout loadmore_prgressbar = null;
	ListView listView = null;
	private ImageButton refreshBtn = null;
	private Button backBtn = null;
	private String gid;
	private int page = 1;
	private boolean isRefresh;

	private AppContext appContext;
	private ArticleListAdapter adapter = null;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.group);
		appContext = (AppContext) getApplication();
		initView();
		initData();
	}

	private void initView() {

		TextView textView = (TextView) findViewById(R.id.main_head_title);

		Intent intent = getIntent();
		Bundle bunde = intent.getExtras();
		this.gid = bunde.getString("gid");
		String groupIcon = bunde.getString("groupIcon");
		String gName = bunde.getString("gName");
		String gUserName = bunde.getString("gUserName");
		int gMember = bunde.getInt("gMember");
		int gArticleCount = bunde.getInt("gArticleCount");

		final ImageView group_icon = (ImageView) findViewById(R.id.wowo_icon);
		TextView titView = (TextView) findViewById(R.id.wowo_tit);
		TextView authorView = (TextView) findViewById(R.id.wowo_username_con);
		TextView memberView = (TextView) findViewById(R.id.wowo_member_con);
		TextView articleView = (TextView) findViewById(R.id.wowo_articlecount_con);
		// 设置窝窝标题
		textView.setText(gName);
		AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
		Drawable cachedImage = asyncImageLoader.loadDrawable(groupIcon,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {

						group_icon.setImageDrawable(imageDrawable);
					}
				});
		if (cachedImage == null) {
			group_icon.setImageResource(R.drawable.icon);
		} else {
			group_icon.setImageDrawable(StringUtils.toRoundCornerDrawable(
					cachedImage, 5));
		}

		titView.setText(gName);
		authorView.setText(gUserName);
		memberView.setText(gMember + "");
		articleView.setText(gArticleCount + "");

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
				isRefresh = false;
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
		backBtn = (Button) super.findViewById(R.id.head_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(UIHelper.finish(this));
	}

	private void initData() {

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				progressBar.setVisibility(View.GONE);
				if (msg.what != -1 && null != msg.obj) {
					ArticleList list = (ArticleList) msg.obj;
					if (adapter == null || page == 1) {
						adapter = new ArticleListAdapter(
								ShowGroupActivity.this, list.getArticleList());
						listView.setAdapter(adapter);
						if (page < list.getPageTotal()) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					} else {
						loadmore_prgressbar.setVisibility(View.GONE);
						adapter.loadMore(list.getArticleList());
						if (page < list.getPageTotal()) {
							loadmoreTextView.setVisibility(View.VISIBLE);
						}
					}
					listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent,
								View view, int postion, long id) {

							String articleId = String.valueOf(adapter
									.getItemId(postion));
							if (!"0".equals(articleId)) {
								Intent intent = new Intent();
								intent.putExtra("articleId", articleId);
								intent.setClass(ShowGroupActivity.this,
										ShowArticleActivity.class);
								startActivity(intent);
							}
						}
					});
				} else {
					((AppException) msg.obj).makeToast(ShowGroupActivity.this);
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
					ArticleList list = appContext.getGroupArticleList(gid,
							isRefresh, page);
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
}
