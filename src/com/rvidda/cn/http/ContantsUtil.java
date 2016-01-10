package com.rvidda.cn.http;


/**
 * 常量
 * 
 * @author longbh
 */
public class ContantsUtil {
	
	public final static String currentVesion = "v1.0.1";
	public static String TOKEN = "";
	
    public final static String SYCN_URL = "";
    public final static String SYCN_DATA = "com.dian.diabetes.sycn_data";

    public final static String host = "";

    // 请求状态码
    public static final int GET_KEY_FAILED = 100024;
    public static final int limit = 20;

    // 用户id,作为sharedPreference的key
    public static String USER_ID = "user_id";
    // 判断首次使用，作为sharedPreference的key
    public final static String FIRST_USE = "first_use";
    // 登录成功后存放mid在sharedPreference,key
    public final static String USER_MID = "user_mid";
    // 登录成功后存放sessionId在sharedPreference,key
    public final static String USER_SESSIONID = "user_sessionid";

    // 服务端url
    //public static String HOST = "http://romanticle.xicp.net:12325";
    //public static String HOST = "http://58.215.177.180:9048";
    public static String HOST = "http://121.42.26.180/api";
    public static String HOST1 = "http://121.42.26.180";

    //user
    public final static String URL_MEMBER_LIST = HOST + "/api/user/member/list";
    public final static String UPDATE_CHECK_URL = HOST + "/users";
    public final static String LOGIN = HOST + "/users/:id/confirm_code";
    
    //标签
    public final static String LABELS = HOST + "/labels";
    //更改标签
    public final static String LABELS1 = HOST + "/labels";
    //七牛
    public final static String QiNiu = HOST1 + "/uptoken";
    //提交咨询
    public final static String TiJiaoZiX = HOST + "/subjects";
    //咨询列表
    public final static String ZXLiaoBiao = HOST + "/subjects";
    //个人设置
    public final static String PersonMe =HOST+"/me";
    //提交申请
    public final static String TJlawyer =HOST+"/lawyers";
    //城市
    public final static String Area =HOST+"/areas";
    //未答列表
    public final static String WeiDaliebiao =HOST+"/workroom/pending_subjects";
    //已答列表
    public final static String YiDaliebiao =HOST+"/workroom/doing_subjects";
    
   
    
} 
