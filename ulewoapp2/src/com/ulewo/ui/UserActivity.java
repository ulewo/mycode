package com.ulewo.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.AppContext;
import com.ulewo.AppException;
import com.ulewo.R;
import com.ulewo.bean.LoginUser;
import com.ulewo.bean.User;
import com.ulewo.cache.AsyncImageLoader;
import com.ulewo.cache.AsyncImageLoader.ImageCallback;
import com.ulewo.common.UIHelper;
import com.ulewo.util.Constants;
import com.ulewo.util.StringUtils;

public class UserActivity extends BaseActivity {
	EditText usernameEdit = null;

	EditText pwdEdit = null;

	ImageButton closBtn = null;
	LinearLayout loginlayout = null;
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
	LinearLayout myprogressbar = null;
	
	RelativeLayout head_common = null;
	
	// 刷新按钮
	ImageButton head_refresh = null;

	Handler loginHandler = null;

	Handler getInfoHandler = null;

	AppContext appContext = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.user);
		appContext = (AppContext) getApplication();
		initView();
	}

	
	
	@Override
	protected void onResume() {
		super.onRestart();
		if (null != AppContext.getSessionId()) {
			loginlayout.setVisibility(View.GONE);
			head_common.setVisibility(View.VISIBLE);
			initData();
		}else{
			usernameEdit.setText("");
			pwdEdit.setText("");
			loginlayout.setVisibility(View.VISIBLE);
			head_common.setVisibility(View.GONE);
			userinfolayout.setVisibility(View.GONE);
		}
	}



	private void initView() {

		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_user);
		loginlayout = (LinearLayout) findViewById(R.id.loginlayout);
		userinfolayout = (LinearLayout) findViewById(R.id.userinfolayout);

		usernameEdit = (EditText) findViewById(R.id.login_account);
		pwdEdit = (EditText) findViewById(R.id.login_password);
		closBtn = (ImageButton) findViewById(R.id.login_close_button);
		closBtn.setVisibility(View.GONE);
		Button button = (Button) findViewById(R.id.login_btn_login);

		user_info_icon = (ImageView) findViewById(R.id.user_info_icon);
		user_info_sex = (ImageView) findViewById(R.id.user_info_sex);
		user_info_username = (TextView) findViewById(R.id.user_info_username);
		user_info_jointime = (TextView) findViewById(R.id.user_info_jointime);
		user_info_age = (TextView) findViewById(R.id.user_info_age);
		user_info_mark = (TextView) findViewById(R.id.user_info_mark);
		user_info_address = (TextView) findViewById(R.id.user_info_address);
		user_info_work = (TextView) findViewById(R.id.user_info_work);
		user_info_characters = (TextView) findViewById(R.id.user_info_characters);

		head_common = (RelativeLayout)findViewById(R.id.head_common);
		head_common.setVisibility(View.GONE);
		if (null != AppContext.getSessionId()) {
			loginlayout.setVisibility(View.GONE);
			head_common.setVisibility(View.VISIBLE);
			initData();
		}

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {

				if (StringUtils.isEmpty(usernameEdit.getText().toString())) {
					Toast.makeText(UserActivity.this, R.string.noname,
							Toast.LENGTH_LONG).show();
					return;
				}
				if (StringUtils.isEmpty(pwdEdit.getText().toString())) {
					Toast.makeText(UserActivity.this, R.string.nopassword,
							Toast.LENGTH_LONG).show();
					return;
				}
				login(usernameEdit.getText().toString(),
						StringUtils.encodeByMD5(pwdEdit.getText().toString()));
			}
		});

		head_refresh = (ImageButton) findViewById(R.id.head_refresh);
		head_refresh.setVisibility(View.VISIBLE);
		head_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {

				login(AppContext.getUserName(), AppContext.getPassword());
			}
		});
		myprogressbar = (LinearLayout)findViewById(R.id.myprogressbar);
		myprogressbar.setOnClickListener(UIHelper.noOnclick(this));
	}

	private void login(final String userName, final String password) {
		myprogressbar.setVisibility(View.VISIBLE);
		loginHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				myprogressbar.setVisibility(View.GONE);
				if (msg.what != -1) {
					LoginUser loginUser = (LoginUser) msg.obj;
					// 登录成功
					if (Constants.SUCCESS.equals(loginUser.getLoginResult())) {
						head_common.setVisibility(View.VISIBLE);
						User user = loginUser.getUser();
						AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
						Drawable cachedImage = asyncImageLoader.loadDrawable(
								user.getUserLittleIcon(), new ImageCallback() {
									public void imageLoaded(
											Drawable imageDrawable,
											String imageUrl) {

										user_info_icon
												.setImageDrawable(imageDrawable);
									}
								});
						if (cachedImage == null) {
							user_info_icon.setImageResource(R.drawable.icon);
						} else {
							user_info_icon.setImageDrawable(StringUtils
									.toRoundCornerDrawable(cachedImage, 5));
						}
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
						loginlayout.setVisibility(View.GONE);
						userinfolayout.setVisibility(View.VISIBLE);
					} else {
						Toast.makeText(UserActivity.this, R.string.loginfaill,
								Toast.LENGTH_SHORT).show();
						loginlayout.setVisibility(View.VISIBLE);
						userinfolayout.setVisibility(View.GONE);
					}

				} else {
					((AppException) msg.obj).makeToast(UserActivity.this);
					loginlayout.setVisibility(View.VISIBLE);
					userinfolayout.setVisibility(View.GONE);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {

				Message msg = new Message();
				try {
					LoginUser loginUser = appContext.login(userName, password,
							true);
					msg.what = 0;
					msg.obj = loginUser;
				} catch (AppException e) {
					msg.what = -1;
					msg.obj = e;
				}
				loginHandler.sendMessage(msg);
			}
		}.start();
	}

	private void initData() {

		getInfoHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				if (msg.what != -1) {
					LoginUser loginUser = (LoginUser) msg.obj;
					// 登录成功
					if (Constants.SUCCESS.equals(loginUser.getLoginResult())) {

						User user = loginUser.getUser();
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
						loginlayout.setVisibility(View.VISIBLE);
						userinfolayout.setVisibility(View.GONE);
					}

				} else {
					((AppException) msg.obj).makeToast(UserActivity.this);
					loginlayout.setVisibility(View.VISIBLE);
					userinfolayout.setVisibility(View.GONE);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {

				Message msg = new Message();
				try {
					LoginUser loginUser = appContext.login(
							appContext.getUserName(), appContext.getPassword(),
							false);
					msg.what = 0;
					msg.obj = loginUser;
				} catch (AppException e) {
					msg.what = -1;
					msg.obj = e;
				}
				getInfoHandler.sendMessage(msg);
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
