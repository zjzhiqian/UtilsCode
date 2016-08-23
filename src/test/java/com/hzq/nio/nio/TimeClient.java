package com.hzq.nio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * nio TimeClient
 * Created by hzq on 16/7/17.
 */
public class TimeClient {

    public static void main(String[] args) {


        new Thread(new TimeClientHandle("127.0.0.1", 8080)).start();

    }

    private static class TimeClientHandle implements Runnable {
        private String host;
        private Integer port;


        private Selector selector;
        private SocketChannel socketChannel;
        private volatile boolean stop;

        TimeClientHandle(String host, Integer port) {
            this.host = host;
            this.port = port;
            try {
                selector = Selector.open();
                socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("client init failed");
                System.exit(-1);
            }
        }


        @Override
        public void run() {
            try {
                //如果直接连接成功,则注册到多路复用器上,发送请求,读应答
                if (socketChannel.connect(new InetSocketAddress(host, port))) {
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    doWrite(socketChannel);
                } else {
                    //说明服务器没有返回TCP应答消息,注册OP_CONNECT,当服务器返回TCP应答后,selector就能轮询到这个socketChannel处于就绪状态
                    socketChannel.register(selector, SelectionKey.OP_CONNECT);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("connect failed");
                System.exit(-1);
            }

            while (!stop) {
                try {
                    selector.select(1000);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    SelectionKey key = null;
                    while (iterator.hasNext()) {
                        key = iterator.next();
                        iterator.remove();
                        try {
                            handleInput(key);
                        } catch (IOException e) {
                            if (key != null) {
                                key.cancel();
                                if (key.channel() != null) key.channel().close();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("退出点2");
                    System.exit(-1);
                }
            }
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        private void handleInput(SelectionKey key) throws IOException {
            if (key.isValid()) {
                SocketChannel sc = (SocketChannel) key.channel();
                if (key.isConnectable()) { //连接状态,说明服务器已返回ACK应答消息
                    if (sc.finishConnect()) { //判断客户端是否连接成功
                        sc.register(selector, SelectionKey.OP_READ); //监听读操作,然后发送请求给服务器
                        doWrite(sc);
                    } else {
                        System.out.println("退出点3");
                        System.exit(-1);
                    }
                }

                if (key.isReadable()) {
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(readBuffer);
                    if (readBytes > 0) {
                        readBuffer.flip();
                        byte[] bytes = new byte[readBuffer.remaining()];
                        readBuffer.get(bytes);
                        String body = new String(bytes, "UTF-8");
                        System.out.println("Now is : " + body);
                        this.stop = true;
                    } else if (readBytes < 0) {
                        key.cancel();
                        sc.close();
                    } else {
                        System.out.println("read 0 bytes");
                    }
                }

            }
        }

        private void doWrite(SocketChannel sc) throws IOException {
            byte[] req = "query time order".getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
            writeBuffer.put(req);
            writeBuffer.flip();
            sc.write(writeBuffer);
            if (!writeBuffer.hasRemaining()) {
                System.out.println("send order 2 server succeed.");
            }
        }

    }


}
