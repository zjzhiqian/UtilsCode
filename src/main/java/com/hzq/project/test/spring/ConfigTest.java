package com.hzq.project.test.spring;

import com.hzq.project.test.dao.TestMapper;
import com.hzq.project.test.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;

import java.util.Date;

/**
 * Created by hzq on 16/10/31.
 */
@Configuration
@Order(1)
@Import({User.class})
@PropertySource("classpath:mysql.properties")
@EnableCaching
public class ConfigTest implements TtDefault{

    @Value("${myname}")
    private String myname;

    public String getMyname() {
        return myname;
    }

    public void setMyname(String myname) {
        this.myname = myname;
    }

    @Bean
    Date date() {
        return new Date();
    }

    public static void main(String[] args) {
        ClassLoader loader = User.class.getClassLoader();
        ClassLoader loader1 = TestMapper.class.getClassLoader();
        System.out.println(loader.equals(loader1));
    }




}
