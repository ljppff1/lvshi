package com.rvidda.cn.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.dian.diabetes.widget.listview.PullRefListView;
import com.dian.diabetes.widget.listview.PullRefListView.IXListViewListener;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.adapter.LvShiShouYeAdapter1;
import com.rvidda.cn.adapter.XiaoXiAdapter;
import com.rvidda.cn.domain.LSSYList;
import com.rvidda.cn.utils.DateUtil;

public class XiaoXi extends BaseActivity{
	private List<com.rvidda.cn.domain.XiaoXi> data = new ArrayList<com.rvidda.cn.domain.XiaoXi>();
	private List<com.rvidda.cn.domain.XiaoXi> pageList = new ArrayList<com.rvidda.cn.domain.XiaoXi>();
	private PullRefListView listView;
	private long curentTime;
	private XiaoXiAdapter adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiaoxi);
		   
		 initView();
		 
	}

	private void initView() {
		
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

		adapter = new XiaoXiAdapter(XiaoXi.this, data);
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
				pageList.clear();
				for (int i = 0; i < 10; i++) {
					com.rvidda.cn.domain.XiaoXi zx = new com.rvidda.cn.domain.XiaoXi();
					zx.setContent("消息免费体验，，，，，"+i);
					zx.setTime(i+"分钟前");
					zx.setTitle("消息免费体验，，，d，，"+i);
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
		
		@Override
		public void onClick(View v) {
		switch (v.getId()) {
		
		default:
			break;
		}
		}
	};

}
