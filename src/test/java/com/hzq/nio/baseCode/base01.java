package com.hzq.nio.baseCode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO基础部分代码和讲解
 * <p>
 * <p>
 * NIO由3大部分组成:Channels,Buffers,Selectors
 * Channel主要包括FileChannel,DatagramChannel,SocketChannel,ServerSocketChannel
 * Buffer 主要包括ByteBuffer,CharBuffer,DoubleBuffer,FloatBuffer,IntBuffer,LongBuffer,ShortBuffer
 */
@SuppressWarnings("all")
public class base01 {
    private static FileChannel channel;

    @Before
    public void test00() throws Exception {
        try {
            RandomAccessFile file = new RandomAccessFile("/Users/hzq/Documents/old/UtilsCode/src/test/java/com/hzq/nio/testDoc/a.txt", "rw");
            channel = file.getChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void test99() throws Exception {
        channel.close();
    }

    @Test
    /**
     * 用Channel,Buffer读取文件代码
     * Buffer一般要有4个步骤  1.写入数据到Buffer 2.调用flip()方法 3.从Buffer中读取数据 4.调用clear()方法或者compact()方法
     * clear()方法会清空整个缓冲区。compact()方法只会清除已经读过的数据.任何未读的数据都被移到缓冲区的起始处,新写入的数据将放到缓冲区未读数据的后面。
     * Buffer的3个属性: capacity position limit 以及调用flip()方法后这3个属性的变化
     * Buffer的分配:  每个Buffer都有一个allocate()方法
     * 向Buffer中写数据:  1.使用channel的read()方法 2.使用buffer本身的put()方法
     * 从Buffer中读数据:  1.使用channel的write()方法,把Buffer数据读到channel里  2.使用buffer本身的get()方法
     * buffer常用方法: 1.flip 2.rewind()重读 position设置为0,limit不变 3.clear() position设置为0 limit设置为capacity
     *                 换句话说buffer被清空了,重新写时不需要之前的数据用clear 4.compact() 将所有未读数据放到Buffer起始处,position设置为未读数据的最后一位
     *                 limit设置为capacity 表示开始写数据 5.mark()与reset() mark标记Buffer中的一个特定position 调用reset()方法恢复到这个position
     *
     */
    public void test01() throws Exception {

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
     * 分散（scatter）从Channel读数据到多个buffer中
     */
    public void test02() throws Exception {
        ByteBuffer header = ByteBuffer.allocate(1);
        ByteBuffer body = ByteBuffer.allocate(44);
        ByteBuffer[] bufferArray = {header, body};
        channel.read(bufferArray);
        header.flip();
        body.flip();
        while (header.hasRemaining()) {
            System.out.print((char) header.get());
        }
        System.out.println();
        System.out.println("==========");
        while (body.hasRemaining()) {
            System.out.print((char) body.get());
        }
    }

    @Test
    /**
     * 聚集（gather) 将多个buffer的数据写入同一个Channel
     */
    public void test03() throws Exception {
        ByteBuffer header = ByteBuffer.allocate(14);
        ByteBuffer body = ByteBuffer.allocate(44);
        header.put("123".getBytes());
        header.flip(); //将buffer转换为读模式 读取到channel 只有position--limit之间的数据会被读
        body.put("4231231".getBytes());
        body.flip();
        ByteBuffer[] byteBuffers = {header, body};
        channel.write(byteBuffers);
    }

    @Test
    /**
     * Channel之间的数据传输 transferFrom()  transferTo()
     */
    public void test04() throws Exception {
        RandomAccessFile fromFile = new RandomAccessFile("/Users/hzq/Documents/old/UtilsCode/src/test/java/com/hzq/nio/testDoc/fromFile.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile("/Users/hzq/Documents/old/UtilsCode/src/test/java/com/hzq/nio/testDoc/toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();
        long position = 0;
        long count = fromChannel.size();
//        toChannel.transferFrom(fromChannel,position,count); //将toFile的 position->后的count位  替换成fromFile的内容
        fromChannel.transferTo(position, count, toChannel); //同上
    }


}
