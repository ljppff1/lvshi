package com.rvidda.cn.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.rvidda.cn.R;
import com.rvidda.cn.SBaseAdapter;
import com.rvidda.cn.domain.XiaoXi;

public class XiaoXiAdapter extends SBaseAdapter {

	private DisplayImageOptions options;
	private Context context;

	public XiaoXiAdapter(Context context, List<?> data) {
		super(context, data, R.layout.item_xiaoxi_layout);
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

		holder.mtv1 = (TextView) convertView.findViewById(R.id.mtv1);
		holder.mtv2 = (TextView) convertView.findViewById(R.id.mtv2);

		convertView.setTag(holder);
	}

	@Override
	protected void holderView(View convertView, Object itemObject, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		XiaoXi zxlist = (XiaoXi) itemObject;

		holder.mtv1.setText(zxlist.getTitle());
		holder.mtv2.setText(zxlist.getTime());

	}

	class ViewHolder {
		com.rvidda.cn.view.CircleImageView mPn1;
		View mV1, mV2, mV3, mV4, mV5;
		TextView mtv1, mtv2;
		RelativeLayout mRlshow1, mRlshow2, mRlshow3, mRlshow4, mRlshow5;
	}

}
