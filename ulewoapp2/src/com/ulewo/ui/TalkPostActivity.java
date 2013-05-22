package com.ulewo.ui;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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
import com.ulewo.adapter.TalkListAdapter;
import com.ulewo.bean.TalkList;
import com.ulewo.bean.TalkResult;
import com.ulewo.common.FileUtils;
import com.ulewo.common.ImageUtils;
import com.ulewo.common.MediaUtils;
import com.ulewo.common.UIHelper;
import com.ulewo.util.StringUtils;

public class TalkPostActivity extends BaseActivity {

	private TextView main_head_title = null;

	private LinearLayout progressBar = null;

	// 返回按钮
	Button backBtn = null;

	// 发送按钮
	private TextView postSendBtn = null;

	// 输入框
	private EditText textarea = null;

	// 图片
	ImageView talk_pub_image = null;

	// 拍照
	private ImageButton cameraBtn = null;

	// 图片
	private ImageButton photoBtn = null;

	private TalkListAdapter adapter = null;

	private Handler handler = null;

	private AppContext appContext;

	// 软键盘
	private InputMethodManager imm;

	private String theLarge;

	private String theThumbnail;

	private File imgFile;

	private String tempTweetImageKey = "temp_tweet_image";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.talk_post);
		appContext = (AppContext) getApplication();
		initView();
		// initData();
	}

	private void initView() {

		main_head_title = (TextView) findViewById(R.id.main_head_title);
		main_head_title.setText(R.string.posttalk);
		progressBar = (LinearLayout) findViewById(R.id.myprogressbar);
		// progressBar.setVisibility(View.VISIBLE);
		progressBar.setOnClickListener(UIHelper.noOnclick(this));

		backBtn = (Button) findViewById(R.id.head_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(UIHelper.finish(this));

		postSendBtn = (TextView) findViewById(R.id.head_post_btn);
		postSendBtn.setVisibility(View.VISIBLE);
		postSendBtn.setOnClickListener(publishClickListener);
		imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

		textarea = (EditText) findViewById(R.id.talk_post_textarea);

		// 相机
		cameraBtn = (ImageButton) findViewById(R.id.post_camera_btn);
		cameraBtn.setOnClickListener(new SelectImageListener(ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA));
		// 相册
		photoBtn = (ImageButton) findViewById(R.id.post_photo_btn);
		photoBtn.setOnClickListener(new SelectImageListener(ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD));
		talk_pub_image = (ImageView) findViewById(R.id.talk_pub_image);
		talk_pub_image.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {

				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				new AlertDialog.Builder(v.getContext()).setIcon(android.R.drawable.ic_dialog_info)
						.setTitle(getString(R.string.delete_image))
						.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

								//清除之前保存的编辑图片
								//	((AppContext) getApplication()).removeProperty(tempTweetImageKey);

								imgFile = null;
								talk_pub_image.setVisibility(View.GONE);
								dialog.dismiss();
							}
						}).setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

								dialog.dismiss();
							}
						}).create().show();
				return false;
			}
		});
	}

	class SelectImageListener implements OnClickListener {
		private int type;

		SelectImageListener(int type) {

			this.type = type;
		}

		public void onClick(View paramView) {

			// 手机选图
			if (type == 0) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent, "选择图片"), ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
			}
			// 拍照
			else if (type == 1) {
				String savePath = "";
				// 判断是否挂载了SD卡
				String storageState = Environment.getExternalStorageState();
				if (storageState.equals(Environment.MEDIA_MOUNTED)) {
					savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ulewo/";// 存放照片的文件夹
					File savedir = new File(savePath);
					if (!savedir.exists()) {
						savedir.mkdirs();
					}
				}

				// 没有挂载SD卡，无法保存文件
				if (StringUtils.isEmpty(savePath)) {
					UIHelper.ToastMessage(TalkPostActivity.this, "无法保存照片，请检查SD卡是否挂载");
					return;
				}

				String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				String fileName = "ulewo_" + timeStamp + ".jpg";// 照片命名
				File out = new File(savePath, fileName);
				Uri uri = Uri.fromFile(out);

				theLarge = savePath + fileName;// 该照片的绝对路径

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
			}
		}
	}

	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

		if (resultCode != RESULT_OK)
			return;

		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {

				if (msg.what == 1 && msg.obj != null) {
					// 显示图片
					talk_pub_image.setImageBitmap((Bitmap) msg.obj);
					talk_pub_image.setVisibility(View.VISIBLE);
				}
			}
		};

		new Thread() {
			public void run() {

				Bitmap bitmap = null;

				if (requestCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD) {
					if (data == null)
						return;

					Uri thisUri = data.getData();
					String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(thisUri);

					// 如果是标准Uri
					if (StringUtils.isEmpty(thePath)) {
						theLarge = ImageUtils.getAbsoluteImagePath(TalkPostActivity.this, thisUri);
					}
					else {
						theLarge = thePath;
					}

					String attFormat = FileUtils.getFileFormat(theLarge);

					if (!"photo".equals(MediaUtils.getContentType(attFormat))) {
						Toast.makeText(TalkPostActivity.this, getString(R.string.choose_image), Toast.LENGTH_SHORT)
								.show();
						return;
					}

					// 获取图片缩略图 只有Android2.1以上版本支持
					/*			if (AppContext
										.isMethodsCompat(android.os.Build.VERSION_CODES.ECLAIR_MR1)) {
									String imgName = FileUtils.getFileName(theLarge);
									bitmap = ImageUtils.loadImgThumbnail(TweetPub.this,
											imgName,
											MediaStore.Images.Thumbnails.MICRO_KIND);
								}*/

					if (bitmap == null && !StringUtils.isEmpty(theLarge)) {
						bitmap = ImageUtils.loadImgThumbnail(theLarge, 100, 100);
					}
				}
				// 拍摄图片
				else if (requestCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA) {
					if (bitmap == null && !StringUtils.isEmpty(theLarge)) {
						bitmap = ImageUtils.loadImgThumbnail(theLarge, 100, 100);
					}
				}

				if (bitmap != null) {
					// 存放照片的文件夹
					String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/OSChina/Camera/";
					File savedir = new File(savePath);
					if (!savedir.exists()) {
						savedir.mkdirs();
					}

					String largeFileName = FileUtils.getFileName(theLarge);
					String largeFilePath = savePath + largeFileName;
					// 判断是否已存在缩略图
					if (largeFileName.startsWith("thumb_") && new File(largeFilePath).exists()) {
						theThumbnail = largeFilePath;
						imgFile = new File(theThumbnail);
					}
					else {
						// 生成上传的800宽度图片
						String thumbFileName = "thumb_" + largeFileName;
						theThumbnail = savePath + thumbFileName;
						if (new File(theThumbnail).exists()) {
							imgFile = new File(theThumbnail);
						}
						else {
							try {
								// 压缩上传的图片
								ImageUtils.createImageThumbnail(TalkPostActivity.this, theLarge, theThumbnail, 800, 80);
								imgFile = new File(theThumbnail);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					// 保存动弹临时图片
					//((AppContext) getApplication()).setProperty(tempTweetImageKey, theThumbnail);

					Message msg = new Message();
					msg.what = 1;
					msg.obj = bitmap;
					handler.sendMessage(msg);
				}
			};
		}.start();
	}

	private void initData() {

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				progressBar.setVisibility(View.GONE);
				if (msg.what != -1 && null != msg.obj) {
				}
				else {
					((AppException) msg.obj).makeToast(TalkPostActivity.this);
					progressBar.setVisibility(View.GONE);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {

				Message msg = new Message();
				try {
					TalkList list = appContext.getTalkList(1, false);
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

	//提交吐槽	
	private View.OnClickListener publishClickListener = new View.OnClickListener() {
		public void onClick(View v) {

			//隐藏软键盘
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

			final String content = textarea.getText().toString();
			if (StringUtils.isEmpty(content)) {
				UIHelper.ToastMessage(v.getContext(), "请输入动弹内容");
				return;
			}
			final Handler handler = new Handler() {
				public void handleMessage(Message msg) {

					if (msg.what == 1) {
						//清除之前保存的编辑内容
						//ac.removeProperty(tempTweetKey, tempTweetImageKey);
						finish();
					}
					else {
						//mMessage.setVisibility(View.GONE);
						//mForm.setVisibility(View.VISIBLE);
					}
				}
			};

			new Thread() {
				public void run() {

					Message msg = new Message();
					TalkResult res = null;
					int what = 0;
					try {
						res = appContext.addTalk(content, imgFile);
						what = 1;
						msg.what = 1;
						msg.obj = res;
					} catch (AppException e) {
						e.printStackTrace();
						msg.what = -1;
						msg.obj = e;
					}
					handler.sendMessage(msg);
				}
			}.start();
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isExit();
		}
		return super.onKeyDown(keyCode, event);
	}
}
