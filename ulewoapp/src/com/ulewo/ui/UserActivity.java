package com.ulewo.ui;

import java.util.HashMap;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ulewo.R;

public class UserActivity extends BaseActivity implements IMainActivity {
	EditText usernameEdit = null;

	EditText pwdEdit = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.user);
		ExitApplication.getInstance().addActivity(this);
		ImageView imageView = (ImageView) findViewById(R.id.main_head_logo);
		imageView.setImageResource(R.drawable.user);
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_user);

		/*
		 * usernameEdit = (EditText) findViewById(R.id.login_username);
		 * 
		 * pwdEdit = (EditText) findViewById(R.id.login_username);
		 * 
		 * Button button = (Button) findViewById(R.id.login_btn);
		 * 
		 * Intent service = new Intent(this, MainService.class);
		 * startService(service);
		 * 
		 * button.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View paramView) {
		 * 
		 * Map<String, Object> param = new HashMap<String, Object>();
		 * param.put("userName", usernameEdit.getText()); param.put("password",
		 * pwdEdit.getText()); // Task task = new Task(TaskType.QUERYARTICLES,
		 * param); // MainService.newTask(task); } });
		 * 
		 * MainService.addActivity(this);
		 */

	}

	@Override
	public void refresh(Object... obj) {

		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) obj[0];
		Toast.makeText(
				this,
				String.valueOf(map.get("userName")) + "  "
						+ String.valueOf(map.get("password")),
				Toast.LENGTH_LONG).show();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isExit();
		}
		return super.onKeyDown(keyCode, event);
	}

}
