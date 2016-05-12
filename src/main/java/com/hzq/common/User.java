package com.hzq.common;

import com.hzq.common.validator.Annotation.CheckNull;

import java.util.List;
import java.util.Map;

/**
 * Created by hzq on 16/5/9.
 */
public class User {
    @CheckNull("用户名不能为空")
    private String name;

    @CheckNull("年龄不能为空")
    private Integer age;

    @CheckNull("map不能为空")
    private Map map;

    @CheckNull("list不能为空")
    private List<Ttttt> lists;


    public List<Ttttt> getLists() {
        return lists;
    }

    public void setLists(List<Ttttt> lists) {
        this.lists = lists;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Integer getAge() {

        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
