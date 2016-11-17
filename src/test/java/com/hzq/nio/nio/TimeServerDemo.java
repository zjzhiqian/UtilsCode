package com.hzq.nio.nio;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by hzq on 16/7/20.
 */
public class TimeServerDemo {


    public static void main(String[] args) {

        Selector selector = null;
        try {
            selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(8080), 1024);
            channel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("channel finish");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("init failed");
        }

        while (true) {
            try {

                selector.select(1000);
                Set<SelectionKey> keys = selector.selectedKeys();

                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        if (key.isValid()) {
                            if (key.isAcceptable()) {
                                ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                                SocketChannel socketChannel = serverChannel.accept();
                                socketChannel.configureBlocking(false);
                                socketChannel.register(selector, SelectionKey.OP_READ);
                            }
                            if (key.isReadable()) {
                                SocketChannel sc = (SocketChannel) key.channel();
                                ByteBuffer allocate = ByteBuffer.allocate(1024);
                                int read = sc.read(allocate);
                                if (read > 0) {
                                    allocate.flip();
                                    byte[] bytes = new byte[allocate.remaining()];
                                    allocate.get(bytes);
                                    String req = new String(bytes, "UTF-8");
                                    System.out.println("......req:" + req);

                                    byte[] resp = "....response".getBytes();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put(resp);
                                    writeBuffer.flip();
                                    sc.write(writeBuffer);

                                } else if (read < 0) {
                                    key.cancel();
                                    sc.close();
                                } else {
                                    System.out.println("读到0字节");
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) key.channel().close();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }


        }
//        if (selector != null) {
//            try {
//                selector.close();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }

    }


}
