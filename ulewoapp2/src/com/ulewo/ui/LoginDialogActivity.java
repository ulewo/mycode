package com.ulewo.ui;

import android.os.Bundle;
import android.widget.ImageButton;

import com.ulewo.R;
import com.ulewo.common.UIHelper;

public class LoginDialogActivity extends BaseActivity {
	private ImageButton btn_close;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.login_dialog);
		btn_close = (ImageButton)findViewById(R.id.login_close_button);
        btn_close.setOnClickListener(UIHelper.finish(this));   
	}
}
