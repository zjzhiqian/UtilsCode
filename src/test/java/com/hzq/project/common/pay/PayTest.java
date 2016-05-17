package com.hzq.project.common.pay;


import com.bocnet.common.security.PKCS7Tool;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * HttpClient简单示例代码
 *
 * @author huangzhiqian
 */
public class PayTest {
    private static CloseableHttpClient httpclient = null;
    private static CookieStore store;
    private static final String url = "https://ebspay.boc.cn/PGWPortal/RecvOrder.do";
    //https://219.141.226.136/PGWPortal/RecvOrder.do


    private static SSLConnectionSocketFactory socketFactory;

    @Before
    public void init() throws Exception {
        store = new BasicCookieStore();
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());


        File f = new File("/Users/hzq/Downloads/pkcs7demo/BANKOFCHINA.cer");
        f = new File("/Users/hzq/Downloads/pkcs7demo/BOCCA.cer");
        keyStore.load(FileUtils.openInputStream(f), "123".toCharArray());
        SSLContext ctx = SSLContexts
                .custom()
                .loadTrustMaterial(keyStore, TrustSelfSignedStrategy.INSTANCE)
                .build();
        socketFactory = new SSLConnectionSocketFactory(ctx);
        httpclient = HttpClients
                .custom()
                .setDefaultCookieStore(store)
                .setSSLSocketFactory(socketFactory)
                .build();

    }

    @Test
    public void sendHttp() {
        try {
            CloseableHttpResponse response = null;
            try {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setConfig(RequestConfig.custom().setConnectTimeout(40000).setConnectionRequestTimeout(40000).setSocketTimeout(40000).build());
                List<NameValuePair> params = getNameValuePairs();
                HttpEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
                httpPost.setEntity(entity);

                PKCS7Tool tool = PKCS7Tool.getSigner("/Users/hzq/Downloads/pkcs7demo/sign.txt", "", "");
                String sig = tool.sign(new byte[3]);
                System.out.println(sig);


                response = httpclient.execute(httpPost);
                HttpEntity responseEntity = response.getEntity();
                String rs = IOUtils.toString(responseEntity.getContent());
                System.out.println(rs);
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
    }

    private List<NameValuePair> getNameValuePairs() {
        //1|1970-01-01 08:00:00.0|001|1.00|1
        String orderNo = "20999";
        String orderTime = new SimpleDateFormat("yyyymmddHHMMSS").format(new Date());
        String curCode = "001";
        String orderAmount = "0.10";
        //TODO 商户id
        String merChantNo = "1";

        //TODO 商户接收通知URL
        String orderUrl = "";

        String signData = orderNo + "|" + orderTime + "|" + curCode + "|" + orderAmount + "|" + merChantNo;

        List<NameValuePair> params = new ArrayList<>();

        NameValuePair pair = new BasicNameValuePair("merchantNo", merChantNo);
        params.add(pair);

        //支付类型
        pair = new BasicNameValuePair("payType", "1");
        params.add(pair);

        //商户订单号
        pair = new BasicNameValuePair("orderNo", orderNo);
        params.add(pair);

        //订单币种
        pair = new BasicNameValuePair("curCode", curCode);
        params.add(pair);

        //订单金额
        pair = new BasicNameValuePair("orderAmount", orderAmount);
        params.add(pair);

        //订单时间
        pair = new BasicNameValuePair("orderTime", orderTime);
        params.add(pair);

        //订单说明
        pair = new BasicNameValuePair("orderNote", "说明测试");
        params.add(pair);

        //通知商家url
        pair = new BasicNameValuePair("orderUrl", orderUrl);
        params.add(pair);

        //商户签名数据
        pair = new BasicNameValuePair("signData", signData);
        params.add(pair);
        return params;
    }


}


