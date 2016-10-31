package com.hzq.project.test.web;

import com.hzq.project.test.dao.TestMapper;
import com.hzq.project.test.entity.User;
import com.hzq.project.test.service.TestService;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.DefaultDataBinderFactory;
import org.springframework.web.bind.support.WebDataBinderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.beans.PropertyEditor;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzq on 16/4/21.
 */

@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private TestMapper testMapper;


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



    @ResponseBody
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public Map testBindingResult(@Valid @RequestBody User user, BindingResult bindingResult) {

        SpringValidatorAdapter springValidatorAdapter = new SpringValidatorAdapter(null);
        springValidatorAdapter.validate(user, new BindingResult() {
            @Override
            public String getObjectName() {
                return null;
            }

            @Override
            public void setNestedPath(String nestedPath) {

            }

            @Override
            public String getNestedPath() {
                return null;
            }

            @Override
            public void pushNestedPath(String subPath) {

            }

            @Override
            public void popNestedPath() throws IllegalStateException {

            }

            @Override
            public void reject(String errorCode) {

            }

            @Override
            public void reject(String errorCode, String defaultMessage) {

            }

            @Override
            public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {

            }

            @Override
            public void rejectValue(String field, String errorCode) {

            }

            @Override
            public void rejectValue(String field, String errorCode, String defaultMessage) {

            }

            @Override
            public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {

            }

            @Override
            public void addAllErrors(Errors errors) {

            }

            @Override
            public boolean hasErrors() {
                return false;
            }

            @Override
            public int getErrorCount() {
                return 0;
            }

            @Override
            public List<ObjectError> getAllErrors() {
                return null;
            }

            @Override
            public boolean hasGlobalErrors() {
                return false;
            }

            @Override
            public int getGlobalErrorCount() {
                return 0;
            }

            @Override
            public List<ObjectError> getGlobalErrors() {
                return null;
            }

            @Override
            public ObjectError getGlobalError() {
                return null;
            }

            @Override
            public boolean hasFieldErrors() {
                return false;
            }

            @Override
            public int getFieldErrorCount() {
                return 0;
            }

            @Override
            public List<FieldError> getFieldErrors() {
                return null;
            }

            @Override
            public FieldError getFieldError() {
                return null;
            }

            @Override
            public boolean hasFieldErrors(String field) {
                return false;
            }

            @Override
            public int getFieldErrorCount(String field) {
                return 0;
            }

            @Override
            public List<FieldError> getFieldErrors(String field) {
                return null;
            }

            @Override
            public FieldError getFieldError(String field) {
                return null;
            }

            @Override
            public Object getFieldValue(String field) {
                return null;
            }

            @Override
            public Class<?> getFieldType(String field) {
                return null;
            }

            @Override
            public Object getTarget() {
                return null;
            }

            @Override
            public Map<String, Object> getModel() {
                return null;
            }

            @Override
            public Object getRawFieldValue(String field) {
                return null;
            }

            @Override
            public PropertyEditor findEditor(String field, Class<?> valueType) {
                return null;
            }

            @Override
            public PropertyEditorRegistry getPropertyEditorRegistry() {
                return null;
            }

            @Override
            public void addError(ObjectError error) {

            }

            @Override
            public String[] resolveMessageCodes(String errorCode) {
                return new String[0];
            }

            @Override
            public String[] resolveMessageCodes(String errorCode, String field) {
                return new String[0];
            }

            @Override
            public void recordSuppressedField(String field) {

            }

            @Override
            public String[] getSuppressedFields() {
                return new String[0];
            }
        });


        System.out.println(user);
        System.out.println(bindingResult.getErrorCount());
        if (bindingResult.getErrorCount() > 0) {
            String defaultMessage = bindingResult.getFieldErrors().get(0).getDefaultMessage();
            System.out.println(defaultMessage);
        }
        Map<String,String> map  = new HashMap<>();
        map.put("key","key");
        return map;
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
    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "理由")
//    @ResponseStatus(code = HttpStatus.CONFLICT)
    @RequestMapping(path = "/test", headers = {"Accept-Encoding"})
    public Date getById(HttpServletRequest request) {
        request.getSession().setAttribute("1", "2");
        return new Date();
    }
}
