package com.rvidda.cn.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.platform.comapi.map.h;
import com.fanxin.app.fx.ChatActivity;
import com.jauker.widget.BadgeView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rvidda.cn.R;
import com.rvidda.cn.SBaseAdapter;
import com.rvidda.cn.domain.ZXList;
import com.rvidda.cn.view.CircleImageView;

public class ZixunLieBiaoAdapter extends SBaseAdapter {

	private DisplayImageOptions options;
	private Context context;

	public ZixunLieBiaoAdapter(Context context, List<?> data) {
		super(context, data, R.layout.item_liebiao_layout);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.content_bg)
				.showImageForEmptyUri(R.drawable.content_bg)
				.showImageOnLoading(R.drawable.content_bg)
				.showImageOnFail(R.drawable.content_bg).cacheInMemory(true)
				.cacheOnDisc(true).build();
		this.context = context;
	}

	@Override
	protected void newView(View convertView) {
		ViewHolder holder = new ViewHolder();
         
		holder.mRlshow1 = (RelativeLayout) convertView
				.findViewById(R.id.mRlshow1);
		holder.mRlshow2 = (RelativeLayout) convertView
				.findViewById(R.id.mRlshow2);
		holder.mRlshow3 = (RelativeLayout) convertView
				.findViewById(R.id.mRlshow3);
		holder.mRlshow4 = (RelativeLayout) convertView
				.findViewById(R.id.mRlshow4);
		holder.mRlshow5 = (RelativeLayout) convertView
				.findViewById(R.id.mRlshow5);
		holder.mLLshow =(LinearLayout)convertView.findViewById(R.id.mLLshow);
		holder.mPn1 = (CircleImageView) convertView.findViewById(R.id.mPn1);
		holder.mPn2 = (CircleImageView) convertView.findViewById(R.id.mPn2);
		holder.mPn3 = (CircleImageView) convertView.findViewById(R.id.mPn3);
		holder.mPn4 = (CircleImageView) convertView.findViewById(R.id.mPn4);
		holder.mPn5 = (CircleImageView) convertView.findViewById(R.id.mPn5);
		holder.mV1 = convertView.findViewById(R.id.mV1);
		holder.mV2 = convertView.findViewById(R.id.mV2);
		holder.mV3 = convertView.findViewById(R.id.mV3);
		holder.mV4 = convertView.findViewById(R.id.mV4);
		holder.mV5 = convertView.findViewById(R.id.mV5);
		holder.mTv1 = (TextView) convertView.findViewById(R.id.mTv1);
		holder.mTv2 = (TextView) convertView.findViewById(R.id.mTv2);
		holder.mTv3 = (TextView) convertView.findViewById(R.id.mTv3);
		holder.mTv4 = (TextView) convertView.findViewById(R.id.mTv4);
		holder.mTv5 = (TextView) convertView.findViewById(R.id.mTv5);
		holder.mtv1 = (TextView) convertView.findViewById(R.id.mtv1);
		holder.mtv2 = (TextView) convertView.findViewById(R.id.mtv2);

		
		convertView.setTag(holder);
	}

	@Override
	protected void holderView(View convertView, Object itemObject, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		ZXList zxlist = (ZXList) itemObject;
       
		// ImageLoader.getInstance().displayImage(news.getThumbnail(),
		// holder.iconView, options);
		
		
		holder.mtv1.setText(zxlist.getTitle());
		holder.mtv2.setText(zxlist.getTime());
		
		//判断个数
        switch (zxlist.getName().size()) {
		case 0:
			holder.mLLshow.setVisibility(View.GONE);
			break;
		case 1:
			holder.mLLshow.setVisibility(View.VISIBLE);
			holder.mRlshow1.setVisibility(View.VISIBLE);

			holder.mRlshow2.setVisibility(View.GONE);
			holder.mRlshow3.setVisibility(View.GONE);
			holder.mRlshow4.setVisibility(View.GONE);
			holder.mRlshow5.setVisibility(View.GONE);
			holder.mTv1.setText(zxlist.getName().get(0));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(0), holder.mPn1, options);		
			setNumber(1, zxlist, holder);
         break;
		case 2:
			setNumber(2, zxlist, holder);
			holder.mLLshow.setVisibility(View.VISIBLE);
			holder.mRlshow1.setVisibility(View.VISIBLE);
			holder.mRlshow2.setVisibility(View.VISIBLE);
			holder.mRlshow3.setVisibility(View.GONE);
			holder.mRlshow4.setVisibility(View.GONE);
			holder.mRlshow5.setVisibility(View.GONE);
			holder.mTv1.setText(zxlist.getName().get(0));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(0), holder.mPn1, options);		
			holder.mTv2.setText(zxlist.getName().get(1));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(1), holder.mPn2, options);		
         break;
		case 3:
			setNumber(3, zxlist, holder);
			holder.mLLshow.setVisibility(View.VISIBLE);
			holder.mRlshow1.setVisibility(View.VISIBLE);
			holder.mRlshow2.setVisibility(View.VISIBLE);
			holder.mRlshow3.setVisibility(View.VISIBLE);
			holder.mRlshow4.setVisibility(View.GONE);
			holder.mRlshow5.setVisibility(View.GONE);
			holder.mTv1.setText(zxlist.getName().get(0));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(0), holder.mPn1, options);		
			holder.mTv2.setText(zxlist.getName().get(1));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(1), holder.mPn2, options);		
			holder.mTv3.setText(zxlist.getName().get(2));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(2), holder.mPn3, options);		
         break;
		case 4:
			setNumber(4, zxlist, holder);
			holder.mLLshow.setVisibility(View.VISIBLE);
			holder.mRlshow1.setVisibility(View.VISIBLE);
			holder.mRlshow2.setVisibility(View.VISIBLE);
			holder.mRlshow3.setVisibility(View.VISIBLE);
			holder.mRlshow4.setVisibility(View.VISIBLE);
			holder.mRlshow5.setVisibility(View.GONE);
			holder.mTv1.setText(zxlist.getName().get(0));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(0), holder.mPn1, options);		
			holder.mTv2.setText(zxlist.getName().get(1));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(1), holder.mPn2, options);		
			holder.mTv3.setText(zxlist.getName().get(2));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(2), holder.mPn3, options);		
			holder.mTv4.setText(zxlist.getName().get(3));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(3), holder.mPn4, options);		
         break;
		case 5:
			setNumber(5, zxlist, holder);
			holder.mLLshow.setVisibility(View.VISIBLE);
			holder.mRlshow1.setVisibility(View.VISIBLE);
			holder.mRlshow2.setVisibility(View.VISIBLE);
			holder.mRlshow3.setVisibility(View.VISIBLE);
			holder.mRlshow4.setVisibility(View.VISIBLE);
			holder.mRlshow5.setVisibility(View.VISIBLE);
			holder.mTv1.setText(zxlist.getName().get(0));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(0), holder.mPn1, options);		
			holder.mTv2.setText(zxlist.getName().get(1));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(1), holder.mPn2, options);		
			holder.mTv3.setText(zxlist.getName().get(2));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(2), holder.mPn3, options);		
			holder.mTv4.setText(zxlist.getName().get(3));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(3), holder.mPn4, options);		
			holder.mTv5.setText(zxlist.getName().get(4));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(4), holder.mPn5, options);		
         break;
		default:
			setNumber(5, zxlist, holder);
			holder.mLLshow.setVisibility(View.VISIBLE);
			holder.mRlshow1.setVisibility(View.VISIBLE);
			holder.mRlshow2.setVisibility(View.VISIBLE);
			holder.mRlshow3.setVisibility(View.VISIBLE);
			holder.mRlshow4.setVisibility(View.VISIBLE);
			holder.mRlshow5.setVisibility(View.VISIBLE);
			holder.mTv1.setText(zxlist.getName().get(0));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(0), holder.mPn1, options);		
			holder.mTv2.setText(zxlist.getName().get(1));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(1), holder.mPn2, options);		
			holder.mTv3.setText(zxlist.getName().get(2));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(2), holder.mPn3, options);		
			holder.mTv4.setText(zxlist.getName().get(3));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(3), holder.mPn4, options);		
			holder.mTv5.setText(zxlist.getName().get(4));
			ImageLoader.getInstance().displayImage(zxlist.getPhoto().get(4), holder.mPn5, options);		

			break;
		}		
		
		
	/*	BadgeView badgeView = new com.jauker.widget.BadgeView(context);
		badgeView.setTargetView(holder.mV1);
		badgeView.setBackground(1111, Color.RED);
		badgeView.setGravity(Gravity.TOP | Gravity.RIGHT);
		badgeView.setTextSize(10);
		badgeView.setBadgeCount(32);
		if (position < 4) {
			BadgeView badgeView1 = new com.jauker.widget.BadgeView(context);
			badgeView1.setTargetView(holder.mV4);
			badgeView1.setBackground(1111, Color.RED);
			badgeView1.setGravity(Gravity.TOP | Gravity.RIGHT);
			badgeView1.setTextSize(10);
			badgeView1.setBadgeCount(8);
		}

		if (position < 4) {
			holder.mRlshow5.setVisibility(View.GONE);
		}
		if (position < 2) {
			holder.mRlshow4.setVisibility(View.GONE);
			holder.mRlshow5.setVisibility(View.GONE);
		}

		holder.mRlshow2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("userId", "ljppff1");
				intent.putExtra("userAvatar", "dd");
				intent.putExtra("userNick", "ljppff");

				intent.setClass(context, ChatActivity.class);
				context.startActivity(intent);

			}
		});
*/
	}

	private void setNumber(Integer aa,ZXList zxlist,ViewHolder holder){
		switch (aa) {
		case 1:
           do1(aa,  zxlist, holder);
           dol1(aa, zxlist, holder);
			break;
		case 2:
	           do1(aa,  zxlist, holder);
	           do2(aa,  zxlist, holder);
	           dol1(aa, zxlist, holder);
	           dol2(aa, zxlist, holder);
			break;
		case 3:
	           do1(aa,  zxlist, holder);
	           do2(aa,  zxlist, holder);
	           do3(aa,  zxlist, holder);
	           dol1(aa, zxlist, holder);
	           dol2(aa, zxlist, holder);
	           dol3(aa, zxlist, holder);
		case 4:
	           do1(aa,  zxlist, holder);
	           do2(aa,  zxlist, holder);
	           do3(aa,  zxlist, holder);
	           do4(aa,  zxlist, holder);
	           dol1(aa, zxlist, holder);
	           dol2(aa, zxlist, holder);
	           dol3(aa, zxlist, holder);
	           dol4(aa, zxlist, holder);

		case 5:
	           do1(aa,  zxlist, holder);
	           do2(aa,  zxlist, holder);
	           do3(aa,  zxlist, holder);
	           do4(aa,  zxlist, holder);
	           do5(aa,  zxlist, holder);
	           dol1(aa, zxlist, holder);
	           dol2(aa, zxlist, holder);
	           dol3(aa, zxlist, holder);
	           dol4(aa, zxlist, holder);
	           dol5(aa, zxlist, holder);

		default:
			break;
		}
	}
	private void do1(Integer aa,ZXList zxlist,ViewHolder holder){
		BadgeView badgeView1 = new com.jauker.widget.BadgeView(context);
		badgeView1.setTargetView(holder.mV1);
		badgeView1.setBackground(1111, Color.RED);
		badgeView1.setGravity(Gravity.TOP | Gravity.RIGHT);
		badgeView1.setTextSize(10);
		badgeView1.setBadgeCount(Integer.valueOf(zxlist.getNumber().get(0)));
	}
	private void do2(Integer aa,ZXList zxlist,ViewHolder holder){
		BadgeView badgeView1 = new com.jauker.widget.BadgeView(context);
		badgeView1.setTargetView(holder.mV2);
		badgeView1.setBackground(1111, Color.RED);
		badgeView1.setGravity(Gravity.TOP | Gravity.RIGHT);
		badgeView1.setTextSize(10);
		badgeView1.setBadgeCount(Integer.valueOf(zxlist.getNumber().get(1)));
	}
	private void do3(Integer aa,ZXList zxlist,ViewHolder holder){
		BadgeView badgeView1 = new com.jauker.widget.BadgeView(context);
		badgeView1.setTargetView(holder.mV3);
		badgeView1.setBackground(1111, Color.RED);
		badgeView1.setGravity(Gravity.TOP | Gravity.RIGHT);
		badgeView1.setTextSize(10);
		badgeView1.setBadgeCount(Integer.valueOf(zxlist.getNumber().get(2)));
	}
	private void do4(Integer aa,ZXList zxlist,ViewHolder holder){
		BadgeView badgeView1 = new com.jauker.widget.BadgeView(context);
		badgeView1.setTargetView(holder.mV4);
		badgeView1.setBackground(1111, Color.RED);
		badgeView1.setGravity(Gravity.TOP | Gravity.RIGHT);
		badgeView1.setTextSize(10);
		badgeView1.setBadgeCount(Integer.valueOf(zxlist.getNumber().get(3)));
	}
	private void do5(Integer aa,ZXList zxlist,ViewHolder holder){
		BadgeView badgeView1 = new com.jauker.widget.BadgeView(context);
		badgeView1.setTargetView(holder.mV5);
		badgeView1.setBackground(1111, Color.RED);
		badgeView1.setGravity(Gravity.TOP | Gravity.RIGHT);
		badgeView1.setTextSize(10);
		badgeView1.setBadgeCount(Integer.valueOf(zxlist.getNumber().get(4)));
	}
	private void dol1(Integer aa,ZXList zxlist,ViewHolder holder){
		holder.mRlshow1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("userId", "ljppff1");
				intent.putExtra("userAvatar", "dd");
				intent.putExtra("userNick", "ljppff");
				intent.setClass(context, ChatActivity.class);
				context.startActivity(intent);
			}
		});
	}
	private void dol2(Integer aa,ZXList zxlist,ViewHolder holder){
		holder.mRlshow2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("userId", "ljppff1");
				intent.putExtra("userAvatar", "dd");
				intent.putExtra("userNick", "ljppff");
				intent.setClass(context, ChatActivity.class);
				context.startActivity(intent);
			}
		});
	}
	private void dol3(Integer aa,ZXList zxlist,ViewHolder holder){
		holder.mRlshow3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("userId", "ljppff1");
				intent.putExtra("userAvatar", "dd");
				intent.putExtra("userNick", "ljppff");
				intent.setClass(context, ChatActivity.class);
				context.startActivity(intent);
			}
		});
	}
	private void dol4(Integer aa,ZXList zxlist,ViewHolder holder){
		holder.mRlshow4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("userId", "ljppff1");
				intent.putExtra("userAvatar", "dd");
				intent.putExtra("userNick", "ljppff");
				intent.setClass(context, ChatActivity.class);
				context.startActivity(intent);
			}
		});
	}
	private void dol5(Integer aa,ZXList zxlist,ViewHolder holder){
		holder.mRlshow5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("userId", "ljppff1");
				intent.putExtra("userAvatar", "dd");
				intent.putExtra("userNick", "ljppff");
				intent.setClass(context, ChatActivity.class);
				context.startActivity(intent);
			}
		});
	}



	class ViewHolder {
		com.rvidda.cn.view.CircleImageView mPn1,mPn2,mPn3,mPn4,mPn5;
		View mV1, mV2, mV3, mV4, mV5;
		TextView mTv1,mTv2,mTv3,mTv4,mTv5,mtv1,mtv2;
		LinearLayout mLLshow;
		RelativeLayout mRlshow1, mRlshow2, mRlshow3, mRlshow4, mRlshow5;
	}

}
