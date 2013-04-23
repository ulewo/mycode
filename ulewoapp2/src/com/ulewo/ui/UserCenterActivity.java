package com.ulewo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.AppContext;
import com.ulewo.AppException;
import com.ulewo.R;
import com.ulewo.bean.User;
import com.ulewo.util.Constants;

public class UserCenterActivity extends BaseActivity {

	LinearLayout userinfolayout = null;

	ImageView user_info_icon = null;

	ImageView user_info_sex = null;

	TextView user_info_username = null;

	TextView user_info_jointime = null;

	TextView user_info_age = null;

	TextView user_info_mark = null;

	TextView user_info_address = null;

	TextView user_info_work = null;

	TextView user_info_characters = null;

	Button head_back = null;
	// 刷新按钮
	ImageButton head_refresh = null;

	Handler userHandler = null;

	AppContext appContext = null;

	String userId = null;

	boolean isRefresh = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.usercenter);
		appContext = (AppContext) getApplication();
		initView();
		fetchUserInfo();
	}

	private void initView() {

		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_user);
		userinfolayout = (LinearLayout) findViewById(R.id.userinfolayout);
		userinfolayout.setVisibility(View.VISIBLE);
		user_info_icon = (ImageView) findViewById(R.id.user_info_icon);
		user_info_sex = (ImageView) findViewById(R.id.user_info_sex);
		user_info_username = (TextView) findViewById(R.id.user_info_username);
		user_info_jointime = (TextView) findViewById(R.id.user_info_jointime);
		user_info_age = (TextView) findViewById(R.id.user_info_age);
		user_info_mark = (TextView) findViewById(R.id.user_info_mark);
		user_info_address = (TextView) findViewById(R.id.user_info_address);
		user_info_work = (TextView) findViewById(R.id.user_info_work);
		user_info_characters = (TextView) findViewById(R.id.user_info_characters);

		head_refresh = (ImageButton) findViewById(R.id.head_refresh);
		head_refresh.setVisibility(View.VISIBLE);
		head_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				isRefresh = true;
				fetchUserInfo();
			}
		});
		head_back = (Button) findViewById(R.id.head_back);
		head_back.setVisibility(View.VISIBLE);
		head_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UserCenterActivity.this.finish();
			}
		});

		Intent intent = getIntent();
		Bundle bunde = intent.getExtras();
		userId = bunde.getString("userId");
	}

	private void fetchUserInfo() {

		userHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what != -1) {

					// 登录成功
					if (null != msg.obj) {
						User user = (User) msg.obj;
						if (Constants.SEX_M.equals(user.getSex())) {
							user_info_sex
									.setImageResource(R.drawable.widget_gender_man);
						} else if (Constants.SEX_F.equals(user.getSex())) {
							user_info_sex
									.setImageResource(R.drawable.widget_gender_woman);
						}
						user_info_username.setText(user.getUserName());
						user_info_jointime.setText(user.getRegisterTime());
						user_info_age.setText(user.getAge() + "");
						user_info_mark.setText(user.getMark() + "");
						user_info_address
								.setText(!"".equals(user.getAddress()) ? user
										.getAddress() : "<未知>");
						user_info_work
								.setText(!"".equals(user.getWork()) ? user
										.getWork() : "<未知>");
						user_info_characters.setText(!"".equals(user
								.getCharacters()) ? user.getCharacters()
								: "这个人很懒，什么都没留下");
						userinfolayout.setVisibility(View.VISIBLE);
					} else {
						Toast.makeText(UserCenterActivity.this,
								R.string.loginfaill, Toast.LENGTH_SHORT).show();
						userinfolayout.setVisibility(View.GONE);
					}

				} else {
					((AppException) msg.obj).makeToast(UserCenterActivity.this);
					userinfolayout.setVisibility(View.GONE);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {

				Message msg = new Message();
				try {
					User user = appContext.fetchUserInfo(userId, isRefresh);
					msg.what = 0;
					msg.obj = user;
				} catch (AppException e) {
					msg.what = -1;
					msg.obj = e;
				}
				userHandler.sendMessage(msg);
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
