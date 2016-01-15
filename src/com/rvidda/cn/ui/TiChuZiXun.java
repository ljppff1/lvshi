package com.rvidda.cn.ui;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
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
import com.easemob.util.PathUtil;
import com.easemob.util.VoiceRecorder;
import com.fanxin.app.DemoApplication;
import com.fanxin.app.adapter.MessageAdapter;
import com.fanxin.app.adapter.VoicePlayClickListener;
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
import com.rvidda.cn.adapter.ZXAdapter1;
import com.rvidda.cn.domain.ZXXiaoXi;
import com.rvidda.cn.http.ContantsUtil;
import com.rvidda.cn.utils.Content;
import com.rvidda.cn.utils.Media;
import com.rvidda.cn.utils.PreferenceUtils;
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
	private String LENGTH;
    private String CHENGSHIID="";
	private ImageView iv_setting_group;
	@SuppressLint("HandlerLeak")
	private Handler micImageHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			// 切换msg切换图片
			micImage.setImageDrawable(micImages[msg.what]);
		}
	};

	public static final int REQUEST_CODE_CAMERA = 18;
	public static final int REQUEST_CODE_LOCAL = 19;

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
	private ZXAdapter1 zxxiaoxi;
	private ImageView mBtn_back;
	private PreferenceUtils pp;
	private TextView mTvdd;
	private String TYPE;
	private ImageView btn_picture;
	private ImageView btn_take_picture;
	private Button btn_set_mode_keyboard;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tichuzixun);
	     pp =PreferenceUtils.getInstance(TiChuZiXun.this);
		media =new Media();
        getData();
		initHuanXinView();
		initView();
		setUpView();

	}

	@Override
	protected void onResume() {
		
			mTvdd.setText(pp.getString(Content.City_c1, "城市"));
			CHENGSHIID =pp.getString(Content.City_id1, "");
			 initChangeZX(Title, sendBiaoQianString);
		super.onResume();
	}
	private void getData() {
		
		listzxxx =new ArrayList<ZXXiaoXi>();

		ID =getIntent().getExtras().getString("ID");
		SELECT =getIntent().getExtras().getString("SELECT");
		TYPE =getIntent().getExtras().getString("TYPE");
	    strarray = SELECT.split(",");
	    Title ="";
        sendBiaoQianString =SELECT;
		if(!TextUtils.isEmpty(ID)){
			if(TextUtils.isEmpty(TYPE)){
			    String FILE_pro = getIntent().getExtras().getString("FILE");
			    String echo = getIntent().getExtras().getString("echo");
	   String time = echo.substring(0, 13);
	   String length = echo.substring(14, echo.length());
		ZXXiaoXi zxxx =new ZXXiaoXi();
		zxxx.setMtype("VoiceMessage");
		zxxx.setMfilelocal(FILE_pro);
		zxxx.setTime(changetime(time));
		zxxx.setLength(length);
		zxxx.setMtext("");
		listzxxx.add(zxxx);
			}else{
				initgetZiXunLook();
			}
		}else{
			initTiJiaoFirst("");
		}
	}
	
	private String changetime(String time){
		DateFormat formatter = new SimpleDateFormat("MM-dd hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(time));
        return  formatter.format(calendar.getTime());  
	}
	/**
	 *得到咨询信息
	 */
	private void initgetZiXunLook()
	{
	
		Map<String, Object> params = new HashMap<String, Object>();
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.HOST+"/subjects/"+ID, "get", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
			/**
{"subject":{"id":14,"title":"33","created_at":"2015-11-16 17:44:20","updated_at":"2015-12-28 21:12:26","
user_businesses":[],"lawyers":[],"messages":[{"id":30,"body":"bbbbbbbbbb","type":"TextMessage","echo":
null,"created_at":"2015-11-16 17:44:20"},{"id":162,"body":"http://7u2gfi.com1.z0.glb.clouddn.com/
20151229142429599088942-57404430","type":"VoiceMessage","echo":null,"created_at":"2015-12-29 14:24:30"}],"labels"
:[],"order":{"id":238,"state":"init","uuid":"40SR-N6IF-BRP0","total_price":"0.01"}}}

			 *{"subject":{"id":181,"title":"","created_at":"2016-01-06 13:22:56",
			 *"updated_at":"2016-01-06 13:22:56","user_businesses":[],"lawyers":[],
			 *"messages":[{"id":383,"body":"http://7u2gfi.com1.z0.glb.clouddn.com/20160106132248594879667-40107357","type":"ImageMessage","echo":"1452057776000","created_at":"2016-01-06 13:22:56"}],"labels":[]}}
			 */
							JSONObject jsonObj = new JSONObject(json);
							JSONObject subject = jsonObj.getJSONObject("subject");
							JSONArray messages = subject.getJSONArray("messages");
							for(int i=0;i<messages.length();i++){
							  ZXXiaoXi zx =new ZXXiaoXi();
								 ((JSONObject)messages.get(i)).getString("body");
								 ((JSONObject)messages.get(i)).getString("type");
								 ((JSONObject)messages.get(i)).getString("created_at");
								 
							  
								 zx.setKeyId("");
								 zx.setMtype(((JSONObject)messages.get(i)).getString("type"));
								 zx.setMtext(((JSONObject)messages.get(i)).getString("body"));
								 zx.setMfilelocal("");
									String echo = ((JSONObject)messages.get(i)).getString("echo");
								if(echo.length()>=12){
								 if(((JSONObject)messages.get(i)).getString("type").equals("VoiceMessage")){
									   String time = echo.substring(0, 13);
									   String length = echo.substring(14, echo.length());
									   zx.setLength(length);
									   zx.setTime(changetime(time));
								 }else{
									   String time = echo.substring(0, 13);
									 zx.setTime(changetime(time));
								 }
								}else{
									zx.setTime("");
									zx.setLength("");
								}
								listzxxx.add(zx);
							}
							zxxiaoxi.notifyDataSetChanged();list.setSelection(list.getCount()-1);
							}else{
			                       Toast.makeText(getApplicationContext(), R.string.log6, 0).show();
										}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
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
		buttonSend.setVisibility(View.GONE);
		buttonPressToSpeak = findViewById(R.id.btn_press_to_speak);
		expressionViewpager = (ViewPager) findViewById(R.id.vPager);
		emojiIconContainer = (LinearLayout) findViewById(R.id.ll_face_container);
		btnContainer = (LinearLayout) findViewById(R.id.ll_btn_container);
		iv_emoticons_normal = (ImageView) findViewById(R.id.iv_emoticons_normal);
		iv_emoticons_checked = (ImageView) findViewById(R.id.iv_emoticons_checked);
		loadmorePB = (ProgressBar) findViewById(R.id.pb_load_more);
		btnMore = (Button) findViewById(R.id.btn_more);
		btn_picture =(ImageView)this.findViewById(R.id.btn_picture);
		btn_take_picture =(ImageView)this.findViewById(R.id.btn_take_picture);
		btn_picture.setOnClickListener(listener);
		btn_take_picture.setOnClickListener(listener);
		btnMore.setOnClickListener(listener);
		more = findViewById(R.id.more);
		iv_emoticons_checked.setVisibility(View.INVISIBLE);
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
					btnMore.setVisibility(View.VISIBLE);
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
		btnMore.setVisibility(View.VISIBLE);
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
						LENGTH= length+"";
						if (length > 0) {
/*							sendVoice(voiceRecorder.getVoiceFilePath(),
									voiceRecorder
											.getVoiceFileName(toChatUsername),
									Integer.toString(length), false);
*/					sendFileAndLvyin(voiceRecorder.getVoiceFilePath(),"VoiceMessage");
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
	public void sendFileAndLvyin(final String filenames,final String type){		
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
                            sendFiletoQiNiu(filenames,uptoken, key,type);
							}else{
			                       Toast.makeText(getApplicationContext(), R.string.log6, 0).show();
										}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
	private void sendFiletoQiNiu(final String file,String uptoken,String key,final String type) {
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
					zxxx.setMtype(type);
					zxxx.setMfilelocal(file);
					zxxx.setMtext("");
					zxxx.setLength(LENGTH);
					zxxx.setTime(changetime(System.currentTimeMillis()+""));
					listzxxx.add(zxxx);
                    zxxiaoxi.notifyDataSetChanged();list.setSelection(list.getCount()-1);
                    if(!TextUtils.isEmpty(ID)){
					initTJxx(key, type);
                    }else{
                    initTiJiao(key, type);
                    }

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//   /storage/2A94-E4F0/DCIM/Camera/1/IMG_20351008_174652.jpg
//   /storage/2A94-E4F0/Android/data/com.rvidda.cn/ljppff1#rvidda/ljppff/voice/null20160104T164855.amr
			}
		}, new UploadOptions(null, "audio/amr", true,  new UpProgressHandler(){
            public void progress(String key, double percent){
                Log.e("qiniu", key + ": " + percent);
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
		if(type.equals("VoiceMessage")){
			params.put("message[echo]", System.currentTimeMillis()+"_"+LENGTH);
		}else{
			params.put("message[echo]", System.currentTimeMillis());
		}
		
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
   
	private void initTiJiao(String key,String type)
	{
       try{		
	    JSONObject obj = new JSONObject();
		obj.put("body",key);
	    obj.put("type",type);
		if(type.equals("VoiceMessage")){
			obj.put("echo", System.currentTimeMillis()+"_"+LENGTH);
		}else{
			obj.put("echo", System.currentTimeMillis());
		}

      org.json.JSONArray ja =new org.json.JSONArray();
      ja.put(obj);
       JSONObject j2 =new JSONObject();
       j2.put("title", Title);
       j2.put("messages_attributes", ja);
       
       JSONObject jf =new JSONObject();
       jf.put("subject", j2);
       
      	Map<String, Object> params = new HashMap<String, Object>();
		params.put("json", jf.toString());

		com.rvidda.cn.http.HttpServiceUtil.request(ContantsUtil.TiJiaoZiX, "post1", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
/**
 * {"subject":{"id":177,"title":"lj","created_at":"2016-01-06 10:02:24",
 * "updated_at":"2016-01-06 10:02:24","user_businesses":[],"lawyers":[],"messages":[],"labels":[]}}
 */
							JSONObject jsonObj = new JSONObject(json);
							JSONObject subject = jsonObj.getJSONObject("subject");
						   Integer id = subject.getInt("id");
						   ID =id+"";
							}else{
                       Toast.makeText(getApplicationContext(), R.string.log9, 0).show();

							}
/*						             startActivity(new Intent(getApplicationContext(), ShouYe.class));
								 AppManager.getAppManager().finishActivity();
*/
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
       }catch(Exception e){
    	   
       }

	}

	
	private void initView() {
		mTvdd =(TextView)this.findViewById(R.id.mTvdd);
		mTvdd.setText(pp.getString(Content.City_c1, "城市"));
		mBtn_back =(ImageView)this.findViewById(R.id.mBtn_back);
		mBtn_back.setOnClickListener(listener);
		list =(ListView)this.findViewById(R.id.list);
		zxxiaoxi =new ZXAdapter1(TiChuZiXun.this, listzxxx,media);
		list.setAdapter(zxxiaoxi);
		list.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				hideKeyboard();
				more.setVisibility(View.GONE);
				btnContainer.setVisibility(View.GONE);
				return false;
			}
		});
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
			case R.id.btn_picture:
				selectPicFromLocal(); // 点击图片图标
				break;
			case R.id.btn_take_picture:
				selectPicFromCamera();// 点击照相图标
				break;
			case R.id.btn_more:
				if (more.getVisibility() == View.GONE) {
					System.out.println("more gone");
					hideKeyboard();
					more.setVisibility(View.VISIBLE);
					btnContainer.setVisibility(View.VISIBLE);
					emojiIconContainer.setVisibility(View.GONE);
				} else {
					if (emojiIconContainer.getVisibility() == View.VISIBLE) {
						emojiIconContainer.setVisibility(View.GONE);
						btnContainer.setVisibility(View.VISIBLE);
						iv_emoticons_normal.setVisibility(View.VISIBLE);
						iv_emoticons_checked.setVisibility(View.GONE);
					} else {
						more.setVisibility(View.GONE);
					}

				}

				break;
			case R.id.mBtn_back:
				shouruanjianpan();
				AppManager.getAppManager().finishActivity();
				break;
			case R.id.btn_set_mode_keyboard:
				edittext_layout.setVisibility(View.VISIBLE);
				more.setVisibility(View.GONE);
				buttonSetModeKeyboard.setVisibility(View.GONE);
				buttonSetModeVoice.setVisibility(View.VISIBLE);
				// mEditTextContent.setVisibility(View.VISIBLE);
				mEditTextContent.requestFocus();
				// buttonSend.setVisibility(View.VISIBLE);
				buttonPressToSpeak.setVisibility(View.GONE);
				if (TextUtils.isEmpty(mEditTextContent.getText())) {
					btnMore.setVisibility(View.VISIBLE);
					buttonSend.setVisibility(View.GONE);
				} else {
					btnMore.setVisibility(View.GONE);
					buttonSend.setVisibility(View.VISIBLE);
				}
          break;
			case R.id.btn_send:
				shouruanjianpan();
				if(!TextUtils.isEmpty(mEditTextContent.getEditableText().toString())){
					ZXXiaoXi zxxx =new ZXXiaoXi();
					zxxx.setMtype("TextMessage");
					zxxx.setMfilelocal("");
					zxxx.setTime(changetime(System.currentTimeMillis()+""));
					zxxx.setMtext(mEditTextContent.getEditableText().toString());
					listzxxx.add(zxxx);
					
                    zxxiaoxi.notifyDataSetChanged();list.setSelection(list.getCount()-1);
                    if(!TextUtils.isEmpty(ID)){
					initTJxx(mEditTextContent.getEditableText().toString(), "TextMessage");
                    }else{
                    initTiJiao(mEditTextContent.getEditableText().toString(), "TextMessage");
                    }
				}
				break;
			case R.id.mRlw1:
				shouruanjianpan();
				if(!TextUtils.isEmpty(ID)){
				pop1.showAsDropDown(mLLpp1);
				}else{
					Toast.makeText(getApplicationContext(), R.string.log15, 0).show();
				}
				break;
			case R.id.mRlw2:
				shouruanjianpan();
				if(!TextUtils.isEmpty(ID)){
				pop2.showAsDropDown(mLLpp1);
				}else{
					Toast.makeText(getApplicationContext(), R.string.log15, 0).show();
				}
				break;
			case R.id.mRlw3:
				shouruanjianpan();
				Intent intent =	new Intent(getApplicationContext(), Chengshi.class);
				intent.putExtra("WHAT", "b");
					startActivity(intent);

				break;

			default:
				break;
			}
		}
	};
	private void shouruanjianpan(){
		InputMethodManager imm = (InputMethodManager) getApplicationContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEditTextContent.getWindowToken(), 0);

	}
	
  @Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			 if (requestCode == REQUEST_CODE_CAMERA) { // 发送照片
					if (cameraFile != null && cameraFile.exists())
						Log.e("cameraFile.getAbsolutePath()------>>>>",
								cameraFile.getAbsolutePath());
					sendPicture(cameraFile.getAbsolutePath(), false);
				} else if (requestCode == REQUEST_CODE_LOCAL) { // 发送本地图片
					if (data != null) {
						Uri selectedImage = data.getData();
						if (selectedImage != null) {
							sendPicByUri(selectedImage);
						}
					}
				} 
		}
	  }
	/**
	 * 发送图片
	 * 
	 * @param filePath
	 */
	private void sendPicture(final String filePath, boolean is_share) {
	  //  sendFileAndLvyin(filePath, "ImageMessage");
		ZXXiaoXi zxxx =new ZXXiaoXi();
		zxxx.setMtype("ImageMessage");
		zxxx.setMfilelocal(filePath);
		zxxx.setMtext("");
		zxxx.setLength(ID);
		zxxx.setTime(changetime(System.currentTimeMillis()+""));
		listzxxx.add(zxxx);
        zxxiaoxi.notifyDataSetChanged();list.setSelection(list.getCount()-1);

	    
	    
	    
	}

	/**
	 * 根据图库图片uri发送图片
	 * 
	 * @param selectedImage
	 */
	private void sendPicByUri(Uri selectedImage) {
		// String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(selectedImage, null, null,
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex("_data");
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			cursor = null;

			if (picturePath == null || picturePath.equals("null")) {
				Toast toast = Toast.makeText(this, "找不到图片", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}
			sendPicture(picturePath, false);
		} else {
			File file = new File(selectedImage.getPath());
			if (!file.exists()) {
				Toast toast = Toast.makeText(this, "找不到图片", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;

			}
			sendPicture(file.getAbsolutePath(), false);
		}

	}


	/**
	 * 照相获取图片
	 */
	public void selectPicFromCamera() {
		if (!CommonUtils.isExitsSdcard()) {
			Toast.makeText(getApplicationContext(), "SD卡不存在，不能拍照",
					Toast.LENGTH_SHORT).show();
			return;
		}

		cameraFile = new File(PathUtil.getInstance().getImagePath(),
				DemoApplication.getInstance().getUserName()
						+ System.currentTimeMillis() + ".jpg");
		cameraFile.getParentFile().mkdirs();
		startActivityForResult(
				new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
						MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
				REQUEST_CODE_CAMERA);
	}
	/**
	 * 从图库获取图片
	 */
	public void selectPicFromLocal() {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");

		} else {
			intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, REQUEST_CODE_LOCAL);
	}


	//修改咨询见容
	private void initChangeZX(String title,String content){
	try{
       org.json.JSONArray ja1 =new org.json.JSONArray();
       if(!TextUtils.isEmpty(content)){
		String[] str =content.split(",");
   		for(int i=0;i<str.length;i++){
   		 ja1.put(str[i]);
   		}
       }
       JSONObject j2 =new JSONObject();
       j2.put("title", title);
	j2.put("area_id", CHENGSHIID);
       j2.put("label_ids", ja1);       
       JSONObject jf =new JSONObject();
       jf.put("subject", j2);
       
      	Map<String, Object> params = new HashMap<String, Object>();
		params.put("json", jf.toString());

		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.HOST+"/subjects/"+ID, "put1", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
							JSONObject jsonObj = new JSONObject(json);
                            
							}else{
			                Toast.makeText(getApplicationContext(), R.string.log10, 0).show();
								}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}catch (JSONException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	}
	
	private void initTiJiaoFirst(String key)
	{
	    try {
		    JSONObject obj = new JSONObject();
			

           JSONObject j2 =new JSONObject();
           
           JSONObject jf =new JSONObject();
           jf.put("subject", j2);
           
          	Map<String, Object> params = new HashMap<String, Object>();
    		params.put("json", jf.toString());
    		com.rvidda.cn.http.HttpServiceUtil.request(ContantsUtil.TiJiaoZiX, "post1", params,
    				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
    					@Override
    					public void callback(String json) {
    						try {
    							if(!json.equals("0")){
    /**
     * {"subject":{"id":177,"title":"lj","created_at":"2016-01-06 10:02:24",
     * "updated_at":"2016-01-06 10:02:24","user_businesses":[],"lawyers":[],"messages":[],"labels":[]}}
     */
    							JSONObject jsonObj = new JSONObject(json);
    							JSONObject subject = jsonObj.getJSONObject("subject");
    						    ID = subject.getInt("id")+"";
    							}else{
                           Toast.makeText(getApplicationContext(), R.string.log9, 0).show();

    							}
    					
    						} catch (JSONException e) {
    							e.printStackTrace();
    						}
    					}
    				});
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
