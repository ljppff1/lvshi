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
import android.widget.LinearLayout.LayoutParams;

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
import com.rvidda.cn.view.popWindow2;
import com.rvidda.cn.view.popWindow3;
import com.rvidda.cn.view.popWindow3.onSearchBarItemClickListener;

public class UserSetting extends BaseActivity implements onSearchBarItemClickListener {
	private CircleImageView photo_view;
	private PreferenceUtils pp;
	private ImageView mBtn_back;
	private DisplayImageOptions options;
	private TextView mtv1;
	private TextView mtv3;
	private RelativeLayout mRlw1;
	private ImageView mBtn_setting,IV5,IV4,IV3,IV2,IV1;
	private RelativeLayout mRllocal;
	private RelativeLayout mRlw3;
	private RelativeLayout mRlLw4,mRlLw3,mRlLw2,mRlLw1;
	private ImageView mIvedit;
	private TextView mtv2;
	private popWindow3 pop3;
	private TextView mTvchangebody;
	private ImageView V6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
	     pp =PreferenceUtils.getInstance(UserSetting.this);
 
		initView();
		getZXLiaobiao();
	}

	private void initView() {
		mTvchangebody =(TextView)this.findViewById(R.id.mTvchangebody);
		if(pp.getString(Content.IS_PUTONG_User, "1").equals("1")){
			mTvchangebody.setText("切换到律师身份解答问题");
		}else{
			mTvchangebody.setText("切换身份去提问");
		}

		pop3 = new popWindow3(UserSetting.this, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		pop3.setOnSearchBar1ItemClickListener(this);

		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.content_bg)
		.showImageForEmptyUri(R.drawable.content_bg)
		.showImageOnLoading(R.drawable.content_bg)
		.showImageOnFail(R.drawable.content_bg).cacheInMemory(true)
		.cacheOnDisc(true).build();
		mtv1 =(TextView)this.findViewById(R.id.mtv1);
	    if(TextUtils.isEmpty(pp.getString(Content.City, "城市"))){
	    	mtv1.setText("城市");
	    }else{
	    	mtv1.setText(pp.getString(Content.City, "城市"));
	    }
		mIvedit =(ImageView)this.findViewById(R.id.mIvedit);
		mIvedit.setOnClickListener(listener);
		mtv3 =(TextView)this.findViewById(R.id.mtv3);
		mRlw3 =(RelativeLayout)this.findViewById(R.id.mRlw3);
		mRlw3.setOnClickListener(listener);
		mRlw1 =(RelativeLayout)this.findViewById(R.id.mRlw1);
		mRlw1.setOnClickListener(listener);
		mRlLw4 =(RelativeLayout)this.findViewById(R.id.mRlLw4);
		mRlLw3 =(RelativeLayout)this.findViewById(R.id.mRlLw3);
		mRlLw2 =(RelativeLayout)this.findViewById(R.id.mRlLw2);
		mRlLw1 =(RelativeLayout)this.findViewById(R.id.mRlLw1);
		mRlLw2.setOnClickListener(listener);
		IV4 =(ImageView)this.findViewById(R.id.IV4);
		IV5 =(ImageView)this.findViewById(R.id.IV5);
		IV3 =(ImageView)this.findViewById(R.id.IV3);
		IV2 =(ImageView)this.findViewById(R.id.IV2);
		IV1 =(ImageView)this.findViewById(R.id.IV1);
		V6 =(ImageView)this.findViewById(R.id.V6);
		mtv2 =(TextView)this.findViewById(R.id.mtv2);
		if(!pp.getString(Content.IS_PUTONG_User, "1").equals("1")){
			mRlLw4.setVisibility(View.VISIBLE);
			mRlLw3.setVisibility(View.VISIBLE);
			mRlLw2.setVisibility(View.VISIBLE);
			mRlLw1.setVisibility(View.VISIBLE);
			IV4.setVisibility(View.VISIBLE);
			IV3.setVisibility(View.VISIBLE);
			IV2.setVisibility(View.VISIBLE);
			IV1.setVisibility(View.VISIBLE);
			mRlw1.setVisibility(View.GONE);
		}else{
			mRlLw4.setVisibility(View.GONE);
			mRlLw3.setVisibility(View.GONE);
			mRlLw2.setVisibility(View.GONE);
			mRlLw1.setVisibility(View.GONE);
			IV4.setVisibility(View.GONE);
			IV3.setVisibility(View.VISIBLE);
			IV2.setVisibility(View.GONE);
			IV1.setVisibility(View.GONE);
			mRlw1.setVisibility(View.VISIBLE);
		}

		if(pp.getString(Content.Is_Lawyer, "0").equals("1")){
			mRlw1.setVisibility(View.GONE);
         IV5.setVisibility(View.GONE);
		}else{			
			V6.setVisibility(View.GONE);
			IV3.setVisibility(View.GONE);
			mRlw1.setVisibility(View.VISIBLE);
	         IV5.setVisibility(View.VISIBLE);
		}
			
		mRlLw1.setOnClickListener(listener);
		mRllocal =(RelativeLayout)this.findViewById(R.id.mRllocal);
		mRllocal.setOnClickListener(listener);
		photo_view = (com.rvidda.cn.view.CircleImageView) this
				.findViewById(R.id.photo);
		photo_view.setOnClickListener(listener);
		mBtn_back =(ImageView)this.findViewById(R.id.mBtn_back);
		mBtn_back.setOnClickListener(listener);
		mBtn_setting =(ImageView)this.findViewById(R.id.mBtn_setting);
		mBtn_setting.setOnClickListener(listener);
		mBtn_setting.setVisibility(View.GONE);
		
	}
	@Override
	protected void onResume() {
	    if(TextUtils.isEmpty(pp.getString(Content.City, "城市"))){
	    	mtv1.setText("城市");
	    }else{
	    	mtv1.setText(pp.getString(Content.City, "城市"));
	    }
		initTiJiao2(pp.getString(Content.Cityid, "2"));

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
							String name =user.getString("name");
							if(!TextUtils.isEmpty(name)){
								mtv2.setText(name);
							}
							String is_lawyer = user.getInt("is_lawyer")+"";
                           if(is_lawyer.equals("1")){
                        	   mRlLw1.setVisibility(View.VISIBLE);
                        	   IV1.setVisibility(View.VISIBLE);
                        	   IV5.setVisibility(View.GONE);
                        	   mRlw1.setVisibility(View.GONE);
                           }
                           pp.put(Content.User_Name, name);
                           
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
                   	     String dd = pp.getString(Content.City, "城市");
                	     String dd1 = pp.getString(Content.Cityid, "");
                	     pp.put(Content.City_c1, dd);
                	     pp.put(Content.City_id1, dd1);

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
	//登出
	private void logout() {
		pp.clearPreference();
		AppManager.getAppManager().finishAllActivitybutthis();
		startActivity(new Intent(getApplicationContext(), Login.class));
		AppManager.getAppManager().finishActivity();
	}

	android.view.View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mRlLw2:
				startActivity(new Intent(UserSetting.this, LvShiLeiXing.class));
				break;
			case R.id.mIvedit:
				pop3.showAsDropDown(mtv2);
				break;
			case R.id.mRlLw1:
				//切换身份
				changeBody();
				break;
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
			case R.id.mRlw3:
				logout();
				break;

			default:
				break;
			}
		}


		private void changeBody() {
			if(pp.getString(Content.IS_PUTONG_User, "1").equals("1")){
				pp.put(Content.IS_PUTONG_User, "0");
				mTvchangebody.setText("切换身份到律师");
				//AppManager.getAppManager().finishAllActivitybutthis();
				startActivity(new Intent(getApplicationContext(), LvShiShouYe.class));
				AppManager.getAppManager().finishActivity();
			}else{
				pp.put(Content.IS_PUTONG_User, "1");
				mTvchangebody.setText("切换身份到用户");
				//AppManager.getAppManager().finishAllActivitybutthis();
				startActivity(new Intent(getApplicationContext(), ShouYe.class));
				AppManager.getAppManager().finishActivity();
			}
			
/*			
			AlertDialog.Builder builder = new AlertDialog.Builder(
					UserSetting.this);
			builder.setTitle("选择身份");
			// 指定下拉列表的显示数据
			final String[] cities = { "普通用户", "律师身份" };
			// 设置一个下拉的列表选择项
			builder.setItems(cities, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (which == 0) {
						if(!pp.getString(Content.IS_PUTONG_User, "1").equals("1")){
							pp.put(Content.IS_PUTONG_User, "1");
							AppManager.getAppManager().finishAllActivitybutthis();
							startActivity(new Intent(getApplicationContext(), ShouYe.class));
							AppManager.getAppManager().finishActivity();
						}
					} else {
						if(!pp.getString(Content.IS_PUTONG_User, "1").equals("0")){
							pp.put(Content.IS_PUTONG_User, "0");
							AppManager.getAppManager().finishAllActivitybutthis();
							startActivity(new Intent(getApplicationContext(), LvShiShouYe.class));
							AppManager.getAppManager().finishActivity();
						}
					}
				}
			});			
			builder.show();
*/		}


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
	private void initTiJiao1(String name)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user[name]",name);
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
	private void initTiJiao2(String areaid)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user[area_id]",areaid);
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

	@Override
	public void onSearchButtonClick1(String string, String searchType) {
		mtv2.setText(string);
		initTiJiao1(string);
		
	}


}
