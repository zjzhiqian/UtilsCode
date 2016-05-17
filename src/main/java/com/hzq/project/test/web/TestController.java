package com.hzq.project.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by hzq on 16/4/21.
 */

@Controller
public class TestController {


//    @InitBinder({"kkk"})@
//    @InitBinder
//    public void tt2(WebDataBinder binder) {
//        binder.registerCustomEditor(User.class,new PropertiesEditor());
//        GenericConversionService conversionService= (GenericConversionService)binder.getConversionService();
//        conversionService.addConverter(new CustomConvertor());
//        binder.setDisallowedFields(new String[]{"1","2"});


//        System.out.println("Controller initBinder2");
//    }
//    @ModelAttribute("key")
//    public String tt3() {
//        System.out.println("Controller modelAttibuteMethod");
//        return "123333";
//    }


    @ResponseBody
    @RequestMapping(value = "/test")
    public Date getById(@RequestParam MultiValueMap map) {

        return new Date();
    }
}
