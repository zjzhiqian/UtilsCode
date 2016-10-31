package com.hzq.project.test.dao;


import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by hzq on 16/5/17.
 */
//@Repository
public interface TestMapper {

    Map getOne();

    Map getTwo();
}
