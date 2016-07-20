package com.hzq.nio.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO TimeServer
 * <p>
 * NIO编程优点:
 * 1.客户端发起的连接操作都是异步的,可以通过在多路复用器上注册OP_CONNECT等待后续结果,不需要像之前那样的被阻塞
 * 2.SocketChannel读写操作都是异步的,没有读写数据也不会同步等待,直接返回,这样I/O通信线程可以处理其他链路,无需等待
 * 3.线程模型的优化,在Linux上通过epoll实现,没有连接句柄数限制(fs)意味着可以有成千上万个客户端连接,而且性能不会随着连接数的增加而下降
 * Created by hzq on 16/7/17.
 */
public class TimeServer {

    public static void main(String[] args) {

        System.out.println(System.getProperty("line.separator"));
        new Thread(new MultiplexerTimeServer(8080), "NIO thread").start();
    }

    private static class MultiplexerTimeServer implements Runnable {
        private Selector selector;
        private ServerSocketChannel servChannel;
        private volatile boolean stop;

        MultiplexerTimeServer(Integer port) {
            try {
                //资源初始化,创建多路复用器selector 和serverChannel
                selector = Selector.open();

                servChannel = ServerSocketChannel.open();
                servChannel.configureBlocking(false);
                servChannel.socket().bind(new InetSocketAddress(port), 1024);
                servChannel.register(selector, SelectionKey.OP_ACCEPT); //将Channel注册到selector,监听 SelectionKey.OP_ACCEPT操作位
                System.out.println("the time server started in port : " + port);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("init failed");
                System.exit(-1);
            }
        }

        public void stop() {
            this.stop = true;
        }

        @Override
        public void run() {

            while (!stop) {
                try {
                    selector.select(1000); //selector每隔一秒被唤醒一次 也提供了无参的select方法,当有处于就绪的Channel时,selector就会返回该集合
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();//对就绪的channel进行迭代,进行网络异步读写操作
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    SelectionKey key = null;
                    while (iterator.hasNext()) {
                        key = iterator.next();
                        iterator.remove();
                        try {
                            handleInput(key);
                        } catch (Exception e) {
                            if (key != null) {
                                key.cancel();
                                if (key.channel() != null) key.channel().close();
                            }
                        }
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
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
                if (key.isAcceptable()) {
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();//accept接收客户端连接请求并创建serverSocketChannel实例
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);//完成这个操作相当于完成了TCP三次握手,物理链路正式建立
                }
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(readBuffer);
                    if (readBytes > 0) {
                        readBuffer.flip();//将缓冲区当前的limit设置为position,position设置为0,用于后续操作
                        byte[] bytes = new byte[readBuffer.remaining()];
                        readBuffer.get(bytes);//将缓冲区可读的字节数组赋值到new的bytes数组中
                        String body = new String(bytes, "UTF-8");
                        System.out.println("thr time server received order :" + body);
                        String currentTime = "query time order".equalsIgnoreCase(body) ? new Date().toString() : "bad order";
                        doWrite(sc, currentTime);
                    } else if (readBytes < 0) {
                        key.cancel();
                        sc.close();
                    } else {
                        System.out.println("读到0字节");
                    }
                }

            }
        }

        private void doWrite(SocketChannel channel, String response) throws IOException {
            if (response != null && response.trim().length() > 0) {
                byte[] bytes = response.getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                writeBuffer.put(bytes); //put将bytes数组赋值到缓冲区中
                writeBuffer.flip();
                channel.write(writeBuffer);
                //注:socketChannel是异步非阻塞的,不能保证一次把所有字节都发送给客户端
                // 会出现"半写包的问题",可以通过byteBuffer的hasRemaining处理
            }
        }

    }
}


