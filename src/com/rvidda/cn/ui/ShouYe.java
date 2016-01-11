package com.rvidda.cn.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.easemob.EMCallBack;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.util.VoiceRecorder;
import com.fanxin.app.DemoApplication;
import com.fanxin.app.adapter.VoicePlayClickListener;
import com.fanxin.app.utils.CommonUtils;
import com.lidroid.xutils.http.RequestParams;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.rvidda.cn.AppManager;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.domain.Items;
import com.rvidda.cn.http.ContantsUtil;
import com.rvidda.cn.utils.Content;
import com.rvidda.cn.utils.LoadingDialog;
import com.rvidda.cn.utils.Media;
import com.rvidda.cn.utils.PreferenceUtils;

public class ShouYe extends BaseActivity {
	private String toChatUsername;
	private String filepath;

	private ImageView mIvtalk;
	private AnimationDrawable anim;
	private Media media;
	private boolean Flag = false;
	private ImageView mIvshow1;
	private ImageView mIvshow2;
	private LinearLayout mLLshow3;
	private long exitTime1 = 0;
	private boolean flagl = true;;//判断时间 入口，<2s就继续。>2s肯定会显示那个列表。。
	private boolean flagan = false;//是否按下
	private String file_path;

	private String[] listd1 = { "婚姻", "交通", "税务", "债务", "合同", "房屋", "劳动协议",
			"知识产权", "其它", "婚姻", "更多" };
	private GridView mGv1;
	private List<Items> list = new ArrayList<Items>();
	private Myadapter adapter;
	private WakeLock wakeLock;
	private Handler micImageHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			// 切换msg切换图片
			micImage.setImageDrawable(micImages[msg.what]);
		}
	};
	private VoiceRecorder voiceRecorder;
	private Drawable[] micImages;
	private ImageView micImage;
	private View recordingContainer;
	private TextView recordingHint;
	private LoadingDialog dialog;
	private String LENGTH;

	@Override
	protected void onResume() {
		//取消
		flagan=false;
		flagl = true;
				mIvshow1.setVisibility(View.VISIBLE);
				mIvshow2.setVisibility(View.VISIBLE);
				mLLshow3.setVisibility(View.GONE);
				mRlsure.setVisibility(View.GONE);
				mIvtalk.setVisibility(View.VISIBLE);
				
		super.onResume();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shouye);
		dialog =new LoadingDialog(ShouYe.this, "数据提交，请稍后");
		media = new Media();
		initHuanXin();
		//login();
		initView();
		initgetBiaoqian();
	   //	initData();
		// createFileK();
	}

    private void initHuanXin(){
		micImage = (ImageView) findViewById(R.id.mic_image);

		// 动画资源文件,用于录制语音时
		micImages = new Drawable[] {
				getResources().getDrawable(R.drawable.record_animate_01),
				getResources().getDrawable(R.drawable.record_animate_02),
				getResources().getDrawable(R.drawable.record_animate_03),
				getResources().getDrawable(R.drawable.record_animate_04),
				getResources().getDrawable(R.drawable.record_animate_05),
				getResources().getDrawable(R.drawable.record_animate_06),
				getResources().getDrawable(R.drawable.record_animate_07),
				getResources().getDrawable(R.drawable.record_animate_08),
				getResources().getDrawable(R.drawable.record_animate_09),
				getResources().getDrawable(R.drawable.record_animate_10),
				getResources().getDrawable(R.drawable.record_animate_11),
				getResources().getDrawable(R.drawable.record_animate_12),
				getResources().getDrawable(R.drawable.record_animate_13),
				getResources().getDrawable(R.drawable.record_animate_14), };

		recordingContainer = findViewById(R.id.recording_container);
		recordingHint = (TextView) findViewById(R.id.recording_hint);
		voiceRecorder = new VoiceRecorder(micImageHandler);
		wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE))
				.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "demo");

    }
	
	private void initgetBiaoqian()
	{
	
		Map<String, Object> params = new HashMap<String, Object>();
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.LABELS, "get", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
							JSONObject jsonObj = new JSONObject(json);
							org.json.JSONArray ja =jsonObj.getJSONArray("labels");
							for(int i=0;i<ja.length();i++){
							JSONObject jb =	(JSONObject) ja.get(i);
						    org.json.JSONArray cd =	jb.getJSONArray("children");
						    for(int j=0;j<cd.length();j++){
						    	JSONObject jj =cd.getJSONObject(j);
								Items items = new Items();
								items.setItems(jj.getString("name"));
								items.setId(jj.getInt("id"));
								items.setFlag(false);
								list.add(items);
						    }
							}
							adapter = new Myadapter();
							mGv1.setAdapter(adapter);

							}else{
			                       Toast.makeText(getApplicationContext(), R.string.log6, 0).show();
										}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
	
	
	
	private void initData() {
		for (int i = 0; i < listd1.length; i++) {
			Items items = new Items();
			items.setItems(listd1[i]);
			items.setFlag(false);
			list.add(items);
		}
		
	}

	
	private void createFileK() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				String sendpath = Environment.getExternalStorageDirectory()
						.getCanonicalPath().toString()
						+ "/MessageMediaSend";
				File files = new File(sendpath);
				if (!files.exists()) {
					files.mkdir();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				String receivepath = Environment.getExternalStorageDirectory()
						.getCanonicalPath().toString()
						+ "/MessageMediaReceive";
				File files = new File(receivepath);
				if (!files.exists()) {
					files.mkdir();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	private void login() {
		EMChatManager.getInstance().login("ljppff", "aaaaaa", new EMCallBack() {
			@Override
			public void onSuccess() {
				// 登陆成功，保存用户名密码
				DemoApplication.getInstance().setUserName("ljppff");
				DemoApplication.getInstance().setPassword("aaaaaa");
				runOnUiThread(new Runnable() {
					public void run() {
						// dialog.setMessage(getString(R.string.list_is_for));
					}
				});
				try {
					// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
					// ** manually load all local groups and
					// conversations in case we are auto login
					// 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
					boolean updatenick = EMChatManager.getInstance()
							.updateCurrentUserNick("刘俊");
					if (!updatenick) {
						Log.e("LoginActivity", "update current user nick fail");
					}
					EMGroupManager.getInstance().loadAllGroups();
					EMChatManager.getInstance().loadAllConversations();
					// 处理好友和群组
					/*
					 * runOnUiThread(new Runnable() { public void run() {
					 * processContactsAndGroups(json); } });
					 */
				} catch (Exception e) {
					e.printStackTrace();
					// 取好友或者群聊失败，不让进入主页面
					runOnUiThread(new Runnable() {
						public void run() {
						}
					});
					return;
				}
			}

			@Override
			public void onProgress(int progress, String status) {
			}

			@Override
			public void onError(final int code, final String message) {

				runOnUiThread(new Runnable() {
					public void run() {
						// dialog.dismiss();
						Toast.makeText(getApplicationContext(),
								getString(R.string.Login_failed) + message,
								Toast.LENGTH_SHORT).show();
					}
				});
			}
		});

	}
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};
	private RelativeLayout mVVli;
	private RelativeLayout mRlsure;

	private void initView() {
		mGv1 = (GridView) this.findViewById(R.id.mGv1);
		mGv1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

		mIvshow1 = (ImageView) this.findViewById(R.id.mIvshow1);
		mIvshow2 = (ImageView) this.findViewById(R.id.mIvshow2);
		mLLshow3 = (LinearLayout) this.findViewById(R.id.mLLshow3);
		mRlsure = (RelativeLayout) this.findViewById(R.id.mRlsure);
		mRlsure.setOnClickListener(listener);
		mRlsure.setVisibility(View.GONE);
		mIvtalk = (ImageView) this.findViewById(R.id.mIvtalk);
		mIvtalk.setVisibility(View.VISIBLE);
		anim = (AnimationDrawable) mIvtalk.getBackground();
		
		
		
		mIvtalk.setOnTouchListener(new PressToSpeakListen());
/*		mIvtalk.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				flagan = true;
				exitTime1 = System.currentTimeMillis();
				Toast toast = Toast
						.makeText(getApplicationContext(), "正在录音", 0);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				if (anim != null && !anim.isRunning())
					anim.start();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (flagl&&flagan) {
							mIvshow1.setVisibility(View.GONE);
							mIvshow2.setVisibility(View.GONE);
							mLLshow3.setVisibility(View.VISIBLE);
							flagl = false;
						} else {
							flagl = true;
							Toast toast = Toast.makeText(
									getApplicationContext(), "录音时间太短", 0);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
					}
				}, 2000);
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						if(flagan){
						Toast toast = Toast.makeText(
								getApplicationContext(), "录音时间还剩10s", 0);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						}
						
					}
				}, 10000);
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						if(flagan){
						Toast toast = Toast.makeText(
								getApplicationContext(), "录音完成", 0);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

						if (Flag) {
							media.stopRecord();
							Flag = false;
						}
						if (anim != null && anim.isRunning()) {
							anim.selectDrawable(0);
							anim.stop();

							// startActivity(new Intent(ShouYe.this, Zixun.class));
						}
						}
					}
				}, 20000);

				media.startRecord();
				Flag = true;
				return false;
			}
		});
*/		mVVli = (RelativeLayout) this.findViewById(R.id.mRlwhatshow);
		mVVli.setOnTouchListener(new OnTouchListener() {

			private float x1;
			private float y1;
			private float x2;
			private float y2;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					x1 = event.getX();
					y1 = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					x2 = event.getX();
					y2 = event.getY();
					if (y1 - y2 > 50) {
						startActivity(new Intent(getApplicationContext(),
								ZiXunLieBiao.class));
					}
				}
				return true;
			}
		});

/*		mIvtalk.setOnTouchListener(new OnTouchListener() {

			private float x1;
			private float y1;
			private float x2;
			private float y2;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					x1 = event.getX();
					y1 = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					flagan=false;
					x2 = event.getX();
					y2 = event.getY();
					if (y1 - y2 > 50) {
							mIvshow1.setVisibility(View.VISIBLE);
							mIvshow2.setVisibility(View.VISIBLE);
							mLLshow3.setVisibility(View.GONE);
							mRlsure.setVisibility(View.GONE);
					}
					//抬起，如果大于2s是true，
					if (System.currentTimeMillis() - exitTime1 < 2000) {
						flagl = false;
					} else {
						flagl = true;
						mRlsure.setVisibility(View.VISIBLE);
						mIvtalk.setVisibility(View.GONE);
					}
				
					if (Flag) {
						media.stopRecord();
						Flag = false;
					}
					if (anim != null && anim.isRunning()) {
						anim.selectDrawable(0);
						anim.stop();

						// startActivity(new Intent(ShouYe.this, Zixun.class));
					}
					break;
				}
				return false;
			}
		});*/

	}
	

	/**
	 * 按住说话listener
	 * 
	 */
	class PressToSpeakListen implements View.OnTouchListener {


		@SuppressLint({ "ClickableViewAccessibility", "Wakelock" })
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				exitTime1 = System.currentTimeMillis();
				flagan = true;
				if (!CommonUtils.isExitsSdcard()) {
					Toast.makeText(ShouYe.this, "发送语音需要sdcard支持！",
							Toast.LENGTH_SHORT).show();
					return false;
				}
				try {
					v.setPressed(true);
					wakeLock.acquire();
					if (VoicePlayClickListener.isPlaying)
						VoicePlayClickListener.currentPlayListener
								.stopPlayVoice();
					recordingContainer.setVisibility(View.VISIBLE);
					recordingHint
							.setText(getString(R.string.move_up_to_cancel));
					recordingHint.setBackgroundColor(Color.TRANSPARENT);
					voiceRecorder.startRecording(null, toChatUsername,
							getApplicationContext());
					
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							if (flagl&&flagan) {
								mIvshow1.setVisibility(View.GONE);
								mIvshow2.setVisibility(View.GONE);
								mLLshow3.setVisibility(View.VISIBLE);
								flagl = false;
							} else {
								flagl = true;
								Toast toast = Toast.makeText(
										getApplicationContext(), "录音时间太短", 0);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
							}
						}
					}, 2000);

				} catch (Exception e) {
					e.printStackTrace();
					v.setPressed(false);
					if (wakeLock.isHeld())
						wakeLock.release();
					if (voiceRecorder != null)
						voiceRecorder.discardRecording();
					recordingContainer.setVisibility(View.INVISIBLE);
					Toast.makeText(ShouYe.this, R.string.recoding_fail,
							Toast.LENGTH_SHORT).show();
					return false;
				}

				return true;
			case MotionEvent.ACTION_MOVE: {
				if (event.getY() < 0) {
					recordingHint
							.setText(getString(R.string.release_to_cancel));
					recordingHint
							.setBackgroundResource(R.drawable.recording_text_hint_bg);
				} else {
					recordingHint
							.setText(getString(R.string.move_up_to_cancel));
					recordingHint.setBackgroundColor(Color.TRANSPARENT);
				}
				return true;
			}
			case MotionEvent.ACTION_UP:
				//抬起，如果大于2s是true，
				if (System.currentTimeMillis() - exitTime1 < 2000) {
					flagl = false;
				} else {
					flagl = true;
					mRlsure.setVisibility(View.VISIBLE);
					mIvtalk.setVisibility(View.GONE);
				}

				v.setPressed(false);
				recordingContainer.setVisibility(View.INVISIBLE);
				if (wakeLock.isHeld())
					wakeLock.release();
				if (event.getY() < 0) {
					// discard the recorded audio.
					voiceRecorder.discardRecording();
					//取消
					flagan=false;
							mIvshow1.setVisibility(View.VISIBLE);
							mIvshow2.setVisibility(View.VISIBLE);
							mLLshow3.setVisibility(View.GONE);
							mRlsure.setVisibility(View.GONE);
							mIvtalk.setVisibility(View.VISIBLE);

				} else {
					// stop recording and send voice file
					try {
						int length = voiceRecorder.stopRecoding();
						 LENGTH = length +"";
					//	Toast.makeText(getApplicationContext(), length+"", 1).show();
						if (length > 0) {
/*							sendVoice(voiceRecorder.getVoiceFilePath(),
									voiceRecorder
											.getVoiceFileName(toChatUsername),
									Integer.toString(length), false);
*/				//	sendFileAndLvyin(voiceRecorder.getVoiceFilePath());
							file_path =voiceRecorder.getVoiceFilePath();
							} else if (length == EMError.INVALID_FILE) {
							Toast.makeText(getApplicationContext(), "无录音权限",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), "录音时间太短",
									Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(ShouYe.this, "发送失败，请检测服务器是否连接",
								Toast.LENGTH_SHORT).show();
					}
				}
				return true;
			default:
				recordingContainer.setVisibility(View.INVISIBLE);
				if (voiceRecorder != null)
					voiceRecorder.discardRecording();
				return false;
			}
		}
	}


	class Holder {
		TextView mTvg1;
		RelativeLayout mRlg1;
		ImageView mIvg1;
	}

	class Myadapter extends BaseAdapter {
		@SuppressLint("NewApi")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			Holder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.item_gridview_1, null);
				holder = new Holder();
				holder.mRlg1 = (RelativeLayout) convertView
						.findViewById(R.id.mRlg1);
				holder.mTvg1 = (TextView) convertView.findViewById(R.id.mTvg1);
				holder.mIvg1 = (ImageView) convertView.findViewById(R.id.mIvg1);
				convertView.setTag(holder);

			} else {
				holder = (Holder) convertView.getTag();
			}
			final TextView mTvg1 = (TextView) convertView
					.findViewById(R.id.mTvg1);
			final ImageView mIvg1 = (ImageView) convertView
					.findViewById(R.id.mIvg1);

			holder.mRlg1 = (RelativeLayout) convertView
					.findViewById(R.id.mRlg1);
			holder.mRlg1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Items item = list.get(position);
					if (item.getFlag()) {
						mTvg1.setTextColor(getApplicationContext()
								.getResources().getColor(R.color.black));
						mIvg1.setImageResource(R.drawable.biaoqiandise);
						item.setFlag(false);
					} else {
						item.setFlag(true);
						mTvg1.setTextColor(getApplicationContext()
								.getResources().getColor(R.color.white));
						mIvg1.setImageResource(R.drawable.xuanzhong);

					}
				}
			});

			if (list.get(position).getFlag()) {
				mTvg1.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.white));
				holder.mIvg1.setImageResource(R.drawable.xuanzhong);
			} else {
				mTvg1.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.black));
				holder.mIvg1.setImageResource(R.drawable.biaoqiandise);

			}
			holder.mTvg1.setText(list.get(position).getItems());
			return convertView;

		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mIvtalk:

				break;
			case R.id.mRlsure:
				dialog.show();
				sendFileAndLvyin("");
				break;
			default:
				break;
			}
		}
	};

	

	//发送文件到七牛
	public void sendFileAndLvyin(final String filenames){		
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
                            sendFiletoQiNiu(file_path,uptoken, key);
							}else{
								if (dialog.isShowing()) {
									dialog.cancel();
								}
			                       Toast.makeText(getApplicationContext(), R.string.log6, 0).show();
										}
						} catch (JSONException e) {
							if (dialog.isShowing()) {
								dialog.cancel();
							}
							e.printStackTrace();
						}
					}
				});
	}
	private void sendFiletoQiNiu(String file,String uptoken,String key) {
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
					initTiJiao(keyvalue);
					
				} catch (JSONException e) {
					if (dialog.isShowing()) {
						dialog.cancel();
					}					e.printStackTrace();
				}
//   /storage/2A94-E4F0/DCIM/Camera/1/IMG_20351008_174652.jpg
//   /storage/2A94-E4F0/Android/data/com.rvidda.cn/ljppff1#rvidda/ljppff/voice/null20160104T164855.amr
			}
		}, new UploadOptions(null, "audio/amr", true,  new UpProgressHandler(){
            public void progress(String key, double percent){
                Log.i("qiniu", key + ": " + percent);
            }
        },null));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void initTiJiao(String key)
	{
	    try {
		    JSONObject obj = new JSONObject();
			obj.put("body",key);
		    obj.put("type", "VoiceMessage");
		    obj.put("echo", System.currentTimeMillis()+"_"+LENGTH);
          org.json.JSONArray ja =new org.json.JSONArray();
          ja.put(obj);
           org.json.JSONArray ja1 =new org.json.JSONArray();
	   		for(int i=0;i<list.size();i++){
		   		 ja1.put(list.get(i).getId());
		   		}

           JSONObject j2 =new JSONObject();
           j2.put("title", "lj");
           j2.put("label_ids", ja1);
           j2.put("messages_attributes", ja);
           
           JSONObject jf =new JSONObject();
           jf.put("subject", j2);
           
          	Map<String, Object> params = new HashMap<String, Object>();
    		params.put("json", jf.toString());
    		com.rvidda.cn.http.HttpServiceUtil.request(ContantsUtil.TiJiaoZiX, "post1", params,
    				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
    					@Override
    					public void callback(String json) {
    						try {
    							if(!json.equals("0")){
    /**
     * {"subject":{"id":177,"title":"lj","created_at":"2016-01-06 10:02:24",
     * "updated_at":"2016-01-06 10:02:24","user_businesses":[],"lawyers":[],"messages":[],"labels":[]}}
     */
    							JSONObject jsonObj = new JSONObject(json);
    							JSONObject subject = jsonObj.getJSONObject("subject");
    						   Integer id = subject.getInt("id");
    							Intent intent =new Intent(getApplicationContext(),TiChuZiXun.class);
    							intent.putExtra("ID", id+"");
    							intent.putExtra("SELECT",getsendBiaoqian1());
    							intent.putExtra("FILE", file_path);
    							startActivity(intent);
    							}else{
    								if (dialog.isShowing()) {
    									dialog.cancel();
    								}
                           Toast.makeText(getApplicationContext(), R.string.log9, 0).show();

    							}
    							
    							if(dialog.isShowing()){
    								dialog.cancel();
    							}

    						} catch (JSONException e) {
    							e.printStackTrace();
    							if (dialog.isShowing()) {
    								dialog.cancel();
    							}
    						}
    					}
    				});
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
     
		/*
		Map<String, Object> params = new HashMap<String, Object>();
		
		
		params.put("subject[messages_attributes][body]",key);
		params.put("subject[messages_attributes][type]","VoiceMessage");
	//	params.put("subject[label_ids]",getsendBiaoqian());
		String[] str =getsendBiaoqian().split(",");
		for(int i=0;i<str.length;i++){
			params.put("subject[label_ids]["+i+"]",str[i]);
		}

		params.put("subject[title]","lj");
		com.rvidda.cn.http.HttpServiceUtil.request(ContantsUtil.TiJiaoZiX, "post", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
*//**
 * {"subject":{"id":177,"title":"lj","created_at":"2016-01-06 10:02:24",
 * "updated_at":"2016-01-06 10:02:24","user_businesses":[],"lawyers":[],"messages":[],"labels":[]}}
 *//*
							JSONObject jsonObj = new JSONObject(json);
							JSONObject subject = jsonObj.getJSONObject("subject");
						   Integer id = subject.getInt("id");
							Intent intent =new Intent(getApplicationContext(),TiChuZiXun.class);
							intent.putExtra("ID", id+"");
							intent.putExtra("SELECT",getsendBiaoqian1());
							intent.putExtra("FILE", file_path);
							startActivity(intent);
							}else{
								if (dialog.isShowing()) {
									dialog.cancel();
								}
                       Toast.makeText(getApplicationContext(), R.string.log9, 0).show();

							}
							
							if(dialog.isShowing()){
								dialog.cancel();
							}

						} catch (JSONException e) {
							e.printStackTrace();
							if (dialog.isShowing()) {
								dialog.cancel();
							}
						}
					}
				});*/
	}
	
	
	private String getsendBiaoqian()
	{
		List<Integer> listss =new ArrayList<Integer>();
	  for(int i=0;i<list.size();i++){
		  if(list.get(i).getFlag()){
			  listss.add(list.get(i).getId());
		  }
	  }
	  String str ="[";
	  for(int i=0;i<listss.size();i++){
		  if(i<=listss.size()-2){
			str =str +listss.get(i)+",";  
		  }else{
				str =str +listss.get(i)+"]";   
		  }
	  }
	  return str;
	}
	private String getsendBiaoqian1()
	{
		List<Integer> listss =new ArrayList<Integer>();
	  for(int i=0;i<list.size();i++){
		  if(list.get(i).getFlag()){
			  listss.add(list.get(i).getId());
		  }
	  }
	  String str ="";
	  for(int i=0;i<listss.size();i++){
		  if(i<=listss.size()-2){
			str =str +listss.get(i)+",";  
		  }else{
			  str =str +listss.get(i);
		  }
	  }
	  return str;
	}

	
}
