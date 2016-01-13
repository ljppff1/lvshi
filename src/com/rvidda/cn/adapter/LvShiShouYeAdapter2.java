package com.rvidda.cn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.jauker.widget.BadgeView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rvidda.cn.R;
import com.rvidda.cn.domain.LSSYList;
import com.rvidda.cn.view.CircleImageView;

public class LvShiShouYeAdapter2 extends BaseAdapter {

	private DisplayImageOptions options;
	private Context context;
	private List<LSSYList> data = new ArrayList<LSSYList>();
	private View mVs1;
	private TextView mtv3;
	private RelativeLayout mRlsss1;
	private TextView mtv2;
	private TextView mtv1;

	public LvShiShouYeAdapter2(Context context, List<LSSYList> data) {
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
				R.layout.item_lvshishouye_layout1, null);
		mtv1 =(TextView)convertView.findViewById(R.id.mtv1);
		mtv2 =(TextView)convertView.findViewById(R.id.mtv2);
		mtv1.setText(data.get(position).getName());
		mtv2.setText(data.get(position).getTime());
		com.rvidda.cn.view.CircleImageView mPn1 =(CircleImageView) convertView.findViewById(R.id.mPn1);
		ImageLoader.getInstance().displayImage(data.get(position).getPhoto(), mPn1, options);		
		mVs1 =(View)convertView.findViewById(R.id.mVs1);
		
		if(!data.get(position).getNumber().equals("0")){
		BadgeView badgeView1 = new com.jauker.widget.BadgeView(context);
		badgeView1.setTargetView(mVs1);
		badgeView1.setBackground(1111, Color.RED);
		badgeView1.setGravity(Gravity.TOP | Gravity.RIGHT);
		badgeView1.setClickable(false);
		badgeView1.setTextSize(10);
		badgeView1.setBadgeCount(Integer.valueOf(data.get(position).getNumber()));
		}
		mtv3 =(TextView)convertView.findViewById(R.id.mtv3);
		mtv3.setText(data.get(position).getSp(), BufferType.SPANNABLE);

		
		return convertView;

	}

}
