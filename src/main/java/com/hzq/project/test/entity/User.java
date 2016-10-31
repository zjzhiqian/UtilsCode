package com.hzq.project.test.entity;

import javax.validation.constraints.NotNull;

/**
 * Created by hzq on 16/7/29.
 */
public class User {

    @NotNull(message = "用户名不能为空")
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
