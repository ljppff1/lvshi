package com.rvidda.cn;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

public class AppManager {

	private static Stack<Activity> activityStack;
	private static AppManager instance;

	private AppManager() {
	}

	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	public static boolean isAppmanager() {
		boolean flag = false;
		if (instance == null) {
			flag = true;
		}
		return flag;
	}

	/**
	 * ���Activity����ջ
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * ��ȡ��ǰActivity����ջ�����һ��ѹ��ģ�
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * ������ǰActivity����ջ�����һ��ѹ��ģ�
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	public boolean hasActivity(Class<?> cls) {
		boolean flag = false;
		if (activityStack != null) {
			for (Activity activity : activityStack) {
				if (activity.getClass().equals(cls)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public void finishActivityJob4() {
		for (int i = 0; i < 2; i++) {
			Activity activity = activityStack.lastElement();
			finishActivity(activity);
		}
	}

	public void finishActivityJob3() {
		for (int i = 0; i < 3; i++) {
			Activity activity = activityStack.lastElement();
			finishActivity(activity);
		}
	}

	public void finishActivity5() {
		for (int i = 0; i < 4; i++) {
			Activity activity = activityStack.lastElement();

			finishActivity(activity);
		}
	}

	/**
	 * ������ǰActivity����ջ�����һ��ѹ��ģ�
	 */

	/**
	 * ����ָ����Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * ����ָ��������Activityn
	 */
	public void finishActivity(Class<?> cls) {
		if (activityStack != null) {
			for (Activity activity : activityStack) {
				if (activity.getClass().equals(cls)) {
					finishActivity(activity);
				}
			}
		}
	}

	/**
	 * ����ǰ���Activity
	 */

	public void finishAll1Activity() {
		if (activityStack != null) {
			for (int i = 0, size = activityStack.size() - 1; i < size; i++) {
				if (null != activityStack.get(i)) {
					activityStack.get(i).finish();
				}
			}
			// activityStack.clear();
			// activityStack=null;
		}
	}

	/**
	 * ��������Activity
	 */
	public void finishAllActivity() {
		if (activityStack != null) {
			for (int i = 0, size = activityStack.size(); i < size; i++) {
				if (null != activityStack.get(i)) {
					activityStack.get(i).finish();
				}
			}
			activityStack.clear();
			activityStack = null;
		}
	}

	/**
	 * �˳�Ӧ�ó���
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
