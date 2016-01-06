package com.rvidda.cn.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rvidda.cn.R;
import com.rvidda.cn.domain.Items;

public class popWindow1 extends BasePopupWindow {

	private ImageButton mSearch_Button;

	private onSearchBarItemClickListener mOnSearchBarItemClickListener;

	private Context mContext;
	private static String[] listdp1 = { "婚姻", "交通", "税务", "债务", "合同", "房屋",
			"劳动协议", "知识产权", "其它", "婚姻", "更多" };
	private List<Items> list;
	private Myadapter adapter;

	private RelativeLayout mRlw1;
    String[] mStr;
	private GridView mGv1;

	public popWindow1(Context context, int width, int height,String[] str) {
		super(LayoutInflater.from(context).inflate(R.layout.pop_1, null),
				width, height);
		this.mContext = context;
		this.mStr =str;
	}

	@Override
	public void initViews() {
		list=new ArrayList<Items>();
        list.clear();
		mRlw1 = (RelativeLayout) findViewById(R.id.mRlpopw1);
		mRlw1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mOnSearchBarItemClickListener != null) {
					mOnSearchBarItemClickListener.onSearchButtonClick(getsendBiaoqian(),
							getsendBiaoqian1());
					dismiss();
				}

			}
		});

		mGv1 = (GridView) findViewById(R.id.mGvpop1);
		mGv1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
/*		adapter = new Myadapter();
		mGv1.setAdapter(adapter);
*/
		initgetBiaoqian();
	}
	private String getsendBiaoqian1()
	{
		List<Integer> listss =new ArrayList<Integer>();
	  for(int i=0;i<list.size();i++){
		  if(list.get(i).getFlag()){
			  listss.add(list.get(i).getId());
		  }
	  }
	  String str ="";
	  for(int i=0;i<listss.size();i++){
		  if(i<=listss.size()-2){
			str =str +listss.get(i)+",";  
		  }else{
			  str =str +listss.get(i);
		  }
	  }
	  return str;
	}

	private String getsendBiaoqian()
	{
		List<Integer> listss =new ArrayList<Integer>();
	  for(int i=0;i<list.size();i++){
		  if(list.get(i).getFlag()){
			  listss.add(list.get(i).getId());
		  }
	  }
	  String str ="[";
	  for(int i=0;i<listss.size();i++){
		  if(i<=listss.size()-2){
			str =str +listss.get(i)+",";  
		  }else{
				str =str +listss.get(i)+"]";   
		  }
	  }
	  return str;
	}
	private void initgetBiaoqian()
	{
	
		Map<String, Object> params = new HashMap<String, Object>();
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.LABELS, "get", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
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
								for(int k=0;k<mStr.length;k++){
									if(String.valueOf(jj.getInt("id")).equals(mStr[k])){
								items.setFlag(true);
								break ;
									}else{
										items.setFlag(false);
									}
								}
								list.add(items);
						    }
							}
							adapter = new Myadapter();
							mGv1.setAdapter(adapter);

							}else{
			                       Toast.makeText(mContext, R.string.log6, 0).show();
										}
						} catch (JSONException e) {
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

			Holder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item_gridview_1, null);
				holder = new Holder();
				holder.mRlg1 = (RelativeLayout) convertView
						.findViewById(R.id.mRlg1);
				holder.mTvg1 = (TextView) convertView.findViewById(R.id.mTvg1);
				holder.mIvg1 = (ImageView) convertView.findViewById(R.id.mIvg1);
				convertView.setTag(holder);

			} else {
				holder = (Holder) convertView.getTag();
			}
			final TextView mTvg1 = (TextView) convertView
					.findViewById(R.id.mTvg1);
			final ImageView mIvg1 = (ImageView) convertView
					.findViewById(R.id.mIvg1);

			holder.mRlg1 = (RelativeLayout) convertView
					.findViewById(R.id.mRlg1);
			holder.mRlg1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Items item = list.get(position);
					if (item.getFlag()) {
						mTvg1.setTextColor(mContext.getResources().getColor(
								R.color.black));
						mIvg1.setImageResource(R.drawable.biaoqiandise);
						item.setFlag(false);
					} else {
						item.setFlag(true);
						mTvg1.setTextColor(mContext.getResources().getColor(
								R.color.white));
						mIvg1.setImageResource(R.drawable.xuanzhong);

					}
				}
			});

			if (list.get(position).getFlag()) {
				mTvg1.setTextColor(mContext.getResources().getColor(
						R.color.white));
				holder.mIvg1.setImageResource(R.drawable.xuanzhong);
			} else {
				mTvg1.setTextColor(mContext.getResources().getColor(
						R.color.black));
				holder.mIvg1.setImageResource(R.drawable.biaoqiandise);

			}
			holder.mTvg1.setText(list.get(position).getItems());
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

	private void initData() {/*
							 * for(int i=0;i<listd1.length;i++){ Items items
							 * =new Items(); items.setItems(listd1[i]);
							 * items.setFlag(false); list.add(items); }
							 */
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.mRlw1:
				if (mOnSearchBarItemClickListener != null) {
					mOnSearchBarItemClickListener.onSearchButtonClick("asd",
							"d");
					dismiss();

				}

				break;

			default:
				break;
			}

		}
	};

	public void setOnSearchBarItemClickListener(
			onSearchBarItemClickListener listener) {
		mOnSearchBarItemClickListener = listener;
	}

	public interface onSearchBarItemClickListener {

		void onSearchButtonClick(String string, String searchType);

	}

	@Override
	public void initEvents() {

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
}
