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
import android.text.Spannable;
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
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.exceptions.EaseMobException;
import com.fanxin.app.fx.ChatActivity;
import com.fanxin.app.utils.SmileUtils;
import com.jauker.widget.BadgeView;
import com.rvidda.cn.AppManager;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.adapter.LvShiShouYeAdapter1;
import com.rvidda.cn.adapter.LvShiShouYeAdapter2;
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
	/**
	 *  subject  
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lvshishouye);
		initView();
		initgetziliao();
		getHuanXin();
	}

	private void getHuanXin(){
		normal_list.addAll(loadConversationsWithRecentChat());
        for(int i=0;i<normal_list.size();i++){
        	for(int j=0;j<normal_list.size();j++){
        		list_message.addAll(normal_list.get(j).getAllMessages());
        	}
        }
        
        //得到所有的第一条的那个信息
        for(int i=0;i<list_message.size();i++){
				try {
					list_body.put(list_message.get(i).getStringAttribute("subject"), list_message.get(i));
				} catch (EaseMobException e) {
					e.printStackTrace();
				}

        }
        
       for(int i=0;i<list_message.size();i++){
    	   if(list_message.get(i).isUnread()){
			list_message_unread.add(list_message.get(i));
		}  
       }
       if(list_message_unread.size()>0){
		BadgeView badgeView = new com.jauker.widget.BadgeView(LvShiShouYe.this);
		badgeView.setTargetView(mVnum);
		badgeView.setBackground(1111, Color.RED);
		badgeView.setGravity(Gravity.TOP | Gravity.RIGHT);
		badgeView.setTextSize(10);
		badgeView.setBadgeCount(list_message_unread.size());
       }
       for(int i=0;i<list_message_unread.size();i++){
    	   try {
             
			for(int j=0;j<list_map.size();j++){
				if(list_map.containsKey(list_message_unread.get(i).getStringAttribute("SUBJECT"))){
				list_map.put(list_message_unread.get(i).getStringAttribute("SUBJECT"), list_map.get(j).getInteger(list_message_unread.get(i).getStringAttribute("SUBJECT"))+1);
				}else{
					list_map.put(list_message_unread.get(i).getStringAttribute("SUBJECT"), 1);
				}
			}
		} catch (EaseMobException e) {
			e.printStackTrace();
		}
       }
       initgetziliao1();

	}
	/**
	 * 获取所有会话
	 * 
	 * @param context
	 * @return +
	 */
	private List<EMConversation> loadConversationsWithRecentChat() {
		// 获取所有会话，包括陌生人
		Hashtable<String, EMConversation> conversations = EMChatManager
				.getInstance().getAllConversations();
		List<EMConversation> list = new ArrayList<EMConversation>();
		// 置顶列表再刷新一次

		// 过滤掉messages seize为0的conversation
		for (EMConversation conversation : conversations.values()) {

			if (conversation.getAllMessages().size() != 0) {
				// 不在置顶列表里面
					list.add(conversation);
			}

		}
		return list;
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
{"subjects":[{"id":186,"title":"今天就让他自己知道","created_at":"2016-01-07 10:38:24"
,"subject_lawyer":{"id":156,"lawyer_id":8,"state":"pending"},"
user":{"id":2,"name":"test222","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/hahaha",
"hx_user":"hx_11","hx_password":"password_11"},"labels":[{"id":3,"name":"婚姻"},
{"id":8,"name":"交通"},{"id":10,"name":"知识产权"},{"id":11,"name":"税务"},{"id":13,"name":"房屋"},{"id":5,"name":"合同"},{"id":9,"name":"劳动争议"}]},
{"id":182,"title":"","created_at":"2016-01-06 14:42:10","subject_lawyer":
{"id":153,"lawyer_id":8,"state":"pending"},"user":{"id":14,"name":"膏律师","avatar_url":"http://7u2gfi.com1
.z0.glb.clouddn.com/20151226113629100453175-54423795","hx_user":"hx_11","hx_password":"password_11"},"la
bels":[{"id":3,"name":"婚姻"},{"id":8,"name":"交通"},{"id":10,"name":"知识产权"},{"id":11,"name":"税务"},{"
id":13,"name":"房屋"},{"id":5,"name":"合同"},{"id":9,"name":"劳动争议"}]},{"id":170,"title":"new","created_at":"2016-01-05 23:12:55","subject_lawyer":{"id":144,"lawyer_id":8,"state":"pending"},"user":{"id":9,"name":"测试律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160102193859931489298-74616547","hx_user":"hx_11","hx_password":"password_11"},"labels":[{"id":8,"name":"交通"}]},{"id":169,"title":"87854684576457","created_at":"2016-01-05 23:02:51","subject_lawyer":{"id":140,"lawyer_id":8,"state":"pending"},"user":{"id":9,"name":"测试律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160102193859931489298-74616547","hx_user":"hx_11","hx_password":"password_11"},"labels":[{"id":3,"name":"婚姻"}]},{"id":168,"title":"12345","created_at":"2016-01-05 23:00:44","subject_lawyer":{"id":136,"lawyer_id":8,"state":"pending"},"user":{"id":9,"name":"测试律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160102193859931489298-74616547","hx_user":"hx_11","hx_password":"password_11"},"labels":[{"id":8,"name":"交通"}]},{"id":167,"title":"testrrrr4","created_at":"2016-01-05 22:48:45","subject_lawyer":{"id":132,"lawyer_id":8,"state":"pending"},"user":{"id":9,"name":"测试律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20160102193859931489298-74616547","hx_user":"hx_11","hx_password":"password_11"},"labels":[{"id":3,"name":"婚姻"}]},{"id":163,"title":"Ss ","created_at":"2016-01-05 16:56:22","subject_lawyer":{"id":149,"lawyer_id":8,"state":"pending"},"user":{"id":14,"name":"膏律师","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/20151226113629100453175-54423795","hx_user":"hx_11","hx_password":"password_11"},"labels":[{"id":3,"name":"婚姻"},{"id":8,"name":"交通"},{"id":10,"name":"知识产权"},{"id":11,"name":"税务"},{"id":13,"name":"房屋"},{"id":5,"name":"合同"},{"id":9,"name":"劳动争议"}]}]}					 */
							JSONObject jsonObj = new JSONObject(json);
							pageList.clear();
                            org.json.JSONArray jsub =jsonObj.getJSONArray("subjects");
                            for(int i=0;i<jsub.length();i++){
                            	LSSYList zx =new LSSYList();
                            	zx.setSubject_lawyer_id(((JSONObject)jsub.get(i)).getJSONObject("subject_lawyer").getString("id"));
                            	zx.setName(((JSONObject)jsub.get(i)).getString("title"));
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
                                if(list_body.containsKey(((JSONObject)jsub.get(i)).getJSONObject("subject_lawyer").getString("id"))){
                                	EMMessage dd = list_body.get(((JSONObject)jsub.get(i)).getJSONObject("subject_lawyer").getString("id"));
                                		TextMessageBody txtBody = (TextMessageBody) dd.getBody();
                                		Spannable span = SmileUtils
                                				.getSmiledText(getApplicationContext(), txtBody.getMessage());
                                	zx.setSp(span);
                                }else{
                            		Spannable span = SmileUtils
                            				.getSmiledText(getApplicationContext(),"暂无消息");
                                	zx.setSp(span);
                                }
                            	
                            	if(list_map.containsKey(((JSONObject)jsub.get(i)).getJSONObject("subject_lawyer").getString("id"))){
                            		zx.setNumber(list_map.get(((JSONObject)jsub.get(i)).getJSONObject("subject_lawyer").getString("id"))+"");
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
				if(position>0){
					Intent intent=new Intent(getApplicationContext(), ZiXunLook.class);
					intent.putExtra("ID", data.get(position-1).getSubject_lawyer_id());
					intent.putExtra("NAME", data.get(position-1).getName());
					intent.putExtra("HXUSER", data.get(position-1).getHx_user());
					intent.putExtra("PHOTO", data.get(position-1).getPhoto());
					startActivity(intent);
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

		//已答
		listView1 = (com.dian.diabetes.widget.listview.PullRefListView) this.findViewById(R.id.news_list1);
		listView1.setVisibility(View.GONE);
		listView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0 || position == -1) {
					return;
				}
				if (position == data.size() + 1) {
					initgetziliao1();
				}
				if(position>0){/*
					Intent intent=new Intent(getApplicationContext(), ZiXunLook.class);
					intent.putExtra("ID", data.get(position-1).getSubject_lawyer_id());
					intent.putExtra("NAME", data.get(position-1).getName());
					intent.putExtra("HXUSER", data.get(position-1).getHx_user());
					intent.putExtra("PHOTO", data.get(position-1).getPhoto());
					startActivity(intent);*/
					
					Intent intent = new Intent();
					intent.putExtra("SUBJECT",  data1.get(position-1).getSubject_lawyer_id());
					intent.putExtra("userId", data1.get(position-1).getHx_user());
					intent.putExtra("userAvatar",data1.get(position-1).getPhoto());
					intent.putExtra("userNick",  data1.get(position-1).getName());
					intent.putExtra("PHOTO", data1.get(position-1).getPhoto());
					intent.setClass(getApplicationContext(), ChatActivity.class);
					
				}
			}
		});
		listView1.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				curentTime = System.currentTimeMillis();
				initgetziliao1();
			}

			@Override
			public void onLoadMore() {
				initgetziliao1();
			}
		});
		adapter1 = new LvShiShouYeAdapter2(LvShiShouYe.this, data1);
		listView1.setPullLoadEnable(false);
		listView1.setAdapter(adapter1);
	//	listView1.onListRefresh();

		
		
		
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
				initgetziliao();
				mRlchange.setVisibility(View.VISIBLE);
				mRlchange1.setVisibility(View.GONE);
				listView1.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				break;
			case R.id.mRlchange:
				getHuanXin();
				mRlchange.setVisibility(View.GONE);
				mRlchange1.setVisibility(View.VISIBLE);
				listView1.setVisibility(View.VISIBLE);
				listView.setVisibility(View.GONE);
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

}
