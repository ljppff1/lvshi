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

public class Login extends BaseActivity{
	private TextView mTvde;
	private RelativeLayout mRlLogin;
	private RelativeLayout mRll1;
	private TextView mTvcode;
	private CountDownTimer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		   
		 initView();
		 
	}

	@SuppressLint("ResourceAsColor") private void initView() {
		
		mRlLogin =(RelativeLayout)this.findViewById(R.id.mRlLogin);
		mRlLogin.setOnClickListener(listener);
		mRll1 =(RelativeLayout)this.findViewById(R.id.mRll1);
		mRll1.setOnClickListener(listener);
		mTvcode =(TextView)this.findViewById(R.id.mTvcode);
		
		
		
		
		mTvde =(TextView)this.findViewById(R.id.mTvde);
		
		
		 String url_2_text = "点击登陆，即表示同意";
	        String url_3_text = "《用户协议》";
	        
	        mTvde.setText(url_2_text);
	        SpannableString spStr = new SpannableString(url_3_text);
	        spStr.setSpan(new ClickableSpan() {
	            @Override
	            public void updateDrawState(TextPaint ds) {
	                super.updateDrawState(ds);
	                ds.setColor(Color.RED);       //设置文件颜色
	                ds.setUnderlineText(false);      //设置下划线
	            }
	            @Override
	            public void onClick(View widget) {


	            }
	        }, 0, url_3_text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

	       // mTvr21.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
	        mTvde.append(spStr);
		
		
		
		
	}
	
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mRlLogin:
			startActivity(new Intent(Login.this, ShouYe.class));
			break;
		case R.id.mRll1:
			mRll1.setClickable(false);
			// 开启倒计时的线程
			timer = new CountDownTimer(60000, 1000) {
				@Override
				public void onTick(long millisUntilFinished) {
					mTvcode
							.setText((millisUntilFinished / 1000)
									+ "s后获取");
				}

				@Override
				public void onFinish() {
					mRll1.setClickable(true);
					CharSequence charSequence = getResources()
							.getString(R.string.log2);
					mTvcode.setText(charSequence);
				}
			}.start();

			break;

		default:
			break;
		}
		}
	};

}
