package com.rvidda.cn.adapter;


import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rvidda.cn.R;
import com.rvidda.cn.SBaseAdapter;
import com.rvidda.cn.domain.News;
import com.rvidda.cn.domain.ZXList;
import com.rvidda.cn.utils.DateUtil;
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
		this.context =context;
	}

	@Override
	protected void newView(View convertView) {
		ViewHolder holder = new ViewHolder();

		holder.mRlshow1 =(RelativeLayout)convertView.findViewById(R.id.mRlshow1);
		holder.mRlshow2 =(RelativeLayout)convertView.findViewById(R.id.mRlshow2);
		holder.mRlshow3 =(RelativeLayout)convertView.findViewById(R.id.mRlshow3);
		holder.mRlshow4 =(RelativeLayout)convertView.findViewById(R.id.mRlshow4);
		holder.mRlshow5 =(RelativeLayout)convertView.findViewById(R.id.mRlshow5);
		holder.mPn1 =(CircleImageView) convertView.findViewById(R.id.mPn1);
		holder.mV1 =convertView.findViewById(R.id.mV1);
		holder.mV2 =convertView.findViewById(R.id.mV2);
		holder.mV3 =convertView.findViewById(R.id.mV3);
		holder.mV4 =convertView.findViewById(R.id.mV4);
		holder.mV5 =convertView.findViewById(R.id.mV5);
		holder.mTv1 =(TextView) convertView.findViewById(R.id.mtv1);
		
		convertView.setTag(holder);
	}

	@Override
	protected void holderView(View convertView, Object itemObject, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		ZXList zxlist = (ZXList) itemObject;

			holder.mTv1.setText(zxlist.getName().get(0));
		//	ImageLoader.getInstance().displayImage(news.getThumbnail(), holder.iconView, options);
		
			BadgeView badgeView = new com.jauker.widget.BadgeView(context);
			badgeView.setTargetView(holder.mV1);
			badgeView.setBackground(1111, Color.RED);
			badgeView.setGravity(Gravity.TOP|Gravity.RIGHT);
			badgeView.setTextSize(10);
			badgeView.setBadgeCount(32);
			if(position<4){
			BadgeView badgeView1 = new com.jauker.widget.BadgeView(context);
			badgeView1.setTargetView(holder.mV4);
			badgeView1.setBackground(1111, Color.RED);
			badgeView1.setGravity(Gravity.TOP|Gravity.RIGHT);
			badgeView1.setTextSize(10);
			badgeView1.setBadgeCount(8);
			}

			
       if(position<4){
    	   holder.mRlshow5.setVisibility(View.GONE);
       }
       if(position<2){
    	   holder.mRlshow4.setVisibility(View.GONE);
    	   holder.mRlshow5.setVisibility(View.GONE);
       }
			
		
	}

	class ViewHolder {
		com.rvidda.cn.view.CircleImageView mPn1;
		View mV1,mV2,mV3,mV4,mV5;
		TextView mTv1;
		RelativeLayout mRlshow1,mRlshow2,mRlshow3,mRlshow4,mRlshow5;
	}

}
