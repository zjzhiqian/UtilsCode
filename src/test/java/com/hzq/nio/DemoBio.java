package com.hzq.nio;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * TimeServer
 * Created by hzq on 16/7/16.
 */
public class DemoBio {


    @Test
    public void test01() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                final Socket socket = serverSocket.accept();
                new Thread(() -> {
                    BufferedReader in = null;
                    PrintWriter out = null;
                    try {
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out = new PrintWriter(socket.getOutputStream(), true);
                        String body = null;
                        while (true) {
                            body = in.readLine();
                            if (body == null) break;
                            System.out.println("getRequest:" + body);
                            out.println("responseData");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                in = null;
                            }

                        }
                        if (out != null) {
                            out.close();
                            out = null;
                        }
                        if (socket != null) {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        }
    }


    @Test
    public void client() {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("send To server ");
            String resp = in.readLine();
            System.out.println("getResponse:" + resp);
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


