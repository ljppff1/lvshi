package com.rvidda.cn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore.Video.Media;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.rvidda.cn.R;
import com.rvidda.cn.domain.LSSYList;
import com.rvidda.cn.domain.ZXXiaoXi;
import com.rvidda.cn.view.FlowLayout;

public class ZXAdapter extends BaseAdapter {

	private DisplayImageOptions options;
	private Context context;
	private List<ZXXiaoXi> data = new ArrayList<ZXXiaoXi>();
    private com.rvidda.cn.utils.Media media;
	public ZXAdapter(Context context, List<ZXXiaoXi> data,com.rvidda.cn.utils.Media media) {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.content_bg)
				.showImageForEmptyUri(R.drawable.content_bg)
				.showImageOnLoading(R.drawable.content_bg)
				.showImageOnFail(R.drawable.content_bg).cacheInMemory(true)
				.cacheOnDisc(true).build();
		this.context = context;
		this.data = data;
		this.media =media;
	}
	class ViewHolder {
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
		if(data.get(position).getMtype().equals("V")){
		convertView = LayoutInflater.from(context).inflate(
				R.layout.row_received_voice, null);
		TextView timestamp = (TextView)convertView.findViewById(R.id.timestamp);
		timestamp.setVisibility(View.GONE);
		TextView tv_length = (TextView)convertView.findViewById(R.id.tv_length);
		tv_length.setVisibility(View.GONE);
		ImageView iv_unread_voice = ((ImageView) convertView.findViewById(R.id.iv_unread_voice));
		iv_unread_voice.setVisibility(View.GONE);
		
		ImageView iv = ((ImageView) convertView.findViewById(R.id.iv_voice));
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//语音监听说话
		    if(media.isplay()){
		    	media.stopPlay();
		    }else{
			media.startPlay(data.get(position).getMfilelocal());
		    }
			}
		});
		}else{
			convertView = LayoutInflater.from(context).inflate(
					R.layout.row_received_message, null);
			TextView timestamp = (TextView)convertView.findViewById(R.id.timestamp);
			timestamp.setVisibility(View.GONE);

			TextView tv = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);
			tv.setText(data.get(position).getMtext());
		}
		
		
		
		
		
		return convertView;

	}

}
