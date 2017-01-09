package com.hzq.nio;


import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by hzq on 16/7/20.
 */
public class DemoNio {


    @Test
    public void server() throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(8080), 1024);
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select(1000);
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int read = socketChannel.read(buffer);
                        if (read > 0) {
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            System.out.println("request:" + new String(bytes, "UTF-8"));

                            doWrite(socketChannel, "response");
                        } else if (read < 0) {
                            key.cancel();
                            socketChannel.close();
                        }
                    }
                }
            }
        }
    }

    @Test
    public void client() throws IOException {
        boolean stop = false;

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        boolean connect = socketChannel.connect(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        if (connect) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel, "request");
        } else {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
        while (!stop) {
            selector.select(1000);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isValid()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    if (key.isConnectable()) { //连接状态,说明服务器已返回ACK应答消息
                        if (sc.finishConnect()) { //判断客户端是否连接成功
                            sc.register(selector, SelectionKey.OP_READ); //监听读操作,然后发送请求给服务器
                            doWrite(sc, "握手成功");
                        } else {
                            System.exit(1);
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
                            System.out.println("getResponse:" + body);
                            stop = true;
                        }
                    }
                }
            }
        }
    }


    private void doWrite(SocketChannel sc, String content) throws IOException {
        byte[] req = content.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            System.out.println("send success :" + content);
        }
    }

}
