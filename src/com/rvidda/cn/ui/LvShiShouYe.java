package com.rvidda.cn.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dian.diabetes.widget.listview.PullRefListView;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.exceptions.EaseMobException;
import com.fanxin.app.utils.SmileUtils;
import com.jauker.widget.BadgeView;
import com.rvidda.cn.AppManager;
import com.rvidda.cn.R;
import com.rvidda.cn.adapter.LvShiShouYeAdapter1;
import com.rvidda.cn.adapter.LvShiShouYeAdapter2;
import com.rvidda.cn.domain.LSSYList;
import com.rvidda.cn.fragment.FragmentListView1;
import com.rvidda.cn.fragment.FragmentListView2;
import com.rvidda.cn.utils.Content;
import com.rvidda.cn.utils.DateUtil;

public class LvShiShouYe extends  FragmentActivity {
	private TextView mTvde;
	private RelativeLayout mRlLogin;
	private RelativeLayout mRll1;
	private TextView mTvcode;
	private CountDownTimer timer;
	private View mVnum;
	private List<LSSYList> data = new ArrayList<LSSYList>();
	private List<LSSYList> data1 = new ArrayList<LSSYList>();
	private List<LSSYList> pageList = new ArrayList<LSSYList>();

	private PullRefListView listView;
	private long curentTime;
	private LvShiShouYeAdapter1 adapter;
	private ImageView mBtn_back;
	private PullRefListView listView1;
	private LvShiShouYeAdapter2 adapter1;
	private List<EMConversation> normal_list = new ArrayList<EMConversation>();
    private List<EMMessage> list_message =new ArrayList<EMMessage>();
    private List<EMMessage> list_message_unread =new ArrayList<EMMessage>();
    //未读数量
    private Map<String, Integer> list_map =new HashMap<String,Integer>();
    //第一条信息
    private Map<String, EMMessage> list_body =new HashMap<String,EMMessage>();
	private RelativeLayout mRlchange;
	private RelativeLayout mRlchange1;
	private ArrayList<Fragment> pages;
	private ViewPager pager;
	private TextView mtv1;
	/**
	 *  subject  
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lvshishouye);
        AppManager.getAppManager().addActivity(LvShiShouYe.this);

		initView();
		//initgetziliao();
	//	getHuanXin();
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
					/**
{"subjects":[{"id":272,"title":"","created_at":"2016-01-10 15:50:14","subject_lawyer":{"id":286,"lawyer_id":10,"state":"pending"},"user":{"id":9,"name":"测试律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160102193859931489298-74616547","hx_user":"hx_9"},"labels":[{"id":8,"name":"交通"},{"id":10,"name":"知识产权"},{"id":5,"name":"合同"},{"id":9,"name":"劳动争议"},{"id":13,"name":"房屋"}]},{"id":271,"title":"","created_at":"2016-01-10 15:49:33","subject_lawyer":{"id":282,"lawyer_id":10,"state":"pending"},"user":{"id":9,"name":"测试律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160102193859931489298-74616547","hx_user":"hx_9"},"labels":[{"id":8,"name":"交通"},{"id":5,"name":"合同"}]},{"id":270,"title":"","created_at":"2016-01-10 15:48:56","subject_lawyer":{"id":274,"lawyer_id":10,"state":"pending"},"user":{"id":11,"name":"龙客户","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160109192743277444979-93111815","hx_user":"hx_11"},"labels":[{"id":8,"name":"交通"},{"id":10,"name":"知识产权"}]},{"id":269,"title":"","created_at":"2016-01-10 15:46:37","subject_lawyer":{"id":270,"lawyer_id":10,"state":"pending"},"user":{"id":11,"name":"龙客户","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160109192743277444979-93111815","hx_user":"hx_11"},"labels":[{"id":8,"name":"交通"},{"id":10,"name":"知识产权"}]},{"id":268,"title":"","created_at":"2016-01-10 15:46:22","subject_lawyer":{"id":265,"lawyer_id":10,"state":"pending"},"user":{"id":11,"name":"龙客户","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160109192743277444979-93111815","hx_user":"hx_11"},"labels":[{"id":3,"name":"婚姻"},{"id":8,"name":"交通"}]},{"id":267,"title":"","created_at":"2016-01-10 15:44:14","subject_lawyer":{"id":262,"lawyer_id":10,"state":"pending"},"user":{"id":11,"name":"龙客户","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160109192743277444979-93111815","hx_user":"hx_11"},"labels":[{"id":10,"name":"知识产权"},{"id":5,"name":"合同"},{"id":10,"name":"知识产权"}]},{"id":266,"title":"","created_at":"2016-01-10 15:43:47","subject_lawyer":{"id":258,"lawyer_id":10,"state":"pending"},"user":{"id":11,"name":"龙客户","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160109192743277444979-93111815","hx_user":"hx_11"},"labels":[{"id":10,"name":"知识产权"},{"id":5,"name":"合同"}]},{"id":262,"title":"","created_at":"2016-01-10 15:35:47","subject_lawyer":{"id":242,"lawyer_id":10,"state":"pending"},"user":{"id":11,"name":"龙客户","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160109192743277444979-93111815","hx_user":"hx_11"},"labels":[{"id":3,"name":"婚姻"},{"id":5,"name":"合同"}]},{"id":259,"title":"","created_at":"2016-01-10 15:28:49","subject_lawyer":{"id":239,"lawyer_id":10,"state":"pending"},"user":{"id":11,"name":"龙客户","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160109192743277444979-93111815","hx_user":"hx_11"},"labels":[{"id":3,"name":"婚姻"},{"id":8,"name":"交通"},{"id":10,"name":"知识产权"},{"id":5,"name":"合同"},{"id":9,"name":"劳动争议"},{"id":3,"name":"婚姻"},{"id":8,"name":"交通"},{"id":3,"name":"婚姻"},{"id":8,"name":"交通"},{"id":10,"name":"知识产权"},{"id":8,"name":"交通"},{"id":10,"name":"知识产权"},{"id":5,"name":"合同"},{"id":8,"name":"交通"},{"id":10,"name":"知识产权"},{"id":5,"name":"合同"}]},{"id":258,"title":"","created_at":"2016-01-10 15:28:11","subject_lawyer":{"id":235,"lawyer_id":10,"state":"pending"},"user":{"id":11,"name":"龙客户","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160109192743277444979-93111815","hx_user":"hx_11"},"labels":[{"id":3,"name":"婚姻"},{"id":8,"name":"交通"},{"id":10,"name":"知识产权"},{"id":5,"name":"合同"},{"id":3,"name":"婚姻"},{"id":8,"name":"交通"},{"id":3,"name":"婚姻"},{"id":8,"name":"交通"},{"id":10,"name":"知识产权"},{"id":8,"name":"交通"},{"id":10,"name":"知识产权"},{"id":5,"name":"合同"},{"id":9,"name":"劳动争议"}]},{"id":257,"title":"","created_at":"2016-01-10 15:27:30","subject_lawyer":{"id":230,"lawyer_id":10,"state":"pending"},"user":{"id":11,"name":"龙客户","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160109192743277444979-93111815","hx_user":"hx_11"},"labels":[{"id":3,"name":"婚姻"},{"id":8,"name":"交通"}]},{"id":256,"title":"","created_at":"2016-01-10 15:27:14","subject_lawyer":{"id":227,"lawyer_id":10,"state":"pending"},"user":{"id":11,"name":"龙客户","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160109192743277444979-93111815","hx_user":"hx_11"},"labels":[{"id":3,"name":"婚姻"}]},{"id":255,"title":"","created_at":"2016-01-10 15:26:38","subject_lawyer":{"id":224,"lawyer_id":10,"state":"pending"},"user":{"id":11,"name":"龙客户","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160109192743277444979-93111815","hx_user":"hx_11"},"labels":[{"id":3,"name":"婚姻"}]},{"id":238,"title":"","created_at":"2016-01-10 10:58:25","subject_lawyer":{"id":216,"lawyer_id":10,"state":"pending"},"user":{"id":10,"name":null,"avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa","hx_user":"hx_10"},"labels":[{"id":3,"name":"婚姻"},{"id":8,"name":"交通"},{"id":5,"name":"合同"}]},{"id":237,"title":"","created_at":"2016-01-10 10:51:39","subject_lawyer":{"id":211,"lawyer_id":10,"state":"pending"},"user":{"id":10,"name":null,"avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa","hx_user":"hx_10"},"labels":[{"id":3,"name":"婚姻"}]},{"id":236,"title":"","created_at":"2016-01-10 10:42:33","subject_lawyer":{"id":206,"lawyer_id":10,"state":"pending"},"user":{"id":10,"name":null,"avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa","hx_user":"hx_10"},"labels":[{"id":3,"name":"婚姻"}]},{"id":230,"title":"","created_at":"2016-01-09 22:04:43","subject_lawyer":{"id":197,"lawyer_id":10,"state":"pending"},"user":{"id":2,"name":"龙律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/hahaha","hx_user":"hx_2"},"labels":[{"id":3,"name":"婚姻"}]},{"id":226,"title":"更后悔过","created_at":"2016-01-09 21:53:07","subject_lawyer":{"id":189,"lawyer_id":10,"state":"pending"},"user":{"id":2,"name":"龙律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/hahaha","hx_user":"hx_2"},"labels":[{"id":8,"name":"交通"}]},{"id":225,"title":"哈哈哈就不","created_at":"2016-01-09 21:51:57","subject_lawyer":{"id":185,"lawyer_id":10,"state":"pending"},"user":{"id":2,"name":"龙律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/hahaha","hx_user":"hx_2"},"labels":[{"id":3,"name":"婚姻"}]},{"id":222,"title":"沪港","created_at":"2016-01-09 21:46:02","subject_lawyer":{"id":181,"lawyer_id":10,"state":"pending"},"user":{"id":2,"name":"龙律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/hahaha","hx_user":"hx_2"},"labels":[{"id":8,"name":"交通"}]},{"id":221,"title":"给哥哥","created_at":"2016-01-09 21:41:29","subject_lawyer":{"id":177,"lawyer_id":10,"state":"pending"},"user":{"id":2,"name":"龙律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/hahaha","hx_user":"hx_2"},"labels":[{"id":8,"name":"交通"}]},{"id":220,"title":"","created_at":"2016-01-09 20:55:51","subject_lawyer":{"id":173,"lawyer_id":10,"state":"pending"},"user":{"id":2,"name":"龙律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/hahaha","hx_user":"hx_2"},"labels":[{"id":8,"name":"交通"}]},{"id":219,"title":"他方法反弹哥哥发微博都没得了你还能不让给","created_at":"2016-01-09 20:08:35","subject_lawyer":{"id":169,"lawyer_id":10,"state":"pending"},"user":{"id":11,"name":"龙客户","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160109192743277444979-93111815","hx_user":"hx_11"},"labels":[{"id":8,"name":"交通"},{"id":5,"name":"合同"}]},{"id":217,"title":"","created_at":"2016-01-09 17:57:01","subject_lawyer":{"id":277,"lawyer_id":10,"state":"pending"},"user":{"id":9,"name":"测试律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160102193859931489298-74616547","hx_user":"hx_9"},"labels":[{"id":8,"name":"交通"},{"id":10,"name":"知识产权"},{"id":9,"name":"劳动争议"},{"id":3,"name":"婚姻"}]},{"id":212,"title":"555err","created_at":"2016-01-09 15:00:25","subject_lawyer":{"id":161,"lawyer_id":10,"state":"pending"},"user":{"id":2,"name":"龙律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/hahaha","hx_user":"hx_2"},"labels":[{"id":3,"name":"婚姻"}]},{"id":180,"title":"","created_at":"2016-01-06 11:14:53","subject_lawyer":{"id":165,"lawyer_id":10,"state":"pending"},"user":{"id":2,"name":"龙律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/hahaha","hx_user":"hx_2"},"labels":[{"id":8,"name":"交通"},{"id":10,"name":"知识产权"}]}]}
id":13,"name":"房屋"},{"id":5,"name":"合同"},{"id":9,"name":"劳动争议"}]},{"id":170,"title":"new","created_at":"2016-01-05 23:12:55","subject_lawyer":{"id":144,"lawyer_id":8,"state":"pending"},"user":{"id":9,"name":"测试律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160102193859931489298-74616547","hx_user":"hx_11","hx_password":"password_11"},"labels":[{"id":8,"name":"交通"}]},{"id":169,"title":"87854684576457","created_at":"2016-01-05 23:02:51","subject_lawyer":{"id":140,"lawyer_id":8,"state":"pending"},"user":{"id":9,"name":"测试律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160102193859931489298-74616547","hx_user":"hx_11","hx_password":"password_11"},"labels":[{"id":3,"name":"婚姻"}]},{"id":168,"title":"12345","created_at":"2016-01-05 23:00:44","subject_lawyer":{"id":136,"lawyer_id":8,"state":"pending"},"user":{"id":9,"name":"测试律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160102193859931489298-74616547","hx_user":"hx_11","hx_password":"password_11"},"labels":[{"id":8,"name":"交通"}]},{"id":167,"title":"testrrrr4","created_at":"2016-01-05 22:48:45","subject_lawyer":{"id":132,"lawyer_id":8,"state":"pending"},"user":{"id":9,"name":"测试律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160102193859931489298-74616547","hx_user":"hx_11","hx_password":"password_11"},"labels":[{"id":3,"name":"婚姻"}]},{"id":163,"title":"Ss ","created_at":"2016-01-05 16:56:22","subject_lawyer":{"id":149,"lawyer_id":8,"state":"pending"},"user":{"id":14,"name":"膏律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20151226113629100453175-54423795","hx_user":"hx_11","hx_password":"password_11"},"labels":[{"id":3,"name":"婚姻"},{"id":8,"name":"交通"},{"id":10,"name":"知识产权"},{"id":11,"name":"税务"},{"id":13,"name":"房屋"},{"id":5,"name":"合同"},{"id":9,"name":"劳动争议"}]}]}					 */
								
								
							JSONObject jsonObj = new JSONObject(json);
							pageList.clear();
                            org.json.JSONArray jsub =jsonObj.getJSONArray("subjects");
                            for(int i=0;i<jsub.length();i++){  
                            	LSSYList zx =new LSSYList();
                            	zx.setSubject_lawyer_id(((JSONObject)jsub.get(i)).getJSONObject("subject_lawyer").getString("id"));
                            	zx.setName(((JSONObject)jsub.get(i)).getString("title"));
                            	String time =((JSONObject)jsub.get(i)).getString("created_at");
                            	zx.setTime(time.substring(time.length()-8, time.length()));
                            	zx.setSubject(((JSONObject)jsub.get(i)).getString("id"));
                            	zx.setUserid(((JSONObject)jsub.get(i)).getJSONObject("user").getString("id"));
                            	zx.setPhoto(((JSONObject)jsub.get(i)).getJSONObject("user").getString("avatar_url"));
                            	zx.setHx_user(((JSONObject)jsub.get(i)).getJSONObject("user").getString("hx_user"));
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
	
	/**
	 * 已答列表
	 */
	private void initgetziliao1()
	{
	
		Map<String, Object> params = new HashMap<String, Object>();
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.YiDaliebiao, "get", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
							JSONObject jsonObj = new JSONObject(json);
                            org.json.JSONArray jsub =jsonObj.getJSONArray("subjects");
                            pageList.clear();
                            for(int i=0;i<jsub.length();i++){
                            	LSSYList zx =new LSSYList();
                            	zx.setName(((JSONObject)jsub.get(i)).getString("title"));
                            	zx.setUserid(((JSONObject)jsub.get(i)).getJSONObject("user").getString("id"));
                            	zx.setPhoto(((JSONObject)jsub.get(i)).getJSONObject("user").getString("avatar_url"));
                                if(list_body.containsKey(((JSONObject)jsub.get(i)).getString("id"))){
                                	EMMessage dd = list_body.get(((JSONObject)jsub.get(i)).getString("id"));
                                		TextMessageBody txtBody = (TextMessageBody) dd.getBody();
                                		Spannable span = SmileUtils
                                				.getSmiledText(getApplicationContext(), txtBody.getMessage());
                                	zx.setSp(span);
                                }else{
                            		Spannable span = SmileUtils
                            				.getSmiledText(getApplicationContext(),"暂无消息");
                                	zx.setSp(span);
                                }
                            	
                            	if(list_map.containsKey(((JSONObject)jsub.get(i)).getString("id"))){
                            		zx.setNumber(list_map.get(((JSONObject)jsub.get(i)).getString("id"))+"");
                            	}else{
                            	zx.setNumber("0");
                            	}
                            	str.clear();
                            	org.json.JSONArray labels = ((JSONObject)jsub.get(i)).getJSONArray("labels");
                            	for(int j=0;j<labels.length();j++){
                            		str.add(((JSONObject)labels.get(j)).getString("name"));
                            	}
                            	zx.setBiaoqian(str);
                            	pageList.add(zx);
                            }
								data1.clear();
							    data1.addAll(pageList);
								listView1.setPullLoadEnable(false);
							
							onLoad1();

							
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
		mRlchange =(RelativeLayout)this.findViewById(R.id.mRlchange);
		mRlchange.setOnClickListener(listener);
		mRlchange1 =(RelativeLayout)this.findViewById(R.id.mRlchange1);
		mRlchange1.setOnClickListener(listener);
		mRlchange.setVisibility(View.VISIBLE);
		mRlchange1.setVisibility(View.GONE);
		mVnum = (View) this.findViewById(R.id.mVnum);
		mBtn_back =(ImageView)this.findViewById(R.id.mBtn_back);
		mBtn_back.setOnClickListener(listener);
		mtv1 =(TextView)this.findViewById(R.id.mtv1);
		pager = (ViewPager) findViewById(R.id.main_viewpager);
		pages = new ArrayList<Fragment>();
		// create new fragments
		pages.add(new FragmentListView1());
		pages.add(new FragmentListView2());

		// set adapter
		pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
					if (arg0 == 0) {
						mRlchange.setVisibility(View.VISIBLE);
						mRlchange1.setVisibility(View.GONE);
						mtv1.setText("可答问题");

					} else {
						mRlchange.setVisibility(View.GONE);
						mRlchange1.setVisibility(View.VISIBLE);
						mtv1.setText("已答问题");
					}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		pager.setCurrentItem(0);

		
	}

	// adapter
	private class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return pages.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return pages.get(arg0);
		}
	}

	private List<String> str = new ArrayList<String>();

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};
	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(DateUtil.getNowTime());
	}
	private void onLoad1() {
		listView1.stopRefresh();
		listView1.stopLoadMore();
		listView1.setRefreshTime(DateUtil.getNowTime());
	}

	OnClickListener listener = new OnClickListener() {

		private ProgressDialog dialog;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlchange1:
				pager.setCurrentItem(0, true);
				mRlchange.setVisibility(View.VISIBLE);
				mRlchange1.setVisibility(View.GONE);
				mtv1.setText("可答问题");
				break;
			case R.id.mRlchange:
				pager.setCurrentItem(1, true);
				mRlchange.setVisibility(View.GONE);
				mRlchange1.setVisibility(View.VISIBLE);
				mtv1.setText("已答问题");
				break;
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
	
	private long exitTime;
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			if((System.currentTimeMillis() - exitTime) > 2000){
				Toast.makeText(getApplicationContext(),"再按一次退出应用", 1).show();
				exitTime = System.currentTimeMillis();
				}else{
				com.rvidda.cn.AppManager.getAppManager().AppExit(getApplicationContext());
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			return true;
			}
		return false;
		}

}
