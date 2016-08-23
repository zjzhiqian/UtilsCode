package com.hzq.entity;

import org.msgpack.annotation.MessagePackBeans;

/**
 * Created by hzq on 16/7/5.
 */
@MessagePackBeans
public class User {

    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User(Integer age) {
        this.age = age;
    }


    public User(){}
    public boolean isHigh() {
        return true;
    }

    public static String set01(String key1) {
        return null;
    }

    public static String set02(String key2) {
        return null;
    }

    public static String set03(String key3) {
        return null;
    }

    public static String set04(String key4) {
        return null;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                '}';
    }
}
