package com.rvidda.cn.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fanxin.app.fx.ChatActivity;
import com.rvidda.cn.AppManager;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.adapter.ZXAdapter1;
import com.rvidda.cn.domain.ZXXiaoXi;
import com.rvidda.cn.utils.Media;

public class ZiXunLook extends BaseActivity {


	private ImageView mBtn_back;
	private LinearLayout mRltt2;
	private ArrayList<ZXXiaoXi> listzxxx;
	private String ID;
	private String NAME;
	private TextView mtv1;
	private ListView list;
	private Media media;
	private ZXAdapter1 zxxiaoxi;
	private String HXUSER;
	private String PHOTO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zixunlook);
		media =new Media();
		listzxxx =new ArrayList<ZXXiaoXi>();
        ID =getIntent().getStringExtra("ID");
        HXUSER =getIntent().getStringExtra("HXUSER");
        PHOTO =getIntent().getStringExtra("PHOTO");
        NAME=getIntent().getStringExtra("NAME");
		initView();
		
		initgetZiXunLook();
	}

	private void initView() {
		mtv1 =(TextView)this.findViewById(R.id.mtv1);
		mtv1.setText(NAME);		
		mBtn_back =(ImageView)this.findViewById(R.id.mBtn_back);
		mBtn_back.setOnClickListener(listener);
		mRltt2=(LinearLayout)this.findViewById(R.id.mRltt2);
		mRltt2.setOnClickListener(listener);
		
		list =(ListView)this.findViewById(R.id.list);
		zxxiaoxi =new ZXAdapter1(getApplicationContext(), listzxxx,media);
		list.setAdapter(zxxiaoxi);

	}
	

	/**
	 *得到咨询信息
	 */
	private void initgetZiXunLook()
	{
	
		Map<String, Object> params = new HashMap<String, Object>();
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.HOST+"/subjects/"+ID, "get", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
			/**
{"subject":{"id":14,"title":"33","created_at":"2015-11-16 17:44:20","updated_at":"2015-12-28 21:12:26","
user_businesses":[],"lawyers":[],"messages":[{"id":30,"body":"bbbbbbbbbb","type":"TextMessage","echo":
null,"created_at":"2015-11-16 17:44:20"},{"id":162,"body":"http://7u2gfi.com1.z0.glb.clouddn.com/
20151229142429599088942-57404430","type":"VoiceMessage","echo":null,"created_at":"2015-12-29 14:24:30"}],"labels"
:[],"order":{"id":238,"state":"init","uuid":"40SR-N6IF-BRP0","total_price":"0.01"}}}			 */
							JSONObject jsonObj = new JSONObject(json);
							JSONObject subject = jsonObj.getJSONObject("subject");
							JSONArray messages = subject.getJSONArray("messages");
							for(int i=0;i<messages.length();i++){
							  ZXXiaoXi zx =new ZXXiaoXi();
								 ((JSONObject)messages.get(i)).getString("body");
								 ((JSONObject)messages.get(i)).getString("type");
								 ((JSONObject)messages.get(i)).getString("created_at");
							  
								 zx.setKeyId("");
								 zx.setMtype(((JSONObject)messages.get(i)).getString("type"));
								 zx.setMtext(((JSONObject)messages.get(i)).getString("body"));
								 zx.setMfilelocal("");
								 listzxxx.add(zx);
							}
							zxxiaoxi.notifyDataSetChanged();
							}else{
			                       Toast.makeText(getApplicationContext(), R.string.log6, 0).show();
										}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mBtn_back:
			AppManager.getAppManager().finishActivity();	
				break;
			case R.id.mRltt2:
				initChangeZX();
				break;
			default:
				break;
			}
			
		}
	};
    

	//修改咨询状态
	private void initChangeZX(){
		Map<String, Object> params = new HashMap<String, Object>();
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.HOST+"/workroom/subject_lawyers/"+ID+"/accept", "put", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
							JSONObject jsonObj = new JSONObject(json);
							Intent intent = new Intent();
							intent.putExtra("SUBJECT", ID);
							intent.putExtra("userId", HXUSER);
							intent.putExtra("userAvatar",PHOTO);
							intent.putExtra("userNick", NAME);
							intent.putExtra("PHOTO", PHOTO);
							intent.setClass(getApplicationContext(), ChatActivity.class);
							startActivity(intent);
							AppManager.getAppManager().finishActivity();
							}else{
			                Toast.makeText(getApplicationContext(), R.string.log10, 0).show();
								}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

    
}
