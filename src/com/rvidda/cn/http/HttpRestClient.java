package com.rvidda.cn.http;

import android.content.Context;

/*Make asynchronous HTTP requests, handle responses in anonymous callbacks 
 HTTP requests happen outside the UI thread 
 Requests use a threadpool to cap concurrent resource usage 
 GET/POST params builder (RequestParams) 
 Multipart file uploads with no additional third party libraries 
 Tiny size overhead to your application, only 19kb for everything 
 Automatic smart request retries optimized for spotty mobile connections 
 Automatic gzip response decoding support for super-fast requests 
 Optional built-in response parsing into JSON (JsonHttpResponseHandler) 
 Optional persistent cookie store, saves cookies into your app's SharedPreferences
 */

/**
 * 
 * 所有执行http请求操作调用此类
 */
public class HttpRestClient {
	private static HttpUrls mHttpUrls = new HttpUrls();

	/**
	 * 上传分隔符
	 */
	/*
	 * public static final String BOUNDARY =
	 * "------------------------- --265001916915724";
	 *//**
	 * 上传文件结束标记
	 */
	/*
	 * private static final String FILEUPLOAD_ENDTAG = "\r\n--" + BOUNDARY +
	 * "--" + "\r\n";
	 */

	public static AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();

	public static HttpUrls getmHttpUrls() {
		return mHttpUrls;
	}

	public static void setmHttpUrls(HttpUrls mHttpUrls) {
		HttpRestClient.mHttpUrls = mHttpUrls;
	}
	
	/**
	 * 创建用户，取验证码
	 */
	public static void postLogin(Context context,RequestParams requestParams,String json,
			JsonHttpResponseHandler handler) {
		// RequestParams params=new RequestParams();
		mAsyncHttpClient.post(context, HttpUrls.BaseUrl +"/users",
				requestParams,json, handler);
	}

	
	
}
