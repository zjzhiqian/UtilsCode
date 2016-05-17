/**
 * @(#)Aaaa.java
 * @author huangzhiqian
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月3日 huangzhiqian 创建版本
 */
package com.hzq.project.common.httpClient;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * httpClientUtil
 * Created by hzq on 16/4/22.
 */
@Component
public class HttpUtils implements InitializingBean {

    private static CloseableHttpClient httpclient = null;
    private static RequestConfig config = null;


    @Override
    public void afterPropertiesSet() throws Exception {
        httpclient = HttpClients.custom().build();
        config = RequestConfig.custom().setConnectTimeout(40000).setConnectionRequestTimeout(40000).setSocketTimeout(40000).build();
    }

    public String sendHttp(String url) {
        String result = "";
        try {
            CloseableHttpResponse response = null;
            try {
                HttpGet httpget = new HttpGet(url);
                httpget.setConfig(config);
                setHeadersForGet(httpget);
                response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = IOUtils.toString(entity.getContent());
                }
                EntityUtils.consume(entity);
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private static void setHeadersForGet(HttpGet httpget) {
    }


}


