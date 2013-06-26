package com.ulewo.ui;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.AppContext;
import com.ulewo.AppException;
import com.ulewo.R;
import com.ulewo.bean.ReTalk;
import com.ulewo.bean.ReTalkList;
import com.ulewo.bean.ReTalkResult;
import com.ulewo.cache.AsyncImageLoader;
import com.ulewo.cache.AsyncImageLoader.ImageCallback;
import com.ulewo.common.UIHelper;
import com.ulewo.util.Constants;
import com.ulewo.util.StringUtils;

public class ShowTalkActivity extends BaseActivity {

	private LinearLayout progressBar = null;

	private int talkId = 0;

	private AppContext appContext;

	private Handler handler;

	private LinearLayout talkDetal = null;

	private LinearLayout retalk_list = null;

	private LayoutInflater mInflater;

	private String userid = null;

	private String username = null;

	private int page = 1;

	private EditText retalkEditText = null;

	private Button subReTalkBtn = null;

	// 全局变量是否刷新
	boolean isRefresh = false;

	// 加载更多
	private LinearLayout loadMoreView = null;

	// 加载更多按钮
	private TextView loadmoreTextView = null;

	// 加载更多进度条
	private LinearLayout loadmore_prgressbar = null;

	// 头部状态栏
	private Button backBtn = null;
	// 刷新按钮
	private ImageButton refreshBtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.talk_detail);
		appContext = (AppContext) getApplication();
		initView();
		initData();
	}

	private void initView() {
		backBtn = (Button) findViewById(R.id.head_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(UIHelper.finish(ShowTalkActivity.this));

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

		retalkEditText = (EditText) findViewById(R.id.recomment_input);
		subReTalkBtn = (Button) findViewById(R.id.subrecomment);
		subReTalkBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null == AppContext.getSessionId()) {
					UIHelper.showLoginDialog(ShowTalkActivity.this);
					return;
				}
				String content = String.valueOf(retalkEditText.getText())
						.trim();
				if (StringUtils.isEmpty(content)) {
					Toast.makeText(ShowTalkActivity.this, R.string.nocontent,
							Toast.LENGTH_LONG).show();
					return;
				}
				if (content.length() > Constants.MAXCONTENTLENGTH) {
					Toast.makeText(ShowTalkActivity.this,
							R.string.contenttoolong, Toast.LENGTH_LONG).show();
					return;
				}
				subComment(content);
			}
		});

		mInflater = LayoutInflater.from(this);
		talkDetal = (LinearLayout) findViewById(R.id.talk_detail_layout);
		retalk_list = (LinearLayout) findViewById(R.id.retalk_list);

		progressBar = (LinearLayout) findViewById(R.id.myprogressbar);
		progressBar.setVisibility(View.VISIBLE);
		progressBar.setOnClickListener(UIHelper
				.noOnclick(ShowTalkActivity.this));
		LinearLayout talkItem = (LinearLayout) mInflater.inflate(
				R.layout.talk_item, null);
		final ImageView icon = (ImageView) talkItem
				.findViewById(R.id.talk_user_icon);
		TextView talk_username = (TextView) talkItem
				.findViewById(R.id.talk_username);
		TextView talk_content = (TextView) talkItem
				.findViewById(R.id.talk_content);
		final ImageView talk_img = (ImageView) talkItem
				.findViewById(R.id.talk_img);
		TextView talk_time = (TextView) talkItem.findViewById(R.id.talk_time);
		TextView talk_recount = (TextView) talkItem
				.findViewById(R.id.talk_recount);

		Intent intent = getIntent();
		Bundle bund = intent.getExtras();

		String userIcon = bund.getString("usericon");
		AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
		Drawable cachedIconImage = asyncImageLoader.loadDrawable(userIcon,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {

						if (null != imageDrawable) {
							icon.setImageDrawable(imageDrawable);
						} else {
							icon.setImageResource(R.drawable.icon);
						}
					}
				}, true);
		if (cachedIconImage == null) {
			icon.setImageResource(R.drawable.icon);
		} else {
			icon.setImageDrawable(StringUtils.toRoundCornerDrawable(
					cachedIconImage, 5));
		}
		userid = bund.getString("userid");
		username = bund.getString("username");
		talk_username.setText(username);
		talk_username.setOnClickListener(authorClickListener);
		String imgUrl = bund.getString("imgUrl");
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		final int screenWidthDip = dm.widthPixels;
		if (StringUtils.isNotEmpty(imgUrl)) {
			talk_img.setVisibility(View.VISIBLE);
			Drawable cachedImage = asyncImageLoader.loadDrawable(imgUrl,
					new ImageCallback() {
						public void imageLoaded(Drawable imageDrawable,
								String imageUrl) {
							if (null != imageDrawable) {
								Bitmap bitmap = ((BitmapDrawable) imageDrawable)
										.getBitmap();
								int imgwidth = bitmap.getWidth();
								if (imgwidth > screenWidthDip - 80) {
									imgwidth = screenWidthDip - 80;
								}
								LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
										imgwidth,
										LinearLayout.LayoutParams.WRAP_CONTENT);
								talk_img.setLayoutParams(p);
								talk_img.setImageDrawable(imageDrawable);
							} else {
								talk_img.setImageResource(R.drawable.imgloadingfail);
							}
						}
					}, true);
			if (cachedImage == null) {
				talk_img.setImageResource(R.drawable.imgloading);
			} else {
				Bitmap bitmap = ((BitmapDrawable) cachedImage).getBitmap();
				int imgwidth = bitmap.getWidth();
				if (imgwidth > screenWidthDip - 80) {
					imgwidth = screenWidthDip - 80;

					LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
							imgwidth, LinearLayout.LayoutParams.WRAP_CONTENT);
					talk_img.setLayoutParams(p);
					talk_img.setImageDrawable(StringUtils
							.toRoundCornerDrawable(cachedImage, 5));

				}
			}
		}
		talk_content.setText(bund.getString("content"));
		talk_time.setText(bund.getString("time"));
		talk_recount.setText(String.valueOf(bund.get("recount")));

		talkDetal = (LinearLayout) findViewById(R.id.talk_detail_layout);
		talkDetal.addView(talkItem);
		talkId = Integer.parseInt(bund.getString("talkId"));

		loadMoreView = (LinearLayout) View.inflate(this, R.layout.loadmore,
				null);

		// 要先讲加载更多添加到listView中在获取，不然获取不到。
		loadmore_prgressbar = (LinearLayout) loadMoreView
				.findViewById(R.id.loadmore_progressbar);
		loadmoreTextView = (TextView) loadMoreView
				.findViewById(R.id.loadmoretextview);
		loadmoreTextView.setVisibility(View.VISIBLE);
		loadmoreTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadmoreTextView.setVisibility(View.GONE);
				loadmore_prgressbar.setVisibility(View.VISIBLE);
				++page;
				initData();
			}
		});
	}

	private void initData() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what != -1 && null != msg.obj) {
					final ReTalkList list = (ReTalkList) msg.obj;
					ArrayList<ReTalk> retalkList = list.getReTalkList();
					AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
					retalk_list.removeView(loadMoreView);
					if (isRefresh) {
						retalk_list.removeAllViews();
					}
					for (final ReTalk retalk : retalkList) {
						LinearLayout talkItem = (LinearLayout) mInflater
								.inflate(R.layout.talk_item, null);
						retalk_list.addView(talkItem);
						final ImageView icon = (ImageView) talkItem
								.findViewById(R.id.talk_user_icon);
						TextView talk_username = (TextView) talkItem
								.findViewById(R.id.talk_username);
						TextView talk_content = (TextView) talkItem
								.findViewById(R.id.talk_content);
						TextView talk_time = (TextView) talkItem
								.findViewById(R.id.talk_time);
						ImageView countIcon = (ImageView) talkItem
								.findViewById(R.id.talk_recount_icon);
						countIcon.setVisibility(View.GONE);
						TextView talk_recount = (TextView) talkItem
								.findViewById(R.id.talk_recount);
						talk_recount.setVisibility(View.GONE);
						Drawable cachedIconImage = asyncImageLoader
								.loadDrawable(retalk.getUserIcon(),
										new ImageCallback() {
											public void imageLoaded(
													Drawable imageDrawable,
													String imageUrl) {

												if (null != imageDrawable) {
													icon.setImageDrawable(imageDrawable);
												} else {
													icon.setImageResource(R.drawable.icon);
												}
											}
										}, true);
						if (cachedIconImage == null) {
							icon.setImageResource(R.drawable.icon);
						} else {
							icon.setImageDrawable(StringUtils
									.toRoundCornerDrawable(cachedIconImage, 5));
						}
						username = retalk.getUserName();
						talk_username.setText(username);
						talk_username.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								UIHelper.showUserCenter(v.getContext(),
										retalk.getUserId(),
										retalk.getUserName());
							}
						});
						talk_content.setText(retalk.getContent());
						talk_time.setText(retalk.getCreateTime());
					}
					int pageTotal = list.getPageTotal();
					if (pageTotal > page) {
						retalk_list.addView(loadMoreView);
						loadmoreTextView.setVisibility(View.VISIBLE);
						loadmore_prgressbar.setVisibility(View.GONE);
					}
					progressBar.setVisibility(View.GONE);
				} else {
					((AppException) msg.obj).makeToast(ShowTalkActivity.this);
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
					ReTalkList list = appContext.reTalkList(page, isRefresh,
							talkId);
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
		progressBar.setVisibility(View.VISIBLE);
		InputMethodManager inputMethodManager = (InputMethodManager) ShowTalkActivity.this
				.getApplicationContext().getSystemService(
						Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(
				retalkEditText.getWindowToken(), 0);
		retalkEditText.setText("");
		final Handler subCommentHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what != -1) {
					ReTalkResult result = (ReTalkResult) msg.obj;
					if (result.isLogin()) {
						page = 1;
						isRefresh = true;
						initData();
					} else {
						UIHelper.showLoginDialog(ShowTalkActivity.this);
						return;
					}
				} else {
					((AppException) msg.obj).makeToast(ShowTalkActivity.this);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Message msg = new Message();
				try {
					ReTalkResult result = appContext.addReTalk(content, talkId);
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

	private View.OnClickListener authorClickListener = new View.OnClickListener() {
		public void onClick(View v) {

			UIHelper.showUserCenter(v.getContext(), userid, username);
		}
	};
}
