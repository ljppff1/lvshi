package com.rvidda.cn.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rvidda.cn.R;

public class popWindow3 extends BasePopupWindow {

	private ImageButton mSearch_Button;

	private onSearchBarItemClickListener mOnSearchBarItemClickListener;

	private Context mContext;

	private RelativeLayout mRlw1;

	private RelativeLayout mRlp1;

	private RelativeLayout mRlp2;

	private EditText mEtp2;

	public popWindow3(Context context, int width, int height) {
		super(LayoutInflater.from(context).inflate(R.layout.pop_3, null),
				width, height);
		this.mContext = context;
	}

	@Override
	public void initViews() {

		mEtp2 = (EditText) findViewById(R.id.mEtp2);

		mRlp1 = (RelativeLayout) findViewById(R.id.mRlpd1);
		mRlp1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				shouruanjianpan();
				dismiss();
			}
		});
		mRlp2 = (RelativeLayout) findViewById(R.id.mRlpd2);
		mRlp2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mOnSearchBarItemClickListener != null) {
					if(!TextUtils.isEmpty(mEtp2.getEditableText().toString())){
					mOnSearchBarItemClickListener.onSearchButtonClick1(mEtp2.getEditableText().toString(),
							"d");
					shouruanjianpan();
					dismiss();
					}else{
						Toast.makeText(mContext, R.string.zx2, 0).show();
					}
				}
			}
		});

	}
	private void shouruanjianpan(){
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEtp2.getWindowToken(), 0);

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
