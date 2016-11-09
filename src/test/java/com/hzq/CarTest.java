package com.hzq;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * CarTest
 * Created by hzq on 16/11/6.
 */
public class CarTest {

    private static final CloseableHttpClient client = HttpClients.custom().build();
    private static final String TOKEN_URL_TEST = "http://42.96.134.15:20009/oauth/AccessToken/token.json";
    private static final String TOKEN_URL_PROD = "https://open.souche.com/oauth/AccessToken/token.json";
    private static final String DATA_URL_TEST = "http://42.96.134.15:20009/api/car/queryCar.json";
    private static final String DATA_URL_PROD = "https://open.souche.com/api/car/queryCar.json";
    private static final boolean isProd = true;
    private static Logger logger = LoggerFactory.getLogger(CarTest.class);


    /**
     * 获取token接口
     */
    private String getToken() throws IOException {
        String url = TOKEN_URL_TEST;
        List<NameValuePair> params = new ArrayList<>();
        if (isProd) {
            url = TOKEN_URL_PROD;
            params.add(new BasicNameValuePair("username", "cx0576"));
            params.add(new BasicNameValuePair("password", "D9C67CBD875F94BFC0F4C16297AEC2AE"));
            params.add(new BasicNameValuePair("client_id", "cx0576"));
            params.add(new BasicNameValuePair("client_secret", "8114a135-8fc2-4b72-8b34-fd2fc210c722"));
            params.add(new BasicNameValuePair("grant_type", "password"));
        } else {
            params.add(new BasicNameValuePair("username", "cx0576"));
            params.add(new BasicNameValuePair("password", "cx0576"));
            params.add(new BasicNameValuePair("client_id", "cx0576"));
            params.add(new BasicNameValuePair("client_secret", "227b39f8-9cd4-4d4b-b74c-7ef41af2f238"));
            params.add(new BasicNameValuePair("grant_type", "password"));
        }
        String s = sendAndGetResponse(url,params);
        Map map = JSON.parseObject(s, Map.class);
        return (String) ((Map) map.get("data")).get("access_token");
    }

    /**
     * 发送post请求,并返回json结果
     */
    private String sendAndGetResponse(String url, List<NameValuePair> params) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        StringBuilder messageBuilder = new StringBuilder("\n");
        messageBuilder.append(post.getMethod());
        messageBuilder.append("  ");
        messageBuilder.append(post.getURI());
        messageBuilder.append("  ");
        HttpEntity entity = post.getEntity();
        String body = IOUtils.toString(entity.getContent());
        List<NameValuePair> parse = URLEncodedUtils.parse(body, ContentType.get(entity).getCharset());
        parse.stream().forEach(pair -> {
            messageBuilder.append(pair.getName());
            messageBuilder.append(":");
            messageBuilder.append(pair.getValue());
            messageBuilder.append(" ");
        });
        logger.warn("send httpRequest: {}", messageBuilder.toString());
        CloseableHttpResponse response = client.execute(post);
        InputStream content = response.getEntity().getContent();
        String s = IOUtils.toString(content);
        response.close();
        logger.warn("get httpResponse: \n{}", s);
        return s;
    }

    /**
     * 转换日期
     */
    private String formatIso8601Date(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    @Test
    public void test01() throws IOException {
        String token = "6ca12e61628503ca01146cf6f64e6ad6";
        logger.warn("getToken: {}", token);
        String url = DATA_URL_TEST;
        if (isProd) url = DATA_URL_PROD;
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", token));
        params.add(new BasicNameValuePair("timestamp", formatIso8601Date(new Date())));
        params.add(new BasicNameValuePair("signNonce", new Random().nextInt(10000) + ""));
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR, -2);
        instance.getTime();
        params.add(new BasicNameValuePair("startQueryTime", formatIso8601Date(instance.getTime())));
        Calendar instance2 = Calendar.getInstance();
        instance2.add(Calendar.YEAR, -2);
        params.add(new BasicNameValuePair("endQueryTime", formatIso8601Date(instance2.getTime())));
        params.add(new BasicNameValuePair("pageIndex", "1"));
        params.add(new BasicNameValuePair("pageSize", "20"));
        params.add(new BasicNameValuePair("carDealerType", "all"));
//        params.add(new BasicNameValuePair("brandCode", "brand-53"));
        String s = sendAndGetResponse(url, params);
        logger.warn("getData: {}", s);
    }
}
