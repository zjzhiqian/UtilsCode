package com.hzq.project.test.web;

import com.hzq.project.test.entity.User;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
//        System.out.println("Controller modelAttributeMethod");
//        return "123333";
//    }

    @ModelAttribute("1")
    public String test001(@ModelAttribute User user) {
        System.out.println(1);
        return "3";
    }


    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Date getByIdPost(HttpServletRequest request) {
        request.getSession().setAttribute("1", "2");
        return new Date();
    }


    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.DELETE)
    public Date getByIdDelete(HttpServletRequest request) {
        request.getSession().setAttribute("1", "2");
        return new Date();
    }


    @ResponseBody
//    @ResponseStatus(code = HttpStatus.CONFLICT,reason = "理由")
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @RequestMapping(path = "/test", headers = {"Accept-Encoding"})
    public Date getById(HttpServletRequest request) {
        request.getSession().setAttribute("1", "2");
        return new Date();
    }
}
