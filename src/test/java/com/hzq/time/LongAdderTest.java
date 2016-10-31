package com.hzq.time;

import sun.misc.Signal;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by hzq on 16/9/18.
 */
public class LongAdderTest {


    public static void main(String[] args) throws InterruptedException {
        LongAdder adder = new LongAdder();
//        adder.add(3);

        System.out.println(System.getProperties().getProperty("os.name"));


        Signal sig = new Signal(getOSSignalType());
        Signal.handle(sig, new SystemShutDown());

        TimeUnit.SECONDS.sleep(30);
    }


    private static String getOSSignalType() {
        return System.getProperties().getProperty("os.name").toLowerCase().startsWith("win") ? "INT" : "USR2";
    }

}
