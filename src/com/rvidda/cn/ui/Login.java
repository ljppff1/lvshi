package com.rvidda.cn.ui;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;
import com.fanxin.app.Constant;
import com.fanxin.app.DemoApplication;
import com.fanxin.app.DemoHXSDKHelper;
import com.fanxin.app.fx.LoginActivity;
import com.fanxin.app.fx.others.LoadDataFromServer;
import com.fanxin.app.fx.others.LoadDataFromServer.DataCallBack;
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
        dialog = new ProgressDialog(Login.this);
        
        
		if (DemoHXSDKHelper.getInstance().isLogined()) {
			// ** 免登陆情况 加载所有本地群和会话
			//不是必须的，不加sdk也会自动异步去加载(不会重复加载)；
			//加上的话保证进了主页面会话和群组都已经load完毕
			EMGroupManager.getInstance().loadAllGroups();
			EMChatManager.getInstance().loadAllConversations();
		}

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
		
		private ProgressDialog dialog;

		@Override
		public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mRlLogin:
			login();
			
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
	private ProgressDialog dialog;

    private void login() {
      /*  dialog.setMessage("正在登录...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        */
    	
    	/*
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					EMChatManager.getInstance().createAccountOnServer("ljppff4", "aaaaaa");
				} catch (EaseMobException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		*/
    	
    	
    	
          EMChatManager.getInstance().login("ljppff", "aaaaaa", new EMCallBack() {
                @Override
                public void onSuccess() {
                    // 登陆成功，保存用户名密码
                    DemoApplication.getInstance().setUserName("ljppff");
                    DemoApplication.getInstance().setPassword("aaaaaa");
                    runOnUiThread(new Runnable() {
                        public void run() {
                     //       dialog.setMessage(getString(R.string.list_is_for));
                        }
                    });
                    try {
                        // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                        // ** manually load all local groups and
                        // conversations in case we are auto login
                        // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                        boolean updatenick = EMChatManager.getInstance()
                                .updateCurrentUserNick("刘俊");
                        if (!updatenick) {
                            Log.e("LoginActivity",
                                    "update current user nick fail");
                        }
                        EMGroupManager.getInstance().loadAllGroups();
                        EMChatManager.getInstance().loadAllConversations();
                        // 处理好友和群组
/*                       runOnUiThread(new Runnable() {
                            public void run() {
                                processContactsAndGroups(json);
                            }
                        });
*/            			startActivity(new Intent(Login.this, ShouYe.class));

                    } catch (Exception e) {
                        e.printStackTrace();
                        // 取好友或者群聊失败，不让进入主页面
                        runOnUiThread(new Runnable() {
                            public void run() {
                                dialog.dismiss();
                                DemoApplication.getInstance().logout(null);
                                Toast.makeText(getApplicationContext(),
                                        R.string.login_failure_failed,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                }

                @Override
                public void onProgress(int progress, String status) {
                }

                @Override
                public void onError(final int code, final String message) {

                    runOnUiThread(new Runnable() {
                        public void run() {
                           // dialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.Login_failed) + message,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            
            
            
            
            
            
            
        }
    

}
