package com.ulewo.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ulewo.AppContext;
import com.ulewo.R;
import com.ulewo.cache.AsyncImageLoader;
import com.ulewo.cache.AsyncImageLoader.ImageCallback;
import com.ulewo.common.UIHelper;
import com.ulewo.util.StringUtils;

public class ShowTalkActivity extends BaseActivity {

	private LinearLayout progressBar = null;

	private Button backBtn = null;

	private int talkId = 0;

	private AppContext appContext;

	private Handler handler;

	private LinearLayout talkDetal = null;

	private LinearLayout retalk_list = null;

	private LayoutInflater mInflater;

	private String userid = null;

	private String username = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.talk_detail);
		appContext = (AppContext) getApplication();
		initView();
		//initData();
	}

	private void initView() {

		mInflater = LayoutInflater.from(this);
		talkDetal = (LinearLayout) findViewById(R.id.talk_detail_layout);
		//	retalk_list = (LinearLayout) findViewById(R.id.retalk_list);
		LinearLayout talkItem = (LinearLayout) mInflater.inflate(R.layout.talk_item, null);
		final ImageView icon = (ImageView) talkItem.findViewById(R.id.talk_user_icon);
		TextView talk_username = (TextView) talkItem.findViewById(R.id.talk_username);
		TextView talk_content = (TextView) talkItem.findViewById(R.id.talk_content);
		final ImageView talk_img = (ImageView) talkItem.findViewById(R.id.talk_img);
		TextView talk_time = (TextView) talkItem.findViewById(R.id.talk_time);
		TextView talk_recount = (TextView) talkItem.findViewById(R.id.talk_recount);

		Intent intent = getIntent();
		Bundle bund = intent.getExtras();

		String userIcon = bund.getString("usericon");
		AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
		Drawable cachedIconImage = asyncImageLoader.loadDrawable(userIcon, new ImageCallback() {
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {

				if (null != imageDrawable) {
					icon.setImageDrawable(imageDrawable);
				}
				else {
					icon.setImageResource(R.drawable.icon);
				}
			}
		}, true);
		if (cachedIconImage == null) {
			icon.setImageResource(R.drawable.icon);
		}
		else {
			icon.setImageDrawable(StringUtils.toRoundCornerDrawable(cachedIconImage, 5));
		}
		userid = bund.getString("userid");
		username = bund.getString("username");
		talk_username.setText(username);
		talk_username.setOnClickListener(authorClickListener);
		String imgUrl = bund.getString("imgUrl");
		if (StringUtils.isNotEmpty(imgUrl)) {
			talk_img.setVisibility(View.VISIBLE);
			Drawable cachedImage = asyncImageLoader.loadDrawable(imgUrl, new ImageCallback() {
				public void imageLoaded(Drawable imageDrawable, String imageUrl) {

					if (null != imageDrawable) {
						talk_img.setImageDrawable(imageDrawable);
					}
					else {
						talk_img.setImageResource(R.drawable.icon);
					}
				}
			}, true);
			if (cachedImage == null) {
				talk_img.setImageResource(R.drawable.icon);
			}
			else {
				talk_img.setImageDrawable(StringUtils.toRoundCornerDrawable(cachedImage, 5));
			}
		}
		talk_content.setText(bund.getString("content"));
		talk_time.setText(bund.getString("time"));
		talk_recount.setText(String.valueOf(bund.get("recount")));

		talkDetal = (LinearLayout) findViewById(R.id.talk_detail_layout);
		talkDetal.addView(talkItem);
	}

	private void initData() {

		//	articleId = Integer.parseInt(bunde.getString("articleId"));

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

			}
		};
		new Thread() {
			@Override
			public void run() {

			}
		}.start();
	}

	private View.OnClickListener authorClickListener = new View.OnClickListener() {
		public void onClick(View v) {

			UIHelper.showUserCenter(v.getContext(), userid, username);
		}
	};
}
