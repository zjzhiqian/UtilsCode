package com.hzq.common.validator.Annotation;

import java.lang.annotation.*;

/**
 * 非空校验注解
 * Created by hzq on 16/5/13.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CheckNull {
    String value() default "参数不能为空";
}
