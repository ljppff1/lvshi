package com.rvidda.cn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Video.Media;
import android.text.TextUtils;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rvidda.cn.R;
import com.rvidda.cn.domain.LSSYList;
import com.rvidda.cn.domain.ZXXiaoXi;
import com.rvidda.cn.utils.Content;
import com.rvidda.cn.utils.DownLoad;
import com.rvidda.cn.utils.PreferenceUtils;
import com.rvidda.cn.view.FlowLayout;

public class ZXAdapter1 extends BaseAdapter {

	private DisplayImageOptions options;
	private Context context;
	private List<ZXXiaoXi> data = new ArrayList<ZXXiaoXi>();
    private com.rvidda.cn.utils.Media media;
	private PreferenceUtils pp;
	private String myUserAvatar;
	public ZXAdapter1(Context context, List<ZXXiaoXi> data,com.rvidda.cn.utils.Media media) {
	     pp =PreferenceUtils.getInstance(context);
		myUserAvatar =pp.getString(Content.Avator_Url, "");
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
	private Handler handler =new Handler(){
		public void handleMessage(android.os.Message msg) {
		     switch (msg.what) {
			case 1:
			    ZXXiaoXi zxxiao = ((ZXXiaoXi)msg.obj);
			    if(media.isplay()){
			    	media.stopPlay();
			    }else{
			    if(!TextUtils.isEmpty(zxxiao.getMfilelocal())){
				media.startPlay(zxxiao.getMfilelocal());
			    }
			    }
				break;

			default:
				break;
			}
		};
	};
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
		if(data.get(position).getMtype().equals("VoiceMessage")){
		convertView = LayoutInflater.from(context).inflate(
				R.layout.row_received_voice, null);
		
		ImageView iv_userhead = (ImageView)convertView.findViewById(R.id.iv_userhead);
		ImageLoader.getInstance().displayImage(myUserAvatar, iv_userhead, options);		

		TextView timestamp = (TextView)convertView.findViewById(R.id.timestamp);
		timestamp.setText(data.get(position).getTime());
        if(TextUtils.isEmpty(data.get(position).getTime())){
        	timestamp.setVisibility(View.GONE);
        }

		TextView tv_length = (TextView)convertView.findViewById(R.id.tv_length);
		tv_length.setText(data.get(position).getLength());
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
		    if(!TextUtils.isEmpty(data.get(position).getMfilelocal())){
			media.startPlay(data.get(position).getMfilelocal());
		    }else{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                      String str=  new DownLoad().download(data.get(position).getMtext());
                      if(!TextUtils.isEmpty(str)){
                    	  data.get(position).setMfilelocal(str);
                    	 Message msg =new Message();
                    	 msg.what =1;
                    	 msg.obj =data.get(position);
                    	 handler.sendMessage(msg);
                      }
                    }
                }).start();
		    }
		    }
			}
		});
		}else{
			convertView = LayoutInflater.from(context).inflate(
					R.layout.row_received_message, null);
			TextView timestamp = (TextView)convertView.findViewById(R.id.timestamp);
			timestamp.setText(data.get(position).getTime());
            if(TextUtils.isEmpty(data.get(position).getTime())){
            	timestamp.setVisibility(View.GONE);
            }
    		ImageView iv_userhead = (ImageView)convertView.findViewById(R.id.iv_userhead);
    		ImageLoader.getInstance().displayImage(myUserAvatar, iv_userhead, options);		

			TextView tv = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);
			tv.setText(data.get(position).getMtext());
		}
		
		
		
		
		
		return convertView;

	}

}
