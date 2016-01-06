package com.rvidda.cn.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.view.ViewPager;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMError;
import com.easemob.chat.EMConversation;
import com.easemob.util.VoiceRecorder;
import com.fanxin.app.adapter.MessageAdapter;
import com.fanxin.app.adapter.VoicePlayClickListener;
import com.fanxin.app.fx.ChatActivity;
import com.fanxin.app.utils.CommonUtils;
import com.fanxin.app.widget.PasteEditText;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.rvidda.cn.AppManager;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.adapter.ZXAdapter;
import com.rvidda.cn.domain.Items;
import com.rvidda.cn.domain.ZXXiaoXi;
import com.rvidda.cn.http.ContantsUtil;
import com.rvidda.cn.utils.Media;
import com.rvidda.cn.view.popWindow1;
import com.rvidda.cn.view.popWindow1.onSearchBarItemClickListener;
import com.rvidda.cn.view.popWindow2;

public class TiChuZiXun extends BaseActivity implements
		onSearchBarItemClickListener,
		com.rvidda.cn.view.popWindow2.onSearchBarItemClickListener {
	
	private View recordingContainer;
	private ImageView micImage;
	private TextView recordingHint;
	private ListView listView;
	private PasteEditText mEditTextContent;
	private View buttonSetModeKeyboard;
	private View buttonSetModeVoice;
	private View buttonSend;
	private View buttonPressToSpeak;
	// private ViewPager expressionViewpager;
	private LinearLayout emojiIconContainer;
	private LinearLayout btnContainer;

	private View more;
	private ClipboardManager clipboard;
	private ViewPager expressionViewpager;
	private InputMethodManager manager;
	private List<String> reslist;
	private Drawable[] micImages;
	private int chatType;
	private EMConversation conversation;
	public static ChatActivity activityInstance = null;
	// 给谁发送消息
	private String toChatUsername;
	private VoiceRecorder voiceRecorder;
	private MessageAdapter adapter;
	private File cameraFile;
	public static int resendPos;


	private ImageView iv_emoticons_normal;
	private ImageView iv_emoticons_checked;
	private RelativeLayout edittext_layout;
	private ProgressBar loadmorePB;
	private boolean isloading;
	private final int pagesize = 20;
	private boolean haveMoreData = true;
	private Button btnMore;
	public String playMsgId;
	private String sendBiaoQianString;

	String myUserNick = "";
	String myUserAvatar = "";
	String toUserNick = "";
	String toUserAvatar = "";
	// 分享的照片
	String iamge_path = null;
	// 设置按钮
	private ImageView iv_setting;
	private ImageView iv_setting_group;
	@SuppressLint("HandlerLeak")
	private Handler micImageHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			// 切换msg切换图片
			micImage.setImageDrawable(micImages[msg.what]);
		}
	};

	
	private String Title;
	private TextView mTvde;
	private RelativeLayout mRlLogin;
	private RelativeLayout mRll1;
	private TextView mTvcode;
	private CountDownTimer timer;
	private RelativeLayout mRlw1;
	private RelativeLayout mRlw2;
	private RelativeLayout mRlw3;
	private popWindow1 pop1;
	private LinearLayout mLLpp1;
	private popWindow2 pop2;
	private WakeLock wakeLock;
	private String ID;
	private String SELECT;
	private String[] strarray;
	private ListView list;
	private Media media;
	private ArrayList<ZXXiaoXi> listzxxx;
	private ZXAdapter zxxiaoxi;
	private ImageView mBtn_back;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tichuzixun);
		media =new Media();
        getData();
		initHuanXinView();
		initView();
		setUpView();

	}

	private void getData() {
		ID =getIntent().getExtras().getString("ID");
		SELECT =getIntent().getExtras().getString("SELECT");
	    strarray = SELECT.split(",");
	    String FILE_pro = getIntent().getExtras().getString("FILE");
		listzxxx =new ArrayList<ZXXiaoXi>();

		ZXXiaoXi zxxx =new ZXXiaoXi();
		zxxx.setMtype("V");
		zxxx.setMfilelocal(FILE_pro);
		zxxx.setMtext("");
		listzxxx.add(zxxx);

	}

	private void setUpView() {
		clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE))
				.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "demo");
		
	}

	private void initHuanXinView() {
		recordingContainer = findViewById(R.id.recording_container);
		micImage = (ImageView) findViewById(R.id.mic_image);
		recordingHint = (TextView) findViewById(R.id.recording_hint);
		listView = (ListView) findViewById(R.id.list);
		mEditTextContent = (PasteEditText) findViewById(R.id.et_sendmessage);
		buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
		buttonSetModeKeyboard.setOnClickListener(listener);
		edittext_layout = (RelativeLayout) findViewById(R.id.edittext_layout);
		buttonSetModeVoice = findViewById(R.id.btn_set_mode_voice);
		buttonSend = findViewById(R.id.btn_send);
		buttonSend.setOnClickListener(listener);
		buttonPressToSpeak = findViewById(R.id.btn_press_to_speak);
		expressionViewpager = (ViewPager) findViewById(R.id.vPager);
		emojiIconContainer = (LinearLayout) findViewById(R.id.ll_face_container);
		btnContainer = (LinearLayout) findViewById(R.id.ll_btn_container);
		iv_emoticons_normal = (ImageView) findViewById(R.id.iv_emoticons_normal);
		iv_emoticons_checked = (ImageView) findViewById(R.id.iv_emoticons_checked);
		loadmorePB = (ProgressBar) findViewById(R.id.pb_load_more);
		btnMore = (Button) findViewById(R.id.btn_more);
		iv_emoticons_checked.setVisibility(View.INVISIBLE);
		more = findViewById(R.id.more);
		edittext_layout.setBackgroundResource(R.drawable.input_bar_bg_normal);

		// 动画资源文件,用于录制语音时
		micImages = new Drawable[] {
				getResources().getDrawable(R.drawable.record_animate_01),
				getResources().getDrawable(R.drawable.record_animate_02),
				getResources().getDrawable(R.drawable.record_animate_03),
				getResources().getDrawable(R.drawable.record_animate_04),
				getResources().getDrawable(R.drawable.record_animate_05),
				getResources().getDrawable(R.drawable.record_animate_06),
				getResources().getDrawable(R.drawable.record_animate_07),
				getResources().getDrawable(R.drawable.record_animate_08),
				getResources().getDrawable(R.drawable.record_animate_09),
				getResources().getDrawable(R.drawable.record_animate_10),
				getResources().getDrawable(R.drawable.record_animate_11),
				getResources().getDrawable(R.drawable.record_animate_12),
				getResources().getDrawable(R.drawable.record_animate_13),
				getResources().getDrawable(R.drawable.record_animate_14), };

		edittext_layout.requestFocus();
		voiceRecorder = new VoiceRecorder(micImageHandler);
		buttonPressToSpeak.setOnTouchListener(new PressToSpeakListen());
		mEditTextContent.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					edittext_layout
							.setBackgroundResource(R.drawable.input_bar_bg_active);
				} else {
					edittext_layout
							.setBackgroundResource(R.drawable.input_bar_bg_normal);
				}
			}
		});
		mEditTextContent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edittext_layout
						.setBackgroundResource(R.drawable.input_bar_bg_active);
				more.setVisibility(View.GONE);
				iv_emoticons_checked.setVisibility(View.INVISIBLE);
				emojiIconContainer.setVisibility(View.GONE);
				btnContainer.setVisibility(View.GONE);
			}
		});
		// 监听文字框
		mEditTextContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(s)) {
					btnMore.setVisibility(View.GONE);
					buttonSend.setVisibility(View.VISIBLE);
				} else {
					btnMore.setVisibility(View.GONE);
					buttonSend.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}


	/**
	 * 显示语音图标按钮
	 * 
	 * @param view
	 */
	public void setModeVoice(View view) {
		hideKeyboard();
		edittext_layout.setVisibility(View.GONE);
		more.setVisibility(View.GONE);
		view.setVisibility(View.GONE);
		buttonSetModeKeyboard.setVisibility(View.VISIBLE);
		buttonSend.setVisibility(View.GONE);
		btnMore.setVisibility(View.GONE);
		buttonPressToSpeak.setVisibility(View.VISIBLE);
		btnContainer.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 隐藏软键盘
	 */
	private void hideKeyboard() {
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}


	/**
	 * 按住说话listener
	 * 
	 */
	class PressToSpeakListen implements View.OnTouchListener {
		@SuppressLint({ "ClickableViewAccessibility", "Wakelock" })
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (!CommonUtils.isExitsSdcard()) {
					Toast.makeText(TiChuZiXun.this, "发送语音需要sdcard支持！",
							Toast.LENGTH_SHORT).show();
					return false;
				}
				try {
					v.setPressed(true);
					wakeLock.acquire();
					if (VoicePlayClickListener.isPlaying)
						VoicePlayClickListener.currentPlayListener
								.stopPlayVoice();
					recordingContainer.setVisibility(View.VISIBLE);
					recordingHint
							.setText(getString(R.string.move_up_to_cancel));
					recordingHint.setBackgroundColor(Color.TRANSPARENT);
					voiceRecorder.startRecording(null, toChatUsername,
							getApplicationContext());
				} catch (Exception e) {
					e.printStackTrace();
					v.setPressed(false);
					if (wakeLock.isHeld())
						wakeLock.release();
					if (voiceRecorder != null)
						voiceRecorder.discardRecording();
					recordingContainer.setVisibility(View.INVISIBLE);
					Toast.makeText(TiChuZiXun.this, R.string.recoding_fail,
							Toast.LENGTH_SHORT).show();
					return false;
				}

				return true;
			case MotionEvent.ACTION_MOVE: {
				if (event.getY() < 0) {
					recordingHint
							.setText(getString(R.string.release_to_cancel));
					recordingHint
							.setBackgroundResource(R.drawable.recording_text_hint_bg);
				} else {
					recordingHint
							.setText(getString(R.string.move_up_to_cancel));
					recordingHint.setBackgroundColor(Color.TRANSPARENT);
				}
				return true;
			}
			case MotionEvent.ACTION_UP:
				v.setPressed(false);
				recordingContainer.setVisibility(View.INVISIBLE);
				if (wakeLock.isHeld())
					wakeLock.release();
				if (event.getY() < 0) {
					// discard the recorded audio.
					voiceRecorder.discardRecording();

				} else {
					// stop recording and send voice file
					try {
						int length = voiceRecorder.stopRecoding();
						if (length > 0) {
/*							sendVoice(voiceRecorder.getVoiceFilePath(),
									voiceRecorder
											.getVoiceFileName(toChatUsername),
									Integer.toString(length), false);
*/					sendFileAndLvyin(voiceRecorder.getVoiceFilePath());
							} else if (length == EMError.INVALID_FILE) {
							Toast.makeText(getApplicationContext(), "无录音权限",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), "录音时间太短",
									Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(TiChuZiXun.this, "发送失败，请检测服务器是否连接",
								Toast.LENGTH_SHORT).show();
					}
				}
				return true;
			default:
				recordingContainer.setVisibility(View.INVISIBLE);
				if (voiceRecorder != null)
					voiceRecorder.discardRecording();
				return false;
			}
		}
	}
	
	//发送文件到七牛
	public void sendFileAndLvyin(final String filenames){		
		Map<String, Object> params = new HashMap<String, Object>();
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.QiNiu, "get", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
							JSONObject jsonObj = new JSONObject(json);
							String uptoken = jsonObj.getString("uptoken");
						    String key = jsonObj.getString("key");
                            sendFiletoQiNiu(filenames,uptoken, key);
							}else{
			                       Toast.makeText(getApplicationContext(), R.string.log6, 0).show();
										}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
	private void sendFiletoQiNiu(final String file,String uptoken,String key) {
		UploadManager uploadManager = new UploadManager();
		try{
		uploadManager.put(file, key, uptoken,
		new UpCompletionHandler() {
		    @Override
			public void complete(String key, ResponseInfo info, JSONObject res) {
				// TODO Auto-generated method stub
		        Log.e("qiniu---", key + ",\r\n " + info + ",\r\n " + res);
		        try {
					String keyvalue = res.getString("key");
					
					ZXXiaoXi zxxx =new ZXXiaoXi();
					zxxx.setMtype("V");
					zxxx.setMfilelocal(file);
					zxxx.setMtext("");
					listzxxx.add(zxxx);
                    zxxiaoxi.notifyDataSetChanged();
					initTJxx(key, "VoiceMessage");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//   /storage/2A94-E4F0/DCIM/Camera/1/IMG_20351008_174652.jpg
//   /storage/2A94-E4F0/Android/data/com.rvidda.cn/ljppff1#rvidda/ljppff/voice/null20160104T164855.amr
			}
		}, new UploadOptions(null, "audio/amr", true,  new UpProgressHandler(){
            public void progress(String key, double percent){
                Log.i("qiniu", key + ": " + percent);
            }
        },null));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void initTJxx(String key,String type)
	{
		Map<String, Object> params = new HashMap<String, Object>();
	//VoiceMessage   TextMessage	
		params.put("message[body]",key);
		params.put("message[type]",type);
		com.rvidda.cn.http.HttpServiceUtil.request(ContantsUtil.HOST+"/subjects/"+ID+"/messages", "post", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
							JSONObject jsonObj = new JSONObject(json);
							
							}else{
                         Toast.makeText(getApplicationContext(), R.string.log9, 0).show();

							}
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

	}

	
	private void initView() {
		mBtn_back =(ImageView)this.findViewById(R.id.mBtn_back);
		mBtn_back.setOnClickListener(listener);
		list =(ListView)this.findViewById(R.id.list);
		zxxiaoxi =new ZXAdapter(getApplicationContext(), listzxxx,media);
		list.setAdapter(zxxiaoxi);
		mLLpp1 = (LinearLayout) this.findViewById(R.id.mLLpp1);
		mRlw1 = (RelativeLayout) this.findViewById(R.id.mRlw1);
		mRlw2 = (RelativeLayout) this.findViewById(R.id.mRlw2);
		mRlw3 = (RelativeLayout) this.findViewById(R.id.mRlw3);
		mRlw1.setOnClickListener(listener);
		mRlw2.setOnClickListener(listener);
		mRlw3.setOnClickListener(listener);

		pop1 = new popWindow1(TiChuZiXun.this, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,strarray);
		pop1.setOnSearchBarItemClickListener(this);
		pop2 = new popWindow2(TiChuZiXun.this, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		pop2.setOnSearchBar1ItemClickListener(this);

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mBtn_back:
				AppManager.getAppManager().finishActivity();
				break;
			case R.id.btn_set_mode_keyboard:
			edittext_layout.setVisibility(View.VISIBLE);
			buttonSetModeVoice.setVisibility(View.VISIBLE);
			// mEditTextContent.setVisibility(View.VISIBLE);
			mEditTextContent.requestFocus();
			// buttonSend.setVisibility(View.VISIBLE);
			buttonPressToSpeak.setVisibility(View.GONE);
				buttonSend.setVisibility(View.VISIBLE);
				buttonSetModeKeyboard.setVisibility(View.GONE);
          break;
			case R.id.btn_send:
				if(!TextUtils.isEmpty(mEditTextContent.getEditableText().toString())){
					ZXXiaoXi zxxx =new ZXXiaoXi();
					zxxx.setMtype("T");
					zxxx.setMfilelocal("");
					zxxx.setMtext(mEditTextContent.getEditableText().toString());
					listzxxx.add(zxxx);
                    zxxiaoxi.notifyDataSetChanged();
					initTJxx(mEditTextContent.getEditableText().toString(), "TextMessage");
				}
				break;
			case R.id.mRlw1:
				pop1.showAsDropDown(mLLpp1);
				break;
			case R.id.mRlw2:
				pop2.showAsDropDown(mLLpp1);
				break;
			case R.id.mRlw3:
				startActivity(new Intent(TiChuZiXun.this, Chengshi.class));
				break;

			default:
				break;
			}
		}
	};

	//修改咨询见容
	private void initChangeZX(String title,String content){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subject[title]", title);
		params.put("subject[label_ids]", content);
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.HOST+"subjects/"+ID, "put", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
							JSONObject jsonObj = new JSONObject(json);

							}else{
			                       Toast.makeText(getApplicationContext(), R.string.log6, 0).show();
										}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

	}
	
	@Override
	public void onSearchButtonClick(String string, String searchType) {
       sendBiaoQianString =string;
	    strarray = searchType.split(",");
        initChangeZX(Title, sendBiaoQianString);
	}

	@Override
	public void onSearchButtonClick1(String string, String searchType) {
      Title =string;
      initChangeZX(Title, sendBiaoQianString);

	}

}
