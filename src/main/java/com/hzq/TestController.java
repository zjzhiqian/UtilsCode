package com.hzq;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by hzq on 16/4/21.
 */

@Controller
public class TestController{

    private  String aa="aa";

    @InitBinder
    public void tt(WebDataBinder binder){
        System.out.println("initBinder1");
        aa="vvvv";
    }

    @InitBinder
    public void tt2(WebDataBinder binder){
        System.out.println("initBinder2");
        aa="vvvv";
    }


    @ModelAttribute
    public void tt3(){
        System.out.println("modelAttibuteMethod");
    }

    @ModelAttribute
    public void tt4(){
        System.out.println("modelAttibuteMetho2d");
    }



    @ResponseBody
    @RequestMapping(value = "/test")
    public Date getById() {
        System.out.println(aa);
        return new Date();
    }
}
