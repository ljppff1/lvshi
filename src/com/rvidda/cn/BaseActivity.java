package com.rvidda.cn;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class BaseActivity extends Activity {
	private boolean allowFullScreen = true;
	private boolean allowDestroy = true;

	private View view;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private SystemBarTintManager mTintManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		allowFullScreen = true;
		AppManager.getAppManager().addActivity(this);

		mTintManager = new SystemBarTintManager(this);
		mTintManager.setStatusBarTintEnabled(true);
		mTintManager.setNavigationBarTintEnabled(true);
		mTintManager.setTintColor(Color.RED);

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public boolean isAllowFullScreen() {
		return allowFullScreen;
	}

	public void setAllowFullScreen(boolean allowFullScreen) {
		this.allowFullScreen = allowFullScreen;
	}

	public void setAllowDestroy(boolean allowDestroy) {
		this.allowDestroy = allowDestroy;
	}

	public void setAllowDestroy(boolean allowDestroy, View view) {
		this.allowDestroy = allowDestroy;
		this.view = view;
	}

	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager imeManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imeManager.hideSoftInputFromWindow(getWindow().getDecorView()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		return false;
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AppManager.getAppManager().finishActivity();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
