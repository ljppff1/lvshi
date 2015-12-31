package com.rvidda.cn.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.view.popWindow1;
import com.rvidda.cn.view.popWindow1.onSearchBarItemClickListener;
import com.rvidda.cn.view.popWindow2;

public class TiChuZiXun extends BaseActivity implements
		onSearchBarItemClickListener,
		com.rvidda.cn.view.popWindow2.onSearchBarItemClickListener {
	private TextView mTvde;
	private RelativeLayout mRlLogin;
	private RelativeLayout mRll1;
	private TextView mTvcode;
	private CountDownTimer timer;
	private RelativeLayout mRlw1;
	private RelativeLayout mRlw2;
	private RelativeLayout mRlw3;
	private popWindow1 pop1;
	private LinearLayout mLLpp1;
	private popWindow2 pop2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tichuzixun);

		initView();

	}

	private void initView() {
		mLLpp1 = (LinearLayout) this.findViewById(R.id.mLLpp1);
		mRlw1 = (RelativeLayout) this.findViewById(R.id.mRlw1);
		mRlw2 = (RelativeLayout) this.findViewById(R.id.mRlw2);
		mRlw3 = (RelativeLayout) this.findViewById(R.id.mRlw3);
		mRlw1.setOnClickListener(listener);
		mRlw2.setOnClickListener(listener);
		mRlw3.setOnClickListener(listener);

		pop1 = new popWindow1(TiChuZiXun.this, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		pop1.setOnSearchBarItemClickListener(this);
		pop2 = new popWindow2(TiChuZiXun.this, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		pop2.setOnSearchBar1ItemClickListener(this);

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlw1:
				pop1.showAsDropDown(mLLpp1);
				break;
			case R.id.mRlw2:
				pop2.showAsDropDown(mLLpp1);
				break;
			case R.id.mRlw3:
				startActivity(new Intent(TiChuZiXun.this, Chengshi.class));
				break;

			default:
				break;
			}
		}
	};

	@Override
	public void onSearchButtonClick(String string, String searchType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSearchButtonClick1(String string, String searchType) {
		// TODO Auto-generated method stub

	}

}
