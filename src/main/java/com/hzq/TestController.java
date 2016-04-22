package com.hzq;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
/**
 * Created by hzq on 16/4/21.
 */

@Controller
public class TestController {

    @ResponseBody
    @RequestMapping(value = "/test")
    public Date getById(){
        return new Date();
    }
}
