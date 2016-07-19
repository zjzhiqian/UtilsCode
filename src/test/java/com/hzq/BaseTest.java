package com.hzq;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * BaseTest
 * Created by hzq on 16/6/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context-spring.xml", "classpath:context-mysql-datasource.xml"})
public class BaseTest {
}
