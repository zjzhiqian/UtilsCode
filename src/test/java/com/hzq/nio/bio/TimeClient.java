package com.hzq.nio.bio;

import java.io.*;
import java.net.Socket;

/**
 * TimeClient
 * Created by hzq on 16/7/16.
 */
public class TimeClient {

    public static void main(String[] args) {
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
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
