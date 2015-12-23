package com.rvidda.cn.ui;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.utils.Media;

public class ShouYe extends BaseActivity{

	private ImageView mIvtalk;
	private AnimationDrawable anim;
	private Media media;
	private boolean Flag =false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shouye);
		 media=new Media();

	//	createFileK();
		 initView();
		
	}

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

	private void initView() {
		mIvtalk =(ImageView)this.findViewById(R.id.mIvtalk);
		anim = (AnimationDrawable) mIvtalk.getBackground();
		mIvtalk.setOnClickListener(listener);
		mIvtalk.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
			  Toast toast = Toast.makeText(getApplicationContext(), "正在录音", 0);
			  toast.setGravity(Gravity.CENTER, 0, 0);
			  toast.show();
				if(anim!=null&&!anim.isRunning())
				anim.start();
				media.startRecord();
				Flag=true;
				return false;
			}
		});
		mIvtalk.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
				  if(Flag){
					media.stopRecord();
					Flag=false;
				  }
					if(anim!=null&&anim.isRunning()){
						  anim.selectDrawable(0); 
					anim.stop();
					
					startActivity(new Intent(ShouYe.this, Zixun.class));
					}
					break;
				}
				return false;
			}
		});

	}
	
	OnClickListener listener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mIvtalk:
			
			break;
		default:
			break;
		}
		}
	};

}
