package com.rvidda.cn.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;

public class ShenQingQiliao extends BaseActivity{

	private RelativeLayout mRlwhatn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shenqing);
		   
		 initView();
		 
	}

    private void initView() {
    	mRlwhatn =(RelativeLayout)this.findViewById(R.id.mRlwhatn);
    	mRlwhatn.setOnClickListener(listener);
    	
    	
	}
	
	OnClickListener listener =new OnClickListener() {
		
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
