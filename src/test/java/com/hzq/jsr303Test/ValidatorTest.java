package com.hzq.jsr303Test;

import com.hzq.project.test.entity.User;

/**
 * ValidatorTest
 * Created by hzq on 16/9/6.
 */
public class ValidatorTest {

    public static void main(String[] args) {
        User u = new User();
        u.setName("12");
        BeanValidator.validBean(u);
    }
}
