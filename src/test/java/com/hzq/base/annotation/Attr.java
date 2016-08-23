package com.hzq.base.annotation;

import java.lang.annotation.*;

/**
 * Created by hzq on 16/5/12.
 */

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Attr {

    String value() default "213";
}

