package com.hzq.nio.nio;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * fileChannel Test类
 * Created by hzq on 16/7/19.
 */
public class FileChannelTest {

    @Test
    /**
     * 写文件到Buffer,并且输入
     */
    public void test01() throws Exception {
        RandomAccessFile accessFile = new RandomAccessFile("/Users/hzq/Documents/UtilsCode/pom.xml", "rw");
        FileChannel channel = accessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        int read = channel.read(byteBuffer);
        while (read != -1) {
            byteBuffer.flip();

            while (byteBuffer.hasRemaining()) {
                System.out.print((char) byteBuffer.get());
            }
            byteBuffer.clear();
            read = channel.read(byteBuffer);
        }
        accessFile.close();

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
