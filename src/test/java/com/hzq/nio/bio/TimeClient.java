package com.hzq.nio.bio;

import org.junit.Test;

import java.io.*;
import java.net.Socket;

/**
 * TimeClient
 * Created by hzq on 16/7/16.
 */
public class TimeClient {

    @Test
    public void test01() {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("query time order");
            System.out.println("send order 2 server success.");
            String resp = in.readLine();
            System.out.println("now is : " + resp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (out != null)
                out.close();
        }
    }

}
