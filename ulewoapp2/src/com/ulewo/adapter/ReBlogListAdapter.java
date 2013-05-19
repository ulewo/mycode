package com.ulewo.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ulewo.R;
import com.ulewo.bean.ReBlog;
import com.ulewo.cache.AsyncImageLoader;
import com.ulewo.cache.AsyncImageLoader.ImageCallback;
import com.ulewo.common.UIHelper;
import com.ulewo.util.StringUtils;

public class ReBlogListAdapter extends BaseAdapter {

	private List<ReBlog> list = null;

	private LayoutInflater mInflater;
	private AsyncImageLoader asyncImageLoader = null;
	private ListView listView;
	/*
	 * private LayoutInflater subflater;
	 * 
	 * private LayoutInflater menueflater;
	 */

	private Context context;
	private static int maxImgWidth = 200;

	public ReBlogListAdapter(Context context, List<ReBlog> list,
			AsyncImageLoader asyncImageLoader, ListView listView) {

		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.asyncImageLoader = asyncImageLoader;
		this.list = list;
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		maxImgWidth = wm.getDefaultDisplay().getWidth();
		// subflater = LayoutInflater.from(context);
		// menueflater = LayoutInflater.from(context);
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

		final ReBlog reBlog = list.get(postion);

		ImageView imageView = (ImageView) view
				.findViewById(R.id.recomment_icon);
		String imageUrl = reBlog.getAuthorIcon();
		imageView.setTag(imageUrl);
		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {

						ImageView imageViewByTag = (ImageView) listView
								.findViewWithTag(imageUrl);
						if (null != imageDrawable) {
							imageViewByTag.setImageDrawable(imageDrawable);
						} else {
							imageViewByTag.setImageResource(R.drawable.icon);
						}
					}
				}, true);
		if (cachedImage == null) {
			imageView.setImageResource(R.drawable.icon);
		} else {
			imageView.setImageDrawable(StringUtils.toRoundCornerDrawable(
					cachedImage, 5));
		}

		TextView recomment_username = (TextView) view
				.findViewById(R.id.recomment_username);
		recomment_username.setText(reBlog.getAuthorName());
		recomment_username.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIHelper.showUserCenter(v.getContext(), reBlog.getAuthorid(),
						reBlog.getAuthorName());
			}
		});
		// 如果at人不为空那么就显示at的人
		if (StringUtils.isNotEmpty(reBlog.getAtUserId())) {
			TextView recomment_atrename = (TextView) view
					.findViewById(R.id.recomment_atrename);
			recomment_atrename.setVisibility(View.VISIBLE);

			TextView recomment_atusername = (TextView) view
					.findViewById(R.id.recomment_atusername);
			recomment_atusername.setVisibility(View.VISIBLE);
			recomment_atusername.setText(reBlog.getAtUserName());
			recomment_atusername.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					UIHelper.showUserCenter(v.getContext(),
							reBlog.getAtUserId(), reBlog.getAtUserId());
				}
			});
		}

		TextView recomment_posttime = (TextView) view
				.findViewById(R.id.recomment_posttime);
		recomment_posttime.setText(reBlog.getReTime());

		TextView recomment_con = (TextView) view
				.findViewById(R.id.recomment_con);
		recomment_con.setText(Html.fromHtml(reBlog.getContent(), imgGetter,
				null));

		Button item_rebtn = (Button) view.findViewById(R.id.item_rebtn);
		item_rebtn.setVisibility(View.GONE);
		/*
		 * // 回复btn Button item_rebtn = (Button)
		 * view.findViewById(R.id.item_rebtn); item_rebtn.setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { if (null ==
		 * AppContext.getSessionId()) { UIHelper.showLoginDialog(context);
		 * return; } } });
		 */

		/*
		 * LinearLayout liner = (LinearLayout) view
		 * .findViewById(R.id.recomment_sub_layout);
		 * liner.removeAllViewsInLayout(); List<ReBlog> childList =
		 * reBlog.getChildList(); if (childList.size() > 0) {
		 * liner.setVisibility(View.VISIBLE); } else {
		 * liner.setVisibility(View.GONE); } View subview = null; for (final
		 * ReBlog subRe : childList) { subview =
		 * this.subflater.inflate(R.layout.rearticle_sub_item, null);
		 * liner.addView(subview); TextView nameView = (TextView) subview
		 * .findViewById(R.id.recoment_sub_name); TextPaint paint1 =
		 * nameView.getPaint(); paint1.setFakeBoldText(true);
		 * nameView.setText(subRe.getAuthorName());
		 * 
		 * TextView atNameView = (TextView) subview
		 * .findViewById(R.id.recoment_sub_atname); TextPaint paint2 =
		 * atNameView.getPaint(); paint2.setFakeBoldText(true);
		 * 
		 * atNameView.setText(subRe.getAtUserName());
		 * 
		 * TextView conView = (TextView) subview
		 * .findViewById(R.id.recoment_sub_con);
		 * conView.setText(Html.fromHtml(subRe.getContent(), null, new
		 * MxgsaTagHandler(context)));
		 */
		/*
		 * subview.setOnTouchListener(new OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View paramView, MotionEvent
		 * paramMotionEvent) {
		 * 
		 * if (paramMotionEvent.getAction() == MotionEvent.ACTION_DOWN) {
		 * subview.setBackgroundResource(R.color.recomentsubpressed); } else {
		 * subview.setBackgroundResource(R.color.recomentsubnormal); } return
		 * true; } });
		 */

	}

	ImageGetter imgGetter = new Html.ImageGetter() {
		@Override
		public Drawable getDrawable(String source) {
			Drawable drawable = asyncImageLoader
					.loadImageFromUrlNoSynchronization(source);
			if (null != drawable) {
				resizeDrawable(drawable);
			}
			resizeDrawable(drawable);
			return drawable;
		}
	};

	private void resizeDrawable(Drawable drawable) {
		if (null != drawable) {
			int width = drawable.getIntrinsicWidth();
			int height = drawable.getIntrinsicHeight();
			if (width > maxImgWidth) {
				height = maxImgWidth * height / width;
				width = maxImgWidth;
			}
			drawable.setBounds(0, 0, width, height);
		}
	}

	public void loadMore(List<ReBlog> rearticleList) {

		list.addAll(rearticleList);
		this.notifyDataSetChanged();
		// this.notifyDataSetInvalidated();
	}

	public void addItem(ReBlog reBlog) {
		list.add(reBlog);
		this.notifyDataSetChanged();
	}
}
