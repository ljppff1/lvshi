package com.rvidda.cn.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class ActivityUtils {
	private static InputMethodManager manager;

	public static void cancelkeycode(Context context){
		manager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		((Activity)context).getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		if (((Activity)context).getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (((Activity)context).getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(((Activity)context).getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}

	}

}
