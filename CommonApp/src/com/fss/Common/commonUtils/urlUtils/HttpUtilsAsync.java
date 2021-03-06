package com.fss.Common.commonUtils.urlUtils;

import android.content.Context;
import com.fss.Common.commonUtils.basicUtils.BasicUtils;
import com.fss.Common.commonUtils.logUtils.Logs;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.net.CookieStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by cym on 14-4-29.
 */
public class HttpUtilsAsync {
    private static final String BASE_URL = "http://api.fss.com/1/";
    private static final int TIME_OUT = 15;
    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        //client.setTimeout();

        client.get(getAbsoluteUrl(url), responseHandler);


    }

    public static void getWithCookie(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        //  myCookieStore.clear();
        client.setCookieStore(myCookieStore);
        client.get(getAbsoluteUrl(url), responseHandler);
//        Iterator iterator = myCookieStore.getCookies().iterator();
//        while (iterator.hasNext()) {
//            Cookie cookie = (Cookie) iterator.next();
//            Logs.d("cookie name--" + cookie.getName());
//            Logs.d("cookie value--" + cookie.getValue());
//        }

//        BasicCookieStore basicCookieStore = new BasicCookieStore();
//
//        client.setCookieStore(basicCookieStore);
//        client.get(getAbsoluteUrl(url), responseHandler);
//        Iterator iterator = basicCookieStore.getCookies().iterator();
//        while (iterator.hasNext()) {
//            Cookie cookie = (Cookie) iterator.next();
//            Logs.d("sssss" + cookie.getName());
//            Logs.d("sssss" + cookie.getValue());
//        }


    }

    public static void getUseCookie(Context context, String url, HashMap hashMap, AsyncHttpResponseHandler responseHandler) {
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        if (BasicUtils.judgeIfNull(hashMap)) {
            Iterator iterator = hashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                Cookie cookie = new BasicClientCookie(key.toString(), value.toString());
                myCookieStore.addCookie(cookie);
            }
        }
        client.setCookieStore(myCookieStore);
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    public static void getWithCookie(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
        client.get(getAbsoluteUrl(url), params, responseHandler);

    }

    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
//        return BASE_URL + relativeUrl;
        return relativeUrl;
    }

    public static String getUrlFromHashMap(String originUrl, HashMap hashMap) {
        String returnUrl = originUrl;

        if (BasicUtils.judgeIfNull(hashMap)) {
            returnUrl = returnUrl + "?";
            Iterator iterator = hashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                returnUrl += key + "=" + value + "&";
            }
            if (returnUrl.endsWith("&")) {
                returnUrl = returnUrl.substring(0, returnUrl.length() - 1);
            }
        }
        Logs.d(returnUrl);
        return returnUrl;
    }
}
