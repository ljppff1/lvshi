package com.rvidda.cn.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
	private ImageView mIvPeople;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zixunliebiao);

		initView();

	}

	private void initView() {
		mIvPeople =(ImageView)this.findViewById(R.id.mIvPeople);
		mIvPeople.setOnClickListener(listener);
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
					getZXLiaobiao(false);
				}
			}
		});
		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				curentTime = System.currentTimeMillis();
				getZXLiaobiao(true);
			}

			@Override
			public void onLoadMore() {
				getZXLiaobiao(false);
			}
		});

		adapter = new ZixunLieBiaoAdapter(ZiXunLieBiao.this, data);
		listView.setPullLoadEnable(false);
		listView.setAdapter(adapter);
		listView.onListRefresh();
		getZXLiaobiao(true);

	}

	private List<String> str = new ArrayList<String>();
	private List<String> num = new ArrayList<String>();

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};

	//咨询列表
	public void getZXLiaobiao(final boolean state){		
		Map<String, Object> params = new HashMap<String, Object>();
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.ZXLiaoBiao, "get", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
/**
 * {"subjects":[{"id":179,"title":"把考虑考虑","user_id":15,"created_at":"2016-01-06 10:27:07","lawyers":[]},
 * {"id":178,"title":"lj","user_id":15,"created_at":"2016-01-06 10:16:46","lawyers":[]},{
 * "id":177,"title":"lj","user_id":15,"created_at":"2016-01-06 10:02:24","lawyers":[]},
"id":175,"title":"lj","user_id":15,"created_at":"2016-01-06 09:59:02","lawyers":[]}]}
 */
							JSONObject jsonObj = new JSONObject(json);
							org.json.JSONArray jas =jsonObj.getJSONArray("subjects");
							pageList.clear();

							for(int i=0;i<jas.length();i++){
								ZXList zx = new ZXList();
								zx.setTitle(((JSONObject)jas.get(i)).getString("title"));
								zx.setTime(((JSONObject)jas.get(i)).getString("created_at"));
								org.json.JSONArray lawyers = ((JSONObject)jas.get(i)).getJSONArray("lawyers");
								ArrayList<String> templist1 = new ArrayList<String>();
								ArrayList<String> templist2 = new ArrayList<String>();
								ArrayList<String> templist3 = new ArrayList<String>();
								for(int j=0;j<lawyers.length();j++){
									templist1.add(((JSONObject)lawyers.get(j)).getString("real_name"));
									templist2.add(((JSONObject)lawyers.get(j)).getString("avatar_url"));
								if(j<2){
									templist3.add("4");
								}else{
									templist3.add("0");
								}
								}
								zx.setPhoto(templist2);
								zx.setName(templist1);
								zx.setNumber(templist3);
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
					listView.setPullLoadEnable(false);
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
			case R.id.mIvPeople:
				startActivity(new Intent(ZiXunLieBiao.this, UserSetting.class));
				break;
			default:
				break;
			}
		}
	};

}
