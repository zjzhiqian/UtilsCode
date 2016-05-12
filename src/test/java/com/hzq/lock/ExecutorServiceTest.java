package com.hzq.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hzq on 16/5/13.
 */
public class ExecutorServiceTest {

    public static void main(String args[]){
        ExecutorService service = Executors.newFixedThreadPool(3);
        service.execute(() ->{
            for(int i = 0 ;i <100000000;i++)
            System.out.println(i);
        });
        service.shutdownNow();
        System.out.println("===========================");
    }


}

