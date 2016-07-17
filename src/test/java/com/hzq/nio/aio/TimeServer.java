package com.hzq.nio.aio;



/**
 * aio TimeServer
 * Created by hzq on 16/7/17.
 */
public class TimeServer {

    public static void main(String[] args) {
        new Thread(new AsyncTimeServerHandler(8080)).start();
    }


}
