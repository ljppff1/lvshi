package com.rvidda.cn.http;

import org.json.JSONException;
import org.json.JSONTokener;

import android.os.Message;

/**
 * asyncHttpClient.post(null,new SerializableHttpResponseHandler() {
 * 
 * @Override public Object onParseResponse(String cotent) { return null; } });
 *           http请求对调函数,将http返回的内容封装为自己想要的数据格式
 * 
 */
public abstract class ObjectHttpResponseHandler extends
        AsyncHttpResponseHandler
{
    protected static final int SUCCESS_SERILIZABLE_MESSAGE = 102;

    public ObjectHttpResponseHandler()
    {
        super();
    }

    /**
     * 成功返回
     * 
     * @param response
     */
    public void onSuccess(Object response)
    {

    }

    public void onSuccess(int statusCode, Object response)
    {
        onSuccess(response);
    }

    /**
     * 失败返回
     */
    public void onFailure(Throwable e, String errorResponse)
    {

    }

    /**
     * 必须实现此方法将string内容封装为自己定义的数据类型
     * 
     * @param cotent
     * @return
     */
    public abstract Object onParseResponse(String cotent);

    /**
     * 执行在后台线程
     */
    @Override
    protected void sendSuccessMessage(int statusCode, String responseBody)
    {
        Object jsonResponse = onParseResponse(responseBody);
        sendMessage(obtainMessage(SUCCESS_SERILIZABLE_MESSAGE, new Object[] {
                statusCode, jsonResponse }));
    }

    /**
     * 调用ui线程
     */
    @Override
    protected void handleMessage(Message msg)
    {
        switch (msg.what)
        {
        case SUCCESS_SERILIZABLE_MESSAGE:
            Object[] response = (Object[]) msg.obj;
            handleSuccessSeriableMessage(((Integer) response[0]).intValue(),
                    response[1]);
            break;
        default:
            super.handleMessage(msg);
        }
    }

    protected void handleSuccessSeriableMessage(int statusCode,
            Object jsonResponse)
    {
        onSuccess(statusCode, jsonResponse);
    }

    @Override
    protected void handleFailureMessage(Throwable e, String responseBody)
    {
        onFailure(e, responseBody);
    }

    /**
     * 将string转换为json,转换失败返回null
     * 
     * @param responseBody
     * @throws JSONException
     */
    protected Object parseResponseToJson(String responseBody)
            throws JSONException
    {
        Object result = null;
        responseBody = responseBody.trim();
        if (responseBody.startsWith("{") || responseBody.startsWith("["))
        {
            result = new JSONTokener(responseBody).nextValue();
        }
        if (result == null)
        {
            result = responseBody;
        }
        return result;
    }

}
