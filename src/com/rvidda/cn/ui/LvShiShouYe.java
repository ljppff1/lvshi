package com.rvidda.cn.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.dian.diabetes.widget.listview.PullRefListView;
import com.dian.diabetes.widget.listview.PullRefListView.IXListViewListener;
import com.jauker.widget.BadgeView;
import com.rvidda.cn.AppManager;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.adapter.LvShiShouYeAdapter1;
import com.rvidda.cn.domain.Items;
import com.rvidda.cn.domain.LSSYList;
import com.rvidda.cn.ui.ShouYe.Myadapter;
import com.rvidda.cn.utils.DateUtil;

public class LvShiShouYe extends BaseActivity {
	private TextView mTvde;
	private RelativeLayout mRlLogin;
	private RelativeLayout mRll1;
	private TextView mTvcode;
	private CountDownTimer timer;
	private View mVnum;
	private List<LSSYList> data = new ArrayList<LSSYList>();
	private List<LSSYList> pageList = new ArrayList<LSSYList>();

	private PullRefListView listView;
	private long curentTime;
	private LvShiShouYeAdapter1 adapter;
	private ImageView mBtn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lvshishouye);
		initView();
		initgetziliao();
	}

	private void initgetziliao()
	{
	
		Map<String, Object> params = new HashMap<String, Object>();
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.WeiDaliebiao, "get", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
							JSONObject jsonObj = new JSONObject(json);
                            org.json.JSONArray jsub =jsonObj.getJSONArray("subjects");
                            for(int i=0;i<jsub.length();i++){
                            	LSSYList zx =new LSSYList();
                            	zx.setName(((JSONObject)jsub.get(i)).getString("title"));
                            	zx.setUserid(((JSONObject)jsub.get(i)).getJSONObject("user").getString("id"));
                            	zx.setPhoto(((JSONObject)jsub.get(i)).getJSONObject("user").getString("avatar_url"));
                            	str.clear();
                            	org.json.JSONArray labels = ((JSONObject)jsub.get(i)).getJSONArray("labels");
                            	for(int j=0;j<labels.length();j++){
                            		str.add(((JSONObject)labels.get(j)).getString("name"));
                            	}
                            	zx.setBiaoqian(str);
                            	pageList.add(zx);
                            }
								data.clear();
							    data.addAll(pageList);
								listView.setPullLoadEnable(false);
							
							onLoad();

							
							}else{
			                       Toast.makeText(getApplicationContext(), R.string.log6, 0).show();
										}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
	
	

	
	private void initView() {
		mVnum = (View) this.findViewById(R.id.mVnum);
		BadgeView badgeView = new com.jauker.widget.BadgeView(LvShiShouYe.this);
		badgeView.setTargetView(mVnum);
		badgeView.setBackground(1111, Color.RED);
		badgeView.setGravity(Gravity.TOP | Gravity.RIGHT);
		badgeView.setTextSize(10);
		badgeView.setBadgeCount(6);
		mBtn_back =(ImageView)this.findViewById(R.id.mBtn_back);
		mBtn_back.setOnClickListener(listener);
		
		listView = (com.dian.diabetes.widget.listview.PullRefListView) this
				.findViewById(R.id.news_list);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0 || position == -1) {
					return;
				}
				if (position == data.size() + 1) {
					initgetziliao();
				}
			}
		});
		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				curentTime = System.currentTimeMillis();
				initgetziliao();
			}

			@Override
			public void onLoadMore() {
				initgetziliao();
			}
		});

		adapter = new LvShiShouYeAdapter1(LvShiShouYe.this, data);
		listView.setPullLoadEnable(false);
		listView.setAdapter(adapter);
		listView.onListRefresh();

	}

	private List<String> str = new ArrayList<String>();

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};

	private void loadData(final boolean state) {

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				str.clear();
				str.add("标签1");
				str.add("标签2");
				str.add("标签3");
				str.add("标签5");
				str.add("标签6");
				str.add("asdfjjoiajwoiefjawf");
				str.add("胖小虎");
				str.add("在在在在在在在在在在在在");
				str.add("标签3");
				str.add("标签5");
				str.add("标签3");
				str.add("标签5");

				pageList.clear();
				for (int i = 0; i < 10; i++) {
					LSSYList zx = new LSSYList();
					zx.setBiaoqian(str);
					zx.setName("Allen");
					pageList.add(zx);
				}
				if (state) {
					data.clear();
				}
				data.addAll(pageList);
				if (pageList.size() < 10) {
					listView.setPullLoadEnable(false);
				} else {
					listView.setPullLoadEnable(true);
				}
				onLoad();
			}
		}, 2000);

	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(DateUtil.getNowTime());
	}

	OnClickListener listener = new OnClickListener() {

		private ProgressDialog dialog;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mBtn_back:
				startActivity(new Intent(getApplicationContext(), UserSetting.class));
				
				break;
			case R.id.mRlLogin:

				break;

			default:
				break;
			}
		}

	};

}
