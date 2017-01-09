package com.hzq.nio;


import java.nio.ByteBuffer;

/**
 * Created by hzq on 16/7/19.
 */
public class TestBuffer {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("123".getBytes());
        buffer.flip();
        char c = (char) buffer.get();
        System.out.println(c);
        System.out.println(buffer.position() + "  " + buffer.limit());
        buffer.clear();
//        buffer.compact();
        char d = (char) buffer.get();
        System.out.println(d);
        System.out.println(buffer.position() + "  " + buffer.limit());
    }
}
