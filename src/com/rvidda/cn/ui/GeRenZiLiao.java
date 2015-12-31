package com.rvidda.cn.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.R.color;
import com.rvidda.cn.view.FlowLayout;
import com.rvidda.cn.view.MyListView;
import com.rvidda.cn.view.StickyScrollView;

public class GeRenZiLiao extends BaseActivity {

	private FlowLayout mFFF;
	private MyListView mListView;
	private List<GeRenZiLiao.Data> list = new ArrayList<GeRenZiLiao.Data>();
	private List<GeRenZiLiao.Data> listfirst = new ArrayList<GeRenZiLiao.Data>();
	private Myadapter adapter;
	private Boolean flag = true;
	private RelativeLayout mRlall;
	private StickyScrollView mScroll1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gerenziliao);

		initData();
		initView();

	}

	class Data {
		private String name;
		private String numstart;
		private String detail;
		private String time;
	}

	private void initData() {
		for (int i = 0; i < 30; i++) {
			Data dd = new Data();
			dd.name = "Angelinar" + i;
			dd.numstart = 3 + "";
			dd.detail = "律师服务。。。" + i + "某个人的生活";
			dd.time = "2015-09-01" + "---" + i;
			list.add(dd);
		}
	}

	private void initView() {

		mRlall = (RelativeLayout) this.findViewById(R.id.mRlall);
		mRlall.setVisibility(View.GONE);
		mRlall.setOnClickListener(listener);

		mListView = (com.rvidda.cn.view.MyListView) this
				.findViewById(R.id.mListView);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});

		adapter = new Myadapter();
		mListView.setAdapter(adapter);

		mFFF = (com.rvidda.cn.view.FlowLayout) this.findViewById(R.id.mFFF);
		for (int i = 0; i < 10; i++) {
			LinearLayout.LayoutParams layoutParamsButtonCancel = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParamsButtonCancel.gravity = Gravity.CENTER;
			layoutParamsButtonCancel.leftMargin = dip2px(
					getApplicationContext(), 1);
			layoutParamsButtonCancel.rightMargin = dip2px(
					getApplicationContext(), 1);
			layoutParamsButtonCancel.topMargin = dip2px(
					getApplicationContext(), 1);
			layoutParamsButtonCancel.bottomMargin = dip2px(
					getApplicationContext(), 1);
			TextView tv1 = new TextView(getApplicationContext());
			tv1.setTextColor(color.black);

			tv1.setLayoutParams(layoutParamsButtonCancel);
			Drawable drawable1 = getResources().getDrawable(R.drawable.flag_02);
			tv1.setBackgroundDrawable(drawable1);
			tv1.setGravity(Gravity.CENTER);
			if (i > 5) {
				tv1.setText("合同纠纷合同纠纷" + i);

			} else {
				tv1.setText("合同纠纷" + i);

			}
			mFFF.addView(tv1);
		}

	}

	class Holder {
		TextView mtv1, mtv2, mtv3, mTv4, mTv5;
		ImageView mIvP1, mIvP2, mIvP3, mIvP4, mIvP5;
		Button mBtn2, mBtn1;
	}

	class Myadapter extends BaseAdapter {
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			Holder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(GeRenZiLiao.this).inflate(
						R.layout.item_pingjia_layout, null);
				holder = new Holder();

				holder.mtv1 = (TextView) convertView.findViewById(R.id.mtv1);
				holder.mtv2 = (TextView) convertView.findViewById(R.id.mtv2);
				holder.mtv3 = (TextView) convertView.findViewById(R.id.mtv3);

				holder.mIvP1 = (ImageView) convertView.findViewById(R.id.mIvP1);
				holder.mIvP2 = (ImageView) convertView.findViewById(R.id.mIvP2);
				holder.mIvP3 = (ImageView) convertView.findViewById(R.id.mIvP3);
				holder.mIvP4 = (ImageView) convertView.findViewById(R.id.mIvP4);
				holder.mIvP5 = (ImageView) convertView.findViewById(R.id.mIvP5);

				convertView.setTag(holder);

			} else {
				holder = (Holder) convertView.getTag();
			}
			if (flag) {
				mRlall.setVisibility(View.VISIBLE);
			} else {
				mRlall.setVisibility(View.GONE);

			}

			holder.mtv1.setText(list.get(position).name);
			holder.mtv3.setText(list.get(position).detail);
			holder.mtv2.setText(list.get(position).time);

			if (position < 4) {
				holder.mIvP1.setVisibility(View.VISIBLE);
				holder.mIvP2.setVisibility(View.VISIBLE);
				holder.mIvP3.setVisibility(View.GONE);
				holder.mIvP4.setVisibility(View.GONE);
				holder.mIvP5.setVisibility(View.GONE);

			} else {
				holder.mIvP1.setVisibility(View.VISIBLE);
				holder.mIvP2.setVisibility(View.VISIBLE);
				holder.mIvP3.setVisibility(View.VISIBLE);
				holder.mIvP4.setVisibility(View.VISIBLE);
				holder.mIvP5.setVisibility(View.GONE);

			}
			return convertView;

		}

		@Override
		public int getCount() {
			if (flag) {
				if (list.size() > 1)
					return 1;
			} else {
				return list.size();
			}
			return 0;
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

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlall:
				flag = false;
				adapter = new Myadapter();
				mListView.setAdapter(adapter);

				break;

			default:
				break;
			}
		}

	};

}
