package com.rvidda.cn.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.fanxin.app.DemoApplication;
import com.fanxin.app.DemoHXSDKHelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.rvidda.cn.AppManager;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.utils.Content;
import com.rvidda.cn.utils.PreferenceUtils;

public class Login extends BaseActivity {
	private TextView mTvde;
	private RelativeLayout mRlLogin;
	private RelativeLayout mRll1;
	private TextView mTvcode;
	private CountDownTimer timer;
	private PreferenceUtils pp;
	private EditText mEtCode;
	private EditText mEtTell;
	private int id=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		dialog = new ProgressDialog(Login.this);
     pp =PreferenceUtils.getInstance(Login.this);
     pp.put(Content.TOKEN, "");
     
		if (DemoHXSDKHelper.getInstance().isLogined()) {
			// ** 免登陆情况 加载所有本地群和会话
			// 不是必须的，不加sdk也会自动异步去加载(不会重复加载)；
			// 加上的话保证进了主页面会话和群组都已经load完毕
			EMGroupManager.getInstance().loadAllGroups();
			EMChatManager.getInstance().loadAllConversations();
		}

		initView();

	}

	@SuppressLint("ResourceAsColor")
	private void initView() {

		mRlLogin = (RelativeLayout) this.findViewById(R.id.mRlLogin);
		mRlLogin.setOnClickListener(listener);
		mRll1 = (RelativeLayout) this.findViewById(R.id.mRll1);
		mRll1.setOnClickListener(listener);
		mTvcode = (TextView) this.findViewById(R.id.mTvcode);
		mEtCode =(EditText)this.findViewById(R.id.mEtCode);
		mEtTell =(EditText)this.findViewById(R.id.mEtTell);
		mEtTell.setText(pp.getString(Content.Mobile, ""));
		mTvde = (TextView) this.findViewById(R.id.mTvde);

		String url_2_text = "点击登陆，即表示同意";
		String url_3_text = "《用户协议》";

		mTvde.setText(url_2_text);
		SpannableString spStr = new SpannableString(url_3_text);
		spStr.setSpan(new ClickableSpan() {
			@Override
			public void updateDrawState(TextPaint ds) {
				super.updateDrawState(ds);
				ds.setColor(Color.RED); // 设置文件颜色
				ds.setUnderlineText(false); // 设置下划线
			}

			@Override
			public void onClick(View widget) {

			}
		}, 0, url_3_text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// mTvr21.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
		mTvde.append(spStr);

	}
    
    
	private void initSendCode()
	{
	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user[mobile]",mEtTell.getEditableText().toString());
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.UPDATE_CHECK_URL, "post", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {

					@Override
					public void callback(String json) {
						try {
							//{"user":{"id":15,"mobile":"13652435378","email":null,"mobile_confirm":false,
							//"auth_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxNSwibW9iaWxlIjoiMTM2NTI0MzUzNzgiLCJzdWIiOiJhdXRoIiwiZXhwIjowfQ.uBycfl1hPZ31PQA5BXbsrYhcE4qUIe5D9_z6JHeKz74",
							//"avatar":null,"avatar_url":"http:\/\/7u2gfi.com1.z0.glb.clouddn.com\/Fhab9G1MW-m0MPH0mqVk3QAmUvDa",
							//"auth_code":"857068","hx_user":"hx_15","hx_password":"password_15"}}
							
							if(!json.equals("0")){
							
							JSONObject jsonObj = new JSONObject(json);
								 JSONObject user = jsonObj.getJSONObject("user");
								 String auth_token = user.getString("auth_token"); 
								 String mobile = user.getString("mobile"); 
								 String avatar = user.getString("avatar"); 
								 String avatar_url = user.getString("avatar_url"); 
								 String auth_code = user.getString("auth_code"); 
								 String hx_user = user.getString("hx_user"); 
								 String email = user.getString("email"); 
								 String hx_password = user.getString("hx_password"); 
								  id = user.getInt("id"); 
								 boolean mobile_confirm = user.getBoolean("mobile_confirm");
								// pp.clearPreference();
					             pp.put(Content.TOKEN, auth_token);
					             pp.put(Content.Mobile, mobile);
					             pp.put(Content.UserID, id);
					             pp.put(Content.Avator_Url, avatar_url);
					             pp.put(Content.Hx_User, hx_user);
					             pp.put(Content.Hx_Pd, hx_password);
					             mEtCode.setText(auth_code);
							}else{
			                       Toast.makeText(getApplicationContext(), R.string.log6, 0).show();
										}

/*					             startActivity(new Intent(getApplicationContext(), ShouYe.class));
								 AppManager.getAppManager().finishActivity();
*/
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
		private void initLogin()
		{
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("user[auth_code]",mEtCode.getEditableText().toString());
			com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.HOST + "/users/"+id+"/confirm_code", "put", params,
					new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
						@Override
						public void callback(String json) {
							try {
								if(!json.equals("0")){
								JSONObject jsonObj = new JSONObject(json);
						             login();
								}else{
                           Toast.makeText(getApplicationContext(), R.string.log8, 0).show();
								}
/*						             startActivity(new Intent(getApplicationContext(), ShouYe.class));
									 AppManager.getAppManager().finishActivity();
*/
								
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});

		}

	 public static String createJsonString(String key, Object value) {
		  JSONObject jsonObject = new JSONObject();
		  try {
			jsonObject.put(key, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return jsonObject.toString();
		 }
	

	OnClickListener listener = new OnClickListener() {

		private ProgressDialog dialog;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlLogin:
				if(mEtCode.getEditableText().toString().length()!=6){
					Toast.makeText(getApplicationContext(), R.string.log3, 0).show();
				}else{
					if(id!=0){
					initLogin();
					}else{
						Toast.makeText(getApplicationContext(), R.string.log7, 0).show();
					}
					
				}
				//login();

				break;
			case R.id.mRll1:
				if(!TextUtils.isEmpty(mEtTell.getEditableText().toString())){
				mRll1.setClickable(false);
				// 开启倒计时的线程
				timer = new CountDownTimer(60000, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {
						mTvcode.setText((millisUntilFinished / 1000) + "s后获取");
					}

					@Override
					public void onFinish() {
						mRll1.setClickable(true);
						CharSequence charSequence = getResources().getString(
								R.string.log2);
						mTvcode.setText(charSequence);
					}
				}.start();
				initSendCode();
				}else{
					Toast.makeText(getApplicationContext(),R.string.log1,Toast.LENGTH_SHORT).show();
				}
				break;

			default:
				break;
			}
		}

	};
	private ProgressDialog dialog;

	private void login() {
		EMChatManager.getInstance().login("ljppff", "aaaaaa", new EMCallBack() {
			@Override
			public void onSuccess() {
				// 登陆成功，保存用户名密码
				DemoApplication.getInstance().setUserName("ljppff");
				DemoApplication.getInstance().setPassword("aaaaaa");
				runOnUiThread(new Runnable() {
					public void run() {
						// dialog.setMessage(getString(R.string.list_is_for));
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
						Log.e("LoginActivity", "update current user nick fail");
					}
					EMGroupManager.getInstance().loadAllGroups();
					EMChatManager.getInstance().loadAllConversations();
					// 处理好友和群组
					/*
					 * runOnUiThread(new Runnable() { public void run() {
					 * processContactsAndGroups(json); } });
					 */
                    if(pp.getString(Content.IS_PUTONG_User, "1").equals("1")){
					startActivity(new Intent(Login.this, ShouYe.class));
                    }else{
                   	startActivity(new Intent(Login.this, LvShiShouYe.class));
                    }
                    AppManager.getAppManager().finishActivity();
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
