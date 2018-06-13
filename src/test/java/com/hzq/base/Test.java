package com.hzq.base;

import com.alibaba.fastjson.JSON;

/**
 * Created by hzq on 17/10/24.
 */
public class Test {

    public static void main(String[] args) {

        String value = "{\"id\":\"3\",\"detail.id\":\"4\"}";
        Order result = JSON.parseObject(value, Order.class);
        System.err.println(result);
    }
}
