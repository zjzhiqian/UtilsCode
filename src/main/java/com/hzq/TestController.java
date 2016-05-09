package com.hzq;

import com.hzq.common.User;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzq on 16/4/21.
 */

@Controller
public class TestController {


//    @InitBinder({"kkk"})@
    @InitBinder
    public void tt2(WebDataBinder binder) {
        binder.registerCustomEditor(HashMap.class,new PropertiesEditor());
        GenericConversionService conversionService= (GenericConversionService)binder.getConversionService();
        conversionService.addConverter(new CustomConvertor());



        System.out.println("Controller initBinder2");
    }


//    @ModelAttribute("key")
//    public String tt3() {
//        System.out.println("Controller modelAttibuteMethod");
//        return "123333";
//    }


    @ResponseBody
    @RequestMapping(value = "/test")
    public Date getById(@RequestParam User user) {

        return new Date();
    }
}
