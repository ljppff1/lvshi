package com.rvidda.cn.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.rvidda.cn.AppManager;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.view.CircleImageView;

public class UserSetting1 extends BaseActivity {
	private CircleImageView photo_view;
	private ImageView mBtn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting1);

		initView();

	}

	private void initView() {
		mBtn_back =(ImageView)this.findViewById(R.id.mBtn_back);
		mBtn_back.setOnClickListener(listener);
	}
	android.view.View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mBtn_back:
           AppManager.getAppManager().finishActivity();
				break;

			default:
				break;
			}
		}
	};

}
