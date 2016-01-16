package com.rvidda.cn.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Video.Media;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.ImageMessageBody;
import com.fanxin.app.activity.ShowBigImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.rvidda.cn.R;
import com.rvidda.cn.domain.LSSYList;
import com.rvidda.cn.domain.ZXXiaoXi;
import com.rvidda.cn.http.ContantsUtil;
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
	private ImageView iv_sendPicture;
	private ProgressBar progressBar;
	private TextView percentage;
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
		}else if(data.get(position).getMtype().equals("ImageMessage")){
			convertView = LayoutInflater.from(context).inflate(
					R.layout.row_received_picture1, null);
			iv_sendPicture =(ImageView)convertView.findViewById(R.id.iv_sendPicture);
			TextView timestamp = (TextView)convertView.findViewById(R.id.timestamp);
			timestamp.setText(data.get(position).getTime());
            if(TextUtils.isEmpty(data.get(position).getTime())){
            	timestamp.setVisibility(View.GONE);
            }
           LinearLayout ll_loading = (LinearLayout)convertView.findViewById(R.id.ll_loading);
            ll_loading.setVisibility(View.GONE);
    		ImageView iv_userhead = (ImageView)convertView.findViewById(R.id.iv_userhead);
    		ImageLoader.getInstance().displayImage(myUserAvatar, iv_userhead, options);	
    		progressBar =(ProgressBar)convertView.findViewById(R.id.progressBar);
    		percentage =(TextView)convertView.findViewById(R.id.percentage);
    		if(TextUtils.isEmpty(data.get(position).getMfilelocal())){
    		ImageLoader.getInstance().displayImage(data.get(position).getMtext()+"?imageMogr2/thumbnail/100x100", iv_sendPicture, options);		
    		}else{
        		ImageLoader.getInstance().displayImage("file://"+data.get(position).getMfilelocal(), iv_sendPicture, options);		
        		String ID = data.get(position).getLength();
        		data.get(position).setLength("");
        		if(!TextUtils.isEmpty(ID)){
        		sendFileAndLvyin(progressBar,percentage,data.get(position).getMfilelocal(), data.get(position).getMtype(), ID);
        		}
    		}
    		iv_sendPicture.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, ShowBigImage.class);
					if (!TextUtils.isEmpty(data.get(position).getMfilelocal())) {
						File file = new File(data.get(position).getMfilelocal());
						Uri uri = Uri.fromFile(file);
						intent.putExtra("uri", uri);
					} else {
						intent.putExtra("secret", "");
						intent.putExtra("remotepath", data.get(position).getMtext());
					}	
					context.startActivity(intent);
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
	
	//发送文件到七牛
	public void sendFileAndLvyin(final ProgressBar progressBar,final TextView percentage,final String filenames,final String type,final String ID){		
		Map<String, Object> params = new HashMap<String, Object>();
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.QiNiu, "get", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
							JSONObject jsonObj = new JSONObject(json);
							String uptoken = jsonObj.getString("uptoken");
						    String key = jsonObj.getString("key");
                            sendFiletoQiNiu(progressBar,percentage,filenames,uptoken, key,type,ID);
							}else{
			                       Toast.makeText(context, R.string.log6, 0).show();
										}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
   private int dd =0;
	private void sendFiletoQiNiu(final ProgressBar progressBar,final TextView percentage,final String file,String uptoken,String key,final String type,final String ID) {
		UploadManager uploadManager = new UploadManager();
		try{
		uploadManager.put(file, key, uptoken,
		new UpCompletionHandler() {
		    @Override
			public void complete(String key, ResponseInfo info, JSONObject res) {
				// TODO Auto-generated method stub
		        Log.e("qiniu---", key + ",\r\n " + info + ",\r\n " + res);
		        try {
					String keyvalue = res.getString("key");
					initTJxx(key, type,ID);
                     progressBar.setVisibility(View.GONE);
                     percentage.setVisibility(View.GONE);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//   /storage/2A94-E4F0/DCIM/Camera/1/IMG_20351008_174652.jpg
//   /storage/2A94-E4F0/Android/data/com.rvidda.cn/ljppff1#rvidda/ljppff/voice/null20160104T164855.amr
			}
		}, new UploadOptions(null, "audio/amr", true,  new UpProgressHandler(){
            public void progress(String key, double percent){
               progressBar.setVisibility(View.VISIBLE);
               percentage.setVisibility(View.VISIBLE);
              double result=percent;
              int temp  = (int)(result  * 1000);
              if((temp-dd)>10){
            	  dd=temp;
               result  = (double)temp/ 10;
               percentage.setText(result+"%");
              }
            
            }
        },null));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void initTJxx(String key,String type,String ID)
	{
		Map<String, Object> params = new HashMap<String, Object>();
	//VoiceMessage   TextMessage	
		params.put("message[body]",key);
		params.put("message[type]",type);
			params.put("message[echo]", System.currentTimeMillis());
		
		
		com.rvidda.cn.http.HttpServiceUtil.request(ContantsUtil.HOST+"/subjects/"+ID+"/messages", "post", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
							JSONObject jsonObj = new JSONObject(json);
							
							}else{
                         Toast.makeText(context, R.string.log9, 0).show();

							}
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

	}

}
