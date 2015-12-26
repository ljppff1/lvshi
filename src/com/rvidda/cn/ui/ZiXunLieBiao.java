package com.rvidda.cn.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dian.diabetes.widget.listview.PullRefListView;
import com.dian.diabetes.widget.listview.PullRefListView.IXListViewListener;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.adapter.ZixunLieBiaoAdapter;
import com.rvidda.cn.domain.ZXList;
import com.rvidda.cn.utils.DateUtil;

public class ZiXunLieBiao extends BaseActivity {
	private TextView mTvde;
	private RelativeLayout mRlLogin;
	private RelativeLayout mRll1;
	private TextView mTvcode;
	private CountDownTimer timer;
	private PullRefListView listView;
	private long curentTime;
	private List<ZXList> data = new ArrayList<ZXList>();
	private List<ZXList> pageList = new ArrayList<ZXList>();
    
	private ZixunLieBiaoAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zixunliebiao);

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

		adapter = new ZixunLieBiaoAdapter(ZiXunLieBiao.this, data);
		listView.setPullLoadEnable(false);
		listView.setAdapter(adapter);
		listView.onListRefresh();
		loadData(true);

	}

	private List<String> str = new ArrayList<String>();
	private List<String> num = new ArrayList<String>();
   
	private Handler handler =new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		};
	};
	private void loadData(final boolean state) {
       		
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				str.clear();
				num.clear();
				num.add("3");
				num.add("4");
				num.add("8");
				num.add("12");
				str.add("王律师");
				str.add("李律师");
				str.add("张律师");
				str.add("红律师");
				pageList.clear();
				for (int i = 0; i < 10; i++) {
					ZXList zx = new ZXList();
					zx.setName(str);
					zx.setNumber(num);

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

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlLogin:
				startActivity(new Intent(ZiXunLieBiao.this, ShouYe.class));
				break;

			default:
				break;
			}
		}
	};

}
