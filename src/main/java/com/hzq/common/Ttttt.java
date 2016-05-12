package com.hzq.common;

import com.hzq.common.validator.Annotation.CheckNull;

import java.util.Map;

/**
 * Created by hzq on 16/5/13.
 */
public class Ttttt {
    @CheckNull("tttt不能为空")
    private String name ;

    @CheckNull("map不能为空")
    Map map;

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
