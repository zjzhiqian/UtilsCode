package com.hzq.nio.aio;


/**
 * aio TimeClient
 * Created by hzq on 16/7/17.
 */
public class TimeClient {

    public static void main(String[] args) {
        new Thread(new AsyncTimeClientHandler(8080, "127.0.0.1")).start();
    }
}
