package com.rvidda.cn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.dian.diabetes.widget.listview.PullRefListView;
import com.dian.diabetes.widget.listview.PullRefListView.IXListViewListener;
import com.rvidda.cn.domain.News;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NewListViewActivity extends Activity {/*

	

	private ArrayList<News> data;
	private long curentTime;
	private PullRefListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_list);
		data = new ArrayList<News>();
		curentTime = System.currentTimeMillis();
		initActivity();
	}

	private void initActivity() {
		listView =(com.dian.diabetes.widget.listview.PullRefListView)this.findViewById(R.id.news_list);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0 || position == -1) {
					return;
				}
				if (position == data.size() + 1) {
					loadData(false);
				} else {
					openDetailFragment(data.get(position - 1));
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
		data.addAll(newsBo.listNews());
		adapter = new NewsAdapter(context, data);
		listView.setPullLoadEnable(false);
		listView.setAdapter(adapter);
		listView.onListRefresh();
		loadData(true);
	}

	private void loadData(final boolean state) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("timestamp", curentTime);
		HttpServiceUtil.request(ContantsUtil.NEWS_LIST, "post", params,
				new HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							JSONObject jsonData = new JSONObject(json);
							if (CheckUtil.checkStatusOk(jsonData.getInt("status"))) {
								JSONObject dataObj = jsonData.getJSONObject("data");
								List<News> pageList = service.convertCommon(dataObj.getString("list"));
								if(state){
									data.clear();
									newsBo.clearData();
									newsBo.saveArray(pageList);
								}
								data.addAll(pageList);
								if(pageList.size() < 20){
									listView.setPullLoadEnable(false);
								}else{
									listView.setPullLoadEnable(true);
								}
								curentTime = dataObj.getLong("timestamp");
							} else {
								ToastTool.showToast(jsonData.getInt("status"),context);
							}
						} catch (JSONException e) {
							Toast.makeText(context, "数据解析出错",
									Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
						onLoad();
					}
				});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back_btn:
			finish();
			break;
		case R.id.collect_btn:
			openFavorateFragment();
			break;
		}
	}
	
	private void openFavorateFragment(){
		String tag = "favorate_fragment";
		FragmentManager manager = context.getSupportFragmentManager();
		FavorateFragment tempFragment = (FavorateFragment) context
				.getSupportFragmentManager().findFragmentByTag(tag);
		if (tempFragment == null) {
			tempFragment = FavorateFragment.getInstance();
		}
		if (!tempFragment.isAdded()) {
			tempFragment.show(manager, tag);
		}
	}
	
	private void openDetailFragment(News detail){
		String tag = "news_detail_fragment";
		FragmentManager manager = context.getSupportFragmentManager();
		NewsDetailFragment tempFragment = (NewsDetailFragment) context
				.getSupportFragmentManager().findFragmentByTag(tag);
		if (tempFragment == null) {
			tempFragment = NewsDetailFragment.getInstance();
		}
		tempFragment.setNews(detail);
		if (!tempFragment.isAdded()) {
			tempFragment.show(manager, tag);
		}
	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(DateUtil.getNowTime());
	}
*/}
