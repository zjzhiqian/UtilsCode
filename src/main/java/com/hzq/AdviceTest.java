package com.hzq;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by hzq on 16/4/25.
 */
@ControllerAdvice
public class AdviceTest {


    @ModelAttribute
    public void modelAttrubuteMethod(String id) {
        System.out.println("AdviceTest modelAttrybute");
    }

//    @ModelAttribute
//    public void tt4(){
//        System.out.println("AdviceTest  modelAttrybute2");
//    }

    @InitBinder
    public void InitBinderMethod() {
        System.out.println("AdviceTest InitBinder");
    }



    @InitBinder
    public void InitBinderMethod2() {
        System.out.println("AdviceTest InitBinder2");
    }
}
