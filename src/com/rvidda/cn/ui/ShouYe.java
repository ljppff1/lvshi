package com.rvidda.cn.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.RequestParams;
import com.rvidda.cn.AppManager;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.domain.Items;
import com.rvidda.cn.utils.Media;

public class ShouYe extends BaseActivity {

	private ImageView mIvtalk;
	private AnimationDrawable anim;
	private Media media;
	private boolean Flag = false;
	private ImageView mIvshow1;
	private ImageView mIvshow2;
	private LinearLayout mLLshow3;
	private long exitTime1 = 0;
	private boolean flagl = true;;
	private boolean flagan = false;;

	private String[] listd1 = { "婚姻", "交通", "税务", "债务", "合同", "房屋", "劳动协议",
			"知识产权", "其它", "婚姻", "更多" };
	private GridView mGv1;
	private List<Items> list = new ArrayList<Items>();
	private Myadapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shouye);
		media = new Media();
		initData();
		// createFileK();
		initView();

	}

	private void initData() {
		for (int i = 0; i < listd1.length; i++) {
			Items items = new Items();
			items.setItems(listd1[i]);
			items.setFlag(false);
			list.add(items);
		}
		initgetBiaoqian();
		
	}

	private void initgetBiaoqian()
	{}

	
	private void createFileK() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				String sendpath = Environment.getExternalStorageDirectory()
						.getCanonicalPath().toString()
						+ "/MessageMediaSend";
				File files = new File(sendpath);
				if (!files.exists()) {
					files.mkdir();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				String receivepath = Environment.getExternalStorageDirectory()
						.getCanonicalPath().toString()
						+ "/MessageMediaReceive";
				File files = new File(receivepath);
				if (!files.exists()) {
					files.mkdir();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};
	private RelativeLayout mVVli;
	private RelativeLayout mRlsure;

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

		mIvshow1 = (ImageView) this.findViewById(R.id.mIvshow1);
		mIvshow2 = (ImageView) this.findViewById(R.id.mIvshow2);
		mLLshow3 = (LinearLayout) this.findViewById(R.id.mLLshow3);
		mRlsure = (RelativeLayout) this.findViewById(R.id.mRlsure);
		mRlsure.setVisibility(View.GONE);
		mRlsure.setOnClickListener(listener);
		mIvtalk = (ImageView) this.findViewById(R.id.mIvtalk);
		mIvtalk.setVisibility(View.VISIBLE);
		anim = (AnimationDrawable) mIvtalk.getBackground();
		mIvtalk.setOnClickListener(listener);
		mIvtalk.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				flagan = true;
				exitTime1 = System.currentTimeMillis();
				Toast toast = Toast
						.makeText(getApplicationContext(), "正在录音", 0);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				if (anim != null && !anim.isRunning())
					anim.start();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (flagl) {
							mRlsure.setVisibility(View.VISIBLE);
							mIvshow1.setVisibility(View.GONE);
							mIvshow2.setVisibility(View.GONE);
							mIvtalk.setVisibility(View.GONE);
							mLLshow3.setVisibility(View.VISIBLE);
							flagl = false;
						} else {
							flagl = true;
							Toast toast = Toast.makeText(
									getApplicationContext(), "录音时间太短", 0);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
					}
				}, 2000);
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						Toast toast = Toast.makeText(
								getApplicationContext(), "录音时间还剩10s", 0);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						
					}
				}, 10000);
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						Toast toast = Toast.makeText(
								getApplicationContext(), "录音完成", 0);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

						if (Flag) {
							media.stopRecord();
							Flag = false;
						}
						if (anim != null && anim.isRunning()) {
							anim.selectDrawable(0);
							anim.stop();

							// startActivity(new Intent(ShouYe.this, Zixun.class));
						}
						
					}
				}, 20000);

				media.startRecord();
				Flag = true;
				return false;
			}
		});
		mVVli = (RelativeLayout) this.findViewById(R.id.mRlwhatshow);
		mVVli.setOnTouchListener(new OnTouchListener() {

			private float x1;
			private float y1;
			private float x2;
			private float y2;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					x1 = event.getX();
					y1 = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					x2 = event.getX();
					y2 = event.getY();
					if (y1 - y2 > 50) {
						startActivity(new Intent(getApplicationContext(),
								ZiXunLieBiao.class));

					}
				}
				return true;
			}
		});

		mIvtalk.setOnTouchListener(new OnTouchListener() {

			private float x1;
			private float y1;
			private float x2;
			private float y2;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					x1 = event.getX();
					y1 = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					x2 = event.getX();
					y2 = event.getY();
					if (y1 - y2 > 50) {
						if (flagan) {
							mIvshow1.setVisibility(View.VISIBLE);
							mIvshow2.setVisibility(View.VISIBLE);
							mLLshow3.setVisibility(View.GONE);
							flagan = false;
						} else {
							startActivity(new Intent(getApplicationContext(),
									ZiXunLieBiao.class));
						}
					}
					if (System.currentTimeMillis() - exitTime1 < 2000) {
						flagl = false;
					} else {
						flagl = true;
					}
					

					
					if (Flag) {
						media.stopRecord();
						Flag = false;
					}
					if (anim != null && anim.isRunning()) {
						anim.selectDrawable(0);
						anim.stop();

						// startActivity(new Intent(ShouYe.this, Zixun.class));
					}
					break;
				}
				return false;
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
			case R.id.mIvtalk:

				break;
			case R.id.mRlsure:
				initgetBiaoqian();
				startActivity(new Intent(getApplicationContext(),
						TiChuZiXun.class));
				break;
			default:
				break;
			}
		}
	};

	//七牛的
	private void initgetQiNiu()
	{}

	
	
	
}
