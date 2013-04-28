package com.ulewo.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ulewo.AppContext;
import com.ulewo.R;
import com.ulewo.bean.ReArticle;
import com.ulewo.cache.AsyncImageLoader;
import com.ulewo.cache.AsyncImageLoader.ImageCallback;
import com.ulewo.common.UIHelper;
import com.ulewo.util.StringUtils;

public class ReArticleListAdapter extends BaseAdapter {

	private List<ReArticle> list = null;

	private LayoutInflater mInflater;

	private LayoutInflater subflater;

	private LayoutInflater menueflater;

	private Context context;
	private AsyncImageLoader asyncImageLoader = null;
	private ListView listView;

	private RelativeLayout reSubPanel = null;
	TextView reusers = null;
	TextView content = null;
	Button subreformbtn = null;
	EditText hide_atuserId = null;
	EditText hide_postion = null;
	EditText hide_pid = null;

	public ReArticleListAdapter(Context context, List<ReArticle> list,
			View subView, AsyncImageLoader asyncImageLoader, ListView listView) {
		this.asyncImageLoader = asyncImageLoader;
		this.list = list;
		this.listView = listView;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		subflater = LayoutInflater.from(context);
		menueflater = LayoutInflater.from(context);
		reSubPanel = (RelativeLayout) subView;
		reusers = (TextView) reSubPanel.findViewById(R.id.reuser);
		content = (TextView) reSubPanel.findViewById(R.id.textarea);
		subreformbtn = (Button) reSubPanel.findViewById(R.id.subreformbtn);
		hide_atuserId = (EditText) reSubPanel.findViewById(R.id.hide_atuserId);
		hide_pid = (EditText) reSubPanel.findViewById(R.id.hide_pid);
		hide_postion = (EditText) reSubPanel.findViewById(R.id.hide_postion);
	}

	@Override
	public int getCount() {

		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {

		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {

		if (position < getCount()) {
			return list.get(position).getId();
		} else {
			return 0;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return createViewFromResource(position, convertView);
	}

	private View createViewFromResource(int position, View convertView) {

		View view;
		if (convertView == null) {
			view = this.mInflater.inflate(R.layout.rearticle_item, null);
		} else {
			view = convertView;
		}
		bindView(position, view);
		return view;
	}

	private void bindView(final int postion, View view) {

		final ReArticle reArticle = list.get(postion);

		ImageView imageView = (ImageView) view
				.findViewById(R.id.recomment_icon);
		String imageUrl = reArticle.getAuthorIcon();
		imageView.setTag(imageUrl);
		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {

						ImageView imageViewByTag = (ImageView) listView
								.findViewWithTag(imageUrl);
						if (imageViewByTag != null) {
							imageViewByTag.setImageDrawable(imageDrawable);
						}
					}
				});
		if (cachedImage == null) {
			imageView.setImageResource(R.drawable.icon);
		} else {
			imageView.setImageDrawable(StringUtils.toRoundCornerDrawable(
					cachedImage, 5));
		}

		TextView recomment_username = (TextView) view
				.findViewById(R.id.recomment_username);
		TextView recomment_posttime = (TextView) view
				.findViewById(R.id.recomment_posttime);
		WebView recomment_con = (WebView) view.findViewById(R.id.recomment_con);
		recomment_con.getSettings().setJavaScriptEnabled(false);
		recomment_con.getSettings().setSupportZoom(true);
		recomment_con.getSettings().setBuiltInZoomControls(true);
		recomment_con.getSettings().setDefaultFontSize(13);

		recomment_username.setText(reArticle.getAuthorName());

		recomment_username.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showUserCenter(v.getContext(),
						reArticle.getAuthorid(), reArticle.getAuthorName());
			}
		});

		recomment_posttime.setText(reArticle.getReTime());
		String body = UIHelper.WEB_STYLE + reArticle.getContent();
		recomment_con.loadDataWithBaseURL(null, body, "text/html", "utf-8",
				null);
		recomment_con.setWebViewClient(UIHelper.getWebViewClient());

		// 回复btn
		Button item_rebtn = (Button) view.findViewById(R.id.item_rebtn);
		item_rebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null == AppContext.getSessionId()) {
					UIHelper.showLoginDialog(context);
					return;
				}
				content.setText("");
				reSubPanel.setVisibility(View.VISIBLE);
				reusers.setText(reArticle.getAuthorName());
				hide_postion.setText(postion + "");
				hide_pid.setText(reArticle.getId() + "");
				hide_atuserId.setText(reArticle.getAuthorid());
			}
		});

		LinearLayout liner = (LinearLayout) view
				.findViewById(R.id.recomment_sub_layout);
		liner.removeAllViewsInLayout();
		List<ReArticle> childList = reArticle.getChildList();
		if (childList.size() > 0) {
			liner.setVisibility(View.VISIBLE);
		} else {
			liner.setVisibility(View.GONE);
		}
		View subview = null;
		for (final ReArticle subRe : childList) {
			subview = this.subflater.inflate(R.layout.rearticle_sub_item, null);

			subview.setOnLongClickListener(new OnLongClickListener() {
				PopupWindow mPop = null;

				@Override
				public boolean onLongClick(View v) {
					if (null == AppContext.getSessionId()) {
						UIHelper.showLoginDialog(context);
						return false;
					}
					content.setText("");
					reSubPanel.setVisibility(View.VISIBLE);
					reusers.setText(subRe.getAuthorName());
					hide_postion.setText(postion + "");
					hide_pid.setText(subRe.getPid() + "");
					hide_atuserId.setText(subRe.getAuthorid());
					return true;
				}

				private void initPopWindow() {
					if (mPop == null) {
						mPop = new PopupWindow(menueflater.inflate(
								R.layout.pop, null), LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);
					}
					if (mPop.isShowing()) {
						mPop.dismiss();
					}
				}
			});

			liner.addView(subview);
			TextView nameView = (TextView) subview
					.findViewById(R.id.recoment_sub_name);
			nameView.setText(subRe.getAuthorName());

			nameView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					UIHelper.showUserCenter(v.getContext(),
							subRe.getAuthorid(), subRe.getAuthorName());
				}
			});

			TextView atNameView = (TextView) subview
					.findViewById(R.id.recoment_sub_atname);
			atNameView.setText(subRe.getAtUserName());

			atNameView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					UIHelper.showUserCenter(v.getContext(),
							subRe.getAtUserId(), subRe.getAtUserName());
				}
			});

			TextView conView = (TextView) subview
					.findViewById(R.id.recoment_sub_con);
			conView.setText(subRe.getContent());
			/*
			 * subview.setOnTouchListener(new OnTouchListener() {
			 * 
			 * @Override public boolean onTouch(View paramView, MotionEvent
			 * paramMotionEvent) {
			 * 
			 * if (paramMotionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			 * subview.setBackgroundResource(R.color.recomentsubpressed); } else
			 * { subview.setBackgroundResource(R.color.recomentsubnormal); }
			 * return true; } });
			 */
		}
	}

	public void loadMore(List<ReArticle> rearticleList) {

		list.addAll(rearticleList);
		this.notifyDataSetChanged();
		// this.notifyDataSetInvalidated();
	}

	public void addItem(ReArticle reArticle) {
		list.add(reArticle);
		this.notifyDataSetChanged();
	}

}
