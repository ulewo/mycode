package com.ulewo.ui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ulewo.AppContext;
import com.ulewo.AppException;
import com.ulewo.R;
import com.ulewo.bean.UlewoVersion;
import com.ulewo.service.UpdateService;
import com.ulewo.util.Constants;
import com.ulewo.util.StringUtils;

public class MoreActivity extends BaseActivity {

	private RelativeLayout more_logout = null;
	private RelativeLayout more_check_version = null;
	private RelativeLayout more_about_item = null;
	private RelativeLayout more_exit = null;
	private LinearLayout progressBar = null;

	private Handler checkVersionHandler = null;
	AppContext appContext = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.more);
		appContext = (AppContext) getApplication();
		TextView textView = (TextView) findViewById(R.id.main_head_title);
		textView.setText(R.string.name_more);
		initView();
	}

	private void initView() {
		more_logout = (RelativeLayout) findViewById(R.id.more_logout);
		more_check_version = (RelativeLayout) findViewById(R.id.more_check_version);
		more_about_item = (RelativeLayout) findViewById(R.id.more_about_item);
		more_exit = (RelativeLayout) findViewById(R.id.more_exit);
		progressBar = (LinearLayout) findViewById(R.id.myprogressbar);
		more_check_version.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				checkVersion();
			}
		});

		more_logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				// 创建退出对话框
				AlertDialog isExit = new AlertDialog.Builder(MoreActivity.this)
						.create();
				// 设置对话框标题
				isExit.setTitle("系统提示");
				// 设置对话框消息
				isExit.setMessage("确定要注销吗");
				// 添加选择按钮并注册监听
				isExit.setButton("确定", listener);
				isExit.setButton2("取消", listener);
				// 显示对话框
				isExit.show();

			}

			DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					switch (which) {
					case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
						// 清除session
						appContext.removeUserInfo(Constants.SESSIONID);
						// 删除缓存文件
						String fileName = StringUtils.encodeByMD5("user");
						appContext.deleteObject(fileName);
						// appContext.re
						break;
					case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
						break;
					default:
						break;
					}
				}
			};
		});
		more_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isExit();
			}
		});

		more_about_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				Intent intent = new Intent();
				intent.setClass(MoreActivity.this, AboutActivity.class);
				startActivity(intent);
			}
		});
	}

	// 检测新版本
	private void checkVersion() {
		progressBar.setVisibility(View.VISIBLE);
		checkVersionHandler = new Handler() {
			UlewoVersion version = null;

			@Override
			public void handleMessage(Message msg) {
				progressBar.setVisibility(View.GONE);
				PackageInfo info = appContext.getPackageInfo();
				double curVersion = Double.valueOf(info.versionName);
				if (msg.what != -1) {
					version = (UlewoVersion) msg.obj;
					if (version.getVersion() > curVersion) {
						// 创建对话框
						AlertDialog isUpload = new AlertDialog.Builder(
								MoreActivity.this).create();
						// 设置对话框标题
						isUpload.setTitle("软件更新");
						// 设置对话框消息
						isUpload.setMessage("当前版本" + curVersion + "发现新版本"
								+ version.getVersion() + ",是否更新？");
						// 添加选择按钮并注册监听
						isUpload.setButton("确定", mylistener);
						isUpload.setButton2("取消", mylistener);
						// 显示对话框
						isUpload.show();
					} else {
						AlertDialog isExit = new AlertDialog.Builder(
								MoreActivity.this).create();
						// 设置对话框标题
						isExit.setTitle("软件更新");
						// 设置对话框消息
						isExit.setMessage("已经是最新版本了");
						// 添加选择按钮并注册监听
						isExit.setButton2("确定", mylistener);
						// 显示对话框
						isExit.show();
					}
				} else {
					((AppException) msg.obj).makeToast(MoreActivity.this);
				}

			}

			DialogInterface.OnClickListener mylistener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					switch (which) {
					case AlertDialog.BUTTON_POSITIVE:// "确认更新程序"
						Intent updateIntent = new Intent(MoreActivity.this,
								UpdateService.class);
						String appName = "ulewo";
						if (version != null) {
							appName = version.getAppName();
						}
						updateIntent.putExtra("app_name", appName);
						startService(updateIntent);
						break;
					case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
						break;
					default:
						break;
					}
				}
			};

		};
		new Thread() {

			@Override
			public void run() {

				Message msg = new Message();
				try {
					UlewoVersion version = appContext.fetchVersion();
					msg.what = 0;
					msg.obj = version;
				} catch (AppException e) {
					msg.what = -1;
					msg.obj = e;
				}
				checkVersionHandler.sendMessage(msg);
			}
		}.start();
	}

	private void download() {
		new Thread() {
			@Override
			public void run() {
				ProgressDialog pd = null;
				// 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
				try {
					if (Environment.getExternalStorageState().equals(
							Environment.MEDIA_MOUNTED)) {
						URL url = new URL("");
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setConnectTimeout(5000);
						// 获取到文件的大小
						pd.setMax(conn.getContentLength());
						InputStream is = conn.getInputStream();
						File file = new File(
								Environment.getExternalStorageDirectory(),
								"updata.apk");
						FileOutputStream fos = new FileOutputStream(file);
						BufferedInputStream bis = new BufferedInputStream(is);
						byte[] buffer = new byte[1024];
						int len;
						int total = 0;
						while ((len = bis.read(buffer)) != -1) {
							fos.write(buffer, 0, len);
							total += len;
							// 获取当前下载量
							pd.setProgress(total);
						}
						fos.close();
						bis.close();
						is.close();
					}
				} catch (Exception e) {
				}

			}
		}.start();
	}

	// 安装程序
	protected void installApk(File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.Android.package-archive");// 编者按：此处Android应为android，否则造成安装不了
		startActivity(intent);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isExit();
		}
		return super.onKeyDown(keyCode, event);
	}
}
