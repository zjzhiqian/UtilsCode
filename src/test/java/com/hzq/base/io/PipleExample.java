package com.hzq.base.io;

import org.junit.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * PipleExample
 * Created by hzq on 16/7/12.
 */
public class PipleExample {

    @Test
    public void test01() throws Exception {
        final PipedOutputStream pos = new PipedOutputStream();
        final PipedInputStream pis = new PipedInputStream(pos);
        new Thread(() -> {
            try {
                pos.write("asdsfsdfdsf".getBytes());
            } catch (IOException ignored) {
            }
        }).start();


        new Thread(() -> {
            try {
                int read = pis.read();
                while (read != -1) {
                    System.out.println((char) read);
                    read = pis.read();
                }
            } catch (IOException ignored) {
            }
        }).start();

    }
}
