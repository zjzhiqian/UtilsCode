package com.hzq;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Map;

/**
 * Created by hzq on 16/4/21.
 */

@Controller
public class TestController {



    @InitBinder
    public void tt2(String name) {
        System.out.println(name);
        System.out.println("Controller initBinder2");
    }


    @ModelAttribute("key")
    public String tt3(String id, String name) {
        System.out.println(name);
        System.out.println("Controller modelAttibuteMethod");
        return "123333";
    }


    @ResponseBody
    @RequestMapping(value = "/test/")
    public Date getById(Map map) {
        return new Date();
    }
}
