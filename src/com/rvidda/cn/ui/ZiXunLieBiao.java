package com.rvidda.cn.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.exceptions.EaseMobException;
import com.jauker.widget.BadgeView;
import com.rvidda.cn.AppManager;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.adapter.ZixunLieBiaoAdapter;
import com.rvidda.cn.domain.ZXList;
import com.rvidda.cn.utils.Content;
import com.rvidda.cn.utils.DateUtil;

public class ZiXunLieBiao extends BaseActivity {
	private PullRefListView listView;
	private List<ZXList> data = new ArrayList<ZXList>();
	private List<ZXList> pageList = new ArrayList<ZXList>();

	private ZixunLieBiaoAdapter adapter;
	private ImageView mIvPeople;
	private ImageView mIvadd;
	private ImageView mBtn_back;
	private List<EMConversation> normal_list = new ArrayList<EMConversation>();
    private List<EMMessage> list_message =new ArrayList<EMMessage>();
    private List<EMMessage> list_message_unread =new ArrayList<EMMessage>();
    //未读数量
    private Map<String, Integer> list_map =new HashMap<String,Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zixunliebiao);

		initView();
		getHuanXin();
	}
	
	private void getHuanXin(){
		normal_list.addAll(loadConversationsWithRecentChat());
        	for(int j=0;j<normal_list.size();j++){
        		list_message.addAll(normal_list.get(j).getAllMessages());
        	}        	        
       for(int i=0;i<list_message.size();i++){
    	   if(list_message.get(i).isUnread()){
			list_message_unread.add(list_message.get(i));
		}  
       }
       for(int i=0;i<list_message_unread.size();i++){
    	   try {
				if(list_map.containsKey(list_message_unread.get(i).getStringAttribute(Content.SUBJECT))){
					String str =list_message_unread.get(i).getStringAttribute(Content.SUBJECT);
					Integer ii =list_map.get(str);
					list_map.put(str,(ii+1));
				}else{
					list_map.put(list_message_unread.get(i).getStringAttribute(Content.SUBJECT), 1);
				}
			
		} catch (EaseMobException e) {
			e.printStackTrace();
		}
       }
       getZXLiaobiao(true);
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


	private void initView() {
		mBtn_back =(ImageView)this.findViewById(R.id.mBtn_back);
		mBtn_back.setOnClickListener(listener);
		mIvadd =(ImageView)this.findViewById(R.id.mIvadd);
		mIvadd.setOnClickListener(listener);
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
				if(position>0){
					Intent intent =new Intent(getApplicationContext(),TiChuZiXun.class);
					intent.putExtra("ID", data.get(position-1).getSubject());
					intent.putExtra("SELECT","");
					intent.putExtra("FILE", "");
					intent.putExtra("TYPE", "TT");
					startActivity(intent);

				}
				if (position == data.size() + 1) {
					getZXLiaobiao(false);
				}
			}
		});
		listView.setXListViewListener(new IXListViewListener() {

			private long curentTime;

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
		

	}

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
{"subjects":[{"id":245,"title":"","user_id":14,"created_at":"2016-01-10 11:35:30","lawyers":[]},{"id":241,"title":"","user_id":14,"created_at":"2016-01-10 11:22:01","
lawyers":[{"id":6,"user_id":9,"real_name":"sfg","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa","hx_user":"hx_9"}]},{"id":234,"title":"","user_id":14,"created_at":"2016-01-10 10:24:57","lawyers":[{"id":8,"user_id":11,"real_name":"推广","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa","hx_user":"hx_11"}]},
{"id":197,"title":"","user_id":14,"created_at":"2016-01-07 16:45:13","lawyers":[]},{"id":184,"title":"","user_id":14,"created_at":"2016-01-06 16:17:49","lawyers":[]},{"id":183,"title":"","user_id":14,"created_at":"2016-01-06 16:17:25","lawyers":[]},{"id":182,"title":"","user_id":14,"created_at":"2016-01-06 14:42:10","lawyers":[{"id":4,"user_id":2,"real_name":"sfg","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa","hx_user":"hx_2"},{"id":6,"user_id":9,"real_name":"sfg","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa","hx_user":"hx_9"}]},{"id":176,"title":"","user_id":14,"created_at":"2016-01-06 09:59:18","lawyers":[]},{"id":163,"title":"Ss ","user_id":14,"created_at":"2016-01-05 16:56:22","lawyers":[{"id":4,"user_id":2,"real_name":"sfg","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa","hx_user":"hx_2"},{"id":8,"user_id":11,"real_name":"推广","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa","hx_user":"hx_11"}]},{"id":161,"title":"","user_id":14,"created_at":"2016-01-05 16:51:59","lawyers":[]},{"id":160,"title":"","user_id":14,"created_at":"2016-01-05 16:50:52","lawyers":[]},{"id":159,"title":"","user_id":14,"created_at":"2016-01-05 16:22:41","lawyers":[]},{"id":158,"title":"","user_id":14,"created_at":"2016-01-05 16:22:12","lawyers":[]},{"id":157,"title":"","user_id":14,"created_at":"2016-01-05 16:21:58","lawyers":[]},{"id":156,"title":"","user_id":14,"created_at":"2016-01-05 16:21:47","lawyers":[]},{"id":137,"title":"","user_id":14,"created_at":"2016-01-04 17:55:10","lawyers":[]},{"id":87,"title":"","user_id":14,"created_at":"2015-12-29 15:31:07","lawyers":[]},{"id":86,"title":"成功哥哥","user_id":14,"created_at":"2015-12-29 15:10:27","lawyers":[]},{"id":85,"title":"功夫gg","user_id":14,"created_at":"2015-12-29 11:00:34","lawyers":[]},{"id":84,"title":"功夫","user_id":14,"created_at":"2015-12-29 10:59:56","lawyers":[]},{"id":83,"title":"","user_id":14,"created_at":"2015-12-29 10:15:12","lawyers":[]},{"id":81,"title":"","user_id":14,"created_at":"2015-12-27 18:06:21","lawyers":[]},{"id":80,"title":"","user_id":14,"created_at":"2015-12-27 18:05:55","lawyers":[]},{"id":79,"title":"","user_id":14,"created_at":"2015-12-27 10:48:09","lawyers":[]},{"id":77,"title":"","user_id":14,"created_at":"2015-12-26 15:45:45","lawyers":[]}]} 
{"subjects":[{"id":301,"title":"??????","user_id":11,"created_at":"2016-01-11 15:39:56","lawyers":[{"id":4,"user_id":2,"real_name":"sfg","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa","hx_user":"hx_2"},{"id":6,"user_id":9,"real_name":"sfg","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa","hx_user":"hx_9"},{"id":10,"user_id":14,"real_name":"Ada","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa","hx_user":"hx_14"},{"id":16,"user_id":12,"real_name":"覃明圆","avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa","hx_user":"hx_12"}],"order":{"id":423,"state":"init","uuid":"4107-THAG-5HH4","total_price":"0.01"}},{"id":300,"title":"?","user_id":11,"created_at":"2016-01-11 15:36:25","lawyers":[],"order":{"id":422,"state":"init","uuid":"4107-STMU-A2I4","total_price":"0.01"}},{"id":299,"title":"???","user_id":11,"created_at":"2016-01-11 15:35:06","lawyers":[],"order":{"id":421,"state":"init","uuid":"4107-SMD2-C2US","total_price":"0.01"}},{"id":298,"title":"??","user_id":11,"created_at":"2016-01-11 15:34:43","lawyers":[],"order":{"id":420,"state":"init","uuid":"4107-SK5R-2MRS","total_price":"0.01"}},{"id":297,"title":"1","user_id":11,"created_at":"2016-01-11 15:33:59","lawyers":[],"order":{"id":419,"state":"init","uuid":"4107-SG4U-MURG","total_price":"0.01"}},{"id":296,"title":"122222","user_id":11,"created_at":"2016-01-11 15:30:57","lawyers":[],"order":{"id":418,"state":"init","uuid":"4107-RV6R-EGSG","total_price":"0.01"}},{"id":295,"title":"??????","user_id":11,"created_at":"2016-01-11 15:27:31","lawyers":[],"order":{"id":417,"state":"init","uuid":"4107-RC11-UMVK","total_price":"0.01"}},{"id":294,"title":"","user_id":11,"created_at":"2016-01-11 15:25:33","lawyers":[],"order":{"id":416,"state":"init","uuid":"4107-R104-JAG4","total_price":"0.01"}},{"id":293,"title":"","user_id":11,"created_at":"2016-01-11 15:24:33","lawyers":[],"order":{"id":415,"state":"init","uuid":"4107-QRD9-9NQK","total_price":"0.01"}},{"id":292,"title":"","user_id":11,"created_at":"2016-01-11 15:24:23","lawyers":[],"order":{"id":414,"state":"init","uuid":"4107-QQFK-SD5O","total_price":"0.01"}},{"id":291,"title":"??????","user_id":11,"created_at":"2016-01-11 15:23:50","lawyers":[],"order":{"id":413,"state":"init","uuid":"4107-QNC8-PGJS","total_price":"0.01"}},{"id":276,"title":"","user_id":11,"created_at":"2016-01-10 18:14:03","lawyers":[],"order":{"id":398,"state":"init","uuid":"4100-SVUF-E6MG","total_price":"0.01"}},{"id":275,"title":"","user_id":11,"created_at":"2016-01-10 18:13:34","lawyers":[],"order":{"id":397,"state":"init","uuid":"4100-ST7D-R2M4","total_price":"0.01"}},{"id":273,"title":"银行股","user_id":11,"created_at":"2016-01-10 15:58:49","lawyers":[],"order":{"id":395,"state":"init","uuid":"4100-5C84-29CK","total_price":"0.01"}},{"id":270,"title":"","user_id":11,"created_at":"2016-01-10 15:48:56","lawyers":[]},{"id":269,"title":"","user_id":11,"created_at":"2016-01-10 15:46:37","lawyers":[]},{"id":268,"title":"","user_id":11,"created_at":"2016-01-10 15:46:22","lawyers":[]},{"id":267,"title":"","user_id":11,"created_at":"2016-01-10 15:44:14","lawyers":[]},{"id":266,"title":"","user_id":11,"created_at":"2016-01-10 15:43:47","lawyers":[]},{"id":265,"title":"","user_id":11,"created_at":"2016-01-10 15:40:52","lawyers":[]},{"id":264,"title":"","user_id":11,"created_at":"2016-01-10 15:38:46","lawyers":[]},{"id":263,"title":"","user_id":11,"created_at":"2016-01-10 15:36:01","lawyers":[]},{"id":262,"title":"","user_id":11,"created_at":"2016-01-10 15:35:47","lawyers":[]},{"id":259,"title":"","user_id":11,"created_at":"2016-01-10 15:28:49","lawyers":[]},{"id":258,"title":"","user_id":11,"created_at":"2016-01-10 15:28:11","lawyers":[]}]}
							*/
							JSONObject jsonObj = new JSONObject(json);
							org.json.JSONArray jas =jsonObj.getJSONArray("subjects");
							pageList.clear();
							/*
							intent.putExtra("SUBJECT", "222");
							intent.putExtra("userId", "hx_1");
							intent.putExtra("userAvatar","dd");
							intent.putExtra("userNick", "temper");
							intent.putExtra("PHOTO", "dd");*/

							for(int i=0;i<jas.length();i++){
								ZXList zx = new ZXList();
								zx.setTitle(((JSONObject)jas.get(i)).getString("title"));
								zx.setSubject(((JSONObject)jas.get(i)).getString("id"));
								zx.setTime(((JSONObject)jas.get(i)).getString("created_at"));
								org.json.JSONArray lawyers = ((JSONObject)jas.get(i)).getJSONArray("lawyers");
								ArrayList<String> templist1 = new ArrayList<String>();
								ArrayList<String> templist2 = new ArrayList<String>();
								ArrayList<String> templist3 = new ArrayList<String>();
								ArrayList<String> templist4 = new ArrayList<String>();
								ArrayList<String> templist5 = new ArrayList<String>();
								for(int j=0;j<lawyers.length();j++){
									templist1.add(((JSONObject)lawyers.get(j)).getString("real_name"));
									templist2.add(((JSONObject)lawyers.get(j)).getString("avatar_url"));
								if(list_map.containsKey(((JSONObject)jas.get(i)).getInt("id"))){
									templist3.add(list_map.get(((JSONObject)jas.get(i)).getInt("id"))+"");
								}else{
									templist3.add("0");
								}
								templist4.add(((JSONObject)lawyers.get(j)).getString("hx_user"));
								templist5.add(((JSONObject)lawyers.get(j)).getString("id"));
								}
								zx.setPhoto(templist2);
								zx.setName(templist1);
								zx.setNumber(templist3);
								zx.setHx_user(templist4);
								zx.setSubjectlist(templist5);
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


	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(DateUtil.getNowTime());
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mBtn_back:
				AppManager.getAppManager().finishActivity();
				break;
			case R.id.mIvadd:
				Intent intent =new Intent(getApplicationContext(),TiChuZiXun.class);
				intent.putExtra("ID", "");
				intent.putExtra("SELECT","");
				intent.putExtra("FILE", "");
				intent.putExtra("TYPE", "");
				startActivity(intent);

				break;
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
