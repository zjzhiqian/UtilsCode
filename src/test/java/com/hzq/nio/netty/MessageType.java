package com.hzq.nio.netty;

public enum MessageType {

    SERVICE_REQ((byte) 0), //业务请求
    SERVICE_RESP((byte) 1), //业务响应
    ONE_WAY((byte) 2), //既是请求又是响应
    LOGIN_REQ((byte) 3), //握手请求
    LOGIN_RESP((byte) 4), //握手响应
    HEARTBEAT_REQ((byte) 5), //心跳请求
    HEARTBEAT_RESP((byte) 6); //心跳响应

    private byte value;

    private MessageType(byte value) {
        this.value = value;
    }

    public byte value() {
        return this.value;
    }
}
