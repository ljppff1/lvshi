package com.rvidda.cn.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.rvidda.cn.AppManager;
import com.rvidda.cn.BaseActivity;
import com.rvidda.cn.R;
import com.rvidda.cn.domain.ZXList;
import com.rvidda.cn.http.ContantsUtil;
import com.rvidda.cn.utils.Content;
import com.rvidda.cn.utils.PreferenceUtils;
import com.rvidda.cn.view.CircleImageView;

public class UserSetting extends BaseActivity {
	private CircleImageView photo_view;
	private PreferenceUtils pp;
	private ImageView mBtn_back;
	private DisplayImageOptions options;
	private TextView mtv1;
	private TextView mtv3;
	private RelativeLayout mRlw1;
	private ImageView mBtn_setting;
	private RelativeLayout mRllocal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
	     pp =PreferenceUtils.getInstance(UserSetting.this);

		initView();
		getZXLiaobiao();
	}

	private void initView() {
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.content_bg)
		.showImageForEmptyUri(R.drawable.content_bg)
		.showImageOnLoading(R.drawable.content_bg)
		.showImageOnFail(R.drawable.content_bg).cacheInMemory(true)
		.cacheOnDisc(true).build();
		mtv1 =(TextView)this.findViewById(R.id.mtv1);
		String city = pp.getString(Content.City, "");
		mtv1.setText(city);

		mtv3 =(TextView)this.findViewById(R.id.mtv3);
		mRlw1 =(RelativeLayout)this.findViewById(R.id.mRlw1);
		mRlw1.setOnClickListener(listener);
		mRllocal =(RelativeLayout)this.findViewById(R.id.mRllocal);
		mRllocal.setOnClickListener(listener);
		photo_view = (com.rvidda.cn.view.CircleImageView) this
				.findViewById(R.id.photo);
		photo_view.setOnClickListener(listener);
		mBtn_back =(ImageView)this.findViewById(R.id.mBtn_back);
		mBtn_back.setOnClickListener(listener);
		mBtn_setting =(ImageView)this.findViewById(R.id.mBtn_setting);
		mBtn_setting.setOnClickListener(listener);
		
	}
	@Override
	protected void onResume() {
		String city = pp.getString(Content.City, "");
		mtv1.setText(city);
		String cityId =pp.getString(Content.Cityid, "");
		String choice =pp.getString(Content.Citychoice, "0");
		super.onResume();
	}
	//用户设置
	public void getZXLiaobiao(){		
		Map<String, Object> params = new HashMap<String, Object>();
		com.rvidda.cn.http.HttpServiceUtil.request(com.rvidda.cn.http.ContantsUtil.PersonMe, "get", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
/**
 * {"user":{"id":15,"name":null,"avatar":null,
 * "avatar_url":"http://7u2gfi.com1.z0.glb.clouddn.com/Fhab9G1MW-m0MPH0mqVk3QAmUvDa",
 * "mobile":"13652435378",
 * "email":"","is_lawyer":0,"hx_user":"hx_15","hx_password":"password_15"}}
 */
							JSONObject jsonObj = new JSONObject(json);
							JSONObject user = jsonObj.getJSONObject("user");
							String avatar_url = user.getString("avatar_url");
							String mobile = user.getString("mobile");
							String is_lawyer = user.getInt("is_lawyer")+"";

							pp.put(Content.Avator_Url, avatar_url);
							pp.put(Content.Mobile, mobile);
							pp.put(Content.Is_Lawyer, is_lawyer);
							ImageLoader.getInstance().displayImage(avatar_url,photo_view , options);		
							if(mobile.length()>7){
							mtv3.setText(mobile.substring(0, 3)+"****"+mobile.substring(7));
							}
							
							JSONObject area = user.getJSONObject("area");
							String city = area.getString("city");
							String id = area.getString("id");
                            pp.put(Content.City, city);
                            pp.put(Content.Cityid, id);
							mtv1.setText(city);
							}else{
			                       Toast.makeText(getApplicationContext(), R.string.log6, 0).show();
										}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	android.view.View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRllocal:
			Intent intent =	new Intent(getApplicationContext(), Chengshi.class);
			intent.putExtra("WHAT", "user");
				startActivity(intent);
				break;
			case R.id.mRlw1:
				startActivity(new Intent(UserSetting.this, ShenQingQiliao.class));
				break;
			case R.id.photo:
				takephoto();
				break;
			case R.id.mBtn_back:
				AppManager.getAppManager().finishActivity();
				break;
			case R.id.mBtn_setting:
				startActivity(new Intent(UserSetting.this, UserSetting1.class));
				break;

			default:
				break;
			}
		}

		private void takephoto() {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					UserSetting.this);
			builder.setTitle("加入照片");
			// 指定下拉列表的显示数据
			final String[] cities = { "拍摄照片", "选择照片" };
			// 设置一个下拉的列表选择项
			builder.setItems(cities, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (which == 0) {
						cameraPhoto();
					} else {
						systemPhoto();
					}
				}

				private void systemPhoto() {
					Intent openAlbumIntent = new Intent(
							Intent.ACTION_GET_CONTENT);
					openAlbumIntent.setDataAndType(
							MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
							"image/*");
					startActivityForResult(openAlbumIntent, 1);
				}

				private void cameraPhoto() {
					Uri imageUri = null;
					String fileName = null;
					Intent openCameraIntent = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					// 删除上一次截图的临时文件
					SharedPreferences sharedPreferences = getSharedPreferences(
							"temp", Context.MODE_WORLD_WRITEABLE);
					com.rvidda.cn.utils.ImageTools.deletePhotoAtPathAndName(
							Environment.getExternalStorageDirectory()
									.getAbsolutePath(), sharedPreferences
									.getString("tempName", ""));

					// 保存本次截图临时文件名字
					fileName = String.valueOf(System.currentTimeMillis())
							+ ".jpg";
					Editor editor = sharedPreferences.edit();
					editor.putString("tempName", fileName);
					editor.commit();
					imageUri = Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), fileName));
					// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
					openCameraIntent
							.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(openCameraIntent, 1);
				}
			});
			builder.show();

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {

			case 1:
				Uri uri = null;
				if (data != null) {
					uri = data.getData();
					System.out.println("Data");
				} else {
					System.out.println("File");
					String fileName = getSharedPreferences("temp",
							Context.MODE_WORLD_WRITEABLE).getString("tempName",
							"");
					uri = Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), fileName));
				}
				cropImage(uri, 200, 200, 2);
				break;
			case 2:
				Bitmap photo = null;
				Uri photoUri = data.getData();
				if (photoUri != null) {
					photo = BitmapFactory.decodeFile(photoUri.getPath());
				}
				if (photo == null) {
					Bundle extra = data.getExtras();
					if (extra != null) {
						photo = (Bitmap) extra.get("data");
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					}
				}
				photo_view.setImageBitmap(photo);
				String path1 = com.rvidda.cn.utils.ImageTools.savePhotoToSDCard(photo, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
				if(!TextUtils.isEmpty(path1)){
					sendFileAndLvyin(path1);
				}

				break;

			default:
				break;
			}
		}
	}

	public void cropImage(Uri uri, int outputX, int outputY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, requestCode);
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
					initTiJiao(keyvalue);					
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
	
	private void initTiJiao(String filepath)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user[avatar]",filepath);
		com.rvidda.cn.http.HttpServiceUtil.request(ContantsUtil.PersonMe, "put", params,
				new com.rvidda.cn.http.HttpServiceUtil.CallBack() {
					@Override
					public void callback(String json) {
						try {
							if(!json.equals("0")){
/*
 */
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


}
