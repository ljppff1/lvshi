package com.rvidda.cn.http;

/**
 * 所有的http请求地址
 */
public class HttpUrls
{

    // public static String WEB_ROOT_work =
    // "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines";
    public static String WEB_DETAIL = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent";

    public static String WEB_TOP = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines&rows=15";

    public static String WEB_TOP_scroll = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow";

    public static String WEB_data = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&rows=15";

    public static String WEB_SEARCH = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.searcListByTitle&rows=10";

    // &method=news.getSlideshow
    public static String BaseUrl ="http://dev.soloman.org.cn/API/";
    public static String BaseUrls ="http://ywjno.ngrok.wendal.cn:9080/API/";

    public static String BaseMediaUrl = "http://121.40.128.114:8080";

    public static String BaseImUrl = "121.40.128.114";

    public static String webview_url = "http://121.40.128.114:9090/admin.php/Publication/scan/id/";
    /**
     * 默认地址
     */
    // public HttpUrls() {
    //
    // }
    //
    // public HttpUrls(String webRoot) {
    // }

}