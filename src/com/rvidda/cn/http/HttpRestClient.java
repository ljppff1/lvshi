package com.rvidda.cn.http;

import android.annotation.SuppressLint;
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
public class HttpRestClient
{
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

	public static HttpUrls getmHttpUrls()
	{
		return mHttpUrls;
	}

	public static void setmHttpUrls(HttpUrls mHttpUrls)
	{
		HttpRestClient.mHttpUrls = mHttpUrls;
	}

	/**
	 * 例子命名以doHttp开头
	 * 
	 * @param name
	 * @param pas
	 * @param handler
	 */
	/********************* 登录 注册修改密码 忘记密码请求方法 *************************************/
	public static void doHttpLogin(Context context, String name, String pas,
			AsyncHttpResponseHandler handler)
	{
		RequestParams requestParams = new RequestParams();
		requestParams.put("name", name);
		requestParams.put("password", pas);
		mAsyncHttpClient.post(context, "", requestParams, handler);
	}

	public static void getHttpTop(Context context, int num,
			JsonHttpResponseHandler handler)
	{
		// RequestParams params=new RequestParams();
		// params.put("method", str);
		// params.put("page", num+"");
		// params.put("pageIndex", pageNum+"");
		// params.put("val", "100511D3BE5301280E0992C73A9DEC41");

		mAsyncHttpClient.get(context, HttpUrls.WEB_TOP + "&page=" + num,
				handler);
	}

	public static void getHttpTopscroll(Context context,
			JsonHttpResponseHandler handler)
	{
		// RequestParams params=new RequestParams();
		// params.put("method", str);
		// params.put("page", num+"");
		// params.put("pageIndex", pageNum+"");
		// params.put("val", "100511D3BE5301280E0992C73A9DEC41");

		mAsyncHttpClient.get(context, HttpUrls.WEB_TOP_scroll, handler);
	}

	public static void getHttpdata(Context context, int num, String type,
			JsonHttpResponseHandler handler)
	{
		// RequestParams params=new RequestParams();
		mAsyncHttpClient.get(context, HttpUrls.WEB_data + "&type=" + type
				+ "&page=" + num, handler);
	}

	public static void getHttpDetail(Context context, String id,
			JsonHttpResponseHandler handler)
	{
		mAsyncHttpClient.get(context, HttpUrls.WEB_DETAIL + "&id=" + id,
				handler);
	}

	public static void getHttpSearch(Context context, int num, String key,
			JsonHttpResponseHandler handler)
	{
		// RequestParams params=new RequestParams();
		mAsyncHttpClient.get(context, HttpUrls.WEB_SEARCH + "&page=" + num
				+ "&search=" + key, handler);
	}

	public static void getHttpHuaShangha(Context context, String str,String st,
			RequestParams requestParams, JsonHttpResponseHandler handler)
	{
		// RequestParams params=new RequestParams();
		mAsyncHttpClient.get(context, HttpUrls.BaseUrl+st+"/"+ str, requestParams,
				handler);
	}

	public static void postHttpHuaShangha(Context context, String str,String st,
			RequestParams requestParams, JsonHttpResponseHandler handler)
	{
		// RequestParams params=new RequestParams();
		mAsyncHttpClient.post(context, HttpUrls.BaseUrl +st+"/"+ str, requestParams,
				handler);
	}

	public static void patchHttpHuaShangha(Context context, String str,
			RequestParams requestParams, JsonHttpResponseHandler handler)
	{
		// RequestParams params=new RequestParams();
		mAsyncHttpClient.patch(context, HttpUrls.BaseUrl + str, requestParams,
				handler);
	}
}
