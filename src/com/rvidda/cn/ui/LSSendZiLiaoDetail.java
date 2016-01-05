package com.rvidda.cn.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rvidda.cn.R;
import com.rvidda.cn.fragment.FragmentFuWuXinxi;
import com.rvidda.cn.fragment.FragmentUserXinxi;

public class LSSendZiLiaoDetail extends FragmentActivity implements
		OnPageChangeListener {
	ViewPager pager = null;
	View tabline = null;
	private int mTabLineWidth;

	// titles
	TextView title1 = null;
	TextView title2 = null;

	ArrayList<TextView> titles = null;
	ArrayList<Fragment> pages = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lssendziliaodetail);
		initView();
		initTabline();

	}

	// tablines
	private void initTabline() {
		tabline = findViewById(R.id.main_tab_line);
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		mTabLineWidth = outMetrics.widthPixels / 2;
		LayoutParams lp = tabline.getLayoutParams();
		lp.width = mTabLineWidth;
		tabline.setLayoutParams(lp);
	}

	private void initView() {

		pages = new ArrayList<Fragment>();
		pages = new ArrayList<Fragment>();
		titles = new ArrayList<TextView>();

		pager = (ViewPager) findViewById(R.id.main_viewpager);
		title1 = (TextView) findViewById(R.id.main_tab1);
		title2 = (TextView) findViewById(R.id.main_tab2);

		title1.setOnClickListener(listener);
		title2.setOnClickListener(listener);

		titles.add(title1);
		titles.add(title2);

		// create new fragments
		pages.add(new FragmentFuWuXinxi());
		pages.add(new FragmentUserXinxi());

		// set adapter
		pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		pager.setOnPageChangeListener(this);
		pager.setCurrentItem(0);
		titles.get(0).setSelected(true);

	}

	// adapter
	private class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return pages.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return pages.get(arg0);
		}
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_tab1:
				pager.setCurrentItem(0, true);
				break;
			case R.id.main_tab2:
				pager.setCurrentItem(1, true);
				break;
			default:
				break;
			}
		}

	};

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) tabline
				.getLayoutParams();
		lp.leftMargin = (int) ((arg0 + arg1) * mTabLineWidth);
		tabline.setLayoutParams(lp);

	}

	@Override
	public void onPageSelected(int arg0) {
		// set titles
		for (int i = 0; i < titles.size(); i++) {
			if (arg0 == i) {
				titles.get(i).setSelected(true);
			} else {
				titles.get(i).setSelected(false);
			}
		}

	}

}
