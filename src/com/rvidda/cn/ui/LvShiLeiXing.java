package com.rvidda.cn.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rvidda.cn.AppManager;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.domain.Items;
import com.rvidda.cn.utils.LoadingDialog;

public class LvShiLeiXing extends BaseActivity {

	private ImageView mBtn_back;
	private GridView mGv1;
	private ArrayList<Items> list;
	private RelativeLayout mRLL2;
   private List<Integer> listint =new ArrayList<Integer>();
   private LoadingDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ls_leixing);
	     dialog =new LoadingDialog(LvShiLeiXing.this, "加载数据，请稍后");

		initView();

	}

	private void initView() {
		list=new ArrayList<Items>();
        list.clear();
        mRLL2 =(RelativeLayout)this.findViewById(R.id.mRLL2);
        mRLL2.setOnClickListener(listener);
		mBtn_back =(ImageView)this.findViewById(R.id.mBtn_back);
		mBtn_back.setOnClickListener(listener);
		mGv1 = (GridView) findViewById(R.id.mGvpop1);
		mGv1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
		dialog.show();
		mRLL2.setVisibility(View.GONE);
		initgetguanzhubiaoqian();
		
	
	}
	private void initgetBiaoqian()
	{
	
		Map<String, Object> params = new HashMap<String, Object>();
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.LABELS, "get", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					private Myadapter adapter;

					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
							JSONObject jsonObj = new JSONObject(json);
							org.json.JSONArray ja =jsonObj.getJSONArray("labels");
							for(int i=0;i<ja.length();i++){
							JSONObject jb =	(JSONObject) ja.get(i);
						    org.json.JSONArray cd =	jb.getJSONArray("children");
						    for(int j=0;j<cd.length();j++){
						    	JSONObject jj =cd.getJSONObject(j);
								Items items = new Items();
								items.setItems(jj.getString("name"));
								items.setId(jj.getInt("id"));
								if(listint.size()>0){
								for(int k=0;k<listint.size();k++){
									if(String.valueOf(jj.getInt("id")).equals(listint.get(k)+"")){
								items.setFlag(true);
								break ;
									}else{
										items.setFlag(false);
									}
								}
								}else{
									items.setFlag(false);
								}
								list.add(items);
						    }
							}
							adapter = new Myadapter();
							mGv1.setAdapter(adapter);
							mRLL2.setVisibility(View.VISIBLE);

							if(dialog.isShowing()){
								dialog.cancel();
							}

							}else{
			                       Toast.makeText(LvShiLeiXing.this, R.string.log6, 0).show();
									if(dialog.isShowing()){
										dialog.cancel();
									}

										}
						} catch (JSONException e) {
							if(dialog.isShowing()){
								dialog.cancel();
							}

							e.printStackTrace();
						}
					}
				});
	}
	

	class Holder {
		TextView mTvg1;
		RelativeLayout mRlg1;
		ImageView mIvg1;
	}

	class Myadapter extends BaseAdapter {
		@SuppressLint("NewApi")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

				convertView = LayoutInflater.from(LvShiLeiXing.this).inflate(
						R.layout.item_gridview_1, null);
				RelativeLayout mRlg1 = (RelativeLayout) convertView
						.findViewById(R.id.mRlg1);
				final TextView mTvg1 = (TextView) convertView.findViewById(R.id.mTvg1);
				final ImageView mIvg1 = (ImageView) convertView.findViewById(R.id.mIvg1);


			mRlg1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Items item = list.get(position);
					if (item.getFlag()) {
						mTvg1.setTextColor(LvShiLeiXing.this.getResources().getColor(
								R.color.black));
						mIvg1.setImageResource(R.drawable.biaoqiandise);
						item.setFlag(false);
					} else {
						item.setFlag(true);
						mTvg1.setTextColor(LvShiLeiXing.this.getResources().getColor(
								R.color.white));
						mIvg1.setImageResource(R.drawable.xuanzhong);

					}
				}
			});

			if (list.get(position).getFlag()) {
				mTvg1.setTextColor(LvShiLeiXing.this.getResources().getColor(
						R.color.white));
				mIvg1.setImageResource(R.drawable.xuanzhong);
			} else {
				mTvg1.setTextColor(LvShiLeiXing.this.getResources().getColor(
						R.color.black));
				mIvg1.setImageResource(R.drawable.biaoqiandise);

			}
			mTvg1.setText(list.get(position).getItems());
			return convertView;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	

	private void initgetguanzhubiaoqian()
	{
	
		Map<String, Object> params = new HashMap<String, Object>();
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.LABELS_GZ, "get", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
							JSONObject jsonObj = new JSONObject(json);
							JSONArray lawyer_labels = jsonObj.getJSONArray("lawyer_labels");
							for(int i=0;i<lawyer_labels.length();i++){
								listint.add(((JSONObject)lawyer_labels.get(i)).getInt("label_id"));
							}
							initgetBiaoqian();
							}else{
			                     Toast.makeText(getApplicationContext(), R.string.log6, 0).show();
									if(dialog.isShowing()){
										dialog.cancel();
									}
		
							}
						} catch (JSONException e) {
							if(dialog.isShowing()){
								dialog.cancel();
							}

							e.printStackTrace();
						}
					
					}
				});
	}

	//修改咨询
		private void postXGLS(){
			try{
	       org.json.JSONArray ja1 =new org.json.JSONArray();
	   		for(int i=0;i<list.size();i++){
	   			if(list.get(i).getFlag())
	   		 ja1.put(list.get(i).getId());
	   		}
	       
	       JSONObject jf =new JSONObject();
	       jf.put("label_ids", ja1);
	       
	      	Map<String, Object> params = new HashMap<String, Object>();
			params.put("json", jf.toString());

			com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.LABELS_XG, "post1", params,
					new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
						@Override
						public void callback(String json) {
							try {
								if(!json.equals("0")){
								JSONObject jsonObj = new JSONObject(json);
	                            Toast.makeText(getApplicationContext(), R.string.log16, 0).show();
	                            AppManager.getAppManager().finishActivity();
								}else{
				                Toast.makeText(getApplicationContext(), R.string.log10, 0).show();
									}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
			}catch(Exception e){
				
			}
		}
		
		
		
	
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
        switch (v.getId()) {
        case R.id.mRLL2:
        	postXGLS();
        	break;
		case R.id.mBtn_back:
			AppManager.getAppManager().finishActivity();
			break;

		default:
			break;
		}			
		}
	};
}
