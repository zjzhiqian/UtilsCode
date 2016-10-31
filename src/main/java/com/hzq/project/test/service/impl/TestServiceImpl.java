package com.hzq.project.test.service.impl;

import com.hzq.project.test.service.TestService;
import org.springframework.stereotype.Service;

/**
 * Created by hzq on 16/5/17.
 */
@Service
public class TestServiceImpl implements TestService {


    @Override
    public void aop01() {
        System.out.println("exexute");
    }
}
