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
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.fanxin.app.DemoApplication;
import com.lidroid.xutils.http.RequestParams;
import com.rvidda.cn.AppManager;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.domain.Items;
import com.rvidda.cn.http.ContantsUtil;
import com.rvidda.cn.utils.Content;
import com.rvidda.cn.utils.Media;
import com.rvidda.cn.utils.PreferenceUtils;

public class ShouYe extends BaseActivity {

	private ImageView mIvtalk;
	private AnimationDrawable anim;
	private Media media;
	private boolean Flag = false;
	private ImageView mIvshow1;
	private ImageView mIvshow2;
	private LinearLayout mLLshow3;
	private long exitTime1 = 0;
	private boolean flagl = true;;
	private boolean flagan = false;//是否按下

	private String[] listd1 = { "婚姻", "交通", "税务", "债务", "合同", "房屋", "劳动协议",
			"知识产权", "其它", "婚姻", "更多" };
	private GridView mGv1;
	private List<Items> list = new ArrayList<Items>();
	private Myadapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shouye);
		media = new Media();
		login();
		initView();

		initgetBiaoqian();
	   //	initData();
		// createFileK();
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
	private void inisendBiaoqian()
	{
		List<Integer> listss =new ArrayList<Integer>();
	  for(int i=0;i<list.size();i++){
		  if(list.get(i).getFlag()){
			  listss.add(list.get(i).getId());
		  }
	  }
	  String str ="[";
	  for(int i=0;i<listss.size();i++){
		  if(i<=listss.size()-1){
			str =str +listss.get(i)+"]";  
		  }else{
				str =str +listss.get(i)+",";   
		  }
	  }
	  
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subject.title", "");
		params.put("subject.label_ids", str);
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.HOST+"/subjects/"+"id", "put", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
							JSONObject jsonObj = new JSONObject(json);

							}else{
			                       Toast.makeText(getApplicationContext(), R.string.zx1, 0).show();
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
		mRlsure.setVisibility(View.GONE);
		mRlsure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						TiChuZiXun.class));				
			}
		});
		mIvtalk = (ImageView) this.findViewById(R.id.mIvtalk);
		mIvtalk.setVisibility(View.VISIBLE);
		anim = (AnimationDrawable) mIvtalk.getBackground();
		mIvtalk.setOnClickListener(listener);
		mIvtalk.setOnLongClickListener(new OnLongClickListener() {

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
		mVVli = (RelativeLayout) this.findViewById(R.id.mRlwhatshow);
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

		mIvtalk.setOnTouchListener(new OnTouchListener() {

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
		});

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
				startActivity(new Intent(getApplicationContext(),
						TiChuZiXun.class));
				break;
			default:
				break;
			}
		}
	};

	//七牛的
	private void initgetQiNiu()
	{}

	
	
	
}
