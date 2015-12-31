package com.rvidda.cn.ui;

import java.util.ArrayList;
import java.util.List;

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

import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.domain.Items;

public class Zixun extends BaseActivity {
	private String[] listd1 = { "婚姻", "交通", "税务", "债务", "合同", "房屋", "劳动协议",
			"知识产权", "其它", "婚姻", "更多" };
	private GridView mGv1;
	private List<Items> list = new ArrayList<Items>();
	private Myadapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zixun);

		initData();
		initView();

	}

	private void initData() {
		for (int i = 0; i < listd1.length; i++) {
			Items items = new Items();
			items.setItems(listd1[i]);
			items.setFlag(false);
			list.add(items);
		}
	}

	private void initView() {
		mGv1 = (GridView) this.findViewById(R.id.mGv1);
		mGv1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
		adapter = new Myadapter();
		mGv1.setAdapter(adapter);
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
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.item_gridview_1, null);
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
						mTvg1.setTextColor(getApplicationContext()
								.getResources().getColor(R.color.black));
						mIvg1.setImageResource(R.drawable.biaoqiandise);
						item.setFlag(false);
					} else {
						item.setFlag(true);
						mTvg1.setTextColor(getApplicationContext()
								.getResources().getColor(R.color.white));
						mIvg1.setImageResource(R.drawable.xuanzhong);

					}
				}
			});

			if (list.get(position).getFlag()) {
				mTvg1.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.white));
				holder.mIvg1.setImageResource(R.drawable.xuanzhong);
			} else {
				mTvg1.setTextColor(getApplicationContext().getResources()
						.getColor(R.color.black));
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

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlLogin:

				break;

			default:
				break;
			}
		}
	};

}
