package com.rvidda.cn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.rvidda.cn.R;
import com.rvidda.cn.domain.LSSYList;
import com.rvidda.cn.view.FlowLayout;

public class LvShiShouYeAdapter1 extends BaseAdapter {

	private DisplayImageOptions options;
	private Context context;
	private List<LSSYList> data = new ArrayList<LSSYList>();

	public LvShiShouYeAdapter1(Context context, List<LSSYList> data) {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.content_bg)
				.showImageForEmptyUri(R.drawable.content_bg)
				.showImageOnLoading(R.drawable.content_bg)
				.showImageOnFail(R.drawable.content_bg).cacheInMemory(true)
				.cacheOnDisc(true).build();
		this.context = context;
		this.data = data;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	class ViewHolder {
		com.rvidda.cn.view.FlowLayout mFFF;
		com.rvidda.cn.view.CircleImageView mPn1;
		View mV1, mV2, mV3, mV4, mV5;
		TextView mtv1, mtv2, mtv3;
		RelativeLayout mRlshow1, mRlshow2, mRlshow3, mRlshow4, mRlshow5;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.item_lvshishouye_layout, null);

		/*
		 * ViewHolder holder = null; if(convertView==null){ convertView =
		 * LayoutInflater.from(context)
		 * .inflate(R.layout.item_lvshishouye_layout, null); holder = new
		 * ViewHolder(); holder.mPn1 =(CircleImageView)
		 * convertView.findViewById(R.id.mPn1); holder.mtv1 =(TextView)
		 * convertView.findViewById(R.id.mtv1); holder.mtv2 =(TextView)
		 * convertView.findViewById(R.id.mtv2); holder.mtv3 =(TextView)
		 * convertView.findViewById(R.id.mtv3); convertView.setTag(holder);
		 * 
		 * }else{ holder =(ViewHolder)convertView.getTag(); }
		 */
		FlowLayout mFFF = (com.rvidda.cn.view.FlowLayout) convertView
				.findViewById(R.id.mFFF);
		for (int i = 0; i < data.get(position).getBiaoqian().size(); i++) {
			LinearLayout.LayoutParams layoutParamsButtonCancel = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParamsButtonCancel.gravity = Gravity.CENTER;
			layoutParamsButtonCancel.leftMargin = dip2px(context, 1);
			layoutParamsButtonCancel.rightMargin = dip2px(context, 1);
			layoutParamsButtonCancel.topMargin = dip2px(context, 1);
			layoutParamsButtonCancel.bottomMargin = dip2px(context, 1);
			TextView tv1 = new TextView(context);
			tv1.setLayoutParams(layoutParamsButtonCancel);
			Drawable drawable1 = context.getResources().getDrawable(
					R.drawable.flag_01);
			tv1.setBackgroundDrawable(drawable1);
			tv1.setGravity(Gravity.CENTER);
			tv1.setText(data.get(position).getBiaoqian().get(i));
			mFFF.addView(tv1);
		}

		return convertView;

	}

}
