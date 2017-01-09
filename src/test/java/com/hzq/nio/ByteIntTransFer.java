package com.hzq.nio;

/**
 * Created by hzq on 17/1/5.
 */
public class ByteIntTransFer {


    public static void main(String[] args) {
        System.out.println(bytesToInt(intToBytes(312332731)));
    }

    public static byte[] intToBytes(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (i & 0xFF);
        bytes[1] = (byte) ((i >> 8) & 0xFF);
        bytes[2] = (byte) ((i >> 16) & 0xFF);
        bytes[3] = (byte) ((i >> 24) & 0xFF);
        return bytes;
    }


    public static int bytesToInt(byte[] bytes) {
        return (bytes[0] & 0xFF) | ((bytes[1] & 0xFF) << 8) | ((bytes[2] & 0xFF) << 16) | ((bytes[3] & 0xFF) << 24);
    }
}
