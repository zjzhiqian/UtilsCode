package com.hzq.nio.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * fileChannel,byteBuffer读取文件
 * Created by hzq on 16/7/19.
 */
public class FileChannelTest {

    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("/Users/hzq/Documents/UtilsCode/pom.xml", "rw");
        FileChannel channel = accessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        int read = channel.read(byteBuffer);
        while (read != -1) {
            byteBuffer.flip();

            while (byteBuffer.hasRemaining()) {
                System.out.print((char)byteBuffer.get());
            }
            byteBuffer.clear();
            read = channel.read(byteBuffer);
        }
        accessFile.close();

    }
}
