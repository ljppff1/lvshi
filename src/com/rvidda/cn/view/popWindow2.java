package com.rvidda.cn.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.rvidda.cn.R;

public class popWindow2 extends BasePopupWindow {

	private ImageButton mSearch_Button;

	private onSearchBarItemClickListener mOnSearchBarItemClickListener;

	private Context mContext;

	private RelativeLayout mRlw1;

	private RelativeLayout mRlp1;

	private RelativeLayout mRlp2;

	private EditText mEtp2;
    private String title;
	public popWindow2(Context context, int width, int height,String title1) {
		super(LayoutInflater.from(context).inflate(R.layout.pop_2, null),
				width, height);
		this.mContext = context;
		this.title =title1;
	}

	@Override
	public void initViews() {

		mEtp2 = (EditText) findViewById(R.id.mEtp2);
		mEtp2.setText(title);
		mRlp1 = (RelativeLayout) findViewById(R.id.mRlpd1);
		mRlp1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		mRlp2 = (RelativeLayout) findViewById(R.id.mRlpd2);
		mRlp2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mOnSearchBarItemClickListener != null) {
					mOnSearchBarItemClickListener.onSearchButtonClick1(mEtp2.getEditableText().toString(),
							"d");
					dismiss();
				}
			}
		});

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlpd2:
				if (mOnSearchBarItemClickListener != null) {
					mOnSearchBarItemClickListener.onSearchButtonClick1("asd",
							"d");
					dismiss();

				}

				break;
			case R.id.mRlpd1:
				dismiss();
				break;

			default:
				break;
			}

		}
	};

	public void setOnSearchBar1ItemClickListener(
			onSearchBarItemClickListener listener) {
		mOnSearchBarItemClickListener = listener;
	}

	public interface onSearchBarItemClickListener {

		void onSearchButtonClick1(String string, String searchType);

	}

	@Override
	public void initEvents() {

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
}
