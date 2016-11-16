package com.hzq.bytebuddy.entitypack;

/**
 * Created by hzq on 16/11/16.
 */
public interface DefaultMethod {

    default Object caca() {
        System.out.println("caca......");
        return "123";
    }
}
