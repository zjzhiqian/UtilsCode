package com.hzq.project.common.validator;


import com.hzq.project.common.validator.commonValidator.CheckNullValidator;
import com.hzq.project.common.validator.commonValidator.RetValidator;
import com.hzq.project.common.validator.commonValidator.Validator;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * 优雅的校验方式,可用于参数校验,逻辑校验
 * Created by hzq on 16/5/13.
 */
public class ValidatorComposite<T> {

    private Map<String, T> map;

    public static <T> ValidatorComposite<T> instance() {
        return new ValidatorComposite<T>();
    }

    /**
     * 校验并可以操作局部变量map用于返回
     *
     * @param retValidator
     * @return
     */
    public ValidatorComposite<T> addRet(RetValidator<T> retValidator) {
        if (map == null) {
            map = new HashMap<>();
        }
        retValidator.valid(map);
        return this;
    }

    /**
     * 进行校验
     *
     * @param validator
     * @return
     */
    public ValidatorComposite<T> add(Validator validator) {
        validator.valid();
        return this;
    }

    /**
     * @return
     */
    public Map<String, T> datas() {
        return map;
    }

    /**
     * demoCode
     */
    private void tt() {

        Map a = new HashMap();

        Map data = ValidatorComposite.<Integer>instance()
                .add(() -> {
                    System.out.println(222222);
                    System.out.println(3);
                })
                .addRet((obj) -> {
                    obj.put("",1);
                    a.put("12","3");
                    System.out.println(a);
                })
                .add(new CheckNullValidator(new ArrayList<>(), "list详情数据不能为空"))
                .datas();
        System.out.println(data);
    }

    public static void main(String[] args) {
//        new ValidatorComposite<>().tt();
        BigDecimal d = new BigDecimal("0.02");
        final String format = MessageFormat.format("213123{0},{1},{2}", "3231231231232312312313123123123123123123",new BigDecimal(99),d);
        System.out.println(format);

    }


}

