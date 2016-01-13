package com.rvidda.cn.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.rvidda.cn.AppManager;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.http.ContantsUtil;
import com.rvidda.cn.utils.Content;
import com.rvidda.cn.utils.LoadingDialog;
import com.rvidda.cn.utils.PreferenceUtils;

public class ShenQingQiliao extends BaseActivity {

	private RelativeLayout mRlwhatn;
	private EditText mEt1,mEt2,mEt3,mEt4;
	private TextView mtv2;
	private ImageView mIvziye;
	private static final int TAKE_PICTURE = 0;
	private static final int CHOOSE_PICTURE = 1;
    private String certificate_id;
	private ImageView mBtn_back;
	private TextView mTvdd;
	private LoadingDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shenqing);
	     dialog =new LoadingDialog(ShenQingQiliao.this, "正在提交，请稍后");
	     pp =PreferenceUtils.getInstance(ShenQingQiliao.this);
		initView();

	}
	@Override
	protected void onResume() {
		mTvdd.setText(pp.getString(Content.City_c1, "城市"));

		super.onResume();
	}

	private void initView() {
		mTvdd =(TextView)this.findViewById(R.id.mTvdd1);
		
		mTvdd.setText(pp.getString(Content.City_c1, "城市"));

		mBtn_back =(ImageView)this.findViewById(R.id.mBtn_back);
		mBtn_back.setOnClickListener(listener);
		mEt1 =(EditText)this.findViewById(R.id.mEt1);
		mEt2 =(EditText)this.findViewById(R.id.mEt2);
		mEt3 =(EditText)this.findViewById(R.id.mEt3);
		mEt4 =(EditText)this.findViewById(R.id.mEt4);
		mtv2 =(TextView)this.findViewById(R.id.mtv2);
		mtv2.setOnClickListener(listener);
		mIvziye =(ImageView)this.findViewById(R.id.mIvziye);
		mIvziye.setOnClickListener(listener);
		mRlwhatn = (RelativeLayout) this.findViewById(R.id.mRlwhatn);
		mRlwhatn.setOnClickListener(listener);

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mBtn_back:
				AppManager.getAppManager().finishActivity();
				break;
			case R.id.mIvziye:
				takephoto();
				break;
			case R.id.mtv2:
				if(TextUtils.isEmpty(mEt1.getEditableText().toString())){
					Toast.makeText(getApplicationContext(), R.string.log11,0).show();
				}else if(TextUtils.isEmpty(mEt2.getEditableText().toString())){
					Toast.makeText(getApplicationContext(), R.string.log12,0).show();
				}else if(TextUtils.isEmpty(mEt4.getEditableText().toString())){
					Toast.makeText(getApplicationContext(), R.string.log13,0).show();
				}else if(TextUtils.isEmpty(certificate_id)){
					Toast.makeText(getApplicationContext(), R.string.log14,0).show();
				}else{
					dialog.show();
					initTiJiao();
				}
				break;
			case R.id.mRlwhatn:
				Intent intent =	new Intent(getApplicationContext(), Chengshi.class);
				intent.putExtra("WHAT", "b");
					startActivity(intent);
				break;
			default:
				break;
			}
		}
	};
	private PreferenceUtils pp;

	private void takephoto() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ShenQingQiliao.this);
		builder.setTitle("加入照片");
		// 指定下拉列表的显示数据
		final String[] cities = { "拍摄照片", "选择照片" };
		// 设置一个下拉的列表选择项
		builder.setItems(cities, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					systemPhoto();
				} else {
					cameraPhoto();
				}
			}

			private void systemPhoto() {
				Uri imageUri = null;
				String fileName = null;
				Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileName = "image.jpg";
				imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
				//指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
				openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(openCameraIntent, TAKE_PICTURE);
			}

			private void cameraPhoto() {
				Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
				openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);

			}
		});
		builder.show();

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PICTURE:
				//将保存在本地的图片取出并缩小后显示在界面上
				Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/image.jpg");
				Bitmap newBitmap = com.rvidda.cn.utils.ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / 4, bitmap.getHeight() / 4);
				//由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
				bitmap.recycle();
				
				//将处理过的图片显示在界面上，并保存到本地
				mIvziye.setImageBitmap(newBitmap);
				String path = com.rvidda.cn.utils.ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
				if(!TextUtils.isEmpty(path)){
					sendFileAndLvyin(path);
				}
				break;

			case CHOOSE_PICTURE:
				ContentResolver resolver = getContentResolver();
				//照片的原始资源地址
				Uri originalUri = data.getData(); 
	            try {
	            	//使用ContentProvider通过URI获取原始图片
					Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
					if (photo != null) {
						//为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
						Bitmap smallBitmap = com.rvidda.cn.utils.ImageTools.zoomBitmap(photo, photo.getWidth() / 4, photo.getHeight() / 4);
						//释放原始图片占用的内存，防止out of memory异常发生
						photo.recycle();
						mIvziye.setImageBitmap(smallBitmap);
						String path1 = com.rvidda.cn.utils.ImageTools.savePhotoToSDCard(smallBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
						if(!TextUtils.isEmpty(path1)){
							sendFileAndLvyin(path1);
						}

						
					}
				} catch (FileNotFoundException e) {
				    e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
			default:
				break;
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
	private void sendFiletoQiNiu(String file,String uptoken,String key) {
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
					certificate_id =keyvalue;					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//   /storage/2A94-E4F0/DCIM/Camera/1/IMG_20351008_174652.jpg
//   /storage/2A94-E4F0/Android/data/com.rvidda.cn/ljppff1#rvidda/ljppff/voice/null20160104T164855.amr
			}
		}, new UploadOptions(null, "", true,  new UpProgressHandler(){
            public void progress(String key, double percent){
                Log.i("qiniu", key + ": " + percent);
            }
        },null));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void initTiJiao()
	{
		
	     pp =PreferenceUtils.getInstance(ShenQingQiliao.this);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lawyer[real_name]",mEt1.getEditableText().toString());
		params.put("lawyer[service_tel]",pp.getString(Content.Mobile, "13652435378"));
		params.put("lawyer[service_email]",mEt2.getEditableText().toString());
		params.put("lawyer[area_id]","1");
		params.put("lawyer[certificate_id]",certificate_id);
		params.put("lawyer[certificate_no]",mEt4.getEditableText().toString());
		com.rvidda.cn.http.HttpServiceUtil.request(ContantsUtil.TJlawyer, "post", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
/**{"lawyer":{"id":17,"real_name":"刘俊","service_tel":"13652435378","service_email":"ljppff@163.com","avatar":null,
 * "status":"init","created_at":"2016-01-06 16:32:06","done_subjects_count":0}}
 * 
 */
							JSONObject jsonObj = new JSONObject(json);
							pp.put(Content.Is_Lawyer, 1+"");
							if(dialog.isShowing()){
								dialog.cancel();
							}
							AppManager.getAppManager().finishActivity();
							}else{
                       Toast.makeText(getApplicationContext(), R.string.log9, 0).show();
						if(dialog.isShowing()){
							dialog.cancel();
						}

							}
/*						             startActivity(new Intent(getApplicationContext(), ShouYe.class));
								 AppManager.getAppManager().finishActivity();
*/
							
						} catch (JSONException e) {
							if(dialog.isShowing()){
								dialog.cancel();
							}

							e.printStackTrace();
						}
					}
				});

	}
	
	
}
