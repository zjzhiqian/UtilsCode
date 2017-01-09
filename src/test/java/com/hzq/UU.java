package com.hzq;

/**
 * Created by hzq on 17/1/6.
 */
public class UU implements Comparable<UU> {

    private Integer age;

    @Override
    public int compareTo(UU o) {
        return this.age > o.getAge() ? 1 : -1;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UU{" +
                "age=" + age +
                '}';
    }
}
