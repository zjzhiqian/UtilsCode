package com.hzq.nio.bio;

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
public class TimeServer {
    public static void main(String[] args) throws IOException {
        Integer port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("socket start ...port:" + port);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        }

    }


    private static class TimeServerHandler implements Runnable {

        private Socket socket;

        TimeServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            BufferedReader in = null;
            PrintWriter out = null;
            try {

                in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                out = new PrintWriter(this.socket.getOutputStream(),true);
                String currentTime = null;
                String body = null;
                while (true) {
                    body = in.readLine();
                    if (body == null) break;
                    System.out.println("the time server received order : " + body);
                    currentTime = "query time order".equalsIgnoreCase(body) ? new Date().toString() : "bad order";
                    out.println(currentTime);
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
                if (this.socket != null) {
                    try {
                        this.socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        this.socket = null;
                    }
                }
            }

        }
    }
}


