package com.hzq.time;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by hzq on 16/9/18.
 */
public class SystemShutDown implements SignalHandler {
    @Override
    public void handle(Signal signal) {
        Thread t = new Thread(() -> {
            System.out.println("ShutdownHook execute start...");
            System.out.print("Netty NioEventLoopGroup shutdownGracefully...");
            try {
                TimeUnit.SECONDS.sleep(10);//模拟应用进程退出前的处理操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ShutdownHook execute end...");
            System.out.println("Sytem shutdown over, the cost time is 10000MS");
        }, "ShutdownHook-Thread");
        Runtime.getRuntime().addShutdownHook(t);


        Runtime.getRuntime().exit(0);

    }
}
