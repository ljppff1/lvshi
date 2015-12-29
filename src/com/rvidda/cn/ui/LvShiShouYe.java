package com.rvidda.cn.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.dian.diabetes.widget.listview.PullRefListView;
import com.dian.diabetes.widget.listview.PullRefListView.IXListViewListener;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;
import com.fanxin.app.Constant;
import com.fanxin.app.DemoApplication;
import com.fanxin.app.DemoHXSDKHelper;
import com.fanxin.app.fx.LoginActivity;
import com.fanxin.app.fx.others.LoadDataFromServer;
import com.fanxin.app.fx.others.LoadDataFromServer.DataCallBack;
import com.jauker.widget.BadgeView;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.adapter.LvShiShouYeAdapter;
import com.rvidda.cn.adapter.LvShiShouYeAdapter1;
import com.rvidda.cn.adapter.ZixunLieBiaoAdapter;
import com.rvidda.cn.domain.LSSYList;
import com.rvidda.cn.domain.ZXList;
import com.rvidda.cn.utils.DateUtil;

public class LvShiShouYe extends BaseActivity{
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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lvshishouye);
		 initView();
		 
	}

	private void initView() {
		mVnum =(View)this.findViewById(R.id.mVnum);
		BadgeView badgeView = new com.jauker.widget.BadgeView(LvShiShouYe.this);
		badgeView.setTargetView(mVnum);
		badgeView.setBackground(1111, Color.RED);
		badgeView.setGravity(Gravity.TOP|Gravity.RIGHT);
		badgeView.setTextSize(10);
		badgeView.setBadgeCount(6);

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
					loadData(false);
				}
			}
		});
		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				curentTime = System.currentTimeMillis();
				loadData(true);
			}

			@Override
			public void onLoadMore() {
				loadData(false);
			}
		});

		adapter = new LvShiShouYeAdapter1(LvShiShouYe.this, data);
		listView.setPullLoadEnable(false);
		listView.setAdapter(adapter);
		listView.onListRefresh();
		loadData(true);

	}

	private List<String> str = new ArrayList<String>();
   
	private Handler handler =new Handler(){
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

	OnClickListener listener =new OnClickListener() {
		
		private ProgressDialog dialog;

		@Override
		public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mRlLogin:
			
			break;

		default:
			break;
		}
		}

	};
            
            
            
}
