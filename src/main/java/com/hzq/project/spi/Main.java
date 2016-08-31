package com.hzq.project.spi;

import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;

/**
 * Main
 * Created by hzq on 16/8/31.
 */
public class Main {
    // SPI机制
    //1.resource目录下  META-INF.services新建文件  文件名称为接口的全限定名
    //2.文件内容:实现类的全限定名
    public static void main(String[] args) throws InterruptedException {
        for (; ; ) {
            ServiceLoader<HelloWord> load = ServiceLoader.load(HelloWord.class);
            load.forEach(HelloWord::sayHello);
            TimeUnit.SECONDS.sleep(3);
        }

    }
}
