package com.hzq.rxjava;

import com.google.common.collect.Lists;
import rx.Observable;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hzq on 18/6/12.
 */
public class TransFromBuffer {

    public static void test1() {
        System.err.println("test1=============================");
        Observable<List<Integer>> buffer = Observable.range(0, 10)
                .buffer(2); //buffer(count) 返回每个list的最大数量是count = buffer(count,count)
        buffer.subscribe(System.err::println);
    }


    public static void test2() {
        System.err.println("test2=============================");
        Observable<List<Integer>> buffer = Observable.range(0, 10)
                .buffer(3, 1); //buffer(count,skip)
        buffer.subscribe(System.err::println);
    }

    public static void test3() throws InterruptedException {
        System.err.println("test3=============================");
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(10)
                .buffer(Observable.interval(250, TimeUnit.MILLISECONDS))
                .subscribe(System.err::println);
        TimeUnit.SECONDS.sleep(5);
        System.err.println("");
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(10)
                .buffer(() -> Observable.interval(250, TimeUnit.MILLISECONDS))
                .subscribe(System.err::println);
    }

    public static void test4() throws InterruptedException {
        System.err.println("test4=============================");
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(10)
                .buffer(Observable.interval(250, TimeUnit.MILLISECONDS), aLong -> Observable.timer(200, TimeUnit.MILLISECONDS))
                .subscribe(System.err::println);
    }


    public static void main(String[] args) throws InterruptedException {
//        test1();
//        test2();
//        test3();
//        test4();
//        TimeUnit.SECONDS.sleep(10);
    }
}
