package com.hzq;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * Created by hzq on 16/4/21.
 */

@Controller
public class TestController{

    private  String aa="aa";



    @InitBinder
    public void tt2(WebDataBinder binder){
        System.out.println("Controller initBinder2");
        aa="vvvv";
    }


    @ModelAttribute("key")  //TODO 这个 value的用法
    public String tt3(String id,Integer oo){
        System.out.println("Controller modelAttibuteMethod");
        return "123333";
    }


    @ResponseBody
    @RequestMapping(value = "/test/{page}")
    public Date getById(Map map) {
        System.out.println(aa);
        return new Date();
    }
}
