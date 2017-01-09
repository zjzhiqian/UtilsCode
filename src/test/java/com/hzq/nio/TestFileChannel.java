package com.hzq.nio;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * fileChannel Test类
 * Created by hzq on 16/7/19.
 */
public class TestFileChannel {

    @Test
    public void test00() throws Exception {
        String file = "/Users/hzq/Desktop/aa.txt";
        RandomAccessFile rf = new RandomAccessFile(file, "rw");
        for (int i = 0; i < 10; i++) {//写入基本类型double数据
            rf.writeDouble(i * 1.414);
        }
        rf.close();
        rf = new RandomAccessFile(file, "rw"); //直接将文件指针移到第5个double数据后面
        rf.seek(5 * 8);
        // 覆盖第6个double数据
        rf.writeDouble(47.0001);
        rf.close();
        rf = new RandomAccessFile(file, "r");
        for (int i = 0; i < 10; i++) {
            System.out.println("Value " + i + ": " + rf.readDouble());
        }
        rf.close();

    }


    @Test
    /**
     * 写文件到Buffer,并且输入
     */
    public void test01() throws Exception {
        RandomAccessFile accessFile = new RandomAccessFile("/Users/hzq/Documents/UtilCode/pom.xml", "rw");
        FileChannel channel = accessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = channel.read(buffer);
        while (read != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            buffer.clear();
            read = channel.read(buffer);
        }
    }


    @Test
    /**
     * 文件之间的拷贝,append
     */
    public void test02() throws Exception {
        RandomAccessFile fi = new RandomAccessFile("/Users/hzq/Documents/a.txt", "rw");
        FileChannel fromChannel = fi.getChannel();
        RandomAccessFile to = new RandomAccessFile("/Users/hzq/Documents/b.txt", "rw");
        FileChannel toChannel = to.getChannel();
        long position = 0;
        long count = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, count);
//        toChannel.transferFrom(fromChannel,toChannel.size(),count);
    }

    @Test
    public void test03() throws Exception {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("213".getBytes());

        RandomAccessFile fi = new RandomAccessFile("/Users/hzq/Documents/a.txt", "rw");
        FileChannel channel = fi.getChannel();
        buffer.flip();
        long size = channel.size();
        System.out.println(size);
        channel.position(size);
        channel.write(buffer);
    }

}
