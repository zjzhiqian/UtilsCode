package com.hzq.project.common.httpClient;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hzq on 15/6/21.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context-spring.xml"})
public class HttpUtilsTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private HttpUtils httpUtils;


    @Test
    public void check() {
        String result = httpUtils.sendHttp("http://www.baidu.com");
        Assert.assertTrue(StringUtils.isNoneEmpty(result));
    }


}
