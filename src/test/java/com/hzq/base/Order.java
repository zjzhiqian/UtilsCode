package com.hzq.base;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;

/**
 * Created by hzq on 17/10/24.
 */
public @Data
class Order {
    private String id;

    private Detail detail;
}
