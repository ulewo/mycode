package com.ulewo.ui;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.AppContext;
import com.ulewo.R;
import com.ulewo.bean.Task;
import com.ulewo.bean.User;
import com.ulewo.enums.TaskType;
import com.ulewo.logic.MainService;
import com.ulewo.util.Constants;
import com.ulewo.util.Tools;

public class UserActivity extends BaseActivity implements IMainActivity {
	EditText usernameEdit = null;

	EditText pwdEdit = null;

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

	// 刷新按钮
	ImageButton head_refresh = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.user);
		ExitApplication.getInstance().addActivity(this);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_user);
		loginlayout = (LinearLayout) findViewById(R.id.loginlayout);
		userinfolayout = (LinearLayout) findViewById(R.id.userinfolayout);

		usernameEdit = (EditText) findViewById(R.id.login_username);
		pwdEdit = (EditText) findViewById(R.id.login_password);

		Button button = (Button) findViewById(R.id.login_submit);

		Intent service = new Intent(this, MainService.class);
		startService(service);

		if (null != AppContext.getSessionId()) {
			loginlayout.setVisibility(View.GONE);
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("username", AppContext.getUserId());
			param.put("password", AppContext.getPassword());
			Task task = new Task(TaskType.USERINFO, param, UserActivity.this);
			MainService.newTask(task);
		}

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {

				HashMap<String, Object> param = new HashMap<String, Object>();
				if (Tools.isEmpty(usernameEdit.getText().toString())) {
					Toast.makeText(UserActivity.this, R.string.noname,
							Toast.LENGTH_LONG).show();
					return;
				}
				if (Tools.isEmpty(pwdEdit.getText().toString())) {
					Toast.makeText(UserActivity.this, R.string.nopassword,
							Toast.LENGTH_LONG).show();
					return;
				}
				param.put("username", usernameEdit.getText());
				param.put("password",
						Tools.encodeByMD5(pwdEdit.getText().toString()));
				Task task = new Task(TaskType.USERINFO, param,
						UserActivity.this);
				MainService.newTask(task);
			}
		});

		head_refresh = (ImageButton) findViewById(R.id.head_refresh);
		head_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("username", AppContext.getUserId());
				param.put("password", AppContext.getPassword());
				Task task = new Task(TaskType.USERINFO, param,
						UserActivity.this);
				MainService.newTask(task);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... obj) {
		HashMap<String, Object> myobj = (HashMap<String, Object>) obj[0];
		if (null != myobj.get("user")
				&& Constants.RESULTCODE_SUCCESS.equals(String.valueOf(myobj
						.get("result")))) {
			head_refresh.setVisibility(View.VISIBLE);
			loginlayout.setVisibility(View.GONE);
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

			User user = (User) myobj.get("user");
			if (Constants.SEX_M.equals(user.getSex())) {
				user_info_sex.setImageResource(R.drawable.widget_gender_man);
			} else if (Constants.SEX_F.equals(user.getSex())) {
				user_info_sex.setImageResource(R.drawable.widget_gender_woman);
			}
			user_info_username.setText(user.getUserName());
			user_info_jointime.setText(user.getRegisterTime());
			user_info_age.setText(user.getAge() + "");
			user_info_mark.setText(user.getMark() + "");
			user_info_address.setText(!"".equals(user.getAddress()) ? user
					.getAddress() : "<未知>");
			user_info_work.setText(!"".equals(user.getWork()) ? user.getWork()
					: "<未知>");
			user_info_characters
					.setText(!"".equals(user.getCharacters()) ? user
							.getCharacters() : "这个人很懒，什么都没留下");
		} else if (Constants.RESULTCODE_LOGINFAIL.equals(String.valueOf(myobj
				.get("result")))) {
			Toast.makeText(UserActivity.this, R.string.loginfaill,
					Toast.LENGTH_LONG).show();
			loginlayout.setVisibility(View.VISIBLE);
		} else {
			Toast.makeText(UserActivity.this, R.string.loginfaill,
					Toast.LENGTH_LONG).show();
			loginlayout.setVisibility(View.VISIBLE);
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isExit();
		}
		return super.onKeyDown(keyCode, event);
	}
}
