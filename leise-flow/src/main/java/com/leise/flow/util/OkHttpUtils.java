package com.leise.flow.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by JY-IT-D001 on 2018/8/16.
 */
public class OkHttpUtils {

    private static final Logger LOG = LoggerFactory.getLogger(OkHttpUtils.class);

    /**
     * 根据map获取get请求参数
     *
     * @param queries
     * @return
     */
    private static StringBuffer getQueryString(String url, Map<String, String> queries) {
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator<Entry<String, String>> iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        return sb;
    }

    /**
     * 调用okhttp的newCall方法
     *
     * @param request
     * @return
     */
    private static String executeNewCall(Request request) {
        OkHttpClient okHttpClient = new OkHttpClient();
        try (Response response = okHttpClient.newCall(request).execute();) {
            if (response.isSuccessful()) {
                return response.body().string();
            }
        }
        catch (Exception e) {
            LOG.error("okhttp3 put error >> ex = {}", e.getMessage());
        }
        return null;
    }

    /**
     * 调用okhttp的newCall方法
     *
     * @param request
     * @return
     */
    private static String executeNewCall(Request request, Long connectTimeout, Long readTimeout) {
        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        if (null != connectTimeout) {
            builder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        }
        if (null != readTimeout) {
            builder.readTimeout(readTimeout, TimeUnit.SECONDS);
        }
        okHttpClient = builder.build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            }
        }
        catch (Exception e) {
            LOG.error("okhttp3 put error >> ex = {}", e.getMessage());
        }
        return null;
    }

    /**
     * get
     *
     * @param url 请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public static String get(String url, Map<String, String> queries, Long connectTimeout, Long readTimeout) {
        StringBuffer sb = getQueryString(url, queries);
        Request request = new Request.Builder().url(sb.toString()).build();
        return executeNewCall(request, connectTimeout, readTimeout);
    }

    /**
     * post
     *
     * @param url 请求的url
     * @param params post form 提交的参数
     * @return
     */
    public static String postFormParams(String url, Map<String, String> params, Long connectTimeout, Long readTimeout) {
        FormBody.Builder builder = new FormBody.Builder();
        // 添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        return executeNewCall(request);
    }

    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"} 参数一：请求Url
     * 参数二：请求的JSON 参数三：请求回调
     */
    public static String postJsonParams(String url, String jsonParams, Long connectTimeout, Long readTimeout) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        return executeNewCall(request, connectTimeout, readTimeout);
    }

    /**
     * Post请求发送xml数据.... 参数一：请求Url 参数二：请求的xmlString 参数三：请求回调
     */
    public static String postXmlParams(String url, String xml, Long connectTimeout, Long readTimeout) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        return executeNewCall(request, connectTimeout, readTimeout);
    }

}
