package com.ulewo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ulewo.AppContext;
import com.ulewo.AppException;
import com.ulewo.R;
import com.ulewo.bean.LoginUser;
import com.ulewo.common.UIHelper;
import com.ulewo.util.Constants;
import com.ulewo.util.StringUtils;

public class LoginDialogActivity extends BaseActivity {
	private ImageButton closBtn;
	EditText usernameEdit = null;
	EditText pwdEdit = null;
	Button button =null;
	
	LinearLayout myprogressbar = null;
	
	Handler loginHandler = null;
	AppContext appContext = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.login_dialog);
		appContext = (AppContext)getApplication();
		initView();
	}
	private void initView(){
		usernameEdit = (EditText) findViewById(R.id.login_account);
		pwdEdit = (EditText) findViewById(R.id.login_password);
		closBtn = (ImageButton)findViewById(R.id.login_close_button);
		closBtn.setOnClickListener(UIHelper.finish(this));   
		button = (Button) findViewById(R.id.login_btn_login);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {

				if (StringUtils.isEmpty(usernameEdit.getText().toString())) {
					Toast.makeText(LoginDialogActivity.this, R.string.noname,
							Toast.LENGTH_LONG).show();
					return;
				}
				if (StringUtils.isEmpty(pwdEdit.getText().toString())) {
					Toast.makeText(LoginDialogActivity.this, R.string.nopassword,
							Toast.LENGTH_LONG).show();
					return;
				}
				login(usernameEdit.getText().toString(),
						StringUtils.encodeByMD5(pwdEdit.getText().toString()));
			}
		});
		myprogressbar = (LinearLayout)findViewById(R.id.myprogressbar);
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
						UIHelper.finish(LoginDialogActivity.this);
					} else {
						Toast.makeText(LoginDialogActivity.this, R.string.loginfaill,
								Toast.LENGTH_SHORT).show();
					}
				} else {
					((AppException) msg.obj).makeToast(LoginDialogActivity.this);
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
}
