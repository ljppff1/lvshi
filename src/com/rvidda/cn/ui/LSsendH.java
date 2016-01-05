package com.rvidda.cn.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;

public class LSsendH extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lssendh);

		initView();

	}

	private void initView() {

	}

	OnClickListener listener = new OnClickListener() {

		private ProgressDialog dialog;

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
