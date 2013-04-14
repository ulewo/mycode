package com.ulewo.ui;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.R;
import com.ulewo.bean.Task;
import com.ulewo.bean.User;
import com.ulewo.enums.TaskType;
import com.ulewo.logic.MainService;
import com.ulewo.util.Constants;

public class UserActivity extends BaseActivity implements IMainActivity {
	EditText usernameEdit = null;

	EditText pwdEdit = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.user);
		ExitApplication.getInstance().addActivity(this);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_user);

		usernameEdit = (EditText) findViewById(R.id.login_username);

		pwdEdit = (EditText) findViewById(R.id.login_username);

		Button button = (Button) findViewById(R.id.login_submit);

		Intent service = new Intent(this, MainService.class);
		startService(service);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {

				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("username", usernameEdit.getText());
				param.put("password", pwdEdit.getText());
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

			User user = (User) myobj.get("user");
			System.out.println(user.getUserId());
		} else {
			Toast.makeText(UserActivity.this, R.string.request_timeout,
					Toast.LENGTH_LONG).show();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isExit();
		}
		return super.onKeyDown(keyCode, event);
	}

}
