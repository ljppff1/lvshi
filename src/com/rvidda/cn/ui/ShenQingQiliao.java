package com.rvidda.cn.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;

public class ShenQingQiliao extends BaseActivity {

	private RelativeLayout mRlwhatn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shenqing);

		initView();

	}

	private void initView() {
		mRlwhatn = (RelativeLayout) this.findViewById(R.id.mRlwhatn);
		mRlwhatn.setOnClickListener(listener);

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlwhatn:
				startActivity(new Intent(ShenQingQiliao.this, Chengshi.class));
				break;
			default:
				break;
			}
		}
	};

}
